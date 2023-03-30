package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newsapp.Models.NewsApiRespone;
import com.example.newsapp.Models.NewsHeadLine;
import com.example.newsapp.Models.Photo;
import com.example.newsapp.adapter.Adapter;
import com.example.newsapp.adapter.ImageSliderAdapter;
import com.example.newsapp.db.Dao;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    Adapter adapter;
    ProgressDialog dialog;
    Button b1, b2, b3, b4, b5, b6;
    SearchView searchView;
    Button button;
    String tt;
    ImageView imgF, imgS, imgC;
    ViewPager2 viewPager2;
    CircleIndicator3 circleIndicator3;
    List<Photo> list;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == list.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.btn1);
        b2 = findViewById(R.id.btn2);
        b3 = findViewById(R.id.btn3);
        b4 = findViewById(R.id.btn4);
        b5 = findViewById(R.id.btn5);
        b6 = findViewById(R.id.btn6);
        imgF = findViewById(R.id.imgprofile);
        imgS = findViewById(R.id.ichome);
        imgC = findViewById(R.id.icclose);
        searchView = findViewById(R.id.search_view);
        viewPager2 = findViewById(R.id.viewpager2);
        circleIndicator3 = findViewById(R.id.index);


        list = photoList();
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(list, getApplicationContext());
        viewPager2.setAdapter(imageSliderAdapter);
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });

        imgC.setVisibility(View.GONE);
        searchView.setVisibility(View.GONE);

        imgS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.VISIBLE);
                imgS.setVisibility(View.GONE);
                imgC.setVisibility(View.VISIBLE);
            }
        });
        imgC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.GONE);
                imgC.setVisibility(View.GONE);
                imgS.setVisibility(View.VISIBLE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Searching " + query + "....");
                dialog.show();
                RequestManeger maneger = new RequestManeger(getApplicationContext());
                maneger.getNewsHeadLines(listener, tt, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RequestManeger maneger = new RequestManeger(getApplicationContext());
                maneger.getNewsHeadLines(listener, tt, null);
                return false;
            }
        });
        imgF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);



        RequestManeger maneger = new RequestManeger(this);
        maneger.getNewsHeadLines(listener, "general", null);


        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading.....");
        dialog.show();
    }

    private final OnPetchDataListener listener = new OnPetchDataListener() {
        @Override
        public void OnPetchData(List<NewsHeadLine> list, String mess) {
            if (list.isEmpty()) {
                Toast.makeText(MainActivity.this, "No Data Found.", Toast.LENGTH_SHORT).show();
            } else
                showNews(list);
            dialog.dismiss();
        }

        @Override
        public void Error(String mess) {
            Toast.makeText(MainActivity.this, "Error loading !", Toast.LENGTH_SHORT).show();
        }
    };


    private void showNews(List<NewsHeadLine> list) {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new Adapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void Onclicked(NewsHeadLine newsHeadLine) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("data", newsHeadLine);
        Dao dao = new Dao(getApplicationContext());

        if (dao.Check()) {
            intent.setAction("show");
        }


        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        button = (Button) v;
        tt = button.getText().toString();
        dialog.setTitle("Loading " + tt + "....");
        dialog.show();
        RequestManeger maneger = new RequestManeger(this);
        maneger.getNewsHeadLines(listener, tt, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fvA:
                startActivity(new Intent(MainActivity.this, FavActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Photo> photoList() {
        List<Photo> list = new ArrayList<>();

        list.add(new Photo(R.drawable.img2));
        list.add(new Photo(R.drawable.img6));
        list.add(new Photo(R.drawable.img3));
        list.add(new Photo(R.drawable.img4));
        list.add(new Photo(R.drawable.img5));

        return list;
    }
}