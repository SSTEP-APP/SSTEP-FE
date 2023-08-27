package com.example.sstep.todo.notice;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.alarm.Alarm1_RecyclerViewAdpater;
import com.example.sstep.alarm.Alarm1_recyclerViewWordItemData;
import com.example.sstep.home.Home_Ceo;

import java.util.ArrayList;

public class Notice extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Notice";
    private RecyclerView mRecyclerView;
    private Notice_RecyclerViewAdpater mRecyclerViewAdapter;
    private ArrayList<Notice_recyclerViewWordItemData> list = new ArrayList<>();
    private LinearLayout nodataLayout, dataLayout;
    ImageButton plusbtn, backib;
    TextView listCountNumTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
        plusbtn = findViewById(R.id.notice_plusbtn); plusbtn.setOnClickListener(this);
        backib=findViewById(R.id.notice_backib); backib.setOnClickListener(this);
        listCountNumTv=findViewById(R.id.notice_listCountNumTv);

        // 리사이클 뷰
        list = Notice_recyclerViewWordItemData.createContactsList(6);// 리스트 갯수
        mRecyclerView = (RecyclerView) findViewById(R.id.notice_recycleView);
        nodataLayout = (LinearLayout) findViewById(R.id.notice_nodataLayout);
        dataLayout = (LinearLayout)  findViewById(R.id.notice_dataLayout);
        mRecyclerView.setHasFixedSize(true);

        try {
            if(list.isEmpty()){
                nodataLayout.setVisibility(View.VISIBLE);
                dataLayout.setVisibility(View.GONE);
            }else{
                nodataLayout.setVisibility(View.GONE);
                dataLayout.setVisibility(View.VISIBLE);
                mRecyclerViewAdapter = new Notice_RecyclerViewAdpater(getApplicationContext(), list);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(mRecyclerViewAdapter);

                // 리스트 개수를 텍스트뷰에 설정
                listCountNumTv.setText(String.valueOf(list.size()));
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("error", e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.notice_backib:
                intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
                break;
            case R.id.notice_plusbtn: // '추가 plus'버튼 선택 시
                intent = new Intent(getApplicationContext(), Notice_input.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}