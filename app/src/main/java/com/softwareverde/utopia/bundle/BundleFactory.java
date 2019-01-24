package com.softwareverde.utopia.bundle;

import com.softwareverde.util.Json;

import java.util.HashMap;
import java.util.Map;

public class BundleFactory {
    private static final Map<String, Class<? extends Bundle>> BUNDLE_TYPE_CLASSES = new HashMap<String, Class<? extends Bundle>>();
    static {
        BUNDLE_TYPE_CLASSES.put(ActiveSpellBundle.BUNDLE_TYPE, ActiveSpellBundle.class);
        BUNDLE_TYPE_CLASSES.put(ActiveSpellsBundle.BUNDLE_TYPE, ActiveSpellsBundle.class);
        BUNDLE_TYPE_CLASSES.put(ArmyOffenseBundle.BUNDLE_TYPE, ArmyOffenseBundle.class);
        BUNDLE_TYPE_CLASSES.put(AttackBundle.BUNDLE_TYPE, AttackBundle.class);
        BUNDLE_TYPE_CLASSES.put(AttackDragonInfoBundle.BUNDLE_TYPE, AttackDragonInfoBundle.class);
        BUNDLE_TYPE_CLASSES.put(AvailableSpellsBundle.BUNDLE_TYPE, AvailableSpellsBundle.class);
        BUNDLE_TYPE_CLASSES.put(AvailableThieveryOperationBundle.BUNDLE_TYPE, AvailableThieveryOperationBundle.class);
        BUNDLE_TYPE_CLASSES.put(AvailableThieveryOperationsBundle.BUNDLE_TYPE, AvailableThieveryOperationsBundle.class);
        BUNDLE_TYPE_CLASSES.put(BuildCostBundle.BUNDLE_TYPE, BuildCostBundle.class);
        BUNDLE_TYPE_CLASSES.put(BuildingBundle.BUNDLE_TYPE, BuildingBundle.class);
        BUNDLE_TYPE_CLASSES.put(BuildingInProgressBundle.BUNDLE_TYPE, BuildingInProgressBundle.class);
        BUNDLE_TYPE_CLASSES.put(BuildingsBundle.BUNDLE_TYPE, BuildingsBundle.class);
        BUNDLE_TYPE_CLASSES.put(Bundle.BUNDLE_TYPE, Bundle.class);
        BUNDLE_TYPE_CLASSES.put(ChatMessageBundle.BUNDLE_TYPE, ChatMessageBundle.class);
        BUNDLE_TYPE_CLASSES.put(ChatroomBundle.BUNDLE_TYPE, ChatroomBundle.class);
        BUNDLE_TYPE_CLASSES.put(DeployedArmyBundle.BUNDLE_TYPE, DeployedArmyBundle.class);
        BUNDLE_TYPE_CLASSES.put(DraftRateBundle.BUNDLE_TYPE, DraftRateBundle.class);
        BUNDLE_TYPE_CLASSES.put(ExplorationCostsBundle.BUNDLE_TYPE, ExplorationCostsBundle.class);
        BUNDLE_TYPE_CLASSES.put(ForumTopicBundle.BUNDLE_TYPE, ForumTopicBundle.class);
        BUNDLE_TYPE_CLASSES.put(ForumTopicPostBundle.BUNDLE_TYPE, ForumTopicPostBundle.class);
        BUNDLE_TYPE_CLASSES.put(ForumTopicPostsBundle.BUNDLE_TYPE, ForumTopicPostsBundle.class);
        BUNDLE_TYPE_CLASSES.put(ForumTopicsBundle.BUNDLE_TYPE, ForumTopicsBundle.class);
        BUNDLE_TYPE_CLASSES.put(FundDragonInfoBundle.BUNDLE_TYPE, FundDragonInfoBundle.class);
        BUNDLE_TYPE_CLASSES.put(InfiltrateThievesBundle.BUNDLE_TYPE, InfiltrateThievesBundle.class);
        BUNDLE_TYPE_CLASSES.put(KingdomBundle.BUNDLE_TYPE, KingdomBundle.class);
        BUNDLE_TYPE_CLASSES.put(KingdomIntelBundle.BUNDLE_TYPE, KingdomIntelBundle.class);
        BUNDLE_TYPE_CLASSES.put(KingdomProvinceBundle.BUNDLE_TYPE, KingdomProvinceBundle.class);
        BUNDLE_TYPE_CLASSES.put(MilitaryBundle.BUNDLE_TYPE, MilitaryBundle.class);
        BUNDLE_TYPE_CLASSES.put(MilitaryInProgressBundle.BUNDLE_TYPE, MilitaryInProgressBundle.class);
        BUNDLE_TYPE_CLASSES.put(MilitarySettingsBundle.BUNDLE_TYPE, MilitarySettingsBundle.class);
        BUNDLE_TYPE_CLASSES.put(NewsBundle.BUNDLE_TYPE, NewsBundle.class);
        BUNDLE_TYPE_CLASSES.put(NewspaperBundle.BUNDLE_TYPE, NewspaperBundle.class);
        BUNDLE_TYPE_CLASSES.put(PrivateMessageBundle.BUNDLE_TYPE, PrivateMessageBundle.class);
        BUNDLE_TYPE_CLASSES.put(PrivateMessagesBundle.BUNDLE_TYPE, PrivateMessagesBundle.class);
        BUNDLE_TYPE_CLASSES.put(ProvinceIdBundle.BUNDLE_TYPE, ProvinceIdBundle.class);
        BUNDLE_TYPE_CLASSES.put(ProvinceIdsBundle.BUNDLE_TYPE, ProvinceIdsBundle.class);
        BUNDLE_TYPE_CLASSES.put(ProvinceIntelActiveSpellBundle.BUNDLE_TYPE, ProvinceIntelActiveSpellBundle.class);
        BUNDLE_TYPE_CLASSES.put(ProvinceIntelBundle.BUNDLE_TYPE, ProvinceIntelBundle.class);
        BUNDLE_TYPE_CLASSES.put(ScienceBundle.BUNDLE_TYPE, ScienceBundle.class);
        BUNDLE_TYPE_CLASSES.put(ScienceResultBundle.BUNDLE_TYPE, ScienceResultBundle.class);
        BUNDLE_TYPE_CLASSES.put(ScientistBundle.BUNDLE_TYPE, ScientistBundle.class);
        BUNDLE_TYPE_CLASSES.put(SendAidBundle.BUNDLE_TYPE, SendAidBundle.class);
        BUNDLE_TYPE_CLASSES.put(SpellBundle.BUNDLE_TYPE, SpellBundle.class);
        BUNDLE_TYPE_CLASSES.put(SpellResultBundle.BUNDLE_TYPE, SpellResultBundle.class);
        BUNDLE_TYPE_CLASSES.put(StateCouncilBundle.BUNDLE_TYPE, StateCouncilBundle.class);
        BUNDLE_TYPE_CLASSES.put(StateHistoryBundle.BUNDLE_TYPE, StateHistoryBundle.class);
        BUNDLE_TYPE_CLASSES.put(ThieveryOperationBundle.BUNDLE_TYPE, ThieveryOperationBundle.class);
        BUNDLE_TYPE_CLASSES.put(ThroneBundle.BUNDLE_TYPE, ThroneBundle.class);
        BUNDLE_TYPE_CLASSES.put(TradeSettingsBundle.BUNDLE_TYPE, TradeSettingsBundle.class);
        BUNDLE_TYPE_CLASSES.put(WarRoomBundle.BUNDLE_TYPE, WarRoomBundle.class);
        BUNDLE_TYPE_CLASSES.put(DeployedArmiesBundle.BUNDLE_TYPE, DeployedArmiesBundle.class);
    }

