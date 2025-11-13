# Configuração do Arquivo .env

## 📝 Como Configurar

1. **Crie o arquivo `.env` na pasta `api-intermediaria/`**

2. **Adicione o seguinte conteúdo:**

```env
# Porta em que o servidor irá rodar
PORT=3000

# URL da API externa de destino
API_DESTINO_URL=https://jsonplaceholder.typicode.com/posts/1
```

## 🔧 Exemplos de APIs

### 1. JSONPlaceholder (Teste - Recomendado para começar)
```env
API_DESTINO_URL=https://jsonplaceholder.typicode.com/posts/1
```

### 2. IBGE (Dados do Brasil - Não precisa de API key)
```env
API_DESTINO_URL=https://servicodados.ibge.gov.br/api/v1/localidades/estados
```

### 3. ViaCEP (CEP - Não precisa de API key)
```env
API_DESTINO_URL=https://viacep.com.br/ws/01310100/json/
```

### 4. OpenWeather (Clima - Precisa de API key) ⭐ RECOMENDADO PARA CLIMA
1. Obtenha uma API key gratuita em: https://openweathermap.org/api
2. Configure:
```env
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=São Paulo,BR&appid=SUA_API_KEY_AQUI&units=metric&lang=pt_br
```
3. **Veja instruções detalhadas em:** `CONFIG_CLIMA.md`

### 4.1. API de Clima Alternativa (wttr.in - Não precisa de chave)
```env
API_DESTINO_URL=https://wttr.in/São Paulo?format=j1
```

### 5. JSONPlaceholder - Lista de posts
```env
API_DESTINO_URL=https://jsonplaceholder.typicode.com/posts
```

## ⚠️ Importante

- **NÃO** commite o arquivo `.env` no Git (já está no .gitignore)
- **COPIE** o conteúdo acima e cole no arquivo `.env` que você criar
- **TESTE** a URL no navegador antes de usar no app

## 🧪 Testar a URL

Antes de usar no app, teste a URL diretamente no navegador:

1. Abra a URL no navegador
2. Deve retornar um JSON válido
3. Se funcionar, está pronta para usar!

