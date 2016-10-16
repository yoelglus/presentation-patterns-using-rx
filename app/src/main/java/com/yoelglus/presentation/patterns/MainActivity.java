package com.yoelglus.presentation.patterns;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yoelglus.presentation.patterns.rmvp.RmvpItemListActivity;
import com.yoelglus.presentation.patterns.mvvm.MvvmItemListActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        findViewById(R.id.mvp_passive_rx_btn).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,
                RmvpItemListActivity.class)));
        findViewById(R.id.mvvm_btn).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,
                MvvmItemListActivity.class)));
    }
}
