package com.pahanez.blurout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class BlurRelativeLayout extends RelativeLayout{
	
	private BlurOne  blurer;

	public BlurRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BlurRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BlurRelativeLayout(Context context) {
		super(context);
		
	}
	
	
	private void initBlur(){
		blurer = new BlurOne(this);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		initBlur();
		blurer.onAttachedToWindow();
	}
	
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		blurer.onDetachedFromWindow();
	};
	
	public void setBlurRadius(int radius) {
		blurer.setRadius(radius);
        invalidate();
    }
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setDrawingCacheEnabled(true);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.e("p37td8" , "meas v W " + getWidth() + " , H : " + getHeight());	
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		buildDrawingCache();
		blurer.onMeasured();
		Log.e("p37td8" , "layo v W " + getWidth() + " , H : " + getHeight());			
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		if(blurer != null){
			blurer.drawToCanvas(canvas);
		}
        super.dispatchDraw(canvas);
	}
	


}
