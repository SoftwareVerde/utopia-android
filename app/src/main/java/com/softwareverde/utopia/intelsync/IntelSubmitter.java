package com.softwareverde.utopia.intelsync;

import com.softwareverde.util.Util;
import com.softwareverde.util.WebRequest;
import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.UtopiaUtil;
import com.softwareverde.utopia.bundle.ActiveSpellsBundle;
import com.softwareverde.utopia.bundle.AttackBundle;
import com.softwareverde.utopia.bundle.AttackDragonInfoBundle;
import com.softwareverde.utopia.bundle.BuildingsBundle;
import com.softwareverde.utopia.bundle.KingdomBundle;
import com.softwareverde.utopia.bundle.KingdomIntelBundle;
import com.softwareverde.utopia.bundle.MilitaryBundle;
import com.softwareverde.utopia.bundle.NewspaperBundle;
import com.softwareverde.utopia.bundle.ScienceBundle;
import com.softwareverde.utopia.bundle.SendAidBundle;
import com.softwareverde.utopia.bundle.SpellResultBundle;
import com.softwareverde.utopia.bundle.StateCouncilBundle;
import com.softwareverde.utopia.bundle.ThieveryOperationBundle;
import com.softwareverde.utopia.bundle.ThroneBundle;

import java.util.LinkedList;
import java.util.List;

public class IntelSubmitter {
    private Runnable _onIntelSubmitBeginCallback = null;
    private Runnable _onIntelSubmitEndCallback = null;
    private List<IntelSync> _intelSyncs = new LinkedList<IntelSync>();

    private void _executeIntelCallback(final Runnable callback) {
        if (callback != null) {
            (new Thread() {
                @Override
                public void run() {
                    callback.run();
                }
            }).start();
        }
    }

    private void _submitIntel(final String html, final IntelSync.Extra extra) {
        for (final IntelSync intelSync : _intelSyncs) {
            if (intelSync.canProcessIntel(html, extra)) {
                _executeIntelCallback(_onIntelSubmitBeginCallback);
                intelSync.submitIntel(html, extra, new IntelSync.Callback() {
                    @Override
                    public void run(IntelSync.Response response) {
                        _executeIntelCallback(_onIntelSubmitEndCallback);
                    }
                });
            }
        }
    }

