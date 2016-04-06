package vn.allenw;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;



public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AnhDQ";
    private Button about, getContent;
    private static String url = "https://api.myjson.com/bins/44fql";
    private static final String EXTRA_MES = "extramess";
    private String stringValue;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        showLog("onCreate");
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) stringValue = savedInstanceState.getString(EXTRA_MES);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        about = (Button) view.findViewById(R.id.about);
        getContent = (Button) view.findViewById(R.id.get);
        about.setOnClickListener(this);
        getContent.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                showAbout();
                break;
            case R.id.get:
                showListUserScreen();
                break;
        }
    }

    private void showListUserScreen() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new ListUserFragment()).addToBackStack("ListUser").commit();
    }

    private void showAbout() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new AboutFragment()).addToBackStack("About").commit();
    }


    private String downloadContent(String myurl) throws IOException {
        InputStream is = null;
        int length = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
//            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convertInputStreamToString(is, length);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }


    @Override
    public void onResume() {
        super.onResume();
        showLog("onResume");
    }


    @Override
    public void onPause() {
        showLog("onPause");
        super.onPause();
    }


    public void showLog(String mes) {
        Log.d(TAG, mes);
    }


    @Override
    public void onStop() {
        showLog("onStop");
        super.onStop();
    }
}
