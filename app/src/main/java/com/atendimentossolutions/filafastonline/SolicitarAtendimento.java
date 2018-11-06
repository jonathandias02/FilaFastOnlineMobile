package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolicitarAtendimento extends AppCompatActivity {

    private Spinner spinner_servico, spinner_preferencia;
    private ArrayList<ServicosClass> lista = new ArrayList<ServicosClass>();
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";
    private Integer id, minhaSenha;
    private String nomebd;
    private Button gerarSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_atendimento);
        spinner_servico = (Spinner) findViewById(R.id.spinner_servicos);
        spinner_preferencia = (Spinner) findViewById(R.id.spinner_preferencia);
        gerarSenha = (Button) findViewById(R.id.gerarSenha);

        Intent intent = getIntent();
        if(intent != null){
            Bundle dado = intent.getExtras();
            if(dado != null){
                id = dado.getInt("idFila");
                nomebd = dado.getString("nomebd");
                minhaSenha = dado.getInt("minhaSenha");
            }
        }
        ServicosClass selecionar = new ServicosClass(0, "Selecionar", null);
        lista.add(selecionar);
        listaServicos(id, nomebd);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lista);
        spinner_servico.setAdapter(adapter);

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.preferencia, android.R.layout.simple_spinner_item);
        spinner_preferencia.setAdapter(adapter1);

        ServicosClass servico = ((ServicosClass)spinner_servico.getSelectedItem());
        //Toast.makeText(SolicitarAtendimento.this, "O id é :" + servico.getId(), Toast.LENGTH_LONG).show();
        gerarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(minhaSenha == 0) {
                    Globals globals = (Globals) getApplicationContext();
                    ServicosClass servico = ((ServicosClass) spinner_servico.getSelectedItem());
                    int idPreferencia;
                    if (spinner_preferencia.getSelectedItem().equals("Normal")) {
                        idPreferencia = 1;
                    } else {
                        idPreferencia = 2;
                    }
                    if (servico.getId() == 0) {
                        Toast.makeText(SolicitarAtendimento.this, "Selecione um serviço!", Toast.LENGTH_LONG).show();
                        spinner_servico.requestFocus();
                    } else {
                        solicitarSenha(nomebd, id, idPreferencia, servico.getId(),
                                globals.getNome() + " " + globals.getSobrenome(), servico.getSigla(), globals.getEmail());
                    }
                }else{
                    Toast.makeText(SolicitarAtendimento.this, "Você já está aguardando atendimento!", Toast.LENGTH_LONG).show();
                }
            }
        });

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
            Intent intent = new Intent(SolicitarAtendimento.this, MeuPerfil.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.meusAtendimentos){
            Intent intent = new Intent(SolicitarAtendimento.this, MeusAtendimentos.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.selecionarFila){
            Globals globals = (Globals) getApplicationContext();
            Intent intent = new Intent(SolicitarAtendimento.this, Filas.class);
            intent.putExtra("banco", globals.getNomebd());
            startActivity(intent);
            return true;
        }else if(id == R.id.selecionarEmpresa){
            Intent intent = new Intent(SolicitarAtendimento.this, Inicio.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.sair){
            Intent intent = new Intent(SolicitarAtendimento.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                                ServicosClass servico = new ServicosClass(obj.get("id").getAsInt(), obj.get("nomeServico").getAsString(), obj.get("sigla").getAsString());

                                lista.add(servico);

                            }

                        }catch (Exception erro){
                            Toast.makeText(SolicitarAtendimento.this, "Ocorreu um erro ao carregar servicos!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    private void solicitarSenha(final String banco, final Integer idFila, Integer idPreferencia, Integer idServico, String identificacao,
    String sigla, String email){

        String url = HOST + "solicitarSenha.php";

        Ion.with(SolicitarAtendimento.this)
                .load(url)
                .setBodyParameter("nomebd", banco)
                .setBodyParameter("idFila", String.valueOf(idFila))
                .setBodyParameter("idPreferencia", String.valueOf(idPreferencia))
                .setBodyParameter("idServico", String.valueOf(idServico))
                .setBodyParameter("identificacao", identificacao)
                .setBodyParameter("sigla", sigla)
                .setBodyParameter("email", email)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try{
                            if(result.get("insert").getAsString().equals("SUCESSO")){
                                Toast.makeText(SolicitarAtendimento.this, "Sua senha é: "+result.get("senha").getAsString(), Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }catch (Exception erro){
                            Toast.makeText(SolicitarAtendimento.this, "Ocorreu um erro ao solicitar senha!", Toast.LENGTH_LONG).show();
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
