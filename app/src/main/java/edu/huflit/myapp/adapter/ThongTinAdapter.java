package edu.huflit.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.huflit.myapp.Model.Users;
import edu.huflit.myapp.R;

public class ThongTinAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Users> usersList;

    public ThongTinAdapter(Context context, int layout, List<Users> usersList) {
        this.context = context;
        this.layout = layout;
        this.usersList = usersList;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        TextView tvName = (TextView) view.findViewById(R.id.ten_user);
        TextView tvEmail = (TextView) view.findViewById(R.id.email_user);
        Users users = usersList.get(i);
        tvName.setText(users.getTenTaiKhoan());
        tvEmail.setText(users.getEmail());
        return view;
    }
}
