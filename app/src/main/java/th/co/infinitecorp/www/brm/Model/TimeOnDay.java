package th.co.infinitecorp.www.brm.Model;

import android.content.Intent;

public class TimeOnDay {
    String Tims;
    Double ID;

    public TimeOnDay(String tims, Double ID) {
        Tims = tims;
        this.ID = ID;
    }

    public String getTims() {
        return Tims;
    }

    public void setTims(String tims) {
        Tims = tims;
    }

    public Double getID() {
        return ID;
    }

    public void setID(Double ID) {
        this.ID = ID;
    }
}
