package com.example.aplicativopim.model.DetalheProduto;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.aplicativopim.R;
import com.example.aplicativopim.model.Carrinho.Carrinho;
import com.example.aplicativopim.model.Carrinho.CarrinhoSingleton;
import com.example.aplicativopim.model.FaleConosco;
import com.example.aplicativopim.model.Pedidos.MeusPedidos;
import com.example.aplicativopim.model.Perfil;

public class DetalheProduto extends AppCompatActivity {
    private TextView addToCardBtn;
    private TextView qtdTxt;
    private int qtdInicial = 1;
    private boolean isFavorite = false;
    private ImageButton favoriteButton;
    private ListaMostrarItens produtoDetalhe;
    private TextView cartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        iniciaView();
        setupShareButton();
        setupToolbar();
        setupQuantityControls();
        setupAddToCartButton();
        atualizarContagemDoCarrinho();
        setupFavoriteButton();
    }

    private void setupFavoriteButton() {
        favoriteButton.setOnClickListener(v -> {
            isFavorite = !isFavorite;

            if (isFavorite) {
                favoriteButton.setImageResource(R.drawable.ic_lovefull);
            } else {
                favoriteButton.setImageResource(R.drawable.ic_loviempty);
            }

            animateButton(favoriteButton);
        });
    }

    private void setupShareButton() {
        ImageButton shareButton = findViewById(R.id.shareButton);  // Certifique-se de que o ID está correto no XML
        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            // Formatando o preço, removendo o símbolo de moeda se necessário
            String precoFormatado = produtoDetalhe.getPrecoProduto().replace("R$ ", "");

            String shareBody = "Confira este produto: " + produtoDetalhe.getNomeProduto() +
                    " por R$ " + precoFormatado + "\nwww.microgreen.com";

            String shareSubject = "Produto incrível na MicroFruit!";

            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

            // Inicia o compartilhamento
            startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
        });
    }

    private void iniciaView() {  // Cria ou recupera o produto de algum lugar
        addToCardBtn = findViewById(R.id.addToCardBtn);
        ImageView imgViewProduto = findViewById(R.id.imgProdutoDetalhe);
        ImageView imgProdMini1 = findViewById(R.id.imgProdMini1);
        ImageView imgProdMini2 = findViewById(R.id.imgProdMini2);
        ImageView imgProdMini3 = findViewById(R.id.imgProdMini3);
        ImageView imgProdMini4 = findViewById(R.id.imgProdMini4);
        ImageView imgProdMini5 = findViewById(R.id.imgProdMini5);
        TextView txtNomeProduto = findViewById(R.id.txtNomeProdutoDetalhe);
        TextView txtDescricaoProduto = findViewById(R.id.txtDescricaoProdutoDetalhe);
        TextView txtPrecoProduto = findViewById(R.id.txtPrecoProdutoDetalhe);
        TextView txtPrecoParcelado = findViewById(R.id.txtPrecoParceladoDetalhe);
        TextView txtComentario = findViewById(R.id.txtComentarioDetalhe);

        favoriteButton = findViewById(R.id.favoriteButton);

        Intent intent = getIntent();
        int getImgProduto = intent.getIntExtra("imgProduto", 0);
        int getImgMiniProd01 = intent.getIntExtra("imgProdMini1", 0);
        int getImgMiniProd02 = intent.getIntExtra("imgProdMini2", 0);
        int getImgMiniProd03 = intent.getIntExtra("imgProdMini3", 0);
        int getImgMiniProd04 = intent.getIntExtra("imgProdMini4", 0);
        int getImgMiniProd05 = intent.getIntExtra("imgProdMini5", 0);
        String getNomeProduto = intent.getStringExtra("nomeProduto");
        String getDescricaoProduto = intent.getStringExtra("descricaoProduto");
        String getPrecoProduto = intent.getStringExtra("precoProduto");
        String getComentario = intent.getStringExtra("comentario");

        double parcelamento = Double.parseDouble(getPrecoProduto.replace("R$ ", ""));
        double taxa = parcelamento * 0.10;

        imgViewProduto.setImageResource(getImgProduto);
        imgProdMini1.setImageResource(getImgMiniProd01);
        imgProdMini2.setImageResource(getImgMiniProd02);
        imgProdMini3.setImageResource(getImgMiniProd03);
        imgProdMini4.setImageResource(getImgMiniProd04);
        imgProdMini5.setImageResource(getImgMiniProd05);
        txtNomeProduto.setText(getNomeProduto);
        txtDescricaoProduto.setText(getDescricaoProduto);
        txtPrecoProduto.setText(getPrecoProduto);
        txtPrecoParcelado.setText("em até 12x de R$ " + String.format("%.2f", (parcelamento / 12.0) + taxa));
        txtComentario.setText(getComentario);

        produtoDetalhe = new ListaMostrarItens(getImgProduto, getImgMiniProd01, getImgMiniProd02, getImgMiniProd03, getImgMiniProd04, getImgMiniProd05, getNomeProduto, getDescricaoProduto, getPrecoProduto, getComentario, qtdInicial);

        imgProdMini1.setOnClickListener(v -> imgViewProduto.setImageResource(getImgMiniProd01));
        imgProdMini2.setOnClickListener(v -> imgViewProduto.setImageResource(getImgMiniProd02));
        imgProdMini3.setOnClickListener(v -> imgViewProduto.setImageResource(getImgMiniProd03));
        imgProdMini4.setOnClickListener(v -> imgViewProduto.setImageResource(getImgMiniProd04));
        imgProdMini5.setOnClickListener(v -> imgViewProduto.setImageResource(getImgMiniProd05));
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarDetalheProduto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    private void setupQuantityControls() {
        Button qtdRemoveBtn = findViewById(R.id.qtdRemoveDetalheBtn);
        Button qtdAddBtn = findViewById(R.id.qtdAddDetalheBtn);
        qtdTxt = findViewById(R.id.qtdDetalheTxt);
        qtdTxt.setText(String.valueOf(qtdInicial));

        qtdRemoveBtn.setOnClickListener(v -> {
            if (qtdInicial > 1) {
                qtdInicial--;
                qtdTxt.setText(String.valueOf(qtdInicial));
            }
        });

        qtdAddBtn.setOnClickListener(v -> {
            qtdInicial++;
            qtdTxt.setText(String.valueOf(qtdInicial));
        });
    }

    private void setupAddToCartButton() {
        addToCardBtn.setOnClickListener(v -> {
            // Cria uma nova instância do produto com a quantidade atual
            ListaMostrarItens novoProduto = new ListaMostrarItens(
                    produtoDetalhe.getImgProduto(),
                    produtoDetalhe.getImgMiniProd01(),
                    produtoDetalhe.getImgMiniProd02(),
                    produtoDetalhe.getImgMiniProd03(),
                    produtoDetalhe.getImgMiniProd04(),
                    produtoDetalhe.getImgMiniProd05(),
                    produtoDetalhe.getNomeProduto(),
                    produtoDetalhe.getDescricaoProduto(),
                    produtoDetalhe.getPreco(),
                    produtoDetalhe.getComentario(),
                    qtdInicial // Define a quantidade atual
            );

            // Adiciona a nova instância ao carrinho
            CarrinhoSingleton.getInstance().adicionarProduto(novoProduto);
            atualizarContagemDoCarrinho();
            Toast.makeText(DetalheProduto.this, qtdInicial + " Produto(s) adicionado ao carrinho", Toast.LENGTH_SHORT).show();
        });
    }

    private void atualizarContagemDoCarrinho() {
        int itemCount = CarrinhoSingleton.getInstance().getQuantidadeItens();

        // Verifica se a view de contagem existe
        if (cartItemCount != null) {
            if (itemCount > 0) {
                cartItemCount.setText(String.valueOf(itemCount));
                cartItemCount.setVisibility(View.VISIBLE);  // Mostra o contador se houver itens
            } else {
                cartItemCount.setVisibility(View.GONE);  // Esconde se não houver itens
            }
        }
    }

    private void animateButton(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(300);
        animatorSet.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Configura o layout do ícone do carrinho
        MenuItem menuItem = menu.findItem(R.id.action_card);
        View actionView = menuItem.getActionView();
        if (actionView == null) {
            actionView = getLayoutInflater().inflate(R.layout.custom_cart_icon_layout, null);
        }
        cartItemCount = actionView.findViewById(R.id.cart_badge);

        // Atualiza a contagem do carrinho no início
        atualizarContagemDoCarrinho();

        // Adiciona o clique no ícone do carrinho para abrir a tela do carrinho
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        menuItem.setActionView(actionView);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Sempre atualizar a contagem do carrinho quando a Activity for retomada
        if (cartItemCount != null) {
            atualizarContagemDoCarrinho();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            finish();
            return true;
        } else if (itemId == R.id.action_perfil) {
            startActivity(new Intent(DetalheProduto.this, Perfil.class));
            return true;
        } else if (itemId == R.id.action_card) {
            startActivity(new Intent(DetalheProduto.this, Carrinho.class));
            return true;
        } else if (itemId == R.id.action_fale_conosco) {  // Vai para a página de Meus Pedidos
            startActivity(new Intent(DetalheProduto.this, FaleConosco.class));
            return true;
        } else if (itemId == R.id.action_meus_pedidos) {
            startActivity(new Intent(DetalheProduto.this, MeusPedidos.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}