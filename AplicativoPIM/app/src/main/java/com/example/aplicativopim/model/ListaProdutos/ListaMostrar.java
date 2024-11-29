package com.example.aplicativopim.model.ListaProdutos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.aplicativopim.R;
import com.example.aplicativopim.databinding.ActivityListaMostrarBinding;
import com.example.aplicativopim.model.Carrinho.Carrinho;
import com.example.aplicativopim.model.Carrinho.CarrinhoSingleton;
import com.example.aplicativopim.model.DetalheProduto.AtualizarCarrinho;
import com.example.aplicativopim.model.FaleConosco;
import com.example.aplicativopim.model.ListaProdutos.Categorias.FragmentCategoriaAdapter;
import com.example.aplicativopim.model.Pedidos.MeusPedidos;
import com.example.aplicativopim.model.Perfil;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ListaMostrar extends AppCompatActivity implements AtualizarCarrinho {
    private ActivityListaMostrarBinding binding;
    private TextView cartItemCount;  // Tornar cartItemCount uma variável de instância

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Atualize o binding para inflar o novo layout
        binding = ActivityListaMostrarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerViewFood = binding.RecyclerviewProdutos;
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFood.setHasFixedSize(true);

        setupToolbar();

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        FragmentCategoriaAdapter adapter = new FragmentCategoriaAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Todos");
                        break;
                    case 1:
                        tab.setText("Micro Green");
                        break;
                    case 2:
                        tab.setText("Derivados");
                        break;
                    case 3:
                        tab.setText("Extra");
                        break;
                }
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Encontre o item do carrinho no menu
        final MenuItem menuItem = menu.findItem(R.id.action_card);

        // Infle o layout personalizado
        View actionView = menuItem.getActionView();
        if (actionView == null) {
            actionView = getLayoutInflater().inflate(R.layout.custom_cart_icon_layout, null);
        }

        // Encontre o TextView no layout personalizado para mostrar a contagem do carrinho
        cartItemCount = actionView.findViewById(R.id.cart_badge);
        atualizarContagemDoCarrinho(cartItemCount); // Método para atualizar a contagem

        // Adicione o comportamento de clique para o layout do ícone do carrinho
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        // Substitua o ícone do menu pelo layout personalizado
        menuItem.setActionView(actionView);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Sempre atualizar a contagem do carrinho quando a Activity for retomada
        if (cartItemCount != null) {
            atualizarContagemDoCarrinho(cartItemCount);
        }
    }

    public void atualizarContagemDoCarrinhoPublic() {
        // Chama o método onResume() para atualizar a contagem do carrinho
        onResume();
    }

    private void atualizarContagemDoCarrinho(TextView cartItemCount) {
        int itemCount = CarrinhoSingleton.getInstance().getQuantidadeItens();

        if (itemCount > 0) {
            cartItemCount.setText(String.valueOf(itemCount));
            cartItemCount.setVisibility(View.VISIBLE);
        } else {
            cartItemCount.setVisibility(View.GONE);
        }
    }

    /** Define o comportamento dos itens do menu de opções.*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_perfil) {  // Vai para a página de Perfil
            startActivity(new Intent(ListaMostrar.this, Perfil.class));
            return true;
        } else if (itemId == R.id.action_card) {  // Vai para a página de Carrinho
            startActivity(new Intent(ListaMostrar.this, Carrinho.class));
            return true;
        } else if (itemId == R.id.action_meus_pedidos) {  // Vai para a página de Meus Pedidos
            startActivity(new Intent(ListaMostrar.this, MeusPedidos.class));
            return true;
        } else if (itemId == R.id.action_fale_conosco) {  // Vai para a página de Meus Pedidos
            startActivity(new Intent(ListaMostrar.this, FaleConosco.class));
            return true;
        } else if (itemId == R.id.action_logout) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void atualizarContagemDoCarrinho() {
        onResume();
    }

    public class TodosFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            // Inflate o layout do fragmento
            return inflater.inflate(R.layout.fragment_todos, container, false);
        }
    }

    /** Configura a Toolbar (barra de ferramentas) e os itens do menu. */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarlista);
        setSupportActionBar(toolbar);
    }
}