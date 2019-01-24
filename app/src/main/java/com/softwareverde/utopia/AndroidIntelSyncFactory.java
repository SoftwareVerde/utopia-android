package com.softwareverde.utopia;

import android.content.Context;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.bundle.BundleFactory;
import com.softwareverde.utopia.intelsync.IntelSync;
import com.softwareverde.utopia.intelsync.IntelSyncFactory;
import com.softwareverde.utopia.intelsync.StingerUtil;
import com.softwareverde.utopia.intelsync.UmunkUtil;
import com.softwareverde.utopia.intelsync.UpoopuUtil;
import com.softwareverde.utopia.intelsync.VerdeIntelUtil;
import com.softwareverde.utopia.parser.JsoupHtmlParser;
import com.softwareverde.utopia.util.android.AndroidBase64Util;
import com.softwareverde.utopia.util.android.AndroidBuildVersion;
import com.softwareverde.utopia.util.android.AndroidJavaScriptExecutor;

public class AndroidIntelSyncFactory implements IntelSyncFactory {
    private final Context _applicationContext;

    public AndroidIntelSyncFactory(final Context context) {
        _applicationContext = context.getApplicationContext();
    }

    @Override
    public IntelSync createInstance(IntelSync.IntelSyncType intelSyncType) {
        switch (intelSyncType) {
            case UMUNK: {
                return new UmunkUtil();
            }

            case UPOOPU: {
                final UpoopuUtil.Dependencies upoopuDependencies = new UpoopuUtil.Dependencies();
                upoopuDependencies.setUpoopuLibrary(Util.streamToString(_applicationContext.getResources().openRawResource(R.raw.upoopu_lib)));
                upoopuDependencies.setJavaScriptExecutor(new AndroidJavaScriptExecutor(_applicationContext));
                upoopuDependencies.setBase64Util(new AndroidBase64Util());

                return new UpoopuUtil(upoopuDependencies);
            }
            case STINGER: {
                final StingerUtil.Dependencies stingerDependencies = new StingerUtil.Dependencies();
                stingerDependencies.setBuildVersion(new AndroidBuildVersion(_applicationContext));
                stingerDependencies.setHtmlParser(new JsoupHtmlParser());

                return new StingerUtil(stingerDependencies);
            }

            case VERDE: {
                final VerdeIntelUtil.Dependencies verdeDependencies = new VerdeIntelUtil.Dependencies();
                verdeDependencies.setBase64Util(new AndroidBase64Util());
                verdeDependencies.setBundleFactory(new BundleFactory());

                return new VerdeIntelUtil(verdeDependencies);
            }

            default: {
                return null;
            }
        }
    }
}
