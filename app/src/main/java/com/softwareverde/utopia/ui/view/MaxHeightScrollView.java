package com.softwareverde.utopia.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.softwareverde.utopia.R;

public class MaxHeightScrollView extends ScrollView {
    private static final Integer _defaultHeight = 200;

    private Integer _maxHeight;

    private void _init(final Context context, final AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightScrollView);
            _maxHeight = styledAttrs.getDimensionPixelSize(R.styleable.MaxHeightScrollView_maxHeight, _defaultHeight);

            styledAttrs.recycle();
        }
    }

    public MaxHeightScrollView(final Context context) {
        super(context);
    }

    public MaxHeightScrollView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        if (! this.isInEditMode()) {
            _init(context, attrs);
        }
    }

    public MaxHeightScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (! this.isInEditMode()) {
            _init(context, attrs);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaxHeightScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        if (! this.isInEditMode()) {
            _init(context, attrs);
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(_maxHeight, MeasureSpec.AT_MOST));
    }
}