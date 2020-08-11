import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "persons.db";
    private static final String VERSON = "1";
    private static final String TABLE_NAME = "table_person";
    private static final String ID = "_id";
    private static final String NAME = "name";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, Integer.parseInt(VERSON));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" + "," + NAME
                + " CHAR(10) )";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
