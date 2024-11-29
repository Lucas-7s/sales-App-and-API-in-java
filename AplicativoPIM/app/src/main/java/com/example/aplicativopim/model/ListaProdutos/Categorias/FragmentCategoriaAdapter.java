package com.example.aplicativopim.model.ListaProdutos.Categorias;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentCategoriaAdapter extends FragmentStateAdapter {

    public FragmentCategoriaAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentTodos(); // Fragment para "Todos"
            case 1:
                return new FragmentMicroGreen(); // Fragment para "Micro Green"
            case 2:
                return new FragmentDerivados(); // Fragment para "Sementes"
            case 3:
                return new FragmentExtra(); // Fragment para "Sementes"
            default:
                return new FragmentTodos(); // Fallback
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Número de categorias
    }
}