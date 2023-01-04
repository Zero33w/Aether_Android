package com.example.aether_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class UsuarioActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback{

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    private static final String ETIQUETA_LOG = ">>>>";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private Intent elIntentDelServicio = null;
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothAdapter mBluetoothAdapter;
    private ScanSettings settings;
    private BluetoothLeScanner elEscanner;
    private static final int CODIGO_PETICION_PERMISOS = 11223344;
    private ScanCallback callbackDelEscaneo = null;
    private static UsuarioActivity instancia;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificacion;
    private String UUIDSensor="";
    private long ultimoEnvio;
    private final long INTERVALOMEDICION=20000;
    static final String CANAL_ID = "mi_canal";
    static final int NOTIFICACION_ID = 1;
    private TextView lat,leng,sensor;
    private LocationManager locManager;
    private Location loc;
    Button botonLogout;
    List<ScanFilter> listaFilter = new List<ScanFilter>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<ScanFilter> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(ScanFilter scanFilter) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends ScanFilter> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends ScanFilter> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public ScanFilter get(int i) {
            return null;
        }

        @Override
        public ScanFilter set(int i, ScanFilter scanFilter) {
            return null;
        }

        @Override
        public void add(int i, ScanFilter scanFilter) {

        }

        @Override
        public ScanFilter remove(int i) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<ScanFilter> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<ScanFilter> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<ScanFilter> subList(int i, int i1) {
            return null;
        }
    };
    public UsuarioActivity() {
        instancia = this;
    }
    TextView textoBluetooth;

    //Instancia de MainActivity para llamar a funciones de esta clase desde el servicio
    public static UsuarioActivity getInstancia() {
        return instancia;
    }
    // ---------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        String datoQR = result.getContents();
        String datoProcesado="";

        if(datoQR.startsWith("http://")){
            datoProcesado = datoQR.substring(7);

        }else{
            datoProcesado = datoQR;
        }

        //textoDatoQR = findViewById(R.id.datoQR);
        //textoDatoQR.setText(datoQR + "|||" + datoProcesado);


        botonArrancarServicioPulsado(datoProcesado);
        UUIDSensor = datoProcesado;


    }
    /**
     * botonArrancarServicioPulsado simplemente arranca el servicio cuando se pulsa un boton. View v->botonArrancarServicioPulsado()
     *
     * @param v llamar al onclick.
     *
     * No devuelve ningún valor.
     */
    public void botonArrancarServicioPulsado( View v ) {
        Log.d(ETIQUETA_LOG, " boton arrancar servicio Pulsado");

        if (this.elIntentDelServicio != null) {
            // ya estaba arrancado
            return;
        }


        Log.d(ETIQUETA_LOG, " MainActivity.constructor : voy a arrancar el servicio");

        this.elIntentDelServicio = new Intent(this, ServicioEscucharBeacons.class);

        this.elIntentDelServicio.putExtra("tiempoDeEspera", (long) 5000);
        startService(this.elIntentDelServicio);

    } // ()
    public void botonLogout(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        preferences.edit().clear().commit();

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void botonArrancarServicioPulsado(String uuid) {
        Log.d(ETIQUETA_LOG, " boton arrancar servicio Pulsado");

        if (this.elIntentDelServicio != null) {
            // ya estaba arrancado
            return;
        }

        Log.d(ETIQUETA_LOG, " MainActivity.constructor : voy a arrancar el servicio");

        this.elIntentDelServicio = new Intent(this, ServicioEscucharBeacons.class);

        this.elIntentDelServicio.putExtra("tiempoDeEspera", (long) 5000);
        this.elIntentDelServicio.putExtra("UUID", uuid);
        startService(this.elIntentDelServicio);

    } // ()


    public void botonEscanearQrPulsado(View v){
        new IntentIntegrator(this).initiateScan();
    }
    /**
     * botonDetenerServicioPulsado simplemente arranca el servicio cuando se pulsa un boton. View v->botonDetenerServicioPulsado()
     *
     * @param v llamar al onclick-
     *
     * No devuelve ningún valor.
     */
    public void botonDetenerServicioPulsado( View v ) {

        if ( this.elIntentDelServicio == null ) {
            // no estaba arrancado
            return;
        }

        stopService( this.elIntentDelServicio );

        this.elIntentDelServicio = null;

        Log.d(ETIQUETA_LOG, " boton detener servicio Pulsado" );


    } // ()
    // ---------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------
// onCreate()
// ---------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        notificacion = new NotificationCompat.Builder(UsuarioActivity.this, CANAL_ID);

        long instanteActual=System.currentTimeMillis();


        lat=findViewById(R.id.latitud);
        leng=findViewById(R.id.longitud);
        ActivityCompat.requestPermissions(UsuarioActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            lat.setText("No se han definido los permisos necesarios.");
            leng.setText("");

            return;
        }else{
            Log.d("hola","funcionan cosas");
        }
        //SACAMOS LA LOCALIZACIÓN DEL TELEFONO MOVIL
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lat.setText(String.valueOf(loc.getLatitude()));
        leng.setText(String.valueOf(loc.getLongitude()));

        if((instanteActual-this.ultimoEnvio) >= INTERVALOMEDICION){
            enviarNotificacion();
            //
        }

        botonLogout=findViewById(R.id.buttonlogout);
        botonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Log.d(ETIQUETA_LOG, " MainActivity.constructor : empieza");
        textoBluetooth = findViewById(R.id.resultadoBluetooth);
        textoBluetooth.setText("");
        //preparamos todo para detectar bluetooth
        inicializarBlueTooth();

        Log.d(ETIQUETA_LOG, " MainActivity.constructor : acaba");

    }
    /**
     * solicitarPermiso si el permiso no está concedido lo solicita al iniciar la aplicacion. solicitarPermiso()
     *
     *
     *  No devuelve ningun valor
     */
    private void solicitarPermisos(){
        //Solicitar permiso bluetooth para detectar beacons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//version minima Android M
            //chequea permisos de Android M
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Esta APP necesita permisos de localización.");
                builder.setMessage("Porfavor, otorgue los permisos para poder detectar beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }
    /**
     * onRequestPermissionsResult Una vez obtenido el resultado de la peticion de los permisos informamos del estado,
     * si se han permitido o no.
     * permissions[]:String, requestCode:int,grantResults:int[]->onRequestPermissionsResult()
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     *
     *  No devuelve ningun valor
     */
    //
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISOBLUETOOTH", "Localización permitida");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Funcionalidad limitada");
                    builder.setMessage("Como el acceso a la localización no ha sido otorgado, esta app no podra detectar beacons en segundo plano.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
            }
        }
    }//OnRequestPermission
