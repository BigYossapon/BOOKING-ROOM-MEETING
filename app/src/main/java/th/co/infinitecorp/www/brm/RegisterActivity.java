package th.co.infinitecorp.www.brm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import th.co.infinitecorp.www.brm.API.BRM_API;
import th.co.infinitecorp.www.brm.Model.AddUserinfo;
import th.co.infinitecorp.www.brm.Model.Department;
import th.co.infinitecorp.www.brm.Model.Remember;
import th.co.infinitecorp.www.brm.Model.Userinfo;
import Utils.Cookie;

public class RegisterActivity extends AppCompatActivity {
    CardView registerbtn;
    TextView cancelbtn_register,txt_progressbar,toast_text;
    Spinner spinner_department;
    ImageView img_checkpass;
    int numcheck,selected_num;
    String selectedItem;
    EditText register_user,register_pass,register_phone,register_email,register_name,register_repass;
    ProgressDialog progressbar;
    Toast toast;
    LayoutInflater inflater;
    View layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         inflater = getLayoutInflater();
         layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        toast_text = (TextView) layout.findViewById(R.id.toast_text);
        registerbtn = (CardView) findViewById(R.id.confirm_register);
        spinner_department = (Spinner)findViewById(R.id.spinner_department_register);
        cancelbtn_register = (TextView) findViewById(R.id.text_register_cancel);
        register_user = (EditText) findViewById(R.id.register_username);
        register_email = (EditText) findViewById(R.id.register_email);
        register_pass = (EditText) findViewById(R.id.register_password);
        register_phone = (EditText) findViewById(R.id.register_phone);
        register_name = (EditText) findViewById(R.id.register_name);

        register_repass = (EditText) findViewById(R.id.register_repassword);
        img_checkpass = (ImageView) findViewById(R.id.img_checkpass);
        progressbar = new ProgressDialog(RegisterActivity.this);
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





        cancelbtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(RegisterActivity.this,HomeActivity.class);
                startActivity(newActivity);
            }
        });

        register_repass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = register_pass.getText().toString();
                if (register_repass.length() > 0 && password.length() > 0) {
                    if(charSequence.toString().equals(password)){
                        // give an error that password and confirm password not match
                        img_checkpass.setImageResource(R.drawable.correct);
                        img_checkpass .setVisibility(View.VISIBLE);
                        numcheck=1;
                    }
                    else if(!charSequence.toString().equals(password)){
                        img_checkpass.setImageResource(R.drawable.incorrect);
                        img_checkpass .setVisibility(View.VISIBLE);
                        numcheck=0;

                    }
                }
                else{
                    img_checkpass .setVisibility(View.INVISIBLE);
                    numcheck=0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hideKeyboard(RegisterActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadspinner();
        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedItem = spinner_department.getItemAtPosition(position).toString();
                selected_num = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newActivity = new Intent(RegisterActivity.this,LoginActivity.class);
                if(numcheck==1&&!selectedItem.equals("--Select Department--")&&!register_name.equals(null)&&!register_email.equals(null)&&!register_pass.equals(null)&&!register_phone.equals(null)&&
                        !register_user.equals("")&& !register_email.equals("")&&!register_name.equals("")&&!register_pass.equals("")&& !register_phone.equals("")){
                    progressbar.show();
                    progressbar.setContentView(R.layout.progress_dialog);
                    txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
                    txt_progressbar.setText("Checking...");
                    progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    String user,email,name,pass,phone,fullname;
                    user =  register_user.getText().toString();
                    email = register_email.getText().toString();
                    name =  register_name.getText().toString();
                    pass =   register_pass.getText().toString();
                    phone =   register_phone.getText().toString();

                    AddUserinfo add_userinfo = new AddUserinfo();
                    add_userinfo.setID("");
                    add_userinfo.setDept_ID(selected_num);
                    add_userinfo.setEmail(email);
                    add_userinfo.setName(name);
                    add_userinfo.setUser_Name(user);
                    add_userinfo.setPassword(pass);
                    add_userinfo.setPhone(phone);

                    new BRM_API().POST_DistributeAddUserinfo(RegisterActivity.this,add_userinfo,new BRM_API.DistributeAddUserinfoListener() {
                        @Override
                        public void onListener( Integer http_code) {
                            if(http_code.equals(200)){
                                AddUserinfo add_userinfosave = new AddUserinfo();
                                toast_text.setText("Register Success!");
                                toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                add_userinfosave.setUser_Name(user);
                                add_userinfosave.setPassword(pass);
                                Cookie.SaveAddUserinfo(getApplication(),add_userinfosave);
                                startActivity(newActivity);
                            }
                            else if(http_code.equals(400)){
                                toast_text.setText("Register Failure ,Username already exists!");
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
                                toast_text.setText("Register Failure ,Connect lose!");
                                toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                            }
                            progressbar.cancel();
                        }
                    });
                }
                else{
                    toast_text.setText("Please enter detail to register!");
                    toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent newActivity = new Intent(this,HomeActivity.class);
        startActivity(newActivity);
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

   public void loadspinner(){
       progressbar.show();
       progressbar.setContentView(R.layout.progress_dialog);
       txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
       txt_progressbar.setText("Loading...");
       progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
       new BRM_API().GET_DistributeDepartmentBySpinner(RegisterActivity.this, new BRM_API.DistributeDepartmentListener() {

           @Override
           public void onListener( Integer http_code, List<Department> DepartmentList) {
               //info.getName();
               if(http_code.equals(200)){
                   String[] Departments = new String[DepartmentList.size()+1];
                   for (int i = 0; i < DepartmentList.size(); i++) {

                       Departments[i+1] = DepartmentList.get(i).getName();
                       //Departments = DepartmentList.get(i).getName();

                       ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(RegisterActivity.this,R.layout.custom_spinner, Departments);
                       spinnerArrayAdapter.setDropDownViewResource(R.layout.custom_spinner); // The drop down view
                       spinner_department.setAdapter(spinnerArrayAdapter);
                   }
                   Departments[0] = "--Select Department--";
                   progressbar.cancel();
               }else if(http_code.equals(504)){
                   //condition lost connect
                   Intent newActivity = new Intent(RegisterActivity.this,MainActivity.class);
                   progressbar.cancel();
                   startActivity(newActivity);
                   toast_text.setText("Internet is not Connected, Please check your internet");
                   toast = new Toast(getApplicationContext());
                   toast.setDuration(Toast.LENGTH_SHORT);
                   toast.setView(layout);
                   toast.show();
               }
               else{
                   //condition lost connect
                   Intent newActivity = new Intent(RegisterActivity.this,MainActivity.class);
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