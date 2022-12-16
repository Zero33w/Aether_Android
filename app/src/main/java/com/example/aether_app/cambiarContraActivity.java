package com.example.aether_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class cambiarContraActivity extends AppCompatActivity {

    EditText nuevaContra, antiguaContra, nuevaContraConfirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        nuevaContra=findViewById(R.id.idSensor);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contra);
    }

    private void cambiarContra() {

        //Obtener el correo
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        String correo = preferences.getString("usuario","");

        nuevaContra=findViewById(R.id.idSensor);
        //nuevaContraConfirmar=findViewById(R.id.nuevaContrasenyaRepetida);
        antiguaContra=findViewById(R.id.introducirCorreo);

        if(comprobarNuevaContrasenya(nuevaContra.getText().toString(), nuevaContraConfirmar.getText().toString()))
        {
            LogicaFake.cambiarContrasenya("https://jmarzoz.upv.edu.es/src/ServidorLogica/cambiarContrasenya.php",
                    correo);
        }
        else{
            this.nuevaContraConfirmar.setError("Las contraseñas no coinciden");
        }

    }
    //crear funcion para un boton
    public void botonCambiarContra(View v) {
        cambiarContra();
    }

    private boolean comprobarNuevaContrasenya(String password, String confirmPassword){
        if(password.equals(confirmPassword)){
            return true;
        }
        else {
            return false;

        }
    }

}