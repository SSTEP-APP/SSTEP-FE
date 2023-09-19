package com.example.sstep.todo.checklist;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.AppInData;
import com.example.sstep.R;
import com.example.sstep.todo.checklist.checklist_api.CheckListResponseDto;
import com.example.sstep.todo.checklist.checklist_api.ChecklistApiService;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class checkList2Fragment extends Fragment {

    private CheckList2_RecyclerViewAdpater mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<CheckList2_recyclerViewWordItemData> list = new ArrayList<>();
    private LinearLayout nodataLayout, dataLayout;
    Long categoryId;
    String date;
    Long staffId, storeId;
    boolean owner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.checklist2_fragment, container, false);
        //list = CheckList2_recyclerViewWordItemData.createContactsList(2);// 리스트 갯수
        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_checkList2_recycleView);
        nodataLayout = (LinearLayout) v.findViewById(R.id.fragment_checkList2_nodataLayout);
        dataLayout = (LinearLayout)  v.findViewById(R.id.fragment_checkList2_dataLayout);
        mRecyclerView.setHasFixedSize(true);

        Application appInData = (Application) requireContext().getApplicationContext();
        if (appInData instanceof AppInData) {
            // AppInData 클래스의 인스턴스를 사용할 수 있습니다.
            AppInData appInDataInstance = (AppInData) appInData;
            storeId = appInDataInstance.getStoreId();
            staffId = appInDataInstance.getStaffId();
            owner = appInDataInstance.isOwner();

            // 이제 appInDataInstance를 사용하여 원하는 작업을 수행할 수 있습니다.
        }


        try {
            if(list.isEmpty()){
                nodataLayout.setVisibility(View.VISIBLE);
                dataLayout.setVisibility(View.GONE);
            }
            else {
                nodataLayout.setVisibility(View.GONE);
                dataLayout.setVisibility(View.VISIBLE);
                mRecyclerViewAdapter = new CheckList2_RecyclerViewAdpater(getActivity(), list);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("error", e.toString());
        }
        FetchDataTask fetchDataTask = new FetchDataTask(getActivity());
        fetchDataTask.execute();
        return v;
    }
    private class FetchDataTask extends AsyncTask<Void, Void, String> {

        private CheckListViewModel viewModel;
        private CheckListViewModel2 viewModel2;
        private Context context;
        private String resultData;

        public FetchDataTask(Context context) {
            this.context = context;
            this.viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(CheckListViewModel.class);
            this.viewModel2 = new ViewModelProvider((ViewModelStoreOwner) context).get(CheckListViewModel2.class);
        }



        @Override
        protected String doInBackground(Void... params) {
            try {
                // staffId와 categoryId를 가져오는 부분을 수정
                AppInData appInData = (AppInData) context.getApplicationContext();
                //staffId = appInData.getStaffId();
                Log.d("MyApp", "doInBackground started");
                // doInBackground의 내용
                Log.d("MyApp", "doInBackground finished");
                // categoryId와 date가 변경될 때마다 업데이트되도록 수정
                LiveData<Long> categoryIdLiveData = viewModel.getCategoryIdLiveData();
                categoryId = categoryIdLiveData.getValue();
                date = viewModel2.getDate().getValue();

                // categoryId 값이 null인 경우에 대한 처리
                if (categoryId == null || categoryId <= 0) {
                    categoryId = 1L; // 또는 다른 적절한 기본값
                }

                // date 값이 null인 경우에 대한 처리
                if (date == null) {
                    Date currentDate = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date = sdf.format(currentDate);
                }
                Log.d("MyApp", "staffId: " + staffId + ", categoryId: " + categoryId + ", date: " + date);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                        .addConverterFactory(new NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ChecklistApiService apiService = retrofit.create(ChecklistApiService.class);
                if(owner) {
                    Call<Set<CheckListResponseDto>> call1 = apiService.getStoreCompleteCheckListsByCategory(storeId, categoryId, date);

                    call1.enqueue(new Callback<Set<CheckListResponseDto>>() {
                        @Override
                        public void onResponse(Call<Set<CheckListResponseDto>> call, Response<Set<CheckListResponseDto>> response) {
                            if (response.isSuccessful()) {
                                Set<CheckListResponseDto> checkLists = response.body();
                                // 가져온 데이터 처리

                                StringBuilder result = new StringBuilder();
                                for (CheckListResponseDto checkList : checkLists) {
                                    result.append(checkList.getTitle()).append("\n");
                                    result.append(checkList.getId()).append("\n");
                                }

                                // 가져온 데이터를 멤버 변수에 할당
                                resultData = result.toString();

                                // UI 업데이트 코드
                                onPostExecute(resultData);
                            } else {
                                // 요청 실패 처리
                                String errorMessage = "요청 실패: " + response.code();
                                // 가져온 데이터를 멤버 변수에 할당
                                resultData = errorMessage;

                                // UI 업데이트 코드
                                onPostExecute(resultData);
                            }
                        }

                        @Override
                        public void onFailure(Call<Set<CheckListResponseDto>> call, Throwable t) {
                            // 네트워크 오류 또는 예외 처리

                            // 가져온 데이터를 멤버 변수에 할당
                            resultData = "요청 실패: " + t.getMessage();

                            // UI 업데이트 코드
                            onPostExecute(resultData);
                        }
                    });
                }else {
                    //직원 기준
                    Call<Set<CheckListResponseDto>> call1 = apiService.getCompleteCheckListsByCategory(staffId, categoryId, date);

                    call1.enqueue(new Callback<Set<CheckListResponseDto>>() {
                        @Override
                        public void onResponse(Call<Set<CheckListResponseDto>> call, Response<Set<CheckListResponseDto>> response) {
                            if (response.isSuccessful()) {
                                Set<CheckListResponseDto> checkLists = response.body();
                                // 가져온 데이터 처리

                                StringBuilder result = new StringBuilder();
                                for (CheckListResponseDto checkList : checkLists) {
                                    result.append(checkList.getTitle()).append("\n");
                                    result.append(checkList.getId()).append("\n");
                                }

                                // 가져온 데이터를 멤버 변수에 할당
                                resultData = result.toString();

                                // UI 업데이트 코드
                                onPostExecute(resultData);
                            } else {
                                // 요청 실패 처리
                                String errorMessage = "요청 실패: " + response.code();
                                // 가져온 데이터를 멤버 변수에 할당
                                resultData = errorMessage;

                                // UI 업데이트 코드
                                onPostExecute(resultData);
                            }
                        }

                        @Override
                        public void onFailure(Call<Set<CheckListResponseDto>> call, Throwable t) {
                            // 네트워크 오류 또는 예외 처리

                            // 가져온 데이터를 멤버 변수에 할당
                            resultData = "요청 실패: " + t.getMessage();

                            // UI 업데이트 코드
                            onPostExecute(resultData);
                        }
                    });
                }

                // doInBackground 메서드에서 값을 반환하지 않음
                return null;

            } catch (Exception e) {
                e.printStackTrace();
                // 반환하지 않음
                return null;
            }
        }

        public void updateData(ArrayList<CheckList2_recyclerViewWordItemData> newData) {
            list.clear(); // 기존 데이터 삭제
            list.addAll(newData); // 새로운 데이터 추가
            mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터가 변경되었음을 알림
        }

        @Override
        protected void onPostExecute(String result) {
            // UI 업데이트를 메인(UI) 스레드에서 실행
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // categoryId 관찰자 추가
                    LiveData<Long> categoryIdLiveData = viewModel.getCategoryIdLiveData();
                    categoryIdLiveData.observe(getActivity(), new Observer<Long>() {
                        @Override
                        public void onChanged(Long categoryIdValue) {
                            // categoryId 값이 변경될 때 이곳에서 변수에 할당
                            categoryId = categoryIdValue;

                            // 값이 변경될 때마다 원하는 작업을 수행할 수 있습니다.
                            // 예를 들어, 데이터를 가져오는 작업을 실행할 수 있습니다.
                            // fetchData();

                            // UI 업데이트 (categoryId 변경 시 수행할 작업)
                            // 예: Toast 메시지 또는 다른 UI 업데이트
                            //Toast.makeText(getActivity(), "categoryId 값 변경: " + categoryId, Toast.LENGTH_SHORT).show();
                            fetchDataFromServer();

                        }
                    });

                    LiveData<String> dateLiveData = viewModel2.getDate();
                    // date 관찰자 추가
                    dateLiveData.observe(getActivity(), new Observer<String>() {
                        @Override
                        public void onChanged(String dateValue) {
                            // date 값이 변경될 때 이곳에서 변수에 할당
                            String date1 = dateValue;

                            // SimpleDateFormat을 사용하여 원하는 형식으로 변환
                            SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
                            SimpleDateFormat targetDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

                            try {
                                Date originalDate = originalDateFormat.parse(date1);
                                date = targetDateFormat.format(originalDate);

                                // UI 업데이트 (date 변경 시 수행할 작업)
                                // 예: Toast 메시지 또는 다른 UI 업데이트
                                //Toast.makeText(getActivity(), "date 값 변경:!! " + date, Toast.LENGTH_SHORT).show();
                                fetchDataFromServer();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

            // 이어서 데이터 처리 및 UI 업데이트 코드 작성
            if (result != null) {
                // 각각의 제목과 id를 리스트에 추가
                String[] lines = result.split("\n");
                ArrayList<CheckList2_recyclerViewWordItemData> newData = new ArrayList<>();
                for (int i = 0; i + 1 < lines.length; i += 2) {
                    String title = lines[i];
                    if (lines[i + 1] != null) {
                        long id = Long.parseLong(lines[i + 1]);
                        newData.add(new CheckList2_recyclerViewWordItemData(title, id));
                    }
                }

                // RecyclerView 어댑터 업데이트
                if (mRecyclerViewAdapter == null) {
                    mRecyclerViewAdapter = new CheckList2_RecyclerViewAdpater(getActivity(), newData);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mRecyclerViewAdapter);
                } else {
                    mRecyclerViewAdapter.updateData(newData);
                }

                // 결과를 UI에 표시
                if (newData.isEmpty()) {
                    nodataLayout.setVisibility(View.VISIBLE);
                    dataLayout.setVisibility(View.GONE);
                } else {
                    nodataLayout.setVisibility(View.GONE);
                    dataLayout.setVisibility(View.VISIBLE);
                }
            } else {
                // result가 null인 경우에 대한 처리
                // 예: 오류 메시지 출력 또는 다른 처리
            }
        }

        private void fetchDataFromServer() {
            try {
                AppInData appInData = (AppInData) getActivity().getApplicationContext();
                //staffId = appInData.getStaffId();

                LiveData<Long> categoryIdLiveData = viewModel.getCategoryIdLiveData();
                categoryId = categoryIdLiveData.getValue();
                date = viewModel2.getDate().getValue();

                // categoryId 값이 null인 경우에 대한 처리
                if (categoryId == null || categoryId <= 0) {
                    categoryId = 0L; // 또는 다른 적절한 기본값
                }


                if (date == null) {
                    Date currentDate = new Date();
                    // "yyyy-MM-dd" 형식으로 날짜를 포맷합니다.
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    // 포맷된 날짜 문자열을 얻습니다.
                    String formattedDate = sdf.format(currentDate);
                    date = formattedDate;
                }
                String date1 = date;
                // SimpleDateFormat을 사용하여 원하는 형식으로 변환
                SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
                SimpleDateFormat targetDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                Date originalDate = originalDateFormat.parse(date1);
                date = targetDateFormat.format(originalDate);


                Log.d("MyApp", "doInBackground started"+date);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                        .addConverterFactory(new NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ChecklistApiService apiService = retrofit.create(ChecklistApiService.class);
                if(owner) {
                    Call<Set<CheckListResponseDto>> call1 = apiService.getStoreCompleteCheckListsByCategory(storeId, categoryId, date); // 적절한 storeId를 사용하세요

                    call1.enqueue(new Callback<Set<CheckListResponseDto>>() {
                        @Override
                        public void onResponse(Call<Set<CheckListResponseDto>> call, Response<Set<CheckListResponseDto>> response) {
                            if (response.isSuccessful()) {
                                Set<CheckListResponseDto> checkLists = response.body();

                                // 가져온 데이터 처리
                                StringBuilder result = new StringBuilder();
                                for (CheckListResponseDto checkList : checkLists) {
                                    result.append(checkList.getTitle()).append("\n");
                                    result.append(checkList.getId()).append("\n");
                                }

                                // 가져온 데이터를 멤버 변수에 할당
                                resultData = result.toString();

                                // 이후 데이터 처리 또는 UI 업데이트 작업을 진행할 수 있습니다.
                                handleFetchedData(resultData);
                            } else {
                                // 요청 실패 처리
                                String errorMessage = "요청 실패: " + response.code();

                                // 가져온 데이터를 멤버 변수에 할당
                                resultData = errorMessage;

                                // 이후 실패 처리 작업을 진행할 수 있습니다.
                                handleFetchError(resultData);
                            }
                        }

                        @Override
                        public void onFailure(Call<Set<CheckListResponseDto>> call, Throwable t) {
                            // 네트워크 오류 또는 예외 처리

                            // 가져온 데이터를 멤버 변수에 할당
                            resultData = "요청 실패: " + t.getMessage();

                            // 이후 실패 처리 작업을 진행할 수 있습니다.
                            handleFetchError(resultData);
                        }
                    });
                }else {
                    Call<Set<CheckListResponseDto>> call1 = apiService.getCompleteCheckListsByCategory(staffId, categoryId, date); // 적절한 storeId를 사용하세요

                    call1.enqueue(new Callback<Set<CheckListResponseDto>>() {
                        @Override
                        public void onResponse(Call<Set<CheckListResponseDto>> call, Response<Set<CheckListResponseDto>> response) {
                            if (response.isSuccessful()) {
                                Set<CheckListResponseDto> checkLists = response.body();

                                // 가져온 데이터 처리
                                StringBuilder result = new StringBuilder();
                                for (CheckListResponseDto checkList : checkLists) {
                                    result.append(checkList.getTitle()).append("\n");
                                    result.append(checkList.getId()).append("\n");
                                }

                                // 가져온 데이터를 멤버 변수에 할당
                                resultData = result.toString();

                                // 이후 데이터 처리 또는 UI 업데이트 작업을 진행할 수 있습니다.
                                handleFetchedData(resultData);
                            } else {
                                // 요청 실패 처리
                                String errorMessage = "요청 실패: " + response.code();

                                // 가져온 데이터를 멤버 변수에 할당
                                resultData = errorMessage;

                                // 이후 실패 처리 작업을 진행할 수 있습니다.
                                handleFetchError(resultData);
                            }
                        }

                        @Override
                        public void onFailure(Call<Set<CheckListResponseDto>> call, Throwable t) {
                            // 네트워크 오류 또는 예외 처리

                            // 가져온 데이터를 멤버 변수에 할당
                            resultData = "요청 실패: " + t.getMessage();

                            // 이후 실패 처리 작업을 진행할 수 있습니다.
                            handleFetchError(resultData);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 이곳에서 예외 처리를 할 수 있습니다.
            }
        }

        // 데이터를 성공적으로 가져왔을 때 처리하는 메서드
        private void handleFetchedData(String data) {
            // 데이터를 가공하거나 UI를 업데이트하는 등의 작업을 수행합니다.
            // 예: 데이터를 파싱하여 리스트에 추가하고 어댑터를 업데이트하는 등
            // 결과를 UI에 표시
            if (data != null) {
                // 각각의 제목과 id를 리스트에 추가
                String[] lines = data.split("\n");
                ArrayList<CheckList2_recyclerViewWordItemData> newData = new ArrayList<>();
                for (int i = 0; i + 1 < lines.length; i += 2) {
                    String title = lines[i];
                    if (lines[i + 1] != null) {
                        long id = Long.parseLong(lines[i + 1]);
                        newData.add(new CheckList2_recyclerViewWordItemData(title, id));
                    }
                }
                Log.d("MyApp", "doInBackground started 정상");
                // RecyclerView 어댑터 업데이트
                if (mRecyclerViewAdapter == null) {
                    mRecyclerViewAdapter = new CheckList2_RecyclerViewAdpater(getActivity(), newData);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mRecyclerViewAdapter);
                } else {
                    mRecyclerViewAdapter.updateData(newData);
                }

                // 결과를 UI에 표시
                if (newData.isEmpty()) {
                    nodataLayout.setVisibility(View.VISIBLE);
                    dataLayout.setVisibility(View.GONE);
                } else {
                    nodataLayout.setVisibility(View.GONE);
                    dataLayout.setVisibility(View.VISIBLE);
                }
            } else {
                // result가 null인 경우에 대한 처리
                // 예: 오류 메시지 출력 또는 다른 처리
            }
        }

        // 데이터를 가져오지 못했을 때 처리하는 메서드
        private void handleFetchError(String errorMessage) {
            // 데이터 가져오기 실패에 대한 처리 작업을 수행합니다.
            // 예: 오류 메시지 출력 또는 다른 처리
        }
    }

}