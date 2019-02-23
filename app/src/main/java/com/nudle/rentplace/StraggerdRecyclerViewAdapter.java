package com.nudle.rentplace;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class StraggerdRecyclerViewAdapter extends RecyclerView.Adapter<StraggerdRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";

    private ArrayList<String> mTimes = new ArrayList<>();
    private ArrayList<String> mDescriptons = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    public StraggerdRecyclerViewAdapter(Context context, ArrayList<String> times, ArrayList<String> descriptons, ArrayList<String> names, ArrayList<String> imageUrls) {
        mTimes = times;
        mDescriptons = descriptons;
        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .load(mImageUrls.get(position))
                .apply(requestOptions)
                .into(holder.pimage);

        holder.pname.setText(mNames.get(position));
        holder.pdescription.setText(mDescriptons.get(position));
        holder.ptime.setText(mTimes.get(position));

        holder.pimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick clicked " + mNames.get(position));
                Log.d(TAG, "onClick clicked " + mDescriptons.get(position));
                Log.d(TAG, "onClick clicked " + mTimes.get(position));
                Toasty.warning(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
                Toasty.warning(mContext, mDescriptons.get(position), Toast.LENGTH_SHORT).show();
                Toasty.warning(mContext, mTimes.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView pimage;
        TextView pname;
        TextView pdescription;
        TextView ptime;

        public ViewHolder(View itemView) {
            super(itemView);
            this.pimage = itemView.findViewById(R.id.product_image);
            this.pname = itemView.findViewById(R.id.product_name);
            this.pdescription = itemView.findViewById(R.id.product_description);
            this.ptime = itemView.findViewById(R.id.product_time);
        }
    }
}