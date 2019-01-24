package com.softwareverde.utopia;

import android.util.Base64;

public class Settings {
    private static final String _googleApiKey = "fXh7cX1fd3l6XiozKy8sAX8ydAEDEHx+dnd6d3JjCXF9eHtxd1J9dHloBAMxdT8KCQ8VJjN3Em1nYFtAXH98alNAAwF4VEFvCHI5FzE2Jw4hdCAlKgwAYFt1QVJxcH1gdmYHaXJBf1tXciAuC3YsFg4WCTJ3K3hbfQF8bH1rS3RGCWh4eXNGWU8WDyk6IiQldjIQKw8HQWF+WgcNRAAaSFFXfXplRUFtbl0sLxRvdR4hEyELCXlPc0UAbFBZRnZhW3B0VQBvRlB8bxAncG92NSwvKwgrNg8LYmNzckoDaAd9aER/cEVEcHRdCDhwNgIUEisNKQ4XW2FwQmZafQNrWwJDRVcEeW51WlErdjkmNHB+P3Z1JSMLYnBuRwMYcH5+WFNVQXZlAUBKSAptInJ3NCQ/CzIyDXVtHGNYAXBnHnNISQJDXlx0GBdALRRwPBVwDCknKjIqaEhScwV6RFZFAkpEUUtxckBecnI4MzMgLR8cBmskIXJWVgVOXnZyUAdFfUZ7d3Vkd3U=";

    private static final String _host = "utopia-game.com";
    private static final String _loginUrl = "https://utopia-game.com/shared/login/";
    private static final String _throneUrl = "https://utopia-game.com/wol/game/throne/";
    private static final String _newsUrl = "https://utopia-game.com/wol/game/province_news/";
    private static final String _kingdomNewsUrl = "https://utopia-game.com/wol/game/kingdom_news/";
    private static final String _buildingsUrl = "https://utopia-game.com/wol/game/council_internal/";
    private static final String _trainArmyUrl = "https://utopia-game.com/wol/game/train_army/";
    private static final String _militarySettingsUrl = "https://utopia-game.com/wol/game/train_army/";
    private static final String _militaryUrl = "https://utopia-game.com/wol/game/council_military/";
    private static final String _defensiveSpellsUrl = "https://utopia-game.com/wol/game/enchantment/";
    private static final String _offensiveSpellsUrl = "https://utopia-game.com/wol/game/sorcery/";
    private static final String _activeSpellsUrl = "https://utopia-game.com/wol/game/council_spells/";
    private static final String _stateUrl = "https://utopia-game.com/wol/game/council_state/";
    private static final String _kingdomUrl = "https://utopia-game.com/wol/game/kingdom_details/";
    private static final String _constructBuildingUrl = "https://utopia-game.com/wol/game/build/";
    private static final String _razeBuildingUrl = "https://utopia-game.com/wol/game/raze/";
    private static final String _setKingdomUrl = "https://utopia-game.com/wol/game/change_kingdom/";
    private static final String _thieveryUrl = "https://utopia-game.com/wol/game/thievery/";
    private static final String _kingdomIntelUrl = "https://utopia-game.com/wol/game/kingdom_intel/";
    private static final String _attackUrl = "https://utopia-game.com/wol/game/send_armies/";
    private static final String _calculateOffenseUrl = "https://utopia-game.com/wol/game/send_armies/calculate_offensive_points/";
    private static final String _fundDragonUrl = "https://utopia-game.com/wol/game/fund_dragon/";
    private static final String _attackDragonUrl = "https://utopia-game.com/wol/game/attack_dragon/";
    private static final String _releaseArmyUrl = "https://utopia-game.com/wol/game/release_army/";
    private static final String _aidUrl = "https://utopia-game.com/wol/game/aid/";
    private static final String _explorationUrl = "https://utopia-game.com/wol/game/explore/";
    private static final String _scienceUrl = "https://utopia-game.com/wol/game/science/";
    private static final String _scienceCouncilUrl = "https://utopia-game.com/wol/game/council_science/";

    private static final String _registrationUrl = "https://utopia-game.com/shared/signup/";
    private static final String _forgotPasswordUrl = "https://utopia-game.com/shared/password_forgotten/";

    private static final String _warForumUrl = "https://utopia-game.com/wol/war_forum/topics/";
    private static final String _warForumPostUrl = "https://utopia-game.com/wol/war_forum/posts/";
    private static final String _forumUrl = "https://utopia-game.com/wol/kingdom_forum/topics/";
    private static final String _forumPostUrl = "https://utopia-game.com/wol/kingdom_forum/posts/";
    private static final String _privateMessagesUrl = "https://utopia-game.com/wol/mail/inbox/";
    private static final String _readPrivateMessageUrl = "https://utopia-game.com/wol/mail/view/";
    private static final String _replyPrivateMessageUrl = "https://utopia-game.com/wol/mail/reply/";
    private static final String _deletePrivateMessageUrl = "https://utopia-game.com/wol/mail/delete/";
    private static final String _composePrivateMessageUrl = "https://utopia-game.com/wol/mail/compose/";

    private static final String _createProvinceUrl = "https://utopia-game.com/wol/chooser/create/";

    private static final String _sendProvinceTagUrl = "https://utopia.softwareverde.com/v1/tag-province/";
    private static final String _provinceTagsUrl = "https://utopia.softwareverde.com/v1/province-tags/";
    private static final String _logUnknownNewsUrl = "https://utopia.softwareverde.com/log/news/";
    private static final String _apnsRegistrationUrl = "https://utopia.softwareverde.com/v1/notifications/apns/register/";

