package th.co.infinitecorp.www.brm.Model;

import java.util.Date;
import java.util.List;

public class DayBooking {
    Integer Date;
    List<RoomDetail> roomDetails ;

    public DayBooking(){}
    public DayBooking(Integer date, List<RoomDetail> roomDetails) {
        Date = date;
        this.roomDetails = roomDetails;
    }

    public Integer getDate() {
        return Date;
    }

    public void setDate(Integer date) {
        Date = date;
    }

    public List<RoomDetail> getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(List<RoomDetail> roomDetails) {
        this.roomDetails = roomDetails;
    }
}
