package edu.huflit.myapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.Model.YeuThich;
import edu.huflit.myapp.database.dtbApp;

public class ThemTruyen extends AppCompatActivity {

    public String item;
    EditText  edtTieuDe, edtNoiDung, edtIMG, edtTacGia;
    Button btnThem;
    Spinner spinner;
    ImageView imgTruyen;
    dtbApp dbApp;
    private static int id, idLike = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_truyen);
        edtNoiDung = findViewById(R.id.edtNoiDungTruyen);
        edtTieuDe = findViewById(R.id.edtTieuDe);
        edtIMG = findViewById(R.id.edtIMG);
        btnThem = findViewById(R.id.btnThem);
        edtTacGia = findViewById(R.id.edtTacGia);
        spinner = findViewById(R.id.spinnerTL);

        //
        dbApp = new dtbApp(this);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tieuDe = edtTieuDe.getText().toString();
                String noiDung = edtNoiDung.getText().toString();
                String img = edtIMG.getText().toString();
                String tacGia = edtTacGia.getText().toString();
                TruyenTranh truyenTranh = CreatTruyen();
                if(tieuDe.equals("") || noiDung.equals("") || img.equals("") || tacGia.equals(""))
                {
                    Toast.makeText(ThemTruyen.this, "Đừng bỏ trống nhé@@", Toast.LENGTH_SHORT).show();
                    Log.e("ERR","Hãy nhập đầy đủ thong tin");
                }
                else {
                    dbApp.Addtruyen(truyenTranh);
                    Toast.makeText(ThemTruyen.this, "Thêm truyện thành công!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
//                FirebaseStorage storage = FirebaseStorage.getInstance("gs://truyen-9f7f6.appspot.com");
//                StorageReference folderRef = storage.getReference().child(tieuDe);
//                byte[] emptyBytes = new byte[0];
//                folderRef.child("new.txt").putBytes(emptyBytes);

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getListData());
        spinner.setAdapter(adapter);

    }
    private TruyenTranh CreatTruyen(){
        String tieuDe = edtTieuDe.getText().toString();
        String noiDung = edtNoiDung.getText().toString();
        String img = edtIMG.getText().toString();
        String tacGia = edtTacGia.getText().toString();

        Intent intent = getIntent();
        id = intent.getIntExtra("idTruyen",0);
        TruyenTranh truyenTranh = new TruyenTranh();
        truyenTranh.setIdTruyen(id);
        truyenTranh.setTenTruyen(tieuDe);
        truyenTranh.setLinkAnh(img);
        truyenTranh.setNoiDungTruyen(noiDung);
        truyenTranh.setTacGia(tacGia);
        truyenTranh.setIdLike(idLike);
        truyenTranh.setCate((String) spinner.getSelectedItem());
        return truyenTranh;
    }
    @Override
    protected void onActivityResult(int request, int result, @Nullable Intent data)
    {
        super.onActivityResult(request, result, data);
        if (result == RESULT_OK && request == 1) {
            Uri uri = data.getData();
            imgTruyen.setImageURI(uri);
        }
    }
    private List<String> getListData() {
        List<String> list = new ArrayList<String>();
        Cursor cursor = dbApp.getDataCate();
        while (cursor.moveToNext()){
            String value = cursor.getString(1);
            list.add(value);
        }
        cursor.moveToFirst();
        cursor.close();

        return list;
    }
}