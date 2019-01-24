package com.softwareverde.utopia.database;

import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Province;

public interface UtopiaDatabase {
    void storeProvince(Province province);
    void storeKingdom(Kingdom kingdom);

    Integer getProvinceUtopiaId(Province province);
    Province getProvince(String provinceName, Integer kingdomId, Integer islandId);

    void clear();
}
