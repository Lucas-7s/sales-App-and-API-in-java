package com.example.aplicativopim.model.Carrinho;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicativopim.R;
import com.example.aplicativopim.model.Checkout;
import com.example.aplicativopim.model.DetalheProduto.ListaMostrarItens;

public class Carrinho extends AppCompatActivity {
    private RecyclerView listaCompraRclV;
    private TextView subtotalValorTxtV, subtotalValor2TxtV, freteTaxaValorTxtV, descontoValorTxtV, CupomEdt, cupomTxtV, totalValorTxtV;
    private Button aplicarCupomBtn;
    private CarrinhoAdapter carrinhoAdapter;
    private double valorDesconto = 0;
    private String cupom = "";
    private TextView finalizarCompraBtn;
    private TextView carrinhoVazioTxtV;
    private TextView tituloCompraTxtV;
    private TextView tituloCarrinhoTxtV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        // Configuração da Toolbar e seta de voltar
        setupToolbar();

        // Inicializa os elementos da interface
        initViews();

        // Configuração do RecyclerView e Adapter do carrinho
        setupRecyclerView();

        // Configura o clique do botão aplicar cupom
        configurarBotaoAplicarCupom();

        // Atualiza os valores de subtotal, frete e total
        atualizarTotais();

        finalizarCompraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inicie a nova atividade
                startActivity(new Intent(Carrinho.this, Checkout.class));
            }
        });
    }

    /** Inicializa os componentes de interface. */
    private void initViews() {
        listaCompraRclV = findViewById(R.id.listaCompraRclV);
        subtotalValorTxtV = findViewById(R.id.subtotalValorTxtV);
        subtotalValor2TxtV = findViewById(R.id.subtotalValor2TxtV);
        freteTaxaValorTxtV = findViewById(R.id.freteTaxaValorTxtV);
        CupomEdt = findViewById(R.id.CupomEdt);
        cupomTxtV = findViewById(R.id.cupomTxtV);
        descontoValorTxtV = findViewById(R.id.descontoValorTxtV);
        totalValorTxtV = findViewById(R.id.totalValorTxtV);
        aplicarCupomBtn = findViewById(R.id.aplicarCupomBtn);
        carrinhoVazioTxtV = findViewById(R.id.carrinhoVazioTxtV);
        tituloCompraTxtV = findViewById(R.id.tituloCompraTxtV);
        tituloCarrinhoTxtV = findViewById(R.id.tituloCarrinhoTxtV);
        finalizarCompraBtn = findViewById(R.id.finalizarCompraBtn);
    }

    /** Configura o RecyclerView e o Adapter do carrinho. */
    private void setupRecyclerView() {
        // Configuração do Adapter do carrinho
        carrinhoAdapter = new CarrinhoAdapter(
                CarrinhoSingleton.getInstance().getItensCarrinho(),
                this,
                subtotalValorTxtV,
                subtotalValor2TxtV,
                freteTaxaValorTxtV,
                descontoValorTxtV,
                CupomEdt,
                cupomTxtV,
                totalValorTxtV,
                aplicarCupomBtn,
                carrinhoVazioTxtV,
                tituloCompraTxtV,
                tituloCarrinhoTxtV,
                finalizarCompraBtn
        );

        // Configuração do RecyclerView
        listaCompraRclV.setLayoutManager(new LinearLayoutManager(this));
        listaCompraRclV.setAdapter(carrinhoAdapter);
    }

    /** Configura o botão de aplicar cupom. */
    private void configurarBotaoAplicarCupom() {
        aplicarCupomBtn.setOnClickListener(v -> {
            String cupomDigitado = CupomEdt.getText().toString().trim();

            if (cupomDigitado.equalsIgnoreCase("10OFF")) {
                valorDesconto = 10.00;
                cupom = " - cupom: 10OFF";
                cupomTxtV.setText(String.format("Cupom: 10OFF"));
                Toast.makeText(this, "Cupom aplicado com sucesso!", Toast.LENGTH_SHORT).show();
            } else if (cupomDigitado.equalsIgnoreCase("Presente")) {
                valorDesconto = 9.00;
                cupom = " - cupom: Presente";
                cupomTxtV.setText(String.format("Cupom: Presente"));
                Toast.makeText(this, "Cupom aplicado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                valorDesconto = 0;
                cupom = "";
                cupomTxtV.setText(String.format("Cupom:"));
                Toast.makeText(this, "Cupom inválido ou não encontrado.", Toast.LENGTH_SHORT).show();
            }

            atualizarTotais(); // Atualiza os valores do carrinho
        });
    }

    /**
     * Atualiza os valores de subtotal, frete e total.
     */
    private void atualizarTotais() {
        double subtotal = 0;
        double taxaFrete = 10.00;

        // Verifica se o carrinho está vazio
        if (CarrinhoSingleton.getInstance().getItensCarrinho().isEmpty()) {
            // Exibe a mensagem de carrinho vazio e oculta as informações do carrinho
            carrinhoVazioTxtV.setVisibility(View.VISIBLE);
            tituloCarrinhoTxtV.setVisibility(View.GONE);
            tituloCompraTxtV.setVisibility(View.GONE);
            listaCompraRclV.setVisibility(View.GONE);
            subtotalValorTxtV.setVisibility(View.GONE);
            subtotalValor2TxtV.setVisibility(View.GONE);
            freteTaxaValorTxtV.setVisibility(View.GONE);
            descontoValorTxtV.setVisibility(View.GONE);
            totalValorTxtV.setVisibility(View.GONE);
            finalizarCompraBtn.setVisibility(View.GONE);
            aplicarCupomBtn.setVisibility(View.GONE);
            CupomEdt.setVisibility(View.GONE);
            cupomTxtV.setVisibility(View.GONE);
        } else {
            // Calcula o subtotal com base nos itens no carrinho
            for (ListaMostrarItens item : CarrinhoSingleton.getInstance().getItensCarrinho()) {
                subtotal += Double.parseDouble(item.getPreco().replace("R$ ", "")) * item.getQuantidade();
            }

            double total = subtotal + taxaFrete - valorDesconto;

            // Atualiza os TextViews com os valores calculados
            subtotalValorTxtV.setText(String.format("%.2f", subtotal));
            subtotalValor2TxtV.setText(String.format("%.2f", subtotal));
            descontoValorTxtV.setText(String.format("%.2f", valorDesconto));
            freteTaxaValorTxtV.setText(String.format("%.2f", taxaFrete));
            totalValorTxtV.setText(String.format("%.2f", total));

            // Oculta a mensagem de carrinho vazio e exibe as informações do carrinho
            carrinhoVazioTxtV.setVisibility(View.GONE);
            tituloCarrinhoTxtV.setVisibility(View.VISIBLE);
            tituloCompraTxtV.setVisibility(View.VISIBLE);
            listaCompraRclV.setVisibility(View.VISIBLE);
            subtotalValorTxtV.setVisibility(View.VISIBLE);
            subtotalValor2TxtV.setVisibility(View.VISIBLE);
            freteTaxaValorTxtV.setVisibility(View.VISIBLE);
            descontoValorTxtV.setVisibility(View.VISIBLE);
            totalValorTxtV.setVisibility(View.VISIBLE);
            finalizarCompraBtn.setVisibility(View.VISIBLE);
            aplicarCupomBtn.setVisibility(View.VISIBLE);
            CupomEdt.setVisibility(View.VISIBLE);
            cupomTxtV.setVisibility(View.VISIBLE);
        }
    }

    /** Configura a Toolbar e os itens do menu. */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.simplestoolbarCarrinho);
        setSupportActionBar(toolbar);

        // Habilita a seta de voltar na toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back); // Cor branca da seta
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        // Atualiza os dados do carrinho sempre que a tela é retomada
        atualizarTotais();
    }
}