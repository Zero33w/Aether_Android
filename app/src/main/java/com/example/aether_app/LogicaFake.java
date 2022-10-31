package com.example.aether_app;

import android.util.Log;

import java.util.Date;

public class LogicaFake {

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
}
