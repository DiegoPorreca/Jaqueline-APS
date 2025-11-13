# 🌤️ Configuração de API de Clima

## Opção 1: OpenWeather (Recomendado - Mais Completo)

### Passo a Passo:

1. **Obtenha uma API Key gratuita:**
   - Acesse: https://openweathermap.org/api
   - Clique em "Sign Up" e crie uma conta (é grátis!)
   - Após criar a conta, vá em "API keys"
   - Copie sua chave (pode demorar alguns minutos para ativar)

2. **Configure no arquivo `.env`:**
```env
PORT=3000
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=São Paulo,BR&appid=SUA_API_KEY_AQUI&units=metric&lang=pt_br
```

3. **Substitua:**
   - `SUA_API_KEY_AQUI` pela sua chave
   - `São Paulo,BR` pela cidade desejada (ex: `Rio de Janeiro,BR`, `New York,US`)

### Exemplo de URL configurada:
```env
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=São Paulo,BR&appid=abc123def456ghi789&units=metric&lang=pt_br
```

## Opção 2: API Alternativa (wttr.in - Não precisa de chave)

Se não quiser criar conta no OpenWeather, use esta alternativa:

```env
PORT=3000
API_DESTINO_URL=https://wttr.in/São Paulo?format=j1
```

**Nota:** Esta API tem formato diferente, mas também funciona!

## 🧪 Testar a URL

Antes de usar no app, teste a URL no navegador:

1. **Para OpenWeather:**
   - Cole a URL completa no navegador
   - Deve retornar um JSON com dados de clima

2. **Para wttr.in:**
   - Acesse: `https://wttr.in/São Paulo?format=j1`
   - Deve retornar JSON com dados de clima

## ✅ Verificação

Se a URL estiver correta, você verá no app:
- 🌤️ Previsão do Tempo
- 📍 Nome da cidade
- 🌡️ Temperatura
- ☁️ Condição do tempo
- 💧 Umidade
- 💨 Velocidade do vento

## 🔧 Parâmetros da URL OpenWeather

- `q=São Paulo,BR` - Cidade e país
- `appid=SUA_KEY` - Sua API key
- `units=metric` - Temperatura em Celsius
- `lang=pt_br` - Idioma português brasileiro

## 📝 Exemplos de Cidades

```env
# São Paulo, Brasil
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=São Paulo,BR&appid=SUA_KEY&units=metric&lang=pt_br

# Rio de Janeiro, Brasil
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=Rio de Janeiro,BR&appid=SUA_KEY&units=metric&lang=pt_br

# New York, USA
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=New York,US&appid=SUA_KEY&units=metric&lang=pt_br

# Londres, Reino Unido
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=London,GB&appid=SUA_KEY&units=metric&lang=pt_br
```


