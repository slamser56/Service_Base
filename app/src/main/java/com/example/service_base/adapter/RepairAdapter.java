package com.example.service_base.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service_base.R;
import com.example.service_base.Repair_item.Repair_item;
import com.example.service_base.order_activity;

import java.util.List;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.RepairViewHolder> {


    private List<Repair_item> repair_items;
    private Context ctx;

    public RepairAdapter(List<Repair_item> repair_items, Context ctx)
    {
        this.repair_items = repair_items;
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repair, parent,false);

        return new RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepairViewHolder holder, int position) {
        holder.bind(repair_items.get(position));


    }

    @Override
    public int getItemCount() {
        return repair_items.size();
    }

    class RepairViewHolder extends RecyclerView.ViewHolder {

        public void bind(final Repair_item repair_item) {


            TextViewId.setText(String.valueOf(repair_item.getId()));
            TextViewDate.setText(repair_item.getDate().toString());
            TextViewStatus.setText(repair_item.getStatus().toString());
            Frmlayout.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    String title = "ID"+repair_item.getId();
                    Toast.makeText(ctx,title,Toast.LENGTH_SHORT).show();
                    // on click action here
                    //-- use context to start the new Activity
                    Intent mediaStreamIntent = new Intent(ctx, order_activity.class);
                    ctx.startActivity(mediaStreamIntent);
                }
            });

        }

        TextView TextViewId;
        TextView TextViewDate;
        TextView TextViewStatus;
        FrameLayout Frmlayout;

        public RepairViewHolder(@NonNull View itemView) {
            super(itemView);

            TextViewId = itemView.findViewById(R.id.text_view_id);
            TextViewDate = itemView.findViewById(R.id.text_view_date);
            TextViewStatus = itemView.findViewById(R.id.text_view_status);
            Frmlayout = itemView.findViewById(R.id.lnrlayout);




        }


    }


}



