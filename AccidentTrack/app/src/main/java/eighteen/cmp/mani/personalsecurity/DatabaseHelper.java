package eighteen.cmp.mani.personalsecurity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper {
	public static final String KEY_ID = "Id";
	public static final String KEY_FIRST = "Name";
	public static final String KEY_SECOND = "Password";

	public static final String KEY_FOURTH = "City";
	public static final String KEY_FIFTH = "State";

    public static final String one = "Name";
    public static final String two = "PhNo";

	public static final String nine = "comment";
	private static final String DATABASE_NAME = "REDD";

	private static final String DATABASE_TABLE1 = "UserRegister";
	private static final String DATABASE_TABLE2 = "Emergency";
	
	private static final int DATABASE_VERSION = 3;
	private DbHelper dbh;
	private final Context context;
	private static SQLiteDatabase ourdatabase;
	
	private static final String DATABASE_CREATE1 ="create table "+DATABASE_TABLE1+"("
			+ KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_FIRST+" TEXT NOT NULL, "+KEY_SECOND+" TEXT NOT NULL, "
			+KEY_FOURTH+" TEXT NOT NULL, "
			+KEY_FIFTH+" TEXT NOT NULL);";
	
	private static final String DATABASE_CREATE2 ="create table "+DATABASE_TABLE2+"("	+ KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ one+" TEXT NOT NULL,"
            + two+" TEXT NOT NULL);";

	public DatabaseHelper(Context c){
		this.context=c;
	}
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null,DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
		

		@Override
		public void onCreate(SQLiteDatabase arg0) {
			// TODO Auto-generated method stub
			
			arg0.execSQL(DATABASE_CREATE1);
			arg0.execSQL(DATABASE_CREATE2);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			arg0.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE1);
			onCreate(arg0);
		}
		
	}
	
	public DatabaseHelper open(){
		dbh=new DbHelper(context);
		ourdatabase=dbh.getWritableDatabase();
		return this;
	}
	public void close(){
		dbh.close();
	}
	
	public long UserRegister(String name, String s2, String s4, String s5) {
		// TODO Auto-generated method stub
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FIRST, name);
		initialValues.put(KEY_SECOND, s2);


		initialValues.put(KEY_FOURTH, s4);
		initialValues.put(KEY_FIFTH, s5);


		
		return ourdatabase.insert(DATABASE_TABLE1, null, initialValues);
	}

    public long Complaints(String name, String s2) {
        // TODO Auto-generated method stub
        ContentValues initialValues = new ContentValues();
        initialValues.put(one, name);
        initialValues.put(two, s2);


        return ourdatabase.insert(DATABASE_TABLE2, null, initialValues);
    }

    public ArrayList<String> getDetails() {

        String[] colname=new String[]{one,two};

        Cursor c=ourdatabase.query(DATABASE_TABLE2, colname,null,null, null,null,null,null);
        ArrayList<String> arr=new ArrayList<String>();

        int date=c.getColumnIndex(one);
        int detail=c.getColumnIndex(two);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            arr.add("Name			          :"+c.getString(date)+"\n"+
                    "Phone Number	:"+c.getString(detail)
                   );
        }
        return arr;
    }




    public Cursor getAllContacts()
    {
        return ourdatabase.query(DATABASE_TABLE2, new String[] {one,two},null,null, null, null, null, null);
    }
	public Cursor getAllContacts1()
	{
		return ourdatabase.query(DATABASE_TABLE2, new String[] {two},null,null, null, null, null, null);
	}

    public Cursor getAllTemp()
    {
        return ourdatabase.query(DATABASE_TABLE1, new String[] {one,two},
                null,null, null, null, null, null);
    }
	/*public String retrieveage(String name){
		String pass="";
		String[] colname=new String[]{KEY_THIRD};
		String[] s=new String[]{name};
		Cursor c = ourdatabase.query(DATABASE_TABLE1, colname, KEY_FIRST + "=?",s, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			pass=c.getString(0);
		}
		return pass;

	}*/


    public String retrievepass(String name){
        String pass="";
        String[] colname=new String[]{KEY_SECOND};
        String[] s=new String[]{name};
        Cursor c = ourdatabase.query(DATABASE_TABLE1, colname, KEY_FIRST + "=?",s, null, null, null, null);
        if(c!=null){
            c.moveToFirst();
            pass=c.getString(0);
        }
        return pass;

    }
	public void delete(String id) {
		String[] s=new String[]{id};
		ourdatabase.delete(DATABASE_TABLE2, two + " =?", s);
	}
}

