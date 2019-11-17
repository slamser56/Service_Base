package com.example.service_base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.service_base.R;
import com.example.service_base.Repair_item.Parts;
import java.util.List;

public class PartsAdapter extends RecyclerView.Adapter<PartsAdapter.PartsAdapterViewHolder> {

    private List<Parts> parts;
    private Context ctx;
    boolean[] checked;

    public PartsAdapter(List<Parts> parts, Context ctx) {
        this.parts = parts;
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public PartsAdapter.PartsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parts_adapter, parent, false);

        return new PartsAdapter.PartsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartsAdapter.PartsAdapterViewHolder holder, final int position) {
        holder.bind(parts.get(position));
        holder.checkBox.setChecked(checked[position]);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[position] = !checked[position];
            }
        });
    }

    @Override
    public int getItemCount() {
        checked = new boolean[parts.size()];
        return parts.size();
    }

    public boolean[] getChecked() {
        return checked;
    }

    class PartsAdapterViewHolder extends RecyclerView.ViewHolder {

        public void bind(final Parts parts) {


            TextRepairWorkId.setText(String.valueOf(parts.getId_p()));
            TextRepairWorkDate.setText(parts.getDate_p().toString());
            TextRepairWork.setText(parts.getName_p().toString());
        }

        TextView TextRepairWorkId;
        TextView TextRepairWorkDate;
        TextView TextRepairWork;
        CheckBox checkBox;

        public PartsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.parts_checkbox);
            TextRepairWorkId = itemView.findViewById(R.id.text_parts_id);
            TextRepairWorkDate = itemView.findViewById(R.id.text__parts_date);
            TextRepairWork = itemView.findViewById(R.id.text_parts);

        }


    }
}
