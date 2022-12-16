package com.example.aether_app.ui.slideshow;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aether_app.LogicaFake;
import com.example.aether_app.databinding.FragmentSlideshowBinding;
import com.example.aether_app.ui.gallery.GalleryFragment;

public class SlideshowFragment extends Fragment {

    public FragmentSlideshowBinding binding;
    public static SlideshowFragment instance;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        displayLayout();

        //final TextView textView = binding.textSlideshow;
        //slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void displayLayout(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        String correo = preferences.getString("usuario","");
        LogicaFake.confirmarSensorVinculado("https://jmarzoz.upv.edu.es/src/ServidorLogica/disponeSensor.php", correo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public SlideshowFragment() {
        instance = this;
    }

    public static SlideshowFragment getInstance() {
        return instance;
    }
}