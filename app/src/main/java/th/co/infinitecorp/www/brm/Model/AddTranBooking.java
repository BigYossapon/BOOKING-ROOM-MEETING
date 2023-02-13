package th.co.infinitecorp.www.brm.Model;

public class AddTranBooking {

    String Start_Date;
    String End_Date;
    String Description;
    String RecorderID;
    String RoomID;


    public AddTranBooking(String start_Date, String end_Date, String description, String recorderID, String roomID) {
        Start_Date = start_Date;
        End_Date = end_Date;
        Description = description;
        RecorderID = recorderID;
        RoomID = roomID;
    }

    public AddTranBooking() {

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

    public String getRecorderID() {
        return RecorderID;
    }

    public void setRecorderID(String recorderID) {
        RecorderID = recorderID;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }
}
