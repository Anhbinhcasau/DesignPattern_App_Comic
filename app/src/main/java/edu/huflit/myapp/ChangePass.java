package edu.huflit.myapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.huflit.myapp.Model.Users;
import edu.huflit.myapp.database.dtbApp;


public class ChangePass extends AppCompatActivity {
    EditText edtOldPass, edtNewPass, edtRePass;
    Button btnDoi;
    String name, oldPass, newPass, rePass, email;
    dtbApp dtbapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        edtOldPass = findViewById(R.id.edtOldPass);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtRePass = findViewById(R.id.edtRepass);
        btnDoi = findViewById(R.id.btnDoi);
        dtbapp=new dtbApp(this);

        name = getIntent().getStringExtra("TaiKhoan");
        int id = getIntent().getIntExtra("Id", 0);
        email = getIntent().getStringExtra("email");
        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass = edtOldPass.getText().toString();
                newPass = edtNewPass.getText().toString();
                rePass = edtRePass.getText().toString();
                String password = dtbapp.getPasswordById(id);
                if((oldPass.trim()).equals(password.trim())){
                    if ((newPass.trim()).equals(rePass.trim())){
                        Users users = changePass();
                        dtbapp.ChangePass(users);
                        Toast.makeText(ChangePass.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else{
                        Toast.makeText(ChangePass.this, "Mật khẩu mới không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ChangePass.this, "Sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public Users changePass(){
        newPass = edtNewPass.getText().toString();
        int id = getIntent().getIntExtra("Id", 0);
        Users users = new Users(id, newPass);
        return users;
    }

}