package com.example.sstep.store;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.home.Home_Ceo;
import com.example.sstep.store.store_api.StoreResponseDto;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.MemberModel;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.example.sstep.user.staff.AddSch_RecyclerViewAdpater;
import com.example.sstep.user.staff.Staff_infoInput_recyclerViewItem;
import com.example.sstep.user.staff.addSchedule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectStore extends AppCompatActivity implements View.OnClickListener {

    ImageButton searchIbtn;
    Button storeregBtn;
    FrameLayout onelistF;
    int store_Code;
    Dialog showComplete_dialog, showConfirm_dialog;
    BaseDialog_OkCenter baseDialog_okCenter, baseDialog_okCenter2;
    private RecyclerView mRecyclerView;
    private SelectStore_RecyclerViewAdpater mRecyclerViewAdapter;
    private List<SelectStore_recyclerViewItem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectstore);

        storeregBtn = findViewById(R.id.selectstore_storeregBtn); storeregBtn.setOnClickListener(this);
        searchIbtn = findViewById(R.id.selectstore_searchIbtn); searchIbtn.setOnClickListener(this);
        //onelistF = findViewById(R.id.selectstore_onelistF); onelistF.setOnClickListener(this);

        baseDialog_okCenter = new BaseDialog_OkCenter(SelectStore.this, R.layout.searchstore_dl);
        baseDialog_okCenter2 = new BaseDialog_OkCenter(SelectStore.this, R.layout.searchstore_dl2);

        showComplete_dialog = new Dialog(SelectStore.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.searchstore_dl); // xml 레이아웃 파일과 연결

        showConfirm_dialog = new Dialog(SelectStore.this);
        showConfirm_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showConfirm_dialog.setContentView(R.layout.searchstore_dl2); // xml 레이아웃 파일과 연결

        // Intent로 전달받은 ID 값 가져오기
        String userId = getIntent().getStringExtra("userId");


        //리사이클러뷰를 통해 사업장 리스트 가지고 오기
        firstInit();

        mRecyclerViewAdapter = new SelectStore_RecyclerViewAdpater(mList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SelectStore.this, RecyclerView.VERTICAL, false));

        //메인쓰레드 에러로 백그라운드에서 실행하는 코드, 리사이클러뷰를 이용해서 스토어를 리스트로 보여주는 코드
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    MemberApiService apiService = retrofit.create(MemberApiService.class);

                    Call<List<StoreResponseDto>> call = apiService.getStoresBelongMember(userId);
                    retrofit2.Response<List<StoreResponseDto>> response = call.execute();

                    if (response.isSuccessful()) {
                        final List<StoreResponseDto> stores = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(stores);
                            }
                        });
                    } else {
                        System.out.println("API call failed: " + response.code());
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

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.selectstore_storeregBtn: // '사업장등록하기'버튼
                intent = new Intent(getApplicationContext(), RegisterStore.class);
                startActivity(intent);
                finish();
                break;
                /*
            case R.id.selectstore_onelistF: // 리스트
                intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
                break;

                 */
            case R.id.selectstore_searchIbtn: // 사업장 검색
                showSearchStoreDl();
            default:
                break;
        }
    }

    public void showSearchStoreDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText searchstore_dl_numEt; Button searchstore_dl_okBtn;
        searchstore_dl_numEt = showComplete_dialog.findViewById(R.id.searchstore_dl_numEt);
        searchstore_dl_okBtn = showComplete_dialog.findViewById(R.id.searchstore_dl_okBtn);
        // '사업장 코드 검색 dialog' _ 확인 버튼 클릭 시
        searchstore_dl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    store_Code = Integer.parseInt(searchstore_dl_numEt.getText().toString());

                    showConfirmDl();
                } catch (Exception e) {
                    // 사용자가 유효하지 않은 값을 입력한 경우 예외 처리
                    showComplete_dialog.show();
                    // 다이얼로그의 배경을 투명으로 만든다.
                    showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView join_okdl_commentTv; Button join_okdl_okBtn;
                    join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
                    join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
                    join_okdl_commentTv.setText("코드가 잘못입력되었습니다.");
                    join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }

            }
        });
    }

    public void showConfirmDl(){
        showConfirm_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showConfirm_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView searchstore_dl2_storeNameTv, searchstore_dl2_addressTv;
        Button searchstore_dl2_noBtn, searchstore_dl2_okBtn;
        searchstore_dl2_storeNameTv = showConfirm_dialog.findViewById(R.id.searchstore_dl2_storeNameTv);
        searchstore_dl2_addressTv = showConfirm_dialog.findViewById(R.id.searchstore_dl2_addressTv);
        searchstore_dl2_noBtn = showConfirm_dialog.findViewById(R.id.searchstore_dl2_noBtn);
        searchstore_dl2_okBtn = showConfirm_dialog.findViewById(R.id.searchstore_dl2_okBtn);

        // '사업장 코드 검색 dialog' _ 취소 버튼 클릭 시
        searchstore_dl2_noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirm_dialog.dismiss();
                showComplete_dialog.dismiss();
            }
        });


        //ok버튼 클릭시
        searchstore_dl2_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirm_dialog.dismiss();
                showComplete_dialog.dismiss();
            }
        });
    }
    public void firstInit(){
        mRecyclerView = (RecyclerView) findViewById(R.id.selectstore_storeRV); //리사이클뷰 아이디 받기
        mList = new ArrayList<>();
    }

    public void addItem(String name, String address, String person){
        SelectStore_recyclerViewItem item = new SelectStore_recyclerViewItem();

        item.setSelectStoreName(name);
        item.setSelectStoreAddress(address);
        item.setSelectStorePerson(person);

        mList.add(item);
    }
    private void updateRecyclerView(List<StoreResponseDto> stores) {
        mList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신
        for (StoreResponseDto store : stores) {
            addItem(store.getName(), store.getAddress(), "" + store.getCount());
        }
        mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
    }

    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();
        storeregBtn.setText(errorMsg);
    }
}