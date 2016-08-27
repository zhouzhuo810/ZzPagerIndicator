package me.zhouzhuo.zzpagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.zhouzhuo.zzpagerindicator.adapter.ZzBasePagerAdapter;
import me.zhouzhuo.zzpagerindicator.en.IndicatorType;
import me.zhouzhuo.zzpagerindicator.intef.IPagerIndicator;
import me.zhouzhuo.zzpagerindicator.widget.Utils;

/**
 * Created by zz on 2016/8/22.
 */
public class ZzPagerIndicator extends HorizontalScrollView implements IPagerIndicator {

    private IndicatorType indicatorType = IndicatorType.RoundPoint;

    private ViewPager mViewPager;

    private LinearLayout mIndicatorContainer;
    private boolean shouldExpand = false;
    private Paint selectPaint;
    private Paint unSelectPaint;
    private Paint underlinePaint;
    private int colorSelectPoint = 0xff00ff00;
    private int colorUnSelectPoint = 0xffff00ff;
    private int selectPointSize = 100;
    private int unSelectPointSize = 90;
    private int spacing = 8;

    private boolean showUnderline = true;
    private int tabTextColorSelect = 0xffff00ff;
    private int tabTextColorUnSelect = 0xff00ff00;
    private int tabTextSizeUnSelect = 30;
    private int tabTextSizeSelect = 40;
    private int underlineHeight = 10;
    private int underlinePadding = 0;
    private int underlineColor = 0xffe96d5b;

    private int tabPadding = 24;
    private int zz_tab_icon_size = 80;

    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private int tabCount;
    private int lastScrollX = 0;
    private boolean isNeedScaleInPx = false;


    public ZzPagerIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public ZzPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public ZzPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {

        setFillViewport(true);
        setWillNotDraw(false);

        //init attrs
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZzPagerIndicator);
            isNeedScaleInPx = a.getBoolean(R.styleable.ZzPagerIndicator_zz_is_need_scale_in_px, false);

            shouldExpand = a.getBoolean(R.styleable.ZzPagerIndicator_zz_should_tab_expand, false);
            int indicatorInt = a.getInt(R.styleable.ZzPagerIndicator_zz_indicator_type, 0);
            colorSelectPoint = a.getColor(R.styleable.ZzPagerIndicator_zz_select_point_color, 0xff00ff00);
            colorUnSelectPoint = a.getColor(R.styleable.ZzPagerIndicator_zz_unselect_point_color, 0xffff00ff);
            selectPointSize = a.getDimensionPixelSize(R.styleable.ZzPagerIndicator_zz_select_point_size, 100);
            unSelectPointSize = a.getDimensionPixelSize(R.styleable.ZzPagerIndicator_zz_unselect_point_size, 90);
            spacing = a.getDimensionPixelSize(R.styleable.ZzPagerIndicator_zz_point_spacing, 8);

            tabTextColorSelect = a.getColor(R.styleable.ZzPagerIndicator_zz_select_tab_text_color, 0xffff00ff);
            tabTextColorUnSelect = a.getColor(R.styleable.ZzPagerIndicator_zz_unselect_tab_text_color, 0xff00ff00);
            tabTextSizeSelect = a.getDimensionPixelSize(R.styleable.ZzPagerIndicator_zz_select_tab_text_size, 40);
            tabTextSizeUnSelect = a.getDimensionPixelSize(R.styleable.ZzPagerIndicator_zz_unselect_tab_text_size, 30);
            showUnderline = a.getBoolean(R.styleable.ZzPagerIndicator_zz_show_underline, true);
            underlineHeight = a.getDimensionPixelSize(R.styleable.ZzPagerIndicator_zz_underline_height, 10);
            underlinePadding = a.getDimensionPixelSize(R.styleable.ZzPagerIndicator_zz_underline_padding, 20);
            tabPadding = a.getDimensionPixelSize(R.styleable.ZzPagerIndicator_zz_tab_padding, 24);
            zz_tab_icon_size = a.getDimensionPixelSize(R.styleable.ZzPagerIndicator_zz_tab_icon_size, 80);
            underlineColor = a.getColor(R.styleable.ZzPagerIndicator_zz_underline_color, 0xffe96d5b);

            if (isNeedScaleInPx) {
                selectPointSize = Utils.scaleValue(context, selectPointSize);
                unSelectPointSize = Utils.scaleValue(context, unSelectPointSize);
                spacing = Utils.scaleValue(context, spacing);
                tabTextSizeSelect = Utils.scaleValue(context, tabTextSizeSelect);
                tabTextSizeUnSelect = Utils.scaleValue(context, tabTextSizeUnSelect);
                underlineHeight = Utils.scaleValue(context, underlineHeight);
                underlinePadding = Utils.scaleValue(context, underlinePadding);
                tabPadding = Utils.scaleValue(context, tabPadding);
                zz_tab_icon_size = Utils.scaleValue(context, zz_tab_icon_size);
            }