//------------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------------
    //METODOS BLUETOOTH
//------------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * inicializarBluetooth al iniciar la app se activa el bluetooth, inicializarBlueeTooth()
     *
     *  No devuelve ningun valor
     */
    private void inicializarBlueTooth() {
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos adaptador BT ");

        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

        elEscanner = bta.getBluetoothLeScanner();

        //solicito permisos por si no los tengo
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            solicitarPermisos();
        }

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitamos adaptador BT ");
        bta.enable();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled() );

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState() );

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");

        this.elEscanner = bta.getBluetoothLeScanner();

        if ( this.elEscanner == null ) {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Socorro: NO hemos obtenido escaner btle  !!!!");

        }

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): voy a perdir permisos (si no los tuviera) !!!!");

        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(
                    UsuarioActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION},
                    CODIGO_PETICION_PERMISOS);
        }
        else {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): parece que YA tengo los permisos necesarios !!!!");

        }

    } // ()
    /**
     * buscarEsteDispositivoBTLE busca nuestro dispositivo con el sensor para luego mostrar los datos, dispositivoBuscado:int->buscarEsteDispositivoBTLE()
     *
     * @param dispositivoBuscado dispositivo que queremos buscar.
     *  No devuelve ningun valor
     */
    public void buscarEsteDispositivoBTLE(final String dispositivoBuscado) {
        Log.d(ETIQUETA_LOG, " buscarEsteDispositivoBTLE(): empieza ");

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): instalamos scan callback ");


        // super.onScanResult(ScanSettings.SCAN_MODE_LOW_LATENCY, result); para ahorro de energía

        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult resultado) {
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanResult() ");

                mostrarInformacionDispositivoBTLE(resultado);
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onBatchScanResults() ");

            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanFailed() ");

            }
        };

        ScanFilter sf = new ScanFilter.Builder().setDeviceName(dispositivoBuscado).build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            settings = new ScanSettings.Builder ()
                    .setScanMode (ScanSettings.SCAN_MODE_BALANCED)
                    .setCallbackType (ScanSettings.CALLBACK_TYPE_FIRST_MATCH

                    )
                    .build ();
        }
        listaFilter.add(sf);
        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + dispositivoBuscado);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            solicitarPermisos();

        }
        //this.elEscanner.startScan(listaFilter, settings, this.callbackDelEscaneo );
        this.elEscanner.startScan( this.callbackDelEscaneo );
    } // ()
    /**
     * detenerBusquedaDispositivosBTLE detener la busqueda de los dispositivos, detenerBusquedaDispositivosBTLE()
     *
     *  No devuelve ningun valor
     */
    public void detenerBusquedaDispositivosBTLE() {

        if (this.callbackDelEscaneo == null) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            solicitarPermisos();

        }
        this.elEscanner.stopScan(this.callbackDelEscaneo);
        this.callbackDelEscaneo = null;

    } // ()
    /**
     * mostrarInformacionDispositivoBTLE muestra los datos de NUESTRO sensor y además llama a GuardarMedidaEnBD
     * para hacer un post de los datos, resultado:ScanResult->mostrarInformacionDispositivoBTLE()
     *
     * @param resultado los datos que saca del sensor.
     *  No devuelve ningun valor
     */
    private void mostrarInformacionDispositivoBTLE(ScanResult resultado) {

        BluetoothDevice bluetoothDevice = resultado.getDevice();
        byte[] bytes = resultado.getScanRecord().getBytes();
        int rssi = resultado.getRssi();



        TramaIBeacon tib = new TramaIBeacon(bytes);


        if(Utilidades.bytesToString(tib.getUUID()).equals("AHETERTECH_GRUP3")){
            Log.d(ETIQUETA_LOG, " ****************************************************");
            Log.d(ETIQUETA_LOG, " ****** DISPOSITIVO DETECTADO BTLE ****************** ");
            Log.d(ETIQUETA_LOG, " ****************************************************");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                solicitarPermisos();

            }
            Log.d(ETIQUETA_LOG, " nombre = " + bluetoothDevice.getName());
            Log.d(ETIQUETA_LOG, " toString = " + bluetoothDevice.toString());

            Log.d(ETIQUETA_LOG, " dirección = " + bluetoothDevice.getAddress());
            Log.d(ETIQUETA_LOG, " rssi = " + rssi);

            Log.d(ETIQUETA_LOG, " bytes = " + new String(bytes));
            Log.d(ETIQUETA_LOG, " bytes (" + bytes.length + ") = " + Utilidades.bytesToHexString(bytes));
            //Pasamos los datos de major y minor a la funcion para que haga el POST
            LogicaFake.GuardarMedidaEnBD(String.valueOf(Utilidades.bytesToInt(tib.getMajor())) ,
                    String.valueOf(Utilidades.bytesToInt(tib.getMinor())),"https://jmarzoz.upv.edu.es/src/ServidorLogica/gestionMediciones.php",
                    Utilidades.bytesToString(tib.getUUID()));
            this.ultimoEnvio=System.currentTimeMillis();
            if (Utilidades.bytesToInt(tib.getMinor())<60){
                enviarNotificacionDatosPeligrosos();
            }
            LogicaFake.estadoNodo("https://jmarzoz.upv.edu.es/src/ServidorLogica/gestionMediciones.php");

            textoBluetooth.setText("Nombre: "+Utilidades.bytesToString(tib.getUUID())+"Major: " + Utilidades.bytesToInt(tib.getMajor()) + ", Minor: " + Utilidades.bytesToInt(tib.getMinor()));
            //------------------------------------------------
            //LOG
            //------------------------------------------------
            Log.d(ETIQUETA_LOG, " ----------------------------------------------------");
            Log.d(ETIQUETA_LOG, " prefijo  = " + Utilidades.bytesToHexString(tib.getPrefijo()));
            Log.d(ETIQUETA_LOG, "          advFlags = " + Utilidades.bytesToHexString(tib.getAdvFlags()));
            Log.d(ETIQUETA_LOG, "          advHeader = " + Utilidades.bytesToHexString(tib.getAdvHeader()));
            Log.d(ETIQUETA_LOG, "          companyID = " + Utilidades.bytesToHexString(tib.getCompanyID()));
            Log.d(ETIQUETA_LOG, "          iBeacon type = " + Integer.toHexString(tib.getiBeaconType()));
            Log.d(ETIQUETA_LOG, "          iBeacon length 0x = " + Integer.toHexString(tib.getiBeaconLength()) + " ( "
                    + tib.getiBeaconLength() + " ) ");
            Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToHexString(tib.getUUID()));
            Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToString(tib.getUUID()));
            Log.d(ETIQUETA_LOG, " major  = " + Utilidades.bytesToHexString(tib.getMajor()) + "( "
                    + Utilidades.bytesToInt(tib.getMajor()) + " ) ");
            Log.d(ETIQUETA_LOG, " minor  = " + Utilidades.bytesToHexString(tib.getMinor()) + "( "
                    + Utilidades.bytesToInt(tib.getMinor()) + " ) ");
            Log.d(ETIQUETA_LOG, " txPower  = " + Integer.toHexString(tib.getTxPower()) + " ( " + tib.getTxPower() + " )");
            Log.d(ETIQUETA_LOG, " ****************************************************");
        }
        sensor=findViewById(R.id.sensor);
        if (rssi>-75){
            sensor.setText("Esta cerca");
        }else if(rssi<-95) {
            sensor.setText("Esta lejos");
        }

    } // ()

    /**
     * enviarNotificacion envia una notificación cuando el sensor da problemas
     *  enviarNotificacion()
     *
     *  No devuelve ningun valor
     */
    public void enviarNotificacion(){
        notificacion.setSmallIcon(R.mipmap.ic_launcher);
        notificacion.setContentTitle("Aether");
        notificacion.setContentText("Sensor desactivado");

        notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CANAL_ID, "Mis Notificaciones",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion del canal");
            notificationManager.createNotificationChannel(notificationChannel);
        }


        notificationManager.notify(NOTIFICACION_ID, notificacion.build());


    }
    public void enviarNotificacionDatosPeligrosos(){
        notificacion.setSmallIcon(R.mipmap.ic_launcher);
        notificacion.setContentTitle("Aether");
        notificacion.setContentText("TU SENSOR DETECTA UNA MEDIDA PELIGROSA");

        notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CANAL_ID, "Mis Notificaciones",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion del canal");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(NOTIFICACION_ID, notificacion.build());
    }
    public void botonIrId(View view) {
        Intent intent = new Intent(this, VincularActivity.class);
        startActivity(intent);
    }

    public void botonMandarDatos1(View view) {
        LogicaFake.GuardarMedidaEnBD(String.valueOf("1"
        ), "1", "https://jmarzoz.upv.edu.es/src/ServidorLogica/gestionMediciones.php", "SENSOR1");
    }
    public void botonMandarDatos2(View view) {
        LogicaFake.GuardarMedidaEnBD(String.valueOf("2"
        ), "2", "https://jmarzoz.upv.edu.es/src/ServidorLogica/gestionMediciones.php", "SENSOR2");
    }
    public void botonMandarDatos3(View view) {
        LogicaFake.GuardarMedidaEnBD(String.valueOf("3"
        ), "3", "https://jmarzoz.upv.edu.es/src/ServidorLogica/gestionMediciones.php", "SENSOR3");
    }
}