package cn.ninesmart.ninenews.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import cn.ninesmart.ninenews.R;

/**
 * Author   : perqin
 * Date     : 17-1-28
 */

public class BottomViewAwareScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {
    private View mBottomView;
    private int mBottomViewId;

    public BottomViewAwareScrollingViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomViewAwareScrollingViewBehavior);
        mBottomViewId = a.getResourceId(R.styleable.BottomViewAwareScrollingViewBehavior_behavior_bottomView, -1);
        a.recycle();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (mBottomView == null) {
            mBottomView = parent.findViewById(mBottomViewId);
        }
        return child.getId() == mBottomView.getId() || super.layoutDependsOn(parent, child, dependency);
    }
}
