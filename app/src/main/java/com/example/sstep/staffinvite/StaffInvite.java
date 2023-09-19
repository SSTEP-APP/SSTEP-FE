package com.example.sstep.staffinvite;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.AppInData;
import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;

import com.example.sstep.home.Home_Ceo;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.store.store_api.StoreResponseDto;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.example.sstep.user.staff.InputStaffInfo;
import com.example.sstep.user.staff.staff_infoInput;
import com.example.sstep.user.staff_api.StaffInviteResponseDto;
import com.example.sstep.user.staff_api.StaffRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StaffInvite extends AppCompatActivity implements View.OnClickListener {

    Button completeBtn, yesBtn, noBtn;
    ImageButton backib;
    long storeId;

    private RecyclerView mRecyclerView;
    private StaffInvite_RecyclerViewAdpater mRecyclerViewAdapter;

    private List<StaffInvite_recyclerViewItem> mList;

    private RecyclerView iSRecyclerView;
    private StaffInvite_InviteStaff_RecyclerViewAdpater iSRecyclerViewAdapter;

    private List<StaffInvite_InviteStaff_recyclerViewItem> iSList;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 100;

    // 생성된 인증번호를 저장할 리스트 선언
    private List<String> generatedCodes = new ArrayList<>();

    Dialog showComplete_dialog, staffInviteYes_dialog;
    BaseDialog_OkCenter baseDialog_okCenter, baseDialog_okCenter2;
    TextView approvenumTv, waitNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffinvite1);

        completeBtn = findViewById(R.id.staffInvite1_completeBtn); completeBtn.setOnClickListener(this);
        backib = findViewById(R.id.staffInvite1_backib); backib.setOnClickListener(this);
        //yesBtn=findViewById(R.id.staffInvite1_yesBtn); yesBtn.setOnClickListener(this);
        //noBtn=findViewById(R.id.staffInvite1_noBtn); noBtn.setOnClickListener(this);

        baseDialog_okCenter = new BaseDialog_OkCenter(StaffInvite.this, R.layout.join_okdl);
        baseDialog_okCenter2 = new BaseDialog_OkCenter(StaffInvite.this, R.layout.staffinvite_okdl3);

        showComplete_dialog = new Dialog(StaffInvite.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        staffInviteYes_dialog = new Dialog(StaffInvite.this);
        staffInviteYes_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        staffInviteYes_dialog.setContentView(R.layout.staffinvite_okdl3); // xml 레이아웃 파일과 연결
        approvenumTv = findViewById(R.id.staffInvite1_approvenumTv);
        waitNum = findViewById(R.id.staffInvite1_waitnumTv);

        AppInData appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기
        storeId = appInData.getStoreId(); // 사용자 ID 가져오기

        firstInitIS();
        firstInit();


        mRecyclerViewAdapter = new StaffInvite_RecyclerViewAdpater(mList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(StaffInvite.this, RecyclerView.VERTICAL, false)); //리사이클러뷰 양식지정

        iSRecyclerViewAdapter = new StaffInvite_InviteStaff_RecyclerViewAdpater(iSList);
        iSRecyclerView.setAdapter(iSRecyclerViewAdapter);
        iSRecyclerView.setLayoutManager(new LinearLayoutManager(StaffInvite.this, RecyclerView.VERTICAL, false)); //리사이클러뷰 양식지정


        TextView tv = findViewById(R.id.staffInvite1_approveTv);


        approvenumTv.setText(String.valueOf(mRecyclerViewAdapter.getItemCount()));
        waitNum.setText(String.valueOf(iSRecyclerViewAdapter.getItemCount()));



        //초대 받은 사람 리스트
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    StoreApiService apiService = retrofit.create(StoreApiService.class);

                    Call<Set<StaffInviteResponseDto>> call = apiService.getInputCodeStaffs(storeId); //storeId 삽입
                    retrofit2.Response<Set<StaffInviteResponseDto>> response = call.execute();

                    if (response.isSuccessful()) {
                        final Set<StaffInviteResponseDto> codeStaffs = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onResume(codeStaffs);
                            }
                        });
                    } else {
                        tv.setText("API call failed: " + response.code());
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


        //초대한 사람 리스트
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    StoreApiService apiService2 = retrofit.create(StoreApiService.class);

                    Call<Set<StaffInviteResponseDto>> call2 = apiService2.getInviteStaffs(storeId); //storeId 삽입
                    retrofit2.Response<Set<StaffInviteResponseDto>> response2 = call2.execute();

                    if (response2.isSuccessful()) {
                        final Set<StaffInviteResponseDto> codeStaffsIS = response2.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onResumeIS(codeStaffsIS);
                            }
                        });
                    } else if (response2.code()==500){

                    } else{
                        tv.setText("API call failed!!: " + response2.code());
                    }
                } catch (Exception e) {
                    final String errorMsg = e.toString();
                    tv.setText(e.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleError(errorMsg);
                        }
                    });
                }
            }
        }).start();


        // 맨 아래 '직원초대' 클릭 시
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StaffInvite2.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.staffInvite1_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
                break;
            case R.id.staffInvite1_completeBtn: // 직원 초대
                intent = new Intent(getApplicationContext(),StaffInvite2.class);
                startActivity(intent);
                finish();
                break;

                /*
            case R.id.staffInvite1_resendBtn: // 재전송
                break;
            case R.id.staffInvite1_deleteBtn: // 삭제
                break;

                 */
            default:
                break;
        }
    }

    public void firstInit(){
        mRecyclerView = (RecyclerView) findViewById(R.id.staffInvite1_recycleView); //리사이클뷰 아이디 받기
        mList = new ArrayList<>();
    }

    protected void onResume(Set<StaffInviteResponseDto> stores) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        updateRecyclerView(stores); // 원하는 업데이트 로직을 여기에 작성

        mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신
    }


    public void addItem(String name, String userName, long id){
        StaffInvite_recyclerViewItem item = new StaffInvite_recyclerViewItem();

        item.setStaffInviteName(name);
        item.setStaffInviteUserName(userName);
        item.setStaffInviteId(id);

        mList.add(item);
    }
    private void updateRecyclerView(Set<StaffInviteResponseDto> stores) {
        mList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신
        for (StaffInviteResponseDto store : stores) {
            addItem(store.getName(), store.getUsername(), store.getStaffId());

        }
        mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
        mRecyclerViewAdapter.setOnItemClickListener(new StaffInvite_RecyclerViewAdpater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                StaffInvite_recyclerViewItem item = mList.get(position);
                staffInviteYes_dialog.show();
                // 다이얼로그의 배경을 투명으로 만든다.
                staffInviteYes_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView join_okdl_commentTv; Button join_okdl_okBtn, join_okdl_noBtn;
                join_okdl_commentTv = staffInviteYes_dialog.findViewById(R.id.staffInvite_okdl3_tv1);
                join_okdl_okBtn = staffInviteYes_dialog.findViewById(R.id.staffInvite_okdl3_okbtn2);
                join_okdl_noBtn = staffInviteYes_dialog.findViewById(R.id.staffInvite_okdl3_canclebtn1);
                join_okdl_commentTv.setText(item.getStaffInviteName()+" 직원을 합류시키시겠습니까?");
                //확인 시
                join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), InputStaffInfo.class);
                        intent.putExtra("staffId",item.getStaffInviteId());
                        Toast.makeText(StaffInvite.this, "스테프아이디"+item.getStaffInviteId(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                });
                //취소시 닫기
                join_okdl_noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        staffInviteYes_dialog.dismiss();
                    }
                });
            }
        });
    }

    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();
    }



    public void firstInitIS(){
        iSRecyclerView = (RecyclerView) findViewById(R.id.staffInvite1_is_recycleView); //리사이클뷰 아이디 받기
        iSList = new ArrayList<>();
    }

    protected void onResumeIS(Set<StaffInviteResponseDto> stores) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        updateRecyclerViewIS(stores); // 원하는 업데이트 로직을 여기에 작성

        iSRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신
    }


    public void addItemIS(String name, String userName){
        StaffInvite_InviteStaff_recyclerViewItem item = new StaffInvite_InviteStaff_recyclerViewItem();

        item.setStaffInviteISName(name);
        item.setStaffInviteISUserName(userName);

        iSList.add(item);
    }
    private void updateRecyclerViewIS(Set<StaffInviteResponseDto> stores) {
        iSList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신
        for (StaffInviteResponseDto store : stores) {
            addItemIS(store.getName(), store.getUsername());

        }
        iSRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림

    }

    // 휴대폰 인증 처리 메서드
