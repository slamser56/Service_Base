package com.example.service_base.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service_base.R;
import com.example.service_base.Repair_item.Repair_item;
import com.example.service_base.order_activity;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Repair_item> repair_items;
    private Context ctx;

    public CommentAdapter(List<Repair_item> repair_items, Context ctx)
    {
        this.repair_items = repair_items;
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_adapter, parent,false);

        return new CommentAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        holder.bind(repair_items.get(position));
    }

    @Override
    public int getItemCount() {
        return repair_items.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        public void bind(final Repair_item repair_item) {


            TextCommentId.setText(String.valueOf(repair_item.getId_c()));
            TextCommentDate.setText(repair_item.getDate_c().toString());
            TextComment.setText(repair_item.getComment().toString());
        }

        TextView TextCommentId;
        TextView TextCommentDate;
        TextView TextComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            TextCommentId = itemView.findViewById(R.id.text_comment_id);
            TextCommentDate = itemView.findViewById(R.id.text_comment_date);
            TextComment = itemView.findViewById(R.id.text_comment);

        }


    }
}
