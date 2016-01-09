package com.doudou.cn.testcalendarviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements OnClickListener,OnTouchListener{
	private CalendarView calendar;
	private ImageButton calendarLeft;
	private TextView calendarCenter;
	private ImageButton calendarRight;
	private SimpleDateFormat format;
	private RelativeLayout rl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
    private void initView() {
        format = new SimpleDateFormat("yyyy-MM-dd");
        //获取日历控件对象
        calendar = (CalendarView)findViewById(R.id.calendar);
        rl = (RelativeLayout) findViewById(R.id.rl);
        calendar.setSelectMore(false); //单选  
        calendarLeft = (ImageButton)findViewById(R.id.calendarLeft);
        calendarCenter = (TextView)findViewById(R.id.calendarCenter);
        calendarRight = (ImageButton)findViewById(R.id.calendarRight);
        try {
            //设置日历日期
            Date date = format.parse("2015-01-01");
            calendar.setCalendarData(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
        String[] ya = calendar.getYearAndmonth().split("-"); 
        calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
        calendar.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            
            @Override
            public void onGlobalLayout() {
                calendar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                LayoutParams lp = (LayoutParams) rl.getLayoutParams();
                lp.width=calendar.getWidth();
                rl.setLayoutParams(lp);
            }
        });
        calendarLeft.setOnClickListener(this);
        calendarRight.setOnClickListener(this);
        //设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
        calendar.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void OnItemClick(Date selectedStartDate,
                    Date selectedEndDate, Date downDate) {
                if(calendar.isSelectMore()){
                    Toast.makeText(getApplicationContext(), format.format(selectedStartDate)+"到"+format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), format.format(downDate), Toast.LENGTH_SHORT).show();
                }
            }
        }); 
        
    }
    @Override
    public void onClick(View v) {
        String[] ya=null;
       switch (v.getId()) {
        case R.id.calendarLeft:
          //点击上一月 同样返回年月 
            String leftYearAndmonth = calendar.clickLeftMonth(); 
             ya = leftYearAndmonth.split("-"); 
            calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
            break;

        case R.id.calendarRight:
            //点击下一月
            String rightYearAndmonth = calendar.clickRightMonth();
             ya = rightYearAndmonth.split("-"); 
            calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
            break;
    }
        
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub 
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub 
        return super.onTouchEvent(event);
    }
}
