package com.example.aether_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {

    EditText usuario, contrasenya, correo, confirmContra;
    Button botonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        usuario=findViewById(R.id.nombre);
        contrasenya=findViewById(R.id.contra);
        correo=findViewById(R.id.correo);
        confirmContra=findViewById(R.id.confirmContra);
    }
    /**
     * registro Registra al usuario.
     * registro()
     *
     * No devuelve ningún valor.
     */
    public void registro() {
        String email = this.correo.getText().toString();
        String password = this.contrasenya.getText().toString();
        if (validateData(email, password, this.confirmContra.getText().toString())) {
            LogicaFake.registrarUsuario("https://jmarzoz.upv.edu.es/src/ServidorLogica/registroUsuariosAPP.php",
                    usuario.getText().toString(),correo.getText().toString(),confirmContra.getText().toString());
            Toast.makeText(getApplicationContext(), "REGISTRADO CON ÉXITO", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "ERROR AL REGISTRARSE", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * botonRegistrar Al pulsar el botón registra un usuario.
     * View v->botonRegistrar()
     * @param v llamar al onclick.
     *
     * No devuelve ningún valor.
     */
    public void botonRegistrar(View v) {
        this.registro();
    }
    /**
     * validateData comprueba si el correo esta bien y las contraseñas.
     * String email, String password, String confirmPassword->validateData()-> T/F
     *
     * @param email correo introducido.
     * @param password primera contraseña introducida.
     * @param confirmPassword segunda contraseña introducida.
     *
     * Devuelve un true o false.
     */
    public boolean validateData(String email, String password, String confirmPassword) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.correo.setError("El email no es valido");
            return false;
        } else if (password.length() < 6) {
            this.contrasenya.setError("La contraseña es muy corta");
            return false;
        } else if (password.equals(confirmPassword)) {
            return true;
        } else {
            this.confirmContra.setError("Las contraseñas no coinciden");
            return false;
        }
    }
}