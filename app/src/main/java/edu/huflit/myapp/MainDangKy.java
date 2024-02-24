package edu.huflit.myapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.huflit.myapp.Model.Users;
import edu.huflit.myapp.database.dtbApp;

public class MainDangKy extends AppCompatActivity {
    EditText edttk,edtmk,edtemail;
    Button btnDK;

    TextView tvback;
    dtbApp dtbApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_ky);

        dtbApp = new dtbApp(this);

        AnhXa();

        tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvback.setPaintFlags(tvback.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent i = new Intent(MainDangKy.this,MainLogin.class);
                startActivity(i);
            }
        });

        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taikhoan = edttk.getText().toString();
                String matkhau = edtmk.getText().toString();
                String email = edtemail.getText().toString();

                Users tk = CreatTK();

                if(taikhoan.equals("") || matkhau.equals("") || email.equals(""))
                {
                    Toast.makeText(MainDangKy.this, "Hãy điền vào ô trống ", Toast.LENGTH_SHORT).show();
                }
                else {
                    dtbApp.Add(tk);
                    Toast.makeText(MainDangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),MainLogin.class);
                    startActivity(i);
                }
            }
        });
    }

    private Users CreatTK() {
        String taikhoan = edttk.getText().toString();
        String matkhau = edtmk.getText().toString();
        String email = edtemail.getText().toString();
        int Phanquyen = 2;
        Users tk = new Users(taikhoan,matkhau,email,Phanquyen);
        return tk;
    }
    public void AnhXa()
    {
        edtmk = findViewById(R.id.dkmatkhau);
        edttk = findViewById(R.id.dktaikhoan);
        edtemail =findViewById(R.id.dkemail);
        btnDK = findViewById(R.id.btndangky);
        tvback = findViewById(R.id.backLogin);
    }


}