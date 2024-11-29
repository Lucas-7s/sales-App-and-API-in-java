package com.example.aplicativopim.model;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.aplicativopim.R;

public class FaleConosco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fale_conosco);

        // Address Click Listener to open in Google Maps
        GridLayout addressLbl = findViewById(R.id.addressLbl);
        addressLbl.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=R. Antônio Macedo, 505 - Parque São Jorge, São Paulo - SP, 03087-010");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        // Configuração da Toolbar e seta de voltar
        setupToolbar();

        // Phone Button
        LinearLayout phoneButton = findViewById(R.id.phoneButton);
        phoneButton.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:11912345678"));
            startActivity(callIntent);
        });

        // Email Button
        LinearLayout emailButton = findViewById(R.id.emailButton);
        emailButton.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:microgreen@microgreen.com"));
            startActivity(emailIntent);
        });

        // WhatsApp Button
        LinearLayout whatsappButton = findViewById(R.id.whatsappButton);
        whatsappButton.setOnClickListener(v -> {
            String url = "https://wa.me/5511943035000";
            Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
            whatsappIntent.setData(Uri.parse(url));
            startActivity(whatsappIntent);
        });

        // Website Button
        LinearLayout websiteButton = findViewById(R.id.websiteButton);
        websiteButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.microgreen.com"));
            startActivity(browserIntent);
        });
    }

    /** Configura a Toolbar e os itens do menu. */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.simplestoolbarContato);
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