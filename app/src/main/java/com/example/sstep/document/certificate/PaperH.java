package com.example.sstep.document.certificate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.AppInData;
import com.example.sstep.R;
import com.example.sstep.document.healthdoc_api.HealthDocApiService;
import com.example.sstep.document.healthdoc_api.HealthDocResponseDto;
import com.example.sstep.home.Home_Ceo;
import com.example.sstep.staffinvite.StaffInvite;
import com.example.sstep.staffinvite.StaffInvite_RecyclerViewAdpater;
import com.example.sstep.store.SelectStore_RecyclerViewAdpater;
import com.example.sstep.store.SelectStore_recyclerViewItem;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.example.sstep.user.staff_api.StaffInviteResponseDto;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaperH extends AppCompatActivity implements View.OnClickListener {

    ImageButton backib;
    LinearLayout reglistL,unreglistL;
    TextView regNumTv, unRegNumTv;

    long storeId;

    private RecyclerView regRecyclerView;
    private RecyclerView unRegRecyclerView;

    private PaperH_Reg_RecyclerViewAdpater regRecyclerViewAdapter;
    private PaperH_Unreg_RecyclerViewAdpater unRegRecyclerViewAdapter;

    private List<PaperH_Reg_recyclerViewItem> regList;
    private List<PaperH_Unreg_recyclerViewItem> unRegList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperh);
        backib = findViewById(R.id.paperh_backib); backib.setOnClickListener(this);
        regNumTv = findViewById(R.id.paperh_regnumTv);
        unRegNumTv = findViewById(R.id.paperh_unregnumTv);


        //storeId지정
        //AppInData appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기
        //storeId = appInData.getStoreId(); // 사용자 ID 가져오기
        storeId=1;

        regFirstInit();
        UnRegFirstInit();

        regRecyclerViewAdapter = new PaperH_Reg_RecyclerViewAdpater(regList);
        regRecyclerView.setAdapter(regRecyclerViewAdapter);
        regRecyclerView.setLayoutManager(new LinearLayoutManager(PaperH.this, RecyclerView.VERTICAL, false)); //리사이클러뷰 양식지정

        unRegRecyclerViewAdapter = new PaperH_Unreg_RecyclerViewAdpater(unRegList);
        unRegRecyclerView.setAdapter(unRegRecyclerViewAdapter);
        unRegRecyclerView.setLayoutManager(new LinearLayoutManager(PaperH.this, RecyclerView.VERTICAL, false)); //리사이클러뷰 양식지정


        //보건증 등록된 사람 보기
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    HealthDocApiService apiService = retrofit.create(HealthDocApiService.class);

                    Call<Set<HealthDocResponseDto>> call = apiService.getRegHealthDocStaffs(storeId); //storeId 삽입
                    retrofit2.Response<Set<HealthDocResponseDto>> response = call.execute();

                    if (response.isSuccessful()) {
                        final Set<HealthDocResponseDto> codeStaffs = response.body();
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

        //보건증 등록 안된 사람 보기
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    HealthDocApiService apiService = retrofit.create(HealthDocApiService.class);

                    Call<Set<HealthDocResponseDto>> call = apiService.getUnRegHealthDocStaffs(storeId); //storeId 삽입
                    retrofit2.Response<Set<HealthDocResponseDto>> response = call.execute();

                    if (response.isSuccessful()) {
                        final Set<HealthDocResponseDto> codeStaffs = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UnRegOnResume(codeStaffs);
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

        regNumTv.setText(String.valueOf(regRecyclerViewAdapter.getItemCount()));
        unRegNumTv.setText(String.valueOf(unRegRecyclerViewAdapter.getItemCount()));


        //임시파일
        regNumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getApplicationContext(), PaperHinput.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.paperh_backib: // '뒤로가기'
                intent = new Intent(getApplicationContext(), Paper.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }


    public void regFirstInit(){
        regRecyclerView = (RecyclerView) findViewById(R.id.paperh_regRV); //리사이클뷰 아이디 받기
        regList = new ArrayList<>();
    }

    protected void regOnResume(Set<HealthDocResponseDto> list) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        RegUpdateRecyclerView(list); // 원하는 업데이트 로직을 여기에 작성

        regRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신
    }


    public void RegAddItem(String name, String date){
        PaperH_Reg_recyclerViewItem item = new PaperH_Reg_recyclerViewItem();

        item.setPaperH_reg_name(name);
        item.setPaperH_reg_date(date);

        regList.add(item);
    }
    private void RegUpdateRecyclerView(Set<HealthDocResponseDto> list) {
        regList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신
        for (HealthDocResponseDto docH : list) {
            RegAddItem(docH.getName(), docH.getCheckUpDate());

        }
        regRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림

        //수정
        /*
        PaperH_Reg_RecyclerViewAdpater.setOnItemClickListener(new PaperH_Reg_RecyclerViewAdpater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 해당 아이템 레이아웃 클릭 시 처리할 코드 이쪽 수정 필!
                PaperH_Reg_recyclerViewItem item = regList.get(position);



                Intent intent = new Intent(getApplicationContext(), Home_Ceo.class); //사장, 스테프 구분 필요
                startActivity(intent);
                finish();
            }
        });

         */

    }

    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();
    }

    public void UnRegFirstInit(){
        unRegRecyclerView = (RecyclerView) findViewById(R.id.paperh_unregRV); //리사이클뷰 아이디 받기
        unRegList = new ArrayList<>();
    }

    protected void UnRegOnResume(Set<HealthDocResponseDto> list) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        UnRegUpdateRecyclerView(list); // 원하는 업데이트 로직을 여기에 작성

        unRegRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신
    }


    public void UnRegAddItem(String name){
        PaperH_Unreg_recyclerViewItem item = new PaperH_Unreg_recyclerViewItem();

        item.setPaperH_UnReg_Name(name);

        unRegList.add(item);
    }
    private void UnRegUpdateRecyclerView(Set<HealthDocResponseDto> list) {
        unRegList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신
        for (HealthDocResponseDto docH : list) {
            UnRegAddItem(docH.getName());

        }
        unRegRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림

    }


}