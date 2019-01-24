package com.softwareverde.utopia.intelsync;

import com.softwareverde.util.WebRequest;
import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.ThieveryOperation;
import com.softwareverde.utopia.parser.HtmlParser;
import com.softwareverde.utopia.util.BuildVersion;


public class StingerUtil implements IntelSync {
    private static final String _stingerDomain = "http://stingernet.ddns.net/";

    public static class Dependencies {
        private BuildVersion _buildVersion = null;
        private HtmlParser _htmlParser = null;

        public void setBuildVersion(final BuildVersion buildVersion) { _buildVersion = buildVersion; }
        public void setHtmlParser(final HtmlParser htmlParser) { _htmlParser = htmlParser; }
    }

    private static String _getDataType(IntelSync.IntelType intelType, String operationIdentifier) {
        switch (intelType) {
            case SELF_THRONE:
            case SELF_MILITARY:
            case SELF_SURVEY:
            case SELF_SCIENCE:
            case THIEVERY_OPERATION: {
                if (operationIdentifier != null) {
                    switch (operationIdentifier.toUpperCase()) {
                        case ThieveryOperation.Identifiers.INFILTRATE:
                        case ThieveryOperation.Identifiers.SPY_ON_THRONE:
                        case ThieveryOperation.Identifiers.SPY_ON_MILITARY:
                        case ThieveryOperation.Identifiers.SPY_ON_SCIENCE:
                        case ThieveryOperation.Identifiers.SURVEY: {
                            return "intel";
                        }
                        default: break;
                    }

                    return "op";
                }

                return "intel";
            }

            case COMBAT_SPELL:
            case SELF_SPELL: {
                return "op";
            }

            case ATTACK: { return "attack"; }

            case AID: { return"aid"; }

            case DRAGON: { return "dragon"; }

            case KINGDOM:
            case SELF_NEWS:
            case KINGDOM_NEWS: {
                return "intel";
            }

            default: return null;
        }
    }

    private static String _getSubtypeFromOperationIdentifier(String operationIdentifier) {
        switch (operationIdentifier.toUpperCase()) {
            case ThieveryOperation.Identifiers.SPY_ON_THRONE: return "sot";
            case ThieveryOperation.Identifiers.SPY_ON_MILITARY: return "som";
            case ThieveryOperation.Identifiers.SPY_ON_SCIENCE: return "sos";
            case ThieveryOperation.Identifiers.SURVEY: return "survey";
            default: return "";
        }
    }

    private HtmlParser _htmlParser;
    private BuildVersion _buildVersion;
    private String _stingerInstanceName;
    private String _username;
    private String _password;
    private ProvinceData _provinceData;
    private Boolean _isLoggedIn = false;

    public StingerUtil(final Dependencies dependencies) {
        _buildVersion = dependencies._buildVersion;
        _htmlParser = dependencies._htmlParser;
    }

    private String _getUrl() {
        return _stingerDomain + _stingerInstanceName +"/";
    }
    private String _getParseUrl() {
        return _getUrl() +"agent.php";
    }

    private void _onFailure(String message, Callback callback) {
        if (callback != null) {
            callback.run(new Response(false, message));
        }
    }

    @Override
    public void login(String username, String password, Callback callback) {
        _username = username;
        _password = password;

        _isLoggedIn = true;

        if (callback != null) {
            callback.run(new Response(true, null));
        }
    }

    @Override
    public Boolean canProcessIntel(String html, IntelSync.Extra extra) {
        if (extra != null && extra.intelType.equals(IntelType.SELF_ACTIVE_SPELLS)) {
            return false;
        }

        return true;
    }

