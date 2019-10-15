package eighteen.cmp.mani.personalsecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class Register extends AppCompatActivity {
    EditText un,pass,age,city,state,bg,hh;
    Button reg;
    String otp_res;
    String user,pwd,agee,cityy,statee;
    DatabaseHelper dbh;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;
    public final static String PHNO="phno";
    public final static String HEALTH="hh";

    final static public String BG="bg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        un=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.pass);

        city=(EditText)findViewById(R.id.city);
        state=(EditText)findViewById(R.id.state);

        reg=(Button)findViewById(R.id.register);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=un.getText().toString();

                pwd=pass.getText().toString();

                cityy=city.getText().toString();
                statee=state.getText().toString();


                if(un.getText().toString().contentEquals("") && pass.getText().toString().contentEquals("") && city.getText().toString().contentEquals("") &&
                        state.getText().toString().contentEquals("")  ){
                    Toast.makeText(Register.this,
                            "Enter Details first", Toast.LENGTH_LONG)
                            .show();
                }
                else if(un.getText().toString().contentEquals("")){
                    Toast.makeText(Register.this,
                            "Enter Username", Toast.LENGTH_LONG)
                            .show();
                }
                else if(pass.getText().toString().contentEquals("")){
                    Toast.makeText(Register.this,
                            "Enter Password", Toast.LENGTH_LONG)
                            .show();
                }


                else if(city.getText().toString().contentEquals("")){
                    Toast.makeText(Register.this,
                            "Enter City", Toast.LENGTH_LONG)
                            .show();
                }
                else if(state.getText().toString().contentEquals("")){
                    Toast.makeText(Register.this,
                            "Enter State", Toast.LENGTH_LONG)
                            .show();
                }


                else {
                    dbh = new DatabaseHelper(Register.this);
                    dbh.open();

                    dbh.UserRegister(user,pwd,cityy,statee);


                    Toast.makeText(Register.this,
                            "Congrats: Register Successfull", Toast.LENGTH_LONG)
                            .show();

                    Intent ii = new Intent(Register.this,Login.class);
                    startActivity(ii);
                    dbh.close();


                }










            }
        });

    }
}
