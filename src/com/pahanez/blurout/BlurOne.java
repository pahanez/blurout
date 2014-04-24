package com.pahanez.blurout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver;

@SuppressLint("NewApi")
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

		mRs = RenderScript.create(view.getContext());

		android.util.Log.e("p37td8", "" + mInitialBitmap);
	}

	private void makeInitialBitmap() {
		// mView.setDrawingCacheEnabled(true);
		// mView.measure(MeasureSpec.makeMeasureSpec(mView.getWidth(),
		// MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(
		// mView.getHeight(), MeasureSpec.UNSPECIFIED));
		// mView.layout(0, 0, mView.getMeasuredWidth(),
		// mView.getMeasuredHeight());

		// mView.buildDrawingCache(true);
		Bitmap b = mView.getDrawingCache();
		mInitialBitmap = Bitmap.createBitmap(b);
		android.util.Log.e("p37td8", "makeInitialBitmap : " + mInitialBitmap.getWidth() + " , " + mInitialBitmap.getHeight() + " , " + mInitialBitmap.getAllocationByteCount());
		mView.setDrawingCacheEnabled(false);
	}

	@SuppressLint("MissingSuperCall")
	public void onDetachedFromWindow() {
		mView.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
	}

	public void onAttachedToWindow() {
		// makeInitialBitmap();
		mView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
	}

	public void setRadius(int radius) {
		mRadius = radius;
	}

	@SuppressLint("NewApi")
	public void drawToCanvas(Canvas canvas) {
		if (mBitmap != null) {
			// Draw off-screen bitmap using inverse of the scale matrix
			Log.e("p37td8", "drawtocanvas"); 
			Log.e("p37td8", "wid :: " + mBitmap.getWidth() + " hei : " + mBitmap.getHeight());
			Log.e("p37td8", "bytes" + mBitmap.getAllocationByteCount());
			canvas.drawLine(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mPaint);
			android.util.Log.e("p37td8", "draw mBitmap :"  + mBitmap.getWidth() + " , " + mBitmap.getHeight() );
			canvas.drawBitmap(mBitmap, 0, 0, mPaint);
		}
	}

	private final ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
		@Override
		public boolean onPreDraw() {
			if (mView.getVisibility() == View.VISIBLE) {
				// drawOffscreenBitmap();
				makeBlurBitmap();
			}
			return true;
		}

		private void makeBlurBitmap() {

			int width = Math.round(mView.getWidth() * SCALE_FACTOR);
			int height = Math.round(mView.getHeight() * SCALE_FACTOR);

			width = Math.max(width, 1);
			height = Math.max(height, 1);

			if (mBitmap == null) {
				Log.e("p37td8", "makeBlurBitmap");
				mBitmap = fastblur(mInitialBitmap, 10);
			}

		}

	};

	public void onMeasured() {
		makeInitialBitmap();
	}

	@SuppressLint("NewApi")
	public Bitmap fastblur(Bitmap sentBitmap, int radius) {

		if (VERSION.SDK_INT > 16) {
			Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

			final Allocation input = Allocation.createFromBitmap(mRs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
			final Allocation output = Allocation.createTyped(mRs, input.getType());
			final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(mRs, Element.U8_4(mRs));
			script.setRadius(radius /* e.g. 3.f */);
			script.setInput(input);
			script.forEach(output);
			output.copyTo(bitmap);
			return bitmap;
		}
		return null;
	}
}
