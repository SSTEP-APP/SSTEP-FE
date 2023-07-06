package com.example.sstep.user.login;

import androidx.appcompat.app.AppCompatActivity;
import com.example.sstep.R;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.MemberModel;

import android.os.Bundle;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_test extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        TextView tv = findViewById(R.id.Login_test_tv);

        try {


            // 2. 네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MemberApiService apiService = retrofit.create(MemberApiService.class);
            //찾을 id 입력하는 부분
            Call<MemberModel> call = apiService.getDataFromServer("hi2");
            call.enqueue(new Callback<MemberModel>() {
                @Override
                public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                    if (response.isSuccessful()) {
                        MemberModel data = response.body();
                        // 3. 데이터 표시
                        tv.setText(data.toString()); // 데이터를 TextView에 표시하는 예시입니다.
                    } else {
                        // 오류 처리
                        int statusCode = response.code();
                        String errorMessage;
                        if (statusCode == 404) {
                            errorMessage = "데이터를 찾을 수 없습니다.";
                        } else if (statusCode == 500) {
                            errorMessage = "서버 내부 오류가 발생했습니다.";
                        } else {
                            errorMessage = "오류가 발생했습니다. 상태 코드: " + statusCode;
                        }
                        tv.setText(errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<MemberModel> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                    tv.setText(errorMessage);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
            tv.setText(e.toString());
        }
    }
}