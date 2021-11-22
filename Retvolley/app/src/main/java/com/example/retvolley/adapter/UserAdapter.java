package com.example.retvolley.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.retvolley.R;
import com.example.retvolley.model.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context, List<User> object){super(context,0,object);}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        User user = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_user, parent, false );
        }
        TextView txtFullname = convertView.findViewById(R.id.txt_user_fullname);
        TextView txtEmail = convertView.findViewById(R.id.txt_user_email);
        TextView txtid = convertView.findViewById(R.id.txt_id);

        txtFullname.setText(user.getUser_fullname());
        txtEmail.setText(user.getUser_email());
        txtid.setText(user.getId());
        return  convertView;
    }
}