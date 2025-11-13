package com.example.myapplicationgemini;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;




/* IMPORTANTE: Para usar o Firebase Firestore, você precisará adicionar as dependências
// no arquivo build.gradle (app) e seguir a documentação oficial para inicialização.
// Os imports abaixo são placeholders da lógica:
// import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseUser;
// import com.google.firebase.firestore.FirebaseFirestore;
// import com.google.firebase.firestore.CollectionReference;
// import com.google.firebase.firestore.DocumentReference;
*/

public class MainActivity extends AppCompatActivity {



    // Códigos para Intents
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final String TAG = "DetectorParasitas";

    // Componentes da UI
    private ImageView imagePreview;
    private MaterialButton btnTakePhoto, btnSelectGallery, btnStartAnalysis;
    private ProgressBar progressBar;
    private TextView tvResult;

    // Estado do Aplicativo
    private Uri selectedImageUri = null;

    // Simulação de Dados de Detecção
    private final String[] mockResults = {
            "Plasmodium falciparum (Malária) - Confiança: 98.5%",
            "Trypanosoma cruzi (Doença de Chagas) - Confiança: 75.2%",
            "Resultado Negativo - Confiança: 99.9%",
            "Leishmania spp. (Leishmaniose) - Confiança: 91.0%"
    };

