package edu.huflit.myapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.huflit.myapp.Model.Photo;
import edu.huflit.myapp.Model.ThongTin;
import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.Model.Users;
import edu.huflit.myapp.adapter.NavigationAdapter;
import edu.huflit.myapp.adapter.PhotoAdater;
import edu.huflit.myapp.adapter.ThongTinAdapter;
import edu.huflit.myapp.adapter.TruyenTranhAdapter;
import edu.huflit.myapp.database.dtbApp;

public class Home extends AppCompatActivity {
    public static GridView gridView;
    TruyenTranhAdapter adapter;
    public static ArrayList<TruyenTranh> tranhArrayList;

    Toolbar toolbar;

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    private ViewPager viewPager;
    private PhotoAdater photoAdater;

    private List<Photo> mListPhoto;

    private Timer mTimer;

    EditText edTSearch,edtName,edtEmail;

    String email,tentaikhoan,pass;

    ListView listviewthongtin, listviewmanhinhchinh;

    ArrayList<ThongTin> navigationsArrayList;
    ArrayList<Users> usersArrayList;

    NavigationAdapter NavigationAdapter;
    ThongTinAdapter ThongTinAdapter;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public static dtbApp dtbapp;
    int pk, idus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Lấy dữ liệu từ trang Login qua
        email = getIntent().getStringExtra("Email");
        tentaikhoan = getIntent().getStringExtra("TaiKhoan");
        idus = getIntent().getIntExtra("Id",0);
        pk = getIntent().getIntExtra("phanquyen", 2);
        pass = getIntent().getStringExtra("MK");

        AnhXa();
        dtbapp = new dtbApp(this);


        ActionBar();
        //Phương thức lấy ảnh để set lên viewpager
        mListPhoto = GetListPhoto();
        photoAdater = new PhotoAdater(this,mListPhoto);
        viewPager.setAdapter(photoAdater);
        //Hàm animation
        autoImage();
        //Hàm hiện list truyện
        Init();
        Onclick();
        SetUp();
        //Hàm tìm kiếm truyện
        SetClick();
        sp = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        editor = sp.edit();

        listviewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Thông Tin Cá Nhân
                if(i == 0) {
                    Intent intent = new Intent(Home.this, Layout_User.class);
                    intent.putExtra("Id",idus);
                    intent.putExtra("TaiKhoan",tentaikhoan);
                    intent.putExtra("Email",email);
                    intent.putExtra("PQ", pk);
                    intent.putExtra("MK", pass);
                    startActivity(intent);
                }
                //Đăng bài
                else if (i == 1) {
                    if(pk == 1) {
                        Intent intent1 = new Intent(Home.this, LayoutAdmin.class);
                        tentaikhoan= getIntent().getStringExtra("username");
                        intent1.putExtra("phanquyen", pk);
                        intent1.putExtra("userId", idus);
                        intent1.putExtra("TaiKhoan", tentaikhoan);
                        email = getIntent().getStringExtra("Email");
                        startActivity(intent1);
                    }
                    else{
                        Toast.makeText(Home.this, "Bạn không có quyền đăng bài", Toast.LENGTH_SHORT).show();
                    }
                }
                //Yêu thích
                else if (i == 2) {
                    Intent intent = new Intent(Home.this, LayoutLike.class);
                    intent.putExtra("Id",idus);
                    intent.putExtra("phanquyen", pk);
                    intent.putExtra("userId", idus);
                    intent.putExtra("TenUser", tentaikhoan);
                    startActivity(intent);
                }
                //Thể Loại
                else if (i == 3) {
                    Intent intent = new Intent(Home.this, TheLoai.class);
                    intent.putExtra("phanquyen", pk);
                    intent.putExtra("userId", idus);
                    intent.putExtra("TaiKhoan", tentaikhoan);
                    startActivity(intent);
                }
                //Đổi Mật Khẩu
                else if (i == 4) {
                    Intent intent = new Intent(Home.this,ChangePass.class);
                    intent.putExtra("Id",idus);
                    intent.putExtra("TaiKhoan",tentaikhoan);
                    intent.putExtra("Email",email);
                    intent.putExtra("phanquyen", pk);
                    startActivity(intent);
                }
                //Setting
                else if (i == 5) {
                    Intent intent = new Intent(Home.this, Setting.class);
                    startActivity(intent);
                }
                //Đăng Xuất
                else if (i == 6) {
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(Home.this, MainLogin.class));
                    finish();
                }
            }
        });
    }

    //Thiết lập icon thông qua drawerLayout
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_drop);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    // View Pager
    private List<Photo> GetListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.viewpaper_dragonball));
        list.add(new Photo(R.drawable.viewpager_onepiece));
        list.add(new Photo(R.drawable.viewpager_doraemon));
        list.add(new Photo(R.drawable.viewpager_naruto));

        return list;
    }

    // Hiện các truyện
    public void Init() {
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
        //Thực hiện khi không sử dụng
        cursor.close();
        adapter = new TruyenTranhAdapter(this,0,tranhArrayList);
        gridView.setAdapter(adapter);
    }
    private void SetUp() {
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawerLayout.closeDrawer(GravityCompat.START);
        Init();
    }

    //Phương thực gọi các biến
    private void AnhXa() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edTSearch = findViewById(R.id.edtSearch);
        gridView = findViewById(R.id.gVDSTRUYEN);
        viewPager = findViewById(R.id.viewFlipper);
        toolbar = findViewById(R.id.Toolbar);
        navigationView = findViewById(R.id.navigationView);
        listviewmanhinhchinh = findViewById(R.id.manhinhchuyenmuc);
        listviewthongtin = findViewById(R.id.manthongtin);
        drawerLayout = findViewById(R.id.drawerlayout);

        //Thông tin
        usersArrayList = new ArrayList<>();
        usersArrayList.add(new Users(tentaikhoan,email));
        ThongTinAdapter = new ThongTinAdapter(this, R.layout.navigation_thongtin, usersArrayList);
        listviewthongtin.setAdapter(ThongTinAdapter);



        //Chuyên mục
        navigationsArrayList = new ArrayList<>();
        navigationsArrayList.add(new ThongTin("Thông Tin", R.drawable.icon_login));
        navigationsArrayList.add(new ThongTin("Quản lý truyện",R.drawable.icon_dangbai));
        navigationsArrayList.add(new ThongTin("Yêu thích",R.drawable.baseline_favorite_red));
        navigationsArrayList.add(new ThongTin("Thể Loại",R.drawable.icon_theloai));
        navigationsArrayList.add(new ThongTin("Đổi mật khẩu",R.drawable.icon_doimatkhau));
        navigationsArrayList.add(new ThongTin("Setting",R.drawable.icon_setting));
        navigationsArrayList.add(new ThongTin("Đăng Xuất", R.drawable.icon_logout));

        NavigationAdapter = new NavigationAdapter(this,R.layout.layout_chuyenmuc,navigationsArrayList);
        listviewmanhinhchinh.setAdapter(NavigationAdapter);


    }

    // Thanh tìm kiếm truyện
    private void SetClick() {
        edTSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = edTSearch.getText().toString();
                adapter.SearchTruyen(s);
            }
        });
    }

    //Hiện các truyện được kéo từ database
    public void Onclick() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = dtbapp.getDataTruyen();
                if (cursor.moveToPosition(position)) {
                    // Di chuyển con trỏ đến vị trí item được chọn
                    int idTruyen = cursor.getInt(0);
                    String Ten = cursor.getString(1);
                    String tomtat = cursor.getString(2);
                    String anh = cursor.getString(3);
                    String tacgia = cursor.getString(4);
                    String theLoai = cursor.getString(5);
                    Intent i = new Intent(Home.this, Home_Detail.class);
                    i.putExtra("anh", anh);
                    i.putExtra("idTruyen", idTruyen);
                    i.putExtra("Ten", Ten);
                    i.putExtra("tomtat", tomtat);
                    i.putExtra("phanquyen", pk);
                    i.putExtra("userId", idus);
                    i.putExtra("TaiKhoan", tentaikhoan);
                    i.putExtra("tacgia", tacgia);
                    i.putExtra("TL", theLoai);
                    startActivity(i);
                }
                cursor.close();
            }
        });
    }

    // Hình ảnh tự chuyển động
    private void autoImage(){
        if(mListPhoto == null || mListPhoto.isEmpty() || viewPager == null) {
            return;
        }
        if(mTimer == null){
            mTimer = new Timer();
        }

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size()-1;
                        if(currentItem<totalItem) {
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }
                        else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}