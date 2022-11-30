package com.example.aether_app;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aether_app.ui.home.HomeFragment;

import java.util.HashMap;
import java.util.Map;

// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
public class MainActivity extends AppCompatActivity {
    EditText usuario, contrasenya;
    Button botonLogin,botonCambiarContra;
    String usuarioAux, contrasenyaAux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = findViewById(R.id.usuario);
        contrasenya = findViewById(R.id.contrasenya);
        botonLogin = findViewById(R.id.botonLogin);
        botonCambiarContra = findViewById(R.id.contrasenya_olvidada_boton);
        recuperarPreferencias();
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioAux = usuario.getText().toString();
                contrasenyaAux = contrasenya.getText().toString();
                if (!usuarioAux.isEmpty() && !contrasenyaAux.isEmpty()) {
                    validarUsuario("https://jmarzoz.upv.edu.es/src/ServidorLogica/comprobarCredencialesAPP.php");
                } else {
                    Toast.makeText(MainActivity.this, "No nse permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * validarUsuario hace el login.
     * String URL->validarUsuario()
     *
     * @param URL del servidor.
     *
     * No devuelve nada.
     */
    private void validarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    guardarReferencias();
                    Intent intent=new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("correo",usuarioAux);
                parametros.put("contrasenya",contrasenyaAux);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    /**
     * guardarReferencias y recuperarPreferencias pasar el usuario y la contraseña para hacer el login.
     * guardarReferencias(), recuperarPreferencias()
     *
     * No devuelve nada.
     */
    private void guardarReferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("usuario",usuarioAux);
        editor.putString("contrasenya",contrasenyaAux);
        editor.putBoolean("sesion",true);
        editor.commit();
    }

    private void recuperarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        usuario.setText(preferences.getString("usuario",""));
        contrasenya.setText(preferences.getString("contrasenya",""));
    }
    /**
     * botonMandarRegistro Al pulsar el botón manda ventana registro.
     * View v->botonMandarRegistro()
     * @param v llamar al onclick.
     *
     * No devuelve ningún valor.
     */
    public void botonMandarRegistro(View v) {
        Intent intent=new Intent(getApplicationContext(),RegistrarActivity.class);
        startActivity(intent);
        finish();
    }
    public void botonMandarCambio(View v) {
        Intent intent=new Intent(getApplicationContext(),OlvidarContraActivity.class);
        startActivity(intent);
        finish();
    }
} // class
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------