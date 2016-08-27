package me.zhouzhuo.zzpagerindicatordemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo.zzpagerindicator.ZzPagerIndicator;
import me.zhouzhuo.zzpagerindicatordemo.adapter.MyPagerAdapter;

/**
 * Created by zz on 2016/8/23.
 */
public class RoundPointActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_point);

        ZzPagerIndicator indicator = (ZzPagerIndicator) findViewById(R.id.indicator);
        ZzPagerIndicator indicatorPx = (ZzPagerIndicator) findViewById(R.id.indicator_px);
        ZzPagerIndicator indicatorSaveSize = (ZzPagerIndicator) findViewById(R.id.indicator_same_size);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        //initViews
        List<ImageView> views = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(iv);
        }

        //initDatas
        List<String> beanList = new ArrayList<>();
        beanList.add("http://p2.so.qhmsg.com/bdr/_240_/t010df481c03c2770e2.jpg");
        beanList.add("http://p0.so.qhmsg.com/bdr/_240_/t01a10cf974ae39d3aa.jpg");
        beanList.add("http://p4.so.qhmsg.com/bdr/_240_/t0120236a7d521b6f34.jpg");

        //set adapter
        MyPagerAdapter adapter = new MyPagerAdapter(this, views, beanList);
        viewPager.setAdapter(adapter);

        //attach indicator to viewpager
        indicator.setViewPager(viewPager);
        indicatorPx.setViewPager(viewPager);
        indicatorSaveSize.setViewPager(viewPager);

    }
}
