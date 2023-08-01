package drawable.user.staff;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class addSchedule extends AppCompatActivity {


    SimpleDateFormat ymFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    Calendar calendar = new GregorianCalendar(); //오늘날짜 받기
    String mDate = ymFormat.format(calendar.getTime());
    ImageButton backBtn;
    Button selectDate, completeBtn,dayAddBtn;
    TextView startTimeText, endTimeText;
    Dialog timePickDialog;
    CheckBox sun, mon,tue,wed,thu,fri,sat;
    LinearLayout checkLinearL;
    private RecyclerView mRecyclerView;
    private AddSch_RecyclerViewAdpater mRecyclerViewAdapter;
    private ArrayList<Staff_infoInput_recyclerViewItem> mList;
    private ArrayList<String> checkedItems = new ArrayList<>();
    int checkCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule);

        sun = findViewById(R.id.addSch_sunBtn);
        mon = findViewById(R.id.addSch_monBtn);
        tue = findViewById(R.id.addSch_tueBtn);
        wed = findViewById(R.id.addSch_wedBtn);
        thu = findViewById(R.id.addSch_thuBtn);
        fri = findViewById(R.id.addSch_friBtn);
        sat = findViewById(R.id.addSch_satBtn);

        checkLinearL = findViewById(R.id.addSch_checkLinearL);
        completeBtn = findViewById(R.id.addSch_completeBtn);
        backBtn = findViewById(R.id.addSch_backBtn);
        startTimeText = findViewById(R.id.addSch_startTimeText);
        endTimeText = findViewById(R.id.addSch_endTimeText);
        selectDate = findViewById(R.id.addSch_selectDate);
        timePickDialog = new Dialog(addSchedule.this);
        timePickDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        timePickDialog.setContentView(R.layout.dialog_time_setting);// xml 레이아웃 파일과 연결
        dayAddBtn = findViewById(R.id.addSch_dayAddBtn);



        //뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), staff_infoInput.class);
                startActivity(intent);
                finish();
            }
        });

        //일정시작일 설정
        selectDate.setText(mDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //시간을 누르면 다이얼로그 생성
        startTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickDialog.show();
                TimePicker timePicker = timePickDialog.findViewById(R.id.dialog_time_set_timePicker);

                timePicker.setIs24HourView(true);
                Button dialog_time_set_okBtn = timePickDialog.findViewById(R.id.dialog_time_set_okBtn);
                Button dialog_time_set_cancleBtn = timePickDialog.findViewById(R.id.dialog_time_set_cancleBtn);
                dialog_time_set_cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timePickDialog.dismiss();
                    }
                });
                dialog_time_set_okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startTimeText.setText( timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                        timePickDialog.dismiss();

                    }
                });
            }
        });

        //시간을 누르면 다이얼로그 생성
        endTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickDialog.show();
                TimePicker timePicker = timePickDialog.findViewById(R.id.dialog_time_set_timePicker);

                timePicker.setIs24HourView(true);
                Button dialog_time_set_okBtn1 = timePickDialog.findViewById(R.id.dialog_time_set_okBtn);
                Button dialog_time_set_cancleBtn1 = timePickDialog.findViewById(R.id.dialog_time_set_cancleBtn);
                //취소버튼 누르면 취소처리
                dialog_time_set_cancleBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timePickDialog.dismiss();
                    }
                });
                //확인버튼 누르면 시간을 텍스트에 기록
                dialog_time_set_okBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        endTimeText.setText( timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                    }
                });
            }
        });

        //설정된 값을 리스트로 생성

        dayAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerViewAdapter ==null){
                    getCheckedItems();
                    String b = startTimeText.getText().toString();
                    String c = endTimeText.getText().toString();
                    String[] strArray1 = b.split(":");
                    String[] strArray2 = c.split(":");
                    int hoursDiff = Integer.parseInt(strArray2[0]) - Integer.parseInt(strArray1[0]);
                    int minutesDiff = Integer.parseInt(strArray2[1]) - Integer.parseInt(strArray1[1]);
                    //리사이클 뷰
                    firstInit();
                    for (int i = 0; i < checkCount; i++) {
                        addItem(checkedItems.get(i),startTimeText.getText().toString()+"~"+endTimeText.getText().toString(), hoursDiff+"h"+minutesDiff);
                    }
                    mRecyclerViewAdapter = new AddSch_RecyclerViewAdpater(mList);
                    mRecyclerView.setAdapter(mRecyclerViewAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(addSchedule.this, RecyclerView.VERTICAL, false));
                }
                else {
                    mRecyclerViewAdapter.notifyDataSetChanged();
                    getCheckedItems();
                    String b = startTimeText.getText().toString();
                    String c = endTimeText.getText().toString();
                    String[] strArray1 = b.split(":");
                    String[] strArray2 = c.split(":");
                    int hoursDiff = Integer.parseInt(strArray2[0]) - Integer.parseInt(strArray1[0]);
                    int minutesDiff = Integer.parseInt(strArray2[1]) - Integer.parseInt(strArray1[1]);
                    for (int i = 0; i < checkCount; i++) {
                        addItem(checkedItems.get(i),startTimeText.getText().toString()+"~"+endTimeText.getText().toString(), hoursDiff+"h"+minutesDiff);
                    }
                    mRecyclerViewAdapter = new AddSch_RecyclerViewAdpater(mList);
                    mRecyclerView.setAdapter(mRecyclerViewAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(addSchedule.this, RecyclerView.VERTICAL, false));

                }

            }
        });

        //완료버튼
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //개발중~ 끝나는 시간이 작을경우 어떻게 할 것인가
                //String b=startTimeText.getText().toString();
                //if(b!=null){
                //    String c = endTimeText.getText().toString();
                //    String[] strArray1 = b.split(":");
                //    String[] strArray2 = c.split(":");
                //    int hoursDiff = Integer.parseInt(strArray2[0]) - Integer.parseInt(strArray1[0]);
                //    int minutesDiff = Integer.parseInt(strArray2[1]) - Integer.parseInt(strArray1[1]);
                //    if (hoursDiff<0){
                //        Toast.makeText(getApplicationContext(), "끝나는 시간이 시작시간보다 작습니다.", Toast.LENGTH_SHORT).show();
                //    }
                // }
                ArrayList<String> listData = new ArrayList<>();

                if(mList!=null) {
                    for (int i = 0; i < mList.size(); i++) {
                        String data = mList.get(i).getStaff_infoInput_days() +","+ mList.get(i).getStaff_infoInput_time()  +","+ mList.get(i).getStaff_infoInput_cancelImg();
                        listData.add(data);
                    }
                }
                Intent intent = new Intent(addSchedule.this, staff_infoInput.class);
                intent.putStringArrayListExtra("LIST_DATA", listData);
                startActivity(intent);
            }
        });

    }
    public void firstInit(){
        mRecyclerView = (RecyclerView) findViewById(R.id.addSch_scheduleRV); //리사이클뷰 아이디 받기
        mList = new ArrayList<>();
    }

    public void addItem(String day_of_week, String time, String workTime){
        Staff_infoInput_recyclerViewItem item = new Staff_infoInput_recyclerViewItem();

        item.setStaff_infoInput_days(day_of_week);
        item.setStaff_infoInput_time(time);
        item.setStaff_infoInput_cancelImg(workTime);

        mList.add(item);
    }
    private void getCheckedItems() {
        checkedItems.clear();
        checkCount = 0;
        int childCount = checkLinearL.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = checkLinearL.getChildAt(i);
            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    checkedItems.add(checkBox.getText().toString());
                    checkCount++;
                }
            }
        }
        for (int i = 0; i < checkLinearL.getChildCount(); i++) {
            View childView = checkLinearL.getChildAt(i);
            if (childView instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) childView;
                checkBox.setChecked(false);
            }
        }
    }
}