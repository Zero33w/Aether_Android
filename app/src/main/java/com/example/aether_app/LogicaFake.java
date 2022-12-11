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
import com.example.aether_app.ui.gallery.GalleryFragment;
import com.example.aether_app.ui.home.HomeFragment;
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
    /**
     * obtenerPorcentaje extrae de BBDD el porcentaje dependiendo de lso datos obtenidos.
     * URL:String, idSensor:String->ontenerPorcentaje()
     *
     * @param URL la URL del servidor.
     * @param idSensor El nombre de la id del sensor.
     *  No devuelve ningun valor.
     */
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
    /**
     * editarNobre edita los datos de la BBDD.
     * URL:String, nombreNuevo:String, contrasenya:String->editarNombre()
     *
     * @param URL la URL del servidor.
     * @param nombreNuevo El nombre introducido por el usuario.
     * @param contrasenya La contraseña introducida por el usuario.
     *  No devuelve ningun valor.
     */
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
    /**
     * cambiarContrasenya edita la contraseña de la BBDD.
     * URL:String, correo:String, contrasenya:String, nuevaContrasenya:String->cambiarContrasenya()
     *
     * @param URL la URL del servidor.
     * @param correo El correo introducido por el usuario.
     * @param contrasenya La contraseña antigua del usuario.
     * @param nuevaContrasenya La contraseña nueva introducida por el usuario.
     *  No devuelve ningun valor.
     */
    public static void cambiarContrasenya(String URL, String correo, String nuevaContrasenya, String contrasenya){
        Log.d("clienterestandroid", "boton_enviar_pulsado");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        elPeticionario.hacerPeticionREST("POST", URL,
                //"{\"Valor\": \"8888\", \"TipoMedida\": \"PruebaMovil\", \"Fecha\": \" " + fechaHoy.getTime() + " \" , \"Latitud\": \"1231\" , \"Longitud\": \"1231\"}",
                "correo="+correo+"&contrasenya="+contrasenya+"&nuevaContrasenya="+nuevaContrasenya,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("clienterestandroid" ,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
    }
/*
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

 */
    /**
     * obtenerDatosUsuario obtiene los datos del usuario.
     * URL:String, correo:String, contrasenya:String->obtenerDatosUsuario()
     *
     * @param URL la URL del servidor.
     * @param correo El correo introducido por el usuario.
     *  No devuelve ningun valor.
     */
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
    /**
     * enviarSensor obtiene el avatar del usuario.
     * URL:String, correo:String, contrasenya:String->enviarSensor()
     *
     * @param URL la URL del servidor.
     * @param correo El correo introducido por el usuario.
     * @param idSensor El id del sensor.
     *  No devuelve ningun valor.
     */
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
}
