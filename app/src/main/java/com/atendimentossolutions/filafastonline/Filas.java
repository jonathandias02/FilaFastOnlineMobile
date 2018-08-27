package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Filas extends AppCompatActivity {

    private TextView txt_bancodados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filas);

        txt_bancodados = (TextView) findViewById(R.id.txt_bancodados);

        Intent intent = getIntent();
        if(intent != null){
            Bundle dado = intent.getExtras();
            if(dado != null){
                String nomebd = dado.getString("banco");
                txt_bancodados.setText(nomebd);
            }
        }

    }
}
