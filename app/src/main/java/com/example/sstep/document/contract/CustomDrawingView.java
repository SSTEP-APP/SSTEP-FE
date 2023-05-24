package com.example.sstep.document.contract;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class CustomDrawingView extends View {
    private Path drawingPath;
    private Paint drawingPaint;
    private Bitmap canvasBitmap;
    private Canvas drawCanvas;
    private int canvasColor = Color.WHITE;

    private ScrollView parentScrollView;

    public CustomDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        drawingPath = new Path();
        drawingPaint = new Paint();
        drawingPaint.setColor(Color.BLACK);
        drawingPaint.setStrokeWidth(5f);
        drawingPaint.setStyle(Paint.Style.STROKE);
        drawingPaint.setStrokeJoin(Paint.Join.ROUND);
        drawingPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setParentScrollView(ScrollView scrollView) {
        parentScrollView = scrollView;
    }

    public void clearCanvas() {
        drawCanvas.drawColor(canvasColor);
        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, null);
        canvas.drawPath(drawingPath, drawingPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (parentScrollView != null) {
                    parentScrollView.requestDisallowInterceptTouchEvent(true);
                }
                drawingPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawingPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                if (parentScrollView != null) {
                    parentScrollView.requestDisallowInterceptTouchEvent(false);
                }
                drawCanvas.drawPath(drawingPath, drawingPaint);
                drawingPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }
}