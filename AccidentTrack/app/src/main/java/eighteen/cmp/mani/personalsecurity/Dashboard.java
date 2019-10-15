package eighteen.cmp.mani.personalsecurity;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    LinearLayout admin,user,temp,em;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        user=(LinearLayout)findViewById(R.id.viewtask);
       /* admin=(TextView)findViewById(R.id.admin);
        temp=(TextView)findViewById(R.id.temp);*/
        em=(LinearLayout)findViewById(R.id.addtask);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this,AccidentDetection.class);
                startActivity(i);

                Intent intent=new Intent(Dashboard.this,Accelarometerservice.class);
                startService(intent);
            }
        });
       /* admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this,HeartBeat.class);
                startActivity(i);
            }
        });
       temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this,Temparature.class);
                startActivity(i);
            }
        }); */
        runTime();
        em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this,Emergency.class);
                startActivity(i);
            }
        });
    }
    public void runTime(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
            Toast.makeText(this,"granted",Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }
    }
}
