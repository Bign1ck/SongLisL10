package sg.edu.rp.c346.id22005813.songlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_YEAR + " TEXT, "
                + COLUMN_STARS + " INTEGER)";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        // Create table(s) again
        onCreate(db);
    }

    public void insertTask(String title, String name, String year, int stars) {

        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the 2nd as value
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STARS, stars);
        // Insert the row into the TABLE_TASK
        db.insert(TABLE_TASK, null, values);
        // Close the database connection
        db.close();
    }

    public ArrayList<String> getTaskContent() {
        // Create an ArrayList that holds String objects
        ArrayList<String> tasks = new ArrayList<String>();
        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_TITLE, COLUMN_NAME, COLUMN_YEAR, COLUMN_STARS};
        // Run the query and get back the Cursor object
        Cursor cursor = db.query(TABLE_TASK, columns, null, null, null, null, null, null);

        // moveToFirst() moves to first row, null if no records
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row
            //  and returns true; moveToNext() returns false
            //  when no more next row to move to
            do {
                // Add the task content to the ArrayList object
                //  getString(0) retrieves first column data
                //  getString(1) return second column data
                //  getInt(0) if data is an integer value
                tasks.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return tasks;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_TITLE, COLUMN_NAME, COLUMN_YEAR, COLUMN_STARS};
        String orderBy = " ASC";
        boolean asc = false;
        if (!asc) {
            orderBy = " DESC";
        }
        Cursor cursor = db.query(TABLE_TASK, columns, null, null, null, null, COLUMN_NAME + orderBy, null);

        int columnIndexTitle = cursor.getColumnIndex(COLUMN_TITLE);
        int columnIndexName = cursor.getColumnIndex(COLUMN_NAME);
        int columnIndexYear = cursor.getColumnIndex(COLUMN_YEAR);
        int columnIndexStars = cursor.getColumnIndex(COLUMN_STARS);

        while (cursor.moveToNext()) {
            String title = cursor.getString(columnIndexTitle);
            String name = cursor.getString(columnIndexName);
            String year = cursor.getString(columnIndexYear);
            int stars = cursor.getInt(columnIndexStars);
            Task obj = new Task(title, name, year, stars);
            tasks.add(obj);
        }

        cursor.close();
        db.close();
        return tasks;
    }

    public void insertTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, task.getTitle());
        values.put(COLUMN_NAME, task.getSinger());
        values.put(COLUMN_YEAR, task.getYear());
        values.put(COLUMN_STARS, task.getRating());
        db.insert(TABLE_TASK, null, values);
        db.close();
    }
}