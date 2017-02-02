package com.example.daniele.hosteltest.ui;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.daniele.hosteltest.R;
import com.example.daniele.hosteltest.rest.model.Property;

public class MainActivity extends AppCompatActivity implements MainFragment.PropertyListener {

    private MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.main_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            mMainFragment = MainFragment.newInstance();
            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.main_fragment_container, mMainFragment, MainFragment.FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onItemSelected(Property property) {
        PropertyDetailFragment detailFragment = PropertyDetailFragment.newInstance();
        Bundle args = new Bundle();
        args.putString(PropertyDetailFragment.PROPERTY_ID, property.getId());
        detailFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
