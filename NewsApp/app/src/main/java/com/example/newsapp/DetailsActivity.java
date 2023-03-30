package com.example.newsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.Models.NewsHeadLine;
import com.example.newsapp.db.Dao;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

public class DetailsActivity extends AppCompatActivity {
    NewsHeadLine newsHeadLine;
    TextView txttitel, txtauthor, txttime, txtdetail, txtconten;
    ImageView imgdetail, imgshare, imgfava, imgfava1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        txttitel = findViewById(R.id.txt_titel_detail);
        txtauthor = findViewById(R.id.txt_author);
        txttime = findViewById(R.id.txt_time);
        txtdetail = findViewById(R.id.txt_detail);
        txtconten = findViewById(R.id.txt_content);
        imgdetail = findViewById(R.id.img_detail);
        imgshare = findViewById(R.id.icshare);
        imgfava = findViewById(R.id.fava);
        imgfava1 = findViewById(R.id.fava2);

        setTitle("Details News ");
        newsHeadLine = (NewsHeadLine) getIntent().getSerializableExtra("data");

        txttitel.setText(newsHeadLine.getTitle());
        //txttitel.setText(newsHeadLine.getTitle());
        txtauthor.setText(newsHeadLine.getAuthor());
        txttime.setText(newsHeadLine.getPublishedAt());
        txtdetail.setText(newsHeadLine.getDescription());
        txtconten.setText(newsHeadLine.getContent());
        Picasso.get().load(newsHeadLine.getUrlToImage()).into(imgdetail);

        imgfava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgfava1.setVisibility(View.VISIBLE);
                imgfava.setVisibility(View.GONE);
                Dao dao = new Dao(getApplicationContext());
                dao.Check();
                dao.Insert(newsHeadLine);
                Toast.makeText(DetailsActivity.this, "Added to favorite", Toast.LENGTH_SHORT).show();



            }
        });
        imgshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ShareContent();


            }
        });


    }

    private void ShareContent() {
        Bitmap bitmap = getBitMap(imgdetail);
        try {
            File file = new File(this.getExternalCacheDir(), "image.png");
            FileOutputStream fou = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fou);
            fou.flush();
            fou.close();
            file.setReadable(true, true);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            //intent.putExtra(Intent.EXTRA_TEXT, txtdetail.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, txttitel.getText().toString());
            intent.putExtra(Intent.EXTRA_SUBJECT, "World News ");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/*");
            startActivity(Intent.createChooser(intent, "Share to : "));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitMap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Drawable drawable = v.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        } else {
            canvas.drawColor(Color.CYAN);
        }
        v.draw(canvas);
        return bitmap;

    }
}