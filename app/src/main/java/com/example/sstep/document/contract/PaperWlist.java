package com.example.sstep.document.contract;

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

import com.example.sstep.R;
import com.example.sstep.document.certificate.Paper;
import com.example.sstep.document.certificate.PaperH_Reg_recyclerViewItem;
import com.example.sstep.document.certificate.PaperH_Unreg_recyclerViewItem;
import com.example.sstep.document.healthdoc_api.HealthDocResponseDto;
import com.example.sstep.document.work_doc_api.WorkDocApiService;
import com.example.sstep.document.work_doc_api.WorkDocResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaperWlist extends AppCompatActivity implements View.OnClickListener{

    LinearLayout reglistL, unreglistL;
    ImageButton backib;

    TextView regNumTv, unRegNumTv;

    long storeId;

    private RecyclerView regRecyclerView;
    private RecyclerView unRegRecyclerView;

    private PaperW_Com_RecyclerViewAdpater regRecyclerViewAdapter;
    private PaperW_UnCom_RecyclerViewAdpater unRegRecyclerViewAdapter;

    private List<PaperW_Com_recyclerViewItem> regList;
    private List<PaperW_UnCom_recyclerViewItem> unRegList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperwlist);

        //reglistL=findViewById(R.id.paperwlist_reglistL); reglistL.setOnClickListener(this);
        //unreglistL=findViewById(R.id.paperwlist_unreglistL); unreglistL.setOnClickListener(this);
        backib=findViewById(R.id.paperwlist_backib); backib.setOnClickListener(this);

        storeId = 3;

        regFirstInit();
        UnRegFirstInit();

        regRecyclerViewAdapter = new PaperW_Com_RecyclerViewAdpater(regList);
        regRecyclerView.setAdapter(regRecyclerViewAdapter);
        regRecyclerView.setLayoutManager(new LinearLayoutManager(PaperWlist.this, RecyclerView.VERTICAL, false)); //리사이클러뷰 양식지정

        unRegRecyclerViewAdapter = new PaperW_UnCom_RecyclerViewAdpater(unRegList);
        unRegRecyclerView.setAdapter(unRegRecyclerViewAdapter);
        unRegRecyclerView.setLayoutManager(new LinearLayoutManager(PaperWlist.this, RecyclerView.VERTICAL, false)); //리사이클러뷰 양식지정

        regNumTv = findViewById(R.id.paperwlist_regnumTv);
        unRegNumTv = findViewById(R.id.paperwlist_unregnumTv);

        TextView tv = findViewById(R.id.paperwlist_regTv);


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
                    WorkDocApiService apiService = retrofit.create(WorkDocApiService.class);

                    Call<Set<WorkDocResponseDto>> call = apiService.getRegWorkDocStaffs(storeId); //storeId 삽입
                    retrofit2.Response<Set<WorkDocResponseDto>> response = call.execute();

                    if (response.isSuccessful()) {
                        final Set<WorkDocResponseDto> codeStaffs = response.body();
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
                    WorkDocApiService apiService = retrofit.create(WorkDocApiService.class);

                    Call<Set<WorkDocResponseDto>> call = apiService.getUnRegWorkDocStaffs(storeId); //storeId 삽입
                    retrofit2.Response<Set<WorkDocResponseDto>> response = call.execute();

                    if (response.isSuccessful()) {
                        final Set<WorkDocResponseDto> codeStaffs = response.body();
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
                            tv.setText(errorMsg);
                        }
                    });
                }
            }
        }).start();

        regNumTv.setText(String.valueOf(regRecyclerViewAdapter.getItemCount()));
        unRegNumTv.setText(String.valueOf(unRegRecyclerViewAdapter.getItemCount()));

        regNumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getApplicationContext(), PaperW.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.paperwlist_backib: // '뒤로가기' 선택 시
                intent = new Intent(getApplicationContext(), Paper.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperwlist_reglistL: // 등록 직원 리스트
                intent = new Intent(getApplicationContext(), PaperWlist.class);
                startActivity(intent);
                finish();
            case R.id.paperwlist_unreglistL: // 미등록 직원 리스트
                intent = new Intent(getApplicationContext(), PaperW.class);
                startActivity(intent);
                finish();
            default:
                break;
        }
    }

    public void regFirstInit(){
        regRecyclerView = (RecyclerView) findViewById(R.id.paperwlist_comRV); //리사이클뷰 아이디 받기
        regList = new ArrayList<>();
    }

    protected void regOnResume(Set<WorkDocResponseDto> list) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        RegUpdateRecyclerView(list); // 원하는 업데이트 로직을 여기에 작성

        regRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신
    }


    public void RegAddItem(String name){
        PaperW_Com_recyclerViewItem item = new PaperW_Com_recyclerViewItem();

        item.setComName(name);

        regList.add(item);
    }
    private void RegUpdateRecyclerView(Set<WorkDocResponseDto> list) {
        regList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신
        for (WorkDocResponseDto docH : list) {
            RegAddItem(docH.getStaffName());

        }
        regRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림

    }

    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();
    }

    public void UnRegFirstInit(){
        unRegRecyclerView = (RecyclerView) findViewById(R.id.paperwlist_unComRV); //리사이클뷰 아이디 받기
        unRegList = new ArrayList<>();
    }

    protected void UnRegOnResume(Set<WorkDocResponseDto> list) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        UnRegUpdateRecyclerView(list); // 원하는 업데이트 로직을 여기에 작성

        unRegRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신
    }


    public void UnRegAddItem(String name){
        PaperW_UnCom_recyclerViewItem item = new PaperW_UnCom_recyclerViewItem();

        item.setUnComName(name);

        unRegList.add(item);
    }
    private void UnRegUpdateRecyclerView(Set<WorkDocResponseDto> list) {
        unRegList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신
        for (WorkDocResponseDto docH : list) {
            UnRegAddItem(docH.getStaffName());

        }
        unRegRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림

    }
}