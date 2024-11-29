package com.example.aplicativopim.model;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.aplicativopim.R;

public class Checkout extends AppCompatActivity {
    private LinearLayout secaoCartao, secaoPix, secaoEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Configuração da Toolbar e seta de voltar
        setupToolbar();

        // Inicializando o Spinner
        // Views
        Spinner spinnerMetodoPagamento = findViewById(R.id.spinner_metodo_pagamento);

        // Inicializando as seções de pagamento
        secaoCartao = findViewById(R.id.layout_cartao);
        secaoPix = findViewById(R.id.layout_pix);
        secaoEntrega = findViewById(R.id.layout_pagamento_entrega);

        // Inicialmente, ocultar todas as seções
        esconderTodasSecoes();

        // Array com os métodos de pagamento
        String[] metodosPagamento = {
                "Selecione o método de pagamento", // Hint
                "PIX",
                "Cartão",
                "Pagamento na entrega"
        };

        // Criando o ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, metodosPagamento);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Definindo o adaptador no Spinner
        spinnerMetodoPagamento.setAdapter(adapter);

        // Listener para a seleção do Spinner
        spinnerMetodoPagamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String metodoSelecionado = parent.getItemAtPosition(position).toString();

                // Exibir a seção correta de acordo com o método de pagamento selecionado
                switch (metodoSelecionado) {
                    case "PIX":
                        esconderTodasSecoes();
                        secaoPix.setVisibility(View.VISIBLE);
                        break;

                    case "Cartão":
                        esconderTodasSecoes();
                        secaoCartao.setVisibility(View.VISIBLE);
                        break;

                    case "Pagamento na entrega":
                        esconderTodasSecoes();
                        secaoEntrega.setVisibility(View.VISIBLE);
                        break;

                    default:
                        esconderTodasSecoes();  // Caso o usuário escolha "Selecione o método de pagamento"
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Não fazer nada
            }
        });
    }

    /** Configura a Toolbar e os itens do menu. */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.simplestoolbarCheckout);
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

    // Função para ocultar todas as seções
    private void esconderTodasSecoes() {
        secaoCartao.setVisibility(View.GONE);
        secaoPix.setVisibility(View.GONE);
        secaoEntrega.setVisibility(View.GONE);
    }
}