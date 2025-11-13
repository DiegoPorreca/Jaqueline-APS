package com.example.myapplicationgemini

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplicationgemini.data.model.ApiResponse
import com.example.myapplicationgemini.databinding.ActivityMainBinding
import com.example.myapplicationgemini.ui.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser

/**
 * Activity principal do aplicativo
 * Implementa a arquitetura MVVM com ViewModel e LiveData
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val TAG = "MainActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        Log.d(TAG, "MainActivity criada")
        
        setupObservers()
        setupClickListeners()
    }
    
    /**
     * Configura os observadores do LiveData do ViewModel
     */
    private fun setupObservers() {
        // Observa o estado de carregamento
        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnConsultar.isEnabled = !isLoading
            
            if (isLoading) {
                binding.btnConsultar.text = "Consultando..."
            } else {
                binding.btnConsultar.text = "Consultar Dados"
            }
        })
        
        // Observa a resposta da API
        viewModel.apiResponse.observe(this, Observer { response ->
            if (response != null) {
                Log.d(TAG, "Dados recebidos: ${response.data}")
                displayData(response)
            }
        })
        
        // Observa mensagens de erro
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) {
                Log.e(TAG, "Erro: $errorMessage")
                showError(errorMessage)
            }
        })
    }
    
    /**
     * Configura os listeners dos botões
     */
    private fun setupClickListeners() {
        binding.btnConsultar.setOnClickListener {
            Log.d(TAG, "Botão Consultar clicado")
            viewModel.clearData()
            binding.textResult.visibility = View.GONE
            viewModel.fetchData()
        }
    }
    
    /**
     * Exibe os dados recebidos da API de forma formatada
     * Detecta automaticamente se são dados de clima e formata adequadamente
     */
    private fun displayData(response: ApiResponse) {
        try {
            val gson = Gson()
            val dataJson = gson.toJson(response.data)
            
            // Tenta detectar se são dados de clima
            val weatherData = try {
                gson.fromJson(dataJson, com.example.myapplicationgemini.data.model.WeatherData::class.java)
            } catch (e: Exception) {
                null
            }
            
            // Se for dados de clima, exibe de forma amigável
            if (weatherData != null && weatherData.cityName != null) {
                displayWeatherData(weatherData, response)
            } else {
                // Exibe dados genéricos em JSON formatado
                val jsonElement: JsonElement = JsonParser.parseString(dataJson)
                val formattedJson = gson.newBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(jsonElement)
                
                binding.textResult.text = buildString {
                    append("✅ Dados Recebidos com Sucesso!\n\n")
                    append("📊 Resposta da API:\n")
                    append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n")
                    append("Status: ${if (response.success) "Sucesso" else "Erro"}\n")
                    response.message?.let { append("Mensagem: $it\n") }
                    response.timestamp?.let { append("Timestamp: $it\n") }
                    append("\n📦 Dados (JSON):\n")
                    append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
                    append(formattedJson)
                }
            }
            
            binding.textResult.visibility = View.VISIBLE
            Toast.makeText(this, "Dados carregados com sucesso!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao formatar dados: ${e.message}", e)
            binding.textResult.text = "Erro ao formatar dados: ${e.message}"
            binding.textResult.visibility = View.VISIBLE
        }
    }
    
    /**
     * Exibe dados de clima de forma amigável e formatada
     */
    private fun displayWeatherData(weatherData: com.example.myapplicationgemini.data.model.WeatherData, response: ApiResponse) {
        val main = weatherData.main
        val weather = weatherData.weather?.firstOrNull()
        val wind = weatherData.wind
        
        binding.textResult.text = buildString {
            append("🌤️ PREVISÃO DO TEMPO\n")
            append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n")
            
            // Cidade
            append("📍 Cidade: ${weatherData.cityName ?: "N/A"}\n\n")
            
            // Temperatura
            main?.temperature?.let { temp ->
                append("🌡️ Temperatura: ${String.format("%.1f", temp)}°C\n")
            }
            
            // Sensação térmica
            main?.feelsLike?.let { feelsLike ->
                append("🤲 Sensação: ${String.format("%.1f", feelsLike)}°C\n")
            }
            
            // Descrição do tempo
            weather?.description?.let { desc ->
                val descFormatted = desc.split(" ").joinToString(" ") { word ->
                    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                }
                append("☁️ Condição: $descFormatted\n")
            }
            
            // Umidade
            main?.humidity?.let { humidity ->
                append("💧 Umidade: $humidity%\n")
            }
            
            // Pressão
            main?.pressure?.let { pressure ->
                append("📊 Pressão: $pressure hPa\n")
            }
            
            // Vento
            wind?.speed?.let { speed ->
                val speedKmh = speed * 3.6 // Converte m/s para km/h
                append("💨 Vento: ${String.format("%.1f", speedKmh)} km/h\n")
            }
            
            // Informações adicionais
            append("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
            append("📅 ${response.timestamp ?: "Dados atualizados"}\n")
            
            // JSON completo (colapsado)
            append("\n📋 JSON Completo:\n")
            val gson = Gson()
            val formattedJson = gson.newBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(JsonParser.parseString(gson.toJson(response.data)))
            append(formattedJson)
        }
    }
    
    /**
     * Exibe mensagens de erro
     */
    private fun showError(message: String) {
        binding.textResult.text = buildString {
            append("❌ Erro ao Consultar Dados\n\n")
            append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n")
            append("$message\n\n")
            append("Verifique:\n")
            append("• Se a API intermediária está rodando\n")
            append("• Se a URL está configurada corretamente\n")
            append("• Se há conexão com a internet\n")
            append("• Os logs do servidor para mais detalhes")
        }
        binding.textResult.visibility = View.VISIBLE
        Toast.makeText(this, "Erro: $message", Toast.LENGTH_LONG).show()
    }
}

