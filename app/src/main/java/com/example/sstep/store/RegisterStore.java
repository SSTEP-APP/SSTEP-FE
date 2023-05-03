package com.example.sstep.store;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class RegisterStore extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_store);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.regiStore_addressbtn:
                break;
            default:
                break;
        }
    }
}