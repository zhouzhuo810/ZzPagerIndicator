package me.zhouzhuo.zzpagerindicatordemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo.zzpagerindicator.ZzPagerIndicator;
import me.zhouzhuo.zzpagerindicator.adapter.ZzFragmentPagerAdapter;
import me.zhouzhuo.zzpagerindicatordemo.adapter.MyPagerAdapter;
import me.zhouzhuo.zzpagerindicatordemo.fragment.FragmentOne;
import me.zhouzhuo.zzpagerindicatordemo.fragment.FragmentThree;
import me.zhouzhuo.zzpagerindicatordemo.fragment.FragmentTwo;

/**
 * Created by zz on 2016/8/23.
 */
public class TabTextActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_with_text);

        ZzPagerIndicator indicator = (ZzPagerIndicator) findViewById(R.id.indicator);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        //initial fragments
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentOne());
        fragments.add(new FragmentTwo());
        fragments.add(new FragmentThree());
        ZzFragmentPagerAdapter adapter = new ZzFragmentPagerAdapter(getSupportFragmentManager(), fragments, new String[] {"QQ", "微博", "微信"});
        viewPager.setAdapter(adapter);

        //attach indicator to viewpager
        indicator.setViewPager(viewPager);

    }
}
