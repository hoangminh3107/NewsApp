package com.example.newsapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.DetailsActivity;
import com.example.newsapp.FavActivity;
import com.example.newsapp.MainActivity;
import com.example.newsapp.Models.NewsHeadLine;
import com.example.newsapp.R;
import com.example.newsapp.SelectListener;
import com.example.newsapp.db.Dao;
import com.example.newsapp.db.Dao2;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<NewsHeadLine> list;
    SelectListener listener;
    int fav = 1;


    public Adapter(Context context, List<NewsHeadLine> list, SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_head_list, parent, false));

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitel.setText(list.get(holder.getAdapterPosition()).getTitle());
        holder.txtSource.setText(list.get(holder.getAdapterPosition()).getSource().getName());
        if (list.get(holder.getAdapterPosition()).getUrlToImage() != null) {
            Picasso.get().load(list.get(holder.getAdapterPosition()).getUrlToImage())
                    .into(holder.img_headline);

        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dao2 dao = new Dao2(context.getApplicationContext());
                dao.Insert(list.get(holder.getAdapterPosition()));
                listener.Onclicked(list.get(holder.getAdapterPosition()));
            }
        });

        //cach 2:
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context , DetailsActivity.class)
//                        .putExtra("data", list.get(holder.getAdapterPosition())));
//            }
//        });


        holder.img_fav.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fav = 2;

                if (fav == 2) {


                    fav = 1;
                    Dao dao = new Dao(context);
                    dao.Insert(list.get(holder.getAdapterPosition()));
                    holder.img_fav.setImageResource(R.drawable.ic_b);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.img_fav.setVisibility(View.GONE);
                        }
                    },1000);
                    Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });


//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Dao dao= new Dao(context);
//                dao.Del(list.get(holder.getAdapterPosition()).getTitle());
//                holder.img_fav.setImageResource(R.drawable.ic_fv);
//                return false;
//            }
//        });

//        if(fav==1){
//            holder.img_fav.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Dao dao= new Dao(context);
////                    dao.Del(list.get(holder.getAdapterPosition()).getTitle());
////                    list.remove(list.get(holder.getAdapterPosition()));
////                    notifyDataSetChanged();
//                    holder.img_fav.setImageResource(R.drawable.ic_fv);
//
//                }
//            });
//        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitel, txtSource;
        ImageView img_headline, img_fav;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitel = itemView.findViewById(R.id.txt_titel);
            txtSource = itemView.findViewById(R.id.txt_source);
            img_headline = itemView.findViewById(R.id.img_headline);
            img_fav = itemView.findViewById(R.id.img_fav);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
