package com.example.aether_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class editarNombreActivity extends AppCompatActivity {

    EditText nuevoNombre, contra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nombre);
    }
    private void cambiarNombre() {
        nuevoNombre=findViewById(R.id.introducirCorreo);
        contra=findViewById(R.id.idSensor);
        LogicaFake.editarNombre("https://jmarzoz.upv.edu.es/src/ServidorLogica/cambiarNombreUsuario.php",
                nuevoNombre.getText().toString(),contra.getText().toString());
    }
    //crear funcion para un boton
    public void botonCambiarNombre(View v) {
        cambiarNombre();
    }

}