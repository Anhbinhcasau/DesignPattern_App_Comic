package edu.huflit.myapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

import edu.huflit.myapp.Model.List_Comment;
import edu.huflit.myapp.Model.TruyenTranh;
import edu.huflit.myapp.adapter.Comment_Adapter;
import edu.huflit.myapp.database.dtbApp;

public class Comment extends AppCompatActivity {

    EditText edtComment;
    Button btnSend, btnExtComment;
    ListView lvComment;
    ArrayList<List_Comment> arrComment;
    String comment, nameUser;
    dtbApp dtbapp;
    int idTruyen, pq;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        lvComment = findViewById(R.id.lVComment);
        btnSend = findViewById(R.id.btnSend);
        btnExtComment = findViewById(R.id.btnExitComment);
        edtComment = findViewById(R.id.edtComment);
        dtbapp=new dtbApp(this);
        ShowComment();
        nameUser = getIntent().getStringExtra("TenUser");
        idTruyen = getIntent().getIntExtra("idTruyen", 0);
        pq = getIntent().getIntExtra("phanquyen", 0);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = edtComment.getText().toString();
                if(comment.equals("")){
                    Toast.makeText(Comment.this, "Hãy nhập comment", Toast.LENGTH_SHORT).show();
                }else {
                    List_Comment listComment = addComment();
                    dtbapp.comment(listComment);
                    ShowComment();
                    edtComment.setText("");
                }
            }
        });
        btnExtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public List_Comment addComment(){
        comment = edtComment.getText().toString();
        List_Comment listComment = new List_Comment(nameUser,comment,pq,idTruyen);
        return listComment;
    }
    public void ShowComment(){
        idTruyen = getIntent().getIntExtra("idTruyen", 0);
        Cursor cursor = dtbapp.getDataCommentdById(idTruyen);
        arrComment = new ArrayList<List_Comment>();
        nameUser = getIntent().getStringExtra("TenUser");
        pq = getIntent().getIntExtra("phanquyen", 0);
        while (cursor.moveToNext()){
            List_Comment listComment = new List_Comment();
            String comment = cursor.getString(1);
            String Ten =cursor.getString(2);
            int idcmt = cursor.getInt(0);
            listComment.setPq(pq);
            listComment.setNameUserN(nameUser);
            listComment.setIdCmt(idcmt);
            listComment.setComment(comment);
            listComment.setNameUser(Ten);
            arrComment.add(listComment);

        }
        cursor.close();
        Collections.reverse(arrComment);
        adapter= new Comment_Adapter(this,R.layout.layout_comment,arrComment);
        lvComment.setAdapter(adapter);
    }

}