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
public class FragmentMicroGreen extends Fragment implements AtualizarCarrinho {

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

        ListaMostrarItens food1 = new ListaMostrarItens(
                R.drawable.prod01_v01,
                R.drawable.prod01_v01,
                R.drawable.prod01_v02,
                R.drawable.prod01_v03,
                R.drawable.prod01_v04,
                R.drawable.prod01_v05,
                "MicroFruit Morango",
                "Sabor Doce e Suave - Brotos Frescos e Nutritivos. 🍓 Se você busca um sabor delicioso e a nutrição ideal, nossos brotos de morango são a escolha perfeita para sua alimentação diária. Com um toque adocicado e a essência do morango fresco, esses brotos são cultivados com o máximo cuidado, proporcionando uma fonte rica em nutrientes e vitaminas essenciais para uma dieta equilibrada.\n\n" +
                        "Benefícios: Ricos em antioxidantes, vitaminas C e K, ideais para saladas, smoothies e sobremesas saudáveis.\n\n" +
                        "Conteúdo: 1x Pacote de Brotos de Morango",
                "R$ 29.90",
                "Qualidade: Boa\n\nMuito bom o produto 😍😍😍, recebi bem rápido. O sabor é muito bom, comprei para provar e recomendo, logo comprarei novamente!",
                1
        );
        foodList.add(food1);

        ListaMostrarItens food2 = new ListaMostrarItens(
                R.drawable.prod02_v01,
                R.drawable.prod02_v01,
                R.drawable.prod02_v02,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Broto de Alfalfa",
                "Descrição: Pequenas folhas crocantes com um sabor suave.\n\n" +
                        "Benefícios: Rico em vitaminas A, C, D e E, e antioxidantes.",
                "R$ 19.90",
                "Qualidade: Boa\n\nProduto fresco e chegou rápido. Vou comprar novamente. 🥗",
                1
        );
        foodList.add(food2);

        ListaMostrarItens food3 = new ListaMostrarItens(
                R.drawable.prod03_v01,
                R.drawable.prod03_v01,
                R.drawable.prod03_v02,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Broto de Rúcula",
                "Descrição: Folhas verdes com um sabor levemente picante.\n\n" +
                        "Benefícios: Fonte de vitaminas A, C, K e minerais como cálcio e ferro.",
                "R$ 27.90",
                "Qualidade: Muito Boa\n\nAdorei o sabor picante! Vai bem em saladas e sanduíches. 🌶",
                1
        );
        foodList.add(food3);

        ListaMostrarItens food4 = new ListaMostrarItens(
                R.drawable.prod04_v01,
                R.drawable.prod04_v01,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Broto de Mostarda",
                "Descrição: Folhas verdes com um sabor picante e levemente amargo.\n\n" +
                        "Benefícios: Contém vitaminas A, C e K, além de minerais como cálcio e ferro.",
                "R$ 28.90",
                "Qualidade: Boa\n\nExcelente sabor para pratos mais exóticos. Experiência ótima! 🧄",
                1
        );
        foodList.add(food4);

        ListaMostrarItens food5 = new ListaMostrarItens(
                R.drawable.prod05_v01,
                R.drawable.prod05_v01,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Broto de Ervilha",
                "Descrição: Brotos verdes e tenros com um sabor doce e fresco.\n\n" +
                        "Benefícios: Alta concentração de vitaminas A, C, e K, além de proteínas e fibras.",
                "R$ 22.90",
                "Qualidade: Excelente\n\nSabor adocicado, perfeito para uma dieta balanceada. 💚",
                1
        );
        foodList.add(food5);

        ListaMostrarItens food6 = new ListaMostrarItens(
                R.drawable.prod06_v01,
                R.drawable.prod06_v01,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Broto de Rabanete",
                "Descrição: Folhas verdes com um sabor picante e refrescante.\n\n" +
                        "Benefícios: Fonte de vitaminas C e K, além de antioxidantes.",
                "R$ 29.90",
                "Qualidade: Muito Boa\n\nÓtima escolha para acompanhar refeições leves! 🌸",
                1
        );
        foodList.add(food6);

        ListaMostrarItens food7 = new ListaMostrarItens(
                R.drawable.prod07_v01,
                R.drawable.prod07_v01,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Broto de Beterraba",
                "Descrição: Folhas verdes e vermelhas com um sabor terroso.\n\n" +
                        "Benefícios: Rico em vitaminas A e C, ferro e fibras.",
                "R$ 22.90",
                "Qualidade: Boa\n\nBom produto, ideal para dar um toque especial às saladas! 🥗",
                1
        );
        foodList.add(food7);

        ListaMostrarItens food8 = new ListaMostrarItens(
                R.drawable.prod08_v01,
                R.drawable.prod08_v01,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Broto de Couve",
                "Descrição: Folhas verdes escuras com um sabor robusto.\n\n" +
                        "Benefícios: Alta concentração de vitaminas A, C, e K, além de antioxidantes.",
                "R$ 24.90",
                "Qualidade: Excelente\n\nProduto fresco e de alta qualidade! 😋",
                1
        );
        foodList.add(food8);

        ListaMostrarItens food9 = new ListaMostrarItens(
                R.drawable.prod09_v01,
                R.drawable.prod09_v01,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Broto de Cebolinha",
                "Descrição: Folhas verdes finas com um sabor suave de cebola.\n\n" +
                        "Benefícios: Fonte de vitaminas A, C e K, além de minerais como cálcio e ferro.",
                "R$ 24.90",
                "Qualidade: Muito Boa\n\nÓtimo complemento para pratos caseiros! 🌱",
                1
        );
        foodList.add(food9);

        ListaMostrarItens food10 = new ListaMostrarItens(
                R.drawable.prod10_v01,
                R.drawable.prod10_v01,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                R.drawable.not_found,
                "Mistura de Microgreens",
                "Descrição: Blend de várias variedades de microgreens para um sabor e textura variados.\n\n" +
                        "Benefícios: Oferece um mix de nutrientes e sabores em um único pacote.",
                "R$ 39.90",
                "Qualidade: Excelente\n\nSuper prático e gostoso! 🥗",
                1
        );
        foodList.add(food10);

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