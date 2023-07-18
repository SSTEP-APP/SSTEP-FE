package com.example.sstep.todo.checklist;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.document.certificate.PaperHinput;

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
    Button addEndTimeBtn, addStaffBtn, completeBtn;
    LinearLayout staffList_layout, repeatLayout;
    RadioGroup repeatRG;
    Spinner repeatTimeSpinner, repeatStartSpinner, repeatEndSpinner;
    private CheckList_write_Spinner spinnerAdapter;
    private List<String> list = new ArrayList<>();
    boolean completeBtnState;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

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
        repeatLayout = findViewById(R.id.checkList_write_repeatLayout);
        repeatRG = findViewById(R.id.checkList_write_repeatRG);
        repeatTimeSpinner = findViewById(R.id.checkList_write_repeatTimespinner);
        completeBtn=findViewById(R.id.checkList_write_completeBtn);

        baseDialog_okCenter = new BaseDialog_OkCenter(CheckList_write.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(CheckList_write.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

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

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputValidity();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCompleteDl();
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
                        repeatLayout.setVisibility(View.GONE);
                        break;
                    case R.id.checkList_write_repeatRB:
                        repeatLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //반복주기 스피너
        //스피너 값 설정

        String[] repeatTerm = {"매일", "2일에 1번", "1주일에 1번"};
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

    private void checkInputValidity() {
        // 버튼 활성화&비활성화
        boolean isTitle = !title.getText().toString().trim().isEmpty(); // true

        completeBtnState = isTitle;
        if (completeBtnState==true){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }

    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("해야할 일을 추가하였습니다.");
        // '로그인 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
            }
        });
    }
}