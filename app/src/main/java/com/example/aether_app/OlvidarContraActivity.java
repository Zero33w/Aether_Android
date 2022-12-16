package com.example.aether_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OlvidarContraActivity extends AppCompatActivity {

    EditText correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contra);

        correo = findViewById(R.id.recuperar_contrasenya);
    }

    private void enviarCorreoRecupararContra(){
        LogicaFake.cambiarContrasenya("https://jmarzoz.upv.edu.es/src/ServidorLogica/enviarCorreoRecuperarAPP.php",
                correo.getText().toString());
        Toast.makeText(getApplicationContext(), "Se ha eviado un correo para el cambio", Toast.LENGTH_SHORT).show();
    }
    public void botonOlvidarContra(View v){
       enviarCorreoRecupararContra();
    }
}