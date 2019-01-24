package com.softwareverde.utopia.database;

import java.util.List;

public interface Database {
    interface Row {
        List<String> getColumnNames();
        String getValue(String columnName);
    }

    void executeDdl(String query);
    void executeSql(String query, String[] parameters);
    List<Row> query(String query, String[] parameters);
    Long getInsertId();

    Integer getVersion();
    void setVersion(Integer newVersion);

    Boolean shouldBeCreated();
    Boolean shouldBeUpgraded();
    Boolean shouldBeDowngraded();
}