package noelc.mypray.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import noelc.mypray.R;
import noelc.mypray.common.Argument;
import noelc.mypray.customclass.PrayEvent;

/**
 * Created by noelchew on 6/13/15.
 */
public class OrderSuccessActivity extends ActionBarActivity {

    private ImageView ivPic;
    private Button btnInvite, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        setToolbar();
        setView();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.text_view_title);
        ImageView ivLeft = (ImageView) toolbar.findViewById(R.id.image_view_left);
        ImageView ivRight = (ImageView) toolbar.findViewById(R.id.image_view_right);
        ivRight.setVisibility(View.GONE);

        tvTitle.setText("Order Success");

        ivLeft.setImageResource(R.drawable.mypray_back);
//        ivLeft.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setSupportActionBar(toolbar);

    }

    private void setView() {

        ivPic = (ImageView) findViewById(R.id.image_view_event);
        btnInvite = (Button) findViewById(R.id.button_invite);
        btnHome = (Button) findViewById(R.id.button_home);

        btnInvite.setOnClickListener(btnInviteOnClickListener);
        btnHome.setOnClickListener(btnHomeOnClickListener);

        PrayEvent prayEvent;
        String prayEventString = getIntent().getExtras().getString(Argument.PRAY_EVENT);
        if (prayEventString != null && !TextUtils.isEmpty(prayEventString)) {
            prayEvent = PrayEvent.deserialise(prayEventString);
            ivPic.setImageResource(prayEvent.getPictureResourceId());
            ivPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    private View.OnClickListener btnInviteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PrayEvent prayEvent;
            String prayEventString = getIntent().getExtras().getString(Argument.PRAY_EVENT);
            if (prayEventString != null && !TextUtils.isEmpty(prayEventString)) {
                prayEvent = PrayEvent.deserialise(prayEventString);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/html");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Please join me in the " + prayEvent.getTitle() + " event on " + prayEvent.getDate());
                startActivity(Intent.createChooser(shareIntent, "Invite using"));
            }
        }
    };

    private View.OnClickListener btnHomeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(OrderSuccessActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}