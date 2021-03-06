package com.jweihao.jdemo.ui.activity.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.jweihao.jdemo.adapter.MessageAdapter;
import com.jweihao.jdemo.bean.Data;
import com.jweihao.jdemo.utils.GoToActivityUtil;
import com.jweihao.jdemo.utils.ImageWatcherUtil;
import com.jweihao.jdemo.utils.SpaceItemDecorationUtil;
import com.jweihao.jdemo.view.MessagePicturesLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wh.customcontrol.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ielse.view.imagewatcher.ImageWatcher;


        /*  Author: LuckSiege
        *   GitHub: https://github.com/LuckSiege/PictureSelector
        * */

public class TwitterActivity extends AppCompatActivity implements ImageWatcher.OnPictureLongPressListener, MessagePicturesLayout.Callback {

    @BindView(R.id.headBackButton)
    ImageButton mHeadBackButton;
    @BindView(R.id.headShareButton)
    ImageButton mHeadShareButton;
    @BindView(R.id.life_recycler)
    RecyclerView mLifeRecycler;
    @BindView(R.id.v_image_watcher)
    ImageWatcher mImageWatcher;
    @BindView(R.id.weibo_swipefresh)
    SwipeRefreshLayout mWeiboSwipefresh;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private boolean isTranslucentStatus = false;
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            isTranslucentStatus = true;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        ButterKnife.bind(this);
        initData();
    }


    @OnClick({R.id.headBackButton, R.id.headShareButton, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.headBackButton:
                TwitterActivity.this.finish();
                break;
            //友盟一键分享
            case R.id.headShareButton:
                break;
            case R.id.fab:
//                Toast.makeText(this, "我是发帖按钮", Toast.LENGTH_SHORT).show();
                GoToActivityUtil.goToActivity(this, SendWeiBoActivity.class);
            default:
                break;
        }
    }

    private void initData() {

        //下拉刷新
        mWeiboSwipefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));
        mWeiboSwipefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        mLifeRecycler.setLayoutManager(new LinearLayoutManager(this));
        mLifeRecycler.addItemDecoration(new SpaceItemDecorationUtil(this).setSpace(14).setSpaceColor(0xFFECECEC));
        mLifeRecycler.setAdapter(adapter = new MessageAdapter(this).setPictureClickCallback(this));
        adapter.set(Data.get(), 1);

        // 一般来讲， ImageWatcher 需要占据全屏的位置
        // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
        mImageWatcher.setTranslucentStatus(!isTranslucentStatus ? ImageWatcherUtil.calcStatusBarHeight(this) : 0);
        // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
        mImageWatcher.setErrorImageRes(R.mipmap.error_picture);
        // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
        mImageWatcher.setOnPictureLongPressListener(this);

        mImageWatcher.setLoader(new ImageWatcher.Loader() {
            @Override
            public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {
                Picasso.with(context).load(url).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        lc.onResourceReady(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        lc.onLoadFailed(errorDrawable);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        lc.onLoadStarted(placeHolderDrawable);
                    }
                });
            }
        });

        ImageWatcherUtil.fitsSystemWindows(isTranslucentStatus, findViewById(R.id.v_fit));
    }

    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //请求数据
                        initData();
                        //刷新数据
                        adapter.notifyDataSetChanged();
                        //关闭刷新状态
                        mWeiboSwipefresh.setRefreshing(false);
                    }
                });
            }
        }).start();

    }


    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        mImageWatcher.show(mFab, i, imageGroupList, urlList);
        mFab.setVisibility(View.GONE);
    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {
        Toast.makeText(v.getContext().getApplicationContext(), "长按了第" + (pos + 1) + "张图片", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (!mImageWatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
