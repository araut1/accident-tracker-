package eighteen.cmp.mani.personalsecurity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBAdapter {
	
	public static final String KEY_NAME = "appname";
	public static final String KEY_ELAPS = "elapsedtime";
	
	
	public static final String ID = "Id";
	public static final String FIRST = "Heart";
	public static final String SECOND = "Body";
	
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "MyDB";
	private static final String DATABASE_TABLE = "appevoluationtable";
	
	
	private static final String DATABASE_TABLE1 = "temptable";
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_CREATE ="create table appevoluationtable ( "
					+ "appname text not null, elapsedtime text not null);";
	
	private static final String DATABASE_CREATE1 ="create table temptable ( "
			+ "Id INTEGER PRIMARY KEY AUTOINCREMENT,Heart text not null, Body text not null);";
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			try {
				db.execSQL(DATABASE_CREATE);
				db.execSQL(DATABASE_CREATE1);
				System.out.println("successfully db created");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}
	
	
	//---opens the database---
	public DBAdapter open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	
	//---closes the database---
	public void close()
	{
		DBHelper.close();
	}
	
	
	//---insert a contact into the database---
	public long insertContact(String appname, String elapse)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, appname);
		initialValues.put(KEY_ELAPS, elapse);
		System.out.println("successfully db inserted"+appname+":"+elapse);
		return db.insert(DATABASE_TABLE, null, initialValues);
				
	}
	
	
	public long insertTemp(String appname, String elapse)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(FIRST, appname);
		initialValues.put(SECOND, elapse);
		System.out.println("successfully db inserted"+appname+":"+elapse);
		return db.insert(DATABASE_TABLE1, null, initialValues);
				
	}

	public ArrayList<String> getDetails() {

		String[] colname=new String[]{KEY_NAME,KEY_ELAPS};

		Cursor c=db.query(DATABASE_TABLE, colname, null,null, null,null,null,null);
		ArrayList<String> arr=new ArrayList<String>();

		int date=c.getColumnIndex(KEY_NAME);
		int detail=c.getColumnIndex(KEY_ELAPS);

		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			arr.add("Name			          :"+c.getString(date)+"\n"+
					"Phone Number	:"+c.getString(detail)+"\n"
					);
		}
		return arr;
	}
	
	public Cursor getAllContacts()
	{
		return db.query(DATABASE_TABLE, new String[] {KEY_NAME,KEY_ELAPS}, 
				null,null, null, null, null, null);
	}
	
	public Cursor getAllTemp()
	{
		return db.query(DATABASE_TABLE1, new String[] {FIRST,SECOND}, 
				null,null, null, null, null, null);
	}


	public void deleteTable() {
		// TODO Auto-generated method stub
		db.execSQL("delete from "+ DATABASE_TABLE);
		
	}

	public void delete(String id) {
		String[] s = new String[] { id };
		db.delete(DATABASE_TABLE, KEY_ELAPS + " = ?", s);
	}
	public void delete1(String id) {
		String[] s=new String[]{id};
		db.delete(DATABASE_TABLE,KEY_ELAPS + " = ?", s);
	}


	//---retrieves a particular contact---
	
	
}