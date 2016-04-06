package vn.allenw;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private String TAG = "HOMEFRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.content_layout) == null) {
            HomeFragment fragment = new HomeFragment();
//            fragment.setRetainInstance(true);
            fm.beginTransaction().add(R.id.content_layout, fragment, TAG).commit();
        }

    }
}
