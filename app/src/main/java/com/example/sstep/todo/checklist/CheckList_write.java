package com.example.sstep.todo.checklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;

import java.util.ArrayList;
import java.util.List;


public class CheckList_write extends AppCompatActivity {
    private static final String TAG = "CheckList_write";
    private RecyclerView mRecyclerView;
    private ArrayList<CheckList_write_recyclerViewItem> mList;
    private CheckList_write_RecyclerViewAdpater mRecyclerViewAdapter;
    private String selectedItem;

    ImageButton backBtn;
    EditText title, content;
    CheckBox endTimeCB , pictureCB, staffCB;
    Button addEndTimeBtn, addStaffBtn;
    LinearLayout staffList_layout, repeatTimeLayout, repeatPeriodLayout;
    RadioGroup repeatRG;
    Spinner repeatTimeSpinner, repeatStartSpinner, repeatEndSpinner;
    private CheckList_write_Spinner spinnerAdapter;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_list_write);
        backBtn = findViewById(R.id.checkList_write_back_Btn);
        title = findViewById(R.id.checkList_write_titleEt);
        content = findViewById(R.id.checkList_write_contentEt);
        endTimeCB = findViewById(R.id.checkList_write_endTimeRB);
        pictureCB = findViewById(R.id.checkList_write_pictureRB);
        staffCB = findViewById(R.id.checkList_write_staffRB);
        addEndTimeBtn = findViewById(R.id.checkList_write_addEndTimeBtn);
        addStaffBtn = findViewById(R.id.checkList_write_selectStaffBtn);
        staffList_layout = findViewById(R.id.checkList_write_staffList_layout);
        repeatTimeLayout = findViewById(R.id.checkList_write_repeatTimeLayout);
        repeatPeriodLayout = findViewById(R.id.checkList_write_repeatPeriodLayout);
        repeatRG = findViewById(R.id.checkList_write_repeatRG);
        repeatTimeSpinner = findViewById(R.id.checkList_write_repeatTimespinner);

        //리사이클 뷰
        firstInit();
        // 임의로 5개 입력
        //db에서 값 받아서 넣기
        for (int i = 0; i < 5; i++) {
            addItem("iconName", "cancelBtn", "staff_name");
        }

        mRecyclerViewAdapter = new CheckList_write_RecyclerViewAdpater(mList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


        //상단 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
            }
        });

        //입력사항 받기
        //title.getText();

        //마감예정 시간 체크박스
        endTimeCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    addEndTimeBtn.setVisibility(View.GONE);

                } else {
                    addEndTimeBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        //할일 사진 촬영 체크박스

        //할일 담당자 체크박스
        staffCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    addStaffBtn.setVisibility(View.VISIBLE);
                    staffList_layout.setVisibility(View.VISIBLE);


                } else {
                    addStaffBtn.setVisibility(View.GONE);
                    staffList_layout.setVisibility(View.GONE);
                }
            }
        });

        //반복여부
        repeatRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.checkList_write_norepeatRB:
                        repeatTimeLayout.setVisibility(View.GONE);
                        repeatPeriodLayout.setVisibility(View.GONE);
                        break;
                    case R.id.checkList_write_repeatRB:
                        repeatTimeLayout.setVisibility(View.VISIBLE);
                        repeatPeriodLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //반복주기 스피너
        //스피너 값 설정

        String[] repeatTerm = {"매일마다", "2일에 1번", "1주일에 1번"};
        for (int i = 0; i < repeatTerm.length; i++) {
            list.add(repeatTerm[i]);
        }
        spinnerAdapter = new CheckList_write_Spinner(this, list);
        repeatTimeSpinner.setAdapter(spinnerAdapter);
        repeatTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 어댑터에서 정의한 메서드를 통해 스피너에서 선택한 아이템의 이름을 받아온다
                selectedItem = spinnerAdapter.getItem();
                //Toast.makeText(CheckList_write.this, "선택한 아이템 : " + selectedItem, Toast.LENGTH_SHORT).show();
                // 어댑터에서 정의하는 게 귀찮다면 아래처럼 구할 수도 있다
                // getItemAtPosition()의 리턴형은 Object이므로 String 캐스팅이 필요하다
                String otherItem = (String) repeatTimeSpinner.getItemAtPosition(i);
                //Log.e(TAG, "getItemAtPosition() - 선택한 아이템 : " + otherItem);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //반복시작일 스피너?


        //스피너 값 설정


    }


    //
    public void firstInit(){
        mRecyclerView = (RecyclerView) findViewById(R.id.checkList_write_staffList_recycleView);
        mList = new ArrayList<>();
    }

    public void addItem(String imgName, String mainText, String subText){
        CheckList_write_recyclerViewItem item = new CheckList_write_recyclerViewItem();

        item.setCheckList_write_Img(imgName);
        item.setCheckList_write_cancelImg(mainText);
        item.setChecklist_write_staffName(subText);

        mList.add(item);
    }
}