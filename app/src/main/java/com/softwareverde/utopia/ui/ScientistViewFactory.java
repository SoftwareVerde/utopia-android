package com.softwareverde.utopia.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Scientist;

public class ScientistViewFactory {
    private final LayoutInflater _layoutInflater;

    public ScientistViewFactory(final LayoutInflater layoutInflater) {
        _layoutInflater = layoutInflater;
    }

    public View createInstance(final Scientist scientist, final ViewGroup container) {
        final View scientistView = _layoutInflater.inflate(R.layout.scientist_item, container, false);
        ((TextView) scientistView.findViewById(R.id.scientist_name)).setText(scientist.getName());

        container.addView(scientistView);

        return scientistView;
    }
}