package com.softwareverde.utopia;

import android.content.Context;
import android.test.ActivityTestCase;

import com.softwareverde.utopia.bundle.AttackBundle;
import com.softwareverde.utopia.bundle.BuildingsBundle;
import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.DeployedArmyBundle;
import com.softwareverde.utopia.bundle.ExplorationCostsBundle;
import com.softwareverde.utopia.bundle.ForumTopicBundle;
import com.softwareverde.utopia.bundle.ForumTopicPostBundle;
import com.softwareverde.utopia.bundle.ForumTopicPostsBundle;
import com.softwareverde.utopia.bundle.ForumTopicsBundle;
import com.softwareverde.utopia.bundle.InfiltrateThievesBundle;
import com.softwareverde.utopia.bundle.KingdomBundle;
import com.softwareverde.utopia.bundle.KingdomIntelBundle;
import com.softwareverde.utopia.bundle.MilitaryBundle;
import com.softwareverde.utopia.bundle.SendAidBundle;
import com.softwareverde.utopia.bundle.SpellResultBundle;
import com.softwareverde.utopia.bundle.ThieveryOperationBundle;
import com.softwareverde.utopia.bundle.WarRoomBundle;
import com.softwareverde.utopia.parser.JsoupHtmlParser;
import com.softwareverde.utopia.parser.UtopiaParser;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

public class ParseTests extends ActivityTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    private String _getContents(int resourceId) {
        Context context = getInstrumentation().getContext();
        try {
            StringWriter writer = new StringWriter();
            InputStream iStream = context.getResources().openRawResource(resourceId);
            IOUtils.copy(iStream, writer);
            return writer.toString();
        } catch (IOException e) { }

