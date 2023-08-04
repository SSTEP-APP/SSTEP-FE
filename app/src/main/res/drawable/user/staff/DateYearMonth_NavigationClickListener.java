package drawable.user.staff;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateYearMonth_NavigationClickListener implements View.OnClickListener {
    private TextView dateTextView;
    private Button leftButtonId;
    private Button rightButtonId;

    public DateYearMonth_NavigationClickListener(TextView dateTextView, Button leftButtonId, Button rightButtonId) {
        this.dateTextView = dateTextView;
        this.leftButtonId = leftButtonId;
        this.rightButtonId = rightButtonId;
    }

    @Override
    public void onClick(View view) {
        String currentDate = dateTextView.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월", Locale.getDefault());

        try {
            Date date = sdf.parse(currentDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (view == leftButtonId) {
                // 왼쪽 버튼 클릭 시 이전 달로 이동
                calendar.add(Calendar.MONTH, -1);
            } else if (view == rightButtonId) {
                // 오른쪽 버튼 클릭 시 다음 달로 이동
                calendar.add(Calendar.MONTH, 1);
            }

            Date newDate = calendar.getTime();
            String newDateStr = sdf.format(newDate);

            // 연도 변경 시 처리
            int year = calendar.get(Calendar.YEAR);
            if (newDateStr.startsWith(Integer.toString(year + 1))) {
                newDateStr = newDateStr.replace(Integer.toString(year + 1), Integer.toString(year));
            } else if (newDateStr.startsWith(Integer.toString(year - 1))) {
                newDateStr = newDateStr.replace(Integer.toString(year - 1), Integer.toString(year));
            }

            dateTextView.setText(newDateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}