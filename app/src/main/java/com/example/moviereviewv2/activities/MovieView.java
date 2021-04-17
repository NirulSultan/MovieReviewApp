package com.example.moviereviewv2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviereviewv2.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class MovieView extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);

        //Getting Values
        String title = getIntent().getExtras().getString("title");
        String vote_average = getIntent().getExtras().getString("vote_average");
        String poster_path = getIntent().getExtras().getString("poster_path");
        String overview = getIntent().getExtras().getString("overview");

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView movie_title = findViewById(R.id.aa_movie_title);
        TextView movie_rating = findViewById(R.id.aa_movie_vote);
        TextView movie_description = findViewById(R.id.aa_description);
        ImageView movie_poster = findViewById(R.id.aa_movieposter);

        //Setting Values
        movie_title.setText(title);
        movie_rating.setText(vote_average);
        movie_description.setText(overview);
        collapsingToolbarLayout.setTitle(title);


        //LOAD AND SET IMG
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+poster_path)
                .apply(requestOptions)
                .into(movie_poster);

    }
}