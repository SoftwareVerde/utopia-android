package com.softwareverde.utopia.config;

import android.content.Context;

import com.softwareverde.utopia.AndroidVibrator;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.UtopiaUtil;
import com.softwareverde.utopia.database.AndroidKeyValueStore;
import com.softwareverde.utopia.database.AndroidSqliteDatabase;
import com.softwareverde.utopia.database.UtopiaDatabaseV1;
import com.softwareverde.utopia.parser.HtmlParser;
import com.softwareverde.utopia.parser.JsoupHtmlParser;
import com.softwareverde.utopia.parser.UtopiaParser;
import com.softwareverde.utopia.ui.ProvinceTagNotificationMaker;
import com.softwareverde.utopia.util.android.AndroidBuildVersion;

public class ServiceBuildConfiguration implements BuildConfiguration {

    private void _setUtopiaUtilDependencies(final Context applicationContext) {
        final HtmlParser htmlParser = new JsoupHtmlParser();

        final UtopiaUtil.Dependencies utopiaUtilDependencies = new UtopiaUtil.Dependencies();
        utopiaUtilDependencies.setBuildVersion(new AndroidBuildVersion(applicationContext));
        utopiaUtilDependencies.setHtmlParser(htmlParser);
        utopiaUtilDependencies.setUtopiaParser(new UtopiaParser(htmlParser));
        UtopiaUtil.setDependencies(utopiaUtilDependencies);
    }

    private void _setSessionDependencies(final Context applicationContext) {
        final Session.Dependencies sessionDependencies = new Session.Dependencies();
        sessionDependencies.setKeyValueStore(new AndroidKeyValueStore(applicationContext, AndroidKeyValueStore.Stores.SESSION));
        sessionDependencies.setUtopiaDatabase(new UtopiaDatabaseV1(new AndroidSqliteDatabase(applicationContext, UtopiaDatabaseV1.DATABASE_NAME, UtopiaDatabaseV1.DATABASE_VERSION)));
        sessionDependencies.setIntelSyncFactory(null);
        sessionDependencies.setNotificationMaker(new ProvinceTagNotificationMaker(applicationContext));
        sessionDependencies.setVibrator(new AndroidVibrator(applicationContext));
        Session.setDependencies(sessionDependencies);
    }

    @Override
    public void configureDependencies(final Context context) {
        final Context applicationContext = context.getApplicationContext();

        _setUtopiaUtilDependencies(applicationContext);
        _setSessionDependencies(applicationContext);
    }
}