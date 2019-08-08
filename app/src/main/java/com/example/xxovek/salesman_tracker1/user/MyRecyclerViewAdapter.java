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

    public List<String> mData1,mData2,mData3,mData4,mData5,mData6,mData7,mData8,mData9,mData10;
    public LayoutInflater mInflater;
    public static ItemClickListener mClickListener;
    public String mData;
    public String flag="0";

    // data is passed into the constructor
   /* public MyRecyclerViewAdapter(Context context, List<String> data, List<String> data1, List<String> data2, List<String> data3, String data4) {
        this.mInflater = LayoutInflater.from(context);
        this.mData1 = data1;
        this.mData2 = data2;
        this.mData3 = data3;
        this.mData = data4;


    }*/

    public MyRecyclerViewAdapter(Context context,List<String> mData1, List<String> mData2, List<String> mData3, List<String> mData4, List<String> mData5, List<String> mData6, List<String> mData7, List<String> mData8, List<String> mData9, List<String> mData10, String mdata) {
        this.mInflater = LayoutInflater.from(context);
        this.mData1 = mData1;
        this.mData2 = mData2;
        this.mData3 = mData3;
        this.mData4 = mData4;
        this.mData5 = mData5;
        this.mData6 = mData6;
        this.mData7 = mData7;
        this.mData8 = mData8;
        this.mData9 = mData9;
        this.mData10 = mData10;
        this.mData = mdata;

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
        String t1 = mData1.get(position);
        String t2 = mData2.get(position);
        String t3 = mData3.get(position);
        String t4 = mData4.get(position);
        String t5 = mData5.get(position);
        String t6 = mData6.get(position);
        String t7 = mData7.get(position);
        String t8 = mData8.get(position);
        String t9 = mData9.get(position);
        String t10 = mData10.get(position);
        flag = mData;



        holder.myTextView1.setText(t1);
        holder.myTextView2.setText(t2);
        holder.myTextView3.setText(t3);
        holder.myTextView4.setText(t4);
        holder.myTextView5.setText(t5);
        holder.myTextView6.setText(t6);
        holder.myTextView7.setText(t7);
        holder.myTextView8.setText(t8);
        holder.myTextView9.setText(t9);
        holder.myTextView10.setText(t10);

        if (flag.equals("0")){
            //holder.mybutton.setVisibility(View.VISIBLE);
        }
        else if(flag.equals("1")){
           // holder.mybutton.setVisibility(View.GONE);
          //  holder.myTextView3.setVisibility(View.GONE);

        }else if(flag.equals("2")) {
            holder.myTextView1.setVisibility(View.GONE);
            holder.myTextView4.setVisibility(View.GONE);
            holder.myTextView5.setVisibility(View.GONE);
            holder.myTextView6.setVisibility(View.GONE);
            holder.myTextView7.setVisibility(View.GONE);
            holder.myTextView8.setVisibility(View.GONE);
            holder.myTextView9.setVisibility(View.GONE);
            holder.myTextView10.setVisibility(View.GONE);

        }else if(flag.equals("3")) {
           
            holder.myTextView1.setVisibility(View.GONE);
            holder.myTextView5.setVisibility(View.GONE);
            holder.myTextView6.setVisibility(View.GONE);
            holder.myTextView7.setVisibility(View.GONE);
            holder.myTextView8.setVisibility(View.GONE);
            holder.myTextView9.setVisibility(View.GONE);
            holder.myTextView10.setVisibility(View.GONE);

        }
        else if(flag.equals("4")) {

            holder.myTextView1.setVisibility(View.GONE);
            holder.myTextView7.setVisibility(View.GONE);
            holder.myTextView8.setVisibility(View.GONE);
            holder.myTextView9.setVisibility(View.GONE);
            holder.myTextView10.setVisibility(View.GONE);

        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData1.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView1,myTextView2,myTextView3,myTextView4,myTextView5,myTextView6,myTextView7,myTextView8,myTextView9,myTextView10;
        public ImageButton ib_delete,ib_edit;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView1 = itemView.findViewById(R.id.t1);
            myTextView2 = itemView.findViewById(R.id.t2);
            myTextView3 = itemView.findViewById(R.id.t3);
            myTextView4 = itemView.findViewById(R.id.t4);
            myTextView5 = itemView.findViewById(R.id.t5);
            myTextView6 = itemView.findViewById(R.id.t6);
            myTextView7 = itemView.findViewById(R.id.t7);
            myTextView8 = itemView.findViewById(R.id.t8);
            myTextView9 = itemView.findViewById(R.id.t9);
            myTextView10 = itemView.findViewById(R.id.t10);
            ib_delete = itemView.findViewById(R.id.ib_delete);
            ib_edit = itemView.findViewById(R.id.ib_edit);
            myTextView10 = itemView.findViewById(R.id.t10);
            //mybutton = itemView.findViewById(R.id.imageButton);
            myTextView1.setVisibility(View.INVISIBLE);


            itemView.setOnClickListener(this);
           // mybutton.setOnClickListener(this);
            myTextView2.setOnClickListener(this);
            myTextView3.setOnClickListener(this);
            myTextView4.setOnClickListener(this);
            ib_delete.setOnClickListener(this);
            ib_edit.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {mClickListener.onItemClick(view, getAdapterPosition());}


        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
         return mData1.get(id);
    }

    public String getItem2(int id) {
        return mData2.get(id);
    }

    public String getItem3(int id) {
        return mData3.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);

    }
}