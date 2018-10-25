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

public class AlterarSenha extends AppCompatActivity {

    private EditText edt_senhaAtual, edt_novaSenha, edt_confirmarNovaSenha;
    private Button salvarNovaSenha;
    private String senhaAtual, novaSenha, confirmarSenha;
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        edt_senhaAtual = (EditText) findViewById(R.id.edt_senhaAtual);
        edt_novaSenha = (EditText) findViewById(R.id.edt_novaSenha);
        edt_confirmarNovaSenha = (EditText) findViewById(R.id.edt_confirmarNovaSenha);
        salvarNovaSenha = (Button) findViewById(R.id.salvarNovaSenha);

        salvarNovaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = HOST + "alterarSenha.php";
                senhaAtual = edt_senhaAtual.getText().toString();
                novaSenha = edt_novaSenha.getText().toString();
                confirmarSenha = edt_confirmarNovaSenha.getText().toString();
                Globals globals = (Globals) getApplicationContext();
                if(senhaAtual.isEmpty()){
                    Toast.makeText(AlterarSenha.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();
                    edt_senhaAtual.requestFocus();
                }else if(novaSenha.isEmpty()){
                    Toast.makeText(AlterarSenha.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();
                    edt_novaSenha.requestFocus();
                } else if(confirmarSenha.isEmpty()){
                    Toast.makeText(AlterarSenha.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();
                    edt_confirmarNovaSenha.requestFocus();
                }else{
                    if(senhaAtual.equals(novaSenha)){
                        Toast.makeText(AlterarSenha.this, "A nova senha não pode ser igual a atual!", Toast.LENGTH_LONG).show();
                        edt_senhaAtual.setText("");
                        edt_novaSenha.setText("");
                        edt_confirmarNovaSenha.setText("");
                        edt_senhaAtual.requestFocus();
                    }else if(novaSenha.equals(confirmarSenha)){
                        Ion.with(AlterarSenha.this)
                                .load(url)
                                .setBodyParameter("senhaAtual", senhaAtual)
                                .setBodyParameter("senha", novaSenha)
                                .setBodyParameter("email", globals.getEmail())
                                .asJsonObject()
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception e, JsonObject result) {
                                        try {
                                            String retorno = result.get("update").getAsString();
                                            if(retorno.equals("SUCESSO")){
                                                Toast.makeText(AlterarSenha.this, "Senha alterada com sucesso!", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(AlterarSenha.this, MeuPerfil.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(AlterarSenha.this, "Senha atual incorreta!", Toast.LENGTH_LONG).show();
                                                edt_senhaAtual.setText("");
                                                edt_novaSenha.setText("");
                                                edt_confirmarNovaSenha.setText("");
                                                edt_senhaAtual.requestFocus();
                                            }

                                        } catch (Exception erro) {
                                            Toast.makeText(AlterarSenha.this, "Ops, Ocorreu um erro, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }else{
                        Toast.makeText(AlterarSenha.this, "As senhas não conferem!", Toast.LENGTH_LONG).show();
                        edt_novaSenha.setText("");
                        edt_confirmarNovaSenha.setText("");
                        edt_novaSenha.requestFocus();
                    }
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
            Intent intent = new Intent(AlterarSenha.this, MeuPerfil.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.selecionarFila){
            Globals globals = (Globals) getApplicationContext();
            Intent intent = new Intent(AlterarSenha.this, Filas.class);
            intent.putExtra("banco", globals.getNomebd());
            startActivity(intent);
            return true;
        }else if(id == R.id.selecionarEmpresa){
            Intent intent = new Intent(AlterarSenha.this, Inicio.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.sair){
            Intent intent = new Intent(AlterarSenha.this, Login.class);
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
