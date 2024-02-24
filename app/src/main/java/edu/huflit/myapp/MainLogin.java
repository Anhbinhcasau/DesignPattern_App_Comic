package edu.huflit.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.huflit.myapp.database.dtbApp;

public class MainLogin extends AppCompatActivity {
    EditText edtTK,edtMK;
    Button btnDangNhap, btnDangKy;
    SharedPreferences sp,rmb;
    SharedPreferences.Editor editor, rmbEditor;
    dtbApp dtbApp;
    CheckBox ckbRemember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXaLogin();

        dtbApp = new dtbApp(this);

        //Tạo sự kiện click button với Intent
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainLogin.this,MainDangKy.class);
                startActivity(intent);
            }
        });

        Cursor cursor = dtbApp.getData();

        sp = getSharedPreferences("AutoLogin", MODE_PRIVATE );
        rmb = getSharedPreferences("SaveUser", MODE_PRIVATE );

        editor = sp.edit();
        rmbEditor = rmb.edit();

        boolean login = sp.getBoolean("ISLOGGEDIN", false);
        boolean remember = rmb.getBoolean("REMEMBER", false);

        if (remember == true){
            edtTK.setText(rmb.getString("username", ""));
            edtMK.setText(rmb.getString("password", ""));
        }

        if (login == true){
            String username = sp.getString("username", "");
            String password = sp.getString("password", "");
            while (cursor.moveToNext()){

                String datataikhoan = cursor.getString(1);
                String datamatkhau = cursor.getString(2);

                if(datataikhoan.equals(username) && datamatkhau.equals(password)){
                    int id = cursor.getInt(0);
                    int phanquyen =cursor.getInt(4);
                    String email =cursor.getString(3);
                    String tk = cursor.getString(1);

                    Intent i = new Intent(MainLogin.this,Home.class);
                    i.putExtra("phanquyen",phanquyen);
                    i.putExtra("Id",id);
                    i.putExtra("TaiKhoan",tk);
                    i.putExtra("Email",email);
                    startActivity(i);
                    finish();
                }
            }
        }


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo 2 biến Tài khoản và Mật Khẩu
                String mtaikhoan = edtTK.getText().toString();
                String mmatkhau = edtMK.getText().toString();
                boolean found = false;

                //Sử dụng con trỏ để lấy dữ liệu từ database

                //Thực hiền vòng lặp để lấy dữ liệu từ cursor với moveToNext di chuyển tiếp
                while (cursor.moveToNext()) {
                    //Lấy dữ liệu và gán vào biến
                    // ô 0 : Id - ô 1 : Tài Khoản - ô 2 : Mật Khẩu - ô 3 : Email - ô 4 : Phân Quyền

                    String datataikhoan = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);
                    if(datataikhoan.equals(mtaikhoan) && datamatkhau.equals(mmatkhau)) {

                        //Lấy dữ liệu phân quyền và id
                        int id = cursor.getInt(0);
                        int phanquyen =cursor.getInt(4);
                        String email =cursor.getString(3);
                        String tk = cursor.getString(1);

                        if(ckbRemember.isChecked()){
                            rmbEditor.putString("username" , datataikhoan);
                            rmbEditor.putString("password", datamatkhau);
                            rmbEditor.putBoolean("REMEMBER", true);
                            rmbEditor.apply();
                        }
                        editor.putString("username" , datataikhoan);
                        editor.putString("password", datamatkhau);
                        editor.putBoolean("ISLOGGEDIN", true);
                        editor.apply();

                        //Chuyển vào màn hình HOME app đọc truyện
                        Intent i = new Intent(MainLogin.this,Home.class);

                        Toast.makeText(MainLogin.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        //Gửi dữ liệu qua Activity là Main Home
                        i.putExtra("phanquyen",phanquyen);
                        i.putExtra("Id",id);
                        i.putExtra("TaiKhoan",tk);
                        i.putExtra("Email",email);
                        i.putExtra("MK", mmatkhau);
                        startActivity(i);
                        finish();
                        found = true;
                    }
                }
                cursor.moveToFirst();
                if (!found) {
                    Toast.makeText(MainLogin.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void AnhXaLogin() {
        edtTK = findViewById(R.id.taikhoan);
        edtMK = findViewById(R.id.matkhau);
        btnDangNhap = findViewById(R.id.dangnhap);
        btnDangKy = findViewById(R.id.dangky);
        ckbRemember = findViewById(R.id.ckbRemember);
    }
}