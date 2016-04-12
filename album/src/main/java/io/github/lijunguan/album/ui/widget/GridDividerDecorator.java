package io.github.lijunguan.album.ui.widget;

/**
 * Created by lijunguan on 2016/4/11
 * email: lijunguan199210@gmail.com
 * blog : https://lijunguan.github.io
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lijunguan on 2016/4/11
 * email: lijunguan199210@gmail.com
 * blog : https://lijunguan.github.io
 */
public class GridDividerDecorator extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int mDividerSize;

    public GridDividerDecorator(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        mDividerSize = mDivider.getIntrinsicHeight();
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawRightDivider(c, parent);
        drawBottomDivider(c, parent);
    }

    private void drawRightDivider(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDividerSize;
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawBottomDivider(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() - params.rightMargin + mDividerSize;
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + mDividerSize;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        if (spanCount == -1)
            throw new ClassCastException("Can not cast" + parent.getLayoutManager() + "to GridLayoutManager");
        if (isLastCloum(itemPosition, spanCount)) {
            //如果是最后一列则不绘制右边的Divider
            outRect.set(0, 0, 0, mDividerSize);
        } else {
            outRect.set(0, 0, mDividerSize, mDividerSize);
        }
    }

    private boolean isLastCloum(int itemPosition, int spanCount) {
        if ((itemPosition + 1) % spanCount == 0)
            return true;
        return false;

    }

    /**
     * 得到RecyclerView的列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        } else {
            return -1;
        }
    }
}