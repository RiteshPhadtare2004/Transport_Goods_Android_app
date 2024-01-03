package com.example.transportgoods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder> {

    private List<UserHelperClass> mList = new ArrayList<>();
    private Context mContext;
    public MyRecycleAdapter(List<UserHelperClass> list, Context context){
        mList = list;
        mContext = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_layout,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.Pick_Up_Point_of_Good.setText("Pick Up Point of Good : "+mList.get(position).getPick_Up_Point_of_Good());
        holder.Drop_Point_of_Good.setText("Drop Point of Good : "+mList.get(position).getDrop_Point_of_Good());
        holder.good_address.setText("Pickup Good Address : "+mList.get(position).getGood_address());
        holder.good_address_drop.setText("Drop Good Address : "+mList.get(position).getGood_address_drop());
        holder.vehical_Good_can_be_travelled.setText("Vehical Required to transport : "+mList.get(position).getVehical_Good_can_be_travelled());
        holder.name_of_good.setText("Name of Good : "+mList.get(position).getName_of_good());
        holder.phone_no.setText("Contact to this Phone number : "+mList.get(position).getPhone_no()+"");

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Pick_Up_Point_of_Good,Drop_Point_of_Good,vehical_Good_can_be_travelled,good_address,good_address_drop,name_of_good,phone_no;
        public MyViewHolder(View itemView) {
            super(itemView);
            Pick_Up_Point_of_Good = itemView.findViewById(R.id.Pick_Up_Point_of_Good);
            Drop_Point_of_Good = itemView.findViewById(R.id.Drop_Point_of_Good);
            vehical_Good_can_be_travelled = itemView.findViewById(R.id.vehical_Good_can_be_travelled);
            good_address = itemView.findViewById(R.id.good_address);
            good_address_drop = itemView.findViewById(R.id.good_address_drop);
            name_of_good = itemView.findViewById(R.id.name_of_good);
            phone_no = itemView.findViewById(R.id.phone_no);
        }
    }
}
