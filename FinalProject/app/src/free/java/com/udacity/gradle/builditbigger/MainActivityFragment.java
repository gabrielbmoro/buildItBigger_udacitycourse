package com.udacity.gradle.builditbigger;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.showjokeandroidlibrary.ShowJokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);


        AdView mAdView = (AdView) root.findViewById(R.id.adView);

        Button btnTellJoke = root.findViewById(R.id.btnTellJoke);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        btnTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String joke = new ProvidesJokes().getJoke();

                EndpointsAsyncTask task = new EndpointsAsyncTask();
                task.setContract(new EndpointsAsyncTask.CallbackAsyncTask() {
                    @Override
                    public void onPostExecute(String result) {
                        ShowJokeActivity.startActivity(getActivity(), result);
                    }
                });
                task.execute(new Pair<Context, String>(getActivity(), joke));
            }
        });

        return root;
    }
}



