package com.atendimentossolutions.filafastonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MeusAtendimentos extends AppCompatActivity {

    private ListView listaAtendimentos;
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";
    private ItemMeusAtendimentosAdapter itemMeusAtendimentosAdapter;
    private List<ItemMeusAtendimentos> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_atendimentos);

        listaAtendimentos = (ListView) findViewById(R.id.listaAtendimentos);

        lista = new ArrayList<ItemMeusAtendimentos>();
        itemMeusAtendimentosAdapter = new ItemMeusAtendimentosAdapter(MeusAtendimentos.this, lista);

        listaAtendimentos.setAdapter(itemMeusAtendimentosAdapter);

        listarAtendimentos();

    }

    private void listarAtendimentos(){

        String url = HOST + "meusAtendimentos.php";
        Globals globals = (Globals) getApplicationContext();
        Ion.with(MeusAtendimentos.this)
                .load(url)
                .setBodyParameter("nomebd", globals.getNomebd())
                .setBodyParameter("email", globals.getEmail())
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        try{

                            for(int i = 0; i < result.size(); i++){

                                JsonObject obj = result.get(i).getAsJsonObject();
                                ItemMeusAtendimentos item = new ItemMeusAtendimentos();
                                item.setSenha(obj.get("sigla").getAsString()+obj.get("numero").getAsString());
                                item.setDataAtendimento(obj.get("dataAtendimento").getAsString());
                                item.setServico(obj.get("servico").getAsString());
                                item.setAtendente(obj.get("atendente").getAsString());

                                lista.add(item);

                            }

                            itemMeusAtendimentosAdapter.notifyDataSetChanged();

                        }catch (Exception erro){
                            Toast.makeText(MeusAtendimentos.this, "Ocorreu um erro ao carregar empresas!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
