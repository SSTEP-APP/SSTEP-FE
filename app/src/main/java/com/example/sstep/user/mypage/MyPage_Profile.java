package com.example.sstep.user.mypage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.document.PhotoDialog;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.store.store_api.StoreResponseDto;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.MemberModel;
import com.example.sstep.user.member.MemberRequestDto;
import com.example.sstep.user.member.MemberResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.example.sstep.user.staff_api.StaffApiService;
import com.example.sstep.user.staff_api.StaffResponseDto;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPage_Profile extends AppCompatActivity implements View.OnClickListener, PhotoDialog.PhotoDialogListener{

    EditText nameEt, phoneEt;
    String memberId, nameStr, phoneStr;
    ImageButton backib, cameraIb;
    Button completeBtn;
    CircleImageView profileIv;
    Intent intent;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_profile);

        nameEt = findViewById(R.id.mypage_profile_nameEt);
        phoneEt = findViewById(R.id.mypage_profile_phoneEt);
        profileIv = findViewById(R.id.mypage_profile_profileIv);
        completeBtn=findViewById(R.id.mypage_profile_completeBtn); completeBtn.setOnClickListener(this);
        backib = findViewById(R.id.mypage_profile_backib);backib.setOnClickListener(this);

        baseDialog_okCenter = new BaseDialog_OkCenter(MyPage_Profile.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(MyPage_Profile.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        // 카메라 권한 요청
        cameraIb = findViewById(R.id.mypage_profile_cameraIb);
        cameraIb.setOnClickListener(this);
        ActivityCompat.requestPermissions(this, new String[]
                {android.Manifest.permission.CAMERA}, MODE_PRIVATE);

        // 전화번호 입력시 자동 '-' 입력
        phoneEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // memberId 가져오기
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MemberApiService apiService = retrofit.create(MemberApiService.class);
            memberId = "819id";
            //적은 id를 기반으로 db에 검색
            Call<MemberResponseDto> call = apiService.getMemberByUsername(memberId);
            call.enqueue(new Callback<MemberResponseDto>() {
                @Override
                public void onResponse(Call<MemberResponseDto> call, Response<MemberResponseDto> response) {
                    if (response.isSuccessful()) {
                        MemberResponseDto data = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        nameStr =data.getName(); // id에 id 설정
                        nameEt.setText(nameStr);
                        phoneStr =data.getPhoneNum();
                        phoneEt.setText(phoneStr);
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<MemberResponseDto> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mypage_profile_backib: // '뒤로가기'
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mypage_profile_cameraIb: // '카메라'
                showPhotoDialog();
                break;
            case R.id.mypage_profile_completeBtn: // 입력완료 버튼
                showCompleteDl();
                break;
            default:
                break;
        }
    }

    // 사진 촬영 다이얼로그 띄우기
    public void showPhotoDialog() {
        PhotoDialog photoDialog = new PhotoDialog(MyPage_Profile.this, MyPage_Profile.this);
        photoDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE: // 카메라
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    onPhotoSelected(bitmap);
                    break;
                case GALLERY_REQUEST_CODE: // 갤러리
                    Uri uri = data.getData();
                    try {
                        Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        onPhotoSelected(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    // '계약서 작성완료'버튼 클릭 시
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);

        // 정보 수정하기
        try {

            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            MemberApiService apiService = retrofit.create(MemberApiService.class);
            // 회원가입에 필요한 데이터를 MemberRequestDto 객체로 생성
            MemberRequestDto memberRequestDto = new MemberRequestDto(
                    nameEt.getText().toString().trim(),
                    phoneEt.getText().toString().trim()
            );

            // 회원가입 요청을 서버에 전송
            Call<Void> call = apiService.joinMember(memberRequestDto);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // 수정 성공
                        Void memberResponseDto = response.body();
                        join_okdl_commentTv.setText("프로필 수정을 완료하였습니다.");
                        // 응답을 필요에 따라 처리하세요.
                    } else {
                        // 실패
                        int statusCode = response.code();
                        join_okdl_commentTv.setText("프로필 수정을 실패했습니다.오류코드"+statusCode);
                        // 에러 응답을 처리하세요.
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // 네트워크 오류나 기타 이유로 회원가입 실패
                    String errorMessage = t != null ? t.getMessage() : "Unknown error";
                    join_okdl_commentTv.setText("실패.오류코드"+errorMessage);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }


        // '회원가입 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onPhotoSelected(Bitmap bitmap) {
        profileIv.setImageBitmap(bitmap);
        profileIv.setClipToOutline(true);
    }
}