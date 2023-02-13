package th.co.infinitecorp.www.brm.Model;

public class SearchTranBooking {
    String RoomID = null;
    String RecorderID = null;

    public SearchTranBooking(String roomID, String recorderID) {
        RoomID = roomID;
        RecorderID = recorderID;
    }

    public SearchTranBooking() {

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
}
