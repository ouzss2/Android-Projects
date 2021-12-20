package com.tunibest.RecyclV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tunibest.R;

public class Rec_Arcane extends RecyclerView.Adapter<Rec_Arcane.MyViewHolder>{

    public String[] MoviesName;
    public String[] MoviesNumber;
    public int[] image;
    public Context context;
    private final RecyclerViewInterface recyclerViewInterface;

    public Rec_Arcane(String[] moviesName, String[] moviesNumber, int[] image, Context context,RecyclerViewInterface recyclerViewInterface) {
        MoviesName = moviesName;
        MoviesNumber = moviesNumber;
        this.image = image;
        this.context = context;
        this.recyclerViewInterface=recyclerViewInterface;
    }


    @NonNull
    @Override
    public Rec_Arcane.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_element,parent ,false);
        return new Rec_Arcane.MyViewHolder(view,recyclerViewInterface);
       
    }

    @Override
    public void onBindViewHolder(@NonNull Rec_Arcane.MyViewHolder holder, int position) {
        holder.movieNm.setText(MoviesName[position]);
        holder.MovieImg.setImageResource(image[position]);
        holder.epnbr.setText(MoviesNumber[position]);
    }

    @Override
    public int getItemCount() {
        return MoviesName.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView movieNm;
        TextView epnbr;
        ImageView MovieImg;
        public MyViewHolder(View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            movieNm = (TextView) view.findViewById(R.id.mytext);
            MovieImg = (ImageView) view.findViewById(R.id.myimg);
            epnbr = (TextView) view.findViewById(R.id.mynbr);
            view.setOnClickListener(new View.OnClickListener() {
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

        public void setMovieNm(TextView movieNm) {
            this.movieNm = movieNm;
        }

        public TextView getEpnbr() {
            return epnbr;
        }

        public void setEpnbr(TextView epnbr) {
            this.epnbr = epnbr;
        }

        public ImageView getMovieImg() {
            return MovieImg;
        }

        public void setMovieImg(ImageView movieImg) {
            MovieImg = movieImg;
        }
    }
}
