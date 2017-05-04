package com.example.administrator.banner2;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private LinearLayout dotLayout;
    private Timer timer;
    private Handler mHandler = new Handler();

    private int prePosition = 0;

    int[] imgRes = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        dotLayout = (LinearLayout) findViewById(R.id.dotLayout);

        initDots();

        mViewPager.setPageMargin(20);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView view = new ImageView(MainActivity.this);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                view.setImageResource(imgRes[position % imgRes.length]);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotLayout.getChildAt(prePosition).setEnabled(false);
                dotLayout.getChildAt(position % imgRes.length).setEnabled(true);
                prePosition = position % imgRes.length;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setPageTransformer(true, new ScaleInTransformer());
        if (null != timer) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    }
                });
            }
        }, 0, 1000);

    }

    private void initDots() {
        if (null != dotLayout) {
            dotLayout.removeAllViews();
        }
        for (int i = 0; i < imgRes.length; i++) {
            ImageView dot = new ImageView(this);
            dot.setEnabled(false);
            dot.setImageResource(R.drawable.dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            dot.setLayoutParams(params);
            dotLayout.addView(dot);
        }
        dotLayout.getChildAt(0).setEnabled(true);
    }
}
