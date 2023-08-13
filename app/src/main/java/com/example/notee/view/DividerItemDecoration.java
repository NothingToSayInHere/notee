package com.example.notee.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private final Drawable divider;
    private final int paddingStart;
    private final int paddingEnd;

    public DividerItemDecoration(Context context, int resId, int paddingStart, int paddingEnd) {
        divider = ContextCompat.getDrawable(context, resId);
        this.paddingStart = paddingStart;
        this.paddingEnd = paddingEnd;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = parent.getPaddingLeft() + paddingStart;
        int right = parent.getWidth() - parent.getPaddingRight() - paddingEnd;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }
}