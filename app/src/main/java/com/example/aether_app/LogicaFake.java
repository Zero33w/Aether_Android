package com.example.aether_app;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aether_app.ui.gallery.GalleryFragment;
import com.example.aether_app.ui.home.HomeFragment;
import com.example.aether_app.ui.slideshow.SlideshowFragment;
import com.google.gson.Gson;

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
    public static void obtenerPorcentaje(String URL, String idSensor){
        Log.d("clienterestandroid", "boton_enviar_pulsado");
        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        elPeticionario.hacerPeticionREST("GET", URL+"?idSensor="+idSensor,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("prueba-get-porcentaje" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                        try{
                            HomeFragment.getInstance().binding.calidadAireValorText.setText(cuerpo);
                            HomeFragment.getInstance().binding.progressBar.setProgress(Math.round(Float.parseFloat(cuerpo)));

                        }catch (Exception e){
                            Log.d("prueba-get-porcentaje", "Error al actualizar el porcentaje");
                            HomeFragment.getInstance().binding.calidadAireValorText.setText("Nada");
                            HomeFragment.getInstance().binding.progressBar.setProgress(0);
                        }


                    }
                });
    }

    public static void editarNombre(String URL, String nombreNuevo, String contrasenya){
        Log.d("clienterestandroid", "boton_enviar_pulsado");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        Date fechaHoy = new Date();
        elPeticionario.hacerPeticionREST("POST", URL,
                //"{\"Valor\": \"8888\", \"TipoMedida\": \"PruebaMovil\", \"Fecha\": \" " + fechaHoy.getTime() + " \" , \"Latitud\": \"1231\" , \"Longitud\": \"1231\"}",
                "nombre="+nombreNuevo+"&correo="+contrasenya,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("clienterestandroid" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
    }

    public static void cambiarContrasenya(String URL, String correo){
        Log.d("clienterestandroid", "boton_enviar_pulsado");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        elPeticionario.hacerPeticionREST("POST", URL,
                //"{\"Valor\": \"8888\", \"TipoMedida\": \"PruebaMovil\", \"Fecha\": \" " + fechaHoy.getTime() + " \" , \"Latitud\": \"1231\" , \"Longitud\": \"1231\"}",
                "correo="+correo,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("clienterestandroid" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
    }

    public static void cambiarAvatar(String URL, String Avatar){
        Log.d("clienterestandroid", "boton_enviar_pulsado");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        elPeticionario.hacerPeticionREST("POST", URL,
                //"{\"Valor\": \"8888\", \"TipoMedida\": \"PruebaMovil\", \"Fecha\": \" " + fechaHoy.getTime() + " \" , \"Latitud\": \"1231\" , \"Longitud\": \"1231\"}",
                "idAvatar="+Avatar,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("clienterestandroid" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
    }

    public static void obtenerDatosUsuario(String URL, String correo){
        Log.d("clienterestandroid", "boton_enviar_pulsado");
        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        elPeticionario.hacerPeticionREST("GET", URL+"?correo="+correo,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("get-datos-usuario" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);

                        //Conversión JSON a Objeto usuario
                        Usuario usuario = new Usuario();
                        Gson gson = new Gson();
                        usuario = gson.fromJson(cuerpo,Usuario.class);

                        //Asignar en el layout el valor de los texto
                        GalleryFragment.getInstance().binding.nombreUsuarioTexto.setText(usuario.getNombre());
                        GalleryFragment.getInstance().binding.correoUsuarioTexto.setText(usuario.getCorreo());
                    }
                });
    }

    //hacer una peticion post que recibe una url un correo y una idSensor
    public static void enviarSensor(String URL, String correo, String idSensor){
        Log.d("clienterestandroid", "boton_enviar_pulsado");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        elPeticionario.hacerPeticionREST("POST", URL,
                //"{\"Valor\": \"8888\", \"TipoMedida\": \"PruebaMovil\", \"Fecha\": \" " + fechaHoy.getTime() + " \" , \"Latitud\": \"1231\" , \"Longitud\": \"1231\"}",
                "correo="+correo+"&idSensor="+idSensor,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("clienterestandroid" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
    }

    public static void confirmarSensorVinculado(String URL, String usuario ){
        Log.d("clienterestandroid", "boton_enviar_pulsado");
        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        elPeticionario.hacerPeticionREST("GET", URL+"?correo="+usuario,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("prueba-get-porcentaje" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);

                        if (Integer.parseInt(cuerpo)==1){
                            SlideshowFragment.getInstance().binding.sinSensor.setVisibility(View.INVISIBLE);
                            SlideshowFragment.getInstance().binding.conSensor.setVisibility(View.VISIBLE);
                        }
                        else{
                            SlideshowFragment.getInstance().binding.sinSensor.setVisibility(View.VISIBLE);
                            SlideshowFragment.getInstance().binding.conSensor.setVisibility(View.INVISIBLE);
                        }

                    }
                });
    }

    public static void estadoNodo(String URL ){
        Log.d("clienterestandroid", "boton_enviar_pulsado");
        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        elPeticionario.hacerPeticionREST("GET", URL+"?datos="+"u",
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("prueba-get-porcentaje" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);

                        //Conversión JSON a Objeto usuario
                        Medida medida = new Medida();
                        Gson gson = new Gson();

                        //quitar corchetes del array
                        cuerpo  = cuerpo.replace("[","");
                        cuerpo = cuerpo.replace("]","");

                        medida = gson.fromJson(cuerpo,Medida.class);

                        long momentoUltimaMedida = medida.getMomentoMedicion();

                        Log.d("prueba", String.valueOf(momentoUltimaMedida));

                        long ultimoEnvio;
                        ultimoEnvio=System.currentTimeMillis();
                        if (ultimoEnvio-momentoUltimaMedida<60000 ){

                            SlideshowFragment.getInstance().binding.conectadoIcono.getBackground().setTint(Color.rgb(31, 176, 77));
                        }
                        else {
                            SlideshowFragment.getInstance().binding.conectadoIcono.getBackground().setTint(Color.rgb(185, 45, 45));
                        }
                    }
                });
    }

}
