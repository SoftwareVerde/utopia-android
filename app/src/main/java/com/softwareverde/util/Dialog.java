package com.softwareverde.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

public class Dialog {
	private static Activity _currentActivity;           // Used for Pop-up Alerts
	private static ProgressDialog _progressDialog;      // Used for progress spinner
	private static String _progressDialogTitle = "";
	private static Boolean _is_init = false;

	public static Runnable buttonCallback = null;
	
	public static void setActivity(Activity context) {
		if (context != _currentActivity) {
			_currentActivity = context;
			_is_init = false;
			buttonCallback = null;
		}
	}

	private static void init() {
		if (! _currentActivity.isFinishing()) {
			_currentActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					_is_init = true;
				}
			});
		}
	}

	public static void alert(final String title, final String message, final Runnable callback) {
		if (_currentActivity == null) {
			System.out.println("ERROR: Invoking alert without a valid current activity.");
			callback.run();
			return;
		}

		buttonCallback = callback;
		
		if (! _currentActivity.isFinishing()) {
			_currentActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (!_is_init) init();

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(_currentActivity);
					alertBuilder
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(title)
						.setMessage(message)
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (callback != null) {
									callback.run();
								}
							}
						})
						.create()
						.show();
				}
			});
		}
	}

	public static void confirm(final String title, final String message, final Runnable onConfirm, final Runnable onDeny) {
        if (! _currentActivity.isFinishing()) {
            _currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!_is_init) init();
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(_currentActivity);
                    alertBuilder
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (onConfirm != null) {
                                    onConfirm.run();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (onDeny != null) {
                                    onDeny.run();
                                }
                            }
                        })
                        .show();
                }
            });
        }
    }
	
	public static void showProgress(final String title, final String message) {
		if (! _currentActivity.isFinishing()) {
			_currentActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
                    try {
                        if (_progressDialog != null) _progressDialog.dismiss();
                    } catch (Exception e) { }

					_progressDialog = new ProgressDialog(_currentActivity);
					_progressDialog.setTitle(title);
					_progressDialog.setMessage(message);
					_progressDialog.setCancelable(false);
					_progressDialog.setOnKeyListener(new OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                // _currentActivity.onBackPressed();
                                // _currentActivity.dispatchKeyEvent(event);
                                // dialog.dismiss();
                                return false;
                            }
                            return true;
                        }
                    });
					_progressDialog.show();
					_progressDialogTitle = title;
				}
			});
		}
	}
	public static void hideProgress() {
		hideProgress(null);
	}
	public static void hideProgress(final String onlyIfMatchTitle) {
		if (! _currentActivity.isFinishing()) {
			_currentActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (_progressDialog != null) {
						if (onlyIfMatchTitle == null || _progressDialogTitle.equals(onlyIfMatchTitle)) {
							_progressDialog.dismiss();
						}
					}
				}
			});
		}
	}
    public static Boolean isShowingProgress() {
        return Dialog.isShowingProgress(null);
    }
	public static Boolean isShowingProgress(String title) {
        if (_progressDialog == null) {
            return false;
        }

        return (_progressDialog.isShowing() && (title == null || _progressDialogTitle.equals(title)));
    }

	// It is necessary that to destroy the progress dialog before any activity pauses...
	public static void onPause() {
		if (_progressDialog != null) _progressDialog.dismiss();
		_progressDialog = null;
	}
}
