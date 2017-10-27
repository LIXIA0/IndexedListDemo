package com.example.lixiao.deomdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.lixiao.deomdemo.R;

/**
 * Created by lixiao on 2017/10/26.
 */

public class SideBar extends View{
    private String[] indexNames = {"#", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint paint = new Paint();//onDraw()中用到的绘制工具类对象
    private int currentChoice = -1; // 当前选择的字母
    private TextView indexViewer; // 显示当前选中的索引的放大版
    private OnIndexChoiceChangedListener listener; // 回调接口，用来监听选中索引该表时出发的事件
    public SideBar(Context context) {
        this(context,null);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int totalwidth = getWidth();
        int totalheight = getHeight();
        int singleHeight = (totalheight - 5) / indexNames.length;
        for (int i=0;i<indexNames.length;i++){
            paint.setColor(Color.rgb(34, 66, 99));//设置字母颜色
            paint.setTypeface(Typeface.DEFAULT_BOLD);//设置字体
            paint.setTextSize(30);//设置字体大小
            paint.setAntiAlias(true);//抗锯齿
            //如果当前的手指触摸索引和字母索引相同，那么字体颜色进行区分
            if(i==currentChoice){
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            /*
         * 绘制字体，需要制定绘制的x、y轴坐标
         *
         * x轴坐标 = 控件宽度的一半 - 字体宽度的一半
         * y轴坐标 = singleHeight * i + singleHeight
         */
            float xpos = totalwidth / 2 - paint.measureText(indexNames[i]) / 2;
            float ypos = singleHeight * i + singleHeight;
            canvas.drawText(indexNames[i], xpos, ypos, paint);

            // 重置画笔，准备绘制下一个字母索引
            paint.reset();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float y = event.getY();//手指所指的Y的位置
        int lastChoice = currentChoice;
        final OnIndexChoiceChangedListener listener = this.listener;
        final int c = (int) (y / getHeight() * indexNames.length); // 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数
        switch (action) {
            case MotionEvent.ACTION_UP:
                // 如果手指没有触摸屏幕，SideBar的背景颜色为默认，索引字母提示控件不可见
                setBackgroundColor(Color.parseColor("#00000000")); // 背景设置为透明
                currentChoice = -1;
                invalidate(); // 实时更新视图绘制
                if (indexViewer != null) {
                    indexViewer.setVisibility(View.INVISIBLE);
                }
                break;
            default: // 按下手指或手指滑动
                setBackgroundResource(R.drawable.sidebar_background);
                if (lastChoice != c) {
                    // 如果触摸点没有超出控件范围
                    if (c >= 0 && c < indexNames.length) {
                        if (listener != null) {
                            listener.onIndexChoiceChanged(indexNames[c]);
                        }

                        if (indexViewer  != null) {
                            indexViewer .setText(indexNames[c]);
                            indexViewer .setVisibility(View.VISIBLE);
                        }

                        currentChoice = c;
                        invalidate();
                    }

                }
                break;
        }
        return true;
    }

    public void setIndexViewer(TextView indexViewer) {
            this.indexViewer = indexViewer;
    }
    public void setOnIndexChoiceChangedListener(OnIndexChoiceChangedListener listener) {
            this.listener = listener;
    }
     /**
      * 回调接口，用来监听选中索引该表时出发的事件
     */
     public interface OnIndexChoiceChangedListener {
         void onIndexChoiceChanged(String s);
     }
}
