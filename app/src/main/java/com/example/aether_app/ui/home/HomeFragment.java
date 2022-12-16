package com.example.aether_app.ui.home;

import android.graphics.Color;
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
import com.example.aether_app.R;
import com.example.aether_app.databinding.FragmentHomeBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {

    public FragmentHomeBinding binding;
    private static HomeFragment instance;
    public String valorMediaAire ="Nada";

    //Gráficas
    private LineChart graficaLineas;
    private LineDataSet datosGraficaLineas;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Log.d("CMEADOR",binding.calidadAireValorText.getText().toString());

        LogicaFake.obtenerPorcentaje("https://jmarzoz.upv.edu.es/src/ServidorLogica/obtenerMediaAire.php","AHETERTECH_GRUP3");

        //Grafica
        graficaLineas = binding.grafica;

        // Creamos un set de datos
        ArrayList<Entry> datos = new ArrayList<Entry>();
        for (int i = 0; i<11; i++){
            Random r = new Random();
            float y = r.nextInt(100);
            //float y = (int) (Math.random() * 8) + 1;
            datos.add(new Entry((float) i,(float)y));
        }

        // Unimos los datos al data set
        datosGraficaLineas = new LineDataSet(datos, "Calidad de aire");

        // Asociamos al gráfico
        LineData lineData = new LineData();
        lineData.addDataSet(datosGraficaLineas);

        //Ajustes de la grafica
        graficaLineas.getAxisRight().setEnabled(false);
        graficaLineas.getAxisLeft().setAxisMaximum(100);
        graficaLineas.getAxisLeft().setAxisMinimum(0);
        graficaLineas.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        datosGraficaLineas.setColor(Color.rgb(117, 176, 210));
        graficaLineas.getDescription().setEnabled(false);

        //Introducimos los datos a la gráfica
        graficaLineas.setData(lineData);


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