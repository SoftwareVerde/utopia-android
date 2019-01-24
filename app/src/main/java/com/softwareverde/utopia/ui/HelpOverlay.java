package com.softwareverde.utopia.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.HaltableThread;

public class HelpOverlay {
    private Activity _activity;
    private View _view;

    private TextView _iconDescription;
    private HaltableThread _hideIconDescriptionThread;
    private Integer _offsetX = 50;
    private Integer _offsetY = 25;

    private void _createHideIconThread() {
        _hideIconDescriptionThread = new HaltableThread() {
            private Integer _runCount = 0;

            @Override
            public Boolean shouldContinue() {
                if (_iconDescription == null) { return false; }

                if (_runCount > 24) {
                    _activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final AlphaAnimation fadeOutAnimation = new AlphaAnimation(1, 0);
                            fadeOutAnimation.setInterpolator(new AccelerateInterpolator());
                            fadeOutAnimation.setDuration(750);
                            _iconDescription.setAnimation(fadeOutAnimation);
                            _iconDescription.setVisibility(View.GONE);
                        }
                    });

                    return false;
                }

                _runCount += 1;
                return true;
            }
        };
        _hideIconDescriptionThread.setSleepTime(50L);
    }

    public void show(final String helpMessage, final View describedView) {
        if (_hideIconDescriptionThread != null) {
            _hideIconDescriptionThread.halt();
            _hideIconDescriptionThread = null;
        }

        final int[] position = new int[2];
        describedView.getLocationOnScreen(position);

        final int[] parentPosition = new int[2];
        _view.getLocationOnScreen(parentPosition);

        final Integer x = position[0] - parentPosition[0] - AndroidUtil.dpToPixels(_offsetX, _activity);
        final Integer y = position[1] - parentPosition[1]  - AndroidUtil.dpToPixels(_offsetY, _activity);

        _iconDescription.setX(x < 0 ? 0 : x);
        _iconDescription.setY(y < 0 ? 0 : y);
        _iconDescription.setText(helpMessage);

        _iconDescription.setVisibility(View.VISIBLE);
        final AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setDuration(750);
        fadeInAnimation.setInterpolator(new DecelerateInterpolator());
        _iconDescription.setAnimation(fadeInAnimation);

        _createHideIconThread();
        _hideIconDescriptionThread.start();
    }

    public HelpOverlay(final Activity activity, final View rootView) {
        _activity = activity;
        _view = rootView;

        _iconDescription = new TextView(_activity);
        _iconDescription.setTextColor(Color.parseColor("#FFFFFF"));
        _iconDescription.setBackgroundColor(Color.parseColor("#AA000000"));
        _iconDescription.setTypeface(null, Typeface.ITALIC);
        _iconDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        _iconDescription.setVisibility(View.GONE);
        _iconDescription.setPadding(5, 5, 5, 5);

        ((ViewGroup) _view).addView(_iconDescription);
    }

    public void setDisplayOffset(final Integer x, final Integer y) {
        _offsetX = x;
        _offsetY = y;
    }
}
