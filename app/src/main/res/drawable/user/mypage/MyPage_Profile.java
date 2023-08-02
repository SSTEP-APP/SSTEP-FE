package drawable.user.mypage;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.document.PhotoDialog;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPage_Profile extends AppCompatActivity implements View.OnClickListener, PhotoDialog.PhotoDialogListener{

    EditText nameEt, phoneEt, emailEt;
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
        emailEt = findViewById(R.id.mypage_profile_emailEt);
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
        join_okdl_commentTv.setText("프로필 수정을 완료하였습니다.");
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