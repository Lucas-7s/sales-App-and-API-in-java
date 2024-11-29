package com.example.aplicativopim.model.Carrinho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicativopim.databinding.ActivityCarrinhoItemBinding;
import com.example.aplicativopim.model.DetalheProduto.ListaMostrarItens;

import java.util.ArrayList;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder> {

    private final ArrayList<ListaMostrarItens> itensCarrinhoArrayList;
    private final Context context;
    private final TextView subtotalValorTxtV;
    private final TextView subtotalValor2TxtV;
    private final TextView freteTaxaTxtV;
    private final double taxaFrete = 10.00; // Exemplo de taxa de frete fixa
    private final TextView descontoTxtV;
    private final TextView CupomEdt;
    private final TextView cupomTxtV;
    private final TextView totalValorTxtV;
    private final Button aplicarCupomBtn;
    private double valorDesconto = 0;
    private final TextView carrinhoVazioTxtV;
    private final TextView tituloCompraTxtV;
    private final TextView tituloCarrinhoTxtV;
    private final TextView finalizarCompraBtn;


    // Construtor do Adapter
    public CarrinhoAdapter(ArrayList<ListaMostrarItens> itensCarrinho, Context context, TextView subtotalValorTxtV,
                           TextView subtotalValor2TxtV, TextView freteTaxaTxtV, TextView descontoTxtV,
                           TextView CupomEdt, TextView cupomTxtV, TextView totalValorLbl,
                           Button aplicarCupomBtn, TextView carrinhoVazioLbl, TextView tituloCarrinhoLbl, TextView tituloCompraLbl, TextView finalizarCompraBtn) {
        this.itensCarrinhoArrayList = itensCarrinho;
        this.context = context;
        this.subtotalValorTxtV = subtotalValorTxtV;
        this.subtotalValor2TxtV = subtotalValor2TxtV;
        this.freteTaxaTxtV = freteTaxaTxtV;
        this.descontoTxtV = descontoTxtV;
        this.CupomEdt = CupomEdt;
        this.cupomTxtV = cupomTxtV;
        this.totalValorTxtV = totalValorLbl;
        this.aplicarCupomBtn = aplicarCupomBtn;
        this.carrinhoVazioTxtV = carrinhoVazioLbl;
        this.tituloCompraTxtV = tituloCompraLbl;
        this.tituloCarrinhoTxtV = tituloCarrinhoLbl;
        this.finalizarCompraBtn = finalizarCompraBtn;

        configurarBotaoAplicarCupom();
        atualizarTotais();
    }

    private void configurarBotaoAplicarCupom() {
        aplicarCupomBtn.setOnClickListener(v -> {
            String cupomDigitado = CupomEdt.getText().toString().trim();

            if (cupomDigitado.equalsIgnoreCase("10OFF")) {
                valorDesconto = 10.00;
                Toast.makeText(context, "Cupom aplicado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                valorDesconto = 0;
                Toast.makeText(context, "Cupom inválido ou não encontrado.", Toast.LENGTH_SHORT).show();
            }

            atualizarTotais(); // Atualiza os valores do carrinho
        });
    }

    @NonNull
    @Override
    public CarrinhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item do carrinho
        ActivityCarrinhoItemBinding itemBinding = ActivityCarrinhoItemBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new CarrinhoViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoViewHolder holder, int position) {
        ListaMostrarItens item = itensCarrinhoArrayList.get(position);

        // Define as informações do item no carrinho
        holder.binding.imgProdutoCarrinho.setBackgroundResource(item.getImgProduto());
        holder.binding.txtNomeProdutoCarrinho.setText(item.getNomeProduto());
        holder.binding.qtdCarrinhoTxt.setText(String.valueOf(item.getQuantidade()));
        holder.binding.txtPreco.setText(String.valueOf(item.getPreco().replace("R$ ", "")));

        // Clique para remover o item do carrinho
        holder.binding.btnRemover.setOnClickListener(v -> {
            removerItem(position);
            Toast.makeText(context, "Item removido do carrinho!", Toast.LENGTH_SHORT).show();
        });

        // Clique para aumentar a quantidade
        holder.binding.qtdAddCarrinhoBtn.setOnClickListener(v -> {
            item.setQuantidade(item.getQuantidade() + 1);
            notifyItemChanged(position);
            atualizarTotais();
        });

        // Clique para diminuir a quantidade
        holder.binding.qtdRemoveCarrinhoBtn.setOnClickListener(v -> {
            if (item.getQuantidade() > 1) {
                item.setQuantidade(item.getQuantidade() - 1);
                notifyItemChanged(position);
                atualizarTotais();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itensCarrinhoArrayList.size();
    }

    // Método para remover um item do carrinho
    private void removerItem(int position) {
        itensCarrinhoArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itensCarrinhoArrayList.size());
        atualizarTotais();

        // Verifica se o carrinho está vazio após remover o item
        if (itensCarrinhoArrayList.isEmpty()) {
            carrinhoVazioTxtV.setVisibility(View.VISIBLE);
            tituloCarrinhoTxtV.setVisibility(View.GONE);
            tituloCompraTxtV.setVisibility(View.GONE);
            subtotalValorTxtV.setVisibility(View.GONE);
            subtotalValor2TxtV.setVisibility(View.GONE);
            freteTaxaTxtV.setVisibility(View.GONE);
            descontoTxtV.setVisibility(View.GONE);
            totalValorTxtV.setVisibility(View.GONE);
            aplicarCupomBtn.setVisibility(View.GONE);
            CupomEdt.setVisibility(View.GONE);
            cupomTxtV.setVisibility(View.GONE);
            finalizarCompraBtn.setVisibility(View.GONE);
        }
    }

    // Método para atualizar os valores de subtotal, frete e total
    private void atualizarTotais() {
        double subtotal = 0;
        for (ListaMostrarItens item : itensCarrinhoArrayList) {
            subtotal += Double.parseDouble(item.getPreco().replace("R$ ", "")) * item.getQuantidade();
        }

        double total = subtotal + taxaFrete - valorDesconto;

        if (itensCarrinhoArrayList.isEmpty()) {
            // Exibe a mensagem de carrinho vazio e oculta as informações do carrinho
            carrinhoVazioTxtV.setVisibility(View.VISIBLE);
            tituloCarrinhoTxtV.setVisibility(View.GONE);
            tituloCompraTxtV.setVisibility(View.GONE);
            subtotalValorTxtV.setVisibility(View.GONE);
            subtotalValor2TxtV.setVisibility(View.GONE);
            freteTaxaTxtV.setVisibility(View.GONE);
            descontoTxtV.setVisibility(View.GONE);
            totalValorTxtV.setVisibility(View.GONE);
            aplicarCupomBtn.setVisibility(View.GONE);
            CupomEdt.setVisibility(View.GONE);
            cupomTxtV.setVisibility(View.GONE);
            finalizarCompraBtn.setVisibility(View.GONE);
        } else {
            // Atualiza os TextViews com os valores calculados
            subtotalValorTxtV.setText(String.format("%.2f", subtotal));
            subtotalValor2TxtV.setText(String.format("%.2f", subtotal));
            descontoTxtV.setText(String.format("%.2f", valorDesconto));
            freteTaxaTxtV.setText(String.format("%.2f", taxaFrete));
            totalValorTxtV.setText(String.format("%.2f", total));

            // Oculta a mensagem de carrinho vazio e exibe as informações do carrinho
            carrinhoVazioTxtV.setVisibility(View.GONE);
            tituloCarrinhoTxtV.setVisibility(View.VISIBLE);
            tituloCompraTxtV.setVisibility(View.VISIBLE);
            subtotalValorTxtV.setVisibility(View.VISIBLE);
            subtotalValor2TxtV.setVisibility(View.VISIBLE);
            freteTaxaTxtV.setVisibility(View.VISIBLE);
            descontoTxtV.setVisibility(View.VISIBLE);
            totalValorTxtV.setVisibility(View.VISIBLE);
            aplicarCupomBtn.setVisibility(View.VISIBLE);
            CupomEdt.setVisibility(View.VISIBLE);
            cupomTxtV.setVisibility(View.VISIBLE);
            finalizarCompraBtn.setVisibility(View.VISIBLE);
        }
    }

    // ViewHolder para o Adapter do Carrinho
    public static class CarrinhoViewHolder extends RecyclerView.ViewHolder {
        ActivityCarrinhoItemBinding binding;

        public CarrinhoViewHolder(ActivityCarrinhoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}