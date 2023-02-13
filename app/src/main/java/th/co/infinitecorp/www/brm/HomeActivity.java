package th.co.infinitecorp.www.brm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import Utils.Network;
import th.co.infinitecorp.www.brm.Model.Remember;
import th.co.infinitecorp.www.brm.Model.Userinfo;
import Utils.Cookie;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   Button CardRoom1,CardRoom2,CardRoom3,CardRoom4;
    String ROOM;
    int NUMROOM;
    ImageView imgdrawer;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Intent select;
    TextView txt_progressbar,name_id;
    ProgressDialog progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       checkinternet();
        imgdrawer = (ImageView) findViewById(R.id.drawerbtn_home) ;
        CardRoom1 = (Button) findViewById(R.id.CardRoom1);
        CardRoom2 = (Button) findViewById(R.id.CardRoom2);
        CardRoom3 = (Button) findViewById(R.id.CardRoom3);
        CardRoom4 = (Button) findViewById(R.id.CardRoom4);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        name_id = (TextView) headerView.findViewById(R.id.name_id);

    Remember remember_get = Cookie.GetRemember(getApplication());
        Userinfo userinfo_get = Cookie.GetUserinfo(getApplication());

        if(remember_get.getRemember_status() || remember_get.getRemember_check()){
        //th.co.infinitecorp.www.brm.menu clear on no login
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu);
            name_id.setText(userinfo_get.getName());

        }
        else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu2);
                name_id.setText("");
        }


        progressbar = new ProgressDialog(HomeActivity.this);
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

        CardRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent (HomeActivity.this,RoomActivity.class);
                 ROOM = "ROOM 1 ";
                 NUMROOM = 1;
                 newActivity.putExtra("NUMROOM",NUMROOM);
                newActivity.putExtra("ROOM",ROOM);
                startActivity(newActivity);
            }
        });

        CardRoom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent (HomeActivity.this,RoomActivity.class);
                ROOM = "ROOM 2 ";
                newActivity.putExtra("ROOM",ROOM);
                NUMROOM = 2;
                newActivity.putExtra("NUMROOM",NUMROOM);
                startActivity(newActivity);
            }
        });

        CardRoom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent (HomeActivity.this,RoomActivity.class);
                ROOM = "ROOM 3 ";
                newActivity.putExtra("ROOM",ROOM);
                NUMROOM = 3;
                newActivity.putExtra("NUMROOM",NUMROOM);
                startActivity(newActivity);
            }
        });

        CardRoom4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent (HomeActivity.this,RoomActivity.class);
               ROOM = "ROOM 4 ";
                NUMROOM = 4;
                newActivity.putExtra("NUMROOM",NUMROOM);
               newActivity.putExtra("ROOM",ROOM);
                startActivity(newActivity);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /* AddUserinfo add_userinfo = Cookie.GetAddUserinfo(getApplication());
        add_userinfo.getName();
        add_userinfo.getPassword();*/

    }

    @Override
    public void onBackPressed() {


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()){
            case R.id.menu_login_id:
                select = new Intent(HomeActivity.this, LoginActivity.class);
                drawerLayout.closeDrawers();
                startActivity(select);

                break;
            case R.id.menu_home_id:
               // select = new Intent(HomeActivity.this, HomeActivity.class);
                drawerLayout.closeDrawers();
               // startActivity(select);

                break;
            case R.id.menu_profile_id:
                select = new Intent(HomeActivity.this, ProfileActivity.class);
                drawerLayout.closeDrawers();
                startActivity(select);

                break;
            case R.id.menu_detail_id:
                select = new Intent(HomeActivity.this, DetailBookingActivity.class);
                drawerLayout.closeDrawers();
                startActivity(select);

                break;
            case R.id.menu_logout_id:
                select = new Intent(HomeActivity.this, MainActivity.class);
                drawerLayout.closeDrawers();
                progressbar.show();
                progressbar.setContentView(R.layout.progress_dialog);
                txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
                txt_progressbar.setText("Logging out...");
                progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        progressbar.cancel();
                        Remember remember_save = new Remember();
                        remember_save.setRemember_status(false);
                        Cookie.SaveRemember(getApplication(),remember_save);
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



        }

        return true;
    }

    public void checkinternet(){
        Intent newActivity = new Intent(HomeActivity.this,MainActivity.class);

        LayoutInflater inflater = getLayoutInflater();
        View  layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        Toast toast;
        TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
        if(Network.isNetworkConnected(HomeActivity.this)){

        }else{
            startActivity(newActivity);
            toast_text.setText("Internet is not Connected, Please check your internet");
            toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }
}