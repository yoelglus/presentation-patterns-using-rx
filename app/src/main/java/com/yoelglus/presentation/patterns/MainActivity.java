package com.yoelglus.presentation.patterns;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yoelglus.presentation.patterns.mvc.MvcItemListActivity;
import com.yoelglus.presentation.patterns.mvp.MvpItemListActivity;
import com.yoelglus.presentation.patterns.mvppassive.MvpPassiveItemListActivity;
import com.yoelglus.presentation.patterns.mvppassiverx.MvpPassiveRxItemListActivity;
import com.yoelglus.presentation.patterns.mvpvm.MvpVmItemListActivity;
import com.yoelglus.presentation.patterns.mvvm.MvvmItemListActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        findViewById(R.id.mvp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MvpItemListActivity.class));
            }
        });
        findViewById(R.id.mvp_passive_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MvpPassiveItemListActivity.class));
            }
        });
        findViewById(R.id.mvp_passive_rx_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MvpPassiveRxItemListActivity.class));
            }
        });
        findViewById(R.id.mvp_vm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MvpVmItemListActivity.class));
            }
        });
        findViewById(R.id.mvvm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MvvmItemListActivity.class));
            }
        });
        findViewById(R.id.mvc_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MvcItemListActivity.class));
            }
        });
    }
}
