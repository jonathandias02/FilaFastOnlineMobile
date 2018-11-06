package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Avaliar extends AppCompatActivity {

    Button btn_avaliar, btn_naoavaliar;
    RadioGroup radioAvaliacao;
    Integer id;
    String nomebd;
    String radioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar);

        radioAvaliacao = (RadioGroup) findViewById(R.id.radioAvaliacao);
        btn_avaliar = (Button) findViewById(R.id.btn_avaliar);
        btn_naoavaliar = (Button) findViewById(R.id.btn_naoavaliar);

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
                id = dado.getInt("idFila");
                nomebd = dado.getString("nomebd");
            }
        }

        btn_avaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Avaliar.this, "funcionando" + radioSelecionado, Toast.LENGTH_LONG).show();
            }
        });

        btn_naoavaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Avaliar.this, "funcionando", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
