package th.co.infinitecorp.www.brm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import Utils.GDATA;
import th.co.infinitecorp.www.brm.Model.Remember;
import th.co.infinitecorp.www.brm.Model.TimeOnDay;
import th.co.infinitecorp.www.brm.Model.Userinfo;
import Utils.Cookie;

public class MainActivity extends AppCompatActivity {
    Handler setdelay;
    Runnable startdelay,enddelay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<TimeOnDay> timeOnDayList = new ArrayList<>();
        timeOnDayList.add(new TimeOnDay("08:00",8.0));
        timeOnDayList.add(new TimeOnDay("08:30",8.3));
        timeOnDayList.add(new TimeOnDay("09:00",9.0));
        timeOnDayList.add(new TimeOnDay("09:30",9.3));
        timeOnDayList.add(new TimeOnDay("10:00",10.0));
        timeOnDayList.add(new TimeOnDay("10:30",10.3));
        timeOnDayList.add(new TimeOnDay("11:00",11.0));
        timeOnDayList.add(new TimeOnDay("11:30",11.3));
        timeOnDayList.add(new TimeOnDay("12:00",12.0));
        timeOnDayList.add(new TimeOnDay("12:30",12.3));
        timeOnDayList.add(new TimeOnDay("13:00",13.0));
        timeOnDayList.add(new TimeOnDay("13:30",13.3));
        timeOnDayList.add(new TimeOnDay("14:00",14.0));
        timeOnDayList.add(new TimeOnDay("14:30",14.3));
        timeOnDayList.add(new TimeOnDay("15:00",15.0));
        timeOnDayList.add(new TimeOnDay("15:30",15.3));
        timeOnDayList.add(new TimeOnDay("16:00",16.0));
        timeOnDayList.add(new TimeOnDay("16:30",16.3));
        timeOnDayList.add(new TimeOnDay("17:00",17.0));
        timeOnDayList.add(new TimeOnDay("17:30",17.3));

        GDATA.timeOnDays = timeOnDayList;

        setdelay = new Handler();
        startdelay = new Runnable() {
            @Override
            public void run() {
                Intent newActivity = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(newActivity);
            }
        };setdelay.postDelayed(startdelay,2000);

        enddelay = new Runnable() {
            @Override
            public void run() {
                Intent newActivity = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(newActivity);
            }
        };setdelay.postDelayed(startdelay,2000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Remember remember_get =  Cookie.GetRemember(getApplication());
        remember_get.setRemember_check(false);
        Cookie.SaveRemember(getApplication(),remember_get);
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

    @Override
    public void onBackPressed() {

    }



}