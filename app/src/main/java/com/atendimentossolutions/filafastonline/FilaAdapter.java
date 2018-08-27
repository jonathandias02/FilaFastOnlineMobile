package com.atendimentossolutions.filafastonline;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class FilaAdapter extends BaseAdapter {

    private Context ctx;
    private List<Fila> lista;

    public FilaAdapter(Context ctx2, List<Fila> lista2){
        ctx = ctx2;
        lista = lista2;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Fila getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = null;

        if (view == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            v = inflater.inflate(R.layout.itemfila, null);
        } else {
            v = view;
        }

        Fila f = getItem(i);

        TextView txt_nomefila = (TextView) v.findViewById(R.id.txt_nomefila);

        txt_nomefila.setText(f.getNome());

        return v;
    }
}
