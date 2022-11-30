package com.example.aether_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarActivity extends AppCompatActivity {

    EditText usuario, contrasenya, correo, confirmContra;
    Button botonRegistro;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        usuario=findViewById(R.id.nombre);
        contrasenya=findViewById(R.id.introducirCorreo);
        correo=findViewById(R.id.correo);
        confirmContra=findViewById(R.id.idSensor);
        checkBox=findViewById(R.id.terminos_condiciones_boton);
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
        if (validateData(email, password, this.confirmContra.getText().toString())&&checkBox.isChecked()) {
            LogicaFake.registrarUsuario("https://jmarzoz.upv.edu.es/src/ServidorLogica/enviarCorreoRegistro.php",
                    usuario.getText().toString(),correo.getText().toString(),confirmContra.getText().toString());
            Toast.makeText(getApplicationContext(), "Te hemos mandado un correo de verificacion", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
            this.checkBox.setError("Debes aceptar los términos y condiciones");
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
    public void botonTerminos(View v) {
        Intent intent = new Intent(getApplicationContext(), TerminosActivity.class);
        startActivity(intent);
        finish();
    }
}