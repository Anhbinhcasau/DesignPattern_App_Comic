package edu.huflit.myapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.huflit.myapp.Comment;
import edu.huflit.myapp.LayoutAdmin;
import edu.huflit.myapp.Model.List_Comment;
import edu.huflit.myapp.R;
import edu.huflit.myapp.database.dtbApp;

public class Comment_Adapter extends ArrayAdapter<List_Comment> {
    Activity context = null;
    ArrayList<List_Comment> myArray = null;
    int layoutId;
    dtbApp dtbapp;


    public Comment_Adapter(Activity context, int layoutId, ArrayList<List_Comment> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = arr;
        this.dtbapp = new dtbApp(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId,null);

        final TextView tvNameUser = (TextView) convertView.findViewById(R.id.tvNameUser);
        final TextView tvComment =  (TextView) convertView.findViewById(R.id.tvComment);
        final ImageView imgAvatarUser = (ImageView) convertView.findViewById(R.id.imgAvatarUser);
        final Button btnDelete = (Button) convertView.findViewById(R.id.btnXoaCmt);

        String image ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSIxKuXfYotFraCK2lF-hy9rJoTWlmZqtLbR-dln5dopVNTjcw_3H1MYAPVEzQdDrjecI&usqp=CAU" ;
        List_Comment emp = myArray.get(position);

        tvNameUser.setText(emp.getNameUser());
        tvComment.setText(emp.getComment());
        Glide.with(getContext()).load(image).into(imgAvatarUser);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pq = emp.getPq();
                String name = emp.getNameUser();
                String nameN = emp.getNameUserN();
                if (pq == 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn muốn xóa bình luận này?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int commentId = getItem(position).getIdCmt();
                            dtbapp.DeleteCmt(commentId);
                            remove(getItem(position));
                            notifyDataSetChanged();
                        }
                    });
                    builder.show();

                }else if (pq == 2 && name.trim().equals(nameN.trim())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn muốn xóa bình luận này?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int commentId = getItem(position).getIdCmt();
                            dtbapp.DeleteCmt(commentId);
                            remove(getItem(position));
                            notifyDataSetChanged();
                        }
                    });
                    builder.show();
                }else {
                    Toast.makeText(context, "Bạn không được xóa bình luận của người khác", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }
}
