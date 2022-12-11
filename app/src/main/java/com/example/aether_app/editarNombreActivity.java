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
    /**
     * cambiarNombre llama a la función de la lógica del negocia.
     * cambiarNombre()
     *
     *  No devuelve ningun valor.
     */
    private void cambiarNombre() {
        nuevoNombre=findViewById(R.id.introducirCorreo);
        contra=findViewById(R.id.idSensor);
        LogicaFake.editarNombre("https://jmarzoz.upv.edu.es/src/ServidorLogica/cambiarNombreUsuario.php",
                nuevoNombre.getText().toString(),contra.getText().toString());
    }
    /**
     * botonCambiarNombre Al pulsar el botón para cambiar la contraseña.
     * View v->botonCambiarNombre()
     * @param v llamar al onclick.
     *
     * No devuelve ningún valor.
     */
    public void botonCambiarNombre(View v) {
        cambiarNombre();
    }

}