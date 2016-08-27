package me.zhouzhuo.zzpagerindicatordemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void roundPoint(View view) {
        Intent intent = new Intent(MainActivity.this, RoundPointActivity.class);
        startActivity(intent);
    }

    public void tabWithText(View view) {
        Intent intent = new Intent(MainActivity.this, TabTextActivity.class);
        startActivity(intent);
    }

    public void tabWithIcon(View view) {
        Intent intent = new Intent(MainActivity.this, TabIconActivity.class);
        startActivity(intent);
    }

    public void tabWithIconAndText(View view) {
        Intent intent = new Intent(MainActivity.this, TabIconTextActivity.class);
        startActivity(intent);
    }
}
