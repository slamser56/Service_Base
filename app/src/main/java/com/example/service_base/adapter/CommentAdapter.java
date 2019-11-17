package com.example.service_base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service_base.R;
import com.example.service_base.Repair_item.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> comment;
    private Context ctx;

    public CommentAdapter(List<Comment> comment, Context ctx) {
        this.comment = comment;
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_adapter, parent, false);

        return new CommentAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        holder.bind(comment.get(position));
    }

    @Override
    public int getItemCount() {
        return comment.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        public void bind(final Comment comment) {


            TextCommentId.setText(String.valueOf(comment.getId_c()));
            TextCommentDate.setText(comment.getDate_c().toString());
            TextComment.setText(comment.getComment().toString());
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
