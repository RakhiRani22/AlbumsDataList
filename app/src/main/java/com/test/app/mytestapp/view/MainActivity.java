package com.test.app.mytestapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.test.app.mytestapp.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            AlbumListFragment fragment = new AlbumListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, AlbumListFragment.TAG).commit();
        }
    }
}
