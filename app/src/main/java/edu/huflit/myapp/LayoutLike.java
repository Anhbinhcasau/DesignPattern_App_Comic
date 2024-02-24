package edu.huflit.myapp;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.Model.YeuThich;
import edu.huflit.myapp.adapter.Like_Adapter;
import edu.huflit.myapp.database.dtbApp;

public class LayoutLike extends AppCompatActivity {

    ListView listView1;
    public static dtbApp dtbapp;
    public static ArrayList<TruyenTranh> tranhArrayListYT;
    Like_Adapter adapter;
    int idUser, pk;
    String nameUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_like);

        dtbapp = new dtbApp(this);
        listView1 = findViewById(R.id.lvyeuthich);
        idUser = getIntent().getIntExtra("Id", 0);
        pk = getIntent().getIntExtra("phanquyen", 0);
        nameUser= getIntent().getStringExtra("TenUser");

        Log.e( "onCreate: ", String.valueOf(idUser));

        Init();
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor csLike = dtbapp.getDataLikeByIDUser(idUser);
                if (csLike.moveToPosition(i)) {
                    int IDtruyen = csLike.getInt(2);
                    Cursor csTruyen = dtbapp.getDataTryenByID(IDtruyen);
                    if (csTruyen != null && csTruyen.moveToFirst()) {
                        int idTruyen = csTruyen.getInt(2);
                        String Ten = csTruyen.getString(0);
                        String tomtat = csTruyen.getString(4);
                        String anh = csTruyen.getString(1);
                        String tacgia = csTruyen.getString(3);
                        String theloai = csTruyen.getString(5);
                        Intent a = new Intent(LayoutLike.this, Home_Detail.class);
                        a.putExtra("anh", anh);
                        a.putExtra("idTruyen", idTruyen);
                        a.putExtra("Ten", Ten);
                        a.putExtra("tomtat", tomtat);
                        a.putExtra("tacgia", tacgia);
                        a.putExtra("TL", theloai);
                        a.putExtra("phanquyen", pk);
                        a.putExtra("userId", idUser);
                        a.putExtra("TaiKhoan", nameUser);

                        startActivity(a);
                        finish();
                    }
                    csTruyen.close();
                }
                csLike.close();
            }
        });
    }

    private void Init(){
        //Hiện các truyện yêu thích
        Cursor csLike = dtbapp.getDataLikeByIDUser(idUser);
        tranhArrayListYT = new ArrayList<TruyenTranh>();
        while(csLike.moveToNext() ){
            int IDtruyen = csLike.getInt(2);
            Cursor csTruyen = dtbapp.getDataTryenByID(IDtruyen);
            if (csTruyen.moveToFirst()){
                TruyenTranh truyenTranh = new TruyenTranh();
                truyenTranh.setLinkAnh(csTruyen.getString(1));
                truyenTranh.setTenTruyen(csTruyen.getString(0));
                tranhArrayListYT.add(truyenTranh);
            }
            csTruyen.close();
        }
        csLike.close();
        adapter = new Like_Adapter(this, 0, tranhArrayListYT);
        listView1.setAdapter(adapter);
    }
}