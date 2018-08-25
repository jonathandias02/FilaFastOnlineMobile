package com.atendimentossolutions.filafastonline;

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

public class Cadastro extends AppCompatActivity {

    private EditText edt_nome, edt_email, edt_senha, edt_confirmar;
    private Button btn_cadastrar;
    private String HOST = String.valueOf(R.string.host);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edt_nome = (EditText) findViewById(R.id.edt_nome);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        edt_confirmar = (EditText) findViewById(R.id.edt_confirmar);
        btn_cadastrar = (Button) findViewById(R.id.btn_cadastrar);

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = HOST + "cadastro.php";
                String nome = edt_nome.getText().toString();
                String email = edt_email.getText().toString();
                String senha = edt_senha.getText().toString();
                String confirmar = edt_confirmar.getText().toString();

                if (nome.isEmpty()) {
                    Toast.makeText(Cadastro.this, "O nome deve ser preenchido!", Toast.LENGTH_LONG).show();
                    edt_nome.requestFocus();
                } else if (email.isEmpty()) {
                    Toast.makeText(Cadastro.this, "O email deve ser preenchido!", Toast.LENGTH_LONG).show();
                    edt_email.requestFocus();
                } else if (senha.isEmpty()) {
                    Toast.makeText(Cadastro.this, "A Senha deve ser preenchido!", Toast.LENGTH_LONG).show();
                    edt_senha.requestFocus();
                } else if (confirmar.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Você deve confirmar a senha!", Toast.LENGTH_LONG).show();
                    edt_confirmar.requestFocus();
                } else {
                    if (confirmar.equals(senha)) {

                        Ion.with(Cadastro.this)
                                .load(url)
                                .setBodyParameter("nome", nome)
                                .setBodyParameter("email", email)
                                .setBodyParameter("senha", senha)
                                .asJsonObject()
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception e, JsonObject result) {
                                        try {
                                            //Toast.makeText(Cadastro.this, "Retorno: " + result.get("NOME").getAsString(), Toast.LENGTH_LONG).show();
                                            String retorno = result.get("CADASTRO").getAsString();
                                            if(retorno.equals("SUCESSO")){
                                                Toast.makeText(Cadastro.this, "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                                            }else if(retorno.equals("EMAIL_ERRO")){
                                                Toast.makeText(Cadastro.this, "Email já cadastrado!", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(Cadastro.this, "Não foi possivel cadastrar!", Toast.LENGTH_LONG).show();
                                            }

                                        } catch (Exception erro) {
                                            Toast.makeText(Cadastro.this, "Ocorreu um erro " + erro, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(Cadastro.this, "As senhas não conferem!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
}
