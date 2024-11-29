package com.example.aplicativopim.model.Carrinho;

import android.view.View;
import android.widget.TextView;

import com.example.aplicativopim.model.DetalheProduto.ListaMostrarItens;

import java.util.ArrayList;


public class CarrinhoSingleton {

    private TextView cartItemCount;
    private static CarrinhoSingleton instancia;
    private final ArrayList<ListaMostrarItens> itensCarrinho;

    // Construtor privado para o padrão Singleton
    private CarrinhoSingleton() {
        itensCarrinho = new ArrayList<>();
    }

    // Método para obter a instância única
    public static CarrinhoSingleton getInstance() {
        if (instancia == null) {
            instancia = new CarrinhoSingleton();
        }
        return instancia;
    }

    public int getQuantidadeItens() {
        return itensCarrinho.size();
    }

    // Método para obter a lista de itens no carrinho
    public ArrayList<ListaMostrarItens> getItensCarrinho() {
        return itensCarrinho;
    }

    // Interface de Listener
    public interface CarrinhoChangeListener {
        void onCarrinhoChanged();
    }

    private final ArrayList<CarrinhoChangeListener> listeners = new ArrayList<>();

    private void notifyCarrinhoChanged() {
        for (CarrinhoChangeListener listener : listeners) {
            listener.onCarrinhoChanged();
        }
    }

    // Chame notifyCarrinhoChanged() nos métodos adicionarProduto(), removerProduto() e limparCarrinho()
    public void adicionarProduto(ListaMostrarItens item) {
        itensCarrinho.add(item);
        notifyCarrinhoChanged();
    }

    public void removerProduto(ListaMostrarItens item) {
        itensCarrinho.remove(item);
        notifyCarrinhoChanged();
    }

    public void limparCarrinho() {
        itensCarrinho.clear();
        notifyCarrinhoChanged();
    }


    private void atualizarContagemDoCarrinho(TextView cartItemCount) {
        // Supondo que você tenha uma função para obter a quantidade de itens no carrinho
        int itemCount = CarrinhoSingleton.getInstance().getQuantidadeItens();

        if (itemCount > 0) {
            cartItemCount.setText(String.valueOf(itemCount));
            cartItemCount.setVisibility(View.VISIBLE);
        } else {
            cartItemCount.setVisibility(View.GONE);
        }
    }
}