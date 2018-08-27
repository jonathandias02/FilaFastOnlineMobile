package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class Inicio extends AppCompatActivity {

    private ListView listview_empresas;
    private String HOST = "http://192.168.0.102/LoginApp/";
    private EmpresasAdapter empresasAdapter;
    private List<Empresas> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        listview_empresas = (ListView) findViewById(R.id.listview_empresas);

        lista = new ArrayList<Empresas>();
        empresasAdapter = new EmpresasAdapter(Inicio.this, lista);

        listview_empresas.setAdapter(empresasAdapter);

        listaEmpresas();

        listview_empresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Empresas empresa = (Empresas) adapterView.getAdapter().getItem(i);
                String nomebd = empresa.getNomebd();
                Intent filas = new Intent(Inicio.this, Filas.class);
                filas.putExtra("banco", nomebd);
                startActivity(filas);
            }
        });

    }

    private void listaEmpresas(){

        String url = HOST + "listar.php";

        Ion.with(Inicio.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        try{

                            for(int i = 0; i < result.size(); i++){

                                JsonObject obj = result.get(i).getAsJsonObject();
                                Empresas empresa = new Empresas();
                                empresa.setEmpresa(obj.get("EMPRESA").getAsString());
                                empresa.setEndereco(obj.get("ENDERECO").getAsString());
                                empresa.setNomebd(obj.get("BANCODEDADOS").getAsString());

                                lista.add(empresa);

                            }

                            empresasAdapter.notifyDataSetChanged();

                        }catch (Exception erro){
                            Toast.makeText(Inicio.this, "Ocorreu um erro ao carregar empresas!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

}
