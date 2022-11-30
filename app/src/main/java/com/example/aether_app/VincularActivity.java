package com.example.aether_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class VincularActivity extends AppCompatActivity {
    private EditText idSensor,correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincular);
    }
    private void vincularSensor() {
        idSensor=findViewById(R.id.idSensor);
        correo=findViewById(R.id.introducirCorreo);
        LogicaFake.enviarSensor("https://jmarzoz.upv.edu.es/src/ServidorLogica/asignarSensorUsuario.php",
                correo.getText().toString(),idSensor.getText().toString());
    }
    public void botonVincularSensor(View v) {
        vincularSensor();
    }
}