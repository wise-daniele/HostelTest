package com.example.daniele.hosteltest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniele.hosteltest.R;
import com.example.daniele.hosteltest.rest.model.Property;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by daniele on 30/01/17.
 */

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>  {

    private final OnItemClickListener mListener;

    protected class PropertyViewHolder extends RecyclerView.ViewHolder {

        View mView;
        ImageView thumbnailImage;
        TextView propertyType;
        TextView propertyName;
        TextView lowestPrice;
        TextView rating;

        public PropertyViewHolder(View view){
            super(view);
            mView = view;
            thumbnailImage = (ImageView) view.findViewById(R.id.thumbnail_image);
            propertyType = (TextView) view.findViewById(R.id.property_type);
            propertyName = (TextView) view.findViewById(R.id.property_name);
            lowestPrice = (TextView) view.findViewById(R.id.lowest_price);
            rating = (TextView) view.findViewById(R.id.property_rating);
        }
    }

    private Context mContext;
    private List<Property> mList;

    public PropertyAdapter(Context context, List<Property> list, OnItemClickListener listener) {
        mContext = context;
        mList = list;
        mListener = listener;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.view_property_item, parent, false);
        return new PropertyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder holder, int position) {
        final Property property = mList.get(position);

        if(!property.getImages().isEmpty()){

            Property.Images myImage = property.getImages().get(0);
            Picasso.with(mContext).load(myImage.getPrefix() + myImage.getSuffix()).into(holder.thumbnailImage);
        }
        else{
            holder.thumbnailImage.setImageResource(R.drawable.android_bkg);
        }
        holder.propertyType.setText(property.getType());
        holder.propertyName.setText(property.getName());
        holder.lowestPrice.setText("N/A");
        holder.rating.setText(Integer.toString(property.getOverallRating().getOverall()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(property);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setPropertyList(List<Property> list){
        mList = list;
    }

    public interface OnItemClickListener {
        void onItemClick(Property item);
    }
}
