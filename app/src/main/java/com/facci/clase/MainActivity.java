package com.facci.clase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent llamado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void ingresar(View v){
        llamado= new Intent(MainActivity.this, IngresarActivity.class);
        startActivity(llamado);
    }

    public void consultar(View v){
        llamado= new Intent(MainActivity.this, ConsultarActivity.class);
        startActivity(llamado);
    }

    public void modificar(View v){
        llamado= new Intent(MainActivity.this, ModifcarActivity.class);
        startActivity(llamado);
    }

    public void eliminar(View v){
        llamado= new Intent(MainActivity.this, EliminarActivity.class);
        startActivity(llamado);
    }
}