    public void submitThroneIntel(final WebRequest request, final ThroneBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.SELF_THRONE;
        extra.targetProvinceName = bundle.get(ThroneBundle.Keys.PROVINCE_NAME);
        extra.targetKingdomId = Util.parseInt(bundle.get(ThroneBundle.Keys.KINGDOM));
        extra.targetIslandId = Util.parseInt(bundle.get(ThroneBundle.Keys.ISLAND));
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void submitNewsIntel(final WebRequest request, final NewspaperBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.SELF_NEWS;
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void submitBuildingsIntel(final WebRequest request, final BuildingsBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.SELF_SURVEY;
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void submitMilitaryIntel(final WebRequest request, final MilitaryBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.SELF_MILITARY;
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void submitAttackDragonIntel(final WebRequest request, final AttackDragonInfoBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.DRAGON;
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void submitActiveSpellsIntel(final WebRequest request, final ActiveSpellsBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.SELF_ACTIVE_SPELLS;
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void submitStateCouncilIntel(final WebRequest request, final StateCouncilBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.STATE_COUNCIL;
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void submitKingdomIntel(final WebRequest request, final Kingdom.Identifier targetKingdomIdentifier, final KingdomIntelBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.KINGDOM;
        extra.targetKingdomId = targetKingdomIdentifier.getKingdomId();
        extra.targetIslandId = targetKingdomIdentifier.getIslandId();
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void submitKingdomIntel(final WebRequest request, final Kingdom.Identifier targetKingdomIdentifier, final KingdomBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.KINGDOM;
        extra.targetKingdomId = targetKingdomIdentifier.getKingdomId();
        extra.targetIslandId = targetKingdomIdentifier.getIslandId();
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void submitAttackIntel(final WebRequest request, final UtopiaUtil.Attack attack, final AttackBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final Province targetProvince = attack.getTargetProvince();

        final IntelSync.Extra extra = new IntelSync.Extra();
        extra.wasSuccess = (Util.parseInt(bundle.get(AttackBundle.Keys.WAS_SUCCESS)) > 0);
        extra.resultText = bundle.get(AttackBundle.Keys.RESULT_TEXT);
        extra.intelType = IntelSync.IntelType.ATTACK;
        extra.targetProvinceName = targetProvince.getName();
        extra.targetKingdomId = targetProvince.getKingdomIdentifier().getKingdomId();
        extra.targetIslandId = targetProvince.getKingdomIdentifier().getIslandId();
        extra.generalsSent = attack.getArmy().getGenerals();
        extra.offenseSent = attack.getCalculatedOffense();
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void submitCastSpellIntel(final WebRequest request, final SpellResultBundle spellResultBundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final Boolean wasSuccess = (Util.parseInt(spellResultBundle.get(SpellResultBundle.Keys.WAS_SUCCESS)) > 0);
        final String resultText = spellResultBundle.get(SpellResultBundle.Keys.RESULT_TEXT);
        final String targetProvinceName = spellResultBundle.get(SpellResultBundle.Keys.TARGET_PROVINCE_NAME);
        final Integer targetKingdom = Util.parseInt(spellResultBundle.get(SpellResultBundle.Keys.TARGET_KINGDOM));
        final Integer targetIsland = Util.parseInt(spellResultBundle.get(SpellResultBundle.Keys.TARGET_ISLAND));
        final String spellIdentifier = spellResultBundle.get(SpellResultBundle.Keys.SPELL_IDENTIFIER);

        final IntelSync.Extra extra = new IntelSync.Extra();

        if (Util.parseInt(spellResultBundle.get(SpellResultBundle.Keys.IS_DEFENSIVE_SPELL)) > 0) {
            extra.intelType = IntelSync.IntelType.SELF_SPELL;
        }
        else {
            extra.intelType = IntelSync.IntelType.COMBAT_SPELL;
        }

        extra.wasSuccess = wasSuccess;
        extra.targetKingdomId = targetKingdom;
        extra.targetIslandId = targetIsland;
        extra.targetProvinceName = targetProvinceName;
        extra.spellIdentifier = spellIdentifier;
        extra.resultText = resultText;
        extra.url = url;
        extra.bundle = spellResultBundle;

        _submitIntel(html, extra);
    }

    public void submitThieveryOperationIntel(final WebRequest request, final ThieveryOperationBundle thieveryOperationBundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final Boolean wasSuccess = (Util.parseInt(thieveryOperationBundle.get(ThieveryOperationBundle.Keys.WAS_SUCCESS)) > 0);
        final String resultText = thieveryOperationBundle.get(ThieveryOperationBundle.Keys.RESULT_TEXT);
        final String targetProvinceName = thieveryOperationBundle.get(ThieveryOperationBundle.Keys.TARGET_PROVINCE_NAME);
        final Integer targetKingdom = Util.parseInt(thieveryOperationBundle.get(ThieveryOperationBundle.Keys.TARGET_KINGDOM));
        final Integer targetIsland = Util.parseInt(thieveryOperationBundle.get(ThieveryOperationBundle.Keys.TARGET_ISLAND));
        final String operationIdentifier = thieveryOperationBundle.get(ThieveryOperationBundle.Keys.OPERATION_IDENTIFIER);
        final Integer thievesSent = Util.parseInt(thieveryOperationBundle.get(ThieveryOperationBundle.Keys.THIEVES_SENT));

        IntelSync.Extra extra = new IntelSync.Extra();
        extra.wasSuccess = wasSuccess;
        extra.resultText = resultText;
        extra.targetKingdomId = targetKingdom;
        extra.targetIslandId = targetIsland;
        extra.targetProvinceName = targetProvinceName;
        extra.intelType = IntelSync.IntelType.THIEVERY_OPERATION;
        extra.thievesSent = thievesSent;
        extra.operationIdentifier = operationIdentifier;
        extra.url = url;
        extra.bundle = thieveryOperationBundle;

        _submitIntel(html, extra);
    }

    public void submitSendAidIntel(final WebRequest request, final SendAidBundle sendAidBundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        final IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.AID;
        extra.targetProvinceName = sendAidBundle.get(SendAidBundle.Keys.TARGET_PROVINCE_NAME);
        extra.targetKingdomId = Util.parseInt(sendAidBundle.get(SendAidBundle.Keys.TARGET_PROVINCE_KINGDOM));
        extra.targetIslandId = Util.parseInt(sendAidBundle.get(SendAidBundle.Keys.TARGET_PROVINCE_ISLAND));
        extra.url = url;
        extra.bundle = sendAidBundle;

        _submitIntel(html, extra);
    }

    public void submitScienceIntel(final WebRequest request, final ScienceBundle bundle) {
        if (! request.hasResult()) { return; }

        final String url = request.getUrl();
        final String html = request.getRawResult();

        IntelSync.Extra extra = new IntelSync.Extra();
        extra.intelType = IntelSync.IntelType.SELF_SCIENCE;
        // extra.targetProvinceName = scienceBundle.get(ScienceBundle.Keys.PROVINCE_NAME);
        // extra.targetKingdom = Util.parseInt(scienceBundle.get(ScienceBundle.Keys.KINGDOM));
        // extra.targetIsland = Util.parseInt(scienceBundle.get(ScienceBundle.Keys.ISLAND));
        extra.url = url;
        extra.bundle = bundle;

        _submitIntel(html, extra);
    }

    public void addIntelSync(final IntelSync intelSync) { _intelSyncs.add(intelSync); }
    public void removeIntelSync(final IntelSync intelSync) { _intelSyncs.remove(intelSync); }
    public void clearIntelSyncs() { _intelSyncs.clear(); }

    public void setIntelSubmitBeginCallback(final Runnable callback) { _onIntelSubmitBeginCallback = callback; }
    public void setIntelSubmitEndCallback(final Runnable callback) { _onIntelSubmitEndCallback = callback; }
}
