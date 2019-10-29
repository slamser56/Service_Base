package com.example.service_base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service_base.R;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.RepairViewHolder> {


    private static int ViewHolderCount;
    private int numberItems;

    public RepairAdapter(int NumberOfItems)
    {
        numberItems = NumberOfItems;
        ViewHolderCount = 0;
    }

    @NonNull
    @Override
    public RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         Context context = parent.getContext();
         int LayoutIdForListItem = R.layout.list_item_repair;

         LayoutInflater layoutInflater = LayoutInflater.from(context);

         View view = layoutInflater.inflate(LayoutIdForListItem, parent, false);

        RepairViewHolder viewHolder = new RepairViewHolder(view);

        viewHolder.TextViewDate.setText("ViewHolder index:" + ViewHolderCount);
        ViewHolderCount++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepairViewHolder holder, int position) {
holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class RepairViewHolder extends RecyclerView.ViewHolder {

        TextView TextViewId;
        TextView TextViewDate;
        TextView TextViewStatus;

        public RepairViewHolder(@NonNull View itemView) {
            super(itemView);

            TextViewId = itemView.findViewById(R.id.text_view_id);
            TextViewDate = itemView.findViewById(R.id.text_view_date);
            TextViewStatus = itemView.findViewById(R.id.status);
        }




        void bind(int ListIndex){
            TextViewId.setText(String.valueOf(ListIndex));
        }
    }


}



