package th.co.infinitecorp.www.brm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import th.co.infinitecorp.www.brm.API.BRM_API;
import th.co.infinitecorp.www.brm.Adapter.Adapter_day_listroom;
import th.co.infinitecorp.www.brm.Adapter.Adapter_hour_listroom;
import th.co.infinitecorp.www.brm.Model.AddTranBooking;
import th.co.infinitecorp.www.brm.Model.AllBooking;
import th.co.infinitecorp.www.brm.Model.DayBooking;
import th.co.infinitecorp.www.brm.Model.Remember;
import th.co.infinitecorp.www.brm.Model.RoomDetail;
import th.co.infinitecorp.www.brm.Model.TranBooking;
import th.co.infinitecorp.www.brm.Model.Userinfo;
import th.co.infinitecorp.www.brm.Model.datetime;
import Utils.Cookie;

public class RoomActivity extends AppCompatActivity {
    ImageView closebooking,show_detail_room;
    CardView btnbooking,confirm_btn_booking;
    TextView textROOM,textROOMbooking,toast_text,search_date,txt_progressbar;
    EditText edt_description;
    TimePicker timestart,timeend;
    DatePickerDialog picker;
    MaterialCalendarView calendarView;
    CalendarView calendar;
    RecyclerView list_detail_day,list_detail_hour;
    ProgressDialog progressbar;
    Toast toast;
    String s_Date,e_Date,getROOM;
    String Time_end_picker,Time_start_picker;
    LayoutInflater inflater;
    View layout;
    Bundle bd;
    Date date;
    int number_room;
    Dialog booking;
    String last_Day;
    String name_room,num_room;
    int day_return,month_return,year_return;
    Date Date_set_min;
    NestedScrollView lt1,lt2;
    private static final int TIME_PICKER_INTERVAL = 30;
    String search_text,date_picker;
    int st_ch_hr,en_ch_hr,st_ch_min,en_ch_min;
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);


        progressbar = new ProgressDialog(RoomActivity.this);
        progressbar.setCanceledOnTouchOutside(false);
        progressbar.setCancelable(false);
        progressbar.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    return true; // Consumed
                }
                else {
                    return false; // Not consumed
                }
            }
        });


        Calendar calendars = Calendar.getInstance();
        long date = calendars.getTime().getTime();

        bd = getIntent().getExtras();

        Date_set_min = new Date();
        try {
            Date_set_min = new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(Locale.getDefault()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        cal.setTime(Date_set_min);
        Integer year_min = cal.get(Calendar.YEAR);
        Integer month_min = cal.get(Calendar.MONTH)+1;
        Integer day_min = cal.get(Calendar.DAY_OF_MONTH);

        getROOM = bd.getString("ROOM");

        number_room = bd.getInt("NUMROOM");

        inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        booking = new Dialog(this);

        booking .setContentView(R.layout.booking_dialog);
        toast_text = (TextView) layout.findViewById(R.id.toast_text);
        closebooking = (ImageView) booking.findViewById(R.id.imgclose_dialog_booking);
        //show_detail_room = (ImageView) findViewById(R.id.image_detail_list_room);
        btnbooking = (CardView) findViewById(R.id.btnbooking);
        calendar = (CalendarView) booking.findViewById(R.id.calendar);
        textROOM = (TextView) findViewById(R.id.textroom);
        btnbooking = (CardView) findViewById(R.id.btnbooking);
        confirm_btn_booking = (CardView) booking.findViewById(R.id.btn_confirm_booking);
        edt_description = (EditText) booking.findViewById(R.id.edtdescription);
        textROOMbooking = (TextView) booking.findViewById(R.id.textroom_booking);
        list_detail_day = (RecyclerView) findViewById(R.id.list_detail_day);
        list_detail_hour = (RecyclerView) findViewById(R.id.list_detail_hour);
        timestart =(TimePicker) booking.findViewById(R.id.datePicker_start);
        timestart.setIs24HourView(true);
        timeend=(TimePicker) booking.findViewById(R.id.datePicker_end);
        timeend.setIs24HourView(true);
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView) ;
        booking.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_slide_leftright;

//        progressbar.show();
//        progressbar.setContentView(R.layout.progress_dialog);
//        txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
//        txt_progressbar.setText("Loading...");
//        progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        textROOM.setText(getROOM);

        // search_date.setText(currentDate);

        calendar.setMinDate(date);
        calendarView.newState().setMinimumDate(CalendarDay.from(year_min,month_min ,1 )).setCalendarDisplayMode(CalendarMode.MONTHS).commit();

        //RefreshLists(int day);
        lt1 = (NestedScrollView) findViewById(R.id.lt1);
        lt1.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                lt2.scrollTo(scrollX,scrollY);
            }
        });
        lt2 = (NestedScrollView) findViewById(R.id.lt2);
        lt2.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                lt1.scrollTo(scrollX,scrollY);
            }
        });

        Userinfo userinfoget = Cookie.GetUserinfo(getApplication());

        setTimePickerInterval(timestart);
        setTimePickerInterval(timeend);

        final Calendar cldr = Calendar.getInstance();
        Integer day = cldr.get(Calendar.DAY_OF_MONTH);
        Integer month = cldr.get(Calendar.MONTH)+1;
        Integer year = cldr.get(Calendar.YEAR);

        date_picker = year + "-" + ((month < 10) ? "0"+month.toString() : month.toString())+ "-" + ((day < 10) ? "0"+day.toString() : day.toString());
        String dates = year + "-" + ((month < 10) ? "0"+month : month)+ "-" + ((day < 10) ? "0"+day : day);
        if(Build.VERSION.SDK_INT >= 26){

            LocalDate convertedDate = LocalDate.parse(dates, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            convertedDate = convertedDate.withDayOfMonth(
                    convertedDate.getMonth().length(convertedDate.isLeapYear()));
            last_Day = String.valueOf(convertedDate.getDayOfMonth());

        }
        else{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = null;
            try {
                convertedDate = dateFormat.parse(dates);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(convertedDate);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            last_Day = String.valueOf(c.getActualMaximum(Calendar.DAY_OF_MONTH));
//            toast_text.setText(last_Day);
//            toast = new Toast(getApplicationContext());
//            toast.setDuration(Toast.LENGTH_SHORT);
//            toast.setView(layout);
//            toast.show();
            //Toast.makeText(RoomActivity.this,date_picker,Toast.LENGTH_SHORT).show();
        }
        //RefreshLists();
//        RefreshLists(day,month,year,last_Day,new ArrayList<>());
//        sync_rc();


        timestart.setHour(8);
        timestart.setMinute(0);
        timeend.setHour(8);
        timeend.setMinute(0);
        st_ch_hr = 8;
        en_ch_hr = 8;
        st_ch_min = 0;
        en_ch_min = 0;
        Time_start_picker = "08:00:00";
        Time_end_picker = "08:00:00";

//        calendarView.state().edit()
//                .setMinimumDate(CalendarDay.from(year, month, day))
//                .commit();
//        sync_rc();


        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                calendarView.setRightArrow(R.drawable.calendar_next_arrow);
                calendarView.setLeftArrow(R.drawable.calendar_back_arrow);
                String days = String.valueOf(date.getDay());
                String month = String.valueOf(date.getMonth());
                String year = String.valueOf(date.getYear());

                date_picker = year + "-" + ((date.getMonth() < 10) ? "0"+month : month)+ "-" + ((date.getDay() < 10) ? "0"+days : days);

                if(Build.VERSION.SDK_INT >= 26){
                    String dates = year + "-" + ((date.getMonth() < 10) ? "0"+month : month)+ "-" + ((date.getDay() < 10) ? "0"+days : days);
                    LocalDate convertedDate = LocalDate.parse(dates, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    convertedDate = convertedDate.withDayOfMonth(
                            convertedDate.getMonth().length(convertedDate.isLeapYear()));
                    last_Day = String.valueOf(convertedDate.getDayOfMonth());
//                    toast_text.setText(last_Day);
//                    toast = new Toast(getApplicationContext());
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.setView(layout);
//                    toast.show();

                    //Toast.makeText(RoomActivity.this,date_picker,Toast.LENGTH_SHORT).show();
                }
                else{
                    String dates =year + "-" + ((date.getMonth() < 10) ? "0"+month : month)+ "-" + ((date.getDay() < 10) ? "0"+days : days);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date convertedDate = null;
                    try {
                        convertedDate = dateFormat.parse(dates);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar c = Calendar.getInstance();
                    c.setTime(convertedDate);
                    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                    last_Day = String.valueOf(c.getActualMaximum(Calendar.DAY_OF_MONTH));

                }

                //RefreshApi();

            }
        });

        timestart.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                if(minute==1){
                    st_ch_hr = hourOfDay;
                    st_ch_min = 30;
                    Time_start_picker = hourOfDay+":30:00";
                }else{
                    st_ch_hr = hourOfDay;
                    st_ch_min = 0;
                    Time_start_picker = hourOfDay+":00:00";
                }

            }
        });
        timeend.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                if(minute==1){
                    en_ch_hr=hourOfDay;
                    en_ch_min=30;
                    Time_end_picker = hourOfDay+":30:00";
                }
                else{
                    en_ch_hr=hourOfDay;
                    en_ch_min=0;
                    Time_end_picker = hourOfDay+":00:00";
                }

            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                //date_picker = dayOfMonth + "/" + month + "/"+ year;
                Integer M = month + 1;
                Integer D = dayOfMonth;
                date_picker = year + "-" + ((M < 10) ? "0"+M.toString() : M.toString())+ "-" + ((D < 10) ? "0"+D.toString() : D.toString());
            }
        });

        hideKeyboard(RoomActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent newActivity = new Intent(this,HomeActivity.class);
        startActivity(newActivity);
    }


    @Override
    protected void onStart() {
        super.onStart();

        RefreshApi();

        btnbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Userinfo userinfo_get = Cookie.GetUserinfo(getApplication());
                textROOMbooking.setText("BOOKING " + getROOM);
                if(userinfo_get.getUserName()==null || userinfo_get.getUserName().equals("")){
                    Intent newActivity = new Intent(RoomActivity.this,LoginActivity.class);
                    startActivity(newActivity);
                }
                else{
                    confirm_btn_booking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if((st_ch_hr>en_ch_hr)){
                                toast_text.setText("Please check your booking end hour and minute!");
                                toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                progressbar.cancel();
                            }
                            else if((st_ch_hr==en_ch_hr)&&(st_ch_min>=en_ch_min)) {
                                toast_text.setText("Please check your booking time is equal!");
                                toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                progressbar.cancel();
                            }
                            else if((st_ch_hr<en_ch_hr)&&(st_ch_min==en_ch_min)){
                                toast_text.setText("Please check your booking min!");
                                toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                progressbar.cancel();
                            }
                            else {
                                progressbar.show();
                                progressbar.setContentView(R.layout.progress_dialog);
                                txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
                                txt_progressbar.setText("Loading...");
                                progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                Userinfo get_userinfo = Cookie.GetUserinfo(getApplication());
                                AddTranBooking set_add_tranbooking = new AddTranBooking();
                                s_Date = date_picker+" "+Time_start_picker;
                                e_Date = date_picker+" "+Time_end_picker;

                                set_add_tranbooking.setStart_Date(s_Date);
                                set_add_tranbooking.setEnd_Date(e_Date);
                                set_add_tranbooking.setDescription(edt_description.getText().toString());
                                set_add_tranbooking.setRecorderID(get_userinfo.getID());
                                set_add_tranbooking.setRoomID(String.valueOf(number_room));

                                new BRM_API().POST_DistributeAddTranBooking(RoomActivity.this, set_add_tranbooking, new BRM_API.DistributeAddTranBookingListener() {
                                    @SuppressLint("NewApi")
                                    @Override
                                    public void onListener(Integer http_code) {
                                        if(http_code.equals(200)){
                                            toast_text.setText("Booking Success!");
                                            toast = new Toast(getApplicationContext());
                                            toast.setDuration(Toast.LENGTH_SHORT);
                                            toast.setView(layout);
                                            toast.show();
                                            cleardialog();
                                            RefreshApi();

                                        }
                                        else if(http_code.equals(400))
                                        {
                                            toast_text.setText("Booking Failure,The Date or Time already exists!");
                                            toast = new Toast(getApplicationContext());
                                            toast.setDuration(Toast.LENGTH_SHORT);
                                            toast.setView(layout);
                                            toast.show();
                                        }
                                        else if(http_code.equals(504)){
                                            toast_text.setText("Internet is not Connected, Please check your internet");
                                            toast = new Toast(getApplicationContext());
                                            toast.setDuration(Toast.LENGTH_SHORT);
                                            toast.setView(layout);
                                            toast.show();
                                        }
                                        else{
                                            toast_text.setText("Booking Failure,lost connect!");
                                            toast = new Toast(getApplicationContext());
                                            toast.setDuration(Toast.LENGTH_SHORT);
                                            toast.setView(layout);
                                            toast.show();
                                            cleardialog();

                                        }
                                        progressbar.cancel();
                                    }

                                });
                                booking.dismiss();
                            }
                        }
                    });
                    closebooking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            booking.dismiss();
                        }
                    });
                    booking.show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Remember remember_get =  Cookie.GetRemember(getApplication());
        Remember remember_save = new Remember();
        Userinfo userinfo_get = Cookie.GetUserinfo(getApplication());
        Userinfo userinfo_save = new Userinfo();

        if(!remember_get.getRemember_status() && !remember_get.getRemember_check()){
            userinfo_save.setDepartmentName("");
            userinfo_save.setEmail("");
            userinfo_save.setID("");
            userinfo_save.setName("");
            userinfo_save.setPassword("");
            userinfo_save.setUserName("");
            userinfo_save.setPhoneNumber("");

            Cookie.SaveUserinfo(getApplication(),userinfo_save);
            remember_save.setRemember_status(false);
            Cookie.SaveRemember(getApplication(),remember_save);
        }
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void RefreshApi(){
        progressbar.show();
        progressbar.setContentView(R.layout.progress_dialog);
        txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
        txt_progressbar.setText("Loading...");
        progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        new BRM_API().GET_DistributeRoomDetail(RoomActivity.this, String.valueOf(number_room), new BRM_API.DistributeRoomDetailListener() {
            @Override
            public void onListener(List<RoomDetail> info, Integer http_code) {
                if(http_code.equals(200)){
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Refreshlist(info);
                    progressbar.cancel();
                }else if(http_code.equals(200)){
                    Intent newActivity = new Intent(RoomActivity.this,MainActivity.class);
                    progressbar.cancel();
                    startActivity(newActivity);
                    toast_text.setText("Internet is not Connected, Please check your internet");
                    toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
                else{
                    Intent newActivity = new Intent(RoomActivity.this,MainActivity.class);
                    progressbar.cancel();
                    startActivity(newActivity);
                    toast_text.setText("Connection failure ,Reconnect");
                    toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });


    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setTimePickerInterval(TimePicker timePicker) {
        try {

            NumberPicker minutePicker = (NumberPicker) timePicker.findViewById(Resources.getSystem().getIdentifier(
                    "minute", "id", "android"));
            NumberPicker hourPicker = (NumberPicker) timePicker.findViewById(Resources.getSystem().getIdentifier(
                    "hour", "id", "android"));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            hourPicker.setMinValue(8);
            hourPicker.setMaxValue(17);
            List<String> displayedValues = new ArrayList<String>();
            List<String> displayedValues_hr = new ArrayList<>();
            for(int n = 8; n<18 ; n++   ){
                displayedValues_hr.add(String.format("%02d",n));
            }
            hourPicker.setDisplayedValues(displayedValues_hr.toArray(new String[0]));
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
        } catch (Exception e) {

        }
    }


    public void Refreshlist (List<RoomDetail> roomDetailList){
        List<datetime> datas = new ArrayList<>();
        List<DayBooking> listbooking = new ArrayList<>();
        int max_dayofmonth = Integer.parseInt(last_Day);

        for(int lb = 1;lb<=max_dayofmonth;lb++){

            List<RoomDetail> list = new ArrayList<>();
            for (RoomDetail data : roomDetailList){
                Date STDATE = new Date();
                try {
                    STDATE  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(data.getStart_Date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
                cal.setTime(STDATE);
                Integer month_st = cal.get(Calendar.DAY_OF_MONTH);
                if(lb == month_st)
                {
                    list.add(data);
                }
            }

            listbooking.add(new DayBooking(lb,list));
        }

        for(int day = 1 ;day<=max_dayofmonth;day++){
            datas.add(new datetime(day));
        }

        Adapter_day_listroom adapter_day = new Adapter_day_listroom(getApplicationContext(), datas) ;
        Adapter_hour_listroom adapter_booking = new Adapter_hour_listroom(getApplicationContext(), listbooking);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        //RecyclerView.LayoutManager layoutManager_hour = new GridLayoutManager(getApplicationContext(),20);
        //RecyclerView.LayoutManager layoutManager_full = new GridLayoutManager(getApplicationContext(),31);
        RecyclerView.LayoutManager layoutManager_hour= new LinearLayoutManager(getApplicationContext());

        list_detail_day.setHasFixedSize(true);
        list_detail_day.setLayoutManager(layoutManager);
        list_detail_day.setAdapter(adapter_day);

        list_detail_hour.setHasFixedSize(true);
        list_detail_hour.setLayoutManager(layoutManager_hour);
        list_detail_hour.setAdapter(adapter_booking);

       // sync_rc();
    }

//    public void booking(AddTranBooking set_add_tranbooking){
//        new BRM_API().POST_DistributeAddTranBooking(RoomActivity.this, set_add_tranbooking, new BRM_API.DistributeAddTranBookingListener() {
//            @Override
//            public void onListener(Integer http_code) {
//                if(http_code.equals(200)){
//                    toast_text.setText("Booking Success!");
//                    toast = new Toast(getApplicationContext());
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.setView(layout);
//                    toast.show();
//
//                }
//                else if(http_code.equals(400))
//                {
//                    toast_text.setText("Booking Failure,The Date or Time already exists!");
//                    toast = new Toast(getApplicationContext());
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.setView(layout);
//                    toast.show();
//                }
//                else{
//                    toast_text.setText("Booking Failure,lost connect!");
//                    toast = new Toast(getApplicationContext());
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.setView(layout);
//                    toast.show();
//                }
//                progressbar.cancel();
//            }
//
//        });
//
//    }




    @SuppressLint("NewApi")
    public void cleardialog(){
        timestart.setHour(8);
        timestart.setMinute(0);
        timeend.setHour(8);
        timeend.setMinute(0);
        edt_description.setText("");
        Time_start_picker = "08:00:00";
        Time_end_picker = "08:00:00";
    }


}