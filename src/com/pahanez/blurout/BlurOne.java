package com.pahanez.blurout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.ViewTreeObserver;

public class BlurOne {
	
	private static final float SCALE_FACTOR = 0.3F;

	private View mView;
	private int mRadius;
	private Bitmap mBitmap;
	private Bitmap mInitialBitmap;
	private Paint mPaint = new Paint();
	
	private RenderScript mRs;

	@SuppressLint("NewApi")
	public BlurOne(View view) {
		this.mView = view;
		mView.setDrawingCacheEnabled(true);
		mInitialBitmap = mView.getDrawingCache();
		mRs = RenderScript.create(view.getContext());
	}

	@SuppressLint("MissingSuperCall")
	public void onDetachedFromWindow() {
		mView.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
	}

	public void onAttachedToWindow() {
		 mView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
	}

	public void setRadius(int radius) {
		mRadius = radius;
	}

	public void drawToCanvas(Canvas canvas) {
		 if (mBitmap != null) {
	            // Draw off-screen bitmap using inverse of the scale matrix
	            if(mBitmap != null){
	            	canvas.drawBitmap(mBitmap, 0, 0 , mPaint);
	            }
	        }
	}

	private final ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
		@Override
		public boolean onPreDraw() {
			if (mView.getVisibility() == View.VISIBLE) {
//				drawOffscreenBitmap();
				makeBitmap();
			}
			return true;
		}

		private void makeBitmap() {
			
			int width = Math.round(mView.getWidth() * SCALE_FACTOR);
	        int height = Math.round(mView.getHeight() * SCALE_FACTOR);
	        
	        width = Math.max(width, 1);
	        height = Math.max(height, 1);

	        if (mBitmap == null || mBitmap.getWidth() != width
	                || mBitmap.getHeight() != height) {
	        	mBitmap = fastblur(mInitialBitmap, 15);
	        }
	        
	        
	        
	        
		}
		
	};

	@SuppressLint("NewApi")
	public Bitmap fastblur(Bitmap sentBitmap, int radius) {
		
		if (VERSION.SDK_INT > 16) {
			Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
				
			final Allocation input = Allocation.createFromBitmap(mRs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
					Allocation.USAGE_SCRIPT);
			final Allocation output = Allocation.createTyped(mRs, input.getType());
			final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(mRs, Element.U8_4(mRs));
			script.setRadius(radius /* e.g. 3.f */);
			script.setInput(input);
			script.forEach(output);
			output.copyTo(bitmap);
			return bitmap;
		}return null;}
}
