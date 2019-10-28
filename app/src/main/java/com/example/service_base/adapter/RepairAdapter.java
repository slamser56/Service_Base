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

public class RepairAdapter  {

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



