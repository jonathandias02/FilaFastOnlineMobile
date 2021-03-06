package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class Avaliar extends AppCompatActivity {

    Button btn_avaliar;
    RadioGroup radioAvaliacao;
    Integer idSenha, idFila;
    String radioSelecionado;
    Integer valor;
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar);

        radioAvaliacao = (RadioGroup) findViewById(R.id.radioAvaliacao);
        btn_avaliar = (Button) findViewById(R.id.btn_avaliar);
        final Globals globals = (Globals) getApplicationContext();

        radioAvaliacao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioSelecionado = ((RadioButton)findViewById(radioAvaliacao.getCheckedRadioButtonId())).getText().toString();
            }
        });

        Intent intent = getIntent();
        if(intent != null){
            Bundle dado = intent.getExtras();
            if(dado != null){
                idSenha = dado.getInt("idSenha");
                idFila = dado.getInt("idFila");
            }
        }

        btn_avaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioSelecionado.equals("Péssimo")){
                    valor = 1;
                }else if(radioSelecionado.equals("Ruim")){
                    valor = 2;
                }else if(radioSelecionado.equals("Bom")){
                    valor = 3;
                }else if(radioSelecionado.equals("Muito Bom")){
                    valor = 4;
                }else if(radioSelecionado.equals("Ótimo")){
                    valor = 5;
                }
                avaliar(globals.getNomebd(), idSenha, valor, globals.getCnpj(), globals.getId(), radioSelecionado);
            }
        });
    }

    @Override
    protected void onPause() {
        Globals banco = (Globals) getApplicationContext();
        super.onPause();
        naoAvaliar(banco.getNomebd(), idSenha);
        Intent intent = new Intent(Avaliar.this, TelaPrincipal.class);
        intent.putExtra("id", idFila);
        intent.putExtra("nomebd", banco.getNomebd());
        startActivity(intent);
        finish();
    }

    private void avaliar(String nomebd, int idSenha, int nota, String cnpj, int idUsuario, String avaliacao){
        String url = HOST + "avaliar.php";

        Ion.with(Avaliar.this)
                .load(url)
                .setBodyParameter("nomebd", nomebd)
                .setBodyParameter("idSenha", String.valueOf(idSenha))
                .setBodyParameter("nota", String.valueOf(nota))
                .setBodyParameter("cnpj", cnpj)
                .setBodyParameter("idUsuario", String.valueOf(idUsuario))
                .setBodyParameter("avaliacao", avaliacao)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try{
                            if(result.get("avaliacao").getAsString().equals("sucesso")){
                                Globals bd = (Globals) getApplicationContext();
                                Intent intent = new Intent(Avaliar.this, TelaPrincipal.class);
                                intent.putExtra("id", idFila);
                                intent.putExtra("nomebd", bd.getNomebd());
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Avaliar.this, "Não foi possivel avaliar!", Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception erro){
                            Toast.makeText(Avaliar.this, "Ocorreu um erro ao avaliar!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    public void naoAvaliar(String nomebd, int idSenha){
        String url = HOST + "naoAvaliar.php";

        Ion.with(Avaliar.this)
                .load(url)
                .setBodyParameter("nomebd", nomebd)
                .setBodyParameter("idSenha", String.valueOf(idSenha))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try{
                            if(result.get("avaliacao").getAsString().equals("sucesso")){

                            }else{
                                Toast.makeText(Avaliar.this, "Não foi possivel avaliar!", Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception erro){
                            Toast.makeText(Avaliar.this, "Ocorreu um erro ao avaliar!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

}
