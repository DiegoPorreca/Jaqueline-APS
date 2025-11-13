# 🌤️ Configuração Rápida - API de Clima

## ⚡ Configuração Rápida (5 minutos)

### Passo 1: Obter API Key do OpenWeather (Gratuito)

1. Acesse: **https://openweathermap.org/api**
2. Clique em **"Sign Up"** (canto superior direito)
3. Preencha o formulário e crie sua conta (é grátis!)
4. Após criar, faça login
5. Vá em **"API keys"** no menu
6. Copie sua chave (pode levar alguns minutos para ativar)

### Passo 2: Configurar o arquivo .env

1. Vá para a pasta `api-intermediaria/`
2. Crie o arquivo `.env` (se não existir)
3. Adicione:

```env
PORT=3000
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=São Paulo,BR&appid=COLE_SUA_API_KEY_AQUI&units=metric&lang=pt_br
```

4. **Substitua `COLE_SUA_API_KEY_AQUI` pela chave que você copiou**

### Passo 3: Testar

1. Inicie a API intermediária:
```bash
cd api-intermediaria
npm start
```

2. Teste no navegador:
   - Acesse: `http://localhost:3000/api/data`
   - Deve retornar JSON com dados de clima

3. Execute o app Android e clique em "Consultar Dados"

## ✅ Resultado Esperado no App

Quando configurado corretamente, o app exibirá:

```
🌤️ PREVISÃO DO TEMPO
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

📍 Cidade: São Paulo

🌡️ Temperatura: 25.5°C
🤲 Sensação: 26.0°C
☁️ Condição: Nublado
💧 Umidade: 65%
📊 Pressão: 1013 hPa
💨 Vento: 12.5 km/h
```

## 🔄 Alterar Cidade

Para consultar outra cidade, altere no `.env`:

```env
# Rio de Janeiro
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=Rio de Janeiro,BR&appid=SUA_KEY&units=metric&lang=pt_br

# New York
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=New York,US&appid=SUA_KEY&units=metric&lang=pt_br
```

## 🆘 Problemas Comuns

**Erro: "Invalid API key"**
- Verifique se copiou a chave corretamente
- Aguarde alguns minutos após criar a conta (pode demorar para ativar)

**Erro: "City not found"**
- Verifique o nome da cidade
- Use o formato: `Cidade,País` (ex: `São Paulo,BR`)

**Não aparece dados formatados**
- Verifique se a API retornou dados válidos
- Veja os logs do servidor para mais detalhes

## 📚 Mais Informações

- Veja `api-intermediaria/CONFIG_CLIMA.md` para mais detalhes
- Documentação OpenWeather: https://openweathermap.org/current


