package com.example.myapplicationgemini.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationgemini.data.model.ApiResponse
import com.example.myapplicationgemini.data.repository.DataRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para gerenciar o estado da UI e a lógica de negócio
 * Utiliza LiveData para comunicação reativa com a Activity
 */
class MainViewModel : ViewModel() {
    
    private val repository = DataRepository()
    private val TAG = "MainViewModel"
    
    // LiveData para o estado de carregamento
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    // LiveData para os dados recebidos
    private val _apiResponse = MutableLiveData<ApiResponse?>()
    val apiResponse: LiveData<ApiResponse?> = _apiResponse
    
    // LiveData para mensagens de erro
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    /**
     * Função para buscar dados da API
     * Executa em uma coroutine para não bloquear a thread principal
     */
    fun fetchData() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Iniciando busca de dados...")
                _isLoading.value = true
                _errorMessage.value = null
                
                val result = repository.fetchData()
                
                result.onSuccess { response ->
                    Log.d(TAG, "Dados recebidos com sucesso")
                    _apiResponse.value = response
                    _isLoading.value = false
                }.onFailure { exception ->
                    Log.e(TAG, "Erro ao buscar dados: ${exception.message}", exception)
                    _errorMessage.value = "Erro: ${exception.message}"
                    _isLoading.value = false
                    _apiResponse.value = null
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exceção não tratada: ${e.message}", e)
                _errorMessage.value = "Erro inesperado: ${e.message}"
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Limpa os dados e mensagens de erro
     */
    fun clearData() {
        _apiResponse.value = null
        _errorMessage.value = null
    }
}


