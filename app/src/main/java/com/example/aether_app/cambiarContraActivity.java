package com.example.aether_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class cambiarContraActivity extends AppCompatActivity {

    EditText nuevaContra, correo, contra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contra=findViewById(R.id.introducirCorreo);
        nuevaContra=findViewById(R.id.idSensor);
        correo=findViewById(R.id.correo);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contra);
    }
    /**
     * cambiarContra llama a la función de la lógica del negocio.
     * cambiarContra()
     *
     *  No devuelve ningun valor.
     */
    private void cambiarContra() {
        contra=findViewById(R.id.introducirCorreo);
        nuevaContra=findViewById(R.id.idSensor);
        correo=findViewById(R.id.correo);
            LogicaFake.cambiarContrasenya("https://jmarzoz.upv.edu.es/src/ServidorLogica/cambiarContrasenya.php",
                    correo.getText().toString(),nuevaContra.getText().toString(),contra.getText().toString());

    }
    /**
     * botonCambiarContra Al pulsar el botón para cambiar la contraseña.
     * View v->botonCambiarContra()
     * @param v llamar al onclick.
     *
     * No devuelve ningún valor.
     */
    public void botonCambiarContra(View v) {
        cambiarContra();
    }




}