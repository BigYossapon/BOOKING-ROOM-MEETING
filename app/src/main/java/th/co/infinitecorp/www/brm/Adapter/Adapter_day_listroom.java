package th.co.infinitecorp.www.brm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import th.co.infinitecorp.www.brm.Model.datetime;
import th.co.infinitecorp.www.brm.R;

public class Adapter_day_listroom extends RecyclerView.Adapter<Adapter_day_listroom.ItemHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<datetime> listday;


    public Adapter_day_listroom(Context context, List<datetime> listday){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listday = listday;

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = layoutInflater.inflate(R.layout.room_booking_day_list,parent,false);

        return new Adapter_day_listroom.ItemHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {

        datetime info = listday.get(i);
        String day = String.valueOf(info.getDay());
        holder.days.setText(day);
//        holder.name.setText(info.getName());
//        holder.date.setText(info.getStartDate().substring(12,info.getStartDate().length())+" - "+info.getEndDate().substring(12,info.getEndDate().length()));
//        holder.department.setText(info.getDepartment());
    }

    @Override
    public int getItemCount() {

        return listday.size();

    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView days;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);

                days = (TextView) itemView.findViewById(R.id.text_day_room_list);
//            name = (TextView) itemView.findViewById(R.id.txt_name);
//            department = (TextView) itemView.findViewById(R.id.txt_department);
//            date = (TextView) itemView.findViewById(R.id.txt_time);

        }



    }

}
