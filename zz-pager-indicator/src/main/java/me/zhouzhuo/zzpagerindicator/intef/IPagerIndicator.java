package me.zhouzhuo.zzpagerindicator.intef;

import android.support.v4.view.ViewPager;

/**
 * Created by zz on 2016/8/22.
 */
public interface IPagerIndicator {

    void setViewPager(ViewPager viewPager);

    void setCurrentItem(int position, boolean animate);

}
