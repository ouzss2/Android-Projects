package com.tunibest.RecyclV;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tunibest.Class.MoviesImageLoader;
import com.tunibest.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    public String[] MoviesName;
    public int[] image;
    public Context context;
    private final RecyclerViewInterface recyclerViewInterface;


    public RecycleAdapter(String[] moviesName, int[] image, Context context,RecyclerViewInterface recyclerViewInterface) {
        MoviesName = moviesName;
        this.image = image;
        this.context = context;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem,parent ,false);
        return new RecycleAdapter.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {
     holder.movieNm.setText(MoviesName[position]);
     holder.MovieImg.setImageResource(image[position]);
    }

    @Override
    public int getItemCount() {
        return MoviesName.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movieNm;
        ImageView MovieImg;
        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            movieNm = itemView.findViewById(R.id.textid);
            MovieImg = itemView.findViewById(R.id.myimg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos !=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemSelect(pos);
                        }
                    }

                        // view.
                }
            });
        }

        public TextView getMovieNm() {
            return movieNm;
        }

        public ImageView getMovieImg() {
            return MovieImg;
        }
    }
}
