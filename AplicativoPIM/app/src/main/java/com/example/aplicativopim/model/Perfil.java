package com.example.aplicativopim.model;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplicativopim.R;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);

        // Configuração das margens da view principal com as insets do sistema
        configureWindowInsets();

        // Configuração da Toolbar e seta de voltar
        setupToolbar();
    }

    /**
     * Configura as insets da janela para a view principal.
     */
    private void configureWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /** Configura a Toolbar e os itens do menu. */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.simplestoolbarPerfil);
        setSupportActionBar(toolbar);

        // Habilita a seta de voltar na toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    /** Cria o menu de opções. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simples, menu);
        return true;
    }

    /** Define o comportamento dos itens do menu de opções. */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {  // Ação para o botão de voltar
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}