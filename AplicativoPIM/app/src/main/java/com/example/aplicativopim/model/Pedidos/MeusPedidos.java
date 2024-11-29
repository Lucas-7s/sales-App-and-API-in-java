package com.example.aplicativopim.model.Pedidos;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aplicativopim.model.DetalhesPedidos;
import com.example.aplicativopim.model.Pedido;
import com.example.aplicativopim.model.PedidoService;
import com.example.aplicativopim.R;
import com.example.aplicativopim.model.RetrofitConfig;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeusPedidos extends AppCompatActivity implements PedidoAdapter.OnPedidoClickListener {

    private RecyclerView recyclerView;
    private TextView emptyStateText;
    private List<Pedido> pedidosOriginais;
    private List<Pedido> pedidos;
    private PedidoAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private ChipGroup chipGroupFiltros;
    private TextInputEditText editTextBusca;
    private String filtroAtual = "todos";
    private String termoBuscaAtual = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_pedidos);

        setupToolbar();
        inicializarViews();
        configurarRecyclerView();
        configurarFiltros();
        configurarBusca();
        configurarPullToRefresh();
        criarCanalNotificacao();
        carregarPedidos();
    }

    private void inicializarViews() {
        recyclerView = findViewById(R.id.pedidosRecyclerView);
        emptyStateText = findViewById(R.id.emptyStateText);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        chipGroupFiltros = findViewById(R.id.chipGroupFiltros);
        editTextBusca = findViewById(R.id.editTextBusca);
    }

    private void configurarRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pedidos = new ArrayList<>();
        pedidosOriginais = new ArrayList<>();
        adapter = new PedidoAdapter(this, pedidos, this);
        recyclerView.setAdapter(adapter);
    }

    private void configurarFiltros() {
        String[] filtros = {"Todos", "Realizado", "Em Trânsito", "Entregue", "Cancelado"};

        for (String filtro : filtros) {
            Chip chip = new Chip(this);
            chip.setText(filtro);
            chip.setCheckable(true);
            chip.setClickable(true);

            if (filtro.equals("Todos")) {
                chip.setChecked(true);
            }

            chipGroupFiltros.addView(chip);
        }

        chipGroupFiltros.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.size() > 0) {
                Chip chip = findViewById(checkedIds.get(0));
                filtroAtual = chip.getText().toString().toLowerCase();
                aplicarFiltrosEBusca();
            }
        });
    }

    private void configurarBusca() {
        editTextBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                termoBuscaAtual = s.toString();
                aplicarFiltrosEBusca();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void configurarPullToRefresh() {
        swipeRefresh.setOnRefreshListener(this::carregarPedidos);
    }

    private void carregarPedidos() {
        PedidoService service = RetrofitConfig.getClient().create(PedidoService.class);
        Call<List<Pedido>> call = service.getPedidos();

        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                swipeRefresh.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {
                    pedidosOriginais.clear();
                    pedidosOriginais.addAll(response.body());
                    aplicarFiltrosEBusca();
                } else {
                    Toast.makeText(MeusPedidos.this,
                            "Erro ao carregar pedidos: " + response.code(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(MeusPedidos.this,
                        "Erro de conexão: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void aplicarFiltrosEBusca() {
        List<Pedido> pedidosFiltrados = pedidosOriginais;

        // Aplicar filtro de status
        if (!filtroAtual.equals("todos")) {
            pedidosFiltrados = pedidosFiltrados.stream()
                    .filter(pedido -> pedido.getStatus().toLowerCase().equals(filtroAtual))
                    .collect(Collectors.toList());
        }

        // Aplicar busca por número do pedido
        if (!termoBuscaAtual.isEmpty()) {
            pedidosFiltrados = pedidosFiltrados.stream()
                    .filter(pedido -> pedido.getId().toString().contains(termoBuscaAtual))
                    .collect(Collectors.toList());
        }

        pedidos.clear();
        pedidos.addAll(pedidosFiltrados);
        adapter.notifyDataSetChanged();
        atualizarVisibilidade();
    }

    private void atualizarVisibilidade() {
        if (pedidos.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyStateText.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyStateText.setVisibility(View.GONE);
        }
    }

    private void criarCanalNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "pedidos_channel",
                    "Atualizações de Pedidos",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("Notificações sobre atualizações de status dos pedidos");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onPedidoClick(Pedido pedido) {
        Intent intent = new Intent(MeusPedidos.this, DetalhesPedidos.class);

        intent.putExtra("id", pedido.getId());
        intent.putExtra("valor_total", pedido.getValorTotal().toString());
        intent.putExtra("status", pedido.getStatus());
        intent.putExtra("data_compra", pedido.getDataCompra() != null ? pedido.getDataCompra().getTime() : 0);
        intent.putExtra("data_em_transito", pedido.getDataCriacao() != null ? pedido.getDataCriacao().getTime() : 0); // Usar dataCriacao como data em trânsito
        intent.putExtra("data_criacao", pedido.getDataCompra() != null ? pedido.getDataCompra().getTime() : 0);
        intent.putExtra("metodo_pagamento", pedido.getMetodoPagamento());
        intent.putExtra("local_pagamento", pedido.getLocalPagamento());
        intent.putExtra("valor_pago", pedido.getValorPago().toString());
        intent.putExtra("quantidade_parcelas", pedido.getQuantidadeParcelas());
        intent.putExtra("valor_parcelas", pedido.getValorParcelas().toString());

        startActivity(intent);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.simplestoolbarMeusPedidos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simples, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}