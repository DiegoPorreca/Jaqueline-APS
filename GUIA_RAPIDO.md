# 🚀 Guia Rápido de Execução

## ⚡ Início Rápido (5 minutos)

### 1. API Intermediária (Terminal 1)

```bash
cd api-intermediaria
npm install
npm start
```

✅ Servidor rodando em `http://localhost:3000`

### 2. App Android (Android Studio)

1. Abra o projeto no Android Studio
2. Aguarde sincronização do Gradle
3. Execute o app (▶️ ou Shift+F10)
4. Clique em "Consultar Dados"

✅ Dados aparecerão na tela!

## 🔧 Configuração Rápida da API Externa

Edite `api-intermediaria/.env`:

```env
# Exemplo 1: API de teste
API_DESTINO_URL=https://jsonplaceholder.typicode.com/posts/1

# Exemplo 2: IBGE (dados do Brasil)
API_DESTINO_URL=https://servicodados.ibge.gov.br/api/v1/localidades/estados

# Exemplo 3: OpenWeather (precisa de API key)
API_DESTINO_URL=https://api.openweathermap.org/data/2.5/weather?q=São Paulo,BR&appid=SUA_KEY&units=metric
```

## 📱 URLs Importantes

- **API Health:** http://localhost:3000/health
- **API Dados:** http://localhost:3000/api/data
- **App:** Abra no emulador/dispositivo

## ⚠️ Problemas Comuns

**App não conecta?**
- Emulador: use `10.0.2.2:3000` (já configurado)
- Dispositivo físico: altere IP em `app/build.gradle.kts` linha 23

**API não inicia?**
- Verifique se Node.js está instalado: `node --version`
- Verifique se a porta 3000 está livre

**Erro de compilação?**
- File → Sync Project with Gradle Files
- Build → Clean Project → Rebuild Project

## 📞 Teste Rápido

1. Abra: http://localhost:3000/api/data no navegador
2. Deve retornar JSON com dados
3. Se funcionar, o app também funcionará!


