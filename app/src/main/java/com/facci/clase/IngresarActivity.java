package com.facci.clase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import negocio.Validaciones;
import negocio.singletonDatos;

public class IngresarActivity extends AppCompatActivity {
    EditText txt_cod, txt_nom, txt_prec, txt_exist;
    String server_URL="http://10.22.25.66:80/guardar-producto";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);
        txt_cod = (EditText)findViewById(R.id.txt_codigo);
        txt_nom = (EditText)findViewById(R.id.txt_nombre);
        txt_prec = (EditText)findViewById(R.id.txt_precio);
        txt_exist = (EditText)findViewById(R.id.txt_existencia);
    }
    public  void  guardar (View v){
        final String codigo,nombre,precio,existencia;

        codigo=txt_cod.getText().toString();
        nombre=txt_nom.getText().toString();
        precio=txt_prec.getText().toString();
        existencia=txt_exist.getText().toString();
        EditText[]campos=new EditText[]{txt_cod,txt_exist,txt_prec,txt_nom};
       if(Validaciones.vacio(campos)){
         //Codigo Inmutable
          StringRequest stringRequest=new StringRequest(Request.Method.POST, server_URL,
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String result) {
                        //Aqui
                           Toast.makeText(IngresarActivity.this, "Se Registro exitosamente el producto", Toast.LENGTH_SHORT).show();

                       }
                   }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   error.printStackTrace();
                   //Aqui Tambien
                   Toast.makeText(IngresarActivity.this, "ocurrio un error al registrar el producto", Toast.LENGTH_SHORT).show();

               }
           }
           ){
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map <String,String> params =new HashMap<String, String >();
                   //Parametros  de los datos que se utilizan
                   params.put("Codigo",codigo);
                   params.put("Nombre",nombre);
                   params.put("Precio",precio);
                   params.put("Existencia",existencia);
                   //arams.put("usuario",usuario);
                   return params;
               }
           };
           singletonDatos.getInstancia(getApplicationContext()).addToRequest(stringRequest);

           //hasta aqui
       }else {
           Toast.makeText(this, "Hay campos sin llenar ", Toast.LENGTH_SHORT).show();
       }

    }


}
