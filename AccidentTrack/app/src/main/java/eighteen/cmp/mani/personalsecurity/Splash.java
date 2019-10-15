package eighteen.cmp.mani.personalsecurity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    long delay=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Timer t=new Timer();
        TimerTask tt =new TimerTask() {
            @Override
            public void run() {

                Intent i=new Intent(Splash.this,Login.class);
                startActivity(i);
                finish();
            }
        };
        t.schedule(tt,delay);
    }
}
