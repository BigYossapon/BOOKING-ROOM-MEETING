package Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import th.co.infinitecorp.www.brm.Model.AddTranBooking;
import th.co.infinitecorp.www.brm.Model.AddUserinfo;
import th.co.infinitecorp.www.brm.Model.Department;
import th.co.infinitecorp.www.brm.Model.Remember;
import th.co.infinitecorp.www.brm.Model.TranBooking;
import th.co.infinitecorp.www.brm.Model.Userinfo;


public class Cookie {

    //Depratment
    public static void SaveDepartment(Context context, Department department){
        Type type=new TypeToken<Department>(){}.getType();
        Gson gson=new Gson();
        String json=gson.toJson(department,type);

        SharedPreferences.Editor editor=context.getSharedPreferences("Department",Context.MODE_PRIVATE).edit();
        editor.putString("Department",json);
        editor.apply();

    }
    public static Department GetDepartment(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("Department",Context.MODE_PRIVATE);
        String strData = sharedPreferences.getString("Department","");
        Type type=new TypeToken<Department>(){}.getType();
        if(strData!=null)
        {
            Gson gson = new Gson();
            Department department = gson.fromJson(strData,type);
            return  department;
        }
        Department department = new Department();
        return department;
    }

    //Userinfo
    public static void SaveUserinfo(Context context, Userinfo userinfo){
        Type type=new TypeToken<Userinfo>(){}.getType();
        Gson gson=new Gson();
        String json=gson.toJson(userinfo,type);

        SharedPreferences.Editor editor=context.getSharedPreferences("Userinfo",Context.MODE_PRIVATE).edit();
        editor.putString("Userinfo",json);
        editor.apply();
    }

    public static Userinfo GetUserinfo(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("Userinfo",Context.MODE_PRIVATE);
        String strData=sharedPreferences.getString("Userinfo",null);
        Type type=new TypeToken<Userinfo>(){}.getType();
        if(strData!=null)
        {
            Gson gson = new Gson();
            Userinfo userinfo = gson.fromJson(strData,type);
            return  userinfo;
        }
        Userinfo userinfo = new Userinfo();
        return userinfo;
    }

    //TranBooking
    public static void SaveTranBooking(Context context, TranBooking tranBooking){
        Type type=new TypeToken<TranBooking>(){}.getType();
        Gson gson=new Gson();
        String json=gson.toJson(tranBooking,type);

        SharedPreferences.Editor editor=context.getSharedPreferences("Tranbooking",Context.MODE_PRIVATE).edit();
        editor.putString("Tranbooking",json);
        editor.apply();
    }
    public static TranBooking GetTranBooking(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Tranbooking", Context.MODE_PRIVATE);
        String strData = sharedPreferences.getString("Tranbooking", null);
        Type type = new TypeToken<TranBooking>() {
        }.getType();
        if (strData != null) {
            Gson gson = new Gson();
            TranBooking tranBooking = gson.fromJson(strData, type);
            return tranBooking;
        }
        TranBooking tranBooking = new TranBooking();
        return tranBooking;
    }

    //Remember
    public static void SaveRemember(Context context, Remember remember){
        Type type=new TypeToken<Remember>(){}.getType();
        Gson gson=new Gson();
        String json=gson.toJson(remember,type);

        SharedPreferences.Editor editor=context.getSharedPreferences("Remember",Context.MODE_PRIVATE).edit();
        editor.putString("Remember",json);
        editor.apply();
    }

    public static Remember GetRemember(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Remember", Context.MODE_PRIVATE);
        String strData = sharedPreferences.getString("Remember", null);
        Type type = new TypeToken<Remember>() {
        }.getType();
        if (strData != null) {
            Gson gson = new Gson();
            Remember remember = gson.fromJson(strData, type);
            return remember;
        }
        Remember remember = new Remember();
        return remember;
    }


    //AddUserinfo
    public static void SaveAddUserinfo(Context context, AddUserinfo addUserinfo){
        Type type=new TypeToken<AddUserinfo>(){}.getType();
        Gson gson=new Gson();
        String json=gson.toJson(addUserinfo,type);

        SharedPreferences.Editor editor=context.getSharedPreferences("AddUserinfo",Context.MODE_PRIVATE).edit();
        editor.putString("AddUserinfo",json);
        editor.apply();
    }
    public static AddUserinfo GetAddUserinfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AddUserinfo", Context.MODE_PRIVATE);
        String strData = sharedPreferences.getString("AddUserinfo", null);
        Type type = new TypeToken<AddUserinfo>() {
        }.getType();
        if (strData != null) {
            Gson gson = new Gson();
            AddUserinfo addUserinfo = gson.fromJson(strData, type);
            return addUserinfo;
        }
        AddUserinfo addUserinfo = new AddUserinfo();
        return addUserinfo;
    }


    //AddTranBooking
    public static void SaveAddTranBooking(Context context, AddTranBooking addTranBooking){
        Type type=new TypeToken<AddTranBooking>(){}.getType();
        Gson gson=new Gson();
        String json=gson.toJson(addTranBooking,type);

        SharedPreferences.Editor editor=context.getSharedPreferences("AddTranBooking",Context.MODE_PRIVATE).edit();
        editor.putString("AddTranBooking",json);
        editor.apply();
    }
    public static AddTranBooking GetAddTranBooking(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AddTranBooking", Context.MODE_PRIVATE);
        String strData = sharedPreferences.getString("AddTranBooking", null);
        Type type = new TypeToken<AddTranBooking>() {
        }.getType();
        if (strData != null) {
            Gson gson = new Gson();
            AddTranBooking addTranBooking = gson.fromJson(strData, type);
            return addTranBooking;
        }
        AddTranBooking addTranBooking = new AddTranBooking();
        return addTranBooking;
    }
}


