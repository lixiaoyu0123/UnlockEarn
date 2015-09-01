package com.chinazmob.unlockearn.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.chinazmob.unlockearn.R;

/**
 * Created by 晓宇 on 2015/2/9.
 */
public class UnlockView extends ViewGroup implements View.OnClickListener {

    private Context mcontext;
    private AlphaAnimation malpha;
    private int mwidth, mheight;
    private int mhalfWith;
    private int malphaViewWidth, malphaViewHeight;
    private int mcenterViewWidth, mcenterViewHeight;
    private int mcenterViewTop, mcenterViewBottom;
    private int malphaViewTop, malphaViewBottom;
    private int mrightViewHalfWidth, mrightViewHalfHeight, mleftViewHalfWidth, mleftViewHalfHeight;
    private int mrightLightViewHalfWidth, mrightLightViewHalfHeight, mleftLightViewHalfWidth, mleftLightViewHalfHeight;

    private ImageView mcenterView, malphaView;
    private ImageView mrightLightView, mleftLightView;
    private ImageView mleftView, mrightView;
    private int mleftCenterPos;
    private int mrightCenterPos;

    private Rect mcenterViewRect;
    private Rect mleftRect, mrightRect;
    private boolean mtracking = false;
    private boolean misFinish = false;
    private Handler mmainHandler = null;

    public UnlockView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mcontext = context;
        initViews(context);
        onAnimationStart();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            mwidth = r;
            mheight = b;
            ////mwidth >> 1为向右位移1位，相当于mHalfWidth / 2。采用位移的原因是计算效率比较高。
            mhalfWith = mwidth>>1;
            getViewDimension();

            mleftCenterPos = mhalfWith/5 + mleftViewHalfWidth;
            mrightCenterPos = mwidth - mhalfWith/5 - mrightViewHalfWidth;

            int centerCirclePos = mheight - mcenterViewHeight/2 - mheight/7;
            mcenterViewTop = centerCirclePos - mcenterViewHeight/2;
            mcenterViewBottom = centerCirclePos + mcenterViewHeight/2;
            malphaViewTop = centerCirclePos - malphaViewHeight/2;
            malphaViewBottom = centerCirclePos + malphaViewHeight/2;

            setChildViewLayout();
            getChildViewRect();

