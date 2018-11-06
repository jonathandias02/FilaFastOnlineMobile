package com.atendimentossolutions.filafastonline;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemMeusAtendimentosAdapter extends BaseAdapter {

    private Context ctx;
    private List<ItemMeusAtendimentos> lista;

    public ItemMeusAtendimentosAdapter(Context ctx2, List<ItemMeusAtendimentos> lista2){
        ctx = ctx2;
        lista = lista2;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public ItemMeusAtendimentos getItem(int i) {
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
            v = inflater.inflate(R.layout.itematendimentos, null);
        } else {
            v = view;
        }

        ItemMeusAtendimentos item = getItem(i);

        TextView txt_senha = (TextView) v.findViewById(R.id.mSenha);
        TextView txt_data = (TextView) v.findViewById(R.id.mData);
        TextView txt_servico = (TextView) v.findViewById(R.id.mServico);
        TextView txt_atendente = (TextView) v.findViewById(R.id.mAtendente);

        txt_senha.setText(item.getSenha());
        txt_data.setText(item.getDataAtendimento());
        txt_servico.setText(item.getServico());
        txt_atendente.setText(item.getAtendente());

        return v;
    }
}
