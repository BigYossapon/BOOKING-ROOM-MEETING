package th.co.infinitecorp.www.brm.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import th.co.infinitecorp.www.brm.Model.MyBooking;
import th.co.infinitecorp.www.brm.R;

public class AdapterMyList extends RecyclerView.Adapter<AdapterMyList.ItemHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<MyBooking> listMyDetails;
    Date datestart;
    Date dateend;
    Dialog mydialog;
    Context pr;


    public AdapterMyList(Context context, List<MyBooking> listMyDetails){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listMyDetails = listMyDetails;

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = layoutInflater.inflate(R.layout.my_detail_list,parent,false);
        pr = parent.getContext();
        return new AdapterMyList.ItemHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {

        mydialog = new Dialog(pr);
        mydialog.setContentView(R.layout.my_detail_select_list);
        mydialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView img_close= (ImageView) mydialog.findViewById(R.id.imgclose_my_dialog_select);

        CardView btn_cancel_booking = (CardView) mydialog.findViewById(R.id.btn_cancel_booking);
        MyBooking info = listMyDetails.get(i);
        btn_cancel_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //function
                mydialog.dismiss();
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });


        holder.img_mydetail_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    datestart=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(info.getStart_Date());
                    dateend=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(info.getEnd_Date());

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
                cal.setTime(datestart);
                Integer year_st = cal.get(Calendar.YEAR);
                Integer month_st = cal.get(Calendar.MONTH)+1;
                Integer day_st = cal.get(Calendar.DAY_OF_MONTH);
                Integer hour_st = cal.get(Calendar.HOUR_OF_DAY);
                Integer minute_st = cal.get(Calendar.MINUTE);
                cal.setTime(dateend);
                Integer year_en = cal.get(Calendar.YEAR);
                Integer month_en = cal.get(Calendar.MONTH)+1;
                Integer day_en = cal.get(Calendar.DAY_OF_MONTH);
                Integer hour_en = cal.get(Calendar.HOUR_OF_DAY);
                Integer minute_en = cal.get(Calendar.MINUTE);
                //ย้ายไปข้างนอก
                TextView txt_detail = (TextView) mydialog.findViewById(R.id.txt_my_detail_select_dialog);
                //((date.getMonth() < 10) ? "0"+month : month)+ "-" + ((date.getDay() < 10) ? "0"+days : days)
                txt_detail.setText("Room : "+info.getRoomID()+"\n\nDate : "+((day_st < 10) ? "0"+day_st : day_st)+"/"+((month_st < 10) ? "0"+month_st : month_st)+"/"+year_st+"\n\nTime : "+((hour_st < 10) ? "0"+hour_st : hour_st)+":"+((minute_st < 10) ? "0"+minute_st : minute_st)+" - "+((hour_en < 10) ? "0"+hour_en : hour_en)+":"+((minute_en < 10) ? "0"+minute_en : minute_en)+"\n\nDescription : "+info.getDescription());
                mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                mydialog.show();
            }
        });
        try {
            datestart=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(info.getStart_Date());
            dateend=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(info.getEnd_Date());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                Date st = sdf.parse(info.getStart_Date());
                Date en = sdf.parse(info.getEnd_Date());
            } catch (ParseException ex) {
                Log.v("Exception", ex.getLocalizedMessage());
            }
            String aaa = "";
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        cal.setTime(datestart);
        Integer year_st = cal.get(Calendar.YEAR);
        Integer month_st = cal.get(Calendar.MONTH)+1;
        Integer day_st = cal.get(Calendar.DAY_OF_MONTH);
        Integer hour_st = cal.get(Calendar.HOUR_OF_DAY);
        Integer minute_st = cal.get(Calendar.MINUTE);
        cal.setTime(dateend);
        Integer year_en = cal.get(Calendar.YEAR);
        Integer month_en = cal.get(Calendar.MONTH)+1;
        Integer day_en = cal.get(Calendar.DAY_OF_MONTH);
        Integer hour_en = cal.get(Calendar.HOUR_OF_DAY);
        Integer minute_en = cal.get(Calendar.MINUTE);

        holder.date.setText(convertDate(day_st)+"/"+convertDate(month_st)+"/"+year_st.toString()+"\n"+convertDate(hour_st)+":"+convertDate(minute_st)+" - "+convertDate(hour_en)+":"+convertDate(minute_en));
        holder.roomid.setText(info.getRoomID());
    }

    @Override
    public int getItemCount() {

        return listMyDetails.size();

    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView date,roomid;
        private CardView img_mydetail_booking;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            roomid = (TextView) itemView.findViewById(R.id.my_detail_room_id);
            date = (TextView) itemView.findViewById(R.id.my_detail_date_id);
            img_mydetail_booking = (CardView) itemView.findViewById(R.id.image_detail_my_booking);


        }





        }
    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }

    }

}
