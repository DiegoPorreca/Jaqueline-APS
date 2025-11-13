# API Intermediária - Sistema Distribuído Mobile

API intermediária desenvolvida em Node.js + Express que recebe requisições do aplicativo Android e encaminha para uma API externa configurável.

## 📋 Funcionalidades

- ✅ Recebe requisições GET do app mobile
- ✅ Encaminha requisições para API externa configurável
- ✅ Trata respostas e erros
- ✅ Retorna dados formatados em JSON
- ✅ Suporta CORS para comunicação com mobile
- ✅ Logs detalhados de requisições
- ✅ Tratamento robusto de erros

## 🚀 Instalação

1. **Instale as dependências:**
```bash
npm install
```

2. **Configure o arquivo .env:**
```bash
cp .env.example .env
```

3. **Edite o arquivo .env e configure a URL da API externa:**
```env
PORT=3000
API_DESTINO_URL=https://jsonplaceholder.typicode.com/posts/1
```

## 🏃 Execução

**Modo produção:**
```bash
npm start
```

**Modo desenvolvimento (com nodemon):**
```bash
npm run dev
```

O servidor estará disponível em: `http://localhost:3000`

## 📡 Endpoints

### GET `/`
Retorna informações sobre a API.

### GET `/health`
Health check da API.

### GET `/api/data`
Endpoint principal que busca dados da API externa configurada.

**Resposta de sucesso:**
```json
{
  "success": true,
  "data": { ... },
  "message": "Dados obtidos com sucesso da API externa",
  "timestamp": "2024-01-01T12:00:00.000Z",
  "metadata": {
    "statusCode": 200,
    "duration": "150ms",
    "apiDestino": "https://..."
  }
}
```

**Resposta de erro:**
```json
{
  "success": false,
  "message": "Erro ao processar requisição",
  "error": "Detalhes do erro",
  "timestamp": "2024-01-01T12:00:00.000Z"
}
```

## 🔧 Configuração da API Externa

Edite o arquivo `.env` e altere a variável `API_DESTINO_URL`:

### Exemplos de APIs:

**1. JSONPlaceholder (Teste):**
```env
API_DESTINO_URL=https://jsonplaceholder.typicode.com/posts/1
```

**2. OpenWeather (Clima):**
```env
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=São Paulo,BR&appid=SUA_API_KEY&units=metric&lang=pt_br
```

**3. IBGE (Dados do Brasil):**
```env
API_DESTINO_URL=https://servicodados.ibge.gov.br/api/v1/localidades/estados
```

**4. ViaCEP (CEP):**
```env
API_DESTINO_URL=https://viacep.com.br/ws/01310100/json/
```

## 📝 Logs

A API gera logs detalhados no console:
- ✅ Requisições recebidas
- ✅ Respostas da API externa
- ❌ Erros e exceções
- ⏱️ Tempo de resposta

## 🔒 Segurança

- CORS habilitado para comunicação com o app mobile
- Validação de configuração da API destino
- Timeout de 10 segundos para requisições
- Tratamento de erros robusto

## 🐛 Troubleshooting

**Erro: "API_DESTINO_URL não configurada"**
- Verifique se o arquivo `.env` existe e contém a variável `API_DESTINO_URL`

**Erro: "ECONNREFUSED"**
- Verifique se a URL da API externa está correta
- Verifique sua conexão com a internet

**Erro: "ETIMEDOUT"**
- A API externa pode estar lenta ou indisponível
- Tente novamente após alguns segundos


