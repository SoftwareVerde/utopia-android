package com.softwareverde.utopia;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.database.AndroidKeyValueStore;
import com.softwareverde.utopia.database.KeyValueStore;
import com.softwareverde.utopia.ui.ChevronAnimation;

import java.util.ArrayList;
import java.util.List;

public class Tutorial {
    private static String getTutorialCompletedKey(final String tutorialTitle) {
        return tutorialTitle + "_TUTORIAL_COMPLETED_KEY";
    }

    private final List<Integer> _tutorialItems = new ArrayList<Integer>();
    private final List<String> _tutorialItemTexts = new ArrayList<String>();
    private Integer _currentTutorialItemIndex = 0;

    private final KeyValueStore _keyValueStore;
    private final String _tutorialTitle;
    private final Activity _activity;
    private final View _view;
    private Runnable _onCompleteCallback = null;
    private ChevronAnimation[] _chevronAnimations = new ChevronAnimation[2];

    private final Integer _sliderWidth;
    private final Integer _sliderHeight;
    private final Integer _sliderButtonWidth;
    private final Integer _sliderButtonHeight;
    private final Integer _sliderHeightPlusMargin;

    private void _finishTutorial() {
        _hideTutorial();
        _markCompleted(true);

        _keyValueStore.putString(Tutorial.getTutorialCompletedKey(_tutorialTitle), "1");

        (new Thread(new Runnable() {
            @Override
            public void run() {
                for (final ChevronAnimation chevronAnimation : _chevronAnimations) {
                    chevronAnimation.stop();
                }
            }
        })).start();

        if (_onCompleteCallback != null) {
            (new Thread(_onCompleteCallback)).start();
        }
    }

    private void _markCompleted(final Boolean isComplete) {
        _keyValueStore.putString(Tutorial.getTutorialCompletedKey(_tutorialTitle), (isComplete ? "1" : "0"));
    }

    private void _showCurrentTutorialItem() {
        _showTutorialForItem(_view.findViewById(_tutorialItems.get(_currentTutorialItemIndex)), _tutorialItemTexts.get(_currentTutorialItemIndex));
    }

