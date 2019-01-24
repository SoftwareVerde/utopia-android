package com.softwareverde.utopia;

import android.content.Context;

public class AndroidVibrator implements Vibrator {
    private final Context _context;

    public AndroidVibrator(final Context context) {
        _context = context;
    }

    @Override
    public void vibrate() {
        android.os.Vibrator vibrator = (android.os.Vibrator) _context.getSystemService(Context.VIBRATOR_SERVICE);
        long pattern[] = { 0, 200, 100, 300, 400 };
        vibrator.vibrate(pattern, -1);
    }
}