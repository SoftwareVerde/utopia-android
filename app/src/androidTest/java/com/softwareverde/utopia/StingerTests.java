package com.softwareverde.utopia;

import android.content.Context;
import android.test.ActivityTestCase;

import com.softwareverde.util.WebRequest;
import com.softwareverde.utopia.bundle.AttackBundle;
import com.softwareverde.utopia.bundle.SendAidBundle;
import com.softwareverde.utopia.bundle.ThieveryOperationBundle;
import com.softwareverde.utopia.bundle.ThroneBundle;
import com.softwareverde.utopia.intelsync.IntelSubmitter;
import com.softwareverde.utopia.intelsync.IntelSync;
import com.softwareverde.utopia.intelsync.StingerUtil;
import com.softwareverde.utopia.parser.JsoupHtmlParser;
import com.softwareverde.utopia.parser.UtopiaParser;
import com.softwareverde.utopia.util.android.AndroidBuildVersion;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class StingerTests extends ActivityTestCase {
    private Context _context;
    private UtopiaUtil _utopiaUtil;
    private IntelSubmitter _intelSubmitter;
    private StingerUtil _subject;
    private Province _province;
    private Province _targetProvince;
    private TestComplete _testComplete;

    class TestComplete {
        Boolean isComplete = false;
        Boolean wasSuccess = false;
        UtopiaUtil.Callback callback;

        void waitForCompletion() {
            try {
                while (! this.isComplete) {
                    Thread.sleep(100);
                }
            } catch (Exception e) { }
        }
    }

    class MockWebRequest extends WebRequest {
        String mockRawResult;
        String mockUrl;

        @Override
        public String getRawResult() {
            return mockRawResult;
        }

        @Override
        public String getUrl() {
            return mockUrl;
        }

        @Override
        public boolean hasResult() {
            return mockRawResult != null;
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        _context = getInstrumentation().getContext();
        _utopiaUtil = UtopiaUtil.getInstance();
        _intelSubmitter = new IntelSubmitter();

        final StingerUtil.Dependencies stingerDependencies = new StingerUtil.Dependencies();
        stingerDependencies.setBuildVersion(new AndroidBuildVersion(_context));
        stingerDependencies.setHtmlParser(new JsoupHtmlParser());
        _subject = new StingerUtil(stingerDependencies);

        _subject.setSubdomain("utopia25");
        _subject.login("joshmg", "password", null);
        _subject.setProvinceData(new IntelSync.ProvinceData("Province", 1, 1));

        _intelSubmitter.addIntelSync(_subject);
        _utopiaUtil.setIntelSubmitter(_intelSubmitter);

        _province = new Province();
        _province.setName("Province");
        _province.setKingdomIdentifier(new Kingdom.Identifier(1, 1));
        _targetProvince = new Province();
        _targetProvince.setName("Province");
        _targetProvince.setKingdomIdentifier(new Kingdom.Identifier(1, 1));

        _testComplete = new TestComplete();
        _testComplete.callback = new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                System.out.print(response.getErrorMessage());

                _testComplete.isComplete = true;
                _testComplete.wasSuccess = response.getWasSuccess();
            }
        };
    }

    private String _getContents(int resourceId) {
        try {
            StringWriter writer = new StringWriter();
            InputStream iStream = _context.getResources().openRawResource(resourceId);
            IOUtils.copy(iStream, writer);
            return writer.toString();
        } catch (IOException e) { }

        return "";
    }

    public void testSubmitFailedAttack() {
        // Setup
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.attack_failure);
        AttackBundle attackBundle = (new UtopiaParser(new JsoupHtmlParser())).parseAttack(data);

        MockWebRequest request = new MockWebRequest();
        request.mockUrl = Settings.getAttackUrl();
        request.mockRawResult = data;

        UtopiaUtil.Attack attack = new UtopiaUtil.Attack(_province);
        attack.setType(UtopiaUtil.Attack.Type.TRADITIONAL_MARCH);
        attack.setTargetProvince(_targetProvince);
        attack.setTime(UtopiaUtil.Attack.Time.DEFAULT);

        UtopiaUtil.Army army = new UtopiaUtil.Army();
        army.setGenerals(1);
        army.setElites(2);
        army.setMercenaries(3);
        army.setHorses(4);
        army.setOffensiveUnits(5);
        army.setPrisoners(6);
        army.setSoldiers(100);

        attack.setArmy(army);
        attack.setCalculatedOffense(1024);

        // Action
        try {
            //                                                             _submitAttackIntel   WebRequest,       Attack,                  AttackBundle,       Callback
            Method submitIntel = _utopiaUtil.getClass().getDeclaredMethod("_submitAttackIntel", WebRequest.class, UtopiaUtil.Attack.class, AttackBundle.class, UtopiaUtil.Callback.class);
            submitIntel.setAccessible(true);
            submitIntel.invoke(_utopiaUtil, request, attack, attackBundle, _testComplete.callback);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        _testComplete.waitForCompletion();

        // Assert
        assertTrue(_testComplete.isComplete);
        assertTrue(_testComplete.wasSuccess);
    }

    public void testSubmitThrone() {
        // Setup
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.throne);

        MockWebRequest request = new MockWebRequest();
        request.mockUrl = Settings.getThroneUrl();
        request.mockRawResult = data;

        ThroneBundle throneBundle = new ThroneBundle();
        throneBundle.put(ThroneBundle.Keys.PROVINCE_NAME, _province.getName());
        throneBundle.put(ThroneBundle.Keys.KINGDOM, ""+ _province.getKingdomIdentifier().getKingdomId());
        throneBundle.put(ThroneBundle.Keys.ISLAND, ""+ _province.getKingdomIdentifier().getIslandId());

        // Action
        try {
            //                                                             _submitThroneIntel   WebRequest,       ThroneBundle,       Callback
            Method submitIntel = _utopiaUtil.getClass().getDeclaredMethod("_submitThroneIntel", WebRequest.class, ThroneBundle.class, UtopiaUtil.Callback.class);
            submitIntel.setAccessible(true);
            submitIntel.invoke(_utopiaUtil, request, throneBundle, _testComplete.callback);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        _testComplete.waitForCompletion();

        // Assert
        assertTrue(_testComplete.isComplete);
        assertTrue(_testComplete.wasSuccess);
    }

    public void testSubmitSurvey() {
        // Setup
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.self_survey);

        MockWebRequest request = new MockWebRequest();
        request.mockUrl = Settings.getBuildingsUrl();
        request.mockRawResult = data;

        final TestComplete testComplete = new TestComplete();
        UtopiaUtil.Callback callback = new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                System.out.print(response.getErrorMessage());

                testComplete.isComplete = true;
                testComplete.wasSuccess = response.getWasSuccess();
            }
        };

        // Action
        try {
            //                                                             _submitBuildingsIntel   WebRequest,       Callback
            Method submitIntel = _utopiaUtil.getClass().getDeclaredMethod("_submitBuildingsIntel", WebRequest.class, UtopiaUtil.Callback.class);
            submitIntel.setAccessible(true);
            submitIntel.invoke(_utopiaUtil, request, callback);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        testComplete.waitForCompletion();

        // Assert
        assertTrue(testComplete.isComplete);
        assertTrue(testComplete.wasSuccess);
    }

    public void testSubmitAttackDragon() {
        // Setup
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.attack_dragon);

        MockWebRequest request = new MockWebRequest();
        request.mockUrl = Settings.getAttackDragonUrl();
        request.mockRawResult = data;

        final TestComplete testComplete = new TestComplete();
        UtopiaUtil.Callback callback = new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                System.out.print(response.getErrorMessage());

                testComplete.isComplete = true;
                testComplete.wasSuccess = response.getWasSuccess();
            }
        };

        // Action
        try {
            //                                                             _submitAttackDragonIntel   WebRequest,       Callback
            Method submitIntel = _utopiaUtil.getClass().getDeclaredMethod("_submitAttackDragonIntel", WebRequest.class, UtopiaUtil.Callback.class);
            submitIntel.setAccessible(true);
            submitIntel.invoke(_utopiaUtil, request, callback);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        testComplete.waitForCompletion();

        // Assert
        assertTrue(testComplete.isComplete);
        assertTrue(testComplete.wasSuccess);
    }

    public void testSubmitSpyOnMilitary() {
        // Setup
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.spy_on_military_deplyed);
        ThieveryOperationBundle thieveryOperationBundle = (new UtopiaParser(new JsoupHtmlParser())).parseThieveryOperation(data);

        MockWebRequest request = new MockWebRequest();
        request.mockUrl = Settings.getThieveryUrl();
        request.mockRawResult = data;

        final TestComplete testComplete = new TestComplete();
        UtopiaUtil.Callback callback = new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                System.out.print(response.getErrorMessage());

                testComplete.isComplete = true;
                testComplete.wasSuccess = response.getWasSuccess();
            }
        };

        // Action
        try {
            //                                                             _submitAttackDragonIntel        WebRequest,       ThieveryOperationBundle,       Callback
            Method submitIntel = _utopiaUtil.getClass().getDeclaredMethod("_submitThieveryOperationIntel", WebRequest.class, ThieveryOperationBundle.class, UtopiaUtil.Callback.class);
            submitIntel.setAccessible(true);
            submitIntel.invoke(_utopiaUtil, request, thieveryOperationBundle, callback);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        testComplete.waitForCompletion();

        // Assert
        assertTrue(testComplete.isComplete);
        assertTrue(testComplete.wasSuccess);
    }

    public void testSubmitSendAid() {
        // Setup
        String data = _getContents(com.softwareverde.utopia.debug.test.R.raw.send_aid);
        SendAidBundle sendAidBundle = (new UtopiaParser(new JsoupHtmlParser())).parseSendAid(data);

        MockWebRequest request = new MockWebRequest();
        request.mockUrl = Settings.getAidUrl();
        request.mockRawResult = data;

        final TestComplete testComplete = new TestComplete();
        UtopiaUtil.Callback callback = new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                System.out.print(response.getErrorMessage());

                testComplete.isComplete = true;
                testComplete.wasSuccess = response.getWasSuccess();
            }
        };

        // Action
        try {
            //                                                             _submitSendAidIntel   WebRequest,       SendAidBundle,       Callback
            Method submitIntel = _utopiaUtil.getClass().getDeclaredMethod("_submitSendAidIntel", WebRequest.class, SendAidBundle.class, UtopiaUtil.Callback.class);
            submitIntel.setAccessible(true);
            submitIntel.invoke(_utopiaUtil, request, sendAidBundle, callback);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        testComplete.waitForCompletion();

        // Assert
        assertTrue(testComplete.isComplete);
        assertTrue(testComplete.wasSuccess);
    }
}

