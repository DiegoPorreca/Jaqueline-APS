/**
 * API Intermediária - Sistema Distribuído Mobile
 * 
 * Esta API recebe requisições do aplicativo Android e encaminha
 * para uma API externa configurável via variável de ambiente.
 * 
 * Funcionalidades:
 * - Recebe requisições GET do app mobile
 * - Encaminha para API externa configurada
 * - Trata respostas e erros
 * - Retorna dados formatados em JSON
 * - Suporta CORS para comunicação com mobile
 */

require('dotenv').config();
const express = require('express');
const axios = require('axios');
const cors = require('cors');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors()); // Permite requisições do app mobile
app.use(express.json()); // Parse JSON no body
app.use(express.urlencoded({ extended: true }));

// Middleware de logging
app.use((req, res, next) => {
    const timestamp = new Date().toISOString();
    console.log(`[${timestamp}] ${req.method} ${req.path}`);
    next();
});

/**
 * Rota principal - Health check
 */
app.get('/', (req, res) => {
    res.json({
        success: true,
        message: 'API Intermediária - Sistema Distribuído Mobile',
        version: '1.0.0',
        endpoints: {
            health: '/health',
            data: '/api/data'
        }
    });
});

/**
 * Rota de health check
 */
app.get('/health', (req, res) => {
    res.json({
        success: true,
        status: 'online',
        timestamp: new Date().toISOString(),
        apiDestino: process.env.API_DESTINO_URL || 'Não configurada'
    });
});

/**
 * Rota principal para buscar dados da API externa
 * GET /api/data
 * 
 * Esta rota:
 * 1. Recebe a requisição do app mobile
 * 2. Faz uma requisição para a API externa configurada
 * 3. Retorna os dados formatados
 */
app.get('/api/data', async (req, res) => {
    const startTime = Date.now();
    
    try {
        // Verifica se a URL da API destino está configurada
        const apiDestinoUrl = process.env.API_DESTINO_URL;
        
        if (!apiDestinoUrl) {
            console.error('❌ Erro: API_DESTINO_URL não configurada no .env');
            return res.status(500).json({
                success: false,
                message: 'API de destino não configurada. Verifique o arquivo .env',
                timestamp: new Date().toISOString()
            });
        }

        console.log(`📡 Encaminhando requisição para: ${apiDestinoUrl}`);

        // Faz a requisição para a API externa
        const response = await axios.get(apiDestinoUrl, {
            timeout: 10000, // Timeout de 10 segundos
            headers: {
                'Accept': 'application/json',
                'User-Agent': 'Sistema-Distribuido-Mobile/1.0'
            }
        });

        const duration = Date.now() - startTime;
        console.log(`✅ Resposta recebida da API externa em ${duration}ms`);

        // Retorna os dados formatados
        res.json({
            success: true,
            data: response.data,
            message: 'Dados obtidos com sucesso da API externa',
            timestamp: new Date().toISOString(),
            metadata: {
                statusCode: response.status,
                duration: `${duration}ms`,
                apiDestino: apiDestinoUrl
            }
        });

    } catch (error) {
        const duration = Date.now() - startTime;
        console.error(`❌ Erro ao buscar dados: ${error.message}`);
        
        // Tratamento de erros específicos
        if (error.code === 'ECONNREFUSED') {
            return res.status(503).json({
                success: false,
                message: 'Não foi possível conectar à API externa. Verifique se a URL está correta.',
                error: error.message,
                timestamp: new Date().toISOString()
            });
        }
        
        if (error.code === 'ETIMEDOUT' || error.code === 'ECONNABORTED') {
            return res.status(504).json({
                success: false,
                message: 'Timeout ao conectar com a API externa. Tente novamente.',
                error: error.message,
                timestamp: new Date().toISOString()
            });
        }
        
        if (error.response) {
            // A API externa retornou um erro HTTP
            return res.status(error.response.status).json({
                success: false,
                message: `Erro na API externa: ${error.response.status} ${error.response.statusText}`,
                error: error.message,
                timestamp: new Date().toISOString()
            });
        }
        
        // Erro genérico
        res.status(500).json({
            success: false,
            message: 'Erro interno ao processar requisição',
            error: error.message,
            timestamp: new Date().toISOString()
        });
    }
});

/**
 * Rota para POST (caso necessário no futuro)
 */
app.post('/api/data', async (req, res) => {
    try {
        const apiDestinoUrl = process.env.API_DESTINO_URL;
        
        if (!apiDestinoUrl) {
            return res.status(500).json({
                success: false,
                message: 'API de destino não configurada'
            });
        }

        const response = await axios.post(apiDestinoUrl, req.body, {
            timeout: 10000,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        });

        res.json({
            success: true,
            data: response.data,
            message: 'Dados enviados com sucesso',
            timestamp: new Date().toISOString()
        });

    } catch (error) {
        console.error('Erro no POST:', error.message);
        res.status(500).json({
            success: false,
            message: 'Erro ao processar requisição POST',
            error: error.message,
            timestamp: new Date().toISOString()
        });
    }
});

// Tratamento de rotas não encontradas
app.use((req, res) => {
    res.status(404).json({
        success: false,
        message: 'Rota não encontrada',
        path: req.path,
        timestamp: new Date().toISOString()
    });
});

// Tratamento de erros não capturados
app.use((err, req, res, next) => {
    console.error('Erro não tratado:', err);
    res.status(500).json({
        success: false,
        message: 'Erro interno do servidor',
        error: err.message,
        timestamp: new Date().toISOString()
    });
});

// Inicia o servidor
app.listen(PORT, () => {
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
    console.log('🚀 API Intermediária - Sistema Distribuído');
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
    console.log(`✅ Servidor rodando na porta ${PORT}`);
    console.log(`📡 API Destino: ${process.env.API_DESTINO_URL || 'NÃO CONFIGURADA'}`);
    console.log(`🌐 Acesse: http://localhost:${PORT}`);
    console.log(`🏥 Health Check: http://localhost:${PORT}/health`);
    console.log(`📊 Endpoint de Dados: http://localhost:${PORT}/api/data`);
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
});


