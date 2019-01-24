package com.softwareverde.utopia.parser;

import com.softwareverde.util.Json;
import com.softwareverde.util.Util;
import com.softwareverde.utopia.Building;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.Spell;
import com.softwareverde.utopia.UtopiaUtil;
import com.softwareverde.utopia.bundle.ActiveSpellBundle;
import com.softwareverde.utopia.bundle.ActiveSpellsBundle;
import com.softwareverde.utopia.bundle.ArmyOffenseBundle;
import com.softwareverde.utopia.bundle.AttackBundle;
import com.softwareverde.utopia.bundle.AttackDragonInfoBundle;
import com.softwareverde.utopia.bundle.AvailableSpellsBundle;
import com.softwareverde.utopia.bundle.AvailableThieveryOperationBundle;
import com.softwareverde.utopia.bundle.AvailableThieveryOperationsBundle;
import com.softwareverde.utopia.bundle.BuildCostBundle;
import com.softwareverde.utopia.bundle.BuildingBundle;
import com.softwareverde.utopia.bundle.BuildingInProgressBundle;
import com.softwareverde.utopia.bundle.BuildingsBundle;
import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.ChatMessageBundle;
import com.softwareverde.utopia.bundle.ChatroomBundle;
import com.softwareverde.utopia.bundle.DeployedArmyBundle;
import com.softwareverde.utopia.bundle.DraftRateBundle;
import com.softwareverde.utopia.bundle.ExplorationCostsBundle;
import com.softwareverde.utopia.bundle.ForumTopicBundle;
import com.softwareverde.utopia.bundle.ForumTopicPostBundle;
import com.softwareverde.utopia.bundle.ForumTopicPostsBundle;
import com.softwareverde.utopia.bundle.ForumTopicsBundle;
import com.softwareverde.utopia.bundle.FundDragonInfoBundle;
import com.softwareverde.utopia.bundle.InfiltrateThievesBundle;
import com.softwareverde.utopia.bundle.KingdomBundle;
import com.softwareverde.utopia.bundle.KingdomIntelBundle;
import com.softwareverde.utopia.bundle.KingdomProvinceBundle;
import com.softwareverde.utopia.bundle.MilitaryBundle;
import com.softwareverde.utopia.bundle.MilitaryInProgressBundle;
import com.softwareverde.utopia.bundle.MilitarySettingsBundle;
import com.softwareverde.utopia.bundle.NewsBundle;
import com.softwareverde.utopia.bundle.NewspaperBundle;
import com.softwareverde.utopia.bundle.PrivateMessageBundle;
import com.softwareverde.utopia.bundle.PrivateMessagesBundle;
import com.softwareverde.utopia.bundle.ProvinceIdBundle;
import com.softwareverde.utopia.bundle.ProvinceIdsBundle;
import com.softwareverde.utopia.bundle.ProvinceIntelActiveSpellBundle;
import com.softwareverde.utopia.bundle.ProvinceIntelBundle;
import com.softwareverde.utopia.bundle.ScienceBundle;
import com.softwareverde.utopia.bundle.ScienceResultBundle;
import com.softwareverde.utopia.bundle.ScientistBundle;
import com.softwareverde.utopia.bundle.SendAidBundle;
import com.softwareverde.utopia.bundle.SpellBundle;
import com.softwareverde.utopia.bundle.SpellResultBundle;
import com.softwareverde.utopia.bundle.StateCouncilBundle;
import com.softwareverde.utopia.bundle.StateHistoryBundle;
import com.softwareverde.utopia.bundle.ThieveryOperationBundle;
import com.softwareverde.utopia.bundle.ThroneBundle;
import com.softwareverde.utopia.bundle.TradeSettingsBundle;
import com.softwareverde.utopia.bundle.WarRoomBundle;
import com.softwareverde.utopia.parser.HtmlParser.Document;
import com.softwareverde.utopia.parser.HtmlParser.Element;
import com.softwareverde.utopia.parser.HtmlParser.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UtopiaParser {
    private final HtmlParser _htmlParser;
    public UtopiaParser(final HtmlParser htmlParser) {
        _htmlParser = htmlParser;
    }

    public static String cleanProvinceName(String originalProvinceName) {
        if (originalProvinceName == null) {
            originalProvinceName = "";
        }

        String provinceName = originalProvinceName.trim();
        provinceName = provinceName.replace("*", "");   // Online
        provinceName = provinceName.replace("^", "");   // Vacation
        provinceName = provinceName.replace("(M)", ""); // Monarch
        provinceName = provinceName.replace("(S)", ""); // Steward

        // Strip Class From Kingdom-Intel
        if (provinceName.contains("(")) {
            Integer end = provinceName.lastIndexOf("(");
            provinceName = provinceName.substring(0, end);
        }

        return provinceName.trim();
    }

    // NOTE: selectDom should be the select element, not its options.
    //  Will not return null.
    private Element _getSelectedOption(final Elements selectDom) {
        for (final Element element : selectDom.select("option")) {
            if (element.getAttributeValue("selected").toUpperCase().equals("SELECTED")) {
                return element;
            }
        }

        // return new Element(Tag.valueOf("div"), "");
        return _htmlParser.createEmptyElement();
    }

    private Json _parseTwoColumnStats(Document dom) {
        Elements gameContent = dom.select(".game-content");
        Elements rows = gameContent.select(".two-column-stats tr");
        Iterator<Element> propertyKeys = rows.select("th").iterator();
        Iterator<Element> propertyValues = rows.select("td").iterator();

        Json twoColumnValues = new Json();
        while (propertyKeys.hasNext() && propertyValues.hasNext()) {
            Element key = propertyKeys.next();
            Element value = propertyValues.next();
            twoColumnValues.put(key.getText().toUpperCase(), value.getText());
        }

        return twoColumnValues;
    }

    public ThieveryOperationBundle parseThieveryOperation(String html) {
        Document dom = _htmlParser.parse(html);

        ThieveryOperationBundle thieveryOperationBundle = new ThieveryOperationBundle();
        _parseCurrentResources(thieveryOperationBundle, dom);

        Elements errors = dom.select(".errorList");
        if (errors.getCount() > 0) {
            thieveryOperationBundle.put(ThieveryOperationBundle.Keys.WAS_SUCCESS, "0");
            thieveryOperationBundle.put(ThieveryOperationBundle.Keys.RESULT_TEXT, errors.getText());
            return thieveryOperationBundle;
        }

        String resultString = "";
        Boolean wasSuccess = false;
        Elements operationResults = dom.select(".message");
        for (Element result : operationResults) {
            if (result.hasClass("good")) {
                wasSuccess = true;
            }
            resultString += result.getText() +"\n";
        }
        resultString = resultString.trim();

        thieveryOperationBundle.put(ThieveryOperationBundle.Keys.WAS_SUCCESS, (wasSuccess ? "1" : "0") );
        thieveryOperationBundle.put(ThieveryOperationBundle.Keys.RESULT_TEXT, resultString);

        final String lostThievesTokenStart = "We lost ";
        final String lostThievesTokenEnd = ". If we are";
        Integer end;
        Integer begin;
        if ((begin = resultString.indexOf(lostThievesTokenStart)) >= 0 && (end = resultString.indexOf(lostThievesTokenEnd, begin)) >= 0) {
            Integer lostThieves = Util.parseInt(resultString.substring(begin + lostThievesTokenStart.length(), end));
            if (lostThieves > 0) {
                thieveryOperationBundle.put(ThieveryOperationBundle.Keys.THIEVES_LOST, lostThieves.toString());
            }
        }

        String provinceName = _getSelectedOption(dom.select("#id_target_province")).getText().trim();
        String kingdom = dom.select("#id_kingdom").getValue().trim();
        String island = dom.select("#id_island").getValue().trim();
        String operationIdentifier = _getSelectedOption(dom.select("#id_operation")).getAttributeValue("value").trim();

        thieveryOperationBundle.put(ThieveryOperationBundle.Keys.TARGET_PROVINCE_NAME, provinceName);
        thieveryOperationBundle.put(ThieveryOperationBundle.Keys.TARGET_KINGDOM, kingdom);
        thieveryOperationBundle.put(ThieveryOperationBundle.Keys.TARGET_ISLAND, island);
        thieveryOperationBundle.put(ThieveryOperationBundle.Keys.OPERATION_IDENTIFIER, operationIdentifier);

        return thieveryOperationBundle;
    }

    private String _parsePersonality(String rulerName) {
        String title;
        rulerName = rulerName.trim();

        title = "The Wealthy";
        if (rulerName.indexOf(title) == 0) {
            return "MERCHANT";
        }

        title = "The Wise";
        if (rulerName.indexOf(title) == 0) {
            return "SAGE";
        }

        title = "the Rogue";
        if (rulerName.length() > title.length() && rulerName.substring(rulerName.length() - title.length()).equals(title)) {
            return "ROGUE";
        }

        title = "the Sorcerer";
        if (rulerName.length() > title.length() && rulerName.substring(rulerName.length() - title.length()).equals(title)) {
            return "MYSTIC";
        }

        title = "the Sorceress";
        if (rulerName.length() > title.length() && rulerName.substring(rulerName.length() - title.length()).equals(title)) {
            return "MYSTIC";
        }

        title = "the Warrior";
        if (rulerName.length() > title.length() && rulerName.substring(rulerName.length() - title.length()).equals(title)) {
            return "WARRIOR";
        }

        title = "The Conniving";
        if (rulerName.indexOf(title) == 0) {
            return "TACTICIAN";
        }

        title = "the Blessed";
        if (rulerName.length() > title.length() && rulerName.substring(rulerName.length() - title.length()).equals(title)) {
            return "CLERIC";
        }

        title = "the Hero";
        if (rulerName.length() > title.length() && rulerName.substring(rulerName.length() - title.length()).equals(title)) {
            return "MYSTIC";
        }

        return "UNKNOWN";
    }

    // Adds the current-resources (i.e. Money, Peasants, Food, etc) into the provided bundle...
    private void _parseCurrentResources(Bundle bundle, Document dom) {
        String currentUtopiaDate = dom.select(".current-date").getText().trim();
        bundle.put(Bundle.Keys.DATE, currentUtopiaDate);

        Elements resourceBarValues = dom.select("#resource-bar tbody th");

        if (resourceBarValues.getCount() != 7) {
            System.out.println("WARNING: Resource bar has "+ resourceBarValues.getCount() +" elements.");
            return;
        }

        Iterator<Element> resourceValues = resourceBarValues.iterator();
        bundle.put(Bundle.Keys.CURRENT_MONEY, Util.parseInt(resourceValues.next().getText()).toString());
        bundle.put(Bundle.Keys.CURRENT_PEASANTS, Util.parseInt(resourceValues.next().getText()).toString());
        bundle.put(Bundle.Keys.CURRENT_FOOD, Util.parseInt(resourceValues.next().getText()).toString());
        bundle.put(Bundle.Keys.CURRENT_RUNES, Util.parseInt(resourceValues.next().getText()).toString());
        bundle.put(Bundle.Keys.CURRENT_NETWORTH, Util.parseInt(resourceValues.next().getText()).toString());
        bundle.put(Bundle.Keys.CURRENT_LAND, Util.parseInt(resourceValues.next().getText()).toString());
    }

    public enum BadAccessType {
        UNVERIFIED_EMAIL, NO_PROVINCE, DEAD_PROVINCE, NONE
    }
    public BadAccessType parseBadAccessType(String html) {
        if (html.contains("Access is Denied")) {
            final List<String> emailVerificationMatch = Util.pregMatch("Your email address \"([^\"]+)\" has not yet been verified.", html);
            if (emailVerificationMatch.size() > 0) {
                return BadAccessType.UNVERIFIED_EMAIL;
            }
        }
        else if (html.contains("To create a new province") && html.contains("/wol/chooser/create/")) {
            return BadAccessType.NO_PROVINCE;
        }
        else {
            final List<String> deadProvinceMatch = Util.pregMatch("your once-mighty province of ([^ ]+) \\(([0-9]+):([0-9]+)\\) has collapsed, and lies in ruins.", html);
            if (deadProvinceMatch.size() > 0) {
                return BadAccessType.DEAD_PROVINCE;
            }
        }

        return BadAccessType.NONE;
    }

    public ThroneBundle parseThrone(String html) {
        Document dom = _htmlParser.parse(html);
        Elements gameContent = dom.select(".game-content");

        Json twoColumnValues = _parseTwoColumnStats(dom);

        String raceString = twoColumnValues.get("RACE", Json.Types.STRING);
        Province.Race race = Province.getRaceForString(raceString);

        ThroneBundle throneBundle = new ThroneBundle();

        _parseCurrentResources(throneBundle, dom);

        final Boolean hasPlague = (html.contains("The Plague has spread throughout our people!"));
        throneBundle.put(ThroneBundle.Keys.HAS_PLAGUE,          (hasPlague ? "1" : "0"));

        throneBundle.put(ThroneBundle.Keys.RACE,                raceString);
        throneBundle.put(ThroneBundle.Keys.RULER_NAME,          twoColumnValues.get("RULER", Json.Types.STRING)); // TODO: Parse ruler name and title...
        throneBundle.put(ThroneBundle.Keys.PERSONALITY,         _parsePersonality(twoColumnValues.get("RULER", Json.Types.STRING)));
        throneBundle.put(ThroneBundle.Keys.LAND,                twoColumnValues.get("LAND", Json.Types.STRING));
        throneBundle.put(ThroneBundle.Keys.PEASANTS,            twoColumnValues.get("PEASANTS", Json.Types.STRING));
        throneBundle.put(ThroneBundle.Keys.GOLD,                twoColumnValues.get("MONEY", Json.Types.STRING));
        throneBundle.put(ThroneBundle.Keys.FOOD,                twoColumnValues.get("FOOD", Json.Types.STRING));
        throneBundle.put(ThroneBundle.Keys.RUNES,               twoColumnValues.get("RUNES", Json.Types.STRING));
        throneBundle.put(ThroneBundle.Keys.TRADE_BALANCE,       twoColumnValues.get("TRADE BALANCE", Json.Types.STRING));
        throneBundle.put(ThroneBundle.Keys.NETWORTH,            twoColumnValues.get("NETWORTH", Json.Types.STRING));
        throneBundle.put(ThroneBundle.Keys.SOLDIERS,            twoColumnValues.get("SOLDIERS", Json.Types.STRING));
        if (race != null) {
            throneBundle.put(ThroneBundle.Keys.DEFENSIVE_UNITS, twoColumnValues.get(Province.getDefensiveUnitKeyword(race).toUpperCase(), Json.Types.STRING));
            throneBundle.put(ThroneBundle.Keys.OFFENSIVE_UNITS, twoColumnValues.get(Province.getOffensiveUnitKeyword(race).toUpperCase(), Json.Types.STRING));
            throneBundle.put(ThroneBundle.Keys.ELITES, twoColumnValues.get(Province.getEliteKeyword(race).toUpperCase(), Json.Types.STRING));
        }
        throneBundle.put(ThroneBundle.Keys.HORSES,              twoColumnValues.get("WAR HORSES", Json.Types.STRING));
        throneBundle.put(ThroneBundle.Keys.PRISONERS,           twoColumnValues.get("PRISONERS", Json.Types.STRING));
        throneBundle.put(ThroneBundle.Keys.OFFENSIVE_POINTS,    twoColumnValues.get("OFF. POINTS", Json.Types.STRING));
        throneBundle.put(ThroneBundle.Keys.DEFENSIVE_POINTS,    twoColumnValues.get("DEF. POINTS", Json.Types.STRING));

        String buildingEfficiencyString = twoColumnValues.get("BUILDING EFF.", Json.Types.STRING);
        Integer buildingEfficiencyEndIndex = buildingEfficiencyString.indexOf("%");
        if (buildingEfficiencyEndIndex >= 0) {
            throneBundle.put(ThroneBundle.Keys.BUILDING_EFFICIENCY, buildingEfficiencyString.substring(0, buildingEfficiencyEndIndex));
        }

        // Parse Thieves and Stealth
        String thievesAndStealthString = twoColumnValues.get("THIEVES", Json.Types.STRING);
        String thievesString = "";
        String stealthString = "";
        if (thievesAndStealthString.contains("(")) {
            thievesString = thievesAndStealthString.substring(0, thievesAndStealthString.indexOf(" ("));
            stealthString = thievesAndStealthString.substring(thievesAndStealthString.indexOf('(') + 1, thievesAndStealthString.indexOf(')') - 1);
        }
        throneBundle.put(ThroneBundle.Keys.THIEVES,             thievesString);
        throneBundle.put(ThroneBundle.Keys.STEALTH,             stealthString);

        // Parse Wizards and Mana
        String wizardsAndManaString = twoColumnValues.get("WIZARDS", Json.Types.STRING);
        String wizardsString = "";
        String manaString = "";
        if (wizardsAndManaString.contains("(")) {
            wizardsString = wizardsAndManaString.substring(0, wizardsAndManaString.indexOf(" ("));
            manaString = wizardsAndManaString.substring(wizardsAndManaString.indexOf('(') + 1, wizardsAndManaString.indexOf(')') - 1);
        }
        throneBundle.put(ThroneBundle.Keys.WIZARDS,             wizardsString);
        throneBundle.put(ThroneBundle.Keys.MANA,                manaString);

        String provinceNameIdentifier = "The Province of ";
        Elements headerElements = dom.select("h2");
        for (Element headerElement : headerElements) {
            String text = headerElement.getText();
            Integer begin = text.indexOf(provinceNameIdentifier);
            if (begin >= 0) {
                String provinceName = text.substring(begin + provinceNameIdentifier.length(), text.indexOf(" (", begin + provinceNameIdentifier.length()));
                throneBundle.put(ThroneBundle.Keys.PROVINCE_NAME, provinceName);

                throneBundle.put(ThroneBundle.Keys.KINGDOM, text.substring(text.indexOf("(") + 1, text.indexOf(":")));
                throneBundle.put(ThroneBundle.Keys.ISLAND, text.substring(text.indexOf(":") + 1, text.indexOf(")")));

                break;
            }
        }

        throneBundle.put(ThroneBundle.Keys.ROYAL_COMMANDS, Util.limitConsecutiveNewlines(gameContent.select("#throne-monarch-message p").getTextWithNewlines().trim()));

        { // Dragon
            final String dragonTokenStart = "A ";
            final String dragonTokenEnd = " Dragon ravages";
            Integer end;
            Integer begin;
            Elements kingdomStatusesElements = dom.select(".advice-message");
            for (Element kingdomStatusElement : kingdomStatusesElements) {
                String kingdomStatus = kingdomStatusElement.getText();
                if ((begin = kingdomStatus.indexOf(dragonTokenStart)) >= 0 && (end = kingdomStatus.indexOf(dragonTokenEnd, begin)) >= 0) {
                    String dragonType = kingdomStatus.substring(begin + dragonTokenStart.length(), end).trim();
                    if (dragonType.length() > 0) {
                        throneBundle.put(ThroneBundle.Keys.DRAGON_TYPE, dragonType);
                        break;
                    }
                }
            }
        }

        // Sanity Check...
        for (String key : throneBundle.getRequiredKeys()) {
            if (! throneBundle.hasKey(key)) {
                System.out.println("ERROR: Bundle doesn't contain: "+ key);
                return null;
            }
        }

        return throneBundle;
    }

    // Active Spells Parser - parses Mystics:Council
    public ActiveSpellsBundle parseActiveSpells(String html) {
        final Document dom = _htmlParser.parse(html);
        final Elements gameContent = dom.select(".game-content");

        final Iterator<Element> activeSpells = gameContent.select("table").select("tr").iterator();

        // Skip the first row...
        if (activeSpells.hasNext()) {
            activeSpells.next();
        }

        final ActiveSpellsBundle activeSpellsBundle = new ActiveSpellsBundle();
        _parseCurrentResources(activeSpellsBundle, dom);

        while (activeSpells.hasNext()) {
            final Element activeSpellRow = activeSpells.next();
            final Iterator<Element> columns = activeSpellRow.select("th, td").iterator();

            String spellName = null;
            String spellDuration = null;
            String spellDescription = null;

            if (columns.hasNext()) {
                spellName = columns.next().getText().trim();
            }
            if (columns.hasNext()) {
                spellDuration = columns.next().getText().trim();
            }
            if (columns.hasNext()) {
                spellDescription = columns.next().getText().trim();
            }

            if (spellName == null || spellDuration == null || spellDescription == null) {
                continue;
            }

            String parseSpellDurationString = "0";
            if (! spellDuration.contains("-")) {
                try {
                    parseSpellDurationString = spellDuration.substring(0, spellDuration.indexOf(" "));
                } catch (Exception e) {
                    System.out.println("Could not parse spellDuration: " + spellDuration);
                }
            }
            final Integer durationInUtopianDays = Util.parseInt(parseSpellDurationString);
            final Integer durationTimeSecondsFromNow = (int) ((durationInUtopianDays + 1) * 60.0f * 60.0f);

            final Long spellExpirationTime = Util.truncateMinutes(System.currentTimeMillis()) / 1000L + durationTimeSecondsFromNow;

            final ActiveSpellBundle activeSpellBundle = new ActiveSpellBundle();
            _parseCurrentResources(activeSpellBundle, dom);
            activeSpellBundle.put(ActiveSpellBundle.Keys.SPELL_NAME, spellName);
            activeSpellBundle.put(ActiveSpellBundle.Keys.SPELL_DESCRIPTION, spellDescription);
            activeSpellBundle.put(ActiveSpellBundle.Keys.SPELL_EXPIRATION_TIME, spellExpirationTime.toString());
            activeSpellsBundle.addToGroup(ActiveSpellsBundle.Keys.ACTIVE_SPELLS, activeSpellBundle);
        }

        return activeSpellsBundle;
    }

    // Defensive Spells Parser - parses Mystic:Enchantment page.
    public AvailableSpellsBundle parseAvailableDefensiveSpells(String html) {
        final Document dom = _htmlParser.parse(html);
        final Elements gameContent = dom.select(".game-content");

        final Iterator<Element> spellList = gameContent.select("#id_spell").select("option").iterator();

        // Skip the first row...
        if (spellList.hasNext()) {
            spellList.next();
        }

        final AvailableSpellsBundle spellListBundle = new AvailableSpellsBundle();
        _parseCurrentResources(spellListBundle, dom);

        while (spellList.hasNext()) {
            final Element spellOption = spellList.next();

            final String optionValue = spellOption.getAttributeValue("value").trim();
            final String optionText = spellOption.getText();

            final String spellName = optionText.substring(0, optionText.indexOf("(")).trim();
            final String runeCostString = optionText.substring(optionText.indexOf("(") + 1, optionText.indexOf(")")).trim();
            final Integer runeCost = Util.parseInt(runeCostString);

            final SpellBundle spellBundle = new SpellBundle();
            _parseCurrentResources(spellBundle, dom);

            spellBundle.put(SpellBundle.Keys.SPELL_NAME, spellName);
            spellBundle.put(SpellBundle.Keys.SPELL_COST, runeCost.toString());
            spellBundle.put(SpellBundle.Keys.SPELL_IDENTIFIER, optionValue);
            spellBundle.put(SpellBundle.Keys.SPELL_TYPE, Spell.typeToString(Spell.SpellType.DEFENSIVE));

            spellListBundle.addToGroup(AvailableSpellsBundle.Keys.SPELL_LIST_BUNDLE, spellBundle);
        }

        return spellListBundle;
    }

    // Offensive Spells Parser - parses Mystic:Sorcery page.
    public AvailableSpellsBundle parseAvailableOffensiveSpells(String html) {
        final Document dom = _htmlParser.parse(html);
        final Elements gameContent = dom.select(".game-content");

        final Iterator<Element> spellList = gameContent.select("#id_spell").select("option").iterator();

        // Skip the first row...
        if (spellList.hasNext()) {
            spellList.next();
        }

        final AvailableSpellsBundle spellListBundle = new AvailableSpellsBundle();
        _parseCurrentResources(spellListBundle, dom);

        while (spellList.hasNext()) {
            final Element spellOption = spellList.next();

            final String optionValue = spellOption.getAttributeValue("value").trim();
            final String optionText = spellOption.getText();

            final String spellName = optionText.substring(0, optionText.indexOf("(")).trim();
            final String runeCostString = optionText.substring(optionText.indexOf("(") + 1, optionText.indexOf(")")).trim();
            final Integer runeCost = Util.parseInt(runeCostString);

            final SpellBundle spellBundle = new SpellBundle();
            _parseCurrentResources(spellBundle, dom);

            spellBundle.put(SpellBundle.Keys.SPELL_NAME, spellName);
            spellBundle.put(SpellBundle.Keys.SPELL_COST, runeCost.toString());
            spellBundle.put(SpellBundle.Keys.SPELL_IDENTIFIER, optionValue);
            spellBundle.put(SpellBundle.Keys.SPELL_TYPE, Spell.typeToString(Spell.SpellType.OFFENSIVE));

            spellListBundle.addToGroup(AvailableSpellsBundle.Keys.SPELL_LIST_BUNDLE, spellBundle);
        }

        { // Parse Target-Province-List

            final String targetString = gameContent.select(".change-kingdom-heading a").getText();
            String targetKingdom = null;
            String targetIsland = null;
            if (targetString.contains(":")) {
                targetKingdom = targetString.substring(0, targetString.indexOf(":")).trim();
                targetIsland = targetString.substring(targetString.indexOf(":") + 1).trim();
            }

            final Iterator<Element> provinceList = gameContent.select("#id_target_province").select("option").iterator();

            // Skip the first row...
            if (provinceList.hasNext()) {
                provinceList.next();
            }

            while (provinceList.hasNext()) {
                final Element provinceOption = provinceList.next();

                final String optionValue = provinceOption.getAttributeValue("value").trim();
                final String optionText = provinceOption.getText();

                final ProvinceIdBundle provinceIdBundle = new ProvinceIdBundle();
                _parseCurrentResources(provinceIdBundle, dom);

                provinceIdBundle.put(ProvinceIdBundle.Keys.NAME, optionText);
                provinceIdBundle.put(ProvinceIdBundle.Keys.UTOPIA_ID, optionValue);
                provinceIdBundle.put(ProvinceIdBundle.Keys.KINGDOM, targetKingdom);
                provinceIdBundle.put(ProvinceIdBundle.Keys.ISLAND, targetIsland);

                spellListBundle.addToGroup(AvailableSpellsBundle.Keys.PROVINCE_LIST_BUNDLE, provinceIdBundle);
            }
        }

        return spellListBundle;
    }

    public static String getBuildingKeyByName(String buildingTitle) {
        if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.BARREN)))                 { return BuildingsBundle.Keys.BARREN; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.HOMES)))             { return BuildingsBundle.Keys.HOMES; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.FARMS)))             { return BuildingsBundle.Keys.FARMS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.MILLS)))             { return BuildingsBundle.Keys.MILLS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.BANKS)))             { return BuildingsBundle.Keys.BANKS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.TRAINING_GROUNDS)))  { return BuildingsBundle.Keys.TRAINING_GROUNDS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.ARMORIES)))          { return BuildingsBundle.Keys.ARMORIES; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.BARRACKS)))          { return BuildingsBundle.Keys.BARRACKS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.FORTS)))             { return BuildingsBundle.Keys.FORTS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.GUARD_STATIONS)))    { return BuildingsBundle.Keys.GUARD_STATIONS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.HOSPITALS)))         { return BuildingsBundle.Keys.HOSPITALS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.GUILDS)))            { return BuildingsBundle.Keys.GUILDS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.TOWERS)))            { return BuildingsBundle.Keys.TOWERS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.THIEVES_DENS)))      { return BuildingsBundle.Keys.THIEVES_DENS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.WATCHTOWERS)))       { return BuildingsBundle.Keys.WATCH_TOWERS; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.LABORATORIES)))      { return BuildingsBundle.Keys.LABORATORIES; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.UNIVERSITIES)))      { return BuildingsBundle.Keys.UNIVERSITIES; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.STABLES)))           { return BuildingsBundle.Keys.STABLES; }
        else if (buildingTitle.equalsIgnoreCase(Building.getBuildingName(Building.Type.DUNGEONS)))          { return BuildingsBundle.Keys.DUNGEONS; }

        System.out.println("WARNING: Cannot find Bundle BuildingKey for: " + buildingTitle);
        return null;
    }

    public BuildingsBundle parseBuildingCouncil(String html) {
        final Document dom = _htmlParser.parse(html);

        final Elements gameContent = dom.select(".game-content");
        final Elements buildEffects = gameContent.select("#council-internal-build-effects");

        final BuildingsBundle buildingsBundle = new BuildingsBundle();

        final List<Building.Type> orderedBuildingList = new ArrayList<Building.Type>();

        final Map<Building.Type, BuildingBundle> buildingTypeToBuildingBundleMap = new HashMap<Building.Type, BuildingBundle>();
        {   // Buildings Complete
            final Iterator<Element> rows = buildEffects.select("tr").iterator();

            // Skip the first row...
            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                final Element row = rows.next();

                final Iterator<Element> columns = row.select("th, td").iterator();
                final Element buildingNameTd = (columns.hasNext() ? columns.next() : null);
                final Element buildingCountTd = (columns.hasNext() ? columns.next() : null);
                final Element buildingPercentTd = (columns.hasNext() ? columns.next() : null);
                final Element buildingEffectTd = (columns.hasNext() ? columns.next() : null);

                if (buildingNameTd == null || buildingCountTd == null || buildingPercentTd == null || buildingEffectTd == null) {
                    continue;
                }

                final String buildingName = buildingNameTd.getText();
                final BuildingBundle buildingBundle = new BuildingBundle();
                _parseCurrentResources(buildingBundle, dom);
                buildingBundle.put(BuildingBundle.Keys.NAME, buildingName);
                buildingBundle.put(BuildingBundle.Keys.COUNT, Util.parseInt(buildingCountTd.getText()).toString());
                buildingBundle.put(BuildingBundle.Keys.PERCENT, ""+ (Util.parseFloat(buildingPercentTd.getText()) / 100.0f));
                buildingBundle.put(BuildingBundle.Keys.EFFECT, buildingEffectTd.getText());

                final String key = UtopiaParser.getBuildingKeyByName(buildingName);
                if (key != null) {
                    buildingsBundle.put(key, buildingBundle);
                    final Building.Type buildingType = Building.getBuildingType(buildingName);
                    orderedBuildingList.add(buildingType);
                    buildingTypeToBuildingBundleMap.put(buildingType, buildingBundle);
                }
            }
        }

        {   // Buildings In-Progress
            final Elements trainingProgress = gameContent.select(".schedule");
            final Iterator<Element> rows = trainingProgress.select("tr").iterator();

            // Skip the first row...
            if (rows.hasNext()) {
                rows.next();
            }

            Integer buildingTypeIndex = 0;

            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                final Element row = rows.next();

                final Iterator<Element> columns = row.select("td").iterator();

                final BuildingInProgressBundle buildingInProgressBundle = new BuildingInProgressBundle();
                _parseCurrentResources(buildingInProgressBundle, dom);

                Integer i = 0;
                while (columns.hasNext()) {
                    final String tickBundleKey = BuildingInProgressBundle.ORDERED_TICK_KEYS.get(i);

                    final Element column = columns.next();
                    final String numberString = column.getText();
                    if (numberString.length() > 0) {
                        buildingInProgressBundle.put(tickBundleKey, Util.parseInt(numberString).toString());
                    }
                    else {
                        buildingInProgressBundle.put(tickBundleKey, "0");
                    }

                    i += 1;
                }

                final String buildingName = Building.getBuildingName(orderedBuildingList.get(buildingTypeIndex));
                final Building.Type buildingType = Building.getBuildingType(buildingName);
                final BuildingBundle buildingBundle = buildingTypeToBuildingBundleMap.get(buildingType);
                if (buildingInProgressBundle.isValid()) { // Surveys do not have in-progress results.
                    buildingBundle.put(BuildingBundle.Keys.IN_PROGRESS, buildingInProgressBundle);
                }

                buildingTypeIndex += 1;
                if (buildingTypeIndex >= orderedBuildingList.size()) {
                    break;
                }
            }
        }

        _parseCurrentResources(buildingsBundle, dom);
        return buildingsBundle;
    }

    public MilitaryBundle parseMilitaryCouncil(String html) {
        Document dom = _htmlParser.parse(html);
        Elements gameContent = dom.select(".game-content");

        MilitaryBundle militaryBundle = new MilitaryBundle();
        _parseCurrentResources(militaryBundle, dom);

        {   // Military Strength
            Elements trainingProgress = gameContent.select(".two-column-stats");
            Iterator<Element> rows = trainingProgress.select("td").iterator();
            if (rows.hasNext()) {
                Element field = rows.next();
                militaryBundle.put(MilitaryBundle.Keys.OFFENSIVE_EFFECTIVENESS, Util.parseFloat(field.getText()).toString());
            }
            if (rows.hasNext()) {
                Element field = rows.next();
                militaryBundle.put(MilitaryBundle.Keys.OFFENSE_AT_HOME, Util.parseInt(field.getText()).toString());
            }
            if (rows.hasNext()) {
                Element field = rows.next();
                militaryBundle.put(MilitaryBundle.Keys.DEFENSIVE_EFFECTIVENESS, Util.parseFloat(field.getText()).toString());
            }
            if (rows.hasNext()) {
                Element field = rows.next();
                militaryBundle.put(MilitaryBundle.Keys.DEFENSE_AT_HOME, Util.parseInt(field.getText()).toString());
            }
        }

        {   // Training In-Progress
            final Elements trainingProgress = gameContent.select(".schedule");
            final Iterator<Element> rows = trainingProgress.select("tr").iterator();

            // Skip the first row...
            if (rows.hasNext()) {
                rows.next();
            }

            final String[] militaryTypes = new String[]{
                MilitaryBundle.Keys.OFFENSIVE_UNITS_PROGRESS, MilitaryBundle.Keys.DEFENSIVE_UNITS_PROGRESS,
                MilitaryBundle.Keys.ELITE_UNITS_PROGRESS, MilitaryBundle.Keys.THIEVES_PROGRESS
            };
            Integer militaryTypeIndex = 0;
            while (rows.hasNext()) {
                final Element row = rows.next();

                final String unitType = row.select("th").getFirst().getText();

                final Iterator<Element> columns = row.select("td").iterator();
                final MilitaryInProgressBundle militaryInProgressBundle = new MilitaryInProgressBundle();
                _parseCurrentResources(militaryInProgressBundle, dom);

                Integer tick = 0;
                while (columns.hasNext()) {
                    final String inProgressBundleKey = MilitaryInProgressBundle.ORDERED_TICK_KEYS.get(tick);
                    final Element column = columns.next();
                    final String numberString = column.getText();
                    if (numberString.length() > 0) {
                        militaryInProgressBundle.put(inProgressBundleKey, Util.parseInt(numberString).toString());
                    }
                    else {
                        militaryInProgressBundle.put(inProgressBundleKey, "0");
                    }
                    tick++;
                }

                militaryBundle.put(militaryTypes[militaryTypeIndex], militaryInProgressBundle);

                militaryTypeIndex++;
                if (militaryTypeIndex >= militaryTypes.length) {
                    break;
                }
            }
        }

        {   // Deployed Armies
            Elements deployedArmies = gameContent.select(".data");
            Iterator<Element> rows = deployedArmies.select("tr").iterator();

            if (rows.hasNext()) {
                Iterator<Element> deployedArmyHeaders = rows.next().select("th, td").iterator();

                // Skip The First Column
                if (deployedArmyHeaders.hasNext()) {
                    deployedArmyHeaders.next();
                }

                // Skip The At-Home Army
                if (deployedArmyHeaders.hasNext()) {
                    deployedArmyHeaders.next();
                }

                Integer deployedArmyIndex = 0;
                while (deployedArmyHeaders.hasNext()) {
                    DeployedArmyBundle deployedArmyBundle = new DeployedArmyBundle();
                    _parseCurrentResources(deployedArmyBundle, dom);

                    Element column = deployedArmyHeaders.next();
                    try {
                        String columnString = column.getText();
                        String returnTimeString = columnString.substring(columnString.indexOf("(") + 1, columnString.indexOf(")"));
                        Float returnTimeUtopiaDays = Util.parseFloat(returnTimeString);
                        Integer returnTimeSecondsFromNow = (int) (returnTimeUtopiaDays * 60.0f * 60.0f);
                        Long returnTimeFromEpoch = (System.currentTimeMillis() / 1000L) + returnTimeSecondsFromNow;


                        deployedArmyBundle.put(DeployedArmyBundle.Keys.RETURN_TIME, returnTimeFromEpoch.toString());

                        militaryBundle.addToGroup(MilitaryBundle.Keys.DEPLOYED_ARMIES, deployedArmyBundle);
                    } catch (Exception e) { } // There is no army in this slot...

                    deployedArmyIndex++;
                }
            }

            String[] atHomeMilitaryTypes = new String[] {
                MilitaryBundle.Keys.GENERALS_HOME, MilitaryBundle.Keys.SOLDIERS_HOME, MilitaryBundle.Keys.OFFENSIVE_UNITS_HOME,
                MilitaryBundle.Keys.DEFENSIVE_UNITS_HOME, MilitaryBundle.Keys.ELITE_UNITS_HOME, MilitaryBundle.Keys.HORSES_HOME,
                null
            };
            String[] militaryTypes = new String[] {
                DeployedArmyBundle.Keys.GENERALS, DeployedArmyBundle.Keys.SOLDIERS, DeployedArmyBundle.Keys.OFFENSIVE_UNITS,
                DeployedArmyBundle.Keys.DEFENSIVE_UNITS, DeployedArmyBundle.Keys.ELITE_UNITS, DeployedArmyBundle.Keys.HORSES,
                DeployedArmyBundle.Keys.CAPTURED_LAND
            };
            Integer militaryTypeIndex = 0;
            while (rows.hasNext()) {
                Iterator<Element> deployedArmyColumns = rows.next().select("th, td").iterator();

                // Skip The First Column
                if (deployedArmyColumns.hasNext()) {
                    deployedArmyColumns.next();
                }

                // The At-Home Army
                if (deployedArmyColumns.hasNext()) {
                    String columnKey = atHomeMilitaryTypes[militaryTypeIndex];
                    Element column = deployedArmyColumns.next();
                    if (columnKey != null) { // i.e. At-Home Captured Land
                        militaryBundle.put(columnKey, column.getText());
                    }
                }

                Integer deployedArmyIndex = 0;
                if (militaryBundle.hasGroupKey(MilitaryBundle.Keys.DEPLOYED_ARMIES)) {
                    List<Bundle> deployedArmyBundles = militaryBundle.getGroup(MilitaryBundle.Keys.DEPLOYED_ARMIES);
                    while (deployedArmyColumns.hasNext() && deployedArmyIndex < deployedArmyBundles.size()) {
                        Element column = deployedArmyColumns.next();
                        DeployedArmyBundle deployedArmyBundle = (DeployedArmyBundle) deployedArmyBundles.get(deployedArmyIndex);

                        if (deployedArmyBundle != null) {
                            String columnString = column.getText();
                            deployedArmyBundle.put(militaryTypes[militaryTypeIndex], columnString);
                        }
                        deployedArmyIndex++;
                    }
                }

                militaryTypeIndex++;
                if (militaryTypeIndex >= militaryTypes.length) {
                    break;
                }
            }
        }

        return militaryBundle;
    }

    public MilitarySettingsBundle parseMilitarySettings(String html) {
        Document dom = _htmlParser.parse(html);
        Elements gameContent = dom.select(".game-content");

        Iterator<Element> draftRateOptions = gameContent.select("#id_draft_rate option").iterator();

        MilitarySettingsBundle militarySettingsBundle = new MilitarySettingsBundle();
        _parseCurrentResources(militarySettingsBundle, dom);

        while (draftRateOptions.hasNext()) {
            Element draftRateOption = draftRateOptions.next();
            String text = draftRateOption.getText().trim();
            String identifier = draftRateOption.getAttributeValue("value").trim().toUpperCase();
            String name = text.substring(0, text.indexOf(" ")).trim();
            String rate = text.substring(text.indexOf("(") + 1, text.indexOf("%")).trim();
            String rateCost = text.substring(text.indexOf("; ") + 1, text.indexOf(" gold")).trim();
            Boolean isSelected = (draftRateOption.getAttributeValue("selected").trim().equalsIgnoreCase("selected"));

            DraftRateBundle draftRateBundle = new DraftRateBundle();
            _parseCurrentResources(draftRateBundle, dom);
            draftRateBundle.put(DraftRateBundle.Keys.IDENTIFIER, identifier);
            draftRateBundle.put(DraftRateBundle.Keys.NAME, name);
            draftRateBundle.put(DraftRateBundle.Keys.RATE, rate);
            draftRateBundle.put(DraftRateBundle.Keys.RATE_COST, rateCost);
            draftRateBundle.put(DraftRateBundle.Keys.IS_SELECTED, (isSelected ? "1" : "0"));
            militarySettingsBundle.addToGroup(MilitarySettingsBundle.Keys.DRAFT_RATES, draftRateBundle);
        }

        militarySettingsBundle.put(MilitarySettingsBundle.Keys.DRAFT_TARGET, gameContent.select("#id_draft_target").getAttributeValue("value"));
        militarySettingsBundle.put(MilitarySettingsBundle.Keys.WAGE_RATE, gameContent.select("#id_wage_rate").getAttributeValue("value"));

        { // Parse Training Costs
            final Elements rows;
            try {
                Elements tables = gameContent.select("table");
                Element trainingTable = tables.getLast();
                rows = trainingTable.select("tr");
            }
            catch (Exception e) {
                return militarySettingsBundle;
            }

            Integer offenseUnitCost = null;
            Integer defenseUnitCost = null;
            Integer eliteCost = null;
            Integer thiefCost = null;

            if (rows.getCount() == 5) {
                offenseUnitCost = Util.parseInt(rows.get(1).select("td").eq(2).getFirst().getText());
                defenseUnitCost = Util.parseInt(rows.get(2).select("td").eq(2).getFirst().getText());
                eliteCost = Util.parseInt(rows.get(3).select("td").eq(2).getFirst().getText());
                thiefCost = Util.parseInt(rows.get(4).select("td").eq(2).getFirst().getText());
            }
            else if (rows.getCount() == 4) {
                offenseUnitCost = Util.parseInt(rows.get(1).select("td").eq(2).getFirst().getText());
                defenseUnitCost = Util.parseInt(rows.get(2).select("td").eq(2).getFirst().getText());
                thiefCost = Util.parseInt(rows.get(3).select("td").eq(2).getFirst().getText());
            }

            if (offenseUnitCost != null) {
                militarySettingsBundle.put(MilitarySettingsBundle.Keys.OFFENSIVE_UNIT_COST, offenseUnitCost.toString());
            }
            if (defenseUnitCost != null) {
                militarySettingsBundle.put(MilitarySettingsBundle.Keys.DEFENSIVE_UNIT_COST, defenseUnitCost.toString());
            }
            if (eliteCost != null) {
                militarySettingsBundle.put(MilitarySettingsBundle.Keys.ELITE_COST, eliteCost.toString());
            }
            if (thiefCost != null) {
                militarySettingsBundle.put(MilitarySettingsBundle.Keys.THIEF_COST, thiefCost.toString());
            }

        }

        return militarySettingsBundle;
    }

    public StateCouncilBundle parseStateCouncil(String html) {
        final Document dom = _htmlParser.parse(html);
        final String currentUtopiaDate = dom.select(".current-date").getText().trim();
        final Elements gameContent = dom.select(".game-content");

        final Iterator<Element> stateRows = gameContent.select("table.three-column-stats").select("tr").iterator();

        // Skip the first row...
        if (stateRows.hasNext()) {
            stateRows.next();
        }

        final StateCouncilBundle stateCouncilBundle = new StateCouncilBundle();
        _parseCurrentResources(stateCouncilBundle, dom);
        stateCouncilBundle.put(StateCouncilBundle.Keys.TICK, UtopiaUtil.countTicksByDate(currentUtopiaDate).toString());

        while (stateRows.hasNext()) {
            final Element stateRow = stateRows.next();
            final Iterator<Element> columns = stateRow.select("th, td").iterator();

            while (columns.hasNext()) {
                final Element attribute = columns.next();
                if (! columns.hasNext()) break; // Key/Value pair has an uneven length...

                final String attributeName = attribute.getText().toUpperCase();
                switch (attributeName) {
                    case "MAX POPULATION":
                        stateCouncilBundle.put(StateCouncilBundle.Keys.MAX_POPULATION, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "UNEMPLOYED PEASANTS":
                        stateCouncilBundle.put(StateCouncilBundle.Keys.UNEMPLOYED_PEASANTS, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "UNFILLED JOBS":
                        stateCouncilBundle.put(StateCouncilBundle.Keys.UNFILLED_JOBS, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "EMPLOYMENT":
                        stateCouncilBundle.put(StateCouncilBundle.Keys.EMPLOYMENT_PERCENT, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "DAILY INCOME":
                        stateCouncilBundle.put(StateCouncilBundle.Keys.INCOME, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "DAILY WAGES":
                        stateCouncilBundle.put(StateCouncilBundle.Keys.MILITARY_WAGES, Util.parseInt(columns.next().getText()).toString());
                        break;
//                    case "CURRENT NETWORTH":
//                        stateCouncilBundle.put(StateCouncilBundle.Keys.NETWORTH, Util.parseInt(columns.next().getText()).toString());
//                        break;
                    case "CURRENT HONOR":
                        stateCouncilBundle.put(StateCouncilBundle.Keys.HONOR, Util.parseInt(columns.next().getText()).toString());
                        break;
                }
            }
        }

        final Iterator<Element> historyRows = gameContent.select("table.math-table").select("tr").iterator();

        // Skip the first row...
        if (historyRows.hasNext()) {
            historyRows.next();
        }

        final StateHistoryBundle stateHistoryBundle = new StateHistoryBundle();
        _parseCurrentResources(stateHistoryBundle, dom);
        stateHistoryBundle.put(StateHistoryBundle.Keys.TICK, UtopiaUtil.countTicksByDate(currentUtopiaDate).toString());

        while (historyRows.hasNext()) {
            final Element historyRow = historyRows.next();
            final Iterator<Element> columns = historyRow.select("th, td").iterator();

            while (columns.hasNext()) {
                final Element attribute = columns.next();
                if (! columns.hasNext()) break; // Key/Value pair has an uneven length...

                final String attributeName = attribute.getText().toUpperCase();
                switch (attributeName) {
                    case "OUR INCOME":
                        stateHistoryBundle.put(StateHistoryBundle.Keys.REVENUE, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "MILITARY WAGES":
                        stateHistoryBundle.put(StateHistoryBundle.Keys.MILITARY_WAGES, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "DRAFT COSTS":
                        stateHistoryBundle.put(StateHistoryBundle.Keys.DRAFT_COST, Util.parseInt(columns.next().getText()).toString());
                        break;
//                    case "SCIENCE COSTS":
//                        stateHistoryBundle.put(StateHistoryBundle.Keys.SCIENCE_COST, Util.parseInt(columns.next().getText()).toString());
//                        break;
                    case "PEASANTS":
                        stateHistoryBundle.put(StateHistoryBundle.Keys.PEASANTS_DELTA, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "FOOD GROWN":
                        stateHistoryBundle.put(StateHistoryBundle.Keys.FOOD_PRODUCED, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "FOOD NEEDED":
                        stateHistoryBundle.put(StateHistoryBundle.Keys.FOOD_REQUIRED, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "FOOD DECAYED":
                        stateHistoryBundle.put(StateHistoryBundle.Keys.FOOD_DECAYED, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "RUNES PRODUCED":
                        stateHistoryBundle.put(StateHistoryBundle.Keys.RUNES_PRODUCED, Util.parseInt(columns.next().getText()).toString());
                        break;
                    case "RUNES DECAYED":
                        stateHistoryBundle.put(StateHistoryBundle.Keys.RUNES_DECAYED, Util.parseInt(columns.next().getText()).toString());
                        break;
                }
            }
        }
        stateCouncilBundle.put(StateCouncilBundle.Keys.STATE_HISTORY_BUNDLE, stateHistoryBundle);

        return stateCouncilBundle;
    }

    public NewspaperBundle parseNews(String html) {
        Document dom = _htmlParser.parse(html);

        NewspaperBundle newspaperBundle = new NewspaperBundle();
        _parseCurrentResources(newspaperBundle, dom);

        Elements gameContent = dom.select(".game-content");

        Elements newsTable = gameContent.select("#news");

        for (Element row : newsTable.select("tr")) {
            // Iterator<Element> incidentTuple = row.select("td").iterator();
            // Element date = ( incidentTuple.hasNext() ? incidentTuple.next() : null );
            // Element report = ( incidentTuple.hasNext() ? incidentTuple.next() : null );

            String type = "";
            String date = row.select(".news-incident-date").getText().trim();
            String report = row.select(".news-incident-report").getText().trim();

            Elements newsTypeElement = row.select(".news-incident-type");
            if (newsTypeElement.getCount() > 0) {
                type = newsTypeElement.getFirst().removeClass("news-incident-type").getClassNames().trim().toUpperCase();

                final String token = "NEWS-";
                Integer begin;
                if ((begin = type.indexOf(token)) >= 0) {
                    type = type.substring(begin + token.length()).trim();
                }
            }

            if (report.length() == 0) {
                continue;
            }

            NewsBundle newsBundle = new NewsBundle();
            _parseCurrentResources(newsBundle, dom);
            newsBundle.put(NewsBundle.Keys.DATE, date);
            newsBundle.put(NewsBundle.Keys.NEWS, report);
            newsBundle.put(NewsBundle.Keys.TYPE, type);
            newspaperBundle.addToGroup(NewspaperBundle.Keys.NEWS_BUNDLE, newsBundle);
        }

        return newspaperBundle;
    }

    public AvailableThieveryOperationsBundle parseAvailableThieveryOperations(String html) {
        Document dom = _htmlParser.parse(html);

        Elements gameContent = dom.select(".game-content");

        AvailableThieveryOperationsBundle availableThieveryOperationsBundle = new AvailableThieveryOperationsBundle();
        _parseCurrentResources(availableThieveryOperationsBundle, dom);

        String targetString = gameContent.select(".change-kingdom-heading a").getText();
        String targetKingdom = null;
        String targetIsland = null;
        if (targetString.contains(":")) {
            targetKingdom = targetString.substring(0, targetString.indexOf(":")).trim();
            targetIsland = targetString.substring(targetString.indexOf(":") + 1).trim();
        }

        { // Parse Target-Province-List
            Iterator<Element> provinceList = gameContent.select("#id_target_province").select("option").iterator();

            // Skip the first row...
            if (provinceList.hasNext()) {
                provinceList.next();
            }

            while (provinceList.hasNext()) {
                Element provinceOption = provinceList.next();

                String optionValue = provinceOption.getAttributeValue("value").trim();
                String optionText = provinceOption.getText();

                ProvinceIdBundle provinceIdBundle = new ProvinceIdBundle();
                _parseCurrentResources(provinceIdBundle, dom);
                provinceIdBundle.put(ProvinceIdBundle.Keys.NAME, optionText);
                provinceIdBundle.put(ProvinceIdBundle.Keys.UTOPIA_ID, optionValue);
                provinceIdBundle.put(ProvinceIdBundle.Keys.KINGDOM, targetKingdom);
                provinceIdBundle.put(ProvinceIdBundle.Keys.ISLAND, targetIsland);

                availableThieveryOperationsBundle.addToGroup(AvailableThieveryOperationsBundle.Keys.PROVINCE_LIST_BUNDLE, provinceIdBundle);
            }
        }

        { // Parse Thievery-Available-Operations
            Iterator<Element> opsList = gameContent.select("#id_operation").select("option").iterator();

            // Skip the first row...
            if (opsList.hasNext()) {
                opsList.next();
            }

            while (opsList.hasNext()) {
                Element opOption = opsList.next();

                String optionValue = opOption.getAttributeValue("value").trim();
                String optionText = opOption.getText();

                AvailableThieveryOperationBundle opBundle = new AvailableThieveryOperationBundle();
                _parseCurrentResources(opBundle, dom);
                opBundle.put(AvailableThieveryOperationBundle.Keys.NAME, optionText);
                opBundle.put(AvailableThieveryOperationBundle.Keys.IDENTIFIER, optionValue);

                availableThieveryOperationsBundle.addToGroup(AvailableThieveryOperationsBundle.Keys.THIEVERY_OPERATION_BUNDLE, opBundle);
            }
        }

        return availableThieveryOperationsBundle;
    }

    public KingdomBundle parseKingdom(String html) {
        final Document dom = _htmlParser.parse(html);
        final Elements gameContent = dom.select(".game-content");

        final KingdomBundle kingdomBundle = new KingdomBundle();
        _parseCurrentResources(kingdomBundle, dom);

        kingdomBundle.put(KingdomBundle.Keys.KINGDOM_ID, gameContent.select("#id_kingdom").getAttributeValue("value"));
        kingdomBundle.put(KingdomBundle.Keys.ISLAND_ID, gameContent.select("#id_island").getAttributeValue("value"));

        String kingdomName = gameContent.select(".change-kingdom-heading").getText().trim();
        if (kingdomName.length() > 0) {
            kingdomName = kingdomName.substring(0, kingdomName.indexOf("("));
            if (kingdomName.toUpperCase().contains("CURRENT KINGDOM IS")) {
                kingdomName = kingdomName.substring("CURRENT KINGDOM IS".length()).trim();
            }
        }
        kingdomBundle.put(KingdomBundle.Keys.NAME, kingdomName);

        for (final Element row : gameContent.select("table.tablesorter tbody").select("tr")) {
            final Boolean isMonarch = row.getClassNames().trim().toUpperCase().equals("MONARCH");

            final Iterator<Element> columns = row.select("th, td").iterator();
            if (columns.hasNext()) {
                columns.next(); // Skip first column...
            }
            final String provinceName = columns.next().getText().trim();
            if (provinceName.equals("-")) {
                continue;
            }

            final String provinceRace = columns.next().getText().trim();
            final Integer provinceAcres = Util.parseInt(columns.next().getText().trim());
            final Integer provinceNetworth = Util.parseInt(columns.next().getText().trim());
            final Integer provinceNwpa = Util.parseInt(columns.next().getText().trim());
            final String provinceTitle = columns.next().getText().trim();

            final KingdomProvinceBundle provinceBundle = new KingdomProvinceBundle();
            _parseCurrentResources(provinceBundle, dom);
            provinceBundle.put(KingdomProvinceBundle.Keys.NAME, cleanProvinceName(provinceName));
            provinceBundle.put(KingdomProvinceBundle.Keys.ACRES, provinceAcres.toString());
            provinceBundle.put(KingdomProvinceBundle.Keys.RACE, provinceRace);
            provinceBundle.put(KingdomProvinceBundle.Keys.NETWORTH, provinceNetworth.toString());
            provinceBundle.put(KingdomProvinceBundle.Keys.NWPA, provinceNwpa.toString());
            provinceBundle.put(KingdomProvinceBundle.Keys.TITLE, provinceTitle);
            provinceBundle.put(KingdomProvinceBundle.Keys.IS_MONARCH, (isMonarch ? "1" : "0"));

            if (provinceNetworth > 0) {
                kingdomBundle.addToGroup(KingdomBundle.Keys.PROVINCES, provinceBundle);
            }
        }

        final Elements atWarMessage = gameContent.select(".advice-message");
        kingdomBundle.put(KingdomBundle.Keys.IS_AT_WAR, (atWarMessage.getCount() > 0 ? "1" : "0"));
        if (atWarMessage.getCount() > 0) {
            final String warringKdString = atWarMessage.select("a").getText().trim();
            final Integer warringKdId = Util.parseInt(warringKdString.substring(0, warringKdString.indexOf(":")));
            final Integer warringKdIsland = Util.parseInt(warringKdString.substring(warringKdString.indexOf(":") + 1));
            kingdomBundle.put(KingdomBundle.Keys.WARRING_KINGDOM_KINGDOM_ID, warringKdId.toString());
            kingdomBundle.put(KingdomBundle.Keys.WARRING_KINGDOM_ISLAND_ID, warringKdIsland.toString());
        }
        else {
            kingdomBundle.put(KingdomBundle.Keys.WARRING_KINGDOM_KINGDOM_ID, "0");
            kingdomBundle.put(KingdomBundle.Keys.WARRING_KINGDOM_ISLAND_ID, "0");
        }

        final Iterator<Element> kingdomMetaDataColumns = gameContent.select("table.two-column-stats").select("th, td").iterator();
        while (kingdomMetaDataColumns.hasNext()) {
            final String key = kingdomMetaDataColumns.next().getText().trim().toUpperCase();

            if (! kingdomMetaDataColumns.hasNext()) {
                break; // Must have an even-number of key-value pair columns.
            }

            switch (key) {
                case "STANCE":
                    kingdomBundle.put(KingdomBundle.Keys.STANCE, kingdomMetaDataColumns.next().getText().trim());
                    break;
                case "AVERAGE OPPONENT RELATIVE SIZE":
                    kingdomBundle.put(KingdomBundle.Keys.AVERAGE_OPPONENT_SIZE, kingdomMetaDataColumns.next().getText().trim());
                    break;
                case "WARS WON / CONCLUDED WARS / WAR SCORE":
                    String data = kingdomMetaDataColumns.next().getText().trim();
                    Integer warsWon = Util.parseInt(data.substring(0, data.indexOf("/")));
                    Integer warsFought = Util.parseInt(data.substring(data.indexOf("/", data.indexOf("/") + 1) + 1));
                    Float warScore = Util.parseFloat(data.substring(data.lastIndexOf("/") + 1));
                    kingdomBundle.put(KingdomBundle.Keys.WARS_WON, warsWon.toString());
                    kingdomBundle.put(KingdomBundle.Keys.WARS_FOUGHT, warsFought.toString());
                    kingdomBundle.put(KingdomBundle.Keys.WAR_SCORE, warScore.toString());
                    break;
                case "TOTAL HONOR":
                    kingdomBundle.put(KingdomBundle.Keys.HONOR, kingdomMetaDataColumns.next().getText().trim());
                    break;
                case "THEIR ATTITUDE TO US": {
                        final String attitude;
                        final String points;

                        final String columnValue = kingdomMetaDataColumns.next().getText().trim();
                        if (columnValue.contains("(") && columnValue.contains("points)")) {
                            points = Util.parseValueBetweenTokens(columnValue, "(", "points)").trim();
                            attitude = columnValue.substring(0, columnValue.indexOf("(")).trim();
                        }
                        else {
                            points = "";
                            attitude = columnValue;
                        }
                        kingdomBundle.put(KingdomBundle.Keys.ATTITUDE_TOWARD_US, attitude);
                        kingdomBundle.put(KingdomBundle.Keys.HOSTILITY_POINTS, points);
                    } break;
                case "OUR ATTITUDE TO THEM": {
                        final String attitude;
                        final String points;

                        final String columnValue = kingdomMetaDataColumns.next().getText().trim();
                        if (columnValue.contains("(") && columnValue.contains("points)")) {
                            points = Util.parseValueBetweenTokens(columnValue, "(", "points)").trim();
                            attitude = columnValue.substring(0, columnValue.indexOf("(")).trim();
                        }
                        else {
                            points = "";
                            attitude = columnValue;
                        }
                        kingdomBundle.put(KingdomBundle.Keys.ATTITUDE_TOWARD_THEM, attitude);
                        kingdomBundle.put(KingdomBundle.Keys.ENEMY_HOSTILITY_POINTS, points);
                    } break;
            }
        }

        { // Parse Hostile Meter
            final Elements meterContainer = gameContent.select(".meter-container");
            final Boolean meterIsEnabled = (meterContainer.getCount() > 0);
            if (meterIsEnabled) { // Meter is enabled.
                final Integer theirMeterValue = Util.parseInt(Util.parseValueBetweenTokens(meterContainer.select(".their-meter-value").getAttributeValue("style"), "left:", "px").trim());
                final Integer ourMeterValue = Util.parseInt(Util.parseValueBetweenTokens(meterContainer.select(".own-meter-value").getAttributeValue("style"), "left:", "px").trim());

                kingdomBundle.put(KingdomBundle.Keys.HAS_METER, "1");
                kingdomBundle.put(KingdomBundle.Keys.METER_VALUE, ourMeterValue.toString());
                kingdomBundle.put(KingdomBundle.Keys.ENEMY_METER_VALUE, theirMeterValue.toString());
            }
            else {
                kingdomBundle.put(KingdomBundle.Keys.HAS_METER, "0");
            }
        }

        return kingdomBundle;
    }

    public ChatroomBundle parseChatroom(String html) {
        final String messagesTokenStart = "var messages = $.parseJSON('";
        final String messagesTokenEnd = "');\n";

        Integer end;
        Integer begin;
        Json messages;
        if ((begin = html.indexOf(messagesTokenStart)) >= 0 && (end = html.indexOf(messagesTokenEnd, begin)) >= 0) {
            final String data = Util.unescapeString(html.substring(begin + messagesTokenStart.length(), end));
            messages = Json.fromString(data);
        }
        else {
            messages = Json.fromString(html).get(0);
        }

        ChatroomBundle chatroomBundle = new ChatroomBundle();
        for (Integer i = 0; i < messages.length(); i++) {
            Json message = messages.get(i);
            if (message.get("message", Json.Types.STRING).length() == 0) {
                continue;
            }

            ChatMessageBundle chatMessageBundle = new ChatMessageBundle();
            chatMessageBundle.put(ChatMessageBundle.Keys.MESSAGE, message.get("message", Json.Types.STRING));
            chatMessageBundle.put(ChatMessageBundle.Keys.DISPLAY_NAME, message.get("display_name", Json.Types.STRING));
            chatMessageBundle.put(ChatMessageBundle.Keys.ID, message.get("id", Json.Types.STRING));
            chatMessageBundle.put(ChatMessageBundle.Keys.TIMESTAMP, Util.parseLong(message.get("time", Json.Types.STRING)).toString());

            chatroomBundle.addToGroup(ChatroomBundle.Keys.MESSAGES, chatMessageBundle);
        }

        return chatroomBundle;
    }

    public SpellResultBundle parseSpellResult(final String html) {
        final Document dom = _htmlParser.parse(html);

        final SpellResultBundle spellResultBundle = new SpellResultBundle();
        _parseCurrentResources(spellResultBundle, dom);

        final Elements errors = dom.select(".errorList");
        if (errors.getCount() > 0) {
            spellResultBundle.put(SpellResultBundle.Keys.WAS_SUCCESS, "0");
            spellResultBundle.put(SpellResultBundle.Keys.RESULT_TEXT, errors.getText());
            return spellResultBundle;
        }

        String resultString = "";
        Boolean wasSuccess = false;
        final Elements operationResults = dom.select(".message");
        for (final Element result : operationResults) {
            if (result.hasClass("good")) {
                wasSuccess = true;
            }
            resultString += result.getText() +"\n";
        }
        resultString = resultString.trim();

        spellResultBundle.put(SpellResultBundle.Keys.WAS_SUCCESS, (wasSuccess ? "1" : "0") );
        spellResultBundle.put(SpellResultBundle.Keys.RESULT_TEXT, resultString);

        final String lostWizardsTokenStart = "terribly wrong with our spell. ";
        final String lostWizardsTokenEnd = " of our wizards were killed";
        Integer end;
        Integer begin;
        if ((begin = resultString.indexOf(lostWizardsTokenStart)) >= 0 && (end = resultString.indexOf(lostWizardsTokenEnd, begin)) >= 0) {
            Integer lostWizards = Util.parseInt(resultString.substring(begin + lostWizardsTokenStart.length(), end));
            if (lostWizards > 0) {
                spellResultBundle.put(SpellResultBundle.Keys.WIZARDS_LOST, lostWizards.toString());
            }
        }

        final Integer spellDuration = Util.parseInt(Util.parseValueBetweenTokens(resultString.toLowerCase(), "for", "days").trim());


        final String provinceName = _getSelectedOption(dom.select("#id_target_province")).getText().trim();
        final String kingdom = dom.select("#id_kingdom").getValue().trim();
        final String island = dom.select("#id_island").getValue().trim();
        final String spellIdentifier = _getSelectedOption(dom.select("#id_spell")).getAttributeValue("value").trim();

        final Integer durationTimeSecondsFromNow = (int) ((spellDuration + 1) * 60.0f * 60.0f);
        final Long spellExpirationTime = Util.truncateMinutes(System.currentTimeMillis()) / 1000L + durationTimeSecondsFromNow;

        spellResultBundle.put(SpellResultBundle.Keys.TARGET_PROVINCE_NAME, provinceName);
        spellResultBundle.put(SpellResultBundle.Keys.TARGET_KINGDOM, kingdom);
        spellResultBundle.put(SpellResultBundle.Keys.TARGET_ISLAND, island);

        spellResultBundle.put(SpellResultBundle.Keys.SPELL_IDENTIFIER, spellIdentifier); // May not be available once mana runs out...
        spellResultBundle.put(SpellResultBundle.Keys.SPELL_DURATION, spellDuration.toString());
        spellResultBundle.put(SpellResultBundle.Keys.SPELL_EXPIRATION_TIME, spellExpirationTime.toString());

        return spellResultBundle;
    }


    public KingdomIntelBundle parseKingdomIntel(String html) {
        Document dom = _htmlParser.parse(html);

        KingdomIntelBundle kingdomIntelBundle = new KingdomIntelBundle();
        _parseCurrentResources(kingdomIntelBundle, dom);

        for (Element row : dom.select(".tablesorter tr")) {
            Elements rowTds = row.select("td");
            if (rowTds.getCount() != 3) { continue; }

            Iterator<Element> tdIterator = rowTds.iterator();

            // Slot Id
            tdIterator.next();

            // Province Name
            String provinceName = cleanProvinceName(tdIterator.next().getText());
            if (provinceName.length() == 0 || provinceName.equals("-")) { continue; }

            /*
                // Personality
                tdIterator.next();

                // Runes
                tdIterator.next();

                // Gold
                tdIterator.next();

                // Food
                tdIterator.next();

                // Defense
                String defense;
                String defenseAge;
                {
                    Element defenseTd = tdIterator.next();
                    defense = defenseTd.getText().trim();
                    String classnames = defenseTd.getClassNames();
                    if (classnames.contains("intel-")) {
                        defenseAge = classnames.substring("intel-".length()).toUpperCase();
                    }
                    else {
                        defenseAge = "";
                    }
                }

                // Army-Return
                List<String> returnDates = new ArrayList<String>();
                for (String returnDate : tdIterator.next().getText().trim().split(",")) {
                    returnDates.add(returnDate.trim());
                }
            */

            // Afflicted Spells
            List<String[]> activeSpellsAndExpirations = new ArrayList<String[]>();
            final String supportSpells = tdIterator.next().getText().trim();
            for (String spellAndDurationString : supportSpells.split(",")) {
                Integer begin;
                Integer end;
                if ((begin = spellAndDurationString.indexOf("(")) < 0 || (end = spellAndDurationString.indexOf("(")) < 0) { continue; }

                String spellName = spellAndDurationString.substring(0, end).trim();
                String duration = spellAndDurationString.substring(begin + 1, spellAndDurationString.indexOf(")")).trim();

                Integer durationInUtopianDays = Util.parseInt(duration);
                Integer durationTimeSecondsFromNow = (int) ((durationInUtopianDays + 1) * 60.0f * 60.0f);
                Long spellExpirationTime = Util.truncateMinutes(System.currentTimeMillis()) / 1000L + durationTimeSecondsFromNow;

                String[] spellAndExpiration = new String[] { spellName, spellExpirationTime.toString() };
                activeSpellsAndExpirations.add(spellAndExpiration);
            }

            // Place data into bundle...

            ProvinceIntelBundle provinceIntelBundle = new ProvinceIntelBundle();
            _parseCurrentResources(provinceIntelBundle, dom);
            provinceIntelBundle.put(ProvinceIntelBundle.Keys.PROVINCE_NAME, provinceName);
            // provinceIntelBundle.put(ProvinceIntelBundle.Keys.DEFENSE_HOME, defense);
            // provinceIntelBundle.put(ProvinceIntelBundle.Keys.INTEL_AGE, defenseAge);

            /*
                String[] returnDateArmyKeys = new String[] {
                    ProvinceIntelBundle.Keys.ARMY_ONE_OUT_UNTIL, ProvinceIntelBundle.Keys.ARMY_TWO_OUT_UNTIL,
                    ProvinceIntelBundle.Keys.ARMY_THREE_OUT_UNTIL, ProvinceIntelBundle.Keys.ARMY_FOUR_OUT_UNTIL, ProvinceIntelBundle.Keys.ARMY_FIVE_OUT_UNTIL
                };
                Integer returnDateIndex = 0;
                for (String returnDate : returnDates) {
                    if (returnDate.length() == 0) { continue; }

                    String currentDateString = kingdomIntelBundle.get(Bundle.Keys.DATE);
                    Integer currentTick = UtopiaUtil.countTicksByDate(currentDateString);
                    Integer currentYear = Util.parseInt(currentDateString.substring(currentDateString.indexOf("YR") + 2));
                    Integer returnDateTick = UtopiaUtil.countTicksByDate(returnDate + ", YR" + currentYear);

                    if (returnDateTick < currentTick) {
                        // The return-date is in the past, so it must be the next year...
                        returnDateTick = UtopiaUtil.countTicksByDate(returnDate +", YR"+ (currentYear+1));
                    }
                    if (Math.abs(returnDateTick - currentTick) > 48) {
                        // Army cannot be out for more than 48 hours, so the army must have already returned...
                        continue;
                    }

                    Integer returnTimeInTicks = returnDateTick - currentTick;

                    Integer returnTimeSecondsFromNow = (int) (returnTimeInTicks * 60.0f * 60.0f);
                    Long returnTimeFromEpoch = (System.currentTimeMillis() / 1000L) + returnTimeSecondsFromNow;

                    provinceIntelBundle.put(returnDateArmyKeys[returnDateIndex++], returnTimeFromEpoch.toString());
                }
            */

            for (String[] activeSpellAndDuration : activeSpellsAndExpirations) {
                ProvinceIntelActiveSpellBundle activeSpellBundle = new ProvinceIntelActiveSpellBundle();
                _parseCurrentResources(activeSpellBundle, dom);
                activeSpellBundle.put(ProvinceIntelActiveSpellBundle.Keys.PROVINCE_NAME, provinceName);
                activeSpellBundle.put(ProvinceIntelActiveSpellBundle.Keys.SPELL_NAME, activeSpellAndDuration[0]);
                activeSpellBundle.put(ProvinceIntelActiveSpellBundle.Keys.SPELL_EXPIRATION, activeSpellAndDuration[1]);
                provinceIntelBundle.addToGroup(ProvinceIntelBundle.Keys.ACTIVE_SPELLS_BUNDLE, activeSpellBundle);
            }

            kingdomIntelBundle.addToGroup(KingdomIntelBundle.Keys.PROVINCES, provinceIntelBundle);
        }

        return kingdomIntelBundle;
    }

    // Parses send_armies to collect the precaculated_modifier required to calculate army-offense.
    public WarRoomBundle parseWarRoomBundle(String html) {
        Document dom = _htmlParser.parse(html);
        Elements gameContent = dom.select(".game-content");

        WarRoomBundle warRoomBundle = new WarRoomBundle();
        _parseCurrentResources(warRoomBundle, dom);

        { // Parse Precalculated Offense-Modifier
            final String precalculatedModifierStartToken = "precalculated_modifier: ";
            final String precalculatedModifierEndToken = ",";
            Integer begin;
            Integer end;
            if ((begin = html.indexOf(precalculatedModifierStartToken)) >= 0 && (end = html.indexOf(precalculatedModifierEndToken, begin)) >= 0) {
                Float offenseModifier = Util.parseFloat(html.substring(begin + precalculatedModifierStartToken.length(), end).trim());
                warRoomBundle.put(WarRoomBundle.Keys.OFFENSE_MODIFIER, offenseModifier.toString());
            }
        }

        { // Parse Target-Province-List
            Iterator<Element> provinceList = gameContent.select("#id_target_province").select("option").iterator();

            String targetKingdom = dom.select("#id_kingdom").getValue().trim();
            String targetIsland = dom.select("#id_island").getValue().trim();

            // Skip the first row...
            if (provinceList.hasNext()) {
                provinceList.next();
            }

            while (provinceList.hasNext()) {
                Element provinceOption = provinceList.next();

                String optionValue = provinceOption.getAttributeValue("value").trim();
                String optionText = provinceOption.getText();
                if (optionText.contains("---")) {
                    optionText = optionText.substring(0, optionText.indexOf("---")).trim();
                }

                ProvinceIdBundle provinceIdBundle = new ProvinceIdBundle();
                _parseCurrentResources(provinceIdBundle, dom);
                provinceIdBundle.put(ProvinceIdBundle.Keys.NAME, optionText);
                provinceIdBundle.put(ProvinceIdBundle.Keys.UTOPIA_ID, optionValue);
                provinceIdBundle.put(ProvinceIdBundle.Keys.KINGDOM, targetKingdom);
                provinceIdBundle.put(ProvinceIdBundle.Keys.ISLAND, targetIsland);

                warRoomBundle.addToGroup(WarRoomBundle.Keys.PROVINCE_LIST_BUNDLE, provinceIdBundle);
            }
        }

        Json twoColumnValues = _parseTwoColumnStats(dom);

        if (twoColumnValues.hasKey("DEPLOYABLE GENERALS")) {
            warRoomBundle.put(WarRoomBundle.Keys.DEPLOYABLE_GENERALS, Util.parseInt(twoColumnValues.get("DEPLOYABLE GENERALS", Json.Types.STRING)).toString());
        }
        if (twoColumnValues.hasKey("MINIMUM CONQUEST NW")) {
            warRoomBundle.put(WarRoomBundle.Keys.MIN_CONQUEST_NW, Util.parseInt(twoColumnValues.get("MINIMUM CONQUEST NW", Json.Types.STRING)).toString());
        }
        if (twoColumnValues.hasKey("MAXIMUM CONQUEST NW")) {
            warRoomBundle.put(WarRoomBundle.Keys.MAX_CONQUEST_NW, Util.parseInt(twoColumnValues.get("MAXIMUM CONQUEST NW", Json.Types.STRING)).toString());
        }
        if (twoColumnValues.hasKey("MERCENARY COST")) {
            warRoomBundle.put(WarRoomBundle.Keys.MERCENARY_COST, Util.parseInt(twoColumnValues.get("MERCENARY COST", Json.Types.STRING)).toString());
        }
        if (twoColumnValues.hasKey("MERCENARY RATE")) {
            String[] numeralsMap = new String[]{ "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN" };
            String mercRateString = twoColumnValues.get("MERCENARY RATE", Json.Types.STRING).trim();

            Integer mercRateNumerator = 0;
            Float mercRateDenominator = 1.0F;

            Integer numeratorStringEndIx = mercRateString.indexOf(" ");
            if (numeratorStringEndIx >= 0) {
                String mercRateNumeratorString = mercRateString.substring(0, numeratorStringEndIx).trim().toUpperCase();
                for (int i=0; i<numeralsMap.length; ++i) {
                    if (numeralsMap[i].equals(mercRateNumeratorString)) {
                        mercRateNumerator = i;
                        break;
                    }
                }
            }

            final String mercRateDenominatorStartToken = "per ";
            final String mercRateDenominatorEndToken = " Troops";
            Integer begin;
            Integer end;
            if ((begin = html.indexOf(mercRateDenominatorStartToken)) >= 0 && (end = html.indexOf(mercRateDenominatorEndToken, begin)) >= 0) {
                String mercRateDenominatorString = html.substring(begin + mercRateDenominatorStartToken.length(), end).trim();
                Float parsedMercRateDenominator = Util.parseFloat(mercRateDenominatorString);
                if (parsedMercRateDenominator > 0.0F) {
                    mercRateDenominator = parsedMercRateDenominator;
                }
            }

            Float mercRate = ((float) mercRateNumerator) / mercRateDenominator;
            warRoomBundle.put(WarRoomBundle.Keys.MERCENARY_RATE, mercRate.toString());
        }

        warRoomBundle.put(WarRoomBundle.Keys.ATTACK_TIME_PARAMS, _parseAttackTimeParameters(html).toString());

        return warRoomBundle;
    }

    public ArmyOffenseBundle parseArmyOffense(String html) {
        ArmyOffenseBundle armyOffenseBundle = new ArmyOffenseBundle();
        armyOffenseBundle.put(ArmyOffenseBundle.Keys.TOTAL_OFFENSE, Util.parseInt(html.trim()).toString());
        return armyOffenseBundle;
    }

    public AttackBundle parseAttack(String html) {
        Document dom = _htmlParser.parse(html);

        AttackBundle attackBundle = new AttackBundle();
        _parseCurrentResources(attackBundle, dom);

        Elements errors = dom.select(".errorList");
        if (errors.getCount() > 0) {
            attackBundle.put(AttackBundle.Keys.WAS_SUCCESS, "0");
            attackBundle.put(AttackBundle.Keys.RESULT_TEXT, errors.getText());
            return attackBundle;
        }

        String resultString = "";
        Boolean wasSuccess = false;
        Elements attackResults = dom.select(".message");
        for (Element result : attackResults) {
            if (result.hasClass("good")) {
                wasSuccess = true;
            }
            resultString += result.getText() +"\n";
        }
        resultString = resultString.trim();

        attackBundle.put(AttackBundle.Keys.WAS_SUCCESS, (wasSuccess ? "1" : "0") );
        attackBundle.put(AttackBundle.Keys.RESULT_TEXT, resultString);

        return attackBundle;
    }

    public AttackDragonInfoBundle parseAttackDragon(String html) {
        Document dom = _htmlParser.parse(html);

        AttackDragonInfoBundle attackDragonInfoBundle = new AttackDragonInfoBundle();
        _parseCurrentResources(attackDragonInfoBundle, dom);

        Elements gameContent = dom.select(".game-content");
        String gameContentText = gameContent.getText();

        final String healthRemainingStartToken = "We estimate him to have ";
        final String healthRemainingEndToken = " points of strength";
        Integer end;
        Integer begin;
        if ((begin = gameContentText.indexOf(healthRemainingStartToken)) >= 0 && (end = gameContentText.indexOf(healthRemainingEndToken, begin)) >= 0) {
            Integer healthRemaining = Util.parseInt(gameContentText.substring(begin + healthRemainingStartToken.length(), end));
            if (healthRemaining > 0) {
                attackDragonInfoBundle.put(AttackDragonInfoBundle.Keys.HEALTH_REMAINING, healthRemaining.toString());
            }
        }

        return attackDragonInfoBundle;
    }

    public FundDragonInfoBundle parseFundDragon(String html) {
        final Document dom = _htmlParser.parse(html);

        final FundDragonInfoBundle fundDragonInfoBundle = new FundDragonInfoBundle();
        _parseCurrentResources(fundDragonInfoBundle, dom);

        final Elements gameContent = dom.select(".game-content");
        final String gameContentText = gameContent.getText();

        final String costRemainingStartToken = "you and your fellow provinces. ";
        final String costRemainingEndToken = " gold coin"; // " gold coins are still needed";
        final Integer end;
        final Integer begin;
        if ((begin = gameContentText.indexOf(costRemainingStartToken)) >= 0 && (end = gameContentText.indexOf(costRemainingEndToken, begin)) >= 0) {
            final Integer costRemaining = Util.parseInt(gameContentText.substring(begin + costRemainingStartToken.length(), end));
            if (costRemaining > 0) {
                fundDragonInfoBundle.put(FundDragonInfoBundle.Keys.COST_REMAINING, costRemaining.toString());
            }
        }

        return fundDragonInfoBundle;
    }

    public InfiltrateThievesBundle parseInfiltrateThieves(String html) {
        Document dom = _htmlParser.parse(html);

        InfiltrateThievesBundle infiltrateThievesBundle = new InfiltrateThievesBundle();
        _parseCurrentResources(infiltrateThievesBundle, dom);

        Elements operationResults = dom.select(".message");

        String operationResultText = operationResults.getText();

        { // Parse Thieves Count
            final String startToken = "They appear to have about ";
            final String endToken = " thieves employed across their lands.";
            Integer end;
            Integer begin;
            if ((begin = operationResultText.indexOf(startToken)) >= 0 && (end = operationResultText.indexOf(endToken, begin)) >= 0) {
                Integer value = Util.parseInt(operationResultText.substring(begin + startToken.length(), end));
                infiltrateThievesBundle.put(InfiltrateThievesBundle.Keys.THIEVES, value.toString());
            }
        }

        { // Parse Confidence
            final String startToken = "Early indications show that our operation was a success and we have ";
            final String endToken = "% confidence in the information retrieved.";
            Integer end;
            Integer begin;
            if ((begin = operationResultText.indexOf(startToken)) >= 0 && (end = operationResultText.indexOf(endToken, begin)) >= 0) {
                Integer value = Util.parseInt(operationResultText.substring(begin + startToken.length(), end));
                infiltrateThievesBundle.put(InfiltrateThievesBundle.Keys.CONFIDENCE, value.toString());
            }
        }

        return infiltrateThievesBundle;
    }

    // Parses: aid
    public TradeSettingsBundle parseTradeBalance(String html) {
        Document dom = _htmlParser.parse(html);

        TradeSettingsBundle tradeSettingsBundle = new TradeSettingsBundle();
        _parseCurrentResources(tradeSettingsBundle, dom);

        Json twoColumnValues = _parseTwoColumnStats(dom);

        tradeSettingsBundle.put(TradeSettingsBundle.Keys.TRADE_BALANCE, Util.parseInt(twoColumnValues.get("CURRENT TRADE BALANCE", Json.Types.STRING)).toString());
        tradeSettingsBundle.put(TradeSettingsBundle.Keys.TAX_RATE, Util.parseInt(twoColumnValues.get("TAX RATE ON RECEIVED GOODS", Json.Types.STRING)).toString());

        // id_block_option_0 - Permitted
        // id_block_option_1 - Blocked
        Boolean aidIsBlocked = dom.select("#id_block_option_1").getAttributeValue("checked").toUpperCase().equals("CHECKED");

        tradeSettingsBundle.put(TradeSettingsBundle.Keys.AID_IS_BLOCKED, (aidIsBlocked ? "1" : "0"));

        return tradeSettingsBundle;
    }

    // Parses: build
    public BuildCostBundle parseBuildCosts(String html) {
        Document dom = _htmlParser.parse(html);

        BuildCostBundle buildCostBundle = new BuildCostBundle();
        _parseCurrentResources(buildCostBundle, dom);

        Json twoColumnValues = _parseTwoColumnStats(dom);

        buildCostBundle.put(BuildCostBundle.Keys.CONSTRUCTION_COST, Util.parseInt(twoColumnValues.get("CONSTRUCTION COST", Json.Types.STRING)).toString());
        buildCostBundle.put(BuildCostBundle.Keys.RAZE_COST, Util.parseInt(twoColumnValues.get("RAZE COST", Json.Types.STRING)).toString());
        buildCostBundle.put(BuildCostBundle.Keys.CONSTRUCTION_TIME, Util.parseInt(twoColumnValues.get("CONSTRUCTION TIME", Json.Types.STRING)).toString());
        buildCostBundle.put(BuildCostBundle.Keys.FREE_CREDITS, Util.parseInt(twoColumnValues.get("FREE BUILDING CREDITS", Json.Types.STRING)).toString());

        return buildCostBundle;
    }

    // Parses: explore
    public ExplorationCostsBundle parseExplorationCosts(String html) {
        final Document dom = _htmlParser.parse(html);

        final ExplorationCostsBundle explorationCostsBundle = new ExplorationCostsBundle();
        _parseCurrentResources(explorationCostsBundle, dom);

        final Json twoColumnValues = _parseTwoColumnStats(dom);

        explorationCostsBundle.put(ExplorationCostsBundle.Keys.SOLDIERS_PER_ACRE, Util.parseInt(twoColumnValues.get("EXPLORATION COSTS (SOLDIERS)", Json.Types.STRING)).toString());
        explorationCostsBundle.put(ExplorationCostsBundle.Keys.GOLD_PER_ACRE, Util.parseInt(twoColumnValues.get("EXPLORATION COSTS (GOLD)", Json.Types.STRING)).toString());
        explorationCostsBundle.put(ExplorationCostsBundle.Keys.AVAILABLE_ACRES, Util.parseInt(twoColumnValues.get("AVAILABLE UNCHARTED ACRES", Json.Types.STRING)).toString());
        explorationCostsBundle.put(ExplorationCostsBundle.Keys.CURRENTLY_EXPLORING, Util.parseInt(twoColumnValues.get("CURRENTLY EXPLORING", Json.Types.STRING)).toString());

        return explorationCostsBundle;
    }

    // Parses: aid
    public SendAidBundle parseSendAid(String html) {
        Document dom = _htmlParser.parse(html);

        SendAidBundle sendAidBundle = new SendAidBundle();
        _parseCurrentResources(sendAidBundle, dom);

        Elements operationResults = dom.select(".message");

        String resultText = operationResults.getText();

        String targetProvinceNameAndKingdom = Util.parseValueBetweenTokens(resultText, "to ", ". It should arrive shortly!");
        String provinceName = "";
        if (targetProvinceNameAndKingdom.contains(" (")) {
            provinceName = targetProvinceNameAndKingdom.substring(0, targetProvinceNameAndKingdom.indexOf(" ("));
        }
        String kingdom = Util.parseValueBetweenTokens(targetProvinceNameAndKingdom, " (", ":");
        String island = Util.parseValueBetweenTokens(targetProvinceNameAndKingdom, ":", ")");

        String additionalTradeBalance = Util.parseValueBetweenTokens(resultText, "shipment has added ", " gold coin");

        sendAidBundle.put(SendAidBundle.Keys.RESULT_TEXT, resultText);

        sendAidBundle.put(SendAidBundle.Keys.TARGET_PROVINCE_NAME, provinceName);
        sendAidBundle.put(SendAidBundle.Keys.TARGET_PROVINCE_KINGDOM, Util.parseInt(kingdom).toString());
        sendAidBundle.put(SendAidBundle.Keys.TARGET_PROVINCE_ISLAND, Util.parseInt(island).toString());

        sendAidBundle.put(SendAidBundle.Keys.ADDITIONAL_TRADE_BALANCE, Util.parseInt(additionalTradeBalance).toString());

        return sendAidBundle;
    }

    public PrivateMessagesBundle parsePrivateMessages(String html) {
        Document dom = _htmlParser.parse(html);

        PrivateMessagesBundle privateMessagesBundle = new PrivateMessagesBundle();
        _parseCurrentResources(privateMessagesBundle, dom);

        Elements privateMessageRows = dom.select(".game-content table tbody tr");
        for (Element privateMessageRow : privateMessageRows) {
            Elements columns = privateMessageRow.select("td");
            if (columns.getCount() != 5) {
                continue;
            }

            String messageId = columns.eq(0).select("input").getAttributeValue("value");

            String senderAndKd = columns.eq(1).getText();
            ParsedPrivateMessageProvinceData parsedPrivateMessageProvinceData = _parsePrivateMessageProvince(senderAndKd);

            String title = columns.eq(2).select("a").getText().trim();

            String utopianDate = columns.eq(3).getText().trim();
            if (utopianDate.contains(" of ")) {
                // Utopian Date is not formatted like it is in other places ("MON Y, YRZ" vs "Month Y of YRZ")
                String month = utopianDate.substring(0, 3).trim();                              // MON
                String day = Util.parseValueBetweenTokens(utopianDate, " ", " of ").trim();         // Y
                String year = utopianDate.substring(utopianDate.lastIndexOf(" YR") + 3).trim(); // Z

                utopianDate = month +" "+ day +", YR"+ year;
            }

            PrivateMessageBundle privateMessageBundle = new PrivateMessageBundle();
            _parseCurrentResources(privateMessageBundle, dom);

            privateMessageBundle.put(PrivateMessageBundle.Keys.ID, messageId);
            privateMessageBundle.put(PrivateMessageBundle.Keys.TITLE, title);
            privateMessageBundle.put(PrivateMessageBundle.Keys.SENDER, parsedPrivateMessageProvinceData.sender);
            privateMessageBundle.put(PrivateMessageBundle.Keys.SENDER_PROVINCE_KINGDOM, parsedPrivateMessageProvinceData.kingdom);
            privateMessageBundle.put(PrivateMessageBundle.Keys.SENDER_PROVINCE_ISLAND, parsedPrivateMessageProvinceData.island);
            privateMessageBundle.put(PrivateMessageBundle.Keys.UTOPIAN_DATE, utopianDate);

            privateMessagesBundle.addToGroup(PrivateMessagesBundle.Keys.PRIVATE_MESSAGES, privateMessageBundle);
        }

        return privateMessagesBundle;
    }

    public PrivateMessageBundle parsePrivateMessage(String html) {
        Document dom = _htmlParser.parse(html);

        PrivateMessageBundle privateMessageBundle = new PrivateMessageBundle();
        _parseCurrentResources(privateMessageBundle, dom);

        String messageIdUrl = dom.select(".game-content .button-link").getFirst().getAttributeValue("href");
        String messageId = messageIdUrl.substring(messageIdUrl.lastIndexOf("/", messageIdUrl.lastIndexOf("/")), messageIdUrl.lastIndexOf("/"));
        privateMessageBundle.put(PrivateMessageBundle.Keys.ID, messageId);

        Elements privateMessageMetadataRows = dom.select(".game-content .mail-view thead tr");
        if (privateMessageMetadataRows.getCount() == 5) {
            String utopianDate = privateMessageMetadataRows.eq(0).select("td").getText().trim();
            String realDate = privateMessageMetadataRows.eq(1).select("td").getText().trim();
            String senderAndKd = privateMessageMetadataRows.eq(2).select("td").getText().trim();
            String receiverAndKd = privateMessageMetadataRows.eq(3).select("td").getText().trim();
            String title = privateMessageMetadataRows.eq(4).select("td").getText().trim();

            ParsedPrivateMessageProvinceData parsedSenderProvinceData = _parsePrivateMessageProvince(senderAndKd);
            // ParsedPrivateMessageProvinceData parsedReceiverProvinceData = _parsePrivateMessageProvince(receiverAndKd);

            privateMessageBundle.put(PrivateMessageBundle.Keys.TITLE, title);
            privateMessageBundle.put(PrivateMessageBundle.Keys.SENDER, parsedSenderProvinceData.sender);
            privateMessageBundle.put(PrivateMessageBundle.Keys.SENDER_PROVINCE_KINGDOM, parsedSenderProvinceData.kingdom);
            privateMessageBundle.put(PrivateMessageBundle.Keys.SENDER_PROVINCE_ISLAND, parsedSenderProvinceData.island);
            privateMessageBundle.put(PrivateMessageBundle.Keys.UTOPIAN_DATE, utopianDate);
            privateMessageBundle.put(PrivateMessageBundle.Keys.REAL_DATE, realDate);
        }

        String content = Util.limitConsecutiveNewlines(dom.select(".game-content .mail-view tbody td").getTextWithNewlines().trim());
        privateMessageBundle.put(PrivateMessageBundle.Keys.CONTENT, content);

        return privateMessageBundle;
    }

    private class ParsedPrivateMessageProvinceData {
        public String sender;
        public String kingdom;
        public String island;
    }

    private ParsedPrivateMessageProvinceData _parsePrivateMessageProvince(String senderAndKd) {
        String sender = senderAndKd;
        String kingdom = "";
        String island = "";

        if (senderAndKd.contains("(")) {
            sender = senderAndKd.substring(0, senderAndKd.lastIndexOf("(")).trim();
            if (senderAndKd.contains(":") && senderAndKd.contains(")")) {
                kingdom = senderAndKd.substring(senderAndKd.lastIndexOf("(") + 1, senderAndKd.lastIndexOf(":")).trim();
                island = senderAndKd.substring(senderAndKd.lastIndexOf(":") + 1, senderAndKd.lastIndexOf(")")).trim();
            }
        }

        ParsedPrivateMessageProvinceData parsedPrivateMessageProvince = new ParsedPrivateMessageProvinceData();
        parsedPrivateMessageProvince.sender = sender;
        parsedPrivateMessageProvince.kingdom = kingdom;
        parsedPrivateMessageProvince.island = island;
        return parsedPrivateMessageProvince;
    }

    private void _defineScienceBookBundleMap(final Map<String, Map<String, String>> keyMap, final String scienceType, final String scienceBooksBundleKey, final String scienceEffectsBundleKey) {
        final Map<String, String> keyMapping = new HashMap<String, String>();
        keyMapping.put("scientist_count", scienceBooksBundleKey);
        keyMapping.put("effect", scienceEffectsBundleKey);
        keyMap.put(scienceType, keyMapping);
    }
    public ScienceBundle parseScience(final String html) {
        final Document dom = _htmlParser.parse(html);

        final ScienceBundle scienceBundle = new ScienceBundle();
        _parseCurrentResources(scienceBundle, dom);

        final Elements gameContent = dom.select(".game-content");

        final Map<String, Map<String, String>> scienceKeyMap = new HashMap<String, Map<String, String>>();
        _defineScienceBookBundleMap(scienceKeyMap, "alchemy",       ScienceBundle.Keys.ALCHEMY_SCIENTIST_COUNT,       ScienceBundle.Keys.ALCHEMY_EFFECT);
        _defineScienceBookBundleMap(scienceKeyMap, "tools",         ScienceBundle.Keys.TOOL_SCIENTIST_COUNT,          ScienceBundle.Keys.TOOL_EFFECT);
        _defineScienceBookBundleMap(scienceKeyMap, "housing",       ScienceBundle.Keys.HOUSING_SCIENTIST_COUNT,       ScienceBundle.Keys.HOUSING_EFFECT);
        _defineScienceBookBundleMap(scienceKeyMap, "production",    ScienceBundle.Keys.FOOD_SCIENTIST_COUNT,          ScienceBundle.Keys.FOOD_EFFECT);
        _defineScienceBookBundleMap(scienceKeyMap, "military",      ScienceBundle.Keys.MILITARY_SCIENTIST_COUNT,      ScienceBundle.Keys.MILITARY_EFFECT);
        _defineScienceBookBundleMap(scienceKeyMap, "crime",         ScienceBundle.Keys.CRIME_SCIENTIST_COUNT,         ScienceBundle.Keys.CRIME_EFFECT);
        _defineScienceBookBundleMap(scienceKeyMap, "channeling",    ScienceBundle.Keys.CHANNELING_SCIENTIST_COUNT,    ScienceBundle.Keys.CHANNELING_EFFECT);

        final Elements scienceTable = gameContent.select("table").eq(0).select("tbody");
        for (final Element row : scienceTable.select("tr")) {
            final Elements ths = row.select("th");
            final Elements tds = row.select("td");
            if (tds.getCount() < 3 || ths.getCount() < 1) {
                continue;
            }

            final String scienceType = ths.eq(0).getText().trim().toLowerCase();
            final String scientistCount = tds.eq(0).getText().trim().toLowerCase();
            final String daysOfExperience = tds.eq(1).getText().trim().toLowerCase();
            final String effect = tds.eq(2).getText().trim();

            final Map<String, String> scienceBundleKeyMap = scienceKeyMap.get(scienceType);
            final String booksBundleKey = scienceBundleKeyMap.get("scientist_count");
            final String effectBundleKey = scienceBundleKeyMap.get("effect");

            scienceBundle.put(booksBundleKey, Util.parseInt(scientistCount).toString());
            scienceBundle.put(effectBundleKey, effect);
        }

        final Elements scientistSpans = gameContent.select(".scientist-widget");
        for (final Element scientistSpan : scientistSpans) {
            final String scientistNameAndTitle = scientistSpan.select(".scientist-name").getText().trim();
            final String scientistAdvice = scientistSpan.select(".scientist-advice").getText().trim();

            final String scientistName;
            {
                if (scientistNameAndTitle.contains(" ")) {
                    scientistName = scientistNameAndTitle.substring(scientistNameAndTitle.indexOf(" ")).trim();
                }
                else {
                    scientistName = "";
                }
            }

            final String scientistTitle;
            {
                if (scientistNameAndTitle.contains(" ")) {
                    scientistTitle = scientistNameAndTitle.substring(0, scientistNameAndTitle.indexOf(" ")).trim();
                }
                else {
                    scientistTitle = "";
                }
            }

            final Integer scientistGraduationTime;
            {
                if (scientistAdvice.contains(" days")) {
                    scientistGraduationTime = Util.parseInt(scientistAdvice.substring(0, scientistAdvice.indexOf(" days")).trim());
                }
                else {
                    scientistGraduationTime = 0;
                }
            }

            final String formName = scientistSpan.select("select").getAttributeValue("name");

            final String selectedAssignment = _getSelectedOption(scientistSpan.select("select")).getAttributeValue("value").trim().toUpperCase();

            final ScientistBundle scientistsBundle = new ScientistBundle();
            _parseCurrentResources(scientistsBundle, dom);

            scientistsBundle.put(ScientistBundle.Keys.SCIENTIST_NAME, scientistName);
            scientistsBundle.put(ScientistBundle.Keys.SCIENTIST_LEVEL, scientistTitle);
            scientistsBundle.put(ScientistBundle.Keys.TICKS_UNTIL_ADVANCEMENT, scientistGraduationTime.toString());
            scientistsBundle.put(ScientistBundle.Keys.CURRENT_ASSIGNMENT, selectedAssignment);
            scientistsBundle.put(ScientistBundle.Keys.FORM_NAME, formName);

            scienceBundle.addToGroup(ScienceBundle.Keys.SCIENTISTS_GROUP, scientistsBundle);
        }

        return scienceBundle;
    }

    public ScienceResultBundle parseScienceResult(String html) {
        Document dom = _htmlParser.parse(html);

        ScienceResultBundle scienceResultBundle = new ScienceResultBundle();
        _parseCurrentResources(scienceResultBundle, dom);

        String resultString = "";
        Boolean wasSuccess = false;
        Elements operationResults = dom.select(".message");
        for (Element result : operationResults) {
            if (result.hasClass("good")) {
                wasSuccess = true;
            }
            resultString += result.getText() +"\n";
        }
        resultString = resultString.trim();

        if (wasSuccess) {
            scienceResultBundle.put(ScienceResultBundle.Keys.WAS_SUCCESS, "1");
        }
        else {
            scienceResultBundle.put(ScienceResultBundle.Keys.WAS_SUCCESS, "0");
        }

        scienceResultBundle.put(ScienceResultBundle.Keys.RESULT_TEXT, resultString);

        return scienceResultBundle;
    }

    public ForumTopicsBundle parseForumTopics(String html) {
        Document dom = _htmlParser.parse(html);

        ForumTopicsBundle forumTopicsBundle = new ForumTopicsBundle();
        _parseCurrentResources(forumTopicsBundle, dom);

        Elements topicRows = dom.select("table.mail-view tbody tr");
        for (Element topicRow : topicRows) {
            Elements tds = topicRow.select("td");

            if (tds.getCount() < 3) { continue; }

            final Element firstTd = tds.get(0);
            final Element secondTd = tds.get(1);
            final Element thirdTd = tds.get(2);

            final String topicAndCreator = firstTd.getText();
            final String url = firstTd.select("a").getAttributeValue("href");

            final String topicName = firstTd.select("a").getText();
            final String creator = topicAndCreator.substring(topicAndCreator.indexOf(topicName) + topicName.length()).trim();
            final String lastPost = secondTd.getText().trim();
            final String postCount = thirdTd.getText().trim();
            final String id = url.substring(url.lastIndexOf("/")+1);


            ForumTopicBundle forumTopicBundle = new ForumTopicBundle();
            _parseCurrentResources(forumTopicBundle, dom);
            forumTopicBundle.put(ForumTopicBundle.Keys.TITLE, topicName);
            forumTopicBundle.put(ForumTopicBundle.Keys.CREATOR, creator);
            forumTopicBundle.put(ForumTopicBundle.Keys.LAST_POST, lastPost);
            forumTopicBundle.put(ForumTopicBundle.Keys.POST_COUNT, postCount);
            forumTopicBundle.put(ForumTopicBundle.Keys.ID, id);

            forumTopicsBundle.addToGroup(ForumTopicsBundle.Keys.TOPICS, forumTopicBundle);
        }

        return forumTopicsBundle;
    }

    public ForumTopicPostsBundle parseForumTopicPosts(final String html, final String topicId) {
        final Document dom = _htmlParser.parse(html);

        final ForumTopicPostsBundle forumTopicPostsBundle = new ForumTopicPostsBundle();
        _parseCurrentResources(forumTopicPostsBundle, dom);
        forumTopicPostsBundle.put(ForumTopicPostsBundle.Keys.TOPIC_ID, topicId);

        final Elements posts = dom.select("table.mail-view");
        for (final Element post : posts) {
            final ForumTopicPostBundle postBundle = new ForumTopicPostBundle();
            _parseCurrentResources(postBundle, dom);

            final Elements headerCells = post.select("th");
            if (headerCells.getCount() != 2) { continue; }

            final Elements cells = post.select("td");
            if (cells.getCount() != 2) { continue; }

            final String postDate = headerCells.get(0).getText().trim();
            final String sequenceNumber = headerCells.get(1).getText().trim().substring(1); // Strip the proceeding "#"...

            final String provinceName = cells.get(0).select("strong").getText().trim(); // Province Name
//            final String fullPosterText = cells.get(0).getText().trim();
//            final String posterKingdomTitle = cells.get(0).select("em").getFirst().getText().trim(); // i.e. "Monarch"
//            final String poster = fullPosterText.substring(0, fullPosterText.indexOf(posterKingdomTitle)).trim();

            final String content = Util.limitConsecutiveNewlines(cells.get(1).getTextWithNewlines().trim());

            postBundle.put(ForumTopicPostBundle.Keys.SEQUENCE_NUMBER, sequenceNumber);
            postBundle.put(ForumTopicPostBundle.Keys.POST_DATE, postDate);
            postBundle.put(ForumTopicPostBundle.Keys.POSTER, provinceName);
            postBundle.put(ForumTopicPostBundle.Keys.CONTENT, content);
            postBundle.put(ForumTopicPostBundle.Keys.TOPIC_ID, topicId);

            forumTopicPostsBundle.addToGroup(ForumTopicPostsBundle.Keys.POSTS, postBundle);
        }

        return forumTopicPostsBundle;
    }

    public ProvinceIdsBundle parseAidProvinceIds(String html, Integer kingdomId, Integer islandId) {
        Document dom = _htmlParser.parse(html);

        ProvinceIdsBundle provinceIdsBundle = new ProvinceIdsBundle();
        _parseCurrentResources(provinceIdsBundle, dom);

        Elements gameContent = dom.select(".game-content");

        { // Parse Target-Province-List
            Iterator<Element> provinceList = gameContent.select("#id_target_province").select("option").iterator();

            // Skip the first row...
            if (provinceList.hasNext()) {
                provinceList.next();
            }

            while (provinceList.hasNext()) {
                Element provinceOption = provinceList.next();

                String optionValue = provinceOption.getAttributeValue("value").trim();
                String optionText = provinceOption.getText();

                ProvinceIdBundle provinceIdBundle = new ProvinceIdBundle();
                _parseCurrentResources(provinceIdBundle, dom);
                provinceIdBundle.put(ProvinceIdBundle.Keys.NAME, optionText);
                provinceIdBundle.put(ProvinceIdBundle.Keys.UTOPIA_ID, optionValue);
                provinceIdBundle.put(ProvinceIdBundle.Keys.KINGDOM, kingdomId.toString());
                provinceIdBundle.put(ProvinceIdBundle.Keys.ISLAND, islandId.toString());

                provinceIdBundle.addToGroup(ProvinceIdsBundle.Keys.PROVINCE_LIST_BUNDLE, provinceIdBundle);
            }
        }

        return provinceIdsBundle;
    }

    public String parseAds(final String html) {
        Document dom = _htmlParser.parse(html);

        final StringBuilder adScriptsStringBuilder = new StringBuilder();
        // adScriptsStringBuilder.append("<!DOCTYPE HTML><html><head><style>html, body { margin: 0px; padding: 0px; background-color: #000000; }</style></head><body><center>");

        for (final Element element : dom.select("script")) {
            final String script = element.getHtml();

            // Don't add reverb (chat) scripts...
            if (! script.contains("reverb")) {
                adScriptsStringBuilder.append(script);
            }

            if (script.contains("google_ad_width")) {
                adScriptsStringBuilder.append("<script>google_ad_width = 320; google_ad_height = 50;</script>"); // Small
                // adScriptsStringBuilder.append("<script>google_ad_width = 468; google_ad_height = 60;</script>"); // Medium
                // adScriptsStringBuilder.append("<script>google_ad_width = 728; google_ad_height = 90;</script>"); // Large
            }
        }

        for (final Element element : dom.select("div")) {
            final String divId = Util.coalesce(element.getAttributeValue("id"), "");
            if (! divId.contains("div-gpt-ad")) { continue; }

            final String adDivHtml = element.getHtml();
            adScriptsStringBuilder.append(adDivHtml);
        }

        // adScriptsStringBuilder.append("</center></body></html>");
        return adScriptsStringBuilder.toString();
    }

    private static String _trimComma(final String originString) {
        final String string = originString.trim();
        if (string.isEmpty()) { return string; }

        final Integer stringLength = string.length();

        if (string.substring(stringLength - 1).equals(",")) {
            return string.substring(0, stringLength - 1);
        }
        return string;
    }

    private Json _parseAttackTimeParameters(final String html) {
        final Json networthAttackTimes = Json.fromString("{"+ _trimComma(Util.parseValueBetweenTokens(html, "var province_nw_attack_times = {", "};")) +"}");
        final Json noNetworthAttackTimes = Json.fromString("{"+ _trimComma(Util.parseValueBetweenTokens(html, "var province_no_nw_attack_times = {", "};")) +"}");
        final Json attackTypeReductions = Json.fromString("{"+ _trimComma(Util.parseValueBetweenTokens(html, "var attack_type_reductions = {", "};")) +"}");
        final Json attackTimeModifications = Json.fromString("{"+ _trimComma(Util.parseValueBetweenTokens(html, "var attack_time_modifications = {", "};")) +"}");

        final Json attackTimeParametersJson = new Json();
        attackTimeParametersJson.put("networthAttackTimes", networthAttackTimes);
        attackTimeParametersJson.put("noNetworthAttackTimes", noNetworthAttackTimes);
        attackTimeParametersJson.put("attackTypeReductions", attackTypeReductions);
        attackTimeParametersJson.put("attackTimeModifications", attackTimeModifications);
        return attackTimeParametersJson;
    }
}
