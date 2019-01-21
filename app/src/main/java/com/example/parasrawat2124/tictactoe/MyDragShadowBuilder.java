package com.example.parasrawat2124.tictactoe;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class MyDragShadowBuilder extends View.DragShadowBuilder {
    private static Drawable shadow;

    public MyDragShadowBuilder(View view) {
        super(view);
    }

    @Override
    public void onProvideShadowMetrics(Point size, Point touch) {
        super.onProvideShadowMetrics(size, touch);
    }

}
