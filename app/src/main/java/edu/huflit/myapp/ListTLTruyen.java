package edu.huflit.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.adapter.AdminTruyenAdapter;
import edu.huflit.myapp.adapter.Like_Adapter;
import edu.huflit.myapp.database.dtbApp;

public class ListTLTruyen extends AppCompatActivity {

    dtbApp dtbapp;
    ArrayList<TruyenTranh> tranhArrayList;
    String cate;
    AdminTruyenAdapter adapter;
    int idUser, pk;
    String nameUser;
    ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tltruyen);
        dtbapp = new dtbApp(this);
        idUser = getIntent().getIntExtra("userId", 0);
        pk = getIntent().getIntExtra("phanquyen", 0);
        nameUser= getIntent().getStringExtra("TaiKhoan");


        listView = findViewById(R.id.lv);
        Init();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor csTruyen = dtbapp.getDataTruyenByCate(cate);
                if (csTruyen.moveToPosition(i)) {
                    int idTruyen = csTruyen.getInt(0);
                    String tenTruyen = csTruyen.getString(1);
                    String noiDung = csTruyen.getString(4);
                    String anh = csTruyen.getString(2);
                    String tacGia = csTruyen.getString(3);
                    String theloai = csTruyen.getString(5);
                    Intent a = new Intent(ListTLTruyen.this, Home_Detail.class);
                    a.putExtra("anh", anh);
                    a.putExtra("idTruyen", idTruyen);
                    a.putExtra("Ten", tenTruyen);
                    a.putExtra("tomtat", noiDung);
                    a.putExtra("tacgia", tacGia);
                    a.putExtra("TL", theloai);

                    a.putExtra("phanquyen", pk);
                    a.putExtra("userId", idUser);
                    a.putExtra("TaiKhoan", nameUser);
                    startActivity(a);
                    finish();
                }
            }
        });
    }
    private void Init(){
        //Hiện các truyện theo thể loại
        tranhArrayList = new ArrayList<TruyenTranh>();
        cate = getIntent().getStringExtra("TL");
        Cursor csTruyen = dtbapp.getDataTruyenByCate(cate);
        while (csTruyen.moveToNext()) {
            TruyenTranh truyenTranh = new TruyenTranh();
            truyenTranh.setLinkAnh(csTruyen.getString(2));
            truyenTranh.setTenTruyen(csTruyen.getString(1));
            tranhArrayList.add(truyenTranh);
        }
        csTruyen.close();
        adapter = new AdminTruyenAdapter(this, 0, tranhArrayList);
        listView.setAdapter(adapter);
    }
}