package com.example.aether_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.Console;

public class editarNombreActivity extends AppCompatActivity {

    EditText nuevoNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nombre);
    }
    private void cambiarNombre() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);

        nuevoNombre=findViewById(R.id.introducirCorreo);
        LogicaFake.editarNombre("https://jmarzoz.upv.edu.es/src/ServidorLogica/cambiarNombreUsuario.php",
                nuevoNombre.getText().toString(), preferences.getString("usuario",""));

    }
    //crear funcion para un boton
    public void botonCambiarNombre(View v) {
        cambiarNombre();
    }

}