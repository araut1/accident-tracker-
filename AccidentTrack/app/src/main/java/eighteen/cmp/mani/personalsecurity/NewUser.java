package eighteen.cmp.mani.personalsecurity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends AppCompatActivity {
    EditText edName,edMobilenum;
    String name,mobilenum;
  //  DBAdapter db;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        edName=(EditText)findViewById(R.id.editText1);
        edMobilenum=(EditText)findViewById(R.id.editText2);
        db = new DatabaseHelper(NewUser.this);
    }
    public void Registeron_db(View view)
    {
        name=edName.getText().toString();
        mobilenum=edMobilenum.getText().toString();

        db.open();

        db.Complaints(name,mobilenum);

        db.close();

        Toast.makeText(NewUser.this,"New User Registered", Toast.LENGTH_LONG).show();


    }
}
