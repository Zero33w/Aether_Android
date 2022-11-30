package com.example.aether_app.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aether_app.LogicaFake;
import com.example.aether_app.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    public FragmentHomeBinding binding;
    private static HomeFragment instance;
    public String valorMediaAire ="Nada";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Log.d("CMEADOR",binding.calidadAireValorText.getText().toString());

        LogicaFake.obtenerPorcentaje("https://jmarzoz.upv.edu.es/src/ServidorLogica/obtenerMediaAire.php","XEVI-GTI-PROY-3A");



        //binding.calidadAireValorText.setText(String.valueOf(valorMediaAire));
        //binding.progressBar.setProgress(Math.round(valor));

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    public HomeFragment() {
        instance = this;
    }

    public static HomeFragment getInstance() {
        return instance;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}