            switch (indicatorInt) {
                case 0:
                    indicatorType = IndicatorType.RoundPoint;
                    break;
                case 1:
                    indicatorType = IndicatorType.TabWithText;
                    break;
                case 2:
                    indicatorType = IndicatorType.TabWithIcon;
                    break;
                case 3:
                    indicatorType = IndicatorType.TabWithIconAndText;
                    break;
            }
            a.recycle();
        }
        if (indicatorType == IndicatorType.RoundPoint) {
            setMinimumHeight(selectPointSize > unSelectPointSize ? selectPointSize * 2 : unSelectPointSize * 2);
        }
        //initial paints
        initPaints();
        //initial linearLayout
        initContainer(context);
        //initial child layout params
        initParams();

    }

    private void initPaints() {
        unSelectPaint = new Paint();
        unSelectPaint.setAntiAlias(true);
        unSelectPaint.setColor(colorUnSelectPoint);
        selectPaint = new Paint();
        selectPaint.setAntiAlias(true);
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setColor(colorSelectPoint);
        underlinePaint = new Paint();
        underlinePaint.setColor(underlineColor);
        underlinePaint.setStyle(Paint.Style.FILL);
        underlinePaint.setAntiAlias(true);
    }

    private void initContainer(Context context) {
        mIndicatorContainer = new LinearLayout(context);
        mIndicatorContainer.setOrientation(LinearLayout.HORIZONTAL);
        mIndicatorContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mIndicatorContainer.setGravity(Gravity.CENTER_VERTICAL);
        addView(mIndicatorContainer);
    }

    private void initParams() {
        if (indicatorType == IndicatorType.TabWithIcon) {
            defaultTabLayoutParams = new LinearLayout.LayoutParams(zz_tab_icon_size, zz_tab_icon_size);
            expandedTabLayoutParams = new LinearLayout.LayoutParams(0, zz_tab_icon_size, 1.0f);
        } else {
            defaultTabLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            expandedTabLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (indicatorType) {
            case RoundPoint:
                drawPoints(canvas);
                break;
            case TabWithIcon:
            case TabWithText:
            case TabWithIconAndText:
                if (showUnderline)
                    drawUnderline(canvas);
                break;
        }
    }

    private void drawUnderline(Canvas canvas) {
        if (mViewPager != null) {
            View currentTab = getItem(currentPosition);
            if (currentTab != null) {
                float lineLeft = currentTab.getLeft() + underlinePadding;
                float lineRight = currentTab.getRight() - underlinePadding;

                // if there is an offset, start interpolating left and right coordinates between current and next tab
                if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

                    View nextTab = getItem(currentPosition + 1);
                    final float nextTabLeft = nextTab.getLeft() + underlinePadding;
                    final float nextTabRight = nextTab.getRight() - underlinePadding;

                    lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
                    lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
                }
                canvas.drawRect(lineLeft, getHeight() - underlineHeight, lineRight, getHeight(), underlinePaint);
            }
        }
    }

    private void drawPoints(Canvas canvas) {
        if (mViewPager != null) {
            int count = mViewPager.getAdapter().getCount();
            int index = mViewPager.getCurrentItem();
            int uLeft = (getWidth() - unSelectPointSize * count - spacing * (count - 1)) / 2;
            int y = getHeight() / 2;
            int sR = selectPointSize / 2;
            int uR = unSelectPointSize / 2;
            for (int i = 0; i < count; i++) {
                int uX = uLeft + 2 * i * uR + uR + i * spacing;
                canvas.drawCircle(uX, y, uR, unSelectPaint);
            }
            float uX = uLeft + (2 * index + 1) * uR + index * spacing;
            canvas.drawCircle(uX, y, sR, selectPaint);
        }
    }

    @Override
    public void setCurrentItem(int position, boolean animate) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position, animate);
        }
    }

    /**
     * bind indicator to your viewpager.
     * @param viewPager your viewpager
     */
    @Override
    public void setViewPager(ViewPager viewPager) {
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        tabCount = viewPager.getAdapter().getCount();
        this.mViewPager = viewPager;
        addPageChangeListener();
        switch (indicatorType) {
            case RoundPoint:
                int count = mViewPager.getAdapter().getCount();
                setMinimumWidth(selectPointSize > unSelectPointSize ? selectPointSize * count + spacing * (count - 1) : unSelectPointSize * count + spacing * (count - 1));
                break;
            case TabWithText:
                setUpText();
                selectText(0);
                break;
            case TabWithIcon:
                setUpIcons();
                selectIcon(0);
                break;
            case TabWithIconAndText:
                setUpIconsAndText();
                selectIconAndText(0);
                break;
        }
    }


    private void setUpIconsAndText() {
        mIndicatorContainer.removeAllViews();
        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(zz_tab_icon_size, zz_tab_icon_size);
        ivParams.topMargin = 5;
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            int icon = ((ZzBasePagerAdapter) mViewPager.getAdapter()).getUnselectedIcon(i);
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(icon);

            TextView tv = new TextView(getContext());
            tv.setGravity(Gravity.CENTER);
            tv.setSingleLine();
            tv.setTextColor(tabTextColorUnSelect);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSizeUnSelect);
            tv.setText(mViewPager.getAdapter().getPageTitle(i));

            LinearLayout ll = new LinearLayout(getContext());
            ll.setGravity(Gravity.CENTER);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.addView(iv, 0, ivParams);
            ll.addView(tv, 1, tvParams);
            ll.setPadding(tabPadding, 0, tabPadding, 0);
            final int finalI = i;
            ll.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    setCurrentItem(finalI, true);
                }
            });
            mIndicatorContainer.addView(ll, i, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
        }
    }

    private void setUpIcons() {
        mIndicatorContainer.removeAllViews();
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            int icon = ((ZzBasePagerAdapter) mViewPager.getAdapter()).getUnselectedIcon(i);
            ImageView iv = new ImageView(getContext());
            iv.setFocusable(true);
            iv.setClickable(true);
            iv.setImageResource(icon);
            final int finalI = i;
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    setCurrentItem(finalI, true);
                }
            });
            iv.setPadding(tabPadding, 0, tabPadding, 0);
            mIndicatorContainer.addView(iv, i, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
        }

    }

    private void setUpText() {
        mIndicatorContainer.removeAllViews();
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            TextView tv = new TextView(getContext());
            tv.setFocusable(true);
            tv.setClickable(true);
            tv.setSingleLine();
            tv.setGravity(Gravity.CENTER);
            tv.setText(mViewPager.getAdapter().getPageTitle(i));
            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    setCurrentItem(finalI, true);
                }
            });
            tv.setPadding(tabPadding, 0, tabPadding, 0);
            mIndicatorContainer.addView(tv, i, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
        }
    }

    private void addPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
                currentPositionOffset = positionOffset;
                invalidate();
            }

            @Override
            public void onPageSelected(int position) {
                switch (indicatorType) {
                    case TabWithIcon:
                        selectIcon(position);
                        break;
                    case TabWithText:
                        selectText(position);
                        break;
                    case TabWithIconAndText:
                        selectIconAndText(position);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (indicatorType == IndicatorType.TabWithIcon) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        scrollToChild(mViewPager.getCurrentItem(), 0);
                    }
                }
            }
        });
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SaveState saveState = new SaveState(superState);
        saveState.position = currentPosition;
        return saveState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SaveState savedState = (SaveState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.position;
        switch (indicatorType) {
            case TabWithIcon:
                selectIcon(currentPosition);
                break;
            case TabWithText:
                selectText(currentPosition);
                break;
            case TabWithIconAndText:
                selectIconAndText(currentPosition);
                break;
        }
    }

    static class SaveState extends BaseSavedState {
        int position;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.position);
        }

        public SaveState(Parcelable superState) {
            super(superState);
        }

        protected SaveState(Parcel in) {
            super(in);
            this.position = in.readInt();
        }

        public static final Parcelable.Creator<SaveState> CREATOR = new Parcelable.Creator<SaveState>() {
            @Override
            public SaveState createFromParcel(Parcel source) {
                return new SaveState(source);
            }

            @Override
            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }
        };
    }

    private void scrollToChild(int position, int offset) {

        if (tabCount == 0) {
            return;
        }

        if (getItem(position) != null) {
            int newScrollX = getItem(position).getLeft() + offset;

            if (newScrollX != lastScrollX) {
                lastScrollX = newScrollX;
                scrollTo(newScrollX, 0);
            }
        }

    }

    private void selectIcon(int position) {
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            ImageView iv = (ImageView) getItem(i);
            if (i == position) {
                int icon = ((ZzBasePagerAdapter) mViewPager.getAdapter()).getSelectedIcon(i);
                iv.setImageResource(icon);
            } else {
                int icon = ((ZzBasePagerAdapter) mViewPager.getAdapter()).getUnselectedIcon(i);
                iv.setImageResource(icon);
            }
        }
    }


    private void selectText(int position) {
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            TextView tv = (TextView) getItem(i);
            if (i == position) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSizeSelect);
                tv.setTextColor(tabTextColorSelect);
            } else {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSizeUnSelect);
                tv.setTextColor(tabTextColorUnSelect);
            }
        }
    }

    private void selectIconAndText(int position) {
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            LinearLayout ll = (LinearLayout) getItem(i);
            ImageView iv = (ImageView) ll.getChildAt(0);
            TextView tv = (TextView) ll.getChildAt(1);
            if (i == position) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSizeSelect);
                tv.setTextColor(tabTextColorSelect);
            } else {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSizeUnSelect);
                tv.setTextColor(tabTextColorUnSelect);
            }
            if (i == position) {
                int icon = ((ZzBasePagerAdapter) mViewPager.getAdapter()).getSelectedIcon(i);
                iv.setImageResource(icon);
            } else {
                int icon = ((ZzBasePagerAdapter) mViewPager.getAdapter()).getUnselectedIcon(i);
                iv.setImageResource(icon);
            }
        }
    }

    private View getItem(int position) {
        return mIndicatorContainer.getChildAt(position);
    }
}
