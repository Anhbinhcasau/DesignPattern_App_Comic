package edu.huflit.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.huflit.myapp.Model.TheLoaiTruyen;
import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.Model.YeuThich;
import edu.huflit.myapp.adapter.AdminTruyenAdapter;
import edu.huflit.myapp.adapter.TheLoai_Adapter;
import edu.huflit.myapp.database.dtbApp;

public class TheLoai extends AppCompatActivity {

    ListView listView;
    ArrayList<TheLoaiTruyen> theLoaiArrayList;
    TheLoai_Adapter theLoai_adapter;
    Button btnAdd;
    EditText edtTL;
    dtbApp dbapp;
    int idUser, pk;
    String nameUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);
        listView = findViewById(R.id.list_theloai);

        btnAdd = findViewById(R.id.btnAddTheLoai);
        edtTL = findViewById(R.id.edtTL);
        dbapp = new dtbApp(this);
        idUser = getIntent().getIntExtra("userId", 0);
        pk = getIntent().getIntExtra("phanquyen", 0);
        nameUser= getIntent().getStringExtra("TaiKhoan");

        if(pk != 1)
        {
            btnAdd.setVisibility(View.GONE);
            edtTL.setVisibility(View.GONE);
        }

        Init();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cate = edtTL.getText().toString();
                TheLoaiTruyen theLoaiTruyen = CreatTL();
                if(cate.equals(""))
                {
                    Toast.makeText(TheLoai.this, "Đừng bỏ trống nhé@@", Toast.LENGTH_SHORT).show();
                    Log.e("ERR","Hãy nhập đầy đủ thong tin");
                }
                else {
                    dbapp.AddCate(theLoaiTruyen);
                    Init();
                    Toast.makeText(TheLoai.this, "Thêm thể loại thành công!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = dbapp.getDataCate();
                if (cursor.moveToPosition(i)) {
                    // Di chuyển con trỏ đến vị trí item được chọn
                    int idCate = cursor.getInt(0);
                    String cate = cursor.getString(1);
                    Intent a = new Intent(TheLoai.this, ListTLTruyen.class);
                    a.putExtra("IdCate", idCate);
                    a.putExtra("TL", cate);
                    a.putExtra("phanquyen", pk);
                    a.putExtra("userId", idUser);
                    a.putExtra("TaiKhoan", nameUser);
                    startActivity(a);
                    finish();
                }
                cursor.close();
            }
        });

    }
    private TheLoaiTruyen CreatTL(){
        String cate = edtTL.getText().toString();
        TheLoaiTruyen theLoaiTruyen = new TheLoaiTruyen();
        theLoaiTruyen.setTenTL(cate);
        return theLoaiTruyen;
    }
    private void Init() {
        Cursor cursor = dbapp.getDataCate();
        theLoaiArrayList = new ArrayList<TheLoaiTruyen>();
        while (cursor.moveToNext()){
            TheLoaiTruyen theLoaiTruyen= new TheLoaiTruyen();
            int id = cursor.getInt(0);
            String Ten =cursor.getString(1);
            theLoaiTruyen.setIdTl(id);
            theLoaiTruyen.setTenTL(Ten);
            theLoaiArrayList.add(theLoaiTruyen);
        }
        cursor.moveToFirst();
        //Thực hiện khi không sử dụng
        cursor.close();
        theLoai_adapter = new TheLoai_Adapter(this,0,theLoaiArrayList);
        listView.setAdapter(theLoai_adapter);
    }

}