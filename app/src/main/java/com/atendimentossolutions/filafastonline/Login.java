package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class Login extends AppCompatActivity {

    private EditText edt_email, edt_senha;
    private Button btn_entrar;
    private TextView txt_cadastrar;
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email = (EditText) findViewById(R.id.edt_login);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        btn_entrar = (Button) findViewById(R.id.btn_entrar);
        txt_cadastrar = (TextView) findViewById(R.id.txt_cadastrar);

        txt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreCadastro = new Intent(Login.this, Cadastro.class);
                startActivity(abreCadastro);
            }
        });

        final Globals sessao = (Globals) getApplicationContext();
        sessao.setId(1);
        sessao.setNome("Jonathan");
        sessao.setSobrenome("Almeida");
        sessao.setTelefone("61 99242-3276");
        sessao.setEmail("jonathan@jonathan.com");

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = HOST + "logar.php";
                final String email = edt_email.getText().toString();
                String senha = edt_senha.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(Login.this, "O email deve ser preenchido!", Toast.LENGTH_LONG).show();
                    edt_email.requestFocus();
                } else if (senha.isEmpty()) {
                    Toast.makeText(Login.this, "A senha deve ser preenchido!", Toast.LENGTH_LONG).show();
                    edt_senha.requestFocus();
                } else {

                    Ion.with(Login.this)
                            .load(url)
                            .setBodyParameter("email", email)
                            .setBodyParameter("senha", senha)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    try {
                                        String retorno = result.get("LOGIN").getAsString();
                                        if (retorno.equals("SUCESSO")) {
                                            Globals globals = (Globals) getApplicationContext();
                                            globals.setId(result.get("id").getAsInt());
                                            globals.setNome(result.get("nome").getAsString());
                                            globals.setSobrenome(result.get("sobrenome").getAsString());
                                            globals.setTelefone(result.get("telefone").getAsString());
                                            globals.setEmail(result.get("email").getAsString());
                                            Toast.makeText(Login.this, "Logado com sucesso!", Toast.LENGTH_LONG).show();
                                            Intent inicio = new Intent(Login.this, Inicio.class);
                                            startActivity(inicio);
                                        } else {
                                            Toast.makeText(Login.this, "Usuário e/ou senha invalido(s)!", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception erro) {

                                        Toast.makeText(Login.this, "Ops, Ocorreu um erro, tente novamente mais tarde!", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

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
