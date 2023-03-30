package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.newsapp.Models.NewsHeadLine;
import com.example.newsapp.adapter.Adapter2;
import com.example.newsapp.db.Dao;
import com.example.newsapp.db.Dao2;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<NewsHeadLine> list;
    Adapter2 adapter;
    Dao2 dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        setTitle("Faverite News ");
        dao= new Dao2(this);
        list= new ArrayList<>();
        list = dao.getAll();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        adapter= new Adapter2(this, list);
        recyclerView.setAdapter(adapter);
    }
}