package com.pahanez.blurout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.ViewTreeObserver.OnPreDrawListener;

@SuppressLint("NewApi")
public class Blur {
	private Blur(){}
	
	public static void in(final View view ){
		view.setDrawingCacheEnabled(true);
		/*view.getViewTreeObserver().addOnDrawListener(new OnDrawListener() {
			
			@Override
			public void onDraw() {
				view.getViewTreeObserver().removeOnDrawListener(this);
				view.buildDrawingCache();

                Bitmap bmp = view.getDrawingCache();
                
                
                
                Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() ),
                        (int) (view.getMeasuredHeight()  ), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(overlay);

//                canvas.translate(-mButton.getLeft()/2 , -mButton.getTop()/2 );
//                canvas.scale(0.5f, 0.5f );
                Paint paint = new Paint();
                paint.setColor(Color.CYAN);
                bmp = StackBlur.fastblur(view.getContext(), bmp, 20);
                canvas.drawBitmap(bmp, 0, 0, paint);
                Log.e("p37td8","test");
                view.setBackground(new BitmapDrawable(view.getContext().getResources(), overlay));
			}
		});*/
		view.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			
			@SuppressLint("NewApi")
			@Override
			public boolean onPreDraw() {
				view.getViewTreeObserver().removeOnPreDrawListener(this);
				view.buildDrawingCache();

                Bitmap bmp = view.getDrawingCache();
                
                
                
                Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() ),
                        (int) (view.getMeasuredHeight()  ), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(overlay);

//                canvas.translate(-mButton.getLeft()/2 , -mButton.getTop()/2 );
//                canvas.scale(0.5f, 0.5f );
                Paint paint = new Paint();
                bmp = StackBlur.fastblur(view.getContext(), bmp, 20);
                canvas.drawBitmap(bmp, 0, 0, paint);
                view.setBackground(new BitmapDrawable(view.getContext().getResources(), overlay));
				return true;
			}
		});
		
	}
	
	
	
	
	public static void in(final View view , final int blurValue){
		view.setDrawingCacheEnabled(true);
		view.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			
			@SuppressLint("NewApi")
			@Override
			public boolean onPreDraw() {
				view.getViewTreeObserver().removeOnPreDrawListener(this);
				view.buildDrawingCache();

                Bitmap bmp = view.getDrawingCache();
                
                
                
                Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() ),
                        (int) (view.getMeasuredHeight()  ), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(overlay);

                Paint paint = new Paint();
                bmp = StackBlur.fastblur(view.getContext(), bmp, blurValue);
                canvas.drawBitmap(bmp, 0, 0, paint);
                view.setBackground(new BitmapDrawable(view.getContext().getResources(), overlay));
				return true;
			}
		});
		
	}
}
