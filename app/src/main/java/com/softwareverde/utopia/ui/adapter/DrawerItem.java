package com.softwareverde.utopia.ui.adapter;

public class DrawerItem {
    protected Integer _id;
    protected String _title;

    public DrawerItem(Integer id, String title) {
        _id = id;
        _title = title;
    }
    public Boolean isEnabled() {
        return _isEnabled();
    }
    protected Boolean _isEnabled() {
        return true;
    }

    public Integer getId() {
        return _id;
    }
    public String getTitle() {
        return _title;
    }
}