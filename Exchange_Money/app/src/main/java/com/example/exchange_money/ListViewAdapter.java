package com.example.exchange_money;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    final ArrayList<Convert> listDescription;

    public ListViewAdapter(ArrayList<Convert> listDescription) {
        this.listDescription = listDescription;
    }

    @Override
    public int getCount() {
        return listDescription.size();
    }

    @Override
    public Object getItem(int position) {
        return listDescription.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewDescription;
        if (convertView == null)
            viewDescription = View.inflate(parent.getContext(), R.layout.details, null);
        else
            viewDescription = convertView;

        Convert convert = (Convert) getItem(position);
        ((TextView) viewDescription.findViewById(R.id.details)).setText(convert.description);

        return viewDescription;
    }
}
