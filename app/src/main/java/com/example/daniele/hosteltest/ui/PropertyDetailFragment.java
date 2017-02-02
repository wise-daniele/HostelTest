package com.example.daniele.hosteltest.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniele.hosteltest.R;
import com.example.daniele.hosteltest.rest.RestClient;
import com.example.daniele.hosteltest.rest.model.Property;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by daniele on 02/02/17.
 */

public class PropertyDetailFragment extends Fragment {

    public static final String PROPERTY_ID = "property_id";

    private String mPropertyId;
    private Property mProperty;

    private CollapsingToolbarLayout mToolbar;
    private ImageView mPropertyImage;
    private TextView mAddress1;
    private TextView mAddress2;
    private TextView mCity;
    private TextView mCountry;
    private TextView mDescription;
    private TextView mDirections;


    public static PropertyDetailFragment newInstance() {
        PropertyDetailFragment fragment = new PropertyDetailFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPropertyId = getArguments().getString(PROPERTY_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_property_detail, container, false);
        mToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        mPropertyImage = (ImageView) rootView.findViewById(R.id.image_hostel);
        mAddress1 = (TextView) rootView.findViewById(R.id.text_address1);
        mAddress2 = (TextView) rootView.findViewById(R.id.text_address2);
        mCity = (TextView) rootView.findViewById(R.id.text_city);
        mCountry = (TextView) rootView.findViewById(R.id.text_country);
        mDescription = (TextView) rootView.findViewById(R.id.text_actual_description);
        mDirections = (TextView) rootView.findViewById(R.id.text_actual_directions);

        updatePropertyDetail();

        return rootView;
    }

    private void updatePropertyDetail(){
        RestClient.HostelInterface service = RestClient.getInstance().getClient().create(RestClient.HostelInterface.class);
        Call<Property> call = service.getProperty(mPropertyId);

        call.enqueue(new Callback<Property>() {
            @Override
            public void onResponse(Call<Property> call, Response<Property> response) {
                mProperty = response.body();
                inflateLayout();
            }

            @Override
            public void onFailure(Call<Property> call, Throwable t) {

            }
        });
    }

    private void inflateLayout(){
        if(!mProperty.getImages().isEmpty()){
            Property.Images myImage = mProperty.getImages().get(0);
            Picasso.with(getActivity()).load(myImage.getPrefix() + myImage.getSuffix()).into(mPropertyImage);
        }
        else{
            mPropertyImage.setImageResource(R.drawable.android_bkg);
        }
        mAddress1.setText(mProperty.getAddress1());
        if(mProperty.getAddress2() != null || mProperty.getAddress2().length() > 0){
            mAddress2.setVisibility(View.VISIBLE);
            mAddress2.setText(mProperty.getAddress2());
        }
        else{
            mAddress2.setVisibility(View.GONE);
        }
        mCity.setText(mProperty.getCity().getName());
        mCountry.setText(mProperty.getCity().getCountry());
        mDescription.setText(mProperty.getDescription());
        mDirections.setText(mProperty.getDirections());
        mToolbar.setTitle(mProperty.getName());
    }
}
