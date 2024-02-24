package edu.huflit.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.myapp.Model.TheLoaiTruyen;
import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.R;
import edu.huflit.myapp.TheLoai;

public class TheLoai_Adapter extends ArrayAdapter<TheLoaiTruyen> {

    private Context ct;
    private ArrayList<TheLoaiTruyen> arrad;

    public TheLoai_Adapter(TheLoai context, int resource, ArrayList<TheLoaiTruyen> theLoaiArrayList) {
        super(context, resource, theLoaiArrayList);
        this.ct = context;
        this.arrad = new ArrayList<>(theLoaiArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null) {
            LayoutInflater inflater=(LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_the_loai,null);
        }

        if(arrad.size()>0){
            TheLoaiTruyen theLoaiTruyen = this.arrad.get(position);

            TextView tenTL = convertView.findViewById(R.id.hienTheLoai);

            tenTL.setText(theLoaiTruyen.getTenTL());

        }
        return convertView;
    }
}
