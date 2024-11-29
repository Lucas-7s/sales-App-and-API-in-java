package com.example.aplicativopim.model.DetalheProduto;

public class ListaMostrarItens {
    int imgProduto;
    int imgProdMini1;
    int imgProdMini2;
    int imgProdMini3;
    int imgProdMini4;
    int imgProdMini5;
    String nomeProduto;
    String descricaoProduto;
    String preco;
    String comentario;
    int quantidade = 1;
    int itensCarrinho;

    public ListaMostrarItens(int imgProduto, int imgProdMini1, int imgProdMini2, int imgProdMini3, int imgProdMini4, int imgProdMini5, String nomeProduto, String descricaoProduto, String preco, String comentario, int quantidade) {
        this.imgProduto = imgProduto;
        this.imgProdMini1 = imgProdMini1;
        this.imgProdMini2 = imgProdMini2;
        this.imgProdMini3 = imgProdMini3;
        this.imgProdMini4 = imgProdMini4;
        this.imgProdMini5 = imgProdMini5;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.preco = preco;
        this.comentario = comentario;
        this.quantidade = quantidade;
    }

    public int getImgProduto() {
        return imgProduto;
    }

    public int getImgMiniProd01() {
        return imgProdMini1;
    }

    public int getImgMiniProd02() {
        return imgProdMini2;
    }

    public int getImgMiniProd03() {
        return imgProdMini3;
    }

    public int getImgMiniProd04() {
        return imgProdMini4;
    }

    public int getImgMiniProd05() {
        return imgProdMini5;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public int getQuantidadeItens() {
        return itensCarrinho;
    }

    public String getPreco() {
        return preco;
    }

    public String getComentario(){
        return comentario;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public String getPrecoProduto() {
        return preco;
    }
}