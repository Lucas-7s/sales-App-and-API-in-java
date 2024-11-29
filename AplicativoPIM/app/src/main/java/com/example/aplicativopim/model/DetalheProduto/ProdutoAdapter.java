package com.example.aplicativopim.model.DetalheProduto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicativopim.databinding.ActivityListaItemBinding;
import com.example.aplicativopim.model.Carrinho.CarrinhoSingleton;

import java.util.ArrayList;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {
    private final ArrayList<ListaMostrarItens> foodList;
    private final Context context;
    private OnItemAddedListener onItemAddedListener;
    private TextView cartItemCount;
    private final AtualizarCarrinho onProdutoAdapterListener;

    public ProdutoAdapter(ArrayList<ListaMostrarItens> foodList, Context context, AtualizarCarrinho listener) {
        this.foodList = foodList;
        this.context = context;
        this.onProdutoAdapterListener = listener;
    }

    public void setOnItemAddedListener(OnItemAddedListener listener) {
        this.onItemAddedListener = listener;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityListaItemBinding listItem = ActivityListaItemBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new ProdutoViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        ListaMostrarItens produto = foodList.get(position);

        // Define as informações do produto
        holder.binding.imgProduto.setBackgroundResource(produto.getImgProduto());
        holder.binding.txtNomeProduto.setText(produto.getNomeProduto());
        holder.binding.txtDescricaoProduto.setText(produto.getDescricaoProduto());
        holder.binding.txtPreco.setText(produto.getPreco());

        // Adiciona o clique no item para abrir a página de detalhes
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalheProduto.class);
            intent.putExtra("imgProduto", produto.getImgProduto());
            intent.putExtra("imgProdMini1", produto.getImgMiniProd01());
            intent.putExtra("imgProdMini2", produto.getImgMiniProd02());
            intent.putExtra("imgProdMini3", produto.getImgMiniProd03());
            intent.putExtra("imgProdMini4", produto.getImgMiniProd04());
            intent.putExtra("imgProdMini5", produto.getImgMiniProd05());
            intent.putExtra("nomeProduto", produto.getNomeProduto());
            intent.putExtra("descricaoProduto", produto.getDescricaoProduto());
            intent.putExtra("precoProduto", produto.getPreco());
            intent.putExtra("comentario", produto.getComentario());
            context.startActivity(intent);
        });

        // Adiciona o clique no botão "Comprar" para adicionar o item ao carrinho
        // Adiciona o clique no botão "Comprar" para adicionar o item ao carrinho
        holder.binding.btComprar.setOnClickListener(v -> {
            // Cria uma nova instância do produto com a quantidade definida como 1
            ListaMostrarItens novoProduto = new ListaMostrarItens(
                    produto.getImgProduto(),
                    produto.getImgMiniProd01(),
                    produto.getImgMiniProd02(),
                    produto.getImgMiniProd03(),
                    produto.getImgMiniProd04(),
                    produto.getImgMiniProd05(),
                    produto.getNomeProduto(),
                    produto.getDescricaoProduto(),
                    produto.getPreco(),
                    produto.getComentario(),
                    1 // Define a quantidade como 1 para cada nova adição
            );

            // Adiciona o novo produto ao carrinho
            CarrinhoSingleton.getInstance().adicionarProduto(novoProduto);
            Toast.makeText(context, "1 Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();

            // Chama o método para atualizar a contagem do carrinho
            if (onProdutoAdapterListener != null) {
                onProdutoAdapterListener.atualizarContagemDoCarrinho();
            }
        });
    }

    // Método para adicionar o produto ao carrinho
    private void adicionarAoCarrinho(ListaMostrarItens produto) {
        produto.setQuantidade(1);
        CarrinhoSingleton.getInstance().adicionarProduto(produto);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ActivityListaItemBinding binding;

        public ProdutoViewHolder(ActivityListaItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // Interface para o callback quando um item é adicionado ao carrinho
    public interface OnItemAddedListener {
        void onItemAdded();
    }
}