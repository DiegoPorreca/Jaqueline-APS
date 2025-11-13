# Sistema Distribuído Mobile - Aplicação Completa

Sistema distribuído para dispositivos móveis desenvolvido como projeto acadêmico. A aplicação consiste em um app Android nativo (Kotlin) que se comunica com uma API intermediária (Node.js/Express), que por sua vez encaminha requisições para uma API externa configurável.

## 📱 Arquitetura do Sistema

```
┌─────────────────┐
│  App Android    │
│    (Kotlin)     │
│  Retrofit +     │
│  Coroutines     │
└────────┬────────┘
         │ HTTP/JSON
         │
         ▼
┌─────────────────┐
│ API Intermediária│
│  Node.js/Express │
│  CORS + Axios    │
└────────┬────────┘
         │ HTTP/JSON
         │
         ▼
┌─────────────────┐
│   API Externa   │
│  (Configurável) │
│  Ex: OpenWeather│
│      IBGE       │
└─────────────────┘
```

## 🏗️ Estrutura do Projeto

```
Jaqueline-APS/
├── app/                          # Aplicativo Android
│   └── src/main/
│       ├── java/com/example/myapplicationgemini/
│       │   ├── MainActivity.kt              # Activity principal
│       │   ├── data/
│       │   │   ├── api/
│       │   │   │   └── ApiService.kt        # Interface Retrofit
│       │   │   ├── model/
│       │   │   │   └── ApiResponse.kt       # Modelos de dados
│       │   │   └── repository/
│       │   │       └── DataRepository.kt    # Camada de repositório
│       │   └── ui/viewmodel/
│       │       └── MainViewModel.kt         # ViewModel (MVVM)
│       └── res/
│           └── layout/
│               └── activity_main.xml        # Layout da tela
│
└── api-intermediaria/            # API intermediária Node.js
    ├── server.js                 # Servidor Express
    ├── package.json              # Dependências
    ├── .env.example             # Exemplo de configuração
    └── README.md                # Documentação da API
```

## 🚀 Como Executar

### Pré-requisitos

**Para o App Android:**
- Android Studio (versão mais recente)
- JDK 11 ou superior
- Android SDK (API 24+)
- Emulador Android ou dispositivo físico

**Para a API Intermediária:**
- Node.js (versão 16 ou superior)
- npm ou yarn

### 1️⃣ Configurar e Executar a API Intermediária

1. **Navegue até a pasta da API:**
```bash
cd api-intermediaria
```

2. **Instale as dependências:**
```bash
npm install
```

3. **Configure o arquivo .env:**
```bash
# Copie o exemplo (se não existir)
cp .env.example .env
```

4. **Edite o arquivo .env e configure a URL da API externa:**
```env
PORT=3000
API_DESTINO_URL=https://jsonplaceholder.typicode.com/posts/1
```

**Exemplos de APIs que você pode usar:**
- **🌤️ OpenWeather (clima) - RECOMENDADO:** Veja `CONFIGURACAO_CLIMA.md` para configurar
- **JSONPlaceholder (teste):** `https://jsonplaceholder.typicode.com/posts/1`
- **IBGE (dados do Brasil):** `https://servicodados.ibge.gov.br/api/v1/localidades/estados`
- **ViaCEP (CEP):** `https://viacep.com.br/ws/01310100/json/`

**⭐ Dica:** O app detecta automaticamente dados de clima e exibe de forma amigável com ícones e formatação especial!

5. **Inicie o servidor:**
```bash
npm start
```

O servidor estará rodando em: `http://localhost:3000`

**Verifique se está funcionando:**
- Acesse: `http://localhost:3000/health`
- Ou: `http://localhost:3000/api/data`

### 2️⃣ Configurar e Executar o App Android

1. **Abra o projeto no Android Studio:**
   - File → Open → Selecione a pasta `Jaqueline-APS`

2. **Aguarde o Gradle sincronizar** (pode demorar alguns minutos na primeira vez)

3. **Configure a URL da API no código:**
   
   O app está configurado para usar `http://10.0.2.2:3000` (localhost do emulador).
   
   **Para emulador Android:** Não precisa alterar nada.
   
   **Para dispositivo físico:** 
   - Descubra o IP da sua máquina na rede local
   - Edite `app/build.gradle.kts` linha 23:
   ```kotlin
   buildConfigField("String", "API_BASE_URL", "\"http://SEU_IP:3000\"")
   ```
   - Exemplo: `"http://192.168.1.100:3000"`

4. **Execute o app:**
   - Conecte um dispositivo ou inicie um emulador
   - Clique em "Run" (▶️) no Android Studio
   - Ou use: `Shift + F10`

