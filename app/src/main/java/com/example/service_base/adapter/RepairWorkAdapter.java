package com.example.service_base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.service_base.R;
import com.example.service_base.Repair_item.Repair_work;

import java.util.List;

public class RepairWorkAdapter extends RecyclerView.Adapter<RepairWorkAdapter.RepairWorkAdapterViewHolder> {

    private List<Repair_work> repair_work;
    private Context ctx;

    public RepairWorkAdapter(List<Repair_work> repair_work, Context ctx)
    {
        this.repair_work = repair_work;
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public RepairWorkAdapter.RepairWorkAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repair_work_adapter, parent,false);

        return new RepairWorkAdapter.RepairWorkAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepairWorkAdapter.RepairWorkAdapterViewHolder holder, int position) {
        holder.bind(repair_work.get(position));
    }

    @Override
    public int getItemCount() {
        return repair_work.size();
    }

    class RepairWorkAdapterViewHolder extends RecyclerView.ViewHolder {

        public void bind(final Repair_work repair_work) {


            TextRepairWorkId.setText(String.valueOf(repair_work.getId_w()));
            TextRepairWorkDate.setText(repair_work.getDate_w().toString());
            TextRepairWork.setText(repair_work.getWork_w().toString());
        }

        TextView TextRepairWorkId;
        TextView TextRepairWorkDate;
        TextView TextRepairWork;

        public RepairWorkAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            TextRepairWorkId = itemView.findViewById(R.id.text_repair_work_id);
            TextRepairWorkDate = itemView.findViewById(R.id.text_repair_work_date);
            TextRepairWork = itemView.findViewById(R.id.text_repair_work);

        }


    }
}
