package com.softwareverde.utopia.parser;

import java.util.Iterator;

public class JsoupHtmlParser implements HtmlParser {
    public static String parseWithNewlines(final String html) {
        final org.jsoup.nodes.Document document = org.jsoup.Jsoup.parse(html);
        document.outputSettings(new org.jsoup.nodes.Document.OutputSettings().prettyPrint(false));
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");
        final String modifiedHtmlContent = document.html().replaceAll("\\\\n", "\n");
        return org.jsoup.parser.Parser.unescapeEntities(org.jsoup.Jsoup.clean(modifiedHtmlContent, "", org.jsoup.safety.Whitelist.none(), new org.jsoup.nodes.Document.OutputSettings().prettyPrint(false)), true);
    }

    public static class JsoupDocument implements Document {
        private org.jsoup.nodes.Document _document;
        public JsoupDocument(final org.jsoup.nodes.Document document) {
            _document = document;
        }

        @Override
        public Elements select(final String selector) {
            return new JsoupElements(_document.select(selector));
        }

        @Override
        public String getText() {
            return _document.text();
        }
    }

    public static class JsoupElement implements Element {
        private org.jsoup.nodes.Element _element;

        public JsoupElement(final org.jsoup.nodes.Element element) {
            _element = element;
        }

        @Override
        public Elements select(final String selector) {
            if (_element == null) { return new JsoupElements(null); }

            return new JsoupElements(_element.select(selector));
        }

        @Override
        public String getText() {
            if (_element == null) { return ""; }

            return _element.text();
        }

        @Override
        public String getTextWithNewlines() {
            if (_element == null) { return ""; }

            return JsoupHtmlParser.parseWithNewlines(_element.html());
        }


        @Override
        public String getAttributeValue(final String attributeName) {
            if (_element == null) { return ""; }

            return _element.attr(attributeName);
        }

        @Override
        public Boolean hasClass(final String className) {
            return _element.hasClass(className);
        }

        @Override
        public Element removeClass(final String className) {
            _element.removeClass(className);
            return this;
        }

        @Override
        public String getClassNames() {
            if (_element == null) { return ""; }

            return _element.className();
        }

        @Override
        public String getHtml() {
            if (_element == null) { return ""; }

            return _element.toString();
        }
    }

    public static class JsoupElements implements Elements {
        private org.jsoup.select.Elements _elements;

        private class ElementsIterator implements Iterator<Element> {
            private final Iterator<org.jsoup.nodes.Element> _iterator = _elements.iterator();

            public ElementsIterator() { }

            public boolean hasNext() {
                return _iterator.hasNext();
            }

            public JsoupElement next() {
                return new JsoupElement(_iterator.next());
            }

            public void remove() {
                _iterator.remove();
            }
        }

        public JsoupElements(final org.jsoup.select.Elements elements) {
            _elements = elements;
        }

        @Override
        public Iterator<Element> iterator() {
            return new ElementsIterator();
        }

        @Override
        public Elements select(final String selector) {
            return new JsoupElements(_elements.select(selector));
        }

        @Override
        public String getText() {
            return _elements.text();
        }

        @Override
        public String getTextWithNewlines() {
            return JsoupHtmlParser.parseWithNewlines(_elements.html());
        }

        @Override
        public Integer getCount() {
            if (_elements == null) { return 0; }
            return _elements.size();
        }

        @Override
        public String getValue() {
            return _elements.val();
        }

        @Override
        public Element get(final Integer index) {
            return new JsoupElement(_elements.get(index));
        }

        @Override
        public Elements eq(final Integer index) {
            return new JsoupElements(_elements.eq(index));
        }

        @Override
        public Element getFirst() {
            return new JsoupElement(_elements.first());
        }

        @Override
        public Element getLast() {
            return new JsoupElement(_elements.last());
        }

        @Override
        public String getAttributeValue(final String attributeName) {
            return _elements.attr(attributeName);
        }
    }

    public JsoupHtmlParser() { }

    @Override
    public Document parse(final String html) {
        return new JsoupDocument(org.jsoup.Jsoup.parse(html));
    }

    @Override
    public Element createEmptyElement() {
        return new JsoupElement(new org.jsoup.nodes.Element(org.jsoup.parser.Tag.valueOf("div"), ""));
    }
}