## 📱 Funcionalidades do App

- ✅ **Tela inicial** com logo e botão "Consultar Dados"
- ✅ **Integração com API intermediária** via Retrofit
- ✅ **Exibição inteligente de dados:**
  - 🌤️ **Detecção automática de dados de clima** com exibição formatada
  - 📊 Exibição genérica em JSON formatado para outros tipos de dados
- ✅ **Tratamento de erros** com mensagens amigáveis
- ✅ **Indicador de carregamento** durante requisições
- ✅ **Logs detalhados** no Logcat
- ✅ **Arquitetura MVVM** com ViewModel e LiveData
- ✅ **Coroutines** para operações assíncronas

## 🔧 Tecnologias Utilizadas

### Mobile (Android)
- **Kotlin** - Linguagem de programação
- **Retrofit** - Cliente HTTP
- **Coroutines** - Programação assíncrona
- **LiveData** - Observáveis reativos
- **ViewModel** - Gerenciamento de estado
- **Material Design** - UI moderna

### API Intermediária
- **Node.js** - Runtime JavaScript
- **Express** - Framework web
- **Axios** - Cliente HTTP
- **CORS** - Cross-Origin Resource Sharing
- **dotenv** - Variáveis de ambiente

## 📝 Estrutura de Código

### App Android

**MainActivity.kt**
- Activity principal que gerencia a UI
- Observa LiveData do ViewModel
- Exibe dados formatados em JSON

**MainViewModel.kt**
- Gerencia a lógica de negócio
- Comunica com o Repository
- Expõe LiveData para a UI

**DataRepository.kt**
- Camada de abstração de dados
- Faz requisições HTTP via Retrofit
- Trata erros e retorna Result

**ApiService.kt**
- Interface Retrofit para comunicação HTTP
- Define endpoints da API

**ApiResponse.kt**
- Modelos de dados (data classes)
- Representa respostas da API

### API Intermediária

**server.js**
- Servidor Express principal
- Rota `/api/data` que encaminha para API externa
- Tratamento de erros robusto
- Logs detalhados

## 🧪 Testando o Sistema

### 1. Teste da API Intermediária

**No navegador ou Postman:**
```bash
# Health check
GET http://localhost:3000/health

# Buscar dados
GET http://localhost:3000/api/data
```

### 2. Teste do App Android

1. Inicie a API intermediária
2. Abra o app no Android Studio
3. Clique no botão "Consultar Dados"
4. Verifique os logs no Logcat
5. Veja os dados exibidos na tela

### 3. Verificar Logs

**API Intermediária (console):**
```
[2024-01-01T12:00:00.000Z] GET /api/data
📡 Encaminhando requisição para: https://...
✅ Resposta recebida da API externa em 150ms
```

**App Android (Logcat):**
```
D/MainActivity: Botão Consultar clicado
D/MainViewModel: Iniciando busca de dados...
D/DataRepository: Iniciando requisição para API intermediária...
D/MainViewModel: Dados recebidos com sucesso
```

## 🐛 Troubleshooting

### Problema: App não consegue conectar à API

**Solução:**
- Verifique se a API intermediária está rodando
- Para emulador: use `http://10.0.2.2:3000`
- Para dispositivo físico: use o IP da sua máquina
- Verifique o Logcat para erros específicos

### Problema: API intermediária retorna erro

**Solução:**
- Verifique se o arquivo `.env` existe e está configurado
- Verifique se a URL da API externa está correta
- Teste a API externa diretamente no navegador
- Verifique os logs do servidor

### Problema: Erro de compilação no Android Studio

**Solução:**
- Sincronize o projeto: File → Sync Project with Gradle Files
- Limpe o projeto: Build → Clean Project
- Reconstrua: Build → Rebuild Project
- Verifique se todas as dependências foram baixadas

## 📚 Documentação Adicional

- [Documentação da API Intermediária](./api-intermediaria/README.md)
- [Documentação do Retrofit](https://square.github.io/retrofit/)
- [Documentação do Express](https://expressjs.com/)
- [Documentação do Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

## 🎯 Próximos Passos (Melhorias Futuras)

- [ ] Cache de dados local (Room Database)
- [ ] Refresh pull-to-refresh
- [ ] Suporte a múltiplas APIs
- [ ] Autenticação e segurança
- [ ] Testes unitários e de integração
- [ ] CI/CD pipeline

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos.

## 👨‍💻 Desenvolvido por

Sistema distribuído para dispositivos móveis - APS 8

---

**Dúvidas?** Verifique os logs ou consulte a documentação específica de cada componente.