//    private void sendSMS() {
//        // 휴대폰 인증 처리 코드 작성
//        // 휴대폰 인증번호 발송
//
//        /*
//        db에서 이름과 전화번호 받아오기
//         */
////        String name = nameEt.getText().toString().trim();
////        String phoneNumber = numberEt.getText().toString().trim().replace("-","");
//        if (!phoneNumber.isEmpty()) {
//            // 권한 체크
//            if (ContextCompat.checkSelfPermission(StaffInvite.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
//                // 인증번호 생성 및 발송
//                String verificationCode = generateUniqueVerificationCode();
//                String message = name + " 님이 초대되었습니다. \n 사업장 코드는 " + verificationCode + " 입니다.";
//                sendVerificationCode(phoneNumber, message);
//                showCompleteDl(message); // 다이얼로그 표시
//            } else {
//                // 권한 요청
//                ActivityCompat.requestPermissions(StaffInvite.this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
//            }
//        } else {
//            Toast.makeText(StaffInvite.this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    // 인증번호 생성 메서드 (중복 방지)
//    private String generateUniqueVerificationCode() {
//        String verificationCode = generateVerificationCode();
//        // Check if the code already exists in the list
//        while (generatedCodes.contains(verificationCode)) {
//            verificationCode = generateVerificationCode();
//        }
//        // Add the newly generated code to the list
//        generatedCodes.add(verificationCode);
//        return verificationCode;
//    }
//
//    // 인증번호 생성 메서드
//    private String generateVerificationCode() {
//        Random random = new Random();
//        int verificationCode = random.nextInt(900000) + 100000;
//        return String.valueOf(verificationCode);
//    }
//
//    // 인증번호 발송 메서드
//    private void sendVerificationCode(String phoneNumber, String verificationCode) {
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNumber, null, verificationCode, null, null);
//        } catch (Exception e) {
//            Toast.makeText(StaffInvite.this, "인증번호 발송에 실패했습니다." + verificationCode, Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }
}