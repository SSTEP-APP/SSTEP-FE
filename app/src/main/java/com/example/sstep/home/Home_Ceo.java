package com.example.sstep.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.alarm.Alarm;
import com.example.sstep.alarm.Alarm1_RecyclerViewAdpater;
import com.example.sstep.alarm.Alarm1_recyclerViewWordItemData;
import com.example.sstep.performance.MonthState;
import com.example.sstep.staffinvite.StaffInvite;
import com.example.sstep.store.SelectStore;
import com.example.sstep.store.modifyStore;
import com.example.sstep.todo.checklist.CheckList;
import com.example.sstep.user.mypage.MyPage;

import java.util.ArrayList;

public class Home_Ceo extends AppCompatActivity implements View.OnClickListener {


    private HomeCeoCheck_RecyclerViewAdpater checkRecyclerViewAdapter;
    private RecyclerView checkRecyclerView;
    private ArrayList<HomeCeoCheck_recyclerViewWordItemData> check_list = new ArrayList<>();
    private LinearLayout check_nodataLayout, check_dataLayout;

    ImageButton menuIBtn, alarmIBtn, staffInviteCloseIBtn;
    Button selectStoreBtn, mypageBtn, checklistBtn, staffInviteBtn;;
    TextView monthstateTv, modifyStoreTv, checkNumTv;
    FrameLayout staffInviteFLayout;
    LinearLayout staffApprovalL, commutePerL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_ceo);
        menuIBtn=findViewById(R.id.homeceo_menuIBtn); menuIBtn.setOnClickListener(this);
        alarmIBtn=findViewById(R.id.homeceo_alarmIBtn); alarmIBtn.setOnClickListener(this);
        selectStoreBtn=findViewById(R.id.homeceo_selectStoreBtn); selectStoreBtn.setOnClickListener(this);
        mypageBtn=findViewById(R.id.homeceo_mypageBtn); mypageBtn.setOnClickListener(this);
        modifyStoreTv=findViewById(R.id.homeceo_modifyStoreTv); modifyStoreTv.setOnClickListener(this);
        monthstateTv=findViewById(R.id.homeceo_monthstateTv); monthstateTv.setOnClickListener(this);
        checklistBtn=findViewById(R.id.homeceo_checklistBtn); checklistBtn.setOnClickListener(this);
        staffInviteBtn=findViewById(R.id.homeceo_staffInviteBtn); staffInviteBtn.setOnClickListener(this);
        staffInviteCloseIBtn=findViewById(R.id.homeceo_staffInviteCloseIBtn); staffInviteCloseIBtn.setOnClickListener(this);
        staffInviteFLayout=findViewById(R.id.homeceo_staffInviteFLayout);
        checkNumTv=findViewById(R.id.homeceo_checkNumTv);
        staffApprovalL=findViewById(R.id.homeceo_staffApprovalL);
        commutePerL=findViewById(R.id.homeceo_commutePerL);


        // 리사이클 뷰
        check_list = HomeCeoCheck_recyclerViewWordItemData.createContactsList(16);// 리스트 갯수
        checkRecyclerView = (RecyclerView) findViewById(R.id.homeceo_checklist_recycleView);
        check_nodataLayout = (LinearLayout) findViewById(R.id.homeceo_checklist_nodataLayout);
        check_dataLayout = (LinearLayout)  findViewById(R.id.homeceo_checklist_dataLayout);
        checkRecyclerView.setHasFixedSize(true);

        try {
            if(check_list.isEmpty()){
                check_nodataLayout.setVisibility(View.VISIBLE);
                check_dataLayout.setVisibility(View.GONE);
            }else{
                check_nodataLayout.setVisibility(View.GONE);
                check_dataLayout.setVisibility(View.VISIBLE);
                checkNumTv.setText(String.valueOf(check_list.size())); // 아이템 개수 설정

                checkRecyclerViewAdapter = new HomeCeoCheck_RecyclerViewAdpater(getApplicationContext(), check_list);
                checkRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                checkRecyclerView.setAdapter(checkRecyclerViewAdapter);
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
            case R.id.homeceo_staffInviteCloseIBtn: // 멤버 초대 닫기 버튼
                staffInviteFLayout.setVisibility(View.GONE);
                staffApprovalL.setVisibility(View.GONE);
                commutePerL.setVisibility(View.GONE);
                break;
            case R.id.homeceo_menuIBtn: // 메뉴
                Animation slideInLeft = AnimationUtils.loadAnimation(Home_Ceo.this, R.anim.slide_in_left);
                menuIBtn.startAnimation(slideInLeft);

                intent = new Intent(Home_Ceo.this, Home_menu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
            case R.id.homeceo_alarmIBtn: // 알림
                intent = new Intent(getApplicationContext(), Alarm.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homeceo_selectStoreBtn: // 사업장 선택
                intent = new Intent(getApplicationContext(), SelectStore.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homeceo_mypageBtn: // 마이페이지
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homeceo_checklistBtn: // 해야할 일
                intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homeceo_modifyStoreTv: // 사업장 정보
                intent = new Intent(getApplicationContext(), modifyStore.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homeceo_monthstateTv: // 사업장 월별 현황
                intent = new Intent(getApplicationContext(), MonthState.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homeceo_staffInviteBtn: // 멤버 초대
                intent = new Intent(getApplicationContext(), StaffInvite.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}