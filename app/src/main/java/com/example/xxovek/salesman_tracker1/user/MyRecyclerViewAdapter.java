package com.example.xxovek.salesman_tracker1.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.xxovek.salesman_tracker1.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData,mData1,mData2,mData3;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String mData4;
    private String flag="0";

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<String> data, List<String> data1, List<String> data2, List<String> data3,  String data4) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mData1 = data1;
        this.mData2 = data2;
        this.mData3 = data3;
        this.mData4 = data4;


    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mData.get(position);
        String phone = mData1.get(position);
        String baddress = mData2.get(position);
        String user_id = mData3.get(position);
        flag = mData4;



        holder.myTextView.setText(name);
        holder.myTextView1.setText(phone);
        holder.myTextView2.setText(baddress);
        holder.myTextView3.setText(user_id);
        if (flag.equals("0")){
            holder.mybutton.setVisibility(View.VISIBLE);
        }
        else if(flag.equals("1")){
            holder.mybutton.setVisibility(View.GONE);
            holder.myTextView3.setVisibility(View.GONE);

        }



    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView,myTextView1,myTextView2,myTextView3;
        ImageButton mybutton;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.name);
            myTextView1 = itemView.findViewById(R.id.phone);
            myTextView2 = itemView.findViewById(R.id.baddress);
            myTextView3 = itemView.findViewById(R.id.cwebsite);
            mybutton = itemView.findViewById(R.id.imageButton);
            myTextView.setVisibility(View.INVISIBLE);







            mybutton.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {mClickListener.onItemClick(view, getAdapterPosition());}


        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
         return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(TodaysVisitsFragment itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);

    }
}