package com.example.moviereviewv2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moviereviewv2.activities.MovieView;
import com.example.moviereviewv2.R;
import com.example.moviereviewv2.modal.Movie;

import com.bumptech.glide.Glide;
;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context mContext;
    private List<Movie> mData;


    public Adapter(Context mContext, List<Movie> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MovieView.class);
                i.putExtra("title",mData.get(viewHolder.getAdapterPosition()).getTitle());
                i.putExtra("vote_average",mData.get(viewHolder.getAdapterPosition()).getVote_average());
                i.putExtra("poster_path",mData.get(viewHolder.getAdapterPosition()).getPoster_path());
                i.putExtra("overview",mData.get(viewHolder.getAdapterPosition()).getDescription());

                mContext.startActivity(i);
            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mData.get(position).getTitle());
        holder.vote_average.setText(mData.get(position).getVote_average());

        //Glide
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500"+mData.get(position).getPoster_path())
                .into(holder.poster_path);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView vote_average;
        ImageView poster_path;
        LinearLayout view_container;

        public ViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            title = itemView.findViewById(R.id.movie_title);
            vote_average = itemView.findViewById(R.id.movie_vote);
            poster_path = itemView.findViewById(R.id.movie_poster);

        }
    }

}
