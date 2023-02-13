package th.co.infinitecorp.www.brm.Model;

public class RoomDetail {
    String Start_Date;
    String End_Date;
    String Description;
    String Status;
    String RoomID;
    String RecorderID;
    Userinfo UserInfo;

    public RoomDetail() {
    }

    public RoomDetail(String start_Date, String end_Date, String description, String status, String roomID, String recorderID, Userinfo userInfo) {
        Start_Date = start_Date;
        End_Date = end_Date;
        Description = description;
        Status = status;
        RoomID = roomID;
        RecorderID = recorderID;
        UserInfo = userInfo;
    }

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

    public String getRecorderID() {
        return RecorderID;
    }

    public void setRecorderID(String recorderID) {
        RecorderID = recorderID;
    }

    public Userinfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(Userinfo userInfo) {
        UserInfo = userInfo;
    }
}
