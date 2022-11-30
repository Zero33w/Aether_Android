package com.example.aether_app.ui.logOut;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aether_app.MenuActivity;
import com.example.aether_app.databinding.FragmentLogOutBinding;
import com.example.aether_app.databinding.FragmentSlideshowBinding;
import com.example.aether_app.ui.slideshow.SlideshowViewModel;

public class logOutFragment extends Fragment {
    private FragmentLogOutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logOutViewModel logOutViewModel =
                new ViewModelProvider(this).get(logOutViewModel.class);

        binding = FragmentLogOutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //final TextView textView = binding.textSlideshow;
        //slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
