package edu.huflit.myapp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.myapp.Model.List_Chapter;
import edu.huflit.myapp.Model.TapTruyen;
import edu.huflit.myapp.adapter.Chapter_Adapter;

import edu.huflit.myapp.adapter.Image_Adapter;

import edu.huflit.myapp.database.dtbApp;


public class Read_Book extends AppCompatActivity {

    private RelativeLayout rlTopBar, rlBottomBar;

    private boolean hidden = true;

    ListView lvComic, mlvChapter;
    Button btnShowChapter, btnExit, btnThemTap,btnPreviousPage, btnNextPage, btnThemAnh, btnRefresh;

    String tenUser, tenTruyen, tap;
    int istap, pq, Tap, IDtruyen;
    dtbApp dtbapp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        AnhXa();

        pq = getIntent().getIntExtra("phanquyen",0);
        tenTruyen = getIntent().getStringExtra("TenTruyen");
        tenUser = getIntent().getStringExtra("TenUser");
        tap = Integer.toString(getIntent().getIntExtra("Tap", 0));
        IDtruyen = getIntent().getIntExtra("idTruyen", 0);

        ShowTap();



        if (pq == 1){
            btnThemAnh.setVisibility(View.VISIBLE);
            btnThemTap.setVisibility(View.VISIBLE);
        }else{
            btnThemAnh.setVisibility(View.INVISIBLE);
            btnThemTap.setVisibility(View.GONE);
            Toast.makeText(this, "Bạn đang đọc truyên "+ tenTruyen+" Tập "+ tap, Toast.LENGTH_SHORT).show();
        }
        btnThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImages();
            }
        });

        //link tới fireebase storage
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://truyen-9f7f6.appspot.com/");
        // tới folder thứ nhất tên truyện
        StorageReference storageRef = storage.getReference().child(tenTruyen);
        // tới folder thứ con Tập số ...
        StorageReference imageRef = storageRef.child("Tập " + tap);

        // lấy Toàn bộ danh sách ảnh có tong folder tập
        imageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                List<StorageReference> imageRefs = listResult.getItems();
                List<Task<byte[]>> tasks = new ArrayList<>();
                for (StorageReference imageRef : imageRefs) {
                    tasks.add(imageRef.getBytes(Long.MAX_VALUE));
                }
                Tasks.whenAllSuccess(tasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> objects) {
                        List<Bitmap> bitmaps = new ArrayList<>();
                        for (Object object : objects) {
                            byte[] bytes = (byte[]) object;
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps.add(bitmap);
                        }
                        Image_Adapter adapterHinh = new Image_Adapter(Read_Book.this, bitmaps);
                        lvComic.setAdapter(adapterHinh);
                    }
                });
            }
        });
        ClickTap();
        btnShowChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerChapter = findViewById(R.id.drawer_chapter);
                drawerChapter.openDrawer((GravityCompat.END));
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //kiem tra neu la tap 1 thi an btnPreviousPage
        Tap = Integer.parseInt(tap);
        if (Tap == 1){
            btnPreviousPage.setVisibility(View.INVISIBLE);
        }
        btnPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Tap = Integer.parseInt(tap);
                Tap -= 1;
                Intent i = new Intent(Read_Book.this, Read_Book.class);
                i.putExtra("Tap", Tap);
                i.putExtra("TenTruyen", tenTruyen);
                i.putExtra("TenUser", tenUser);
                i.putExtra("phanquyen", pq);
                i.putExtra("idTruyen", IDtruyen);
                startActivity(i);
                finish();
            }
        });
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dtbapp.getDataTap();
                int Tap = Integer.parseInt(tap);
                Tap += 1;
                while (cursor.moveToNext()){
                    int IdTruyen = Integer.parseInt(cursor.getString(2));
                    if (IdTruyen == IDtruyen){
                        istap = cursor.getInt(1);
                    }
                }
                istap +=1;
                if (Tap != istap){
                    Intent i = new Intent(Read_Book.this, Read_Book.class);
                    i.putExtra("Tap", Tap);
                    i.putExtra("TenTruyen", tenTruyen);
                    i.putExtra("TenUser", tenUser);
                    i.putExtra("phanquyen", pq);
                    i.putExtra("idTruyen", IDtruyen);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Read_Book.this, "Bạn đang đọc tập mới nhất", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Read_Book.this, Read_Book.class);
                i.putExtra("Tap", Tap);
                i.putExtra("TenTruyen", tenTruyen);
                i.putExtra("TenUser", tenUser);
                i.putExtra("phanquyen", pq);
                i.putExtra("idTruyen", IDtruyen);
                startActivity(i);
                finish();
            }
        });

        lvComic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private static final long DOUBLE_CLICK_TIME_DELTA = 300; //Thời gian giữa 2 lần click (0.3s)
            private long lastClickTime = 0; // Khởi tạo giá trị bằng 0
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // gán thời gian hiện tại
                long clickTime = System.currentTimeMillis();
                if(clickTime - lastClickTime <= DOUBLE_CLICK_TIME_DELTA){
                    if (hidden && pq == 1){
                        btnThemAnh.setVisibility(View.INVISIBLE);
                    }
                    else if(hidden == false && pq == 1){
                        btnThemAnh.setVisibility(View.VISIBLE);
                    }
                    if(hidden){
                        rlBottomBar.setVisibility(View.INVISIBLE);
                        rlTopBar.setVisibility(View.INVISIBLE);
                        hidden = false;
                    }else{
                        rlBottomBar.setVisibility(View.VISIBLE);
                        rlTopBar.setVisibility(View.VISIBLE);
                        hidden = true;
                    }
                }
                lastClickTime = clickTime;
            }
        });
        btnThemTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Read_Book.this, ThemTap.class);
                i.putExtra("idTruyen", IDtruyen);
                startActivity(i);
            }
        });
    }
    public void AnhXa(){
        btnThemTap = findViewById(R.id.btnThemTap);
        btnThemAnh = findViewById(R.id.btnThemAnh);
        btnShowChapter = (Button) findViewById(R.id.btnShowChapter);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnNextPage = findViewById(R.id.btnNextPage);
        btnPreviousPage = findViewById(R.id.btnPreviousPage);
        btnRefresh = findViewById(R.id.btnRefresh);
        lvComic = findViewById(R.id.lvComic);
        rlTopBar = findViewById(R.id.rlTopBar);
        rlBottomBar= findViewById(R.id.rlBottomBar);
        mlvChapter = (ListView) findViewById(R.id.lvChapter);
        dtbapp = new dtbApp(this);
    }
    private static final int REQUEST_CODE_PICK_IMAGES = 101;
    private void pickImages() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Cho phép chọn nhiều ảnh
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGES && resultCode == RESULT_OK) {
            if (data.getClipData() != null) { // Nếu người dùng đã chọn nhiều ảnh
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    uploadImageToFirebase(imageUri); // Tải từng ảnh lên Firebase Storage
                }
            } else if (data.getData() != null) { // Nếu người dùng chỉ chọn một ảnh
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri); // Tải ảnh lên Firebase Storage
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://truyen-9f7f6.appspot.com/");
        StorageReference storageRef = storage.getReference().child(tenTruyen);
        StorageReference imageRef = storageRef.child("Tập "+tap+"/" + imageUri.getLastPathSegment());
        imageRef.putFile(imageUri);
    }
    public void ClickTap() {
        mlvChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = dtbapp.getDataTapByIDTruyen(IDtruyen);
                if (cursor.moveToPosition(position)) {
                    int tenTap = Integer.parseInt(cursor.getString(1));
                    Intent i = new Intent(Read_Book.this, Read_Book.class);
                    i.putExtra("Tap", tenTap);
                    i.putExtra("TenTruyen", tenTruyen);
                    i.putExtra("TenUser", tenUser);
                    i.putExtra("phanquyen", pq);
                    i.putExtra("idTruyen", IDtruyen);
                    finish();
                    startActivity(i);
                }
                cursor.close();
            }
        });
    }
    public void ShowTap(){
        Cursor cursor = dtbapp.getDataTap();
        List<String> items = new ArrayList<String>();
        while (cursor.moveToNext()){
            int IdTruyen = Integer.parseInt(cursor.getString(2));
            if (IdTruyen == IDtruyen){
                String TenTap = Integer.toString(cursor.getInt(1));
                items.add("Tập "+ TenTap);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        mlvChapter.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowTap();
    }
}