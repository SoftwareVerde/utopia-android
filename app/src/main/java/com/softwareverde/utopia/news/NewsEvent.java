package com.softwareverde.utopia.news;

import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.Util;

import java.util.List;

public class NewsEvent {
    public enum EventType {
        THIEVERY_OPERATION, MAGIC_OP, ATTACK, MISC, UNKNOWN
    }

    public enum Icon {
        CROSSED_SWORDS, TOWER_AND_SWORDS, ROMAN_HELMET, SWORDS_AND_GOLD, SWORDS_AND_BLOOD, TOWER_AND_FIRE,
        FIRST_AID_PLUS, CROWN, BIO_SYMBOL, NO_BIO_SYMBOL, NO_HOUSE, TORNADO, DESERT, GREEN_RAT, BREAD,
        SPOTLIGHT, GOLD_COINS, FOOLS_GOLD, PIT, FIREBALL, NUN, LIGHTNING_BOLT, EXPLOSIONS, QUESTIONMARK_HEAD,
        NIGHTMARES, VORTEX, METEOR, TORNADO2, KISS, WIZARD_TOWER_EXPLOSION, THIEF_SILO, THIEF_BANK, THIEF_TOWER,
        KIDNAP_MASK, HOUSE_FIRE, ROGUE, FIST, HORSE, THIEF_BRIBERY, THIEF_BRIBERY_GENERAL, GET_OUT_OF_JAIL,
        WIZARD_KNIFE, PROPAGANDA, SCIENCE
    }

    protected String _date;
    protected String _news;
    protected Province _province;
    protected Province _sourceProvince; // NOTE: This is the "offending" province...

    protected Integer _firstItemOrNull(final List<String> items) {
        if (items.size() > 0) {
            return Util.parseInt(items.get(0));
        }
        return null;
    }

    private Province _parseSourceProvince() {
        final String lowerCaseNews = _news;

        final String provinceToken;
        if (lowerCaseNews.contains("from")) {
            provinceToken = Util.parseValueBetweenTokens(_news, "from", ")");
        }
        else if (lowerCaseNews.contains("by")) {
            provinceToken = Util.parseValueBetweenTokens(_news, "by", ")");
        }
        else {
            provinceToken = null;
        }

        if (provinceToken == null || provinceToken.length() == 0) {
            return null;
        }

        final String provinceName = provinceToken.substring(0, provinceToken.indexOf(" ("));
        final Integer provinceKingdom = Util.parseInt(Util.parseValueBetweenTokens(provinceToken, "(", ":"));
        final Integer provinceIsland = Util.parseInt(provinceToken.substring(provinceToken.indexOf(":")+1));

        final Province province = new Province();
        province.setKingdomIdentifier(new Kingdom.Identifier(provinceKingdom, provinceIsland));
        province.setName(provinceName);
        return province;
    }

    public NewsEvent(Province province, String date, String news) {
        _province = province;
        _date = date;
        _news = news;

        _sourceProvince = _parseSourceProvince();
    }

    public String getDate() {
        return _date;
    }
    public String getNews() {
        return _news;
    }
    public EventType getType() { return EventType.UNKNOWN; }
    public Province getSourceProvince() { return _sourceProvince; }

    @Override
    public String toString() {
        return "NewsEvent: "+ _news
            +" DATE: "+ _date
            +" TYPE: "+ this.getType()
            +" PROVINCE: "+ _sourceProvince
        ;
    }

    public Boolean isAffliction() { return false; }
    public Integer getDuration() { return null; }

    public Integer getNetAcres() { return 0; }
    public Integer getNetPeasants() { return 0; }
    public Integer getNetSoldiers() { return 0; }
    public Integer getNetDefensiveUnits() { return 0; }
    public Integer getNetOffensiveUnits() { return 0; }
    public Integer getNetElites() { return 0; }
    public Integer getNetGold() { return 0; }
    public Integer getNetFood() { return 0; }
    public Integer getNetRunes() { return 0; }
    public Integer getNetHorses() { return 0; }
    public Integer getNetThieves() { return 0; }

    public String getIconName() { return null; }
    public Icon getIcon() { return null; }
}
