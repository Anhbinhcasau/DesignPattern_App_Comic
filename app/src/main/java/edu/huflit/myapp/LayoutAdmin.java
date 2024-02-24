package edu.huflit.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.adapter.AdminTruyenAdapter;
import edu.huflit.myapp.adapter.TruyenTranhAdapter;
import edu.huflit.myapp.database.dtbApp;

public class LayoutAdmin extends AppCompatActivity {
    Button btnThem, btnTheloai;
    ListView listView;
    dtbApp dtbapp;
    ArrayList<TruyenTranh> tranhArrayList;
    AdminTruyenAdapter adapter;
    private boolean isLongClick = false;
    int idUser, pk;
    String nameUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_admin);
        dtbapp = new dtbApp(this);

        btnThem = findViewById(R.id.btnThemTruyen);
        listView = findViewById(R.id.lvQuanLy);
        btnTheloai = findViewById(R.id.btnTheloai);

        idUser = getIntent().getIntExtra("userId", 0);
        pk = getIntent().getIntExtra("phanquyen", 0);
        nameUser= getIntent().getStringExtra("TaiKhoan");

        btnTheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LayoutAdmin.this, TheLoai.class);
                i.putExtra("phanquyen", pk);
                i.putExtra("userId", idUser);
                i.putExtra("TaiKhoan", nameUser);
                startActivity(i);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LayoutAdmin.this, ThemTruyen.class);
                startActivity(i);
            }
        });
        Init();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                isLongClick = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(LayoutAdmin.this);
                builder.setMessage("Bạn muốn xóa truyện này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xóa item tại vị trí position trong danh sách truyện
                        dtbapp.Delete(tranhArrayList.get(i));
                        listView.invalidateViews();
                        Intent i = new Intent(LayoutAdmin.this,LayoutAdmin.class);
                        finish();
                        startActivity(i);

                        // Cập nhật lại dữ liệu và giao diện
                    }
                });
                builder.show();
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isLongClick) {
                    isLongClick = false;
                    Cursor cursor = dtbapp.getDataTruyen();
                    if (cursor.moveToPosition(i)) {
                        // Di chuyển con trỏ đến vị trí item được chọn
                        int idTruyen = cursor.getInt(0);
                        String Ten = cursor.getString(1);
                        String tomtat = cursor.getString(2);
                        String anh = cursor.getString(3);
                        String tacgia = cursor.getString(4);
                        Intent a = new Intent(LayoutAdmin.this, update_truyen.class);
                        a.putExtra("anh", anh);
                        a.putExtra("Id", idTruyen);
                        a.putExtra("Ten", Ten);
                        a.putExtra("tomtat", tomtat);
                        a.putExtra("tacgia", tacgia);
                        startActivity(a);
                    }
                    cursor.close();
                }

            }
        });
    }
    private void Init() {
        Cursor cursor = dtbapp.getDataTruyen();
        tranhArrayList = new ArrayList<TruyenTranh>();

        while (cursor.moveToNext()){
            TruyenTranh truyenTranh= new TruyenTranh();
            int id = cursor.getInt(0);
            String Ten =cursor.getString(1);
            String anh = cursor.getString(3);
            truyenTranh.setIdTruyen(id);
            truyenTranh.setTenTruyen(Ten);
            truyenTranh.setLinkAnh(anh);
            tranhArrayList.add(truyenTranh);
        }
        cursor.moveToFirst();
        //Thực hiện khi không sử dụng
        cursor.close();
        adapter = new AdminTruyenAdapter(this,0,tranhArrayList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Init();
    }
}