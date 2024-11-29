package com.example.aplicativopim.model.Pedidos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicativopim.model.Pedido;
import com.example.aplicativopim.R;
import com.google.android.material.button.MaterialButton;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.text.DecimalFormat;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private Context context;
    private List<Pedido> pedidos;
    private OnPedidoClickListener listener;
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;

    public interface OnPedidoClickListener {
        void onPedidoClick(Pedido pedido);
    }

    public PedidoAdapter(Context context, List<Pedido> pedidos, OnPedidoClickListener listener) {
        this.context = context;
        this.pedidos = pedidos;
        this.listener = listener;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        this.decimalFormat = new DecimalFormat("R$ #,##0.00");
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);

        // Número do pedido
        holder.numeroPedido.setText("Pedido #" + pedido.getId());

        // Valor total formatado
        String valorFormatado = decimalFormat.format(pedido.getValorTotal());
        holder.valorTotal.setText(valorFormatado);

        // Status com cor e formato
        configureStatus(holder, pedido.getStatus());

        // Data da compra
        if (pedido.getDataCompra() != null) {
            holder.dataPedido.setText(dateFormat.format(pedido.getDataCompra()));
        } else {
            holder.dataPedido.setText("Data não disponível");
        }

        // Click listener para o card inteiro
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPedidoClick(pedido);
            }
        });

        // Click listener para o botão de detalhes
        holder.btnVerDetalhes.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPedidoClick(pedido);
            }
        });
    }

    private void configureStatus(PedidoViewHolder holder, String status) {
        GradientDrawable statusBackground = new GradientDrawable();
        statusBackground.setCornerRadius(16);

        int backgroundColor;
        String statusText;

        if (status == null) {
            status = "";
        }

        switch (status.toLowerCase()) {
            case "realizado":
                backgroundColor = ContextCompat.getColor(context, R.color.status_novo);
                statusText = "Realizado";
                break;
            case "em_transito":
                backgroundColor = ContextCompat.getColor(context, R.color.status_em_transito);
                statusText = "Em Trânsito";
                break;
            case "entregue":
                backgroundColor = ContextCompat.getColor(context, R.color.status_entregue);
                statusText = "Entregue";
                break;
            case "cancelado":
                backgroundColor = ContextCompat.getColor(context, R.color.status_cancelado);
                statusText = "Cancelado";
                break;
            default:
                backgroundColor = ContextCompat.getColor(context, R.color.status_novo);
                statusText = status;
                break;
        }

        // Configurar o texto e o fundo do status
        statusBackground.setColor(backgroundColor);
        holder.status.setBackground(statusBackground);
        holder.status.setText(statusText);
        holder.status.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return pedidos != null ? pedidos.size() : 0;
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView numeroPedido;
        TextView valorTotal;
        TextView status;
        TextView dataPedido;
        MaterialButton btnVerDetalhes;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            numeroPedido = itemView.findViewById(R.id.txtNumeroPedido);
            valorTotal = itemView.findViewById(R.id.txtValorTotal);
            status = itemView.findViewById(R.id.txtStatus);
            dataPedido = itemView.findViewById(R.id.txtDataPedido);
            btnVerDetalhes = itemView.findViewById(R.id.btnVerDetalhes);
        }
    }

    public void atualizarPedidos(List<Pedido> novosPedidos) {
        this.pedidos.clear();
        this.pedidos.addAll(novosPedidos);
        notifyDataSetChanged();
    }
}