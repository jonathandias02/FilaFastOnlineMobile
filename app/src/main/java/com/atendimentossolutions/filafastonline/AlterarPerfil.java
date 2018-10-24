package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlterarPerfil extends AppCompatActivity {

    private EditText edt_altEmail, edt_altNome, edt_altSobrenome, edt_altTelefone;
    private Button salvarAlt;
    private String email, nome, sobrenome, telefone;

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

            }
        });

    }
}
