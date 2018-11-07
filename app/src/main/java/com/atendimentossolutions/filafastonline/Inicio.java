package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";
    private EmpresaAdapter empresaAdapter;
    private List<Empresa> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        listview_empresas = (ListView) findViewById(R.id.listview_empresas);

        lista = new ArrayList<Empresa>();
        empresaAdapter = new EmpresaAdapter(Inicio.this, lista);

        listview_empresas.setAdapter(empresaAdapter);

        listaEmpresas();

        listview_empresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Empresa empresa = (Empresa) adapterView.getAdapter().getItem(i);
                String nomebd = empresa.getNomebd();
                Globals globals = (Globals) getApplicationContext();
                globals.setNomebd(nomebd);
                globals.setCnpj(empresa.getCnpj());
                globals.setNomeEmpresa(empresa.getEmpresa());
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
                                Empresa empresa = new Empresa();
                                empresa.setCnpj(obj.get("CNPJ").getAsString());
                                empresa.setEmpresa(obj.get("EMPRESA").getAsString());
                                empresa.setEndereco(obj.get("ENDERECO").getAsString());
                                empresa.setNomebd(obj.get("BANCODEDADOS").getAsString());

                                lista.add(empresa);

                            }

                            empresaAdapter.notifyDataSetChanged();

                        }catch (Exception erro){
                            Toast.makeText(Inicio.this, "Ocorreu um erro ao carregar empresas!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuempresa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.sair){
            Intent intent = new Intent(Inicio.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
