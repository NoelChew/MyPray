package noelc.mypray.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import noelc.mypray.R;


public class LoginActivity extends Activity {

    View vLogin;
//    ImageView ivFbLogin, ivGPlusLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setView();
    }

    private void setView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

//        ivFbLogin = (ImageView) findViewById(R.id.image_view_fblogin);
//        ivGPlusLogin = (ImageView) findViewById(R.id.image_view_gpluslogin);
//
//        ivFbLogin.setOnClickListener(onClickListener);
//        ivGPlusLogin.setOnClickListener(onClickListener);

        vLogin = (View) findViewById(R.id.view_login);
        vLogin.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1500);

        }
    };

    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }
}
