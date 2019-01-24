package com.softwareverde.utopia.ui;

import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softwareverde.utopia.HaltableThread;

public class ChevronAnimation {
    public enum Direction {
        LEFT, RIGHT
    }

    private final Integer brightColor = Color.parseColor("#FFFFFF");
    private final Integer darkColor = Color.parseColor("#AAAAAA");

    private final Integer _chevronCount = 3;
    private final Activity _activity;
    private final Direction _direction;
    private final ViewGroup _containerView;
    private final TextView[] _textViews;
    private final HaltableThread _thread;

    private void _createView() {
        final LinearLayout layout = new LinearLayout(_activity);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);

        for (Integer i=0; i<_chevronCount; ++i) {
            final TextView textView = new TextView(_activity);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setTextColor(brightColor);
            textView.setText(_direction == Direction.LEFT ? "<" : ">");
            textView.setPadding(0, 0, 2, 0);
            layout.addView(textView);

            _textViews[i] = textView;
        }

        _containerView.addView(layout);
    }

    public ChevronAnimation(final Direction direction, final ViewGroup containerView, final Activity activity) {
        _direction = direction;
        _containerView = containerView;
        _activity = activity;
        _textViews = new TextView[_chevronCount];

        _createView();

        _thread = new HaltableThread(new Runnable() {
            @Override
            public void run() {
                final Boolean isRight = _direction.equals(Direction.RIGHT);

                for (Integer i=0; i<_chevronCount; ++i) {
                    final Integer brightIndex = (isRight ? i : _chevronCount - i - 1);
                    _activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (Integer j = 0; j < _chevronCount; ++j) {
                                _textViews[j].setTextColor((brightIndex.equals(j) ? brightColor : darkColor));
                            }
                        }
                    });

                    final Integer sleepTime = (i+1 != _chevronCount ? 400 : 0);

                    try { Thread.sleep(sleepTime); } catch (Exception e) { }
                }
            }
        });
        _thread.setSleepTime(400L);
    }

    public void start() {
        _thread.start();
    }

    public void stop() {
        _thread.halt();
    }

    public void show() {
        _containerView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        _containerView.setVisibility(View.INVISIBLE);
    }

    public Direction getDirection() {
        return _direction;
    }

    public ViewGroup getContainerView() {
        return _containerView;
    }
}
