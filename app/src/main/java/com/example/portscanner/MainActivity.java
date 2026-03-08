package com.example.portscanner;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etHost, etPortStart, etPortEnd;
    private Button btnScan, btnClear;
    private TextView tvResults, tvStatus;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    private Handler mainHandler;
    private Thread scanThread;
    private boolean isScanning = false;

    private final List<Integer> openPorts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainHandler = new Handler(Looper.getMainLooper());

        etHost      = findViewById(R.id.etHost);
        etPortStart = findViewById(R.id.etPortStart);
        etPortEnd   = findViewById(R.id.etPortEnd);
        btnScan     = findViewById(R.id.btnScan);
        btnClear    = findViewById(R.id.btnClear);
        tvResults   = findViewById(R.id.tvResults);
        tvStatus    = findViewById(R.id.tvStatus);
        progressBar = findViewById(R.id.progressBar);
        scrollView  = findViewById(R.id.scrollView);

        btnScan.setOnClickListener(v -> {
            if (isScanning) {
                stopScan();
            } else {
                startScan();
            }
        });

        btnClear.setOnClickListener(v -> {
            tvResults.setText("");
            tvStatus.setText("Aguardando varredura...");
            openPorts.clear();
        });
    }

    private void startScan() {
        String host      = etHost.getText().toString().trim();
        String startStr  = etPortStart.getText().toString().trim();
        String endStr    = etPortEnd.getText().toString().trim();

        if (host.isEmpty()) {
            Toast.makeText(this, "Informe o host ou IP", Toast.LENGTH_SHORT).show();
            return;
        }
        if (startStr.isEmpty() || endStr.isEmpty()) {
            Toast.makeText(this, "Informe o intervalo de portas", Toast.LENGTH_SHORT).show();
            return;
        }

        int portStart, portEnd;
        try {
            portStart = Integer.parseInt(startStr);
            portEnd   = Integer.parseInt(endStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Portas inválidas", Toast.LENGTH_SHORT).show();
            return;
        }

        if (portStart < 1 || portEnd > 65535 || portStart > portEnd) {
            Toast.makeText(this, "Intervalo de portas inválido (1-65535)", Toast.LENGTH_SHORT).show();
            return;
        }

        openPorts.clear();
        tvResults.setText("");
        isScanning = true;
        btnScan.setText("⛔ Parar");
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(portEnd - portStart + 1);
        progressBar.setProgress(0);

        final int fPortStart = portStart;
        final int fPortEnd   = portEnd;

        scanThread = new Thread(() -> {
            PortScanner scanner = new PortScanner(host, fPortStart, fPortEnd, 500);

            mainHandler.post(() ->
                tvStatus.setText("🔍 Escaneando " + host + " [" + fPortStart + " - " + fPortEnd + "]..."));

            scanner.scan(new PortScanner.ScanCallback() {
                @Override
                public void onPortChecked(int port, boolean isOpen, int checked, int total) {
                    mainHandler.post(() -> {
                        progressBar.setProgress(checked);
                        tvStatus.setText("Verificando porta " + port + "... (" + checked + "/" + total + ")");
                        if (isOpen) {
                            openPorts.add(port);
                            String service = PortScanner.getServiceName(port);
                            String portStr = String.format("%-6d", port);
                            String serviceTag = service.isEmpty() ? "" : "  [" + service + "]";
                            String line = "✅  " + portStr + "  ABERTA" + serviceTag + "\n";
                            tvResults.append(line);
                            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                        }
                    });
                }

                @Override
                public void onScanComplete(int totalOpen) {
                    mainHandler.post(() -> {
                        isScanning = false;
                        btnScan.setText("▶ Escanear");
                        progressBar.setVisibility(View.GONE);
                        if (totalOpen == 0) {
                            tvResults.setText("Nenhuma porta aberta encontrada no intervalo.");
                        }
                        tvStatus.setText("✔ Varredura concluída! " + totalOpen + " porta(s) aberta(s) encontrada(s).");
                    });
                }

                @Override
                public void onScanCancelled() {
                    mainHandler.post(() -> {
                        isScanning = false;
                        btnScan.setText("▶ Escanear");
                        progressBar.setVisibility(View.GONE);
                        tvStatus.setText("⚠ Varredura interrompida pelo usuário.");
                    });
                }
            }, () -> !isScanning);
        });

        scanThread.start();
    }

    private void stopScan() {
        isScanning = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isScanning = false;
    }
}
