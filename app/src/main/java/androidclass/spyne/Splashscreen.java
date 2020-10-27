package androidclass.spyne;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by hp on 10/25/2020.
 */

public class Splashscreen extends AppCompatActivity  {
    private static int SPLASH_TIME_OUT = 3000;//in ms
    TextView txtmessage;
    Animation animblinks;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        txtmessage = (TextView) findViewById(R.id.fb);
        animblinks = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);

        // start the animation
        txtmessage.startAnimation(animblinks);

        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splashscreen.this, Login.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
