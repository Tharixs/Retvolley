package com.example.retvolley.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.retvolley.R;

public class VolleyActivity extends AppCompatActivity {
    private final  String TAG = getClass().getSimpleName();
    private ListView lvUserVolley;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        lvUserVolley = findViewById(R.id.lv_user_volley);
        setTitle(getString(R.string.title_activity_volley));
        getUserFromAPI();
    }
    public void actionRefresh(View view){
        getUserFromAPI();
    }
    public  void actionClose(View view){
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.retrofil,menu);
    return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(this, AddUserActivity.class);
                intent.putExtra("typeConnection","volley");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}