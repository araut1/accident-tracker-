package eighteen.cmp.mani.personalsecurity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewUser extends AppCompatActivity {
   // DBAdapter db;
    DatabaseHelper db;
    TextView letstext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_user);
        db = new DatabaseHelper(ViewUser.this);
        letstext=(TextView)findViewById(R.id.textView1);
        letstext.setText("");
    }
    public void redrievedb(View view)
    {
        try{
            db.open();
            int i=1;
            Cursor c = db.getAllContacts();
            if (c.moveToFirst())
            {
                do {
                    //DisplayContact(c);
                    letstext.append("\n"+i+")"+c.getString(0)+":"+c.getString(1));
                    i+=1;

                } while (c.moveToNext());
            }
            db.close();
        }

        catch(Exception exp)
        {
            System.out.println("Message"+exp.getMessage());
        }
    }

    public void DisplayContact(Cursor c)
    {
        letstext.append(c.getString(0));

    }

}
