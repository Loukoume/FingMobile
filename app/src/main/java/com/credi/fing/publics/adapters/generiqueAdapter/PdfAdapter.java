package com.credi.fing.publics.adapters.generiqueAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.credi.fing.R;
import com.credi.fing.publics.ZoomImage;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {
    private List<String> paths;
    Context context;

    public PdfAdapter(List<String> paths, Context context) {
       this.paths=paths;
       this.context=context;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image, parent, false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        String path= paths.get(position);
        if (path != null) {
            Glide.with(context)
                    .load(path)

                    .into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, ZoomImage.class)
                            .putExtra("url", path));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return paths == null ? 0 : paths.size();
    }

    public static class PdfViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}

