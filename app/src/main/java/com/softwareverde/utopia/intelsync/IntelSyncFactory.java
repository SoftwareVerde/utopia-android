package com.softwareverde.utopia.intelsync;

public interface IntelSyncFactory {
    IntelSync createInstance(final IntelSync.IntelSyncType intelSyncType);
}
