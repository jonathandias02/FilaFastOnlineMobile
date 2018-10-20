package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class TelaPrincipal extends AppCompatActivity {

    private int id;
    private TextView ultimaSenha, ultimoGuiche, senha3, senha2, senha1, guiche3, guiche2, guiche1,
    suaSenha, pessoas, preferencia;
    private Button solicitarSenha;
    private String nomebd;
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        ultimaSenha = (TextView) findViewById(R.id.ultimaSenha);
        ultimoGuiche = (TextView) findViewById(R.id.ultimoGuiche);
        senha3 = (TextView) findViewById(R.id.senha3);
        senha2 = (TextView) findViewById(R.id.senha2);
        senha1 = (TextView) findViewById(R.id.senha1);
        guiche3 = (TextView) findViewById(R.id.guiche3);
        guiche2 = (TextView) findViewById(R.id.guiche2);
        guiche1 = (TextView) findViewById(R.id.guiche1);
        suaSenha = (TextView) findViewById(R.id.suaSenha);
        pessoas = (TextView) findViewById(R.id.pessoas);
        preferencia = (TextView) findViewById(R.id.preferencia);
        solicitarSenha = (Button) findViewById(R.id.solicitarSenha);

        Intent intent = getIntent();
        if(intent != null){
            Bundle dado = intent.getExtras();
            if(dado != null){
                id = dado.getInt("id");
                nomebd = dado.getString("nomebd");
            }
        }

        solicitarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(TelaPrincipal.this, SolicitarAtendimento.class);
                intent.putExtra("idFila", id);
                intent.putExtra("nomebd", nomebd);
                startActivity(intent);
            }
        });

        String url = HOST + "senhas.php";

        Ion.with(TelaPrincipal.this)
                .load(url)
                .setBodyParameter("nomebd", nomebd)
                .setBodyParameter("idFila", String.valueOf(id))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try{
                            if(result.size() == 1) {
                                JsonObject uSenha = result.get(0).getAsJsonObject();
                                ultimaSenha.setText(uSenha.get("sigla").getAsString() + uSenha.get("numero").getAsString());
                                ultimoGuiche.setText(uSenha.get("guiche").getAsString());
                                if(uSenha.get("t_preferencia_id").getAsInt() == 1){
                                    preferencia.setText("Normal");
                                }else{
                                    preferencia.setText("Preferencial");
                                }
                            }else if (result.size() == 2){
                                JsonObject uSenha = result.get(0).getAsJsonObject();
                                ultimaSenha.setText(uSenha.get("sigla").getAsString() + uSenha.get("numero").getAsString());
                                ultimoGuiche.setText(uSenha.get("guiche").getAsString());
                                if(uSenha.get("t_preferencia_id").getAsInt() == 1){
                                    preferencia.setText("Normal");
                                }else{
                                    preferencia.setText("Preferencial");
                                }
                                JsonObject s3 = result.get(1).getAsJsonObject();
                                senha1.setText(s3.get("sigla").getAsString() + s3.get("numero").getAsString());
                                guiche1.setText(s3.get("guiche").getAsString());
                            }else if (result.size() == 3){
                                JsonObject uSenha = result.get(0).getAsJsonObject();
                                ultimaSenha.setText(uSenha.get("sigla").getAsString() + uSenha.get("numero").getAsString());
                                ultimoGuiche.setText(uSenha.get("guiche").getAsString());
                                if(uSenha.get("t_preferencia_id").getAsInt() == 1){
                                    preferencia.setText("Normal");
                                }else{
                                    preferencia.setText("Preferencial");
                                }
                                JsonObject s3 = result.get(1).getAsJsonObject();
                                senha1.setText(s3.get("sigla").getAsString() + s3.get("numero").getAsString());
                                guiche1.setText(s3.get("guiche").getAsString());
                                JsonObject s2 = result.get(2).getAsJsonObject();
                                senha2.setText(s2.get("sigla").getAsString() + s2.get("numero").getAsString());
                                guiche2.setText(s2.get("guiche").getAsString());
                            }else if(result.size() == 4){
                                JsonObject uSenha = result.get(0).getAsJsonObject();
                                ultimaSenha.setText(uSenha.get("sigla").getAsString() + uSenha.get("numero").getAsString());
                                ultimoGuiche.setText(uSenha.get("guiche").getAsString());
                                if(uSenha.get("t_preferencia_id").getAsInt() == 1){
                                    preferencia.setText("Normal");
                                }else{
                                    preferencia.setText("Preferencial");
                                }
                                JsonObject s3 = result.get(1).getAsJsonObject();
                                senha1.setText(s3.get("sigla").getAsString() + s3.get("numero").getAsString());
                                guiche1.setText(s3.get("guiche").getAsString());
                                JsonObject s2 = result.get(2).getAsJsonObject();
                                senha2.setText(s2.get("sigla").getAsString() + s2.get("numero").getAsString());
                                guiche2.setText(s2.get("guiche").getAsString());
                                JsonObject s1 = result.get(3).getAsJsonObject();
                                senha3.setText(s1.get("sigla").getAsString() + s1.get("numero").getAsString());
                                guiche3.setText(s1.get("guiche").getAsString());
                            }else{
                                Toast.makeText(TelaPrincipal.this, "Nenhum atendimento realizado até o momento!", Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception erro){
                            Toast.makeText(TelaPrincipal.this, "Ocorreu um erro ao carregar senhas!", Toast.LENGTH_LONG).show();
                        };
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