    private Bundle _getBundleInstance(final String bundleType) {
        try {
            return BUNDLE_TYPE_CLASSES.get(bundleType).newInstance();
        }
        catch (Exception e) {
            System.out.println("NOTICE: Unknown Bundle Type: "+ bundleType);
            return new Bundle();
        }
    }

    public Bundle createBundle(final String bundleJsonAsString) { return _createBundle(Json.fromString(bundleJsonAsString)); }
    public Bundle createBundle(final Json bundleJson) { return _createBundle(bundleJson); }
    private Bundle _createBundle(final Json bundleJson) {
        final String bundleType = bundleJson.get("TYPE", Json.Types.STRING);

        final Bundle bundle = _getBundleInstance(bundleType);

        { // Values
            final Json values = bundleJson.get("VALUES");
            for (final String key : values.getKeys()) {
                final String value = values.get(key, Json.Types.STRING);
                bundle.put(key, value);
            }
        }

        { // Bundles
            final Json bundles = bundleJson.get("BUNDLES");
            for (final String key : bundles.getKeys()) {
                final Json subJsonBundle = bundles.get(key);
                final Bundle subBundle = this.createBundle(subJsonBundle);
                bundle.put(key, subBundle);
            }
        }

        { // Grouped Bundles
            final Json groupedBundles = bundleJson.get("GROUPED_BUNDLES");
            for (final String groupKey : groupedBundles.getKeys()) {
                final Json subJsonBundles = groupedBundles.get(groupKey);
                for (Integer i=0; i<subJsonBundles.length(); ++i) {
                    final Json subJsonBundle = subJsonBundles.get(i);
                    final Bundle subBundle = this.createBundle(subJsonBundle);
                    bundle.addToGroup(groupKey, subBundle);
                }
            }
        }

        return bundle;
    }
}