package com.jack.weather.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.jack.weather.R;
import com.jack.weather.util.Util;

/**
 * Created by Jack on 2016/8/7.
 */


public class ScrollBlurView extends RelativeLayout{

    //底部模糊的图片
    private ImageView BlurImage=null;
    //这是现实图片
    private ImageView ShowImage=null;
    //设置图片Drawable
    private Drawable drawable=null;


    public ScrollBlurView(Context context) {
        this(context,null);
    }

    public ScrollBlurView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollBlurView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化参数
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(attrs,R.styleable.ScrollBlurView,defStyleAttr,0);
        int typedArrayNum=typedArray.getIndexCount();
        for(int i=0;i<typedArrayNum;i++){
            int attr=typedArray.getIndex(i);
            switch(attr){
                case R.styleable.ScrollBlurView_src:
                    drawable=typedArray.getDrawable(attr);
                    break;
            }
        }

        LayoutInflater.from(context).inflate(R.layout.widget_scrollblurview,this);
        BlurImage=(ImageView)findViewById(R.id.blur_image);
        ShowImage=(ImageView)findViewById(R.id.show_img);

        initImage(context);
    }

    //初始化图片
    public void initImage(Context context){
        if(drawable==null||ShowImage==null||BlurImage==null){
            return ;
        }
        ShowImage.setImageDrawable(drawable);
        Bitmap showBitmap=drawableToBitmap(drawable);
        BlurImage.setImageBitmap(DealBluBitmap(context,showBitmap));
    }

    public void setScrollBlur(int move){
        ShowImage.setAlpha(move);
    }


    //图片模糊处理
    public Bitmap DealBluBitmap(Context context, Bitmap image){
        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() * 0.4f);
        int height = Math.round(image.getHeight() * 0.4f);
        // 将缩小后的图片做为预渲染的图片。
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        // 创建一张渲染后的输出图片。
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(25f);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);

        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    /**
     * 将Drawable对象转化为Bitmap对象
     *
     * @param drawable  Drawable对象
     * @return          对应的Bitmap对象
     */
    static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }






}
