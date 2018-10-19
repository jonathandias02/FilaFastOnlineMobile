package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class SolicitarAtendimento extends AppCompatActivity {

    private Spinner spinner_servico;
    private ArrayList<ServicosClass> lista = new ArrayList<ServicosClass>();
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";
    private Integer id;
    private String nomebd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_atendimento);
        spinner_servico = (Spinner) findViewById(R.id.spinner_servicos);

        Intent intent = getIntent();
        if(intent != null){
            Bundle dado = intent.getExtras();
            if(dado != null){
                id = dado.getInt("idFila");
                nomebd = dado.getString("nomebd");
            }
        }
        ServicosClass selecionar = new ServicosClass(0, "Selecionar");
        lista.add(selecionar);
        listaServicos(id, nomebd);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lista);
        spinner_servico.setAdapter(adapter);

        ServicosClass servico = ((ServicosClass)spinner_servico.getSelectedItem());
        //Toast.makeText(SolicitarAtendimento.this, "O id é :" + servico.getId(), Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void listaServicos(Integer id, String nome){

        String url = HOST + "servicos.php";

        Ion.with(SolicitarAtendimento.this)
                .load(url)
                .setBodyParameter("idFila", String.valueOf(id))
                .setBodyParameter("nomebd", nome)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        try{

                            for(int i = 0; i < result.size(); i++){

                                JsonObject obj = result.get(i).getAsJsonObject();
                                ServicosClass servico = new ServicosClass(obj.get("id").getAsInt(), obj.get("nomeServico").getAsString());

                                lista.add(servico);

                            }

                        }catch (Exception erro){
                            Toast.makeText(SolicitarAtendimento.this, "Ocorreu um erro ao carregar servicos!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
}
