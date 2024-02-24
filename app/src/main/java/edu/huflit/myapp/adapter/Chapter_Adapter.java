package edu.huflit.myapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import edu.huflit.myapp.Model.TapTruyen;
import edu.huflit.myapp.R;

public class Chapter_Adapter extends ArrayAdapter<TapTruyen> {
    Activity context = null;
    ArrayList<TapTruyen> myArray= null;
    int layoutId;
    public Chapter_Adapter(Activity context, int layoutId, ArrayList<TapTruyen> arr){
        super(context, layoutId);
        this.context =context;
        this.layoutId = layoutId;
        this.myArray = arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId,null);

        TextView tvChapter = (TextView) convertView.findViewById(R.id.tvChapter);
        TapTruyen emp = myArray.get(position);
        tvChapter.setText(emp.getTenTap());
        return convertView;
    }
}

