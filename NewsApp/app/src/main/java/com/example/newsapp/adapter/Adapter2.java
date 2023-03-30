package com.example.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.newsapp.MainActivity;
import com.example.newsapp.Models.NewsHeadLine;
import com.example.newsapp.R;
import com.example.newsapp.db.Dao;
import com.example.newsapp.db.Dao2;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
    Context context;
    List<NewsHeadLine> list;
    Dao dao;

    public Adapter2(Context context, List<NewsHeadLine> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter2.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_head_list_adapter2,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitel.setText(list.get(position).getTitle());
        holder.txtSource.setText(list.get(position).getName());
        if(list.get(position).getUrlToImage()!= null){
            Picasso.get().load(list.get(position).getUrlToImage())
                    .into(holder.img_headline);
        }
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                dao= new Dao(context);
//                dao.Del(list.get(position).getTitle());
//                list.remove(list.get(position));
//                return false;
//            }
//        });

        //cach 2:
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context , DetailsActivity.class)
//                        .putExtra("data", list.get(holder.getAdapterPosition())));
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailsActivity.class)
                        .putExtra("data", list.get(holder.getAdapterPosition())));
            }
        });
        holder.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao= new Dao(context);
                Dao2 dao2 = new Dao2(context);
                dao.Del(list.get(holder.getAdapterPosition()).getTitle());
                dao2.Del(list.get(holder.getAdapterPosition()).getTitle());
                list.remove(list.get(holder.getAdapterPosition()));
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitel , txtSource;
        ImageView img_headline, img_del;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitel=itemView.findViewById(R.id.txt_titel1);
            txtSource=itemView.findViewById(R.id.txt_source1);
            img_headline = itemView.findViewById(R.id.img_headline1);
            img_del = itemView.findViewById(R.id.img_del);
            cardView= itemView.findViewById(R.id.cardview1);
        }
    }
}
