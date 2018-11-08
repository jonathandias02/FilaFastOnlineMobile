package com.atendimentossolutions.filafastonline;

import android.content.Intent;
import android.support.constraint.solver.GoalRow;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MeuPerfil extends AppCompatActivity {

    private TextView emailPerfil, nomePerfil, sobrenomePerfil, telefonePerfil;
    private Button alterarPerfil, alterarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);

        emailPerfil = (TextView) findViewById(R.id.emailPerfil);
        nomePerfil = (TextView) findViewById(R.id.nomePerfil);
        sobrenomePerfil = (TextView) findViewById(R.id.sobrenomePerfil);
        telefonePerfil = (TextView) findViewById(R.id.telefonePerfil);
        alterarPerfil = (Button) findViewById(R.id.alterarPerfil);
        alterarSenha = (Button) findViewById(R.id.alterarSenha);

        final Globals globals = (Globals) getApplicationContext();
        emailPerfil.setText(globals.getEmail());
        nomePerfil.setText(globals.getNome());
        sobrenomePerfil.setText(globals.getSobrenome());
        telefonePerfil.setText(globals.getTelefone());

        alterarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeuPerfil.this, AlterarPerfil.class);
                intent.putExtra("email", globals.getEmail());
                intent.putExtra("nome", globals.getNome());
                intent.putExtra("sobrenome", globals.getSobrenome());
                intent.putExtra("telefone", globals.getTelefone());
                startActivity(intent);
            }
        });

        alterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeuPerfil.this, AlterarSenha.class);
                startActivity(intent);
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
            Intent intent = new Intent(MeuPerfil.this, MeuPerfil.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.meusAtendimentos){
            Intent intent = new Intent(MeuPerfil.this, MeusAtendimentos.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.selecionarFila){
            Globals globals = (Globals) getApplicationContext();
            Intent intent = new Intent(MeuPerfil.this, Filas.class);
            intent.putExtra("banco", globals.getNomebd());
            startActivity(intent);
            return true;
        }else if(id == R.id.selecionarEmpresa){
            Intent intent = new Intent(MeuPerfil.this, Inicio.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.sair){
            Intent intent = new Intent(MeuPerfil.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Globals globals = (Globals) getApplicationContext();
        Intent intent = new Intent(MeuPerfil.this, TelaPrincipal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("id", globals.getIdFila());
        intent.putExtra("nomebd", globals.getNomebd());
        startActivity(intent);
    }
}
