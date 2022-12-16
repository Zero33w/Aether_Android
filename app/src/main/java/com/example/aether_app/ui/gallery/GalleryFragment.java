package com.example.aether_app.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aether_app.LogicaFake;
import com.example.aether_app.databinding.FragmentGalleryBinding;


public class GalleryFragment extends Fragment {

    public FragmentGalleryBinding binding;
    public static GalleryFragment instance;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Llamada a obtener Datos del Usuario
        SharedPreferences preferences =  this.getActivity().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        LogicaFake.obtenerDatosUsuario("https://jmarzoz.upv.edu.es/src/ServidorLogica/datosUsuario.php", preferences.getString("usuario",""));
        //final TextView textView = binding.textGallery;

        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public GalleryFragment() {
        instance = this;
    }

    public static GalleryFragment getInstance() {
        return instance;
    }
}