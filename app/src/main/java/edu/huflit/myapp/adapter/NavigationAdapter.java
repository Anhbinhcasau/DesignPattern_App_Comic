package edu.huflit.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.huflit.myapp.Model.ThongTin;
import edu.huflit.myapp.R;

public class NavigationAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ThongTin> navigationsList;

    public NavigationAdapter(Context context, int layout, List<ThongTin> navigationsList) {
        this.context = context;
        this.layout = layout;
        this.navigationsList = navigationsList;
    }

    @Override
    public int getCount() {
        return navigationsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        ImageView img = (ImageView) view.findViewById(R.id.imgChuyenMuc);
        TextView txt = (TextView) view.findViewById(R.id.tvChuyenMuc);
        ThongTin cm = navigationsList.get(i);
        txt.setText(cm.getTenchuyenmuc());
        Glide.with(this.context).load(cm.getHinhanh()).into(img);
        return view;
    }
}