        return "";
    }

    public void testParseSpyOnMilitaryWithArmyDeployed() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.spy_on_military_deplyed);
        MilitaryBundle milBundle = (new UtopiaParser(new JsoupHtmlParser())).parseMilitaryCouncil(data);
        Boolean isValid = milBundle.isValid();
        List<String> missingKeys = milBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("Military bundle parsed.");
    }

    public void testParseSpyOnMilitaryWithArmiesHome() {
        // Kingdom.Identifier kingdomIdentifier = new Kingdom.Identifier(9, 5);
        // PersistentStore store = new PersistentStore(_activity, "session");
        // Province targetProvince = store.getProvince("sun storms red", kingdomIdentifier.kingdomId, kingdomIdentifier.islandId);

        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.spy_on_military_home);
        MilitaryBundle milBundle = (new UtopiaParser(new JsoupHtmlParser())).parseMilitaryCouncil(data);
        Boolean isValid = milBundle.isValid();
        List<String> missingKeys = milBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("Military bundle parsed.");

        // targetProvince.update(milBundle);
        // System.out.println("Province updated.");
    }

    public void testParseAttackWhenFailed() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.attack_failure);
        AttackBundle attackBundle = (new UtopiaParser(new JsoupHtmlParser())).parseAttack(data);
        Boolean isValid = attackBundle.isValid();
        List<String> missingKeys = attackBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("Attack bundle parsed.");
    }

    public void testParseWarRoom() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.war_room);
        WarRoomBundle warRoomBundle = (new UtopiaParser(new JsoupHtmlParser())).parseWarRoomBundle(data);
        Boolean isValid = warRoomBundle.isValid();
        List<String> missingKeys = warRoomBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("War bundle parsed.");
    }

    public void testParseKingdom() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.kingdom);
        KingdomBundle kingdomBundle = (new UtopiaParser(new JsoupHtmlParser())).parseKingdom(data);
        Boolean isValid = kingdomBundle.isValid();
        List<String> missingKeys = kingdomBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("Kingdom bundle parsed.");
    }

    public void testParseSelfWarringKingdom() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.kingdom_war_self);
        KingdomBundle kingdomBundle = (new UtopiaParser(new JsoupHtmlParser())).parseKingdom(data);
        Boolean isValid = kingdomBundle.isValid();
        List<String> missingKeys = kingdomBundle.getMissingKeys();
        assertTrue(isValid);

        Integer warringKingdomId = Util.parseInt(kingdomBundle.get(KingdomBundle.Keys.WARRING_KINGDOM_KINGDOM_ID));
        Integer warringIslandId = Util.parseInt(kingdomBundle.get(KingdomBundle.Keys.WARRING_KINGDOM_ISLAND_ID));
        assertEquals((int) warringKingdomId, 8);
        assertEquals((int) warringIslandId, 5);

        System.out.println("Kingdom bundle parsed. War with: "+ warringKingdomId +", "+ warringIslandId);
    }

    public void testParseKingdomIntel() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.kingdom_intel);
        KingdomIntelBundle kingdomIntelBundle = (new UtopiaParser(new JsoupHtmlParser())).parseKingdomIntel(data);
        Boolean isValid = kingdomIntelBundle.isValid();
        List<String> missingKeys = kingdomIntelBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("Kingdom bundle parsed.");
    }

    public void testParseSelfSurvey() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.self_survey);
        BuildingsBundle buildingsBundle = (new UtopiaParser(new JsoupHtmlParser())).parseBuildingCouncil(data);
        Boolean isValid = buildingsBundle.isValid();
        List<String> missingKeys = buildingsBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("Survey bundle parsed.");
    }

    public void testParseSuccessfulOffensiveSpell() {
        final String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.sorcery_success);
        final SpellResultBundle spellResultBundle = (new UtopiaParser(new JsoupHtmlParser())).parseSpellResult(data);
        final Boolean isValid = spellResultBundle.isValid();
        final List<String> missingKeys = spellResultBundle.getMissingKeys();

        assertTrue(isValid);

        System.out.println("Spell Result bundle parsed.");
    }

    public void testParseSuccessfulThieveryOperation() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.spy_on_military_deplyed);
        ThieveryOperationBundle thieveryOperationBundle = (new UtopiaParser(new JsoupHtmlParser())).parseThieveryOperation(data);
        Boolean isValid = thieveryOperationBundle.isValid();
        List<String> missingKeys = thieveryOperationBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("Thievery operation bundle parsed.");
    }

    public void testParseSpyOnMilitaryWithTwoArmiesDeployed() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.spy_on_military_two_deplyed);
        MilitaryBundle milBundle = (new UtopiaParser(new JsoupHtmlParser())).parseMilitaryCouncil(data);
        Boolean isValid = milBundle.isValid();
        List<String> missingKeys = milBundle.getMissingKeys();
        assertTrue(isValid);

        List<Bundle> deployedArmyBundles = milBundle.getGroup(MilitaryBundle.Keys.DEPLOYED_ARMIES);
        assertEquals(deployedArmyBundles.size(), 2);

        DeployedArmyBundle firstDeployedArmyBundle = (DeployedArmyBundle) deployedArmyBundles.get(0);
        assertEquals((int) Util.parseInt(firstDeployedArmyBundle.get(DeployedArmyBundle.Keys.CAPTURED_LAND)), 91);

        DeployedArmyBundle secondDeployedArmyBundle = (DeployedArmyBundle) deployedArmyBundles.get(1);
        assertEquals((int) Util.parseInt(secondDeployedArmyBundle.get(DeployedArmyBundle.Keys.CAPTURED_LAND)), 86);

        System.out.println("Military bundle parsed.");
    }

    public void testParseWarRoomBundle() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.send_armies);
        WarRoomBundle warRoomBundle = (new UtopiaParser(new JsoupHtmlParser())).parseWarRoomBundle(data);
        Boolean isValid = warRoomBundle.isValid();
        List<String> missingKeys = warRoomBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("WarRoom bundle parsed.");
    }

    public void testParseInfiltrationOpResult() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.infiltrate_result);
        InfiltrateThievesBundle infiltrateThievesBundle = (new UtopiaParser(new JsoupHtmlParser())).parseInfiltrateThieves(data);
        Boolean isValid = infiltrateThievesBundle.isValid();
        List<String> missingKeys = infiltrateThievesBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("InfiltrateBundle bundle parsed.");
    }

    public void testParseEnemySurveyResult() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.enemy_survey);
        BuildingsBundle buildingsBundle = (new UtopiaParser(new JsoupHtmlParser())).parseBuildingCouncil(data);
        Boolean isValid = buildingsBundle.isValid();
        List<String> missingKeys = buildingsBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("Enemy survey bundle parsed.");

        // PersistentStore store = (new PersistentStore(MainActivity.currentContext, "session"));
        // Province province = store.getProvince("deadly valley", 8, 5);
        // province.update(buildingsBundle);
        // store.storeProvince(province);
    }

    public void testParseExplorationCost() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.explore);
        ExplorationCostsBundle explorationCostsBundle = (new UtopiaParser(new JsoupHtmlParser())).parseExplorationCosts(data);
        Boolean isValid = explorationCostsBundle.isValid();
        List<String> missingKeys = explorationCostsBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("Exploration Cost bundle parsed.");
    }

    public void testSendAid() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.send_aid);
        SendAidBundle sendAidBundle = (new UtopiaParser(new JsoupHtmlParser())).parseSendAid(data);
        Boolean isValid = sendAidBundle.isValid();
        List<String> missingKeys = sendAidBundle.getMissingKeys();
        assertTrue(isValid);
        System.out.println("Send-Aid bundle parsed.");
    }

    public void testParseForumTopics() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.forum_topics);
        ForumTopicsBundle forumTopicsBundle = (new UtopiaParser(new JsoupHtmlParser())).parseForumTopics(data);
        Boolean isValid = forumTopicsBundle.isValid();
        List<String> missingKeys = forumTopicsBundle.getMissingKeys();
        assertTrue(isValid);

        List<Bundle> bundles = forumTopicsBundle.getGroup(ForumTopicsBundle.Keys.TOPICS);
        assertEquals(10, bundles.size());

        final ForumTopicBundle topicBundle = (ForumTopicBundle) bundles.get(0);
        final String title = topicBundle.get(ForumTopicBundle.Keys.TITLE);
        final String creator = topicBundle.get(ForumTopicBundle.Keys.CREATOR);
        final String lastPost = topicBundle.get(ForumTopicBundle.Keys.LAST_POST);
        final String postCount = topicBundle.get(ForumTopicBundle.Keys.POST_COUNT);
        final String postId = topicBundle.get(ForumTopicBundle.Keys.ID);

        assertEquals("your thougts", title);
        assertEquals("Count Gloria the Sorcerer of The homeless Templar", creator);
        assertEquals("March 12 of YR13", lastPost);
        assertEquals("11", postCount);
        assertEquals("10546", postId);

        System.out.println("Forum-Topics bundle parsed.");
    }

    public void testParseForumTopicPosts() {
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.forum_post);
        ForumTopicPostsBundle forumTopicPostsBundle = (new UtopiaParser(new JsoupHtmlParser())).parseForumTopicPosts(data, "");
        forumTopicPostsBundle.put(ForumTopicPostsBundle.Keys.TOPIC_ID, "0"); // Done via UtopiaUtil...
        Boolean isValid = forumTopicPostsBundle.isValid();
        List<String> missingKeys = forumTopicPostsBundle.getMissingKeys();
        assertTrue(isValid);

        List<Bundle> bundles = forumTopicPostsBundle.getGroup(ForumTopicPostsBundle.Keys.POSTS);
        assertEquals(10, bundles.size());

        final ForumTopicPostBundle topicBundle = (ForumTopicPostBundle) bundles.get(0);
        final String sequenceNumber = topicBundle.get(ForumTopicPostBundle.Keys.SEQUENCE_NUMBER);
        final String postDate = topicBundle.get(ForumTopicPostBundle.Keys.POST_DATE);
        final String poster = topicBundle.get(ForumTopicPostBundle.Keys.POSTER);
        final String content = topicBundle.get(ForumTopicPostBundle.Keys.CONTENT);

        assertEquals("1", sequenceNumber);
        assertEquals("February 13 of YR13", postDate);
        assertEquals("Count Gloria the Sorcerer of The homeless Templar", poster);
        assertEquals("be nice, but brutal hurnest", content);

        System.out.println("Forum-Topic-Posts bundle parsed.");
    }
}

