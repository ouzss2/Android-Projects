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

public class RecycleView_Adapter extends RecyclerView.Adapter<RecycleView_Adapter.MyHolder> {

    public String[] MoviesName;
    public int[] image;
    public Context context;
    private final RecyclerViewInterface recyclerViewInterface;

    public RecycleView_Adapter(String[] moviesName, int[] image, Context context, RecyclerViewInterface recyclerViewInterface) {
        MoviesName = moviesName;
        this.image = image;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem,parent ,false);
        return new RecycleView_Adapter.MyHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.movieNm.setText(MoviesName[position]);
        holder.MovieImg.setImageResource(image[position]);
    }

    @Override
    public int getItemCount() {
        return MoviesName.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView movieNm;
        ImageView MovieImg;
        public MyHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
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
    }
}
