package edu.huflit.myapp.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import edu.huflit.myapp.Model.Rating;
import edu.huflit.myapp.R;
import edu.huflit.myapp.database.dtbApp;

public class Dialog_rating extends Dialog {
    SharedPreferences rating ;
    SharedPreferences.Editor edtRating;
    private int userId;
    private int comicId;
    float userRating;
    int idRating;
    dtbApp dtbapp;
    private  float userRate = 0;
    RatingBar ratingBar;

    public Dialog_rating(@NonNull Context context, int userId, int comicId) {
        super(context);
        this.userId = userId;
        this.comicId = comicId;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_layout);

        rating = getContext().getSharedPreferences("Rating", Context.MODE_PRIVATE);
        final AppCompatButton saveRateBtn = findViewById(R.id.btnSaveRating);
        final AppCompatButton extRateBtn = findViewById(R.id.btnExtRating);
        ratingBar = findViewById(R.id.ratingBar);

        if (dtbapp == null) {
            dtbapp = new dtbApp(getContext());
        }
        Cursor cursor = dtbapp.getDataRatingByID(userId,comicId);
        if (cursor.moveToFirst()){
            userRating = cursor.getFloat(3);
            idRating = cursor.getInt(0);
            ratingBar.setRating(userRating);

        }else{
            ratingBar.setRating(userRate);
        }
        saveRateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dtbapp.getDataRatingByID(userId,comicId);
                if (cursor.moveToFirst()){
                    Rating rating1 = upRating();
                    Log.e( "onClick: ","Chỉnh sưa thanh công");
                    dtbapp.upRating(rating1);

                }else {
                    Rating rating1 = addRating();
                    Log.e( "onClick: ","Thêm Thành công");
                    dtbapp.addRating(rating1);
                    dismiss();
                }
            }
        });
        extRateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    private Rating addRating(){
        float ratingbar = ratingBar.getRating();
        Log.e( "onClick: ","Chỉnh sưa thanh công bạn đã đánh giá Truyên" + ratingbar+ "*");
        Rating rating1 = new Rating(ratingbar, userId,comicId);
        return rating1;
    }
    private  Rating upRating(){
        float ratingbar = ratingBar.getRating();
        Log.e( "onClick: ","Chỉnh sửa thanh công bạn đã đánh giá Truyện: " + ratingbar+"*");
        Rating rating1 = new Rating(idRating,ratingbar);
        Toast.makeText(ratingBar.getContext(), "Bạn đã đánh giá Truyện: " + ratingbar+"*", Toast.LENGTH_SHORT).show();
        dismiss();
        return  rating1;
    }

}
