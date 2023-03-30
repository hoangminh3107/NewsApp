package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.Models.NewsHeadLine;
import com.example.newsapp.adapter.Adapter;
import com.example.newsapp.adapter.Adapter2;
import com.example.newsapp.db.Dao;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity {
   RecyclerView recyclerView;
    List<NewsHeadLine> list;
    Adapter2 adapter;
    Dao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        recyclerView = findViewById(R.id.recyclerviewfav);
        recyclerView.setHasFixedSize(true);
        setTitle("Favorite News ");
        dao= new Dao(this);
        list= new ArrayList<>();
        list = dao.getAll();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        adapter= new Adapter2(this, list);
        recyclerView.setAdapter(adapter);






    }
}