package com.example.sstep.commute;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sstep.AppInData;
import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteRequestDto;
import com.example.sstep.home.Home_staff;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.store.store_api.StoreResponseDto;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Commute_map extends AppCompatActivity implements OnMapReadyCallback {

    AppInData appInData;
    String userName;
    long storeCode;
    String startTimeStr, endTimeStr, commuteDateStr;
    Double latitude, longitude;
    LocalTime startTime, endTime; // 출근 시간, 퇴근 시간
    LocalDate commuteDate; // 출퇴근 일자
    DayOfWeek dayOfWeek; // 출퇴근 요일
    ImageView nocurrentLocationBtn;
    ImageButton mapbtn, currentLocationBtn;
    Button bottomonbtn;
    TextView commute_indl_nameTv, commute_indl_timeTv, commute_outdl_nameTv, commute_outdl_timeTv;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap googleMap;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private static final float MAX_DISTANCE_THRESHOLD = 500.0f; // 일정 거리 (미터) 설정

    Dialog showCommuteIn_dialog, showCommuteOut_dialog;
    BaseDialog_OkCenter baseDialog_okCenter, baseDialog_okCenter2;

    boolean isGoingToWork = true; // 출근/퇴근 상태를 저장하는 변수
    String inout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commute_map);

        mapbtn=findViewById(R.id.commute_mapdl_mapbtn);
        bottomonbtn=findViewById(R.id.commute_mapdl_bottomonbtn);
        currentLocationBtn = findViewById(R.id.currentLocationBtn);
        nocurrentLocationBtn = findViewById(R.id.nocurrentLocationBtn);

        baseDialog_okCenter = new BaseDialog_OkCenter(Commute_map.this, R.layout.commute_indl);
        baseDialog_okCenter2 = new BaseDialog_OkCenter(Commute_map.this, R.layout.commute_outdl);

        showCommuteIn_dialog = new Dialog(Commute_map.this);
        showCommuteIn_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showCommuteIn_dialog.setContentView(R.layout.commute_indl); // xml 레이아웃 파일과 연결

        showCommuteOut_dialog = new Dialog(Commute_map.this);
        showCommuteOut_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showCommuteOut_dialog.setContentView(R.layout.commute_outdl); // xml 레이아웃 파일과 연결

        // ID값 가지고 오기
        appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기
        userName = appInData.getUserName();
        storeCode = appInData.getStoreCode();

        // 위도, 경도 가져오기
        try {

            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            StoreApiService apiService = retrofit.create(StoreApiService.class);
            //적은 id를 기반으로 db에 검색
            Call<StoreResponseDto> call = apiService.getStore(storeCode);
            call.enqueue(new Callback<StoreResponseDto>() {
                @Override
                public void onResponse(Call<StoreResponseDto> call, Response<StoreResponseDto> response) {
                    if (response.isSuccessful()) {
                        StoreResponseDto storeResponseData  = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        Long checkstoreCode =storeResponseData.getCode(); // id에 id 설정
                        latitude= Double.valueOf(storeResponseData.getLatitude());
                        longitude= Double.valueOf(storeResponseData.getLongitude());

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<StoreResponseDto> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

        // 닫기 버튼
        bottomonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inout 값을 읽어와서 해당 메서드 호출
                inout = getIntent().getStringExtra("inout");
                if (inout != null && inout.equals("in")) {
                    isGoingToWork=true;
                } else if (inout != null && inout.equals("out")) {
                    isGoingToWork=false;
                }

                Intent intent = new Intent(getApplicationContext(), Home_staff.class);
                intent.putExtra("isGoingToWork", isGoingToWork); // 이전 상태를 다시 전달
                startActivity(intent);
                finish();


            }
        });

        // 현재 위치 버튼
        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });

        // 출퇴근 버튼
        currentLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inout 값을 읽어와서 해당 메서드 호출
                inout = getIntent().getStringExtra("inout");
                if (inout != null && inout.equals("in")) {
                    showInDl();
                } else if (inout != null && inout.equals("out")) {
                    showOutDl();
                }
            }
        });

        // MapView 초기화
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // FusedLocationProviderClient 초기화
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void showCurrentLocationButton(boolean isMatching) {
        if (isMatching) {
            currentLocationBtn.setVisibility(View.VISIBLE);
            nocurrentLocationBtn.setVisibility(View.GONE);
        } else {
            currentLocationBtn.setVisibility(View.GONE);
            nocurrentLocationBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        // 지도 초기 위치 설정 (예시로 서울시청의 위도 경도 설정)
        LatLng seoulCityHallLatLng = new LatLng(37.5662952, 126.9779451);
        // 초기 카메라 위치 설정...
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoulCityHallLatLng, 15));
        // 현재 위치 마커 표시
        getCurrentLocation();
    }

    // 등록된 위치가 일치하는 경우의 마커를 추가하는 코드
    private void showOkMarker(LatLng latLng) {
        // 녹색 마커 추가
        googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

    // 등록된 위치가 일치하지 않는 경우의 마커를 추가하는 코드
    private void showNoMarker(LatLng latLng) {
        // 빨간색 마커 추가
        googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    // 현재 위치를 가져오고 관련 작업을 수행하는 메서드
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));

                        // 현재 위치에 마커 추가
                        googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("내 위치"));

                        // 등록된 위치와 현재 위치 사이의 거리를 계산
                        LatLng registeredLatLng = new LatLng(latitude, longitude); //37.3454,126.9561
                        float[] distanceResults = new float[1];
                        Location.distanceBetween(
                                registeredLatLng.latitude, registeredLatLng.longitude,
                                currentLatLng.latitude, currentLatLng.longitude,
                                distanceResults);

                        // 일정 거리 이내에 있을 경우 버튼을 보여줌
                        if (distanceResults[0] <= MAX_DISTANCE_THRESHOLD) {
                            showOkMarker(registeredLatLng);
                            showCurrentLocationButton(true); // 버튼을 보여줌
                        } else {
                            showNoMarker(registeredLatLng);
                            showCurrentLocationButton(false); // 버튼을 숨김
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 허용 시, 지도 초기화
                mapView.getMapAsync(this);
            } else {
                // 권한 거부 시 처리 (필요하면 추가)
            }
        }
    }

    // 출근
    public void showInDl(){
        showCommuteIn_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showCommuteIn_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button commute_indl_okBtn;
        commute_indl_nameTv = showCommuteIn_dialog.findViewById(R.id.commute_indl_nameTv);
        commute_indl_timeTv = showCommuteIn_dialog.findViewById(R.id.commute_indl_timeTv);
        commute_indl_okBtn = showCommuteIn_dialog.findViewById(R.id.commute_indl_okBtn);
        commute_indl_nameTv.setText(userName);
        try {

            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            StoreApiService apiService = retrofit.create(StoreApiService.class);
            //적은 id를 기반으로 db에 검색
            Call<StoreResponseDto> call = apiService.getStore(storeCode);
            call.enqueue(new Callback<StoreResponseDto>() {
                @Override
                public void onResponse(Call<StoreResponseDto> call, Response<StoreResponseDto> response) {
                    if (response.isSuccessful()) {
                        StoreResponseDto storeResponseData  = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        Long checkstoreCode =storeResponseData.getCode(); // id에 id 설정
                        latitude= Double.valueOf(storeResponseData.getLatitude());
                        longitude= Double.valueOf(storeResponseData.getLongitude());

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<StoreResponseDto> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

        // 출근 시각 : 현재 시각 가져오기
        startTime = LocalTime.now();
        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault());
        startTimeStr = startTime.format(timeformatter);
        commute_indl_timeTv.setText("출근시간 " + startTimeStr + " 입니다.");
        // '출근 dialog' _ 확인 버튼 클릭 시
        commute_indl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 현재 날짜 가져오기
                commuteDate = LocalDate.now(); // 출퇴근일자, yyyy-mm-dd
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                commuteDateStr = commuteDate.format(formatter);

                dayOfWeek = commuteDate.getDayOfWeek();// 출퇴근 요일

                try {

                    //네트워크 요청 구현
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    CommuteApiService apiService = retrofit.create(CommuteApiService.class);

                    // 사업장등록에 필요한 데이터를 CommuteRequestDto 객체로 생성
                    CommuteRequestDto commuteRequestDto = new CommuteRequestDto(
                            commuteDateStr, //출퇴근 일자
                            dayOfWeek, //출퇴근 요일
                            startTimeStr, //출근 시간
                            null, //퇴근 시간
                            false, //지각 여부
                            null, //출퇴근 관련 이의 신청 메시지
                            null, //정정 출근 시간
                            null //정정 퇴근 시간
                    );

                    //적은 id를 기반으로 db에 검색
                    Call<Void> call = apiService.registerCommute(2L, commuteRequestDto); // staffId에 해당하는 값을 설정해야 함
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "출근시간 저장 성공", Toast.LENGTH_SHORT).show();

                                showCommuteIn_dialog.dismiss(); // 다이얼로그 닫기
                                isGoingToWork = false; // 퇴근 상태로 변경
                                Intent intent = new Intent(getApplicationContext(), Home_staff.class);
                                intent.putExtra("isGoingToWork", isGoingToWork);
                                startActivity(intent);
                                finish();
                            } else {
                                try {
                                    String errorResponse = response.errorBody().string();
                                    Toast.makeText(Commute_map.this, "출근시간 등록 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
                                    // 에러 메시지를 사용하여 추가적인 처리 수행
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // 실패 처리
                            String errorMessage = t != null ? t.getMessage() : "Unknown error";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            t.printStackTrace();

                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    // 퇴근
    public void showOutDl(){
        showCommuteOut_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showCommuteOut_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button commute_outdl_okBtn;
        commute_outdl_nameTv = showCommuteOut_dialog.findViewById(R.id.commute_outdl_nameTv);
        commute_outdl_timeTv = showCommuteOut_dialog.findViewById(R.id.commute_outdl_timeTv);
        commute_outdl_okBtn = showCommuteOut_dialog.findViewById(R.id.commute_outdl_okBtn);
        commute_outdl_nameTv.setText(userName);
        
        // 퇴근 시각 : 현재 시각 가져오기
        endTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault());
        endTimeStr = endTime.format(formatter);
        commute_outdl_timeTv.setText("퇴근시간 " + endTimeStr + " 입니다.");

        // '퇴근 dialog' _ 확인 버튼 클릭 시
        commute_outdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 날짜 가져오기
                commuteDate = LocalDate.now(); // 출퇴근일자, yyyy-mm-dd
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                commuteDateStr = commuteDate.format(formatter);

                dayOfWeek = commuteDate.getDayOfWeek();// 출퇴근 요일

                try {

                    //네트워크 요청 구현
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    CommuteApiService apiService = retrofit.create(CommuteApiService.class);

                    // 사업장등록에 필요한 데이터를 CommuteRequestDto 객체로 생성
                    CommuteRequestDto commuteRequestDto = new CommuteRequestDto(
                            null, //출퇴근 일자
                            null, //출퇴근 요일
                            null, //출근 시간
                            endTimeStr, //퇴근 시간
                            false, //지각 여부
                            null, //출퇴근 관련 이의 신청 메시지
                            null, //정정 출근 시간
                            null //정정 퇴근 시간
                    );

                    //적은 id를 기반으로 db에 검색
                    Call<Void> call = apiService.updateCommute(2L, commuteDateStr, commuteRequestDto); // staffId에 해당하는 값을 설정해야 함
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "퇴근시간 저장 성공", Toast.LENGTH_SHORT).show();

                                showCommuteOut_dialog.dismiss(); // 다이얼로그 닫기
                                isGoingToWork = true; // 출근 상태로 변경
                                Intent intent = new Intent(getApplicationContext(), Home_staff.class);
                                intent.putExtra("isGoingToWork", isGoingToWork);
                                startActivity(intent);
                                finish();
                            } else {
                                try {
                                    String errorResponse = response.errorBody().string();
                                    Toast.makeText(Commute_map.this, "퇴근시간 저장 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
                                    // 에러 메시지를 사용하여 추가적인 처리 수행
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
//                                Toast.makeText(getApplicationContext(), "퇴근시간 저장 실패" + response.code(), Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // 실패 처리
                            String errorMessage = t != null ? t.getMessage() : "Unknown error";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            t.printStackTrace();

                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}