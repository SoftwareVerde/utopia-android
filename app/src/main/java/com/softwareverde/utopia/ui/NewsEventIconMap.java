package com.softwareverde.utopia.ui;

import com.softwareverde.utopia.R;
import com.softwareverde.utopia.news.NewsEvent;

public class NewsEventIconMap {
    public static Integer getResId(final NewsEvent.Icon icon) {
        switch (icon) {
            case CROSSED_SWORDS:            return R.drawable.crossed_swords;
            case TOWER_AND_SWORDS:          return R.drawable.traditional_march;
            case ROMAN_HELMET:              return R.drawable.roman_helmet;
            case SWORDS_AND_GOLD:           return R.drawable.plunder;
            case SWORDS_AND_BLOOD:          return R.drawable.massacre;
            case TOWER_AND_FIRE:            return R.drawable.raze;

            case FIRST_AID_PLUS:            return R.drawable.aid;
            case CROWN:                     return R.drawable.crown;
            case BIO_SYMBOL:                return R.drawable.plague_start;
            case NO_BIO_SYMBOL:             return R.drawable.plague_end;
            case NO_HOUSE:                  return R.drawable.no_house;
            case SCIENCE:                   return R.drawable.scientist;

            case TORNADO:                   return R.drawable.storms_icon;
            case DESERT:                    return R.drawable.drought_icon;
            case GREEN_RAT:                 return R.drawable.vermin_icon;
            case BREAD:                     return R.drawable.gluttony_icon;
            case SPOTLIGHT:                 return R.drawable.spotlight;
            case GOLD_COINS:                return R.drawable.greed_icon;
            case FOOLS_GOLD:                return R.drawable.fools_gold;
            case PIT:                       return R.drawable.pitfalls_icon;
            case FIREBALL:                  return R.drawable.fireball;
            case NUN:                       return R.drawable.chastity_icon;
            case LIGHTNING_BOLT:            return R.drawable.lightning;
            case EXPLOSIONS:                return R.drawable.explosions_icon;
            case QUESTIONMARK_HEAD:         return R.drawable.amnesia;
            case NIGHTMARES:                return R.drawable.nightmares;
            case VORTEX:                    return R.drawable.vortex;
            case METEOR:                    return R.drawable.meteor_showers_icon;
            case TORNADO2:                  return R.drawable.tornado;
            case KISS:                      return R.drawable.kiss;

            case WIZARD_TOWER_EXPLOSION:    return R.drawable.sabatage_wizards;
            case THIEF_SILO:                return R.drawable.rob_graneries;
            case THIEF_BANK:                return R.drawable.rob_vaults;
            case THIEF_TOWER:               return R.drawable.rob_towers;
            case KIDNAP_MASK:               return R.drawable.kidnap;
            case HOUSE_FIRE:                return R.drawable.arson;
            case ROGUE:                     return R.drawable.night_strike;
            case FIST:                      return R.drawable.riots_icon;
            case HORSE:                     return R.drawable.horse;
            case THIEF_BRIBERY:             return R.drawable.bribe_thieves;
            case THIEF_BRIBERY_GENERAL:     return R.drawable.bribe_generals;
            case GET_OUT_OF_JAIL:           return R.drawable.free_prisoner;
            case WIZARD_KNIFE:              return R.drawable.assassinate_wizards;
            case PROPAGANDA:                return R.drawable.propaganda;

            default: return null;
        }
    }
}
