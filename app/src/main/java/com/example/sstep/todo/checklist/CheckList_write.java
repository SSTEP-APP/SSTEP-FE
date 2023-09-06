package com.example.sstep.todo.checklist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.CalendarDialog;
import com.example.sstep.R;
import com.example.sstep.document.certificate.PaperHinput;
import com.example.sstep.todo.checklist.checklist_api.CategoryApiService;
import com.example.sstep.todo.checklist.checklist_api.CategoryRequestDto;
import com.example.sstep.todo.checklist.checklist_api.CategoryResponseDto;
import com.example.sstep.todo.checklist.checklist_api.CheckListManagerRequestDto;
import com.example.sstep.todo.checklist.checklist_api.ChecklistApiService;
import com.example.sstep.todo.checklist.checklist_api.ChecklistRequestDto;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.MemberRequestDto;
import com.example.sstep.user.member.MemberResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import lombok.ToString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CheckList_write extends AppCompatActivity {
    private static final String TAG = "CheckList_write";
    private RecyclerView mRecyclerView;
    private ArrayList<CheckList_write_recyclerViewItem> mList;
    private CheckList_write_RecyclerViewAdpater mRecyclerViewAdapter;
    private String selectedItem;

    RadioButton firstCateRB, secondCateRb, thirdCateRb;
    ImageButton backBtn;
    EditText title, content;
    CheckBox endTimeCB , pictureCB, staffCB;
    Button addEndTimeBtn, addStaffBtn, completeBtn, addCategoryBtn;
    LinearLayout staffList_layout, repeatLayout;
    RadioGroup repeatRG, categoryRG;
    Spinner repeatTimeSpinner, repeatStartSpinner, repeatEndSpinner;
    private CheckList_write_Spinner spinnerAdapter;
    private List<String> list = new ArrayList<>();
    boolean completeBtnState;
    Dialog showComplete_dialog, addCategory_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;
    String today, endDay;

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
        addCategoryBtn = findViewById(R.id.checkList_write_categoryAddBtn);
        categoryRG = findViewById(R.id.checkList_write_categoryRG);
        baseDialog_okCenter = new BaseDialog_OkCenter(CheckList_write.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(CheckList_write.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        addCategory_dialog = new Dialog(CheckList_write.this);
        addCategory_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        addCategory_dialog.setContentView(R.layout.searchstore_dl); // xml 레이아웃 파일과 연결
        firstCateRB = findViewById(R.id.checkList_write_firstCategoryBtn);
        secondCateRb = findViewById(R.id.checkList_write_secondCategoryBtn);
        thirdCateRb= findViewById(R.id.checkList_write_thirdCategoryBtn);

        updateRB();

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
                    addEndTimeBtn.setVisibility(View.INVISIBLE);

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

        //마감예정시간 등록
        addEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendarDialog();
            }
        });


        //카테고리 추가 버튼
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(thirdCateRb.getText().toString().equals("등록해주세요")){
                    showAddCategoryDl();
                }else{
                    Toast.makeText(CheckList_write.this, "카테고리가 전부 등록 되어있습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //카테고리 라디오 버튼 설정


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
        join_okdl_commentTv.setText("로딩 중");
        Date currentDate = new Date();

        // SimpleDateFormat을 사용하여 날짜를 "yyyy년 MM월 dd일" 형식의 문자열로 포맷합니다.
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
        String formattedDate1 = sdf1.format(currentDate);
        String selectedText;
        try {
            //오늘날짜 가져오기
            if (formattedDate1 != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
                try {
                    Date formattedDate= sdf.parse(formattedDate1);

                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDateStr = outputFormat.format(formattedDate);

                    today = formattedDateStr;
                    //startDay = java.sql.Date.valueOf(formattedDateStr);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //카테고리에서 선택된 이름 가져오기
            int selectedRadioButtonId = categoryRG.getCheckedRadioButtonId();

            if (selectedRadioButtonId != -1) {
                // 라디오 버튼 그룹에서 선택된 라디오 버튼을 찾음
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

                // 선택된 라디오 버튼의 텍스트를 가져옴
                selectedText = selectedRadioButton.getText().toString();

                // 선택된 텍스트를 사용하여 작업 수행
                // 예: TextView에 표시하거나 다른 처리 수행
            } else {
                // 어떤 라디오 버튼도 선택되지 않았을 때 처리
                // 예: 사용자에게 알림 메시지 표시
                Toast.makeText(this, "라디오 버튼을 선택해주세요.", Toast.LENGTH_SHORT).show();
                return; // 코드 실행 종료
            }



            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            ChecklistApiService apiService = retrofit.create(ChecklistApiService.class);
// 등록에 필요한 데이터를 ChecklistRequestDto 객체로 생성
            ChecklistRequestDto checklistRequestDto = new ChecklistRequestDto(
                    title.getText().toString().trim(),
                    today,
                    content.getText().toString().trim(),
                    endDay,
                    pictureCB.isChecked(),
                    false,
                    selectedText
            );

// 등록 요청을 서버에 전송
            Call<Void> call = apiService.registerCheckList(19L,checklistRequestDto);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // 등록 성공
                        join_okdl_commentTv.setText("해야할 일 등록이 추가하였습니다.");
                        // 응답을 필요에 따라 처리하세요.
                    } else {
                        // 등록 실패
                        int statusCode = response.code();
                        join_okdl_commentTv.setText("해야할 일 등록이 실패했습니다.\n 오류코드: " + statusCode);
                        // 에러 응답을 처리하세요.
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // 네트워크 오류나 기타 이유로 등록 실패
                    String errorMessage = t != null ? t.getMessage() : "Unknown error";

                        join_okdl_commentTv.setText("할일 등록이 실패했습니다!!\n 오류메시지: " + errorMessage);
                        t.printStackTrace();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }


        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //카테고리 추가 설정
    public void showAddCategoryDl() {
        addCategory_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        addCategory_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView addCategory_dialog_commentTv;
        Button addCategory_dialog_okBtn;
        EditText addCategoryEt;
        addCategory_dialog_commentTv = addCategory_dialog.findViewById(R.id.searchstore_dl_textView);
        addCategory_dialog_okBtn = addCategory_dialog.findViewById(R.id.searchstore_dl_okBtn);
        addCategoryEt = addCategory_dialog.findViewById(R.id.searchstore_dl_numEt);
        addCategoryEt.setInputType(InputType.TYPE_CLASS_TEXT);
        addCategory_dialog_commentTv.setText("추가 할 카테고리를 입력해주세요.");
        addCategoryEt.setHint("카테고리를 입력해 주세요");

        addCategory_dialog_okBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    String cateName = addCategoryEt.getText().toString().trim();
                    addCategory_dialog.dismiss();
                    // 사용자가 유효하지 않은 값을 입력한 경우 예외 처리
                    showComplete_dialog.show();
                    // 다이얼로그의 배경을 투명으로 만든다.
                    showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView join_okdl_commentTv;
                    Button join_okdl_okBtn;
                    join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
                    join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
                    //네트워크 요청 구현
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    CategoryApiService apiService = retrofit.create(CategoryApiService.class);
                    // 등록에 필요한 데이터를 ChecklistRequestDto 객체로 생성
                    CategoryRequestDto categoryRequestDto = new CategoryRequestDto(
                            cateName
                    );

                    // 등록 요청을 서버에 전송
                    Call<Void> call = apiService.saveCategory(1L, categoryRequestDto);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                // 등록 성공
                                join_okdl_commentTv.setText("카테고리 추가가 성공했습니다.");
                                // 응답을 필요에 따라 처리하세요.
                            } else {
                                // 등록 실패
                                int statusCode = response.code();
                                join_okdl_commentTv.setText("카테고리 추가가 실패했습니다.\n 오류코드: " + statusCode+response.body());
                                // 에러 응답을 처리하세요.
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // 네트워크 오류나 기타 이유로 등록 실패
                            String errorMessage = t != null ? t.getMessage() : "Unknown error";

                            join_okdl_commentTv.setText("카테고리 추가 실패!!\n 오류메시지: " + errorMessage);
                            t.printStackTrace();
                        }
                    });
                    join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateRB();
                            showComplete_dialog.dismiss();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //라디오 버튼 업데이트
    private void updateRB() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CategoryApiService apiService = retrofit.create(CategoryApiService.class);
        // 등록에 필요한 데이터를 ChecklistRequestDto 객체로 생성

        // 등록 요청을 서버에 전송
        Call<Set<CategoryResponseDto>> call = apiService.getCategories(1L);

        call.enqueue(new Callback<Set<CategoryResponseDto>>() {
            @Override
            public void onResponse(Call<Set<CategoryResponseDto>> call, Response<Set<CategoryResponseDto>> response) {
                if (response.isSuccessful()) {
                    Set<CategoryResponseDto> categories = response.body();
                    if (!categories.isEmpty()) {
                        Iterator<CategoryResponseDto> iterator = categories.iterator();

                        // 첫 번째 카테고리 가져오기
                        if (iterator.hasNext()) {
                            CategoryResponseDto firstCategory = iterator.next();
                            firstCateRB.setText(firstCategory.getName());
                        }

                        // 두 번째 카테고리 가져오기
                        if (iterator.hasNext()) {
                            CategoryResponseDto secondCategory = iterator.next();
                            secondCateRb.setText(secondCategory.getName());
                        }else {
                            secondCateRb.setText("등록해주세요");
                            secondCateRb.setVisibility(View.INVISIBLE);
                        }

                        // 세 번째 카테고리 가져오기
                        if (iterator.hasNext()) {
                            CategoryResponseDto thirdCategory = iterator.next();
                            thirdCateRb.setText(thirdCategory.getName());
                        }else {
                            thirdCateRb.setText("등록해주세요");
                            thirdCateRb.setVisibility(View.INVISIBLE);
                        }
                    }
                } else {
                    Toast.makeText(CheckList_write.this, "에러발생"+response.body().toString(), Toast.LENGTH_SHORT).show();
                    // 에러 응답을 처리하세요.
                }
            }

            @Override
            public void onFailure(Call<Set<CategoryResponseDto>> call, Throwable t) {
                // 네트워크 오류나 기타 이유로 등록 실패
                Toast.makeText(CheckList_write.this, "에러발생"+t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }

    // 달력 다이얼로그 띄우기
    public void showCalendarDialog() {
        CalendarDialog calendarDialog = new CalendarDialog(CheckList_write.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 사용자가 날짜를 선택하면 호출되는 콜백 메서드
                        // 여기에 선택한 날짜 처리 코드를 작성합니다.
                        String dateString = year + "-" + (month + 1) + "-" + dayOfMonth;
                        addEndTimeBtn.setText(dateString);

                    }
                });

        // 다이얼로그 띄우기
        calendarDialog.show();
    }


}