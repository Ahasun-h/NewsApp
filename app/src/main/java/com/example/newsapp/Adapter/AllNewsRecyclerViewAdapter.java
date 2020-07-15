package com.example.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.newsapp.Activity.NewsDetailActivity;
import com.example.newsapp.Model.AllNews.Article;
import com.example.newsapp.Model.AllNews.Source;
import com.example.newsapp.R;

import java.util.List;

public class AllNewsRecyclerViewAdapter extends RecyclerView.Adapter<AllNewsRecyclerViewAdapter.MyViewHolder> {

    private List<Article> articleList;
    private List<Source> sources;
    private Context context;

    public AllNewsRecyclerViewAdapter(Context mContext, List<Article> articles) {
        this.context = mContext;
        this.articleList = articles;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view;
        final LayoutInflater inflater = LayoutInflater.from( context );
        view = inflater.inflate( R.layout.item,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder( view );




        viewHolder.items.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ImageView imageView = view.findViewById(R.id.img);

                Intent intent= new Intent( context, NewsDetailActivity.class );
                intent.putExtra( "url",articleList.get( viewHolder.getAdapterPosition()).getUrl());
                intent.putExtra( "title",articleList.get( viewHolder.getAdapterPosition()).getTitle());
                intent.putExtra( "img",articleList.get( viewHolder.getAdapterPosition()).getUrlToImage());
                intent.putExtra( "date",articleList.get( viewHolder.getAdapterPosition()).getPublishedAt());
                intent.putExtra( "source",articleList.get( viewHolder.getAdapterPosition()).getSource().getName());
                intent.putExtra( "author",articleList.get( viewHolder.getAdapterPosition()).getAuthor());

//                Pair<ImageView, String> pair = Pair.create(imageView, ViewCompat.getTransitionName(imageView));
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(All, pair);




                context.startActivity(intent);
            }
        } );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        requestOptions.timeout(3000);

        Glide.with(context)
                .load(articleList.get( position ).getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                       holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(articleList.get( position ).getTitle());
        holder.author.setText(articleList.get( position ).getAuthor());
        holder.desc.setText(articleList.get( position ).getDescription());
        holder.publishAt.setText(articleList.get( position ).getPublishedAt());
        holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(articleList.get( position ).getPublishedAt()));


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

       TextView author,publishAt,title,desc,source,time;
       FrameLayout items;
       ImageView imageView;
       ProgressBar progressBar;


        public MyViewHolder(View itemView) {
            super( itemView );

            items = itemView.findViewById( R.id.items );

            author = itemView.findViewById( R.id.author );
            publishAt = itemView.findViewById( R.id.publishAt );
            title = itemView.findViewById( R.id.title );
            desc = itemView.findViewById( R.id.desc );
            source = itemView.findViewById( R.id.source );
            time = itemView.findViewById( R.id.time );

            imageView = itemView.findViewById( R.id.img );

            progressBar = itemView.findViewById( R.id.loadImageProgressBar );

        }
    }

    public void updateList(List<Article> articles){
        this.articleList=articles;
        notifyDataSetChanged();
    }

}
