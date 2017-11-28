package cn.konstant.circleprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 描述:带刻度的圆环进度条
 * 创建人:菜籽
 * 创建时间:2017/11/28 下午4:21
 * 备注:
 */

public class ScaleProgress extends View {

    private Paint mScalePaint;                  // 刻度画笔
    private int mScaleStartColor;               // 刻度线的开始颜色
    private int mScaleStopColor;                // 刻度线的结束颜色
    private int mNormalColor;                   // 刻度的基本颜色
    private float mScaleWidth = 10;              // 刻度的宽度
    private float mScaleHeight = 40;             // 刻度的长度
    private int mScaleNum;                       // 刻度的数量

    private int mProgress = 50;             // 当前进度

    private float centerX;                  // 中心点
    private float centerY;
    private float mRadius;                  // 控件圆的半径

    private Paint mShadowPaint;              // 阴影圆
    private float mShadowWidth = 80;         // 阴影的宽度
    private int mShadowColor;                // 阴影的颜色

    private int mWidth;                      // 控件宽度
    private int mHeight;                     // 控件高度

    private long mAnimDuration;              // 动画持续时间

    private ValueAnimator mAnimator;         // 属性动画
    private float mDynamicValue;             // 动态值


    public ScaleProgress(Context context) {
        this(context, null);
    }

    public ScaleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取用户配置属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScaleProgress);
        mShadowColor = typedArray.getColor(R.styleable.ScaleProgress_scaleShadowColor, Color.parseColor("#0e52a6"));
        mScaleWidth = typedArray.getDimension(R.styleable.ScaleProgress_shadowWidth, 80);
        mNormalColor = typedArray.getColor(R.styleable.ScaleProgress_normalColor, Color.parseColor("#0a4691"));
        mScaleStartColor = typedArray.getColor(R.styleable.ScaleProgress_scaleStartColor, Color.RED);
        mScaleStopColor = typedArray.getColor(R.styleable.ScaleProgress_scaleStopColor, Color.GREEN);
        mScaleWidth = typedArray.getDimension(R.styleable.ScaleProgress_scaleWhdth, 10);
        mScaleHeight = typedArray.getDimension(R.styleable.ScaleProgress_scaleHeight, 40);
        mAnimDuration = typedArray.getInteger(R.styleable.ScaleProgress_animDuration, 1000);
        mScaleNum = typedArray.getInteger(R.styleable.ScaleProgress_scaleNumber, 100);
        mProgress = typedArray.getInteger(R.styleable.ScaleProgress_progress,50);

        typedArray.recycle();

        initUI();
    }

    private void initUI() {
        // 刻度画笔
        mScalePaint = new Paint();
        mScalePaint.setAntiAlias(true);
        mScalePaint.setStrokeWidth(mScaleWidth);
        mScalePaint.setStrokeCap(Paint.Cap.ROUND);
        mScalePaint.setColor(mNormalColor);
        mScalePaint.setStyle(Paint.Style.STROKE);

        mShadowPaint = new Paint();
        mShadowPaint.setAntiAlias(true);
        mShadowPaint.setStrokeWidth(mShadowWidth);
        mShadowPaint.setStyle(Paint.Style.STROKE);
        mShadowPaint.setColor(mShadowColor);
        mShadowPaint.setStrokeCap(Paint.Cap.ROUND);

        mAnimator = new ValueAnimator();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawArcScale(canvas);
    }

    /**
     * 画刻度
     */
    private void drawArcScale(Canvas canvas) {
        canvas.save();

        // 绘制阴影
        canvas.drawCircle(centerX, centerY, mRadius - mShadowWidth / 2, mShadowPaint);

        // 绘制刻度
        for (int i = 0; i < mScaleNum; i++) {
            if (mDynamicValue > i) {
                mScalePaint.setColor(setColorGradient(i / mDynamicValue));
            } else {
                mScalePaint.setColor(mNormalColor);
            }
            float startX = mWidth / 2;
            float startY = (centerY - mRadius) / 2 + mScaleWidth * 2;
            float stopX = mHeight / 2;
            float stopY = mScaleHeight + mScaleWidth * 2;
            canvas.drawLine(startX, startY, stopX, stopY, mScalePaint);
            // 旋转的度数 = 100 / 360
            canvas.rotate(360f / mScaleNum, mWidth / 2, mHeight / 2);
        }

        canvas.restore();
    }

    // 设置渐变
    private int setColorGradient(float percent) {

        float redStart = Color.red(mScaleStartColor) * (1 - percent);
        float greenStart = Color.green(mScaleStartColor) * (1 - percent);
        float blueStart = Color.blue(mScaleStartColor) * (1 - percent);

        float redEnd = Color.red(mScaleStopColor) * percent;
        float greenEnd = Color.green(mScaleStopColor) * percent;
        float blueEnd = Color.blue(mScaleStopColor) * percent;

        int red = Math.round((redStart + redEnd));
        int green = Math.round((greenStart + greenEnd));
        int blue = Math.round((blueStart + blueEnd));

        return (0xFF << 24) | (red << 16) | (green << 8) | blue;
    }

    // 开启动画
    private void startAnimator(float startAngle, float endAngle) {
        mAnimator = ValueAnimator.ofFloat(startAngle, endAngle);
        mAnimator.setDuration(mAnimDuration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percent = (float) animation.getAnimatedValue();
                mDynamicValue = percent * mProgress;
                invalidate();
            }
        });
        mAnimator.start();
    }

    // 设置进度
    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
        startAnimator(0, ((float) mProgress) / 100);
    }

    // 设置刻度数量
    public void setScaleNum(int scaleNum) {
        this.mScaleNum = scaleNum;
        startAnimator(0, ((float) mProgress) / 100);
    }

    // 设置动画时间
    public void setAnimDuration(long mAnimDuration) {
        this.mAnimDuration = mAnimDuration;
        startAnimator(0, ((float) mProgress) / 100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode != MeasureSpec.UNSPECIFIED) {
            mWidth = widthSize;
        }
        if (heightMode != MeasureSpec.UNSPECIFIED) {
            mHeight = heightSize;
        }
        mWidth = mHeight = Math.min(mWidth, mHeight);
        setMeasuredDimension(mWidth, mHeight);

        centerX = centerY = mWidth / 2;

        // 根据最大线宽来计算圆的位置
        float minWidth = mWidth - getPaddingLeft() - getPaddingRight();
        float minHeight = mHeight - getPaddingTop() - getPaddingBottom();
        float minSize = Math.min(minWidth, minHeight);

        mRadius = minSize / 2;

    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) {
            return;
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isInEditMode()) {
            return;
        }
    }
}
