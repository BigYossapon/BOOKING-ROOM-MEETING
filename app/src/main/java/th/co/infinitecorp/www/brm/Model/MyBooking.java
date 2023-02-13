package th.co.infinitecorp.www.brm.Model;

import java.util.List;

public class MyBooking {
    public MyBooking(String start_Date, String end_Date, String description, String status, String roomID, String roomName, List<Userinfo> userinfo) {
        this.Start_Date = start_Date;
        this.End_Date = end_Date;
        this.Description = description;
        this.Status = status;
        this.RoomID = roomID;
        this.RoomName = roomName;
        this.userinfo = userinfo;
    }

    String Start_Date;
    String End_Date;
    String Description;
    String Status;
    String RoomID;
    String RoomName;

    public String getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(String start_Date) {
        Start_Date = start_Date;
    }

    public String getEnd_Date() {
        return End_Date;
    }

    public void setEnd_Date(String end_Date) {
        End_Date = end_Date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRoomID() {

        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public List<Userinfo> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(List<Userinfo> userinfo) {
        this.userinfo = userinfo;
    }

    List<Userinfo> userinfo;

}
