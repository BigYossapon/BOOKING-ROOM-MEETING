package th.co.infinitecorp.www.brm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import th.co.infinitecorp.www.brm.API.BRM_API;
import th.co.infinitecorp.www.brm.Model.Department;
import th.co.infinitecorp.www.brm.Model.Remember;
import th.co.infinitecorp.www.brm.Model.Userinfo;
import Utils.Cookie;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int inviandvisi=0,numcheck=0;
    Spinner spinner_department;
    EditText edt_name,edt_phone,edt_email,edt_username,edt_oldpassword,edt_newpassword,edt_renewpassword;
    CardView btn_showeditpass,confirm_profile;
    String selectedItem;
    TextView txt_progressbar,toast_text,name_id;
    ProgressDialog progressbar;
    ImageView img_checkrenew_pass,imgdrawer;
    FrameLayout layout_renew_password;
    Toast toast;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Intent select;
    LayoutInflater inflater;
    View layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        inflater = getLayoutInflater();
       layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        imgdrawer = (ImageView) findViewById(R.id.drawerbtn_profile) ;
        toast_text = (TextView) layout.findViewById(R.id.toast_text);
        spinner_department = (Spinner)findViewById(R.id.profile_spinner_department);
        btn_showeditpass = (CardView) findViewById(R.id.passwordshow);
        confirm_profile = (CardView) findViewById(R.id.confirm_profile);
        edt_name = (EditText) findViewById(R.id.profile_name);
        edt_phone = (EditText) findViewById(R.id.profile_phone);
        edt_email = (EditText) findViewById(R.id.profile_email);
        edt_username = (EditText) findViewById(R.id.profile_username);
        edt_oldpassword = (EditText) findViewById(R.id.profile_oldpassword);
        edt_newpassword = (EditText) findViewById(R.id.profile_newpassword);
        edt_renewpassword = (EditText) findViewById(R.id.profile_renewpassword);
        img_checkrenew_pass = (ImageView) findViewById(R.id.img_checkpass_profile);
        layout_renew_password = (FrameLayout) findViewById(R.id.profile_layout_renewpassword);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(ProfileActivity.this);

        View headerView = navigationView.getHeaderView(0);
        name_id = (TextView) headerView.findViewById(R.id.name_id);
        progressbar = new ProgressDialog(ProfileActivity.this);
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

        edt_name.setText(userinfoget.getName());
        edt_email.setText(userinfoget.getEmail());
        edt_phone.setText(userinfoget.getPhoneNumber());
        edt_username.setText(userinfoget.getUserName());

        //set my profile spinner department
        if(userinfoget.getDepartmentName().equals("RD")){
            spinner_department.setSelection(1);
        }
        else if(userinfoget.getDepartmentName().equals("TS")){
            spinner_department.setSelection(2);
        }
        else if(userinfoget.getDepartmentName().equals("HR")){
            spinner_department.setSelection(3);
        }
        else if(userinfoget.getDepartmentName().equals("SG")){
            spinner_department.setSelection(4);
        }
        else if(userinfoget.getDepartmentName().equals("PM")){
            spinner_department.setSelection(5);
        }

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


        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedItem = spinner_department.getItemAtPosition(position).toString();



            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_showeditpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inviandvisi==0){
                    edt_oldpassword.setVisibility(View.VISIBLE);
                    edt_newpassword.setVisibility(View.VISIBLE);
                    layout_renew_password.setVisibility(View.VISIBLE);
                    inviandvisi=1;
                }
                else{
                    edt_oldpassword.setVisibility(View.GONE);
                    edt_newpassword.setVisibility(View.GONE);
                    layout_renew_password.setVisibility(View.GONE);
                    inviandvisi=0;
                }

            }
        });

        edt_renewpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = edt_newpassword.getText().toString();
                if (edt_renewpassword.length() > 0 && password.length() > 0) {
                    if(charSequence.toString().equals(password)){
                        // give an error that password and confirm password not match
                        img_checkrenew_pass.setImageResource(R.drawable.correct);
                        img_checkrenew_pass .setVisibility(View.VISIBLE);
                        numcheck=1;
                    }
                    else if(!charSequence.toString().equals(password)){
                        img_checkrenew_pass.setImageResource(R.drawable.incorrect);
                        img_checkrenew_pass .setVisibility(View.VISIBLE);
                        numcheck=0;

                    }
                }
                else{
                    img_checkrenew_pass .setVisibility(View.INVISIBLE);
                    numcheck=0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hideKeyboard(ProfileActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onStart() {
        super.onStart();
        RefreshList();
//        new BRM_API().GET_DistributeDepartmentBySpinner(ProfileActivity.this, new BRM_API.DistributeDepartmentListener() {
//            @Override
//            public void onListener(Integer http_code, List<Department> DepartmentList) {
//                // info.getName()
//                if(http_code.equals(200)){
//                    //ต่อ
//                    int size =DepartmentList.size();
//                    String[] Departments = new String[size];
//                    for (int i = 0; i < DepartmentList.size(); i++) {
//                        Departments[i] = DepartmentList.get(i).getName();
//                        //Departments = DepartmentList.get(i).getName();
//
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ProfileActivity.this, R.layout.custom_spinner, Departments);
//                        spinnerArrayAdapter.setDropDownViewResource(R.layout.custom_spinner); // The drop down view
//                        spinner_department.setAdapter(spinnerArrayAdapter);
//                    }
//                    progressbar.cancel();
//                }else{
//                    //condition lost connect
//                    Intent newActivity = new Intent(ProfileActivity.this,MainActivity.class);
//                    progressbar.cancel();
//                    startActivity(newActivity);
//                    toast_text.setText("Connection failure ,Reconnect");
//                    toast = new Toast(getApplicationContext());
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.setView(layout);
//                    toast.show();
//
//                }
//
//            }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()){
            case R.id.menu_login_id:
                select = new Intent(ProfileActivity.this, LoginActivity.class);
                drawerLayout.closeDrawers();
                startActivity(select);
                break;
            case R.id.menu_home_id:
                select = new Intent(ProfileActivity.this, HomeActivity.class);
                drawerLayout.closeDrawers();
                startActivity(select);

                break;
            case R.id.menu_profile_id:
                select = new Intent(ProfileActivity.this, ProfileActivity.class);
                drawerLayout.closeDrawers();
                startActivity(select);

                break;
            case R.id.menu_detail_id:
                select = new Intent(ProfileActivity.this, DetailBookingActivity.class);
                drawerLayout.closeDrawers();
                startActivity(select);

                break;
            case R.id.menu_logout_id:

                select = new Intent(ProfileActivity.this, MainActivity.class);
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

    public void RefreshList(){
        progressbar.show();
        progressbar.setContentView(R.layout.progress_dialog);
        txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
        txt_progressbar.setText("Loading...");
        progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        new BRM_API().GET_DistributeDepartmentBySpinner(ProfileActivity.this, new BRM_API.DistributeDepartmentListener() {
            @Override
            public void onListener(Integer http_code, List<Department> DepartmentList) {
                // info.getName()
                if(http_code.equals(200)){
                    //ต่อ
                    int size =DepartmentList.size();
                    String[] Departments = new String[size];
                    for (int i = 0; i < DepartmentList.size(); i++) {
                        Departments[i] = DepartmentList.get(i).getName();
                        //Departments = DepartmentList.get(i).getName();

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ProfileActivity.this, R.layout.custom_spinner, Departments);
                        spinnerArrayAdapter.setDropDownViewResource(R.layout.custom_spinner); // The drop down view
                        spinner_department.setAdapter(spinnerArrayAdapter);
                    }
                    progressbar.cancel();
                }
                else if(http_code.equals(504)){
                    //condition lost connect
                    Intent newActivity = new Intent(ProfileActivity.this,MainActivity.class);
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
                    //condition lost connect
                    Intent newActivity = new Intent(ProfileActivity.this,MainActivity.class);
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


}