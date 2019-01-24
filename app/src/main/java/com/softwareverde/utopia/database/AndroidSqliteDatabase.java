package com.softwareverde.utopia.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndroidSqliteDatabase implements Database {
    public static class AndroidSqliteRow implements Database.Row {
        public static AndroidSqliteRow fromCursor(final Cursor cursor) {
            final AndroidSqliteRow androidSqliteRow = new AndroidSqliteRow();

            for (final String columnName : cursor.getColumnNames()) {
                final String columnValue = cursor.getString(cursor.getColumnIndex(columnName));

                androidSqliteRow._columnNames.add(columnName);
                androidSqliteRow._columnValues.put(columnName, columnValue);
            }


            return androidSqliteRow;
        }

        private List<String> _columnNames = new ArrayList<String>();
        private Map<String, String> _columnValues = new HashMap<String, String>();

        private AndroidSqliteRow() { }

        @Override
        public List<String> getColumnNames() {
            return new ArrayList<String>(_columnNames);
        }

        @Override
        public String getValue(final String columnName) {
            if (! _columnValues.containsKey(columnName)) {
                throw new RuntimeException("Row does not contain column: "+ columnName);
            }

            return _columnValues.get(columnName);
        }
    }

    private class AndroidDatabaseHelper extends SQLiteOpenHelper {
        private final Integer _requiredDatabaseVersion;
        private Integer _currentVersion;

        public AndroidDatabaseHelper(final Context context, final String databaseName, final Integer requiredDatabaseVersion) {
            super(context, databaseName, null, requiredDatabaseVersion);
            _requiredDatabaseVersion = requiredDatabaseVersion;
            _currentVersion = requiredDatabaseVersion;
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            _currentVersion = null;
        }

        @Override
        public void onUpgrade(final SQLiteDatabase db, int oldVersion, int newVersion) {
            _currentVersion = oldVersion;
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            _currentVersion = oldVersion;
        }

        public Integer getVersion() {
            return _currentVersion;
        }

        public void setVersion(final Integer newVersion) {
            _currentVersion = newVersion;
        }

        public Boolean shouldBeCreated() {
            return (_currentVersion == null);
        }

        public Boolean shouldBeUpgraded() {
            return (_currentVersion != null && _currentVersion < _requiredDatabaseVersion);
        }

        public Boolean shouldBeDowngraded() {
            return (_currentVersion != null && _currentVersion > _requiredDatabaseVersion);
        }
    }

    private final Context _context;
    private final String _databaseName;
    private final AndroidDatabaseHelper _sqlDbHelper;
    private final SQLiteDatabase _sqliteDatabase;

    public AndroidSqliteDatabase(final Context context, final String databaseName, final Integer requiredDatabaseVersion) {
        _context = context;
        _databaseName = databaseName;
        _sqlDbHelper = new AndroidDatabaseHelper(_context, _databaseName, requiredDatabaseVersion);
        _sqliteDatabase = _sqlDbHelper.getWritableDatabase();
    }

    @Override
    public void executeDdl(String query) {
        _sqliteDatabase.execSQL(query);
    }

    @Override
    public void executeSql(String query, String[] parameters) {
        _sqliteDatabase.execSQL(query, parameters);
    }

    @Override
    public List<Row> query(String query, String[] parameters) {
        final Cursor cursor = _sqliteDatabase.rawQuery(query, parameters);
        final List<Row> results = new ArrayList<Row>(cursor.getCount());

        Integer rowNumber = 0;
        if (cursor.moveToFirst()) {
            do {
                results.add(rowNumber, AndroidSqliteRow.fromCursor(cursor));
                rowNumber += 1;
            } while (cursor.moveToNext());
        }

        cursor.close();

        return results;
    }

    @Override
    public Long getInsertId() {
        Long id = 0L;
        final Cursor cursor = _sqliteDatabase.rawQuery("SELECT last_insert_rowid()", null);
        if (cursor.moveToFirst()) {
            id = cursor.getLong(0);
        }
        cursor.close();

        return id;
    }

    @Override
    public Integer getVersion() {
        return _sqlDbHelper.getVersion();
    }

    @Override
    public void setVersion(final Integer newVersion) {
        _sqlDbHelper.setVersion(newVersion);
    }

    @Override
    public Boolean shouldBeCreated() {
        return _sqlDbHelper.shouldBeCreated();
    }

    @Override
    public Boolean shouldBeUpgraded() {
        return _sqlDbHelper.shouldBeUpgraded();
    }

    @Override
    public Boolean shouldBeDowngraded() {
        return _sqlDbHelper.shouldBeDowngraded();
    }
}
