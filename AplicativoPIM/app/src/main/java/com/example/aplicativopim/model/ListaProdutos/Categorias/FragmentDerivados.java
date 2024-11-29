package com.example.aplicativopim.model.ListaProdutos.Categorias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicativopim.R;
import com.example.aplicativopim.databinding.FragmentTodosBinding;
import com.example.aplicativopim.model.DetalheProduto.AtualizarCarrinho;
import com.example.aplicativopim.model.DetalheProduto.ListaMostrarItens;
import com.example.aplicativopim.model.DetalheProduto.ProdutoAdapter;
import com.example.aplicativopim.model.ListaProdutos.ListaMostrar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTodos#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FragmentDerivados extends Fragment implements AtualizarCarrinho {

    private ArrayList<ListaMostrarItens> foodList = new ArrayList<>();
    private FragmentTodosBinding binding; // Atualize o binding para o layout correto
    private ProdutoAdapter produtoAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTodos newInstance(String param1, String param2) {
        FragmentTodos fragment = new FragmentTodos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se houver argumentos passados, podemos capturá-los aqui
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout correto do fragmento usando o FragmentTodosBinding
        binding = FragmentTodosBinding.inflate(inflater, container, false);

        // Inicialize o RecyclerView e o Adapter
        RecyclerView recyclerViewFood = binding.RecyclerviewProdutos;
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFood.setHasFixedSize(true);
        produtoAdapter = new ProdutoAdapter(foodList, getContext(), this);
        recyclerViewFood.setAdapter(produtoAdapter);

        // Carrega os dados para o RecyclerView
        getFood();

        // Retorna a view vinculada ao binding
        return binding.getRoot();
    }

    private void getFood(){

        ListaMostrarItens food11= new ListaMostrarItens(
                R.drawable.prod11_v01,
                R.drawable.prod11_v01,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Microgreens Desidratados",
                "Descrição: Pacotes de microgreens desidratados para conservação e uso em receitas.\n\n" +
                        "Benefícios: Nutrição prática e de fácil armazenamento.",
                "R$ 18.90",
                "Qualidade: Boa\n\nVersátil e saboroso. Perfeito para pratos rápidos. 🧂",
                1
        );
        foodList.add(food11);

        ListaMostrarItens food12 = new ListaMostrarItens(
                R.drawable.prod12_v01,
                R.drawable.prod12_v01,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Sementes para Germinação",
                "Descrição: Pacotes de sementes de microgreens para cultivo em casa.\n\n" +
                        "Benefícios: Permite que os clientes cultivem seus próprios microgreens.",
                "R$ 12.90",
                "Qualidade: Excelente\n\nÓtimo para quem quer começar a plantar. 🌱",
                1
    );
        foodList.add(food12);

        // Atualiza o adapter com os novos dados
        produtoAdapter.notifyDataSetChanged();
    }

    @Override
    public void atualizarContagemDoCarrinho() {
        // Verifica se a Activity associada ao fragmento é uma instância de ListaMostrar
        if (getActivity() instanceof ListaMostrar) {
            ((ListaMostrar) getActivity()).atualizarContagemDoCarrinhoPublic();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evita vazamento de memória
    }
}