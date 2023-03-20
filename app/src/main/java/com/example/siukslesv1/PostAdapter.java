package com.example.siukslesv1;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;

import org.w3c.dom.Text;

import java.util.List;
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    Context mContext;
    List<Post> mData;

    Button vote;


    public PostAdapter (Context mContext, List<Post> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }

    public MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_post_item,parent,false);

        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder (@NonNull MyViewHolder holder, int position){

        holder.tvTitle.setText(mData.get(position).getName());
        holder.voted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.votes.setText(String.valueOf(mData.get(position).getVoteCount()));
        Glide.with(mContext).load(mData.get(position).getURI()).into(holder.imgPost);
    }
    @Override
    public int getItemCount(){
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView imgPost;

        TextView votes;

        Button voted;

        public MyViewHolder(View itemView){
            super(itemView);

            tvTitle = itemView.findViewById(R.id.row_post_title);
            imgPost = itemView.findViewById(R.id.row_post_image);
            votes = itemView.findViewById(R.id.votes);
            voted = itemView.findViewById(R.id.VoteFor);
        }

    }

}
