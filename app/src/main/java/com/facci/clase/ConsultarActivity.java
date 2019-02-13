package com.facci.clase;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facci.clase.Adaptadores.AdaptadorProductos;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import negocio.singletonDatos;

public class ConsultarActivity extends AppCompatActivity {
    ArrayList<String> lista_productos;

    //Instancia los recycler y el adaptador
    private RecyclerView mRecyclerView;
    private AdaptadorProductos adaptador;
    Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);
        //esta es la url
        String server_URL="http://192.168.1.6:80/todos";
        contexto= this;

        //Declara los valores de los elementos
        lista_productos= new ArrayList<String>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_productos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Esto
        StringRequest stringRequest=new StringRequest(Request.Method.GET, server_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        //Obtiene los valores del servidor
                        JsonParser parser = new JsonParser();
                        JsonElement elementObject = parser.parse(result);
                        //Convierte la respuesta en array
                        JsonArray hijos_obtenidos=elementObject.getAsJsonArray();
                        for (int i=0; i < hijos_obtenidos.size(); i++){

                            //Guarda los nombres de productos en una lista
                            String nombre=hijos_obtenidos.get(i).getAsJsonObject().get("Nombre").toString();
                            lista_productos.add(nombre);
                        }
                        //Notifica al adaptador que hay elementos en la lista
                        adaptador.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ConsultarActivity.this, "Error al obtener los productos", Toast.LENGTH_SHORT).show();
            }
        }
        ){

        };
        singletonDatos.getInstancia(getApplicationContext()).addToRequest(stringRequest);
        adaptador= new AdaptadorProductos(lista_productos, getApplicationContext());
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

                builder.setTitle("Atencion");
                builder.setMessage("¿Qué desea realizar?");

                builder.setPositiveButton("Detalles", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String url_detalles="http://192.168.1.6:80/detalles";
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, url_detalles,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String result) {

                                        //Obtiene los valores del servidor
                                        JsonParser parser = new JsonParser();
                                        JsonElement elementObject = parser.parse(result);
                                        //Convierte la respuesta en array
                                        JsonArray productos_obt=elementObject.getAsJsonArray();
                                        String detalles="";
                                        for (int i=0; i < productos_obt.size(); i++){
                                            //Guarda los nombres de productos en una lista
                                            detalles+="Nombre: "+productos_obt.get(i).getAsJsonObject().get("Nombre").toString()+"\n";
                                            detalles+="Precio: $"+ productos_obt.get(i).getAsJsonObject().get("Precio").toString()+"\n";
                                            detalles+="Existencia: "+ productos_obt.get(i).getAsJsonObject().get("Existencia").toString()+"\n";
                                        }
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(contexto);

                                        builder1.setMessage(detalles);
                                        AlertDialog dialog1 = builder1.create();
                                        dialog1.show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(ConsultarActivity.this, "No se pudo ver detalles", Toast.LENGTH_SHORT).show();
                            }
                        }
                        ){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map <String,String> params =new HashMap<String, String >();
                                int posicion= mRecyclerView.getChildAdapterPosition(v);
                                params.put("Nombre", lista_productos.get(posicion));
                                return params;
                            }
                        };
                        singletonDatos.getInstancia(getApplicationContext()).addToRequest(stringRequest);
                    }
                });
                builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String url_eliminar="http://192.168.1.6:80/eliminacion";
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, url_eliminar,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String result) {
                                        Toast.makeText(ConsultarActivity.this, "Producto elimiando con éxito", Toast.LENGTH_SHORT).show();
                                        int posicion= mRecyclerView.getChildAdapterPosition(v);
                                        lista_productos.remove(posicion);
                                        adaptador.notifyDataSetChanged();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(ConsultarActivity.this, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                            }
                        }
                        ){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map <String,String> params =new HashMap<String, String >();
                                int posicion= mRecyclerView.getChildAdapterPosition(v);
                                params.put("Nombre", lista_productos.get(posicion));
                                return params;
                            }
                        };
                        singletonDatos.getInstancia(getApplicationContext()).addToRequest(stringRequest);

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        mRecyclerView.setAdapter(adaptador);
    }
}
