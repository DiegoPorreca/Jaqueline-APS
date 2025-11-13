package com.example.myapplicationgemini.data.repository

import android.util.Log
import com.example.myapplicationgemini.data.api.RetrofitClient
import com.example.myapplicationgemini.data.model.ApiResponse

/**
 * Repository pattern para gerenciar a comunicação com a API
 * Esta camada abstrai a fonte de dados e facilita testes e manutenção
 */
class DataRepository {
    
    private val apiService = RetrofitClient.apiService
    private val TAG = "DataRepository"
    
    /**
     * Busca dados da API intermediária
     * @return Result contendo ApiResponse em caso de sucesso ou Exception em caso de erro
     */
    suspend fun fetchData(): Result<ApiResponse> {
        return try {
            Log.d(TAG, "Iniciando requisição para API intermediária...")
            val response = apiService.getData()
            
            if (response.success) {
                Log.d(TAG, "Dados recebidos com sucesso: ${response.message}")
                Result.success(response)
            } else {
                val error = Exception(response.message ?: "Erro desconhecido")
                Log.e(TAG, "Erro na resposta da API: ${error.message}")
                Result.failure(error)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar dados: ${e.message}", e)
            Result.failure(e)
        }
    }
}


