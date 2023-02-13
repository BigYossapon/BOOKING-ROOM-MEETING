package th.co.infinitecorp.www.brm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import th.co.infinitecorp.www.brm.API.BRM_API;
import th.co.infinitecorp.www.brm.Model.AddUserinfo;
import th.co.infinitecorp.www.brm.Model.Remember;
import th.co.infinitecorp.www.brm.Model.Userinfo;
import Utils.Cookie;

public class LoginActivity extends AppCompatActivity {
    CheckBox rememberbox;
    CardView login_btn;
    TextView text_register,txt_progressbar,toast_text;
    EditText edt_username,edt_password;
    ProgressDialog progressbar;
    ImageView showhide_pass;
    Toast toast;
    LayoutInflater inflater;
    View layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        toast_text = (TextView) layout.findViewById(R.id.toast_text);
        login_btn = (CardView) findViewById(R.id.loginbtn);
        text_register = (TextView) findViewById(R.id.textregister);
        rememberbox = (CheckBox) findViewById(R.id.rememberbox);
        edt_username = (EditText) findViewById(R.id.login_username);
        edt_password = (EditText) findViewById(R.id.login_password);
        progressbar = new ProgressDialog(LoginActivity.this);
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
        showhide_pass = (ImageView) findViewById(R.id.showhide_pass);

        AddUserinfo add_userinfo = Cookie.GetAddUserinfo(getApplication());
        if( add_userinfo.getUser_Name()!=null && !add_userinfo.getUser_Name().equals("")){
            edt_username.setText(add_userinfo.getUser_Name());
            edt_password.setText( add_userinfo.getPassword());
            add_userinfo.setUser_Name("");
            add_userinfo.setPassword("");
            Cookie.SaveAddUserinfo(getApplication(),add_userinfo);
        }


        text_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(newActivity);
            }
        });

        hideKeyboard(LoginActivity.this);
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
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remember remember_get =  Cookie.GetRemember(getApplication());
                Remember remember_save = new Remember();
                Userinfo userinfosave = new Userinfo();
                String user = edt_username.getText().toString();
                String pass = edt_password.getText().toString();
                progressbar.show();
                progressbar.setContentView(R.layout.progress_dialog);
                txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
                txt_progressbar.setText("Logging in...");
                progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                if ((user==null||user.equals(""))&&(pass==null||pass.equals(""))){
                    toast_text.setText("Please enter Username and Password");
                    toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    progressbar.cancel();
                }
                else if((user!=null||!user.equals(""))&&(pass==null||pass.equals(""))){
                    toast_text.setText("Please enter Password");
                    toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    progressbar.cancel();
                }
                else if((user==null||user.equals(""))&&(pass!=null||!pass.equals(""))){
                    toast_text.setText("Please enter Username");
                    toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    progressbar.cancel();
                }
                else{
                    new BRM_API().Post_DistributeUserandPassword(LoginActivity.this, user, pass, new BRM_API.DistributeOrdersListener() {
                        @Override
                        public void onDistributeOrdersListener(Userinfo info, Integer http_code) {

                            if(http_code.equals(400)){
                                toast_text.setText("Username or Password Incorrect!");
                                toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();

                            }
                            else if(http_code.equals(200)){

                                Intent newActivity = new Intent(LoginActivity.this,HomeActivity.class);
                                boolean isChecked = rememberbox.isChecked ();
                                remember_save.setRemember_status(isChecked);
                                remember_save.setRemember_check(true);
                                Cookie.SaveRemember(getApplication(),remember_save);

                                userinfosave.setDepartmentName(info.getDepartmentName());
                                userinfosave.setEmail(info.getEmail());
                                userinfosave.setID(info.getID());
                                userinfosave.setName(info.getName());
                                userinfosave.setPassword(info.getPassword());
                                userinfosave.setUserName(info.getUserName());
                                userinfosave.setPhoneNumber(info.getPhoneNumber());
                                Cookie.SaveUserinfo(getApplication(),userinfosave);
                                startActivity(newActivity);

                            }
                            else if(http_code.equals(504)){
                                toast_text.setText("Internet is not Connected, Please check your internet");
                                toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                            }

                            else{
                                toast_text.setText("Connection failure , please try again");
                                toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();

                            }
                            progressbar.cancel();
                        }
                    });
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
            remember_save.setRemember_status(false);
            Cookie.SaveUserinfo(getApplication(),userinfo_save);
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

    public void ShowHidePass(View view){

        if(view.getId()==R.id.showhide_pass){

            if(edt_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){

                ((ImageView)(view)).setImageResource(R.drawable.showpass);
                //Show Password
                edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.hidepass);

                //Hide Password
                edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }


    }

}