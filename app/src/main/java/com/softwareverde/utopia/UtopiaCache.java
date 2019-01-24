package com.softwareverde.utopia;

import com.softwareverde.util.WebRequest;

import java.util.HashMap;
import java.util.Map;

public class UtopiaCache {
    private final Long CACHE_TTL = 60L * 1000L;

    private class CachedWebRequest {
        public WebRequest webRequest;
        public Long executedTime;

        public CachedWebRequest(final Long timestamp, final WebRequest webRequest) {
            this.executedTime = timestamp;
            this.webRequest = webRequest;
        }
    }

    private class EmptyCachedWebRequest extends CachedWebRequest {
        public EmptyCachedWebRequest() {
            super(null, null);
        }
    }

    private int _calculateWebRequestHash(final WebRequest webRequest) {
        return webRequest.getUrl().hashCode(); // + webRequest.getGetParams().hashCode();
    }

    private Map<Long, WebRequest> _cachedWebRequests = new HashMap<Long, WebRequest>();

    private CachedWebRequest _getCachedWebRequest(final WebRequest webRequest) {
        final Long now = System.currentTimeMillis();

        final Integer webRequestHash = _calculateWebRequestHash(webRequest);

        for (final Long executedAtTime : _cachedWebRequests.keySet()) {
            final WebRequest cachedWebRequest = _cachedWebRequests.get(executedAtTime);
            final Integer cachedWebRequestHash = _calculateWebRequestHash(cachedWebRequest);

            if (now - executedAtTime > CACHE_TTL) { continue; }

            if (webRequestHash.equals(cachedWebRequestHash)) {
                return new CachedWebRequest(executedAtTime, cachedWebRequest);
            }
        }

        return new EmptyCachedWebRequest();
    }

    public Boolean contains(final WebRequest webRequest) {
        if (webRequest.getType() == WebRequest.RequestType.POST) { return false; }

        final CachedWebRequest cachedWebRequest = _getCachedWebRequest(webRequest);
        return (cachedWebRequest.webRequest != null);
    }

    public WebRequest get(final WebRequest webRequest) {
        return _getCachedWebRequest(webRequest).webRequest;
    }

    public void put(final WebRequest webRequest) {
        final Long now = System.currentTimeMillis();
        _cachedWebRequests.put(now, webRequest);
    }

    public void clear() {
        _cachedWebRequests.clear();
    }
}
