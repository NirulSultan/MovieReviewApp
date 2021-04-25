package com.example.moviereviewv2.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.moviereviewv2.R;
import com.example.moviereviewv2.Service;
import com.example.moviereviewv2.adapter.Adapter;
import com.example.moviereviewv2.fragment.HomeFragment;
import com.example.moviereviewv2.fragment.ProfileFragment;
import com.example.moviereviewv2.fragment.Settingsfragment;
import com.example.moviereviewv2.modal.Movie;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Adapter adapter;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    public static String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=9778a619ac5b3db312ff954ad76e9a31";
    List<Movie> movieList;
    RecyclerView recyclerView;

    //Navigation drawer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.MovieList);
        adapter = new Adapter(this, movieList);

        Intent serviceIntent = new Intent(this, Service.class);
        startService(serviceIntent);

        GetData getData = new GetData();
        getData.execute();

    }


    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
//             case R.id.nav_home:
//                 break;
//             case R.id.nav_logout:
//                 Intent intent = new Intent(this,MainActivity.class);
//                 startActivity(intent);
//                 stopService(new Intent(this, Service.class));
//                 break;
//             case R.id.nav_profile:
//                 Toast.makeText(this, "User profile", Toast.LENGTH_SHORT).show();
//                 break;
                
            case R.id.nav_home:
                PutDataIntoRecyclerView(movieList);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                stopService(new Intent(this, Service.class));
                break;
            case R.id.nav_setting:
                recyclerView.setAdapter(null);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Settingsfragment()).commit();
                break;
            case R.id.nav_profile:
                recyclerView.setAdapter(null);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Get DATA From API
    public class GetData extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String...strings) {

            String current= "";

            try{
                URL url;
                HttpsURLConnection urlConnection = null;

                try{
                    url = new URL(JSON_URL);
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1){
                        current += (char) data;
                        data = isr.read();

                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return current;
        }

        //Display DATA
        @Override
        protected void onPostExecute(String s) {
           try{
               JSONObject jsonObject = new JSONObject(s);
               JSONArray jsonArray = jsonObject.getJSONArray("results");

               for (int i = 0 ; i< jsonArray.length() ; i++){
                   JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                   Movie model = new Movie();
                   model.setTitle(jsonObject1.getString("title"));
                   model.setVote_average(jsonObject1.getString("vote_average"));
                   model.setPoster_path(jsonObject1.getString("poster_path"));
                   model.setDescription(jsonObject1.getString("overview"));
                   movieList.add(model);

               }

           } catch (JSONException e) {
               e.printStackTrace();
           }

           PutDataIntoRecyclerView(movieList);

        }
    }

    private void PutDataIntoRecyclerView(List<Movie> movieList){
//         Adapter adapter = new Adapter(this, movieList);
        adapter = new Adapter(this, movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}
