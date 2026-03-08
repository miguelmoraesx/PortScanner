# 🔍 PortScanner Android

Aplicativo Android educacional para varredura de portas TCP em redes locais ou remotas.

---

## 📁 Estrutura do Projeto

```
PortScanner/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/portscanner/
│       │   ├── MainActivity.java       ← Tela principal + controle de UI
│       │   └── PortScanner.java        ← Lógica de scanning com Sockets
│       └── res/
│           ├── layout/
│           │   └── activity_main.xml   ← Layout da tela
│           ├── drawable/
│           │   ├── card_background.xml
│           │   └── input_background.xml
│           └── values/
│               └── strings.xml
├── build.gradle
├── settings.gradle
└── README.md
```

---

## ⚙️ Como importar no Android Studio

1. Abra o **Android Studio**
2. Clique em **"Open"** (não em "New Project")
3. Navegue até a pasta `PortScanner/` e selecione-a
4. Aguarde a sincronização do Gradle
5. Conecte um dispositivo ou inicie um emulador
6. Clique em **▶ Run**

---

## 🚀 Funcionalidades

| Recurso | Descrição |
|---|---|
| Scan de portas TCP | Verifica portas de 1 a 65535 |
| Identificação de serviços | Reconhece 30+ serviços conhecidos |
| Progresso em tempo real | Barra de progresso + status por porta |
| Cancelamento | Botão para interromper o scan |
| Tema dark | Interface inspirada no GitHub Dark |
| Timeout configurável | 500ms por porta (editável no código) |

---

## 🧰 Tecnologias

- **Java** — linguagem principal
- **Android SDK** mínimo API 21 (Android 5.0+)
- **java.net.Socket** — conexões TCP
- **Thread + Handler** — execução em background sem travar a UI
- **XML Layouts** — interface declarativa

---

## 🧪 Hosts de teste (com autorização)

- `scanme.nmap.org` — host público do projeto Nmap para testes legais
- `127.0.0.1` — localhost (emulador)
- IPs da sua rede local (com permissão do administrador)

---

## ⚠️ Aviso Legal

> Este aplicativo é destinado **exclusivamente para fins educacionais**.  
> Use apenas em redes e hosts para os quais você tem **autorização explícita**.  
> A varredura não autorizada de portas pode ser ilegal em várias jurisdições.

---

## 📖 Conceitos abordados

- **Sockets TCP** — como funciona uma conexão de rede
- **Threading no Android** — por que operações de rede não podem rodar na UI thread
- **Handler/Looper** — comunicação entre threads no Android
- **Port scanning** — como ferramentas como nmap identificam serviços
