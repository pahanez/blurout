package com.pahanez.blurout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewTreeObserver;

public class BlurOne {
	
	private static final float SCALE_FACTOR = 0.3F;

	private View mView;
	private int mRadius;
	private Bitmap mBitmap;

	public BlurOne(View view) {
		this.mView = view;
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
	            canvas.drawBitmap(mBitmap, mMatrixScaleInv, null);
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
	        	mBitmap = Blur.in(mView);
	        }
	        
	        
	        
	        
		}
	};

}