    private static final String _submitVerdeIntelUrl = "https://utopia.softwareverde.com/v1/intel/put/";
    private static final String _verdeProvinceIntelUrl = "https://utopia.softwareverde.com/v1/intel/get/province/";
    private static final String _verdeAvailableIntelUrl = "https://utopia.softwareverde.com/v1/intel/get/kingdom/available-intel/";


    public static String getHost() { return _host; }
    public static String getLoginUrl() { return _loginUrl; }
    public static String getThroneUrl() { return _throneUrl; }
    public static String getNewsUrl() { return _newsUrl; }
    public static String getNewsUrl(Integer month, Integer year) { return _newsUrl + year +"/"+ month +"/"; }
    public static String getKingdomNewsUrl() { return _kingdomNewsUrl; }
    public static String getKingdomNewsUrl(Integer month, Integer year) { return _kingdomNewsUrl + year +"/"+ month +"/"; }
    public static String getBuildingsUrl() { return _buildingsUrl; }
    public static String getTrainArmyUrl() { return _trainArmyUrl; }
    public static String getMilitarySettingsUrl() { return _militarySettingsUrl; }
    public static String getMilitaryUrl() { return _militaryUrl; }
    public static String getDefensiveSpellsUrl() { return _defensiveSpellsUrl; }
    public static String getOffensiveSpellsUrl() { return _offensiveSpellsUrl; }
    public static String getActiveSpellsUrl() { return _activeSpellsUrl; }
    public static String getStateUrl() { return _stateUrl; }
    public static String getKingdomUrl() { return _kingdomUrl; }
    public static String getConstructBuildingUrl() { return _constructBuildingUrl; }
    public static String getRazeBuildingUrl() { return _razeBuildingUrl; }
    public static String getSetKingdomUrl() { return _setKingdomUrl; }
    public static String getThieveryUrl() { return _thieveryUrl; }
    public static String getKingdomIntelUrl(Kingdom.Identifier kingdomIdentifier) {
        return _kingdomIntelUrl + kingdomIdentifier.getKingdomId() +"/"+ kingdomIdentifier.getIslandId() +"/";
    }
    public static String getAttackUrl() { return _attackUrl; }
    public static String getCalculateOffenseUrl() { return _calculateOffenseUrl; }
    public static String getFundDragonUrl() { return _fundDragonUrl; }
    public static String getAttackDragonUrl() { return _attackDragonUrl; }
    public static String getReleaseArmyUrl() { return _releaseArmyUrl; }
    public static String getAidUrl() { return _aidUrl; }
    public static String getExplorationUrl() { return _explorationUrl; }
    public static String getScienceUrl() { return _scienceUrl; }
    public static String getScienceCouncilUrl() { return _scienceCouncilUrl; }

    public static String getCreateProvinceUrl() { return _createProvinceUrl; }
    public static String getRegistrationUrl() { return _registrationUrl; }
    public static String getForgottenPasswordUrl() { return _forgotPasswordUrl; }

    public static String getWarForumUrl() { return _warForumUrl; }
    public static String getWarForumPostUrl(Integer postId, Integer pageNumber) {
        String url = _warForumPostUrl + postId.toString();
        if (pageNumber != null) {
            url += "?page="+ pageNumber.toString();
        }

        return url;
    }

    public static String getForumUrl() { return _forumUrl; }
    public static String getForumPostUrl(Integer postId, Integer pageNumber) {
        String url = _forumPostUrl + postId.toString();
        if (pageNumber != null) {
            url += "?page="+ pageNumber.toString();
        }

        return url;
    }

    public static String getPrivateMessagesUrl() { return _privateMessagesUrl; }
    public static String getReadPrivateMessageUrl(Integer messageId) {
        return _readPrivateMessageUrl + messageId.toString() +"/";
    }
    public static String getReplyPrivateMessageUrl(Integer messageId) {
        return _replyPrivateMessageUrl + messageId.toString() +"/";
    }
    public static String getDeletePrivateMessageUrl(Integer messageId) {
        return _deletePrivateMessageUrl + messageId.toString() +"/";
    }
    public static String getComposePrivateMessageUrl() { return _composePrivateMessageUrl; }


    public static String getGoogleApiKey() {
        String ansiTestKey = "0123456789ABCDEFFEDCBA9876543210";
        byte[] rawEncodedGoogleApiKey = Base64.decode(_googleApiKey, Base64.DEFAULT);
        byte[] byteArray = new byte[rawEncodedGoogleApiKey.length];
        for (int i=0; i<rawEncodedGoogleApiKey.length; ++i) {
            char ansiChar = ansiTestKey.charAt(i % ansiTestKey.length());
            byteArray[i] = Byte.valueOf((byte) (ansiChar ^ (char) rawEncodedGoogleApiKey[i]));
        }

        return new String(byteArray);
    }

    public static String getSendProvinceTagUrl() { return _sendProvinceTagUrl; }
    public static String getProvinceTagsUrl() { return _provinceTagsUrl; }
    public static String getUnknownNewsLogUrl() { return _logUnknownNewsUrl; }
    public static String getApnsRegistrationUrl() { return _apnsRegistrationUrl; }

    public static String getSubmitVerdeIntelUrl() { return _submitVerdeIntelUrl; }
    public static String getVerdeProvinceIntelUrl() { return _verdeProvinceIntelUrl; }
    public static String getVerdeAvailableIntelUrl() { return _verdeAvailableIntelUrl; }

    public static Boolean isChatServiceEnabled() { return false; }
}
