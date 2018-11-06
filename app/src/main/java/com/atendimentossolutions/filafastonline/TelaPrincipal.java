package com.atendimentossolutions.filafastonline;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.zip.Inflater;

public class TelaPrincipal extends AppCompatActivity {

    private int id;
    private TextView ultimaSenha, ultimoGuiche, senha3, senha2, senha1, guiche3, guiche2, guiche1,
    suaSenha, pessoas, preferencia, suaPreferencia, aviso;
    private Button solicitarSenha = null;
    private String nomebd, minhaPreferencia;
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        handler = new Handler();
        handler.post(updateSenhas);

        ultimaSenha = (TextView) findViewById(R.id.ultimaSenha);
        ultimoGuiche = (TextView) findViewById(R.id.ultimoGuiche);
        senha3 = (TextView) findViewById(R.id.senha3);
        senha2 = (TextView) findViewById(R.id.senha2);
        senha1 = (TextView) findViewById(R.id.senha1);
        guiche3 = (TextView) findViewById(R.id.guiche3);
        guiche2 = (TextView) findViewById(R.id.guiche2);
        guiche1 = (TextView) findViewById(R.id.guiche1);
        suaSenha = (TextView) findViewById(R.id.suaSenha);
        suaPreferencia = (TextView) findViewById(R.id.suaPreferencia);
        pessoas = (TextView) findViewById(R.id.pessoas);
        aviso = (TextView) findViewById(R.id.aviso);
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
                if(minhaPreferencia == null){
                    intent.putExtra("minhaSenha", 0);
                }else{
                    intent.putExtra("minhaSenha", 1);
                }
                startActivity(intent);
            }
        });

    }

    private final Runnable updateSenhas = new Runnable() {
        @Override
        public void run() {

            String url = HOST + "senhas.php";
            String urlMinhaSenha = HOST + "minhaSenha.php";

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
                                }
                            }catch (Exception erro){
                                Toast.makeText(TelaPrincipal.this, "Ocorreu um erro ao carregar senhas!", Toast.LENGTH_LONG).show();
                            };
                        }
                    });
            Globals globals = (Globals) getApplicationContext();
            Ion.with(TelaPrincipal.this)
                    .load(urlMinhaSenha)
                    .setBodyParameter("nomebd", nomebd)
                    .setBodyParameter("idFila", String.valueOf(id))
                    .setBodyParameter("email", globals.getEmail())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try{
                                if(result.get("MSENHA").getAsString().equals("SUCESSO") && result.get("situacao").getAsString().equals("Aguardando")){
                                    if(result.get("preferencia").getAsString().equals("1")){
                                        minhaPreferencia = "Normal";
                                    }else {
                                        minhaPreferencia = "Preferencial";
                                    }
                                    if(result.get("pessoasFrente").getAsString().equals("0")){
                                        pessoas.setText("Não há pessoas à sua frente.");
                                    }else {
                                        pessoas.setText(result.get("pessoasFrente").getAsString() + " Pessoa(s) à sua frente.");
                                    }
                                    if(result.get("pessoasFrente").getAsInt() <= 5){
                                        aviso.setText("Dirija-se ao local de atendimento!");
                                    }else{
                                        aviso.setText("");
                                    }
                                    suaSenha.setText("Sua senha: "+result.get("sigla").getAsString()+result.get("numero").getAsString());
                                    suaPreferencia.setText("Atendimento "+minhaPreferencia+".");

                                }else if(result.get("MSENHA").getAsString().equals("SUCESSO") && result.get("situacao").getAsString().equals("Chamada")){
                                    if(result.get("preferencia").getAsString().equals("1")){
                                        minhaPreferencia = "Normal";
                                    }else {
                                        minhaPreferencia = "Preferencial";
                                    }
                                    pessoas.setText("");
                                    suaSenha.setText("Sua senha: "+result.get("sigla").getAsString()+result.get("numero").getAsString());
                                    suaPreferencia.setText("Dirija-se ao Guichê "+result.get("guiche").getAsString());
                                    suaPreferencia.setTextColor(Color.parseColor("#FF0000"));
                                    aviso.setText("");
                                }else if(result.get("MSENHA").getAsString().equals("SUCESSO") && result.get("situacao").getAsString().equals("Atendida")){
                                    Intent intent = new Intent(TelaPrincipal.this, Avaliar.class);
                                    intent.putExtra("idFila", id);
                                    intent.putExtra("nomebd", nomebd);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    pessoas.setText("");
                                    suaSenha.setText("Não está aguardando atendimento.");
                                    suaPreferencia.setTextColor(Color.parseColor("#000000"));
                                    suaPreferencia.setText("");
                                    minhaPreferencia = null;
                                    aviso.setText("");
                                }
                            }catch (Exception erro){
                                Toast.makeText(TelaPrincipal.this, "Ocorreu um erro ao carregar Minha Senha!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

            handler.postDelayed(updateSenhas, 3000);
        }
    };

    @Override
    protected void onResume()
    {
        super.onResume();
        handler.post(updateSenhas);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        handler.removeCallbacks(updateSenhas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Globals globals = (Globals) getApplicationContext();
        MenuItem item = menu.findItem(R.id.perfil);
        item.setTitle(globals.getNome()+" "+globals.getSobrenome());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.perfil){
            Intent intent = new Intent(TelaPrincipal.this, MeuPerfil.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.meusAtendimentos){
            Intent intent = new Intent(TelaPrincipal.this, MeusAtendimentos.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.selecionarFila){
            Globals globals = (Globals) getApplicationContext();
            Intent intent = new Intent(TelaPrincipal.this, Filas.class);
            intent.putExtra("banco", globals.getNomebd());
            startActivity(intent);
            return true;
        }else if(id == R.id.selecionarEmpresa){
            Intent intent = new Intent(TelaPrincipal.this, Inicio.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.sair){
            Intent intent = new Intent(TelaPrincipal.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
