package noelc.mypray.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import noelc.mypray.R;


public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Intent intent;
        if (isLogin()) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();


    }

    private boolean isLogin() {
        boolean isLogin = false;


        return isLogin;
    }

}
