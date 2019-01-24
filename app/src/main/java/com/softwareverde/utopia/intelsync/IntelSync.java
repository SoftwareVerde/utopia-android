package com.softwareverde.utopia.intelsync;

import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.bundle.Bundle;

public interface IntelSync {
    enum IntelSyncType {
        UMUNK, STINGER, UPOOPU, VERDE
    }

    enum IntelType {
        SELF_THRONE, SELF_SURVEY, SELF_SCIENCE, SELF_MILITARY, SELF_SPELL, SELF_ACTIVE_SPELLS,
        THIEVERY_OPERATION, COMBAT_SPELL, ATTACK, AID, DRAGON,
        KINGDOM_NEWS, KINGDOM, SELF_NEWS, STATE_COUNCIL
    }

    class Extra {
        public IntelType intelType;
        // OperationType operationType;

        public Boolean wasSuccess;
        public String resultText;
        public String targetProvinceName;
        public Integer targetKingdomId;
        public Integer targetIslandId;
        public String spellIdentifier;
        public String operationIdentifier;
        public Integer thievesSent;
        public Integer generalsSent;
        public Integer offenseSent;
        public String url;
        public Bundle bundle;

        public Boolean hasTargetSet() {
            return (
                this.targetProvinceName != null && this.targetIslandId != null && this.targetKingdomId != null &&
                (! this.targetProvinceName.trim().isEmpty()) && this.targetIslandId > 0 && this.targetKingdomId > 0
            );
        }
    }

    class Response {
        private Boolean _wasSuccess = false;
        private String _errorMessage = null;

        public Response() { }
        public Response(Boolean wasSuccess, String errorMessage) {
            _wasSuccess = wasSuccess;
            _errorMessage = errorMessage;
        }

        public Boolean getWasSuccess() { return _wasSuccess; }
        public String getErrorMessage() {
            if (_wasSuccess) return null;
            return _errorMessage;
        }
    }

    interface Callback {
        void run(Response response);
    }

    class ProvinceData {
        public String provinceName;
        public Integer kingdomId;
        public Integer islandId;

        public ProvinceData() { }
        public ProvinceData(final String provinceName, final Integer kingdomId, final Integer islandId) {
            this.provinceName = provinceName;
            this.kingdomId = kingdomId;
            this.islandId = islandId;
        }
        public ProvinceData(final Province province) {
            this.provinceName = province.getName();

            final Kingdom.Identifier kingdomIdentifier = province.getKingdomIdentifier();
            if (kingdomIdentifier != null) {
                this.kingdomId = kingdomIdentifier.getKingdomId();
                this.islandId = kingdomIdentifier.getIslandId();
            }
        }
    }

    void setProvinceData(ProvinceData provinceData);

    void login(String username, String password, Callback callback);

    Boolean canProcessIntel(String html, Extra extra);
    void submitIntel(String html, Extra extra, Callback callback);

    Boolean isLoggedIn();

    void setSubdomain(String subdomain);
}
