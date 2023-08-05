package com.example.boutiqueshop.ui.configuraciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.boutiqueshop.databinding.FragmentConfiguracionesBinding;


public class ConfiguracionesFragment extends Fragment {

    private FragmentConfiguracionesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConfiguracionesViewModel configuracionesViewModel =
                new ViewModelProvider(this).get(ConfiguracionesViewModel.class);

        binding = FragmentConfiguracionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textConfiguraciones;
        configuracionesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}