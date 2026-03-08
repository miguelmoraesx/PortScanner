# 🔍 PortScanner Android

<div align="center">

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)

<br/>

> **Ferramenta educacional de varredura de portas TCP para Android**  
> Desenvolvida para aprendizado em redes, sockets e segurança da informação.

</div>

---

## 📋 Sobre o Projeto

O **PortScanner** é um aplicativo Android desenvolvido em **Java** que permite realizar varredura de portas TCP em qualquer host de rede. O usuário informa um endereço IP ou domínio e um intervalo de portas, e o app verifica quais estão abertas — identificando automaticamente o serviço correspondente.

O projeto foi criado com fins **educacionais**, demonstrando na prática conceitos fundamentais de:

- 🌐 Comunicação em redes TCP/IP
- 🔌 Java Sockets (`java.net.Socket`)
- 🧵 Programação concorrente no Android
- 📱 Desenvolvimento de interfaces com XML

---

## ✨ Funcionalidades

| Recurso | Descrição |
|---|---|
| 🔍 **Varredura TCP** | Verifica portas no intervalo de 1 a 65535 |
| 🏷️ **Identificação de Serviços** | Reconhece 35+ serviços conhecidos (HTTP, SSH, FTP, MySQL...) |
| 📊 **Progresso em Tempo Real** | Barra de progresso + contador de portas verificadas |
| ⛔ **Cancelamento** | Interrompe a varredura a qualquer momento |
| 🌙 **Tema Dark** | Interface moderna inspirada no GitHub Dark |
| ⚡ **Thread em Background** | Não trava a UI durante o scan |

---

## 🛠️ Tecnologias

```
📦 PortScanner
├── ☕ Java 8
├── 📱 Android SDK (minSdk 21 / targetSdk 34)
├── 🔌 java.net.Socket — conexões TCP
├── 🧵 Thread + Handler/Looper — background + UI thread
├── 🎨 XML Layouts — interface declarativa
└── 📦 Gradle — build system
```

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
├── gradle.properties
├── build.gradle
└── settings.gradle
```

---

## 🚀 Como Executar

### Pré-requisitos

- [Android Studio](https://developer.android.com/studio) Hedgehog ou superior
- JDK 8+
- Android SDK API 21+
- Emulador ou dispositivo Android

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/miguelmoraesx/PortScanner.git

# 2. Abra no Android Studio
# File → Open → selecione a pasta PortScanner/

# 3. Aguarde a sincronização do Gradle

# 4. Execute o app
# Clique em ▶ Run (Shift+F10)
```

---

## 🧪 Hosts para Teste

> ⚠️ Use apenas em hosts com autorização explícita!

| Host | Descrição |
|---|---|
| `scanme.nmap.org` | Host público do projeto Nmap — liberado para testes |
| `127.0.0.1` | Localhost do emulador |
| `10.0.2.2` | Máquina host do emulador Android |
| IP da sua rede local | Com permissão do administrador |

---

## 🏷️ Serviços Reconhecidos

O app identifica automaticamente mais de **35 serviços conhecidos**, incluindo:

```
21   → FTP          22   → SSH          23   → Telnet
25   → SMTP         53   → DNS          80   → HTTP
110  → POP3         143  → IMAP         443  → HTTPS
445  → SMB          513  → login/cmd    514  → Shell/Syslog
3306 → MySQL        3389 → RDP          5432 → PostgreSQL
5900 → VNC          6379 → Redis        8080 → HTTP Alt
8888 → Jupyter      9200 → Elasticsearch
27017→ MongoDB      ...e muito mais!
```

---

## ⚠️ Aviso Legal

> Este aplicativo é destinado **exclusivamente para fins educacionais**.  
> Utilize apenas em redes e hosts para os quais você possui **autorização explícita**.  
> A varredura não autorizada de portas pode ser **ilegal** em diversas jurisdições.  
> O autor não se responsabiliza pelo uso indevido desta ferramenta.

---

## 📚 Conceitos Abordados

- **Sockets TCP** — como funciona uma conexão de rede em baixo nível
- **Threading no Android** — por que operações de rede não podem rodar na UI thread
- **Handler/Looper** — comunicação segura entre threads no Android
- **Port Scanning** — como ferramentas como nmap identificam serviços ativos
- **Timeout de conexão** — estratégias para varredura eficiente

---

## 👨‍💻 Autor

<div align="center">

Desenvolvido para fins educacionais

[![GitHub](https://img.shields.io/badge/GitHub-miguelmoraesx-181717?style=for-the-badge&logo=github)](https://github.com/miguelmoraesx)

⭐ Se este projeto te ajudou, deixa uma estrela no repositório!

</div>
