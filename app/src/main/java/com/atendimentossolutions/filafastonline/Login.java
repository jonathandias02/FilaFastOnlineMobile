package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.os.Handler;
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
    private TextView txt_cadastrar, txt_esqueceuSenha;
    private String HOST = "http://192.168.0.102/FilaFastOnlineMobile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email = (EditText) findViewById(R.id.edt_login);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        btn_entrar = (Button) findViewById(R.id.btn_entrar);
        txt_cadastrar = (TextView) findViewById(R.id.txt_cadastrar);
        txt_esqueceuSenha = (TextView) findViewById(R.id.txt_esqueceuSenha);

        txt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreCadastro = new Intent(Login.this, Cadastro.class);
                startActivity(abreCadastro);
            }
        });

        txt_esqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent esqueceu = new Intent(Login.this, EsqueceuSenha.class);
                startActivity(esqueceu);
            }
        });

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = HOST + "logar.php";
                final String email = edt_email.getText().toString();
                String senha = edt_senha.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (email.isEmpty()) {
                    Toast.makeText(Login.this, "O email deve ser preenchido!", Toast.LENGTH_LONG).show();
                    edt_email.requestFocus();
                }else if(!email.matches(emailPattern)){
                    Toast.makeText(Login.this, "Email inválido!", Toast.LENGTH_LONG).show();
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
                                            Toast.makeText(Login.this, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                                            Intent inicio = new Intent(Login.this, Inicio.class);
                                            startActivity(inicio);
                                            finish();
                                        } else {
                                            Toast.makeText(Login.this, "Usuário e/ou senha invalido(s)!", Toast.LENGTH_LONG).show();
                                            edt_senha.setText("");
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

    //tratamento do back
    private boolean backPressedOnce = false;
    private Handler backPressedHandler = new Handler();
    private static final int BACK_PRESSED_DELAY = 2000;
    private final Runnable backPressedTimeoutAction = new Runnable() {
        @Override
        public void run() {
            backPressedOnce = false;
        }
    };

    @Override
    public void onBackPressed() {
        if (this.backPressedOnce) {
            // Finaliza a aplicacao
            finish();
            return;
        }else {
            this.backPressedOnce = true;
            Toast.makeText(this, "Pressione novamente para sair", Toast.LENGTH_SHORT).show();
            backPressedHandler.postDelayed(backPressedTimeoutAction, BACK_PRESSED_DELAY);
        }
    }

}
