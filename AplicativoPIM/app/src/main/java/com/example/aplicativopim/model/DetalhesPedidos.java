package com.example.aplicativopim.model;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.aplicativopim.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.math.BigDecimal;

public class DetalhesPedidos extends AppCompatActivity {
    private View circleRealizado, circleEmTransito, circleEntregue, circleCancelado;
    private View progressLine1, progressLine2, progressLine3;
    private TextView textPedidoRealizado, dataPedidoRealizado;
    private TextView textEmTransito, dataEmTransito;
    private TextView textPedidoEntregue, dataPedidoEntregue;
    private TextView textPedidoCancelado, dataPedidoCancelado;
    private TextView textNumeroPedido, textStatus, textValorTotal;
    private LinearLayout layoutEmTransito;
    private LinearLayout layoutEntregue;
    private LinearLayout layoutCancelado;
    private ImageButton btnVoltar;
    private Button btnAjuda;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pedidos);

        inicializarViews();
        configurarBotoes();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String status = extras.getString("status");
            Long id = extras.getLong("id");
            BigDecimal valorTotal = new BigDecimal(extras.getString("valor_total"));

            long dataRealizadoTimestamp = extras.getLong("data_compra", 0);
            long dataEmTransitoTimestamp = extras.getLong("data_em_transito", 0);
            long dataEntregueTimestamp = extras.getLong("data_entregue", 0);
            long dataCanceladoTimestamp = extras.getLong("data_cancelado", 0);

            atualizarInformacoesPedido(id, status, valorTotal, dataRealizadoTimestamp);
            atualizarStatus(status);
            atualizarTodasDatas(dataRealizadoTimestamp, dataEmTransitoTimestamp, dataEntregueTimestamp, dataCanceladoTimestamp);
        }
    }

    private void inicializarViews() {
        // Círculos de status
        circleRealizado = findViewById(R.id.circleRealizado);
        circleEmTransito = findViewById(R.id.circleEmTransito);
        circleEntregue = findViewById(R.id.circleEntregue);
        circleCancelado = findViewById(R.id.circleCancelado);

        // Linhas de progresso
        progressLine1 = findViewById(R.id.progressLine1);
        progressLine2 = findViewById(R.id.progressLine2);
        progressLine3 = findViewById(R.id.progressLine3);

        // Textos de status e datas
        textPedidoRealizado = findViewById(R.id.text_pedido_realizado);
        dataPedidoRealizado = findViewById(R.id.txtDataRealizado);
        textEmTransito = findViewById(R.id.text_em_transito);
        dataEmTransito = findViewById(R.id.txtDataEmTransito);
        textPedidoEntregue = findViewById(R.id.text_pedido_entregue);
        dataPedidoEntregue = findViewById(R.id.txtDataEntregue);
        textPedidoCancelado = findViewById(R.id.text_pedido_cancelado);
        dataPedidoCancelado = findViewById(R.id.txtDataCancelado);

        // Informações do pedido
        textNumeroPedido = findViewById(R.id.txtNumeroPedido);
        textStatus = findViewById(R.id.txtStatus);
        textValorTotal = findViewById(R.id.txtValorTotal);

        // Layouts
        layoutEmTransito = findViewById(R.id.layoutEmTransito);
        layoutEntregue = findViewById(R.id.layoutEntregue);
        layoutCancelado = findViewById(R.id.layoutCancelado);

        // Botões
        btnVoltar = findViewById(R.id.btnVoltar);
        btnAjuda = findViewById(R.id.btnAjuda);

        // Ocultar layouts por padrão
        layoutEmTransito.setVisibility(View.GONE);
        layoutEntregue.setVisibility(View.GONE);
        layoutCancelado.setVisibility(View.GONE);
        progressLine1.setVisibility(View.GONE);
        progressLine2.setVisibility(View.GONE);
        progressLine3.setVisibility(View.GONE);
    }

    private void configurarBotoes() {
        btnVoltar.setOnClickListener(v -> finish());
        btnAjuda.setOnClickListener(v -> mostrarAjuda());
    }

    private void mostrarAjuda() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajuda")
                .setMessage("Aqui você pode acompanhar o status do seu pedido.")
                .setPositiveButton("OK", null)
                .show();
    }

    private void atualizarTodasDatas(long dataRealizadoTimestamp, long dataEmTransitoTimestamp,
                                     long dataEntregueTimestamp, long dataCanceladoTimestamp) {
        // Data Realizado sempre será mostrada
        if (dataRealizadoTimestamp > 0) {
            dataPedidoRealizado.setText(dateFormat.format(new Date(dataRealizadoTimestamp)));
        } else {
            dataPedidoRealizado.setText("--/--/----");
        }

        if (dataCanceladoTimestamp > 0) {
            // Se tem data de cancelamento, mostra apenas ela
            dataPedidoCancelado.setText(dateFormat.format(new Date(dataCanceladoTimestamp)));
            dataEmTransito.setText("--/--/----");
            dataPedidoEntregue.setText("--/--/----");
        } else {
            // Se não tem cancelamento, mostra as outras datas normalmente
            if (dataEmTransitoTimestamp > 0) {
                dataEmTransito.setText(dateFormat.format(new Date(dataEmTransitoTimestamp)));
            } else {
                dataEmTransito.setText("--/--/----");
            }

            if (dataEntregueTimestamp > 0) {
                dataPedidoEntregue.setText(dateFormat.format(new Date(dataEntregueTimestamp)));
            } else {
                dataPedidoEntregue.setText("--/--/----");
            }

            dataPedidoCancelado.setText("--/--/----");
        }
    }

    private void atualizarInformacoesPedido(Long id, String status, BigDecimal valorTotal, long dataRealizadoTimestamp) {
        textNumeroPedido.setText("Número do Pedido: " + id);
        textStatus.setText("Data do Pedido: " + dateFormat.format(new Date(dataRealizadoTimestamp)));
        textStatus.setTextColor(Color.BLACK); // Adicionando esta linha
        textValorTotal.setText("Valor Total:\nR$ " + String.format("%.2f", valorTotal));
        }

    private String formatarStatus(String status) {
        switch (status.toLowerCase()) {
            case "realizado":
                return "Pedido Realizado";
            case "em_transito":
                return "Em Trânsito";
            case "entregue":
                return "Pedido Entregue";
            case "cancelado":
                return "Pedido Cancelado";
            default:
                return status;
        }
    }

    private void atualizarStatus(String status) {
        resetarViews();

        // Sempre mostra o Realizado
        circleRealizado.setVisibility(View.VISIBLE);
        textPedidoRealizado.setVisibility(View.VISIBLE);
        dataPedidoRealizado.setVisibility(View.VISIBLE);

        switch (status.toLowerCase()) {
            case "realizado":
                // Mostra apenas Realizado
                layoutEmTransito.setVisibility(View.GONE);
                layoutEntregue.setVisibility(View.GONE);
                layoutCancelado.setVisibility(View.GONE);
                atualizarParaRealizado();
                break;

            case "em_transito":
                // Mostra Realizado e Em Trânsito
                layoutEmTransito.setVisibility(View.VISIBLE);
                layoutEntregue.setVisibility(View.GONE);
                layoutCancelado.setVisibility(View.GONE);
                progressLine1.setVisibility(View.VISIBLE);
                atualizarParaEmTransito();
                break;

            case "entregue":
                // Mostra todos os status normais
                layoutEmTransito.setVisibility(View.VISIBLE);
                layoutEntregue.setVisibility(View.VISIBLE);
                layoutCancelado.setVisibility(View.GONE);
                progressLine1.setVisibility(View.VISIBLE);
                progressLine2.setVisibility(View.VISIBLE);
                atualizarParaEntregue();
                break;

            case "cancelado":
                // Mostra apenas Realizado e Cancelado
                layoutEmTransito.setVisibility(View.GONE);
                layoutEntregue.setVisibility(View.GONE);
                layoutCancelado.setVisibility(View.VISIBLE);
                progressLine1.setVisibility(View.VISIBLE);
                atualizarParaCancelado();
                break;
        }
    }

    private void resetarViews() {
        // Resetar cores e estilos
        circleRealizado.setBackgroundResource(R.drawable.circle_grey);
        circleEmTransito.setBackgroundResource(R.drawable.circle_grey);
        circleEntregue.setBackgroundResource(R.drawable.circle_grey);
        circleCancelado.setBackgroundResource(R.drawable.circle_grey);

        // Alterando de Color.GRAY para Color.BLACK
        textPedidoRealizado.setTextColor(Color.BLACK);
        textEmTransito.setTextColor(Color.BLACK);
        textPedidoEntregue.setTextColor(Color.BLACK);
        textPedidoCancelado.setTextColor(Color.BLACK);

        textPedidoRealizado.setTypeface(null);
        textEmTransito.setTypeface(null);
        textPedidoEntregue.setTypeface(null);
        textPedidoCancelado.setTypeface(null);

        // Resetar visibilidade
        layoutEmTransito.setVisibility(View.GONE);
        layoutEntregue.setVisibility(View.GONE);
        layoutCancelado.setVisibility(View.GONE);
        progressLine1.setVisibility(View.GONE);
        progressLine2.setVisibility(View.GONE);
        progressLine3.setVisibility(View.GONE);
    }

    private void atualizarParaRealizado() {
        // Começar com tudo invisível
        circleRealizado.setAlpha(0f);
        textPedidoRealizado.setAlpha(0f);
        dataPedidoRealizado.setAlpha(0f);

        // Animar o aparecimento do primeiro status
        circleRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null);

        textPedidoRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(300)
                .setListener(null);

        dataPedidoRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(500)
                .setListener(null);

        // Configurar cores e estilos
        circleRealizado.setBackgroundResource(R.drawable.circle_blue);
        textPedidoRealizado.setTextColor(Color.BLACK); // Mudado para preto
        textPedidoRealizado.setTypeface(null, android.graphics.Typeface.BOLD);
    }


    private void atualizarParaEmTransito() {
        // Primeiro status
        circleRealizado.setAlpha(0f);
        textPedidoRealizado.setAlpha(0f);
        dataPedidoRealizado.setAlpha(0f);

        // Segundo status e linha
        progressLine1.setVisibility(View.VISIBLE);
        progressLine1.setAlpha(0f);
        layoutEmTransito.setVisibility(View.VISIBLE);
        layoutEmTransito.setAlpha(0f);

        // Animar primeiro status
        circleRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null);

        textPedidoRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(300)
                .setListener(null);

        dataPedidoRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Após primeiro status, animar a linha
                        progressLine1.animate()
                                .alpha(1f)
                                .setDuration(500)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        // Após a linha, animar o segundo status
                                        layoutEmTransito.animate()
                                                .alpha(1f)
                                                .setDuration(500)
                                                .setListener(null);
                                    }
                                });
                    }
                });

        // Configurar cores e estilos
        circleRealizado.setBackgroundResource(R.drawable.circle_blue);
        textPedidoRealizado.setTextColor(Color.BLACK); // Mudado para preto

        progressLine1.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));

        circleEmTransito.setBackgroundResource(R.drawable.circle_orange);
        textEmTransito.setTextColor(Color.BLACK); // Mudado para preto
        textEmTransito.setTypeface(null, android.graphics.Typeface.BOLD);
    }

    private void atualizarParaEntregue() {
        // Configurar visibilidade inicial
        circleRealizado.setAlpha(0f);
        textPedidoRealizado.setAlpha(0f);
        dataPedidoRealizado.setAlpha(0f);

        progressLine1.setVisibility(View.VISIBLE);
        progressLine1.setAlpha(0f);
        layoutEmTransito.setVisibility(View.VISIBLE);
        layoutEmTransito.setAlpha(0f);

        progressLine2.setVisibility(View.VISIBLE);
        progressLine2.setAlpha(0f);
        layoutEntregue.setVisibility(View.VISIBLE);
        layoutEntregue.setAlpha(0f);

        // Animar primeiro status
        circleRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null);

        textPedidoRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(300)
                .setListener(null);

        dataPedidoRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Animar primeira linha
                        progressLine1.animate()
                                .alpha(1f)
                                .setDuration(500)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        // Animar segundo status
                                        layoutEmTransito.animate()
                                                .alpha(1f)
                                                .setDuration(500)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        // Animar segunda linha
                                                        progressLine2.animate()
                                                                .alpha(1f)
                                                                .setDuration(500)
                                                                .setListener(new AnimatorListenerAdapter() {
                                                                    @Override
                                                                    public void onAnimationEnd(Animator animation) {
                                                                        // Animar terceiro status
                                                                        layoutEntregue.animate()
                                                                                .alpha(1f)
                                                                                .setDuration(500)
                                                                                .setListener(null);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });

        // Configurar cores e estilos
        circleRealizado.setBackgroundResource(R.drawable.circle_blue);
        circleEmTransito.setBackgroundResource(R.drawable.circle_orange);
        circleEntregue.setBackgroundResource(R.drawable.circle_green);

        textPedidoRealizado.setTextColor(Color.BLACK); // Mudado para preto
        textEmTransito.setTextColor(Color.BLACK); // Mudado para preto
        textPedidoEntregue.setTextColor(Color.BLACK); // Mudado para preto
        textPedidoEntregue.setTypeface(null, android.graphics.Typeface.BOLD);

        progressLine1.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
        progressLine2.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
    }

    private void atualizarParaCancelado() {
        // Começar com tudo invisível
        circleRealizado.setAlpha(0f);
        textPedidoRealizado.setAlpha(0f);
        dataPedidoRealizado.setAlpha(0f);

        progressLine1.setVisibility(View.VISIBLE);
        progressLine1.setAlpha(0f);
        layoutCancelado.setVisibility(View.VISIBLE);
        layoutCancelado.setAlpha(0f);

        // Animar primeiro status
        circleRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null);

        textPedidoRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(300)
                .setListener(null);

        dataPedidoRealizado.animate()
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Após primeiro status, animar a linha
                        progressLine1.animate()
                                .alpha(1f)
                                .setDuration(500)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        // Após a linha, animar o status cancelado
                                        layoutCancelado.animate()
                                                .alpha(1f)
                                                .setDuration(500)
                                                .setListener(null);
                                    }
                                });
                    }
                });

        // Configurar cores e estilos
        circleRealizado.setBackgroundResource(R.drawable.circle_blue);
        circleCancelado.setBackgroundResource(R.drawable.circle_red);

        textPedidoRealizado.setTextColor(Color.BLACK); // Mudado para preto
        textPedidoCancelado.setTextColor(Color.BLACK); // Mudado para preto
        textPedidoCancelado.setTypeface(null, android.graphics.Typeface.BOLD);

        progressLine1.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
    }
}