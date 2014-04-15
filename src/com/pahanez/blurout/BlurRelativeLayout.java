package com.pahanez.blurout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class BlurRelativeLayout extends RelativeLayout{
	
	private BlurOne  blurer;

	public BlurRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initBlur();
	}

	public BlurRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initBlur();
	}

	public BlurRelativeLayout(Context context) {
		super(context);
		initBlur();
	}
	
	private void initBlur(){
		blurer = new BlurOne(this);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
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
	protected void dispatchDraw(Canvas canvas) {
		blurer.drawToCanvas(canvas);
        super.dispatchDraw(canvas);
	}


}
