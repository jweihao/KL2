package com.jweihao.jdemo.ui.activity.md;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jweihao.jdemo.utils.GoToActivityUtil;
import com.wh.customcontrol.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaterialDesignActivity extends AppCompatActivity {

    @BindView(R.id.button_palette)
    Button mButtonPalette;
    @BindView(R.id.button_flatness)
    Button mButtonFlatness;
    @BindView(R.id.button_tinting)
    Button mButtonTinting;
    @BindView(R.id.button_clipping)
    Button mButtonClipping;
    @BindView(R.id.button_recycler)
    Button mButtonRecycler;
    @BindView(R.id.button_transition)
    Button mButtonTransition;
    @BindView(R.id.button_ripple)
    Button mButtonRipple;
    @BindView(R.id.button_circular)
    Button mButtonCircular;
    @BindView(R.id.button_statelist)
    Button mButtonStatelist;
    @BindView(R.id.button_toolbar)
    Button mButtonToolbar;
    @BindView(R.id.button_notification)
    Button mButtonNotification;
    @BindView(R.id.button_swipeRefresh)
    Button mButtonSwipeRefresh;
    @BindView(R.id.button_collapsingtoolbar)
    Button mButtonCollapsingtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.button_palette, R.id.button_flatness, R.id.button_tinting,
            R.id.button_clipping, R.id.button_recycler, R.id.button_transition,
            R.id.button_ripple, R.id.button_circular, R.id.button_statelist,
            R.id.button_toolbar, R.id.button_notification, R.id.button_swipeRefresh,
            R.id.button_collapsingtoolbar
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_palette:
                GoToActivityUtil.goToActivity(this, PaletteActivity.class);
                break;
            case R.id.button_flatness:
                GoToActivityUtil.goToActivity(this, ShadowActivity.class);
                break;
            case R.id.button_tinting:
                GoToActivityUtil.goToActivity(this, TintingActivity.class);
                break;
            case R.id.button_clipping:
                GoToActivityUtil.goToActivity(this, ClippingActivity.class);
                break;
            case R.id.button_recycler:
                GoToActivityUtil.goToActivity(this, RecyclerViewMenuActivity.class);
                break;
            case R.id.button_transition:
                GoToActivityUtil.goToActivity(this, TransitionAnimActivity.class);
                break;
            case R.id.button_ripple:
                GoToActivityUtil.goToActivity(this, RippleActivity.class);
                break;
            case R.id.button_circular:
                GoToActivityUtil.goToActivity(this, CircularRevealActivity.class);
                break;
            case R.id.button_statelist:
                GoToActivityUtil.goToActivity(this, ViewChangesAnimatorActivity.class);
                break;

            case R.id.button_toolbar:
                GoToActivityUtil.goToActivity(this, ToolbarActivity.class);
                break;

            case R.id.button_notification:
                GoToActivityUtil.goToActivity(this, NotificationActivity.class);
                break;
            case R.id.button_swipeRefresh:
                GoToActivityUtil.goToActivity(this, SwipeRefreshLayoutActivity.class);
                break;
            case R.id.button_collapsingtoolbar:
                GoToActivityUtil.goToActivity(this, CollapsingToolbarActivity.class);
                break;
            default:
                break;
        }
    }

}
