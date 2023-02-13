package th.co.infinitecorp.www.brm;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import th.co.infinitecorp.www.brm.API.BRM_API;
import th.co.infinitecorp.www.brm.Adapter.AdapterMyList;
import th.co.infinitecorp.www.brm.Model.MyBooking;
import th.co.infinitecorp.www.brm.Model.Remember;
import th.co.infinitecorp.www.brm.Model.Userinfo;
import Utils.Cookie;

public class DetailBookingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Intent select;
    ImageView imgdrawer,close;
    TextView txt_progressbar,name_id,detail,toast_text;
    ProgressDialog progressbar;
    RecyclerView list_detail;
    Date datestart,dateend;
    CardView btn_cancel_booking;
    Dialog dialog_detail;
    View v,layout;
    Toast toast;
    AlertDialog dialog;
    SwipeRefreshLayout swipe_refresh;
    LayoutInflater inflater;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_booking);

        progressbar = new ProgressDialog(DetailBookingActivity.this);
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

        inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        toast_text = (TextView) layout.findViewById(R.id.toast_text);

        dialog_detail = new Dialog(this);

        dialog_detail.setContentView(R.layout.my_detail_select_list);
        dialog_detail.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        list_detail = (RecyclerView) findViewById(R.id.list_mydetail);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(DetailBookingActivity.this);
        imgdrawer = (ImageView) findViewById(R.id.drawerbtn_detail_booking) ;
        View headerView = navigationView.getHeaderView(0);
        name_id = (TextView) headerView.findViewById(R.id.name_id);
        inflater = LayoutInflater.from(DetailBookingActivity.this);
        v = inflater.inflate(R.layout.my_detail_select_list,null);
//        dialog = new AlertDialog.Builder(DetailBookingActivity.this).create();
//        dialog.setView(v);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

         close = (ImageView) v.findViewById(R.id.imgclose_my_dialog_select);
         detail = (TextView) v.findViewById(R.id.txt_my_detail_select_dialog);
        btn_cancel_booking = (CardView) v.findViewById(R.id.btn_cancel_booking);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setProgressBackgroundColorSchemeResource(R.color.bg_progress_color);
        swipe_refresh.setColorSchemeColors(R.color.bg_color_1,R.color.bg_color_2,R.color.bg_color_3);
         //btn_show_my_booking = (CardView) findViewById(R.id.image_detail_my_booking);

        Userinfo userinfoget = Cookie.GetUserinfo(getApplication());
        if(userinfoget.getUserName() == null||userinfoget.getUserName().equals("")){
            //th.co.infinitecorp.www.brm.menu clear on no login
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu2);
            name_id.setText("");
        }
        else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu);
            name_id.setText(userinfoget.getName());
        }

        btn_cancel_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancel booking
                //refresh list when update
                //RefreshLists();
            }
        });

        imgdrawer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout =(DrawerLayout) findViewById(R.id.drawer);
                // If the navigation drawer is not open then open it, if its already open then close it.

                if(!drawerLayout.isDrawerOpen(Gravity.START)){
                    drawerLayout.openDrawer(Gravity.START);
                }
                else {
                    drawerLayout.closeDrawer(Gravity.END);
                }

            }
        });

//        btn_show_my_booking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog_detail.show();
//            }
//        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        //cookies
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshLists();
                swipe_refresh.setRefreshing(false);
            }
        });
        RefreshLists();
//        Userinfo userinfo = Cookie.GetUserinfo(getApplication());
//        new BRM_API().GET_DistributeMyBooking(DetailBookingActivity.this,userinfo.getID(),new BRM_API.DistributeMyBookingListener() {
//            @Override
//            public void onListener(List<MyBooking> info, Integer http_code) {
//                if(http_code.equals(200)){
//                    RefreshLists(info);
//                    progressbar.cancel();
//                }else{
//                    //reconnect ,lost connect
//                    Intent newActivity = new Intent(DetailBookingActivity.this,MainActivity.class);
//                    progressbar.cancel();
//                    startActivity(newActivity);
//                    toast_text.setText("Connection failure ,Reconnect");
//                    toast = new Toast(getApplicationContext());
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.setView(layout);
//                    toast.show();
////                    RefreshLists(info);
//                }
//
//            }
//
//        });
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()){
            case R.id.menu_home_id:
                select = new Intent(DetailBookingActivity.this, HomeActivity.class);
                drawerLayout.closeDrawers();
                startActivity(select);

                break;
            case R.id.menu_profile_id:
                select = new Intent(DetailBookingActivity.this, ProfileActivity.class);
                drawerLayout.closeDrawers();
                startActivity(select);

                break;
            case R.id.menu_detail_id:
                //current room refresh
                select = new Intent(DetailBookingActivity.this, DetailBookingActivity.class);
                drawerLayout.closeDrawers();
                startActivity(select);

                break;
            case R.id.menu_logout_id:
                select = new Intent(DetailBookingActivity.this, MainActivity.class);
                drawerLayout.closeDrawers();
                progressbar.show();
                progressbar.setContentView(R.layout.progress_dialog);
                txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
                txt_progressbar.setText("Logging out...");
                progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                        progressbar.cancel();
                        Userinfo userinfosave = new Userinfo();
                        userinfosave.setDepartmentName("");
                        userinfosave.setEmail("");
                        userinfosave.setID("");
                        userinfosave.setName("");
                        userinfosave.setPassword("");
                        userinfosave.setUserName("");
                        userinfosave.setPhoneNumber("");
                        Cookie.SaveUserinfo(getApplication(),userinfosave);
                        startActivity(select);

                break;

        }

        return true;
    }

    private void RefreshLists(){
        //cookies
        // RefreshLists(info);
        progressbar.show();
        progressbar.setContentView(R.layout.progress_dialog);
        txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
        txt_progressbar.setText("Loading...");
        progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Userinfo userinfo = Cookie.GetUserinfo(getApplication());
        new BRM_API().GET_DistributeMyBooking(DetailBookingActivity.this,userinfo.getID(),new BRM_API.DistributeMyBookingListener() {
            @Override
            public void onListener(List<MyBooking> info, Integer http_code) {
                if(http_code.equals(200)){

                    AdapterMyList adapter = new AdapterMyList(getApplicationContext(), info) ;

//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                    list_detail.setHasFixedSize(true);
                    list_detail.setLayoutManager(layoutManager);
                    list_detail.setAdapter(adapter);
                    //progressbar.cancel();

                    progressbar.cancel();
                }else if(http_code.equals(504)){
                    //reconnect ,lost connect
                    Intent newActivity = new Intent(DetailBookingActivity.this,MainActivity.class);
                    progressbar.cancel();
                    startActivity(newActivity);
                    toast_text.setText("Internet is not Connected, Please check your internet");
                    toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
                else
                    {
                    //reconnect ,lost connect
                    Intent newActivity = new Intent(DetailBookingActivity.this,MainActivity.class);
                    progressbar.cancel();
                    startActivity(newActivity);
                    toast_text.setText("Connection failure ,Reconnect");
                    toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
//                    RefreshLists(info);
                }

            }

        });

    }
}