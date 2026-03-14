package com.example.portscanner;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
public class PortScanner {

    public interface ScanCallback {
        void onPortChecked(int port, boolean isOpen, int checked, int total);
        void onScanComplete(int totalOpen);
        void onScanCancelled();
    }

    public interface CancelChecker {
        boolean isCancelled();
    }

    private final String host;
    private final int portStart;
    private final int portEnd;
    private final int timeoutMs;

    private static final Map<Integer, String> WELL_KNOWN_PORTS = new HashMap<>();

    static {
        WELL_KNOWN_PORTS.put(513, "login/cmd");
        WELL_KNOWN_PORTS.put(514, "Shell/Syslog");
        WELL_KNOWN_PORTS.put(513, "login/cmd");
        WELL_KNOWN_PORTS.put(514, "Shell/Syslog");
        WELL_KNOWN_PORTS.put(21,   "FTP");
        WELL_KNOWN_PORTS.put(22,   "SSH");
        WELL_KNOWN_PORTS.put(23,   "Telnet");
        WELL_KNOWN_PORTS.put(25,   "SMTP");
        WELL_KNOWN_PORTS.put(53,   "DNS");
        WELL_KNOWN_PORTS.put(80,   "HTTP");
        WELL_KNOWN_PORTS.put(110,  "POP3");
        WELL_KNOWN_PORTS.put(119,  "NNTP");
        WELL_KNOWN_PORTS.put(143,  "IMAP");
        WELL_KNOWN_PORTS.put(194,  "IRC");
        WELL_KNOWN_PORTS.put(443,  "HTTPS");
        WELL_KNOWN_PORTS.put(445,  "SMB");
        WELL_KNOWN_PORTS.put(465,  "SMTPS");
        WELL_KNOWN_PORTS.put(587,  "SMTP (submission)");
        WELL_KNOWN_PORTS.put(993,  "IMAPS");
        WELL_KNOWN_PORTS.put(995,  "POP3S");
        WELL_KNOWN_PORTS.put(1080, "SOCKS Proxy");
        WELL_KNOWN_PORTS.put(1194, "OpenVPN");
        WELL_KNOWN_PORTS.put(1433, "MSSQL");
        WELL_KNOWN_PORTS.put(1521, "Oracle DB");
        WELL_KNOWN_PORTS.put(3000, "Node.js / Dev");
        WELL_KNOWN_PORTS.put(3306, "MySQL");
        WELL_KNOWN_PORTS.put(3389, "RDP");
        WELL_KNOWN_PORTS.put(4444, "Metasploit");
        WELL_KNOWN_PORTS.put(5432, "PostgreSQL");
        WELL_KNOWN_PORTS.put(5900, "VNC");
        WELL_KNOWN_PORTS.put(6379, "Redis");
        WELL_KNOWN_PORTS.put(6667, "IRC");
        WELL_KNOWN_PORTS.put(8080, "HTTP Alternativo");
        WELL_KNOWN_PORTS.put(8443, "HTTPS Alternativo");
        WELL_KNOWN_PORTS.put(8888, "Jupyter Notebook");
        WELL_KNOWN_PORTS.put(9200, "Elasticsearch");
        WELL_KNOWN_PORTS.put(27017,"MongoDB");
    }

    public PortScanner(String host, int portStart, int portEnd, int timeoutMs) {
        this.host      = host;
        this.portStart = portStart;
        this.portEnd   = portEnd;
        this.timeoutMs = timeoutMs;
    }

    public void scan(ScanCallback callback, CancelChecker cancelChecker) {
        int total = portEnd - portStart + 1;
        AtomicInteger checked = new AtomicInteger(0);
        AtomicInteger openCount = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(50); // 50 threads paralelas

        for (int port = portStart; port <= portEnd; port++) {
            if (cancelChecker.isCancelled()) break;
            final int p = port;
            executor.submit(() -> {
                if (cancelChecker.isCancelled()) return;
                boolean isOpen = isPortOpen(host, p, timeoutMs);
                int c = checked.incrementAndGet();
                if (isOpen) openCount.incrementAndGet();
                callback.onPortChecked(p, isOpen, c, total);
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        if (cancelChecker.isCancelled()) callback.onScanCancelled();
        else callback.onScanComplete(openCount.get());
    }

    private boolean isPortOpen(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getServiceName(int port) {
        return WELL_KNOWN_PORTS.getOrDefault(port, "");
    }
}