   /* // --- Firebase Placeholder (Substitua por sua lógica real de inicialização) ---
    // private FirebaseAuth mAuth;
    // private FirebaseFirestore db;
    // private FirebaseUser currentUser;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout XML que criamos
        setContentView(R.layout.activity_main);

        // Inicialização dos componentes da UI
        imagePreview = findViewById(R.id.image_preview);
        btnTakePhoto = findViewById(R.id.btn_take_photo);
        btnSelectGallery = findViewById(R.id.btn_select_gallery);
        btnStartAnalysis = findViewById(R.id.btn_start_analysis);
        progressBar = findViewById(R.id.progress_bar);
        tvResult = findViewById(R.id.tv_result);
/*
        // --- Simulação de Inicialização Firebase (APENAS LÓGICA) ---
        // mAuth = FirebaseAuth.getInstance();
        // db = FirebaseFirestore.getInstance();
        // signInAnonimamente(); // Função a ser implementada para autenticação anônima ou de usuário
*/
        // --- Listeners dos Botões ---

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        btnSelectGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchSelectPictureIntent();
            }
        });

        btnStartAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnalysis();
            }
        });

        // Inicialmente, o botão de análise está desabilitado
        updateAnalysisButtonState();
    }

    /**
     * Inicia a Intent para tirar uma foto usando a câmera.
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Nenhum aplicativo de câmera encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Inicia a Intent para selecionar uma imagem da galeria.
     */
    private void dispatchSelectPictureIntent() {
        Intent selectPictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(selectPictureIntent, REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Caso a foto seja tirada, o bitmap está no 'extras'
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imagePreview.setImageBitmap(imageBitmap);
                // NOTA: Para salvar esta imagem no Firebase, você precisaria de um URI
                // e teria que salvar o Bitmap localmente primeiro.
                selectedImageUri = null; // URI é complexo para Bitmap de miniatura, trataremos como carregado.
                Toast.makeText(this, "Foto capturada e carregada.", Toast.LENGTH_SHORT).show();

            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                // Caso a imagem seja selecionada da galeria
                selectedImageUri = data.getData();
                imagePreview.setImageURI(selectedImageUri);
                Toast.makeText(this, "Imagem selecionada da galeria.", Toast.LENGTH_SHORT).show();
            }
            // Atualiza o estado após carregar a imagem
            updateAnalysisButtonState();
            tvResult.setVisibility(View.GONE);
        }
    }

    /**
     * Habilita/Desabilita o botão de análise.
     */
    private void updateAnalysisButtonState() {
        // Habilitado se o URI não for nulo OU se uma imagem (mesmo que miniatura) foi carregada.
        // Como o Bitmap da câmera não retorna um URI fácil, consideramos que a visualização é suficiente
        // para habilitar a análise no contexto do protótipo.
        boolean isImageLoaded = (selectedImageUri != null || imagePreview.getDrawable() != null);
        btnStartAnalysis.setEnabled(isImageLoaded);
    }


    /**
     * Simula a chamada de análise de ML.
     */
    private void startAnalysis() {
        // Oculta botões e mostra progresso
        btnStartAnalysis.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tvResult.setVisibility(View.GONE);

        // Desabilita botões de entrada
        btnTakePhoto.setEnabled(false);
        btnSelectGallery.setEnabled(false);

        // Simulação de delay de processamento (3 segundos)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Seleciona um resultado mock aleatório
                String detectionResult = mockResults[new Random().nextInt(mockResults.length)];

                // Exibe o resultado na UI
                tvResult.setText("Detecção Principal:\n" + detectionResult);
                tvResult.setVisibility(View.VISIBLE);

                // Oculta progresso e reabilita botões
                progressBar.setVisibility(View.GONE);
                btnStartAnalysis.setVisibility(View.VISIBLE);

                btnTakePhoto.setEnabled(true);
                btnSelectGallery.setEnabled(true);

                // Salva o resultado no Firestore (Lógica)
                saveResultToFirestore(detectionResult);
            }
        }, 3000); // 3 segundos de delay
    }

    /**
     * Simula o salvamento do resultado no Firestore.
     */
    private void saveResultToFirestore(String result) {
        // if (currentUser == null || db == null) {
        //     Log.e(TAG, "Firebase não inicializado ou usuário não autenticado.");
        //     Toast.makeText(this, "Erro: Banco de dados indisponível.", Toast.LENGTH_LONG).show();
        //     return;
        // }

        Map<String, Object> detection = new HashMap<>();
        detection.put("timestamp", new java.util.Date());
        detection.put("resultado_deteccao", result);
/*
        // Se a imagem tiver um URI, ele seria salvo aqui
        // detection.put("image_uri", selectedImageUri != null ? selectedImageUri.toString() : "Foto da Câmera");

        // --- LÓGICA DE SALVAMENTO (Descomente e adapte se tiver Firebase configurado) ---
        // CollectionReference detectionsRef = db.collection("deteccoes_parasitas");
        // detectionsRef.add(detection)
        //     .addOnSuccessListener(documentReference -> {
        //         Log.d(TAG, "Documento salvo com ID: " + documentReference.getId());
        //         Toast.makeText(MainActivity.this, "Resultado salvo no banco de dados!", Toast.LENGTH_SHORT).show();
        //     })
        //     .addOnFailureListener(e -> {
        //         Log.w(TAG, "Erro ao adicionar documento", e);
        //         Toast.makeText(MainActivity.this, "Falha ao salvar resultado.", Toast.LENGTH_SHORT).show();
        //     });
*/
        // Simulação de sucesso se não houver Firebase real:
        Log.i(TAG, "Resultado salvo (simulado): " + result);
        Toast.makeText(MainActivity.this, "Análise Concluída e Salva (Simulada)!", Toast.LENGTH_LONG).show();

        // Volta o botão de análise para Iniciar nova análise.
        btnStartAnalysis.setText("Analisar Novamente");

         /**
          * Função placeholder para autenticação anônima no Firebase.

         private void signInAnonimamente() {
             mAuth.signInAnonymously()
                 .addOnCompleteListener(this, task -> {
                     if (task.isSuccessful()) {
                         currentUser = mAuth.getCurrentUser();
                         Log.d(TAG, "Login anônimo bem-sucedido. UID: " + currentUser.getUid());
                     } else {
                         Log.w(TAG, "Falha no login anônimo", task.getException());
                         Toast.makeText(MainActivity.this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();
                     }
                 });
         }
        */

    }}
