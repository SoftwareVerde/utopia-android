package com.softwareverde.utopia.ui.widget;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softwareverde.utopia.R;

public class InProgressWidget {
    public static InProgressWidget newInstance(final LayoutInflater layoutInflater, final ViewGroup container) {
        final ViewGroup view = (ViewGroup) layoutInflater.inflate(R.layout.in_progress_widget, container, false);
        final InProgressWidget inProgressWidget = new InProgressWidget(view);

        inProgressWidget.attachToView(container);

        return inProgressWidget;
    }

    private static final Integer[] _TICK_VIEWS = new Integer[] {
        R.id.in_progress_tick_00, R.id.in_progress_tick_01, R.id.in_progress_tick_02,
        R.id.in_progress_tick_03, R.id.in_progress_tick_04, R.id.in_progress_tick_05,
        R.id.in_progress_tick_06, R.id.in_progress_tick_07, R.id.in_progress_tick_08,
        R.id.in_progress_tick_09, R.id.in_progress_tick_10, R.id.in_progress_tick_11,
        R.id.in_progress_tick_12, R.id.in_progress_tick_13, R.id.in_progress_tick_14,
        R.id.in_progress_tick_15, R.id.in_progress_tick_16, R.id.in_progress_tick_17,
        R.id.in_progress_tick_18, R.id.in_progress_tick_19, R.id.in_progress_tick_20,
        R.id.in_progress_tick_21, R.id.in_progress_tick_22, R.id.in_progress_tick_23
    };

    private Integer[] _inProgressCounts;
    private ViewGroup _view;
    private ViewGroup _parentView;

    private InProgressWidget(final ViewGroup view) {
        _view = view;
    }

    private void _updateView() {
        if (_inProgressCounts == null || _inProgressCounts.length != 24) {
            _view.setVisibility(View.GONE);
            return;
        }

        Integer totalInProgress = 0;
        for (final Integer inProgressCount : _inProgressCounts) {
            totalInProgress += inProgressCount;
        }

        Integer i = 0;
        for (final Integer inProgressCount : _inProgressCounts) {
            final Integer layoutId = _TICK_VIEWS[i];

            final View inProgressView = _view.findViewById(layoutId);

            final Integer backgroundColor;
            if (inProgressCount > 0) {
                final Integer luminosity = (55 + (200 * inProgressCount / totalInProgress));
                backgroundColor = Color.rgb(luminosity, luminosity, luminosity);
            }
            else {
                backgroundColor = Color.parseColor("#000000");
            }

            inProgressView.setBackgroundColor(backgroundColor);

            i += 1;
        }

        if (totalInProgress == 0) {
            _view.setVisibility(View.GONE);
        }
        else {
            _view.setVisibility(View.VISIBLE);
        }
    }

    public void attachToView(final ViewGroup viewGroup) {
        if (_parentView != null) {
            _parentView.removeView(_view);
        }
        _parentView = viewGroup;
        _parentView.addView(_view);

        _updateView();
    }

    public void removeFromView() {
        if (_parentView != null) {
            _parentView.removeView(_view);
        }
    }

    public void setInProgress(final Integer[] inProgressCounts) {
        _inProgressCounts = inProgressCounts;
        _updateView();
    }
}

