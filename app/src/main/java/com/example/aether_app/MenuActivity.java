package com.example.aether_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aether_app.databinding.ActivityMenuBinding;
import com.google.zxing.integration.android.IntentIntegrator;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        navigationView.setItemIconTintList(null);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    public void botonLogout(View view){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        preferences.edit().clear().commit();

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);

        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu, menu);
        return true;*/
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    //funcion que haga que un boton vaya a otra pantalla
    public void modificarNombre(View view) {
        Intent intent = new Intent(getApplicationContext(), editarNombreActivity.class);
        startActivity(intent);
    }
    public void modificarContrasenya(View view) {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        String correo = preferences.getString("usuario","");
        LogicaFake.cambiarContrasenya("https://jmarzoz.upv.edu.es/src/ServidorLogica/enviarCorreoCambioContrasenya.php",
                correo);
        Toast.makeText(getApplicationContext(), "Se ha eviado un correo para el cambio", Toast.LENGTH_SHORT).show();

    }
    public void irALayautDeMierda(View view) {
        Intent intent = new Intent(getApplicationContext(), UsuarioActivity.class);
        startActivity(intent);
    }

    public void dirigirInformacionGasesWeb(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jmarzoz.upv.edu.es/src/ux/bienvenida.php"));
        startActivity(browserIntent);
    }
}