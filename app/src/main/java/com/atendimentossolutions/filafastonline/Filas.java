package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class Filas extends AppCompatActivity {

    private ListView listview_filas;
    private FilaAdapter filaAdapter;
    private List<Fila> lista;
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";
    private String nomebd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filas);

        listview_filas = (ListView) findViewById(R.id.listview_filas);
        lista = new ArrayList<Fila>();
        filaAdapter = new FilaAdapter(Filas.this, lista);

        listview_filas.setAdapter(filaAdapter);

        Intent intent = getIntent();
        if(intent != null){
            Bundle dado = intent.getExtras();
            if(dado != null){
                nomebd = dado.getString("banco");
                listarFilas(nomebd);
            }
        }

        listview_filas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent principal = new Intent(Filas.this, TelaPrincipal.class);
                startActivity(principal);
            }
        });

    }

    public void listarFilas(String banco){
        String url = HOST + "filas.php";

        Ion.with(Filas.this)
                .load(url)
                .setBodyParameter("nomebd", banco)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        try{
                            for(int i = 0; i < result.size(); i++){
                                JsonObject obj = result.get(i).getAsJsonObject();
                                Fila f = new Fila();
                                f.setNome(obj.get("NOME").getAsString());

                                lista.add(f);

                            }

                            filaAdapter.notifyDataSetChanged();

                        }catch (Exception erro){
                            Toast.makeText(Filas.this, "Ocorreu um erro ao carregar filas!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

}
