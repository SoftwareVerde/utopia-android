package com.softwareverde.utopia;

import com.softwareverde.util.Util;

public class ProvinceCredentials {
    private String _username;
    private String _password;
    private String _provinceName;
    private String _kingdomId;
    private String _islandId;

    public void setCredentials(final String username, final String password) {
        _username = username;
        _password = password;
    }

    public void setProvince(final String provinceName, final Kingdom.Identifier kingdomIdentifier) {
        _provinceName = provinceName;
        _kingdomId = Util.coalesce(kingdomIdentifier.getKingdomId()).toString();
        _islandId = Util.coalesce(kingdomIdentifier.getIslandId()).toString();
    }

    public String getUsername() { return _username; }
    public String getPassword() { return _password; }
    public String getProvinceName() { return _provinceName; }
    public String getKingdomId() { return _kingdomId; }
    public String getIslandId() { return _islandId; }
}