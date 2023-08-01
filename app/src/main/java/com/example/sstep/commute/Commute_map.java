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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.home.Home_staff;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Commute_map extends AppCompatActivity implements OnMapReadyCallback {

    ImageView nocurrentLocationBtn;
    ImageButton mapbtn, currentLocationBtn;
    Button bottomonbtn;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap googleMap;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private static final float MAX_DISTANCE_THRESHOLD = 100.0f; // 일정 거리 (미터) 설정

    Dialog showCommuteIn_dialog, showCommuteOut_dialog;
    BaseDialog_OkCenter baseDialog_okCenter, baseDialog_okCenter2;

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

        // 닫기 버튼
        bottomonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home_staff.class);
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
                String inout = getIntent().getStringExtra("inout");
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoulCityHallLatLng, 15));

        // 현재 위치 마커 표시
        getCurrentLocation();
    }

    private void showOkMarker(LatLng latLng) {
        // 등록된 위치가 일치하는 경우의 마커를 추가하는 코드
        googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

    private void showNoMarker(LatLng latLng) {
        // 등록된 위치가 일치하지 않는 경우의 마커를 추가하는 코드
        googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

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
                        LatLng registeredLatLng = new LatLng(37.3454, 126.9561); //37.3454,126.9561
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
        TextView commute_indl_nameTv, commute_indl_timeTv; Button commute_indl_okBtn;
        commute_indl_nameTv = showCommuteIn_dialog.findViewById(R.id.commute_indl_nameTv);
        commute_indl_timeTv = showCommuteIn_dialog.findViewById(R.id.commute_indl_timeTv);
        commute_indl_okBtn = showCommuteIn_dialog.findViewById(R.id.commute_indl_okBtn);

        // 현재 시각을 구하여 TextView에 설정
        long currentTimeMillis = System.currentTimeMillis();
        int hour = Integer.parseInt(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date(currentTimeMillis)));
        int minute = Integer.parseInt(new SimpleDateFormat("mm", Locale.getDefault()).format(new Date(currentTimeMillis)));
        String currentTime = hour + " : " + minute;
        commute_indl_timeTv.setText("출근시간 " + currentTime + "입니다.");
        boolean isGoingToWork = true;

        // '회원가입 dialog' _ 확인 버튼 클릭 시
        commute_indl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home_staff.class);
                intent.putExtra("isGoingToWork", isGoingToWork);
                startActivity(intent);
                finish();
            }
        });
    }

    // 퇴근
    public void showOutDl(){
        showCommuteOut_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showCommuteOut_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView commute_outdl_nameTv, commute_outdl_timeTv; Button commute_outdl_okBtn;
        commute_outdl_nameTv = showCommuteOut_dialog.findViewById(R.id.commute_outdl_nameTv);
        commute_outdl_timeTv = showCommuteOut_dialog.findViewById(R.id.commute_outdl_timeTv);
        commute_outdl_okBtn = showCommuteOut_dialog.findViewById(R.id.commute_outdl_okBtn);

        // 현재 시각을 구하여 TextView에 설정
        long currentTimeMillis = System.currentTimeMillis();
        int hour = Integer.parseInt(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date(currentTimeMillis)));
        int minute = Integer.parseInt(new SimpleDateFormat("mm", Locale.getDefault()).format(new Date(currentTimeMillis)));
        String currentTime = hour + " : " + minute;
        commute_outdl_timeTv.setText("퇴근시간 " + currentTime + "입니다.");
        boolean isGoingToWork = false;

        // '회원가입 dialog' _ 확인 버튼 클릭 시
        commute_outdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home_staff.class);
                intent.putExtra("isGoingToWork", isGoingToWork);
                startActivity(intent);
                finish();
            }
        });
    }
}