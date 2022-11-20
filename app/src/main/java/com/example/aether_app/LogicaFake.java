package com.example.aether_app;

import android.util.Log;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LogicaFake extends AppCompatActivity {

    /**
     * GuardarMedidaEnBD guarda los datos del sensor en la BBDD.
     * URL:String, major:String, minor:String, uuid:String->GuardarMedidaEnBD()
     *
     * @param major El valor del sensor.
     * @param minor Otro valor del sensor.
     * @param urlDestino El servidor donde vamos a introducir los datos.
     *
     *  No devuelve ningun valor, esta función introduce lo recibido en el sensor a la BBDD
     */

    public static void GuardarMedidaEnBD(String major, String minor,String urlDestino,String uuid) {
        Log.d("clienterestandroid", "boton_enviar_pulsado");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        Date fechaHoy = new Date();
        elPeticionario.hacerPeticionREST("POST", urlDestino,
                //"{\"Valor\": \"8888\", \"TipoMedida\": \"PruebaMovil\", \"Fecha\": \" " + fechaHoy.getTime() + " \" , \"Latitud\": \"1231\" , \"Longitud\": \"1231\"}",
                "idSensor="+uuid+"&valorMedicion="+major+"&momentoMedicion="+fechaHoy.getTime()+"&latitud=38.995844400283715&longitud=-0.16542336747835645",
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("clienterestandroid" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });

    } // pulsado ()
    /**
     * registrarUsuario guarda los datos del registro en la BBDD.
     * URL:String, usuario:String, correo:String, contrasenya:String->GuardarMedidaEnBD()
     *
     * @param URL la URL del servidor.
     * @param usuario El nombre introducido por el usuario.
     * @param correo El correo introducido por el usuario.
     * @param contrasenya La contraseña introducida por el usuario.
     *  No devuelve ningun valor.
     */
    public static void registrarUsuario(String URL, String usuario, String correo, String contrasenya){
        Log.d("clienterestandroid", "boton_enviar_pulsado");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        Date fechaHoy = new Date();
        elPeticionario.hacerPeticionREST("POST", URL,
                //"{\"Valor\": \"8888\", \"TipoMedida\": \"PruebaMovil\", \"Fecha\": \" " + fechaHoy.getTime() + " \" , \"Latitud\": \"1231\" , \"Longitud\": \"1231\"}",
                "nombre="+usuario+"&correo="+correo+"&contrasenya="+contrasenya,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("clienterestandroid" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
        }
    }
