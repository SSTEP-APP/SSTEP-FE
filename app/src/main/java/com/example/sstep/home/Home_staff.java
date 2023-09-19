package com.example.sstep.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.AppInData;
import com.example.sstep.R;
import com.example.sstep.alarm.Alarm;
import com.example.sstep.commute.Commute_map;
import com.example.sstep.store.SelectStore;
import com.example.sstep.todo.checklist.Checklist_detail;
import com.example.sstep.todo.checklist.checklist_api.CheckListResponseDto;
import com.example.sstep.todo.checklist.checklist_api.ChecklistApiService;
import com.example.sstep.user.mypage.MyPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home_staff extends AppCompatActivity implements View.OnClickListener {

    Button commuteBtn, mypageBtn, selectStoreBtn;
    ImageButton menuIBtn, alarmIBtn;

    boolean isGoingToWork = true; // 초기값 설정

    private RecyclerView checkListRecyclerView;

    private Home_Ceo_checkList_RecyclerViewAdpater checkListRecyclerViewAdapter;

    private List<Home_Ceo_checkList_recyclerViewItem> checkListList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_staff);

        alarmIBtn=findViewById(R.id.homestaff_alarmIBtn); alarmIBtn.setOnClickListener(this);
        selectStoreBtn=findViewById(R.id.homestaff_selectStoreBtn); selectStoreBtn.setOnClickListener(this);
        mypageBtn=findViewById(R.id.homestaff_mypageBtn); mypageBtn.setOnClickListener(this);
        commuteBtn=findViewById(R.id.homestaff_commuteBtn); commuteBtn.setOnClickListener(this);
        menuIBtn=findViewById(R.id.homestaff_menuIBtn); menuIBtn.setOnClickListener(this);

        // Commute_map으로부터 전달된 값 읽기
        isGoingToWork = getIntent().getBooleanExtra("isGoingToWork", true);
        if (isGoingToWork) {
            commuteBtn.setText("출근하기");
        } else {
            commuteBtn.setText("퇴근하기");
        }
        // ID값, storeId가지고 오기
        AppInData loginData = (AppInData) getApplication(); // AppInData 클래스의 인스턴스 가져오기
        String userId = loginData.getUserId(); // 사용자 ID 가져오기
        long storeCode = loginData.getStoreCode();
        long storeId = loginData.getStoreId();
        long staffId= loginData.getStaffId();

        regFirstInit();

        checkListRecyclerViewAdapter = new Home_Ceo_checkList_RecyclerViewAdpater(checkListList);
        checkListRecyclerView.setAdapter(checkListRecyclerViewAdapter);
        checkListRecyclerView.setLayoutManager(new LinearLayoutManager(Home_staff.this, RecyclerView.VERTICAL, false)); //리사이클러뷰 양식지정

        //체크리스트 리사이클러뷰 값 불러오기
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ChecklistApiService apiService = retrofit.create(ChecklistApiService.class);


                    Call<Set<CheckListResponseDto>> call = apiService.getStoreByStaffUnCompletedCheckListsByCategory(staffId);

                    retrofit2.Response<Set<CheckListResponseDto>> response = call.execute();

                    if (response.isSuccessful()) {
                        final Set<CheckListResponseDto> codeStaffs = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                regOnResume(codeStaffs);
                            }
                        });
                    } else {

                    }
                } catch (Exception e) {
                    final String errorMsg = e.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleError(errorMsg);
                        }
                    });
                }
            }

        }).start();

        LinearLayout checkListLL = findViewById(R.id.homestaff_checkListLL);
        if(checkListRecyclerViewAdapter.getItemCount() == 0){
            checkListLL.setVisibility(View.VISIBLE);
            checkListRecyclerView.setVisibility(View.GONE);
        }else{
            TextView checkNumTv;
            checkListLL.setVisibility(View.GONE);
            checkListRecyclerView.setVisibility(View.VISIBLE);
            checkNumTv = findViewById(R.id.homestaff_checkNumTv);
            checkNumTv.setText(checkListRecyclerViewAdapter.getItemCount());


        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.homestaff_menuIBtn: // 메뉴
                Animation slideInLeft = AnimationUtils.loadAnimation(Home_staff.this, R.anim.slide_in_left);
                menuIBtn.startAnimation(slideInLeft);

                intent = new Intent(Home_staff.this, Home_menu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
            case R.id.homestaff_commuteBtn: // 출근하기
                if (commuteBtn.getText().toString().equals("출근하기")) {
                    intent = new Intent(getApplicationContext(), Commute_map.class);
                    intent.putExtra("inout", "in");
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(getApplicationContext(), Commute_map.class);
                    intent.putExtra("inout", "out");
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.homestaff_selectStoreBtn: // 사업장 선택
                intent = new Intent(getApplicationContext(), SelectStore.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homestaff_mypageBtn: // 마이페이지
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homestaff_alarmIBtn: // 알림
                intent = new Intent(getApplicationContext(), Alarm.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
    public void regFirstInit(){
        checkListRecyclerView = (RecyclerView) findViewById(R.id.homestaff_checklist_recycleView); //리사이클뷰 아이디 받기
        checkListList = new ArrayList<>();
    }

    protected void regOnResume(Set<CheckListResponseDto> list) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        RegUpdateRecyclerView(list); // 원하는 업데이트 로직을 여기에 작성

        checkListRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신
    }


    public void RegAddItem(String name, String date,long id){
        Home_Ceo_checkList_recyclerViewItem item = new Home_Ceo_checkList_recyclerViewItem();

        item.setHome_Check_title(name);
        item.setHome_Check_date(date);
        item.setHome_Check_checkListId(id);

        checkListList.add(item);
    }
    private void  RegUpdateRecyclerView(Set<CheckListResponseDto> list) {
        checkListList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신
        for (CheckListResponseDto clRes : list) {
            RegAddItem(clRes.getTitle(), clRes.getDate(),clRes.getId());
        }

        Home_Ceo_checkList_RecyclerViewAdpater.setOnItemClickListener(new Home_Ceo_checkList_RecyclerViewAdpater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 해당 아이템 레이아웃 클릭 시 처리할 코드 이쪽 수정 필!
                Home_Ceo_checkList_recyclerViewItem item = checkListList.get(position);
                Intent intent = new Intent(getApplicationContext(), Checklist_detail.class); //사장, 스테프 구분 필요
                //체크리스트 id 받아서 넘기기
                intent.putExtra("checkListId", item.getHome_Check_checkListId());
                //intent.putExtra("staffId", item.getPaperH_reg_staffId);

                startActivity(intent);
                finish();
            }
        });


        checkListRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림

    }

    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();
    }

}