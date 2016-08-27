package me.zhouzhuo.zzpagerindicatordemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import me.zhouzhuo.zzpagerindicator.adapter.ZzBasePagerAdapter;
import me.zhouzhuo.zzpagerindicatordemo.R;

/**
 * Created by zz on 2016/8/22.
 */
public class MyPagerAdapter extends ZzBasePagerAdapter<ImageView, String> {

    private int[] selectedIcons = {
            R.drawable.logo_pro_logo_qq, R.drawable.logo_pro_logo_sina, R.drawable.logo_pro_logo_weixin
    };
    private int[] unSelectedIcons = {
            R.drawable.logo_pro_logo_qq_dis, R.drawable.logo_pro_logo_sina_dis, R.drawable.logo_pro_logo_weixin_dis
    };

    public MyPagerAdapter(Context context, List<ImageView> views, List<String> datas) {
        super(context, views, datas);
    }

    @Override
    public void bindData(ImageView view, final String bean) {
        Glide.with(context).load(bean).into(view);
        //set click listener here(optional)
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, bean, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public String getTabText(String s, int position) {
        switch (position) {
            case 0:
                return "QQ";
            case 1:
                return "WeiXin";
            case 2:
                return "Sina";
            default:
                return "";
        }
    }

    @Override
    public int getSelectedIcon(int position) {
        return selectedIcons[position];
    }

    @Override
    public int getUnselectedIcon(int position) {
        return unSelectedIcons[position];
    }


}
