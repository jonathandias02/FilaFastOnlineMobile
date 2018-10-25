package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class AlterarPerfil extends AppCompatActivity {

    private EditText edt_altEmail, edt_altNome, edt_altSobrenome, edt_altTelefone;
    private Button salvarAlt;
    private String email, nome, sobrenome, telefone, snome, ssobrenome, stelefone;
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_perfil);

        edt_altEmail = (EditText) findViewById(R.id.edt_altEmail);
        edt_altNome = (EditText) findViewById(R.id.edt_altNome);
        edt_altSobrenome = (EditText) findViewById(R.id.edt_altSobrenome);
        edt_altTelefone = (EditText) findViewById(R.id.edt_altTelefone);
        salvarAlt = (Button) findViewById(R.id.salvarAlt);

        Intent intent = getIntent();
        if(intent != null){
            Bundle dado = intent.getExtras();
            if(dado != null){
                email = dado.getString("email");
                nome = dado.getString("nome");
                sobrenome = dado.getString("sobrenome");
                telefone = dado.getString("telefone");
            }
        }

        edt_altEmail.setText(email);
        edt_altNome.setText(nome);
        edt_altSobrenome.setText(sobrenome);
        edt_altTelefone.setText(telefone);

        salvarAlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = HOST + "alterarPerfil.php";
                snome = edt_altNome.getText().toString();
                ssobrenome = edt_altSobrenome.getText().toString();
                stelefone = edt_altTelefone.getText().toString();
                if(snome.isEmpty()){
                    Toast.makeText(AlterarPerfil.this, "O nome deve ser preenchido!", Toast.LENGTH_LONG).show();
                    edt_altNome.requestFocus();
                }else if(ssobrenome.isEmpty()){
                    Toast.makeText(AlterarPerfil.this, "O sobrenome deve ser preenchido!", Toast.LENGTH_LONG).show();
                    edt_altSobrenome.requestFocus();
                }else if(stelefone.isEmpty()){
                    Toast.makeText(AlterarPerfil.this, "O telefone deve ser preenchido!", Toast.LENGTH_LONG).show();
                    edt_altTelefone.requestFocus();
                }else{
                    Ion.with(AlterarPerfil.this)
                            .load(url)
                            .setBodyParameter("nome", snome)
                            .setBodyParameter("sobrenome", ssobrenome)
                            .setBodyParameter("email", email)
                            .setBodyParameter("telefone", stelefone)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    try {
                                        String retorno = result.get("update").getAsString();
                                        if(retorno.equals("SUCESSO")){
                                            Globals globals = (Globals) getApplicationContext();
                                            globals.setNome(snome);
                                            globals.setSobrenome(ssobrenome);
                                            globals.setTelefone(stelefone);
                                            Intent intent = new Intent(AlterarPerfil.this, MeuPerfil.class);
                                            startActivity(intent);
                                            Toast.makeText(AlterarPerfil.this, "Alterado com sucesso!", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(AlterarPerfil.this, "Não foi possivel alterar!", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception erro) {
                                        Toast.makeText(AlterarPerfil.this, "Ops, Ocorreu um erro, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.perfil){
            Intent intent = new Intent(AlterarPerfil.this, MeuPerfil.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.selecionarFila){
            Globals globals = (Globals) getApplicationContext();
            Intent intent = new Intent(AlterarPerfil.this, Filas.class);
            intent.putExtra("banco", globals.getNomebd());
            startActivity(intent);
            return true;
        }else if(id == R.id.selecionarEmpresa){
            Intent intent = new Intent(AlterarPerfil.this, Inicio.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.sair){
            Intent intent = new Intent(AlterarPerfil.this, Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
