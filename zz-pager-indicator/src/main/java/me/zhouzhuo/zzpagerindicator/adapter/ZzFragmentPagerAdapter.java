package me.zhouzhuo.zzpagerindicator.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zz on 2016/8/22.
 */
public class ZzFragmentPagerAdapter extends FragmentPagerAdapter {

    protected List<Fragment> fragments;
    protected String[] titles;
    protected int[] selectedIcons;
    protected int[] unSelectedIcons;

    public ZzFragmentPagerAdapter(FragmentManager manager, List<Fragment> fragments, String[] titles) {
        super(manager);
        this.fragments = fragments;
        this.titles = titles;
    }

    public ZzFragmentPagerAdapter(FragmentManager manager, List<Fragment> fragments, String[] titles, int[] selectedIconRes, int[] unselectedIconRes) {
        super(manager);
        this.fragments = fragments;
        this.titles = titles;
        this.selectedIcons = selectedIconRes;
        this.unSelectedIcons = unselectedIconRes;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public int getSelectedIcon(int position) {
        if (selectedIcons != null && selectedIcons.length > position) {
            return selectedIcons[position];
        }
        return -1;
    }

    public int getUnselectedIcon(int position) {
        if (unSelectedIcons != null && unSelectedIcons.length > position) {
            return unSelectedIcons[position];
        }
        return -1;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments == null ? null : fragments.get(position);
    }
}
