package th.co.infinitecorp.www.brm.Model;

public class ListMyDetail {
    String start_date;
    String end_date;
    String description;
    String status;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String roomid;
    String id;

    public ListMyDetail(String start_date, String end_date, String description, String status, String roomid, String id) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.status = status;
        this.roomid = roomid;
        this.id = id;
    }

}
