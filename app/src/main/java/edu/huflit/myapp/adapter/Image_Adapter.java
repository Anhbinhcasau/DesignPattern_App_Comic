package edu.huflit.myapp.adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import java.util.List;

import java.util.concurrent.BlockingDeque;

import edu.huflit.myapp.R;


public class Image_Adapter extends ArrayAdapter<Bitmap> {
    private Context context;
    private List<Bitmap> bitmaps;

    public Image_Adapter(Context context, List<Bitmap> bitmaps) {
        super(context, 0, bitmaps);
        this.context = context;
        this.bitmaps = bitmaps;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_custom_list_view_image, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.imgComic);
        Bitmap bitmap = bitmaps.get(position);
        Glide.with(getContext())
                .load(bitmap)
                .fitCenter()
                .into(imageView);
        return convertView;
    }
}