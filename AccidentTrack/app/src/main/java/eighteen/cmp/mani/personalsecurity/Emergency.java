package eighteen.cmp.mani.personalsecurity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Emergency extends AppCompatActivity {
   // DBAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency);
     //   db = new DBAdapter(Emergency.this);
    }

    public void newusers(View view)
    {
        Intent ymmint=new Intent(Emergency.this,NewUser.class);
        startActivity(ymmint);
    }

    public void viewusers(View view)
    {
        Intent ymmint=new Intent(Emergency.this,ViewUser.class);
        startActivity(ymmint);

    }

    public void deletetable(View view)
    {
        Intent ymmint=new Intent(Emergency.this,DeleteUser.class);
        startActivity(ymmint);
       /* db.open();

        db.deleteTable();

        db.close();

        Toast.makeText(Emergency.this,"Emergency User Values Cleared",Toast.LENGTH_LONG).show();
        Toast.makeText(Emergency.this,"No Emergency users",Toast.LENGTH_LONG).show();
*/

    }

   /* public void deletparticular(View view)
    {

        Intent myint=new Intent(Emergency.this,DeleteUser.class);
        startActivity(myint);
    }*/


}
