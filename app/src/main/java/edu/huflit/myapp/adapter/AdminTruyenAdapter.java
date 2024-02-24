package edu.huflit.myapp.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.myapp.Home;
import edu.huflit.myapp.LayoutAdmin;
import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.R;

public class AdminTruyenAdapter extends ArrayAdapter<TruyenTranh> {
    private Context ct;
    private ArrayList<TruyenTranh> arrad;

    public AdminTruyenAdapter( Context context, int resource, List<TruyenTranh> objects) {
        super(context, resource, objects);
        this.ct=context;
        this.arrad = new ArrayList<>(objects);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null) {
            LayoutInflater inflater=(LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_admin_truyen,null);
        }

        if(arrad.size()>0){
            TruyenTranh truyenTranh =this.arrad.get(position);

            TextView tenTruyen = convertView.findViewById(R.id.tvNameTruyen);
            ImageView imgTruyen = convertView.findViewById(R.id.imgTruyenAd);

            tenTruyen.setText(truyenTranh.getTenTruyen());
            Glide.with(this.ct).load(truyenTranh.getLinkAnh()).into(imgTruyen);
        }
        return convertView;

    }
}

