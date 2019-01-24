package com.softwareverde.utopia.util.android;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.softwareverde.utopia.util.BuildVersion;

public class AndroidBuildVersion implements BuildVersion {
    private Context _context;

    public AndroidBuildVersion(final Context context) {
        _context = context.getApplicationContext();
    }

    @Override
    public String getVersionName() {
        String version = "";
        try {
            final PackageInfo packageInfo = _context.getPackageManager().getPackageInfo(_context.getPackageName(), 0);
            version = packageInfo.versionName;
        }
        catch (Exception e) {
            System.out.println("Failed to obtain version name.");
        }
        return version;
    }

    @Override
    public Integer getVersionNumber() {
        Integer version = 0;
        try {
            final PackageInfo packageInfo = _context.getPackageManager().getPackageInfo(_context.getPackageName(), 0);
            version = packageInfo.versionCode;
        }
        catch (Exception e) {
            System.out.println("Failed to obtain version code.");
        }
        return version;
    }
}