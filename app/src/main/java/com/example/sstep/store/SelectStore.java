package com.example.sstep.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class SelectStore extends AppCompatActivity implements View.OnClickListener {

    Button storeregBtn;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectstore);

        storeregBtn = findViewById(R.id.selectstore_storeregBtn); storeregBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectstore_storeregBtn:
                intent = new Intent(getApplicationContext(), RegisterStore.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}