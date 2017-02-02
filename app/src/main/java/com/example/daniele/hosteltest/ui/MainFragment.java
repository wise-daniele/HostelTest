package com.example.daniele.hosteltest.ui;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daniele.hosteltest.R;
import com.example.daniele.hosteltest.rest.RestClient;
import com.example.daniele.hosteltest.rest.model.PropertiesResponse;
import com.example.daniele.hosteltest.rest.model.Property;
import com.example.daniele.hosteltest.ui.adapter.PropertyAdapter;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by daniele on 30/01/17.
 */

public class MainFragment extends Fragment {

    public static final String LOG_TAG = MainFragment.class.getSimpleName();
    public static final String FRAGMENT_TAG = "fragment_main";

    private RecyclerView mPropertiesView;
    private List<Property> mPropertiesList;
    private PropertyAdapter mPropertyAdapter;
    private PropertyListener mListener;
    private TextView mTextNoConnection;

    private PropertyAdapter.OnItemClickListener mItemListener;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPropertiesList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTextNoConnection = (TextView) rootView.findViewById(R.id.text_no_connection);
        mPropertiesView = (RecyclerView) rootView.findViewById(R.id.property_list);
        mPropertiesView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mItemListener = new PropertyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Property item) {
                mListener.onItemSelected(item);
            }
        };

        mPropertyAdapter = new PropertyAdapter(getActivity(), mPropertiesList, mItemListener);
        mPropertiesView.setAdapter(mPropertyAdapter);

        if(!isOnline()){
            mTextNoConnection.setVisibility(View.VISIBLE);
        }
        else{
            mTextNoConnection.setVisibility(View.GONE);
            updateProperties();
        }
        return rootView;
    }

    private void updateProperties(){
        RestClient.HostelInterface service = RestClient.getInstance().getClient().create(RestClient.HostelInterface.class);
        Call<PropertiesResponse> call = service.getPropertiesByCity(RestClient.API_ID_GOTHENBURG);

        call.enqueue(new Callback<PropertiesResponse>() {
            @Override
            public void onResponse(Call<PropertiesResponse> call, Response<PropertiesResponse> response) {
                mPropertiesList = response.body().getProperties();
                if(mPropertiesList.isEmpty()){
                    Log.d(LOG_TAG, "Empty");
                }
                else{
                    Log.d(LOG_TAG, "First Property " + mPropertiesList.get(0).getName());
                }
                mPropertyAdapter.setPropertyList(mPropertiesList);
                mPropertyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PropertiesResponse> call, Throwable t) {

            }
        });
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        else {
            return false;
        }
    }

    public interface PropertyListener {
        /**
         * FragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Property property);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (PropertyListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PropertyListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
