package eighteen.cmp.mani.personalsecurity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class DeleteUser extends AppCompatActivity {
  //  DBAdapter db;
    DatabaseHelper db;
    String[] users = new String[20];
ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_user);

        lv=(ListView)findViewById(R.id.listView1);
        db = new DatabaseHelper(DeleteUser.this);
        db.open();
        List<String> data=db.getDetails();

        ArrayAdapter<String> ad= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        lv.setAdapter(ad);

        db.close();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                String item=arg0.getItemAtPosition(arg2).toString();
                String[] res=item.split("\n");
                String[] r=res[1].split(":");

                Toast.makeText(DeleteUser.this,"res"+r[1],Toast.LENGTH_SHORT).show();
                DatabaseHelper dbh1=new DatabaseHelper(DeleteUser.this);
                dbh1.open();
                dbh1.delete(r[1]);
                Toast.makeText(DeleteUser.this,"deleted",Toast.LENGTH_SHORT).show();
                Intent ii = new Intent(DeleteUser.this,DeleteUser.class);
                startActivity(ii);
                dbh1.close();
            }
        });



       /* db = new DBAdapter(DeleteUser.this);
        viewuseronlistview();
        final ListView listView = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.activity_list_item, android.R.id.text1, users);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String item=parent.getItemAtPosition(position).toString();
                // ListView Clicked item index
                int itemPosition     = position;
                String[] res=item.split(":");
                Toast.makeText(DeleteUser.this,"res1"+res[1],Toast.LENGTH_SHORT).show();
                DBAdapter dbh1=new DBAdapter(DeleteUser.this);
                dbh1.open();
                //dbh1.delete(res[1]);
                Toast.makeText(DeleteUser.this,"deleted",Toast.LENGTH_SHORT).show();
                Intent ii = new Intent(DeleteUser.this,DeleteUser.class);
                startActivity(ii);
                // ListView Clicked item value

                // Show Alert
                dbh1.close();

            }

        });*/
    }

    public void viewuseronlistview() {
        try {
            db.open();
            int i = 0;
            Cursor c = db.getAllContacts();
            if (c.moveToFirst()) {
                do {
                    // DisplayContact(c);
                    // letstext.append("\n"+i+")"+c.getString(0)+":"+c.getString(1));
                    users[i] = c.getString(0) + ":" + c.getString(1);
                    i += 1;

                } while (c.moveToNext());
            }
            db.close();


        }

        catch (Exception exp) {
            System.out.println("Message" + exp.getMessage());
        }

    }

}
