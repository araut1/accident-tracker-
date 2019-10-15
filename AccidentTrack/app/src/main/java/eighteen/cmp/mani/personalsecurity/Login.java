package eighteen.cmp.mani.personalsecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity  {
    EditText e1,e2;
    Button b1,reg;
    String name,pass;
    final static public String NAME="name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        e1=(EditText)findViewById(R.id.un);
        e2=(EditText)findViewById(R.id.pd);
        b1=(Button)findViewById(R.id.login);
        reg=(Button)findViewById(R.id.reg);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name= e1.getText().toString();
                pass= e2.getText().toString();
               /* Intent ii = new Intent(Login.this,Dashboard.class);
                startActivity(ii);*/
                if(name.contentEquals("") && pass.contentEquals("")){

                    Toast.makeText(Login.this,"Enter the details", Toast.LENGTH_LONG).show();
                }else{
                    DatabaseHelper dbh = new DatabaseHelper(Login.this);
                    dbh.open();
                    String gpass= dbh.retrievepass(name);
                    //region = dbh.retrievereg(name);
                    if(pass.contentEquals(gpass)){
//				Intent ii = new Intent(this,GenerateQRCode.class);
                        Intent ii = new Intent(Login.this,Dashboard.class);
                        startActivity(ii);
                        Toast.makeText(Login.this,"Login successfully...", Toast.LENGTH_LONG).show();
                        SharedPreferences sp=getSharedPreferences(NAME,0 );
                        SharedPreferences.Editor  se=sp.edit();
                        se.putString("name",e1.getText().toString());
                        se.apply();

                    }else{
                        Toast.makeText(Login.this,"Authentication Failure", Toast.LENGTH_LONG).show();
                    }
                }
                e1.setText("");
                e2.setText("");
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(Login.this,Register.class);
                startActivity(ii);
            }
        });
    }
}
