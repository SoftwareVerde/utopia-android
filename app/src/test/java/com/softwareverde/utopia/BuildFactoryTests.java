package com.softwareverde.utopia;

import com.softwareverde.util.Json;
import com.softwareverde.util.Util;
import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.BundleFactory;
import com.softwareverde.utopia.parser.JsoupHtmlParser;
import com.softwareverde.utopia.parser.UtopiaParser;

import org.junit.Assert;
import org.junit.Test;

public class BuildFactoryTests {
    @Test
    public void testBuildFactoryRecreatesEmptyBundle() {
        // Setup
        final BundleFactory subject = new BundleFactory();

        final Bundle bundle = new Bundle();
        final Json jsonBundle = bundle.toJson();

        // Action
        final Bundle receivedBundle = subject.createBundle(jsonBundle);

        // Assert
        Assert.assertEquals(bundle, receivedBundle);
    }

    @Test
    public void testBuildFactoryRecreatesNonEmptyBundle() {
        // Setup
        final String throneData = Util.streamToString(this.getClass().getResourceAsStream("/throne.html"));
        final UtopiaParser utopiaParser = new UtopiaParser(new JsoupHtmlParser());

        final Bundle bundle = utopiaParser.parseThrone(throneData);;
        final BundleFactory subject = new BundleFactory();

        final Json jsonBundle = bundle.toJson();

        // Action
        final Bundle receivedBundle = subject.createBundle(jsonBundle);

        // Assert
        Assert.assertEquals(bundle, receivedBundle);
    }
}