    private void _showTutorialForItem(final View focusedItem, final String itemText) {
        final RelativeLayout tutorialBackground = (RelativeLayout) _view.findViewById(R.id.tutorial_container);
        tutorialBackground.setVisibility(View.VISIBLE);
        tutorialBackground.removeAllViews();

        tutorialBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nothing.
            }
        });

        final Bitmap bitmap = AndroidUtil.viewToBitmap(focusedItem);
        final ImageView imageView = new ImageView(_activity);
        imageView.setImageBitmap(bitmap);

        final TextView textView = new TextView(_activity);
        textView.setText(itemText);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setWidth(_view.getWidth() * 3 / 4);
        textView.setX(_view.getWidth() / 4 / 2);

        final Integer viewX;
        final Integer viewY;
        {
            final int[] position = new int[2];
            focusedItem.getLocationOnScreen(position);

            final int[] parentPosition = new int[2];
            _view.getLocationOnScreen(parentPosition);

            final Integer x = position[0] - parentPosition[0];
            final Integer y = position[1] - parentPosition[1];

            viewX = (x < 0 ? 0 : x);
            viewY = (y < 0 ? 0 : y);
        }

        imageView.setX(viewX);
        imageView.setY(viewY);

        final Integer viewHeight = bitmap.getHeight();

        final Integer textViewHeight = AndroidUtil.calculateTextViewHeight(textView, _activity);
        final Integer textViewHeightPlusMargin = textViewHeight + AndroidUtil.dpToPixels(10, _activity);

        Integer textViewY = viewY + viewHeight + AndroidUtil.dpToPixels(20, _activity);
        textView.setY(textViewY);
        if (textViewY + AndroidUtil.dpToPixels(_sliderHeightPlusMargin, _activity) > _view.getHeight()) {
            textViewY = viewY - textViewHeightPlusMargin;
        }
        textView.setY(textViewY);

        final Integer sliderY;
        if (Math.max(textViewY + textViewHeightPlusMargin, viewY + viewHeight) + _sliderHeightPlusMargin > _view.getHeight()) {
            sliderY = Math.min(viewY, textViewY) - _sliderHeightPlusMargin;
        }
        else {
            sliderY = Math.max(textViewY + textViewHeightPlusMargin, viewY + viewHeight) + AndroidUtil.dpToPixels(25, _activity);
        }

        tutorialBackground.addView(imageView);
        tutorialBackground.addView(textView);

        imageView.setBackgroundResource(R.drawable.edit_text_background); // Particularly necessary for resources...

        final Animation fadeInAnimation = AnimationUtils.loadAnimation(_activity, android.R.anim.fade_in);
        fadeInAnimation.setDuration(750);
        imageView.setAnimation(fadeInAnimation);
        textView.setAnimation(fadeInAnimation);

        final RelativeLayout sliderLayout = new RelativeLayout(_activity);
        sliderLayout.setLayoutParams(new RelativeLayout.LayoutParams(_sliderWidth, _sliderHeight));
        sliderLayout.setBackgroundResource(R.drawable.slider_background);
        sliderLayout.setX(_view.getWidth() / 2 - _sliderWidth / 2);
        sliderLayout.setY(sliderY);

        for (final ChevronAnimation chevronAnimation : _chevronAnimations) {
            final ViewGroup chevronContainer = chevronAnimation.getContainerView();
            final ViewGroup chevronContainerParent = ((ViewGroup) chevronContainer.getParent());
            if (chevronContainerParent != null) { chevronContainerParent.removeView(chevronContainer); }
            sliderLayout.addView(chevronContainer);

            chevronAnimation.show();
            if (_currentTutorialItemIndex == 0) {
                if (chevronAnimation.getDirection() == ChevronAnimation.Direction.LEFT) {
                    chevronAnimation.hide();
                }
            }
        }

        final Integer initialButtonX = _sliderWidth / 2 - _sliderButtonWidth / 2;
        final ImageView sliderButton = new ImageView(_activity);
        sliderButton.setLayoutParams(new ViewGroup.LayoutParams(_sliderButtonWidth, _sliderButtonHeight));
        sliderButton.setImageResource(R.drawable.slider_button);
        sliderButton.setScaleType(ImageView.ScaleType.FIT_XY);
        sliderButton.setX(initialButtonX);
        sliderButton.setY(0);

        sliderLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            synchronized public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sliderButton.animate().translationX(initialButtonX).start();
                    return true;
                }

                final Float slideThreshold = 0.9F;
                final Integer slideThresholdMinX = (int) (_sliderWidth * (1.0 - slideThreshold));
                final Integer slideThresholdMaxX = (int) (_sliderWidth * slideThreshold - _sliderButtonWidth);

                final Integer minX = 0;
                final Integer maxX = _sliderWidth - _sliderButtonWidth;

                final Integer x = (int) motionEvent.getAxisValue(MotionEvent.AXIS_X) - _sliderButtonWidth / 2;

                final Integer xPosition = (x < minX ? minX : (x > maxX ? maxX : x));

                sliderButton.setX(xPosition);

                if (xPosition >= slideThresholdMaxX) {
                    sliderLayout.setOnTouchListener(null);
                    _currentTutorialItemIndex += 1;

                    if (_currentTutorialItemIndex >= _tutorialItems.size()) {
                        _finishTutorial();
                    }
                    else {
                        _showCurrentTutorialItem();
                    }
                }
                else if (xPosition <= slideThresholdMinX) {
                    if (_currentTutorialItemIndex > 0) {
                        sliderLayout.setOnTouchListener(null);
                        _currentTutorialItemIndex -= 1;

                        _showCurrentTutorialItem();
                    }
                }

                return true;
            }
        });
        sliderLayout.addView(sliderButton);
        tutorialBackground.addView(sliderLayout);
    }

    private void _hideTutorial() {
        final RelativeLayout tutorialBackground = (RelativeLayout) _view.findViewById(R.id.tutorial_container);
        tutorialBackground.removeAllViews();
        tutorialBackground.setVisibility(View.GONE);
    }

    public Tutorial(final String tutorialTitle, final View parentView, final Activity activity) {
        _tutorialTitle = tutorialTitle;

        _activity = activity;
        _view = parentView;

        _keyValueStore = new AndroidKeyValueStore(_activity, AndroidKeyValueStore.Stores.TUTORIALS);

        _sliderWidth = AndroidUtil.dpToPixels(200, _activity);
        _sliderHeight = AndroidUtil.dpToPixels(50, _activity);
        _sliderButtonWidth = AndroidUtil.dpToPixels(50, _activity);
        _sliderButtonHeight = AndroidUtil.dpToPixels(50, _activity);
        _sliderHeightPlusMargin = _sliderHeight + AndroidUtil.dpToPixels(25, _activity);

        for (Integer i=0; i<_chevronAnimations.length; ++i) {
            final ChevronAnimation.Direction direction = (i == 0 ? ChevronAnimation.Direction.RIGHT : ChevronAnimation.Direction.LEFT);

            final LinearLayout chevronLayout = new LinearLayout(_activity);
            chevronLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            chevronLayout.setOrientation(LinearLayout.VERTICAL);
            chevronLayout.setX((direction == ChevronAnimation.Direction.RIGHT ? _sliderWidth - AndroidUtil.dpToPixels(28, _activity) : AndroidUtil.dpToPixels(5, _activity)));
            chevronLayout.setY(_sliderHeight / 2 - AndroidUtil.dpToPixels(19, _activity) / 2);

            _chevronAnimations[i] = new ChevronAnimation(direction, chevronLayout, _activity);
        }
    }

    public void addView(final Integer viewId, final String text) {
        _tutorialItems.add(viewId);
        _tutorialItemTexts.add(text);
    }

    public void start() {
        _currentTutorialItemIndex = 0;

        for (final ChevronAnimation chevronAnimation : _chevronAnimations) {
            chevronAnimation.start();
        }

        _showCurrentTutorialItem();
    }

    public void markCompleted(final Boolean isComplete) {
        _markCompleted(isComplete);
    }

    public Boolean hasBeenCompleted() {
        return (Util.parseInt(_keyValueStore.getString(Tutorial.getTutorialCompletedKey(_tutorialTitle))) > 0);
    }

    public void setOnCompleteCallback(final Runnable callback) {
        _onCompleteCallback = callback;
    }
}
