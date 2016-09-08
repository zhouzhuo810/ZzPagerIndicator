<h2>ZzPagerIndicator</h2>

A powerful custom indicator for Android ViewPager.

<br>
Gradle:

```
compile 'me.zhouzhuo.zzpagerindicator:zz-pager-indicator:1.0.1'
```

Maven:

```
<dependency>
  <groupId>me.zhouzhuo.zzpagerindicator</groupId>
  <artifactId>zz-pager-indicator</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
<br>

<h3>What does it look like ?</h3>


![这里写图片描述](https://github.com/zhouzhuo810/ZzPagerIndicator/blob/master/point.gif)

![这里写图片描述](https://github.com/zhouzhuo810/ZzPagerIndicator/blob/master/icon.gif)

![这里写图片描述](https://github.com/zhouzhuo810/ZzPagerIndicator/blob/master/text.gif)

![这里写图片描述](https://github.com/zhouzhuo810/ZzPagerIndicator/blob/master/icon_text.gif)

<br>
<h3>How to use it ?</h3>

一、准备工作

1.设置adapter，继承`ZzBasePagerAdapter<K extends View, T>`

```java
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

```

二、开始使用

1.布局

```xml
    <!--dp-->
    <me.zhouzhuo.zzpagerindicator.ZzPagerIndicator
        android:id="@+id/indicator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="60dp"
        app:zz_indicator_type="round_point"
        app:zz_point_spacing="8dp"
        app:zz_select_point_color="#0f0"
        app:zz_select_point_size="12dp"
        app:zz_unselect_point_color="#f0f"
        app:zz_unselect_point_size="8dp" />
```

2.java代码

（1）ImageView

```java
        ZzPagerIndicator indicator = (ZzPagerIndicator) findViewById(R.id.indicator);
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
```

（2）Fragment

```java
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
```

三、自定义属性说明

```xml
    <declare-styleable name="ZzPagerIndicator">
        <!--指示器类型-->
        <attr name="zz_indicator_type" format="enum">
            <!--小圆点-->
            <enum name="round_point" value="0" />
            <!--文字-->
            <enum name="tab_with_text" value="1" />
            <!--图标-->
            <enum name="tab_with_icon" value="2" />
            <!--图标和文字-->
            <enum name="tab_with_icon_and_text" value="3" />
        </attr>
        <!--for tab-->
        <!--未选中文字大小-->
        <attr name="zz_unselect_tab_text_size" format="dimension|reference" />
        <!--未选中文字颜色-->
        <attr name="zz_unselect_tab_text_color" format="color|reference" />
        <!--选中文字大小-->
        <attr name="zz_select_tab_text_size" format="dimension|reference" />
        <!--选中文字颜色-->
        <attr name="zz_select_tab_text_color" format="color|reference" />
        <!--下划线颜色-->
        <attr name="zz_underline_color" format="color|reference" />
        <!--下划线高度-->
        <attr name="zz_underline_height" format="dimension|reference" />
        <!--下划线左右边距-->
        <attr name="zz_underline_padding" format="dimension|reference" />
        <!--tab左右间距-->
        <attr name="zz_tab_padding" format="dimension|reference" />
        <!--图标大小-->
        <attr name="zz_tab_icon_size" format="dimension|reference" />
        <!--是否显示下划线-->
        <attr name="zz_show_underline" format="boolean" />
        <!--是否等比例展开-->
        <attr name="zz_should_tab_expand" format="boolean" />

        <!--for point-->
        <!--选中时园点直径-->
        <attr name="zz_select_point_size" format="dimension|reference" />
        <!--未选中时圆点直径-->
        <attr name="zz_unselect_point_size" format="dimension|reference" />
        <!--选中时圆点颜色-->
        <attr name="zz_select_point_color" format="color|reference" />
        <!--未选中时圆点颜色-->
        <attr name="zz_unselect_point_color" format="color|reference" />
        <!--圆点间距-->
        <attr name="zz_point_spacing" format="dimension|reference" />
        
        <!--使用px作为单位时是否根据屏幕大小自动缩放-->
        <attr name="zz_is_need_scale_in_px" format="boolean" />

    </declare-styleable>
```







