package com.softwareverde.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import com.softwareverde.utopia.Util;

import java.io.InputStream;

public class GifView extends View {
    private InputStream _gifInputStream;
    private Movie _gifMovie;
    private Integer _movieWidth, _movieHeight;
    private Integer _movieDuration;
    private Long _movieStart = 0L;

    public GifView(Context context) {
        super(context);
        init(context, null);
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public GifView(Context context, AttributeSet attrs, Integer defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    public void init(Context context, Integer imageId) {
        setFocusable(true);
        if (imageId != null) {
            _gifInputStream = context.getResources().openRawResource(imageId);
            _gifMovie = Movie.decodeStream(_gifInputStream);
            _movieWidth = _gifMovie.width();
            _movieHeight = _gifMovie.height();
            _movieDuration =  _gifMovie.duration();
        }
    }

    public void setWidth(int width) {
        _movieWidth = width;
    }
    public void setHeight(int height) {
        _movieHeight = height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(com.softwareverde.utopia.Util.coalesce(_movieWidth, 0), com.softwareverde.utopia.Util.coalesce(_movieHeight, 0));
    }

    public int getMovieWidth() {
        return com.softwareverde.utopia.Util.coalesce(_movieWidth, 0);
    }

    public int getMovieHeight() {
        return com.softwareverde.utopia.Util.coalesce(_movieHeight, 0);
    }

    public long getMovieDuration() {
        return Util.coalesce(_movieDuration, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Long now = android.os.SystemClock.uptimeMillis();
        if (_movieStart == 0) {
            _movieStart = now;
        }

        if (_gifMovie != null) {

            canvas.scale((float) _movieWidth / _gifMovie.width(), (float) _movieHeight / _gifMovie.height());

            int duration = _gifMovie.duration();
            if (duration == 0) {
                duration = 1000;
            }

            int realTime = (int)((now - _movieStart) % duration);

            _gifMovie.setTime(realTime);

            _gifMovie.draw(canvas, 0, 0);
            invalidate();
        }
    }
}