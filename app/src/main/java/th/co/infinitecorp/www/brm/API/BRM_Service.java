package th.co.infinitecorp.www.brm.API;
import java.util.List;

import th.co.infinitecorp.www.brm.Model.AddTranBooking;
import th.co.infinitecorp.www.brm.Model.AddUserinfo;
import th.co.infinitecorp.www.brm.Model.Department;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BRM_Service {

    //region Distribute
    @GET("LoginAPI")
    Call<ResponseBody>getDistributeLogin(@Query("user_Name") String user_Name, @Query("Password") String Password);

    @GET("LoginAPI")
    Call<ResponseBody>getDistributeProfile(@Query("user_Name") String user_Name, @Query("Password") String Password,@Query("ID") String ID,@Query("Name") String Name,@Query("DepartmentName") String DepartmentName,@Query("PhoneNumber")String PhoneNumber,@Query("Email")String Email);

    @GET("DepartmentAPI")
    Call<List<Department>> getDistributeDepartment(@Query("ID") String ID, @Query("Username") String Name);

    @GET("TranBookingAPI")
    Call<ResponseBody>getDistributeTranBooking(@Query("Start_Date") String Start_Date, @Query("End_Date") String End_Date, @Query("Description") String Description, @Query("Status") String Status, @Query("RoomID") String RoomID);

    @POST("UserinfoAPI")
    Call<ResponseBody>postDistributeAddUserinfo(@Body AddUserinfo AddUserinfo);

    @POST("TranBookingAPI")
    Call<ResponseBody>postDistributeAddTranBooking(@Body AddTranBooking AddTranBooking);

    @GET("TranBookingAPI")
    Call<ResponseBody> getDistributeMyBooking(@Query("search") String search);

    @GET("TranBookingAPI")
    Call<ResponseBody> getDistributeRoomDetail(@Query("search") String search);


}
