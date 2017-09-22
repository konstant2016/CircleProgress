package cn.showmac.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 描述:圆形进度条
 * 创建人:菜籽
 * 创建时间:2017/9/22 下午6:01
 * 备注:
 */

public class CircleProgress extends View {

    private final float DEFAULT_UPPER_WIDTH = 15;
    private final float DEFAULT_LOWER_WIDTH = 20;
    private final float DEFAULT_SHADOW_WIDTH = 30;
    private final float DEFAULT_START_ANGLE = 135;
    private final float DEFAULT_SWEEP_ANGLE = 270;
    private final float DEFAULT_VALUE = 5000;

    // 上层圆
    private Paint mUpperPaint;                      // 上层画笔
    private float mUpperWidth;                      // 上层圆的宽度
    private int[] mUpperColors = {Color.GREEN, Color.YELLOW, Color.YELLOW,Color.RED, Color.RED,Color.GREEN};        // 上层圆的颜色
    private float mValue;                           // 上层圆的数值

    // 下层圆
    private Paint mLowerPaint;
    private float mLowerWidth;
    private int mLowerColor;

    // 阴影圆
    private Paint mShadowPaint;
    private float mShadowWidth;
    private int mShadowColor;

    // 中心点
    private float centerX;
    private float centerY;

    // 大小
    private int mWidth;
    private int mHeight;

    // 绘制角度
    private float mStartAngle;
    private float mSweepAngle;                      // 注意：是圆的扫描角度，不是结束角度


    // 存储圆的左上右下位置
    private RectF mRectF;

    // 圆的半径
    private float mRadius;

    // 扫描梯度(用于设置渐变)
    private SweepGradient mSweepGradient;


    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
        initElements();
    }

    private void initViews(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        mUpperColors[0] = typedArray.getInt(R.styleable.CircleProgress_upperColor1, Color.GREEN);
        mUpperColors[1] = typedArray.getInt(R.styleable.CircleProgress_upperColor2, Color.YELLOW);
        mUpperColors[2] = typedArray.getInt(R.styleable.CircleProgress_upperColor2, Color.YELLOW);
        mUpperColors[3] = typedArray.getInt(R.styleable.CircleProgress_upperColor3, Color.RED);
        mUpperColors[4] = typedArray.getInt(R.styleable.CircleProgress_upperColor3, Color.RED);
        mUpperColors[5] = typedArray.getInt(R.styleable.CircleProgress_upperColor1, Color.GREEN);

        mUpperWidth = typedArray.getDimension(R.styleable.CircleProgress_upperLineWidth, DEFAULT_UPPER_WIDTH);

        mLowerColor = typedArray.getInt(R.styleable.CircleProgress_lowerColor, Color.CYAN);
        mLowerWidth = typedArray.getDimension(R.styleable.CircleProgress_lowerLineWidth, DEFAULT_LOWER_WIDTH);

        mShadowColor = typedArray.getInt(R.styleable.CircleProgress_shadowColor, Color.GRAY);
        mShadowWidth = typedArray.getDimension(R.styleable.CircleProgress_shadowLineWidth, DEFAULT_SHADOW_WIDTH);

        mValue =  typedArray.getFloat(R.styleable.CircleProgress_value, DEFAULT_VALUE);

        mStartAngle = typedArray.getFloat(R.styleable.CircleProgress_startAngle, DEFAULT_START_ANGLE);
        mSweepAngle = typedArray.getFloat(R.styleable.CircleProgress_sweepAngle, DEFAULT_SWEEP_ANGLE);

        typedArray.recycle();
    }

    // 初始化内部控件
    private void initElements() {
        mUpperPaint = new Paint();
        mUpperPaint.setAntiAlias(true);                 // 设置抗锯齿
        mUpperPaint.setStrokeWidth(mUpperWidth);        // 设置圆宽度
        mUpperPaint.setStyle(Paint.Style.STROKE);       // 设置圆的填充形式
        mUpperPaint.setStrokeCap(Paint.Cap.ROUND);      // 设置圆线的线冒样式

        mLowerPaint = new Paint();
        mLowerPaint.setAntiAlias(true);
        mLowerPaint.setStrokeWidth(mLowerWidth);
        mLowerPaint.setStyle(Paint.Style.STROKE);
        mLowerPaint.setColor(mLowerColor);
        mLowerPaint.setStrokeCap(Paint.Cap.ROUND);

        mShadowPaint = new Paint();
        mShadowPaint.setAntiAlias(true);
        mShadowPaint.setStrokeWidth(mShadowWidth);
        mShadowPaint.setStyle(Paint.Style.STROKE);
        mShadowPaint.setColor(mShadowColor);
        mShadowPaint.setStrokeCap(Paint.Cap.ROUND);

        mRectF = new RectF();
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
        float lineWidth = Math.max(mShadowWidth, Math.max(mUpperWidth, mLowerWidth));

        float minWidth = mWidth - getPaddingLeft() - getPaddingRight();
        float minHeight = mHeight - getPaddingTop() - getPaddingBottom();
        float minSize = Math.min(minWidth, minHeight);

        mRadius = (minSize - lineWidth) / 2;

        mRectF.left = centerX - mRadius;
        mRectF.top = centerY - mRadius;
        mRectF.right = centerX + mRadius;
        mRectF.bottom = centerY + mRadius;
        setSweepGradient();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        // 先画阴影
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mShadowPaint);
        canvas.save();
        // 再画背景
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mLowerPaint);
        canvas.save();
        // 再画圆环
        float sweepAngle = mSweepAngle * mValue / 10000;
        canvas.drawArc(mRectF, mStartAngle, sweepAngle, false, mUpperPaint);
        canvas.save();
    }

    // 设置渐变
    private void setSweepGradient() {
        mSweepGradient = new SweepGradient(centerX, centerY, mUpperColors, null);
        Matrix matrix = new Matrix();
        matrix.setRotate(mStartAngle, centerX, centerY);
        mSweepGradient.setLocalMatrix(matrix);
        mUpperPaint.setShader(mSweepGradient);
    }


    // 设置数值
    public void setValue(float value) {
        mValue = value;
        invalidate();
    }

    // 设置渐变色
    public void setUpperColors(int[] colors) {
        mUpperColors = colors;
        setSweepGradient();
        invalidate();
    }

    // 设置上层线宽
    public void setUpperLineWidth(int width) {
        mUpperWidth = width;
    }

    // 设置背景线宽
    public void setLowerLineWidth(int width) {
        mLowerWidth = width;
    }

    // 设置背景线宽
    public void setShadowLineWidth(int width) {
        mShadowWidth = width;
    }
}
