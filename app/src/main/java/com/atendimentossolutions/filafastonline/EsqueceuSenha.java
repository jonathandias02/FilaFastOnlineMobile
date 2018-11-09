package com.atendimentossolutions.filafastonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EsqueceuSenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
