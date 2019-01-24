package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Science;
import com.softwareverde.utopia.Scientist;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.ui.ScientistViewFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScienceFragment extends Fragment {
    private Activity _activity;
    private Session _session;
    private View _view;
    private Province _province;
    private View _scienceContainerView;
    private ScientistViewFactory _scientistViewFactory;
    private List<Scientist> _scientists = new ArrayList<Scientist>();

    private Map<Science.Type, View> _scienceAreas = new HashMap<Science.Type, View>();
    private Map<Science.Type, View> _scienceBackgroundAreas = new HashMap<Science.Type, View>();
    private Map<Science.Type, List<ScientistView>> _scienceAreaAssignments = new HashMap<Science.Type, List<ScientistView>>();
    private Map<Science.Type, Integer> _scienceAreaBorderColors = new HashMap<Science.Type, Integer>();
    private Map<Science.Type, Integer> _scienceAreaBackgroundColors = new HashMap<Science.Type, Integer>();

    private final Object _isCurrentlyDraggingMutex = new Object();
    private Boolean _isCurrentlyDragging = false;

    public ScienceFragment() { }

    // public void setProvince(Province province) {
    //     _province = province;
    //
    //     _drawData();
    // }

    private class ScientistView {
        public View view;
        public Scientist scientist;
    }

    private Integer[] _getLocationOnScreen(final View view) {
        final int[] viewPosition = new int[2];
        view.getLocationOnScreen(viewPosition);

        final int[] parentPosition = new int[2];
        _scienceContainerView.getLocationOnScreen(parentPosition);

        return new Integer[]{
            viewPosition[0] - parentPosition[0],
            viewPosition[1] - parentPosition[1]
        };
    }

    private void _showLoadingScreen() {
        AndroidUtil.showLoadingScreen(_activity);
    }
    private void _hideLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.hideLoadingScreen();
            }
        });
    }

    private void _drawData() {
        if (_scienceContainerView == null) { return; }

        _layoutViews();

        _setScienceEffectText((TextView) _scienceAreas.get(Science.Type.ALCHEMY).findViewById(R.id.science_alchemy_text), Science.Type.ALCHEMY);
        _setScienceEffectText((TextView) _scienceAreas.get(Science.Type.TOOLS).findViewById(R.id.science_tools_text), Science.Type.TOOLS);
        _setScienceEffectText((TextView) _scienceAreas.get(Science.Type.HOUSING).findViewById(R.id.science_housing_text), Science.Type.HOUSING);
        _setScienceEffectText((TextView) _scienceAreas.get(Science.Type.FOOD).findViewById(R.id.science_food_text), Science.Type.FOOD);
        _setScienceEffectText((TextView) _scienceAreas.get(Science.Type.MILITARY).findViewById(R.id.science_military_text), Science.Type.MILITARY);
        _setScienceEffectText((TextView) _scienceAreas.get(Science.Type.CRIME).findViewById(R.id.science_crime_text), Science.Type.CRIME);
        _setScienceEffectText((TextView) _scienceAreas.get(Science.Type.CHANNELING).findViewById(R.id.science_channeling_text), Science.Type.CHANNELING);
    }

    private Boolean _isInsideView(final Float pointX, final Float pointY, final View view) {
        final Integer[] containerPosition = _getLocationOnScreen(view);

        final Integer viewX0 = containerPosition[0];
        final Integer viewY0 = containerPosition[1];

        final Integer viewX1 = viewX0 + view.getWidth();
        final Integer viewY1 = viewY0 + view.getHeight();

        return (pointX >= viewX0 && pointY >= viewY0 && pointX <= viewX1 && pointY <= viewY1);
    }

    private void _assignViewToArea(final ScientistView scientistView, final Science.Type scienceType) {
        // Remove existing assignment...
        for (final Science.Type assignmentKey : _scienceAreaAssignments.keySet()) {
            final List<ScientistView> assignedViews = _scienceAreaAssignments.get(assignmentKey);
            for (final ScientistView assignedView : assignedViews) {
                if (assignedView.view == scientistView.view) {
                    assignedViews.remove(assignedView);
                    break;
                }
            }
        }

        final View containerView = _scienceAreas.get(scienceType);
        final Integer[] containerPosition = _getLocationOnScreen(containerView);

        Integer itemsPerRow = containerView.getWidth() / (scientistView.view.getWidth() + AndroidUtil.dpToPixels(4, _activity));
        if (itemsPerRow == 0) { itemsPerRow = 1; }

        final List<ScientistView> viewsAssignedToArea = _scienceAreaAssignments.get(scienceType);
        final Integer x = AndroidUtil.dpToPixels(4, _activity) + containerPosition[0] + (viewsAssignedToArea.size() % itemsPerRow) * (scientistView.view.getWidth() + AndroidUtil.dpToPixels(2, _activity));
        final Integer y = AndroidUtil.dpToPixels(4, _activity) + containerPosition[1] + (viewsAssignedToArea.size() / itemsPerRow) * (scientistView.view.getHeight() + AndroidUtil.dpToPixels(2, _activity));

        scientistView.view.setX(x);
        scientistView.view.setY(y);
        scientistView.scientist.setAssignment(scienceType);
        viewsAssignedToArea.add(scientistView);
        _setScientistViewColor(scientistView, scienceType);
    }

    private Integer _calculateScienceContainerHeight(final Science.Type scienceType) {
        final ViewGroup containerView = (ViewGroup) _scienceAreas.get(scienceType);
        final List<ScientistView> assignedViews = _scienceAreaAssignments.get(scienceType);

        final Integer itemHeight = AndroidUtil.dpToPixels(35, _activity);
        final Integer itemsPerRow = containerView.getWidth() / (itemHeight + AndroidUtil.dpToPixels(4, _activity));

        Integer height = AndroidUtil.dpToPixels(70, _activity);

        if (itemsPerRow > 0) {
            final Integer rowCountRequired = ((assignedViews.size() - 1) / itemsPerRow) + 1;
            if (assignedViews.size() > 0 && rowCountRequired > 1) {
                height += (itemHeight + AndroidUtil.dpToPixels(1, _activity)) * (rowCountRequired - 1);
            }
        }
        return height;
    }

    private void _setViewHeight(final ViewGroup viewGroup, final Integer height) {
        final ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
        layoutParams.height = height;
        viewGroup.setLayoutParams(layoutParams);
    }

    private void _layoutViews() {
        { // Resize & Normalize Container Size
            final Science.Type[][] scienceTypeRows = new Science.Type[][]{
                new Science.Type[]{ Science.Type.ALCHEMY, Science.Type.TOOLS },
                new Science.Type[]{ Science.Type.HOUSING, Science.Type.FOOD },
                new Science.Type[]{ Science.Type.MILITARY, Science.Type.CRIME },
                new Science.Type[]{ Science.Type.CHANNELING }
            };
            for (final Science.Type[] scienceTypes : scienceTypeRows) {
                if (scienceTypes.length < 1) { continue; }
                if (scienceTypes.length < 2) {
                    // Adjust height for the odd-numbered science (i.e. Channeling)
                    final ViewGroup containerView = (ViewGroup) _scienceAreas.get(scienceTypes[0]);
                    _setViewHeight(containerView, _calculateScienceContainerHeight(scienceTypes[0]));
                    continue;
                }

                final ViewGroup containerView1 = (ViewGroup) _scienceAreas.get(scienceTypes[0]);
                final ViewGroup containerView2 = (ViewGroup) _scienceAreas.get(scienceTypes[1]);

                Integer maxScienceHeight = Math.max(_calculateScienceContainerHeight(scienceTypes[0]), _calculateScienceContainerHeight(scienceTypes[1]));

                _setViewHeight(containerView1, maxScienceHeight);
                _setViewHeight(containerView2, maxScienceHeight);
            }
        }

        { // Place Scientist Views
            for (final Science.Type scienceType : _scienceAreaAssignments.keySet()) {
                final List<ScientistView> assignedViews = _scienceAreaAssignments.get(scienceType);
                _scienceAreaAssignments.put(scienceType, new ArrayList<ScientistView>());
                for (final ScientistView scientistView : assignedViews) {
                    _assignViewToArea(scientistView, scienceType);
                }
            }
        }
    }

    private final Object _shouldScrollMutex = new Object();
    private Boolean _shouldScroll = true;
    private void _enableScrolling(final Boolean shouldScroll) {
        final ScrollView scrollView = (ScrollView) _view.findViewById(R.id.science_scrollview);
        scrollView.requestDisallowInterceptTouchEvent(! _shouldScroll);

        synchronized (_shouldScrollMutex) {
            _shouldScroll = shouldScroll;
        }
    }

    private ScientistView _getScientistView(final View view) {
        for (final Science.Type scienceType : _scienceAreaAssignments.keySet()) {
            for (final ScientistView scientistView : _scienceAreaAssignments.get(scienceType)) {
                if (view == scientistView.view) {
                    return scientistView;
                }
            }
        }
        return null;
    }

    @ColorInt
    private Integer _createHighlightColor(final Integer color) {
        final Float factor = 0.15f;
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

    private void _setZoneHighlighted(final Science.Type scienceType, final Boolean shouldBeHighlighted) {
        final View scienceArea = _scienceAreas.get(scienceType);
        final View scienceBackgroundArea = _scienceBackgroundAreas.get(scienceType);

        final Integer borderColor = _scienceAreaBorderColors.get(scienceType);
        final Integer backgroundColor = _scienceAreaBackgroundColors.get(scienceType);

        scienceArea.setBackgroundColor(shouldBeHighlighted ? _createHighlightColor(borderColor) : borderColor);
        scienceBackgroundArea.setBackgroundColor(shouldBeHighlighted ? _createHighlightColor(backgroundColor) : backgroundColor);
    }

    private final View.OnTouchListener _onScientistViewTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(final View view, final MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                synchronized (_isCurrentlyDraggingMutex) {
                    _isCurrentlyDragging = false;
                    _enableScrolling(true);
                }
                return true;
            }

            synchronized (_isCurrentlyDraggingMutex) {
                _isCurrentlyDragging = true;
            }

            final int[] parentPosition = new int[2];
            _scienceContainerView.getLocationOnScreen(parentPosition);

            final Float absoluteEventX = motionEvent.getRawX() - parentPosition[0];
            final Float absoluteEventY = motionEvent.getRawY() - parentPosition[1];

            final Float eventX = motionEvent.getAxisValue(MotionEvent.AXIS_X);
            final Float eventY = motionEvent.getAxisValue(MotionEvent.AXIS_Y);

            final Float originalX = view.getX() - view.getWidth()/2.0f;
            final Float originalY = view.getY() - view.getHeight()/2.0f;
            final Float newX = originalX + eventX;
            final Float newY = originalY + eventY;

            view.setX(newX);
            view.setY(newY);

            final Boolean eventIsComplete = (motionEvent.getAction() == MotionEvent.ACTION_UP);
            _enableScrolling(eventIsComplete);

            { // Highlight Dropzones
                for (final Science.Type scienceAreaType : _scienceAreas.keySet()) {
                    final View scienceArea = _scienceAreas.get(scienceAreaType);

                    final Boolean shouldBeHighlighted;
                    if (eventIsComplete) {
                        shouldBeHighlighted = false;
                    }
                    else {
                        shouldBeHighlighted = _isInsideView(absoluteEventX, absoluteEventY, scienceArea);
                    }

                    _setZoneHighlighted(scienceAreaType, shouldBeHighlighted);
                }
            }

            if (eventIsComplete) {
                synchronized (_isCurrentlyDraggingMutex) {
                    _isCurrentlyDragging = false;
                }

                for (final Science.Type scienceAreaType : _scienceAreas.keySet()) {
                    final View scienceArea = _scienceAreas.get(scienceAreaType);

                    if (_isInsideView(absoluteEventX, absoluteEventY, scienceArea)) {
                        final ScientistView scientistView = _getScientistView(view);

                        if (scientistView != null) {
                            _assignViewToArea(scientistView, scienceAreaType);
                            break;
                        }
                    }
                }

                _drawData();
            }

            return true;
        }
    };

    private void _setScientistViewColor(final ScientistView scientistView, final Science.Type containerType) {
        final Integer color;
        if (scientistView.scientist.getOriginalAssignment() != containerType) {
            color = Color.parseColor("#AA0000");
        }
        else {
            switch (scientistView.scientist.getLevel()) {
                case PROFESSOR:
                    color = Color.parseColor("#00AA00");
                    break;
                case GRADUATE:
                    color = Color.parseColor("#AAAA00");
                    break;
                default:
                    color = Color.parseColor("#AA0000");
                    break;
            }
        }
        scientistView.view.findViewById(R.id.scientist_item_border).setBackgroundColor(color);
    }

    private void _setScienceEffectText(final TextView textView, final Science.Type scienceType) {
        final String scienceEffect = _province.getScienceEffect(scienceType);
        final Integer originalCount = _province.getScientistCount(scienceType);
        final Integer newCount = _scienceAreaAssignments.get(scienceType).size();

        if (originalCount == null) {
            textView.setText(Science.getStringForType(scienceType));
            return;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Science.getStringForType(scienceType));
        stringBuilder.append(": ");
        stringBuilder.append(newCount);
        stringBuilder.append(" ");

        if (! originalCount.equals(newCount)) {
            stringBuilder.append("(");
            if (newCount > originalCount) {
                stringBuilder.append("+");
            }
            stringBuilder.append(newCount - originalCount);
            stringBuilder.append(") ");
        }
        stringBuilder.append("\n(");
        stringBuilder.append(scienceEffect);
        stringBuilder.append(")");

        textView.setText(stringBuilder.toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.science, container, false);
        _view = rootView;
        _scienceContainerView = _view.findViewById(R.id.science_container);

        final ScrollView scrollView = (ScrollView) _view.findViewById(R.id.science_scrollview);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                synchronized (_shouldScrollMutex) {
                    return (!_shouldScroll);
                }
            }
        });

        _scienceAreas.put(Science.Type.ALCHEMY,     _scienceContainerView.findViewById(R.id.science_alchemy_area));
        _scienceAreas.put(Science.Type.TOOLS,       _scienceContainerView.findViewById(R.id.science_tools_area));
        _scienceAreas.put(Science.Type.HOUSING,     _scienceContainerView.findViewById(R.id.science_housing_area));
        _scienceAreas.put(Science.Type.FOOD,        _scienceContainerView.findViewById(R.id.science_food_area));
        _scienceAreas.put(Science.Type.MILITARY,    _scienceContainerView.findViewById(R.id.science_military_area));
        _scienceAreas.put(Science.Type.CRIME,       _scienceContainerView.findViewById(R.id.science_crime_area));
        _scienceAreas.put(Science.Type.CHANNELING,  _scienceContainerView.findViewById(R.id.science_channeling_area));

        _scienceBackgroundAreas.put(Science.Type.ALCHEMY,     _scienceContainerView.findViewById(R.id.science_alchemy_background_area));
        _scienceBackgroundAreas.put(Science.Type.TOOLS,       _scienceContainerView.findViewById(R.id.science_tools_background_area));
        _scienceBackgroundAreas.put(Science.Type.HOUSING,     _scienceContainerView.findViewById(R.id.science_housing_background_area));
        _scienceBackgroundAreas.put(Science.Type.FOOD,        _scienceContainerView.findViewById(R.id.science_food_background_area));
        _scienceBackgroundAreas.put(Science.Type.MILITARY,    _scienceContainerView.findViewById(R.id.science_military_background_area));
        _scienceBackgroundAreas.put(Science.Type.CRIME,       _scienceContainerView.findViewById(R.id.science_crime_background_area));
        _scienceBackgroundAreas.put(Science.Type.CHANNELING,  _scienceContainerView.findViewById(R.id.science_channeling_background_area));

        _scienceAreaBorderColors.put(Science.Type.ALCHEMY,      Color.parseColor("#817900"));
        _scienceAreaBorderColors.put(Science.Type.TOOLS,        Color.parseColor("#606060"));
        _scienceAreaBorderColors.put(Science.Type.HOUSING,      Color.parseColor("#135269"));
        _scienceAreaBorderColors.put(Science.Type.FOOD,         Color.parseColor("#136917"));
        _scienceAreaBorderColors.put(Science.Type.MILITARY,     Color.parseColor("#690B0B"));
        _scienceAreaBorderColors.put(Science.Type.CRIME,        Color.parseColor("#BA6A00"));
        _scienceAreaBorderColors.put(Science.Type.CHANNELING,   Color.parseColor("#202050"));

        _scienceAreaBackgroundColors.put(Science.Type.ALCHEMY,      Color.parseColor("#2e2b01"));
        _scienceAreaBackgroundColors.put(Science.Type.TOOLS,        Color.parseColor("#202020"));
        _scienceAreaBackgroundColors.put(Science.Type.HOUSING,      Color.parseColor("#08242e"));
        _scienceAreaBackgroundColors.put(Science.Type.FOOD,         Color.parseColor("#0E3109"));
        _scienceAreaBackgroundColors.put(Science.Type.MILITARY,     Color.parseColor("#310202"));
        _scienceAreaBackgroundColors.put(Science.Type.CRIME,        Color.parseColor("#412501"));
        _scienceAreaBackgroundColors.put(Science.Type.CHANNELING,   Color.parseColor("#000020"));

        _scienceAreaAssignments.put(Science.Type.ALCHEMY,       new ArrayList<ScientistView>());
        _scienceAreaAssignments.put(Science.Type.TOOLS,         new ArrayList<ScientistView>());
        _scienceAreaAssignments.put(Science.Type.HOUSING,       new ArrayList<ScientistView>());
        _scienceAreaAssignments.put(Science.Type.FOOD,          new ArrayList<ScientistView>());
        _scienceAreaAssignments.put(Science.Type.MILITARY,      new ArrayList<ScientistView>());
        _scienceAreaAssignments.put(Science.Type.CRIME,         new ArrayList<ScientistView>());
        _scienceAreaAssignments.put(Science.Type.CHANNELING,    new ArrayList<ScientistView>());


        _view.findViewById(R.id.science_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _showLoadingScreen();

                _session.allocateScientists(_scientists, new Session.Callback() {
                    @Override
                    public void run(final Session.SessionResponse response) {
                        if (! response.getWasSuccess()) {
                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final TextView resultText = ((TextView) _view.findViewById(R.id.science_result_text));
                                    resultText.setText("Error allocating scientists:\n"+ response.getErrorMessage());
                                    _hideLoadingScreen();
                                }
                            });
                            return;
                        }

                        _session.downloadScience(new Session.Callback() {
                            @Override
                            public void run(Session.SessionResponse response) {
                                _activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final TextView resultText = ((TextView) _view.findViewById(R.id.science_result_text));
                                        resultText.setText("Scientists allocated.");

                                        _drawData();
                                        _hideLoadingScreen();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        _scienceContainerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                synchronized (_isCurrentlyDraggingMutex) {
                    if (! _isCurrentlyDragging) {
                        // NOTE: Mandate layoutRequest is appended to queue (view.post, not runOnUiThread).
                        _view.post(new Runnable() {
                            @Override
                            public void run() {
                                _layoutViews();
                            }
                        });
                    }
                }
            }
        });

        _drawData();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        _activity = activity;
        _session = Session.getInstance();
        _province = _session.getProvince();

        _scientistViewFactory = new ScientistViewFactory(_activity.getLayoutInflater());
        _scientists = new ArrayList<Scientist>();

        _showLoadingScreen();

        _session.downloadScience(new Session.Callback() {
            @Override
            public void run(final Session.SessionResponse response) {
                _scientists = _province.getScientists();

                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (final Scientist scientist : _scientists) {
                            final View view = _scientistViewFactory.createInstance(scientist, (ViewGroup) _scienceContainerView);
                            view.setOnTouchListener(_onScientistViewTouchListener);

                            final ScientistView scientistView = new ScientistView();
                            scientistView.view = view;
                            scientistView.scientist = scientist;

                            _assignViewToArea(scientistView, scientist.getAssignment());
                        }

                        _drawData();
                        _hideLoadingScreen();
                    }
                });
            }
        });
    }
}