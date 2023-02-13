package th.co.infinitecorp.www.brm.API;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import Utils.Network;
import th.co.infinitecorp.www.brm.Model.AddTranBooking;
import th.co.infinitecorp.www.brm.Model.AddUserinfo;
import th.co.infinitecorp.www.brm.Model.Department;
import th.co.infinitecorp.www.brm.Model.MyBooking;
import th.co.infinitecorp.www.brm.Model.RoomDetail;
import th.co.infinitecorp.www.brm.Model.SearchTranBooking;
import th.co.infinitecorp.www.brm.Model.TranBooking;
import th.co.infinitecorp.www.brm.Model.Userinfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BRM_API {
    // region user and pass
    //ต้องแก้ไขส่วน servec
    public interface DistributeOrdersListener {
        void onDistributeOrdersListener(final Userinfo info, Integer http_code);
    }
    DistributeOrdersListener distributeOrdersListener;
    public void Post_DistributeUserandPassword(final Context context, String user_Name,String Password, final DistributeOrdersListener listener) {
        this.distributeOrdersListener = listener;
        try {
            RestManager restManager = new RestManager();
            Call<ResponseBody> call;

            call = restManager.BRMServiceRoom().getDistributeLogin(user_Name, Password);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        try {
                            String rs = response.body().string();
                            Userinfo infos = new Gson().fromJson(rs, new TypeToken<Userinfo>() {
                            }.getType());

                            listener.onDistributeOrdersListener(infos, response.code());
                        } catch (Exception ex) {
                            Log.d("API_ERROR", "" + ex.getMessage());
                            listener.onDistributeOrdersListener(null, 0);
                        }
                    } else {
                        listener.onDistributeOrdersListener(null, response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(Network.isNetworkConnected(context)){
                        listener.onDistributeOrdersListener(null, 504);
                    }else{
                        listener.onDistributeOrdersListener(null, 0);
                    }
                }
            });
        } catch (Exception ex) {
            Log.d("API_ERROR", "" + ex.getMessage());
            listener.onDistributeOrdersListener(null, 0);
        }
    }//end region


    //region GetMyprofile
    public interface DistributeProfileListener {
        void onListener(final Userinfo info, Integer http_code);
    }

    DistributeProfileListener distributeProfileListener;

    public void POST_DistributeProfile(final Context context, final DistributeProfileListener listener) {
        this.distributeProfileListener = listener;
        try {
            RestManager restManager = new RestManager();
            Call<ResponseBody> call;

                call = restManager.BRMServiceRoom().getDistributeProfile("","","","","","","");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 200) {
                            Userinfo info = new Gson().fromJson(response.body().toString(), new TypeToken<Userinfo>() {
                            }.getType());
                            listener.onListener(info, response.code());
                        } else {
                            listener.onListener(null, 0);
                        }
                    } catch (Exception ex) {
                        Log.d("API_ERROR", "" + ex.getMessage());
                        listener.onListener(null, 0);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    listener.onListener(null, 0);
                }
            });
        } catch (Exception ex) {
            Log.d("API_ERROR", "" + ex.getMessage());
            listener.onListener(null, 0);
        }
    }
    //endregion

    //region GetDepartment
    public interface DistributeDepartmentListener {
        void onListener( Integer http_code, List<Department> departments);
    }

    DistributeDepartmentListener distributeDepartmentListener;

    public void GET_DistributeDepartmentBySpinner(final Context context,final DistributeDepartmentListener listener) {
        this.distributeDepartmentListener = listener;
        try {
            RestManager restManager = new RestManager();
            Call<List<Department>> call;

            call = restManager.BRMServiceRoom().getDistributeDepartment("","");

            call.enqueue(new Callback<List<Department>>() {
                @Override
                public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {

                    try {
                        if (response.code() == 200) {
                            List<Department> DepartmentList =  response.body();
                            listener.onListener( response.code(),DepartmentList);
                        } else {
                            List<Department> DepartmentList =  response.body();
                            listener.onListener( 0,DepartmentList);
                        }
                    } catch (Exception ex) {
                        Log.d("API_ERROR", "" + ex.getMessage());
                        List<Department> DepartmentList =  response.body();
                        listener.onListener( 0,DepartmentList);
                    }
                }
                @Override
                public void onFailure(Call<List<Department>> call, Throwable t) {
                    if(Network.isNetworkConnected(context)){
                        listener.onListener(504, null);
                    }else{
                        listener.onListener(0, null);
                    }
                }
            });
        } catch (Exception ex) {
            Log.d("API_ERROR", "" + ex.getMessage());
            listener.onListener( 0,null);
        }
    }
    //endregion


    //region GetTranBooking
    public interface DistributeTranBookingListener {
        void onListener(final TranBooking info, Integer http_code);
    }

    DistributeTranBookingListener distributeTranBookingListener;

    public void POST_DistributeTranBooking(final Context context, final DistributeTranBookingListener listener) {
        this.distributeTranBookingListener = listener;
        try {
            RestManager restManager = new RestManager();
            Call<ResponseBody> call;

            call = restManager.BRMServiceRoom().getDistributeTranBooking("","","","","");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 200) {
                            TranBooking info = new Gson().fromJson(response.body().string(), new TypeToken<TranBooking>() {
                            }.getType());
                            listener.onListener(info, response.code());
                        } else {
                            listener.onListener(null, 0);
                        }
                    } catch (Exception ex) {
                        Log.d("API_ERROR", "" + ex.getMessage());
                        listener.onListener(null, 0);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    listener.onListener(null, 0);
                }
            });
        } catch (Exception ex) {
            Log.d("API_ERROR", "" + ex.getMessage());
            listener.onListener(null, 0);
        }
    }
    //


    //region PostAddTranBooking
    public interface DistributeAddTranBookingListener {
        void onListener( Integer http_code);
    }

    DistributeAddTranBookingListener distributeAddTranBookingListener;

    public void POST_DistributeAddTranBooking(final Context context,AddTranBooking addTranBooking, final DistributeAddTranBookingListener listener) {
        this.distributeAddTranBookingListener = listener;
        try {
            RestManager restManager = new RestManager();
            Call<ResponseBody> call;

            call = restManager.BRMServiceRoom().postDistributeAddTranBooking(addTranBooking);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 200) {
                            listener.onListener( response.code());
                        } else {
                            listener.onListener( 0);
                        }
                    } catch (Exception ex) {
                        Log.d("API_ERROR", "" + ex.getMessage());
                        listener.onListener( 0);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(Network.isNetworkConnected(context)){
                        listener.onListener( 504);
                    }else{
                        listener.onListener( 0);
                    }
                }
            });
        } catch (Exception ex) {
            Log.d("API_ERROR", "" + ex.getMessage());
            listener.onListener( 0);
        }
    }
    //endregion


    //region PostAddUserinfo
    public interface DistributeAddUserinfoListener {
        void onListener( Integer http_code);
    }

    DistributeAddUserinfoListener distributeAddUserinfoListener;

    public void POST_DistributeAddUserinfo(final Context context,AddUserinfo addUserinfo, final DistributeAddUserinfoListener listener) {
        this.distributeAddUserinfoListener = listener;
        try {
            RestManager restManager = new RestManager();
            Call<ResponseBody> call;

            call = restManager.BRMServiceRoom().postDistributeAddUserinfo(addUserinfo);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 200) {

                            listener.onListener( response.code());
                        } else {
                            listener.onListener( 0);
                        }
                    } catch (Exception ex) {
                        Log.d("API_ERROR", "" + ex.getMessage());
                        listener.onListener( 0);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(Network.isNetworkConnected(context)){
                        listener.onListener(504);
                    }else{
                        listener.onListener(0);
                    }
                }
            });
        } catch (Exception ex) {
            Log.d("API_ERROR", "" + ex.getMessage());
            listener.onListener( 0);
        }
    }
    //endregion

    //region GetMybooking
    public interface DistributeMyBookingListener {
        void onListener(final List<MyBooking> info, Integer http_code);
    }

    DistributeMyBookingListener distributeMyBookingListener;

    public void GET_DistributeMyBooking(final Context context,String id, final DistributeMyBookingListener listener) {
        this.distributeMyBookingListener = listener;
        try {
            RestManager restManager = new RestManager();
            Call<ResponseBody> call;
            SearchTranBooking searchTranBooking = new SearchTranBooking();
            searchTranBooking.setRecorderID(id);
            Type type=new TypeToken<SearchTranBooking>(){}.getType();

            String recorderid =new Gson().toJson(searchTranBooking,type);

            call = restManager.BRMServiceRoom().getDistributeMyBooking(recorderid);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 200) {
                            List<MyBooking> info = new Gson().fromJson(response.body().string(), new TypeToken< List<MyBooking>>() {
                            }.getType());
                            listener.onListener((List<MyBooking>) info, response.code());
                        } else {
                            listener.onListener( null,0);
                        }
                    } catch (Exception ex) {
                        Log.d("API_ERROR", "" + ex.getMessage());
                        listener.onListener( null,0);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(Network.isNetworkConnected(context)){
                        listener.onListener(null, 504);
                    }else{
                        listener.onListener(null, 0);
                    }
                }
            });
        } catch (Exception ex) {
            Log.d("API_ERROR", "" + ex.getMessage());
            listener.onListener(null, 0);
        }
    }
    //endregion

    //region GetRoomDetail
    public interface DistributeRoomDetailListener {
        void onListener(final List<RoomDetail> info, Integer http_code);
    }

    DistributeRoomDetailListener distributeRoomDetailListener;

    public void GET_DistributeRoomDetail(final Context context,String num_room, final DistributeRoomDetailListener listener) {
        this.distributeRoomDetailListener = listener;
        try {
            RestManager restManager = new RestManager();
            Call<ResponseBody> call;

            SearchTranBooking searchTranBooking = new SearchTranBooking();
            searchTranBooking.setRoomID(num_room);
            Type type=new TypeToken<SearchTranBooking>(){}.getType();
            String roomid = new Gson().toJson(searchTranBooking,type);

            call = restManager.BRMServiceRoom().getDistributeRoomDetail(roomid);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 200) {
                            List<RoomDetail> info = new Gson().fromJson(response.body().string(), new TypeToken<List<RoomDetail>>() {
                            }.getType());
                            listener.onListener(info, response.code());
                        } else {
                            listener.onListener( null,0);
                        }
                    } catch (Exception ex) {
                        Log.d("API_ERROR", "" + ex.getMessage());
                        listener.onListener( null,0);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(Network.isNetworkConnected(context)){
                        listener.onListener(null, 504);
                    }else{
                        listener.onListener(null, 0);
                    }
                }
            });
        } catch (Exception ex) {
            Log.d("API_ERROR", "" + ex.getMessage());
            listener.onListener(null, 0);
        }
    }
    //endregion


    //region PostUpdateUserinfo
    public interface DistributeUpdateUserinfoListener {
        void onListener( Integer http_code);
    }

    DistributeUpdateUserinfoListener distributeUpdateUserinfoListener;

    public void POST_DistributeUpdateUserinfo(final Context context,AddUserinfo addUserinfo, final DistributeUpdateUserinfoListener listener) {
        this.distributeUpdateUserinfoListener = listener;
        try {
            RestManager restManager = new RestManager();
            Call<ResponseBody> call;

            call = restManager.BRMServiceRoom().postDistributeAddUserinfo(addUserinfo);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 200) {

                            listener.onListener( response.code());
                        } else {
                            listener.onListener( 0);
                        }
                    } catch (Exception ex) {
                        Log.d("API_ERROR", "" + ex.getMessage());
                        listener.onListener( 0);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(Network.isNetworkConnected(context)){
                        listener.onListener( 504);
                    }else{
                        listener.onListener( 0);
                    }
                }
            });
        } catch (Exception ex) {
            Log.d("API_ERROR", "" + ex.getMessage());
            listener.onListener( 0);
        }
    }
    //endregion

}