    @Override
    public void submitIntel(final String html, final IntelSync.Extra extra, final Callback callback) {
        final String data = _htmlParser.parse(html).getText();

        final WebRequest postIntelRequest = new WebRequest();
        postIntelRequest.setUrl(_getParseUrl());
        postIntelRequest.setType(WebRequest.RequestType.POST);

        postIntelRequest.setGetParam("utopiamobileapp", "true");
        postIntelRequest.setGetParam("v", _buildVersion.getVersionNumber().toString());
        postIntelRequest.setGetParam("st", "");

        Boolean targetIsSelf = false;

        if (extra != null && extra.intelType != null) {
            postIntelRequest.setGetParam("t", StingerUtil._getDataType(extra.intelType, extra.operationIdentifier));

            if (extra.intelType.equals(IntelType.THIEVERY_OPERATION)) {
                postIntelRequest.setGetParam("st", StingerUtil._getSubtypeFromOperationIdentifier(extra.operationIdentifier));
            }
            else if (extra.intelType.equals(IntelType.COMBAT_SPELL)) {
                postIntelRequest.setGetParam("st", "combat");
            }
            else if (extra.intelType.equals(IntelType.KINGDOM)) {
                postIntelRequest.setGetParam("st", "kdinfo");
            }
            else if (extra.intelType.equals(IntelType.KINGDOM_NEWS)) {
                postIntelRequest.setGetParam("st", "kdnews");
            }
            else if (extra.intelType.equals(IntelType.SELF_NEWS)) {
                postIntelRequest.setGetParam("st", "self");
                targetIsSelf = true;
            }
            else if (extra.intelType.equals(IntelType.SELF_SPELL)) {
                postIntelRequest.setGetParam("st", "self");
                targetIsSelf = true;
            }
            else if (extra.intelType.equals(IntelType.SELF_THRONE)) {
                postIntelRequest.setGetParam("st", _getSubtypeFromOperationIdentifier(ThieveryOperation.Identifiers.SPY_ON_THRONE));
                targetIsSelf = true;
            }
            else if (extra.intelType.equals(IntelType.SELF_MILITARY)) {
                postIntelRequest.setGetParam("st", _getSubtypeFromOperationIdentifier(ThieveryOperation.Identifiers.SPY_ON_MILITARY));
                targetIsSelf = true;
            }
            else if (extra.intelType.equals(IntelType.SELF_SCIENCE)) {
                postIntelRequest.setGetParam("st", _getSubtypeFromOperationIdentifier(ThieveryOperation.Identifiers.SPY_ON_SCIENCE));
                targetIsSelf = true;
            }
            else if (extra.intelType.equals(IntelType.SELF_SURVEY)) {
                postIntelRequest.setGetParam("st", _getSubtypeFromOperationIdentifier(ThieveryOperation.Identifiers.SURVEY));
                targetIsSelf = true;
            }
            else if (extra.intelType.equals(IntelType.DRAGON)) {
                targetIsSelf = true;
            }
        }

        postIntelRequest.setPostParam("username", _username);
        postIntelRequest.setPostParam("password", _password);

        if (targetIsSelf) {
            if (extra.targetProvinceName == null) {
                extra.targetProvinceName = _provinceData.provinceName;
            }

            if (extra.targetKingdomId == null || extra.targetIslandId == null) {
                extra.targetKingdomId = _provinceData.kingdomId;
                extra.targetIslandId = _provinceData.islandId;
            }
        }

        if (extra != null && (extra.targetKingdomId != null || (extra.targetIslandId != null && extra.targetProvinceName != null))) {
            postIntelRequest.setPostParam("title", (extra.targetProvinceName != null ? extra.targetProvinceName : "") + (new Kingdom.Identifier(extra.targetKingdomId, extra.targetIslandId)));
        }

        postIntelRequest.setPostParam("data", data);

        if (extra != null && extra.intelType != null && extra.intelType.equals(IntelType.ATTACK)) {
            postIntelRequest.setPostParam("generals", ""+ extra.generalsSent);
            postIntelRequest.setPostParam("offense", ""+ extra.offenseSent); // TODO: Investigate why this is sometimes null...
        }

        postIntelRequest.setFollowsRedirects(true);
        postIntelRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();

                if (callback != null) {
                    final Boolean wasSuccess = (html.contains("+LOGIN") && ! html.contains("-0"));
                    callback.run(new Response(wasSuccess, html));
                }
            }
        });
    }

    @Override
    public Boolean isLoggedIn() {
        return _isLoggedIn;
    }

    @Override
    public void setProvinceData(ProvinceData provinceData) {
        _provinceData = provinceData;
    }

    @Override
    public void setSubdomain(String subdomain) {
        _stingerInstanceName = subdomain;
    }
}