            mcenterViewRect = new Rect(mhalfWith - mcenterViewWidth / 2, mcenterViewTop,
                    mhalfWith + mcenterViewWidth / 2, mcenterViewBottom);
        }
    }

    //设置各图标在LockView中的布局
    private void setChildViewLayout() {
        malphaView.layout(mhalfWith - malphaViewWidth / 2, malphaViewTop,
                mhalfWith + malphaViewWidth / 2, malphaViewBottom);

        mcenterView.layout(mhalfWith - mcenterViewWidth / 2, mcenterViewTop,
                mhalfWith + mcenterViewWidth / 2, mcenterViewBottom);

        mleftView.layout(mleftCenterPos - mleftViewHalfWidth , mcenterViewTop,
                mleftCenterPos + mleftViewHalfWidth,malphaViewBottom);

        mrightView.layout(mrightCenterPos - mrightViewHalfWidth,
                mcenterViewTop,
                mrightCenterPos + mrightViewHalfWidth,
                malphaViewBottom);

        mleftLightView.layout(mleftCenterPos - mleftLightViewHalfWidth, mcenterViewTop,
                mleftCenterPos + mleftLightViewHalfWidth,malphaViewBottom);

        mrightLightView.layout(mrightCenterPos - mrightLightViewHalfWidth,
                mcenterViewTop,
                mrightCenterPos + mrightLightViewHalfWidth,
                malphaViewBottom);
    }

    //创建各图标位置对应的Rect
    private void getChildViewRect() {
        mleftRect = new Rect(mleftCenterPos - mleftViewHalfWidth, mcenterViewTop,
                mleftCenterPos + mleftViewHalfWidth,malphaViewBottom);

        mrightRect = new Rect(mrightCenterPos - mrightViewHalfWidth,
                mcenterViewTop,
                mrightCenterPos + mrightViewHalfWidth,
                malphaViewBottom);
    }

    //获取各个图标的宽、高
    private void getViewDimension() {
        malphaView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        malphaViewWidth = malphaView.getMeasuredWidth();
        malphaViewHeight = malphaView.getMeasuredHeight();

        mcenterView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mcenterViewWidth = mcenterView.getMeasuredWidth();
        mcenterViewHeight = mcenterView.getMeasuredHeight();

        mleftView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mleftViewHalfWidth = (mleftView.getMeasuredWidth()) >> 1;
        mleftViewHalfHeight = (mleftView.getMeasuredHeight()) >> 1;

        mrightView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mrightViewHalfWidth = (mrightView.getMeasuredWidth()) >> 1;
        mrightViewHalfHeight = (mrightView.getMeasuredHeight()) >> 1;

        mleftLightView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mleftLightViewHalfWidth = (mleftLightView.getMeasuredWidth()) >> 1;
        mleftLightViewHalfHeight = (mleftLightView.getMeasuredHeight()) >> 1;

        mrightLightView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mrightLightViewHalfWidth = (mrightLightView.getMeasuredWidth()) >> 1;
        mrightLightViewHalfHeight = (mrightLightView.getMeasuredHeight()) >> 1;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        final int action = ev.getAction();
        final float x = ev.getX();
        final float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //手指点在中心图标范围区域内
                if (mcenterViewRect.contains((int) x, (int) y)) {
                    mtracking = true;
                    //stopViewAnimation();
                    onAnimationEnd();
                    malphaView.setVisibility(View.INVISIBLE);
                    return true;
                }
                break;

            default:
                break;
        }
        //此处返回false，onClick事件才能监听的到
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
                /*mTracking为true时，说明中心图标被点击移动
		 * 即只有在中心图标被点击移动的情况下，onTouchEvent
		 * 事件才会触发。
		 */
        if (mtracking) {
            final int action = event.getAction();
            final float nx = event.getX();
            final float ny = event.getY();

            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    setTargetViewVisible(nx, ny);
                    //中心图标移动
                    handleMoveView(nx, ny);
                    break;
                case MotionEvent.ACTION_UP:
                    mtracking = false;
                    doTriggerEvent(nx, ny);
                    if(!misFinish){
                        setLightInvisible();
                        resetMoveView();
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mtracking = false;
                    doTriggerEvent(nx, ny);
                    setLightInvisible();
                    resetMoveView();
                    break;
            }
        }
        return mtracking || super.onTouchEvent(event);
    }

    //平方和计算
    private float dist2(float dx, float dy) {
        return dx * dx + dy * dy;
    }

    private void handleMoveView(float x, float y) {
        mcenterView.setX((int) x - mcenterView.getWidth() / 2);
        ShowLightView(x, (int) y - mcenterView.getHeight() / 2);
        invalidate();
    }

    //监听解锁、启动拨号、相机、短信应用
    private void doTriggerEvent(float a, float b) {
        if (mleftRect.contains((int) a, (int) b)) {
            //stopViewAnimation();
            onAnimationEnd();
            //setTargetViewInvisible(mLeftView);
            virbate();
            //发送消息到MainActivity类中的mHandler出来
            //mainHandler.obtainMessage(MainActivity.MSG_LAUNCH_SMS).sendToTarget();
        } else if (mrightRect.contains((int) a, (int) b)) {
            ((Activity) mcontext).finish();
            misFinish = true;
            onAnimationEnd();
            //setTargetViewInvisible(mUnLockView);
            virbate();

            //mainHandler.obtainMessage(MainActivity.MSG_LAUNCH_DIAL).sendToTarget();
        }
    }

    //中心图标拖动到指定区域时显示高亮图标
    private void ShowLightView(float a, float b) {
        if (mrightRect.contains((int) a, (int) b)) {
            setLightVisible(mrightLightView);
        } else if (mleftRect.contains((int) a, (int) b)) {
            setLightVisible(mleftLightView);
        } else {
            setLightInvisible();
        }
    }

    private void setLightVisible(ImageView view) {
        view.setVisibility(View.VISIBLE);
        mcenterView.setVisibility(View.INVISIBLE);
    }

    //隐藏高亮图标
    private void setLightInvisible() {
        final View mActivatedViews[] = {mrightLightView, mleftLightView};
        for (View view : mActivatedViews) {
            view.setVisibility(View.INVISIBLE);
        }

        mcenterView.setVisibility(View.VISIBLE);
    }

    private void setTargetViewInvisible(ImageView img) {
        img.setVisibility(View.INVISIBLE);
    }

    private void setTargetViewVisible(float x, float y) {
        if (Math.sqrt(dist2(x - mhalfWith, y - (mcenterView.getTop() + mcenterViewWidth / 2)
        )) > malphaViewHeight / 4) {
        }
    }

    private void setTargetViewVisible() {
        final View mTargetViews[] = {mleftView, mrightView};
        for (View view : mTargetViews) {
            view.setVisibility(View.VISIBLE);
        }
    }

    //重置中心图标，回到原位置
    private void resetMoveView() {
        mcenterView.setX(mwidth / 2 - mcenterViewWidth / 2);
        mcenterView.setY((mcenterView.getTop() + mcenterViewHeight / 2) - mcenterViewHeight / 2);
        onAnimationStart();
        invalidate();
    }

    public void setMainHandler(Handler handler) {
        mmainHandler = handler;
    }

    //解锁时震动
    private void virbate() {
        Vibrator vibrator = (Vibrator) mcontext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onClick(View v) {

    }

    private void initViews(Context context) {
        malphaView = new ImageView(context);
        malphaView.setImageResource(R.drawable.lock_slide_icon_normal_no_quick_launcher);
        setViewsLayout(malphaView);
        malphaView.setVisibility(View.INVISIBLE);

        mcenterView = new ImageView(context);
        mcenterView.setImageResource(R.drawable.lock_slide_icon_pressed);
        setViewsLayout(mcenterView);
        mcenterView.setVisibility(View.VISIBLE);

        mrightView = new ImageView(context);
        mrightView.setImageResource(R.drawable.lock_right_icon_normal);
        setViewsLayout(mrightView);
        mrightView.setVisibility(View.VISIBLE);

        mleftView = new ImageView(context);
        mleftView.setImageResource(R.drawable.lock_left_icon_normal);
        setViewsLayout(mleftView);
        mleftView.setVisibility(View.VISIBLE);

        mleftLightView = new ImageView(context);
        setLightDrawable(mleftLightView);
        setViewsLayout(mleftLightView);
        mleftLightView.setVisibility(INVISIBLE);

        mrightLightView = new ImageView(context);
        setLightDrawable(mrightLightView);
        setViewsLayout(mrightLightView);
        mrightLightView.setVisibility(INVISIBLE);

    }

    private void setLightDrawable(ImageView img) {
        img.setImageResource(R.drawable.lock_touched);
    }

    //设置获取图标的参数，并添加到LockView
    private void setViewsLayout(ImageView image) {
        image.setScaleType(ImageView.ScaleType.CENTER);
        image.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        addView(image);
    }

    //停止中心图标动画
    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
        if (malpha != null) {
            malpha = null;
        }
        malphaView.setAnimation(null);
    }

    //显示中心图标动画
    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
        malphaView.setVisibility(View.VISIBLE);

        if (malpha == null) {
            malpha = new AlphaAnimation(0.0f, 1.0f);
            malpha.setDuration(1000);
        }
        malpha.setRepeatCount(Animation.INFINITE);
        malphaView.startAnimation(malpha);
    }
}
