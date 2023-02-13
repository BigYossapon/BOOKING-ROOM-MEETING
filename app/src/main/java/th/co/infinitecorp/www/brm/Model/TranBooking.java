package th.co.infinitecorp.www.brm.Model;

public class TranBooking {
    String Start_Date;
    String End_Date;
    String Description;
    String Status;
    String RoomID;

    public TranBooking(){
        this.Start_Date = Start_Date;
        this.End_Date = End_Date;
        this.Description = Description;
        this.Status = Status;
        this.RoomID = RoomID;
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
}
