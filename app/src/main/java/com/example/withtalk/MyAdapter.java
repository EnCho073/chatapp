package com.example.withtalk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Chat> mDataset;
    String stMyEmail="";

        public static class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView textView;
            public MyViewHolder(View v){
                super(v);
                textView=v.findViewById(R.id.tvChat);
            }
        }
    //채팅 누가보냈는지
    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if(mDataset.get(position).email.equals(stMyEmail)){
            return 1;
        }else{
            return 2;
        }
    }

    public MyAdapter(ArrayList<Chat> myDataset, String stEmail, String stUser){
            mDataset =myDataset;
            this.stMyEmail=stEmail;
        }
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view,parent,false);
            //나의 채팅=1
            if(viewType==1){
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_my_text_view,parent,false);
            }

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder,int position){
            holder.textView.setText(mDataset.get(position).getText());
        }
        @Override
        public int getItemCount(){return mDataset.size();}
}
