package com.softwareverde.utopia.parser;

import java.util.Iterator;

public interface HtmlParser {
    interface Document {
        Elements select(final String selector);

        String getText();
    }

    interface Element {
        Elements select(final String selector);

        String getText();

        String getTextWithNewlines();

        String getAttributeValue(final String attributeName);

        Boolean hasClass(final String className);

        Element removeClass(final String className);

        String getClassNames();

        String getHtml();
    }

    interface Elements extends Iterable<Element> {
        @Override
        Iterator<Element> iterator();

        Elements select(final String selector);

        String getText();

        String getTextWithNewlines();

        Integer getCount();

        String getValue();

        Element get(final Integer index);

        Elements eq(final Integer index);

        Element getFirst();

        Element getLast();

        String getAttributeValue(final String attributeName);
    }

    Document parse(final String html);
    Element createEmptyElement();
}