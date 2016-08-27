package me.zhouzhuo.zzpagerindicator.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zz on 2016/8/22.
 */
public abstract class ZzBasePagerAdapter<K extends View, T> extends PagerAdapter {

    protected Context context;
    private List<K> views;
    protected List<T> datas;

    public ZzBasePagerAdapter(final Context context, List<K> views, List<T> datas) {
        this.context = context;
        this.views = views;
        this.datas = datas;
    }

    public void setViews(List<K> views) {
        this.views = views;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public K instantiateItem(ViewGroup container, int position) {
        K view = views.get(position);
        if (datas != null) {
            bindData(view, datas.get(position));
        }
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((ViewPager) container).addView(view);
        return view;
    }

    public abstract void bindData(K view, T t);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getTabText(datas.get(position), position);
    }

    public abstract String getTabText(T t, int position);

    public abstract int getSelectedIcon(int position);

    public abstract int getUnselectedIcon(int position);


}
