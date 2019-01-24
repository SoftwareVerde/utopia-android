package com.softwareverde.utopia;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.utopia.ui.MainActivity;

public class AndroidUtil {
    public static final int READ_PHONE_STATE_PERMISSION = 1;
    private static final String _PERMISSION_ALERT_TEXT = "The Utopia App needs your phone's IMEI identifier to manage mandatory upgrades and usage statistics. The identifier is anonymous and is NOT your phone number; no user, or phone, data is associated with the identifier. This app does NOT access your phone number, nor does it attempt to use your phone-service in any way.";

    public static void closeKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        View focusHack = activity.findViewById(R.id.focus_hack);
        if (focusHack != null) {
            focusHack.requestFocus(); // Clear Focus Hack
            focusHack.clearFocus();
        }
    }
    public static void openKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.showSoftInput(view, 0);
    }

    public static Integer dpToPixels(Integer dp, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private static android.app.Dialog _loadingDialog;
    public static synchronized void showLoadingScreen(Context context) {
        if (_loadingDialog == null) {
            _loadingDialog = new android.app.Dialog(context);
            _loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            _loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            _loadingDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            _loadingDialog.setContentView(R.layout.loading_overlay);
            _loadingDialog.setCanceledOnTouchOutside(false);
        }
        else {
            if (_loadingDialog.isShowing()) {
                _loadingDialog.dismiss();
            }
        }

        _loadingDialog.show();
    }
    public static synchronized void hideLoadingScreen() {
        if (_loadingDialog != null) {
            _loadingDialog.dismiss();
            _loadingDialog = null;
        }
    }

    public static void setButtonPressedStyle(View view, Integer button, Boolean isPressed) {
        if (isPressed) {
            view.findViewById(button).setBackgroundColor(Color.parseColor("#202020"));
        }
        else {
            view.findViewById(button).setBackgroundResource(android.R.drawable.dialog_holo_dark_frame);
        }
    }

    public interface AndroidIdCallback {
        void run(String androidId);
    }

    private static void _promptPermission(final Activity context, final MainActivity.PermissionCallback permissionCallback) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            MainActivity.addPermissionCallback(MainActivity.PermissionType.READ_PHONE_STATE, permissionCallback);
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_PHONE_STATE)) {
                Dialog.setActivity(context);
                Dialog.alert(
                        "Phone State Permission",
                        AndroidUtil._PERMISSION_ALERT_TEXT,
                        new Runnable() {
                            @Override
                            public void run() {
                                ActivityCompat.requestPermissions(context, new String[]{ Manifest.permission.READ_PHONE_STATE }, AndroidUtil.READ_PHONE_STATE_PERMISSION);
                            }
                        }
                );
            }
            else {
                ActivityCompat.requestPermissions(context, new String[]{ Manifest.permission.READ_PHONE_STATE }, AndroidUtil.READ_PHONE_STATE_PERMISSION);
            }
        }
        else {
            permissionCallback.run(true);
        }
    }

    /*
        public static void getImei(final Activity context, final AndroidIdCallback callback) {
            final MainActivity.PermissionCallback permissionCallback = new MainActivity.PermissionCallback() {
                @Override
                public void run(Boolean wasGranted) {
                    String imei = null;

                    if (wasGranted) {
                        try {
                            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                            imei = telephonyManager.getDeviceId();
                        } catch (Exception exception) {
                            System.out.println("Failed to obtain Android ID.");
                        }
                    }

                    if (imei == null) {
                        imei = "";
                    }

                    if (callback != null) {
                        callback.run(imei);
                    }
                }
            };

            Util._promptPermission(context, permissionCallback);
        }
    */

    public static void getAndroidId(final Activity context, final AndroidIdCallback callback) {
        final MainActivity.PermissionCallback permissionCallback = new MainActivity.PermissionCallback() {
            @Override
            public void run(Boolean wasGranted) {
                String androidId = null;

                if (wasGranted) {
                    try {
                        androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    } catch (Exception exception) {
                        System.out.println("Failed to obtain Android ID.");
                    }
                }

                if (androidId == null) {
                    androidId = "";
                }

                if (callback != null) {
                    callback.run(androidId);
                }
            }
        };

        AndroidUtil._promptPermission(context, permissionCallback);
    }

    public static void toggleNavigationDrawer(final Activity activity) {
        final Integer gravity = Gravity.LEFT;

        final DrawerLayout drawerLayout = ((DrawerLayout) activity.findViewById(R.id.drawer_layout));
        if (drawerLayout.isDrawerOpen(gravity)) {
            drawerLayout.closeDrawer(gravity);
        }
        else {
            drawerLayout.openDrawer(gravity);
        }
    }

    public static void toggleActionBar(final Activity activity, final Boolean isVisible) {
        try {
            final View decorView = activity.getWindow().getDecorView();

            final Integer resId;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                resId = activity.getResources().getIdentifier("action_bar_container", "id", activity.getPackageName());
            }
            else {
                resId = Resources.getSystem().getIdentifier("action_bar_container", "id", "android");
            }

            if (resId != 0) {
                final View actionBarView = decorView.findViewById(resId);
                if (actionBarView != null) {
                    actionBarView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
                }
            }
        }
        catch (Exception e) { }
    }

    public static Integer calculateTextViewHeight(final TextView textView, final Activity activity) {
        final Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(size.x, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    public static Bitmap viewToBitmap(final View view) {
        final Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(returnedBitmap);
        final Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        }
        else {
            canvas.drawColor(Color.TRANSPARENT);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
}
