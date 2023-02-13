package th.co.infinitecorp.www.brm.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import Utils.GDATA;
import th.co.infinitecorp.www.brm.Model.AllBooking;
import th.co.infinitecorp.www.brm.Model.DayBooking;
import th.co.infinitecorp.www.brm.Model.RoomDetail;
import th.co.infinitecorp.www.brm.Model.TimeOnDay;
import th.co.infinitecorp.www.brm.R;

public class Adapter_hour_listroom extends RecyclerView.Adapter<Adapter_hour_listroom.ItemHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<DayBooking> listbooking;
    Dialog mydialog;
    Context pr;
    TextView Details;
    int pos_en,pos_st;
    public Adapter_hour_listroom(Context context, List<DayBooking> listbooking){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listbooking = listbooking;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = layoutInflater.inflate(R.layout.room_booking_hour_list,parent,false);
        pr = parent.getContext();
        return new Adapter_hour_listroom.ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {

        //detail_user_booking = new Dialog(context);
        DayBooking info = listbooking.get(i);
       // detail_user_booking .setContentView(R.layout.room_booking_hour_select_list);

        mydialog = new Dialog(pr);
        mydialog.setContentView(R.layout.room_booking_hour_select_list);
        mydialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView img_close= (ImageView) mydialog.findViewById(R.id.imgclose_dialog_select);
        Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        for (RoomDetail detail : info.getRoomDetails()){

            Date datestart = new Date();
            Date dateend = new Date();
            try {
                datestart = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(detail.getStart_Date());
                dateend = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(detail.getEnd_Date());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
            cal.setTime(datestart);
            Integer year_st = cal.get(Calendar.YEAR);
            Integer month_st = cal.get(Calendar.MONTH)+1;
            Integer day_st = cal.get(Calendar.DAY_OF_MONTH);
            String hour_st = cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + ((Integer)cal.get(Calendar.HOUR_OF_DAY)).toString() : ((Integer)cal.get(Calendar.HOUR_OF_DAY)).toString();
            String minute_st = cal.get(Calendar.MINUTE) < 10 ? "0" + ((Integer)cal.get(Calendar.MINUTE)).toString() : ((Integer)cal.get(Calendar.MINUTE)).toString();
            cal.setTime(dateend);
            Integer year_en = cal.get(Calendar.YEAR);
            Integer month_en = cal.get(Calendar.MONTH)+1;
            Integer day_en = cal.get(Calendar.DAY_OF_MONTH);
            String hour_en = cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + ((Integer)cal.get(Calendar.HOUR_OF_DAY)).toString() : ((Integer)cal.get(Calendar.HOUR_OF_DAY)).toString();
            String minute_en = cal.get(Calendar.MINUTE) < 10 ? "0" + ((Integer)cal.get(Calendar.MINUTE)).toString() : ((Integer)cal.get(Calendar.MINUTE)).toString();
            String TimeNaja_St = hour_st + ":" + minute_st;
            String TimeNaja_En = hour_en + ":" + minute_en;
            String arr[] = new String[20];
            arr[0] = "08:00";
            arr[1] = "08:30";
            arr[2] = "09:00";
            arr[3] = "09:30";
            arr[4] = "10:00";
            arr[5] = "10:30";
            arr[6] = "11:00";
            arr[7] = "11:30";
            arr[8] = "12:00";
            arr[9] = "12:30";
            arr[10] = "13:00";
            arr[11] = "13:30";
            arr[12] = "14:00";
            arr[13] = "14:30";
            arr[14] = "15:00";
            arr[15] = "15:30";
            arr[16] = "16:00";
            arr[17] = "16:30";
            arr[18] = "17:00";
            arr[19] = "17:30";
            //test 11-12

            if(info.getDate() == day_st)
            {
                for(int check=0;check<20;check++){
                    if(TimeNaja_St.equals(arr[check])){
                        pos_st = check;
                    }
                    if(TimeNaja_En.equals(arr[check])){
                        pos_en = check;
                    }
                }
//                Random rnd = new Random();
//                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                int[] androidColors = pr.getResources().getIntArray(R.array.androidcolors);
                int color = androidColors[new Random().nextInt(androidColors.length)];
               for(int timer =pos_st;timer<=pos_en;timer++){
                   switch (arr[timer]){
                       case "08:00":{
                           holder.time_8_00.setBackgroundColor(color);
                           holder.time_8_00.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                               }
                           });
                           break;
                       }

                       case "08:30" : {
                           holder.time_8_30.setBackgroundColor(color);
                           holder.time_8_30.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                               }
                           });
                           break;
                       }

                       case "09:00" : {
                           holder.time_9_00.setBackgroundColor(color);
                           holder.time_9_00.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                               }
                           });
                           break;
                       } case "09:30" : {
                           holder.time_9_30.setBackgroundColor(color);
                           holder.time_9_30.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                               }
                           });
                           break;
                       }case "10:00" : {
                           holder.time_10_00.setBackgroundColor(color);
                           holder.time_10_00.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                               }
                           });
                           break;
                       }case "10:30" : {
                           holder.time_10_30.setBackgroundColor(color);
                           holder.time_10_30.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                               }
                           });
                           break;
                       }case "11:00" : {
                           holder.time_11_00.setBackgroundColor(color);
                           holder.time_11_00.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                               }
                           });
                           break;
                       }
                       case "11:30" : {
                           holder.time_11_30.setBackgroundColor(color);
                           holder.time_11_30.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                     }
                           });
                           break;
                       }case "12:00" : {
                           holder.time_12_00.setBackgroundColor(color);
                           holder.time_12_00.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                  }
                           });
                           break;
                       }case "12:30" : {
                           holder.time_12_30.setBackgroundColor(color);
                           holder.time_12_30.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                   }
                           });
                           break;
                       }case "13:00" : {
                           holder.time_13_00.setBackgroundColor(color);
                           holder.time_13_00.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                    }
                           });
                           break;
                       }case "13:30" : {
                           holder.time_13_30.setBackgroundColor(color);
                           holder.time_13_30.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                      }
                           });
                           break;
                       }case "14:00" : {
                           holder.time_14_00.setBackgroundColor(color);
                           holder.time_14_00.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                    }
                           });
                           break;
                       }case "14:30" : {
                           holder.time_14_30.setBackgroundColor(color);
                           holder.time_14_30.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                    }
                           });
                           break;
                       }case "15:00" : {
                           holder.time_15_00.setBackgroundColor(color);
                           holder.time_15_00.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                               }
                           });
                           break;
                       }case "15:30" : {
                           holder.time_15_30.setBackgroundColor(color);
                           holder.time_15_30.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                    }
                           });
                           break;
                       }case "16:00" : {
                           holder.time_16_00.setBackgroundColor(color);
                           holder.time_16_00.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                               }
                           });
                           break;
                       }case "16:30" : {
                           holder.time_16_30.setBackgroundColor(color);
                           holder.time_16_30.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                      }
                           });
                           break;
                       }case "17:00" : {
                           holder.time_17_00.setBackgroundColor(color);
                           holder.time_17_00.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                     }
                           });
                           break;
                       }case "17:30" : {
                           holder.time_17_30.setBackgroundColor(color);
                           holder.time_17_30.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   TextView Details = (TextView) mydialog.findViewById(R.id.txt_detail_select_dialog);
                                   Details.setText("Room : "+detail.getRoomID()+"\nDate : "+day_st+"/"+month_st+"/"+year_st+"\nTime : "+hour_st+":"+minute_st+" - "+hour_en+":"+minute_en+"\nDescription : "+detail.getDescription()+"\nName Booking : "+detail.getUserInfo().getName()+"\nDepartment : "
                                           +detail.getUserInfo().getDepartmentName()+"\nEmail : "+detail.getUserInfo().getEmail()+"\nPhone : "+detail.getUserInfo().getPhoneNumber());
                                   mydialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_scale;
                                   mydialog.show();
                                    }
                           });
                           break;
                       }
                   }
               }


            }


        }

    }

    @Override
    public int getItemCount() {
        return listbooking.size();

    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView time_8_00,time_8_30,time_9_00,time_9_30,time_10_00,time_10_30,time_11_00,time_11_30,time_12_00,time_12_30,time_13_00,time_13_30,time_14_00,time_14_30,time_15_00,time_15_30,time_16_00,time_16_30,time_17_00,time_17_30 ;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
           // Details = (TextView) detail_user_booking.findViewById(R.id.txt_detail_select_dialog);

            time_8_00 = (TextView) itemView.findViewById(R.id.time_text_8_00);
            time_8_30 = (TextView) itemView.findViewById(R.id.time_text_8_30);
            time_9_00 = (TextView) itemView.findViewById(R.id.time_text_9_00);
            time_9_30 = (TextView) itemView.findViewById(R.id.time_text_9_30);
            time_10_00 = (TextView) itemView.findViewById(R.id.time_text_10_00);
            time_10_30 = (TextView) itemView.findViewById(R.id.time_text_10_30);
            time_11_00 = (TextView) itemView.findViewById(R.id.time_text_11_00);
            time_11_30 = (TextView) itemView.findViewById(R.id.time_text_11_30);
            time_12_00 = (TextView) itemView.findViewById(R.id.time_text_12_00);
            time_12_30 = (TextView) itemView.findViewById(R.id.time_text_12_30);
            time_13_00 = (TextView) itemView.findViewById(R.id.time_text_13_00);
            time_13_30 = (TextView) itemView.findViewById(R.id.time_text_13_30);
            time_14_00 = (TextView) itemView.findViewById(R.id.time_text_14_00);
            time_14_30 = (TextView) itemView.findViewById(R.id.time_text_14_30);
            time_15_00 = (TextView) itemView.findViewById(R.id.time_text_15_00);
            time_15_30 = (TextView) itemView.findViewById(R.id.time_text_15_30);
            time_16_00 = (TextView) itemView.findViewById(R.id.time_text_16_00);
            time_16_30 = (TextView) itemView.findViewById(R.id.time_text_16_30);
            time_17_00 = (TextView) itemView.findViewById(R.id.time_text_17_00);
            time_17_30 = (TextView) itemView.findViewById(R.id.time_text_17_30);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

        }


    }


}
