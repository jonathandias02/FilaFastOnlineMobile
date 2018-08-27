package com.atendimentossolutions.filafastonline;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EmpresaAdapter extends BaseAdapter {

    private Context ctx;
    private List<Empresa> lista;

    public EmpresaAdapter(Context ctx2, List<Empresa> lista2) {
        ctx = ctx2;
        lista = lista2;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Empresa getItem(int i) {
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
            v = inflater.inflate(R.layout.itemlista, null);
        } else {
            v = view;
        }

        Empresa c = getItem(i);

        TextView itemnomelocal = (TextView) v.findViewById(R.id.itemnomelocal);
        TextView itemenderecolocal = (TextView) v.findViewById(R.id.itemenderecolocal);

        itemnomelocal.setText(c.getEmpresa());
        itemenderecolocal.setText(c.getEndereco());

        return v;
    }
}
