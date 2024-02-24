package edu.huflit.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.huflit.myapp.Model.TapTruyen;
import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.adapter.TruyenTranhAdapter;
import edu.huflit.myapp.database.dtbApp;

public class ThemTap extends AppCompatActivity {

    Button btnThem;
    EditText edtName, edtIMGnoiDung, edtIdTap;
    dtbApp dbApp;
    int idTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tap);

        btnThem = findViewById(R.id.btnThem);
        edtName = findViewById(R.id.edtTenTap);
        dbApp = new dtbApp(this);
        idTruyen = getIntent().getIntExtra("idTruyen", 0);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenTap = edtName.getText().toString();

                TapTruyen tapTruyen = CreatTap();

                if(tenTap.equals(""))
                {
                    Toast.makeText(ThemTap.this, "Đừng bỏ trống nhé@@", Toast.LENGTH_SHORT).show();
                    Log.e("ERR","Hãy nhập đầy đủ thong tin");
                }
                else {
                    dbApp.Addtap(tapTruyen);
                    Toast.makeText(ThemTap.this, "Thêm tập truyện thành công!!", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });

    }
    private TapTruyen CreatTap(){
        int tenTap = Integer.parseInt(edtName.getText().toString());
        TapTruyen tapTruyen = new TapTruyen();
        tapTruyen.setTenTap(tenTap);
        tapTruyen.setIdTruyen(idTruyen);
        return tapTruyen;
    }
}