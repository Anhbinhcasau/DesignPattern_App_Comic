package edu.huflit.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.myapp.Home;
import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.R;

public class TruyenTranhAdapter extends ArrayAdapter<TruyenTranh> {
    private Context ct;
    private ArrayList<TruyenTranh> arr;

    public TruyenTranhAdapter( Context context, int resource, List<TruyenTranh> objects) {
        super(context, resource, objects);
        this.ct=context;
        this.arr = new ArrayList<>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null) {
            LayoutInflater inflater=(LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_truyen,null);
        }

        if(arr.size()>0){
            TruyenTranh truyenTranh =this.arr.get(position);

            TextView tenTruyen = convertView.findViewById(R.id.tvtenTruyen);
            ImageView imgTruyen = convertView.findViewById(R.id.imgAnhtruyen);

            tenTruyen.setText(truyenTranh.getTenTruyen());
            Glide.with(this.ct).load(truyenTranh.getLinkAnh()).into(imgTruyen);
        }

        return convertView;
    }

    public void SearchTruyen(String s) {
        s = s.toUpperCase();
        int k = 0;
        for(int i = 0 ; i < arr.size();i++) {
            TruyenTranh t = arr.get(i);
            String ten = t.getTenTruyen().toUpperCase();
            if(ten.indexOf(s) >= 0) {
                arr.set(i,arr.get(k));
                arr.set(k,t);
                k++;
            }
        }
        notifyDataSetChanged();
    }
}

