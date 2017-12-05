package com.netcircle.imageloader.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.netcircle.imageloader.app.Constants;
import com.netcircle.imageloader.R;
import com.netcircle.imageloader.model.ListImageItem;
import com.netcircle.imageloader.util.VolleySingleton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by sweetgirl on 2017/11/2
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader mImageLoader;
    private Bitmap mBitmap;

    private ArrayList<ListImageItem> imageItems=new ArrayList<>();

    private Context mContext;

    public MyRecyclerViewAdapter(Context context){
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance(context);
        mImageLoader = volleySingleton.getImageLoader();
    }

    public void setTextImages(ArrayList<ListImageItem> imageItems){

        this.imageItems=imageItems;
        notifyItemMoved(0,imageItems.size());
    }

    /**
     * onCreateViewHolder()
     * * @param
     *
     * */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){

        View itemView=layoutInflater.inflate(R.layout.item, parent,false);
        ViewHolder viewHolder=new ViewHolder(itemView);
        return viewHolder;
    }

    /**
     * onBindViewHolder()
     * @param
     *
     */
    @Override
    public void  onBindViewHolder(ViewHolder holder, int position){

        ListImageItem currentTextImage=imageItems.get(position);
        holder.itemView.setTag(position);

        Picasso.with(mContext)
                .load(imageItems.get(position).getImage_url_01())
                .placeholder(R.drawable.im_01)
                .into(holder.mImage01);

    }

    /**
     *  download image
     * @param urlThumbnail
     * @param imageView
     */
    private void loadImages(String urlThumbnail, final ImageView imageView) {
        if (!urlThumbnail.equals(Constants.NA)) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    mBitmap = response.getBitmap();
                    imageView.setImageBitmap(mBitmap);
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    /**
     * getItemCount()
     * @param
     */
    @Override
    public int  getItemCount(){
        return imageItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImage01;

        public ViewHolder(View itemView){
            super(itemView);
            mImage01 = (ImageView)itemView.findViewById(R.id.im_item_01);


           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v,(int)v.getTag());
                    }
                }
            });
        }

    }

    public interface OnItemClickListener{
         void OnItemClick(View view,int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
