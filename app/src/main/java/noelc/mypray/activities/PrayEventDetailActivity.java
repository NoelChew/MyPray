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
public class PrayEventDetailActivity extends ActionBarActivity {

    private ImageView ivPic;
    private TextView tvTitle1, tvDescription, tvDate;
    private Button btnOrder;

    private PrayEvent mPrayEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pray_event_detail);
        setToolbar();
        setView();
        init();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.text_view_title);
        ImageView ivLeft = (ImageView) toolbar.findViewById(R.id.image_view_left);
        ImageView ivRight = (ImageView) toolbar.findViewById(R.id.image_view_right);
        ivRight.setVisibility(View.GONE);

        tvTitle.setText("Event Details");

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
        tvTitle1 = (TextView) findViewById(R.id.text_view_title1);
        tvDate = (TextView) findViewById(R.id.text_view_date);
        tvDescription = (TextView) findViewById(R.id.text_view_description);
//        btnInvite = (Button) findViewById(R.id.button_invite);
        btnOrder = (Button) findViewById(R.id.button_buy);

//        btnInvite.setOnClickListener(btnInviteOnClickListener);
        btnOrder.setOnClickListener(btnOrderOnClickListener);

    }

    private void init() {
        String prayEventJson = getIntent().getExtras().getString(Argument.PRAY_EVENT);

        if (prayEventJson != null && !TextUtils.isEmpty(prayEventJson)) {
            mPrayEvent = PrayEvent.deserialise(prayEventJson);

            ivPic.setImageResource(mPrayEvent.getPictureResourceId());
            tvTitle1.setText(mPrayEvent.getTitle());
            tvDescription.setText(mPrayEvent.getDescription());
            tvDate.setText(mPrayEvent.getDate() + " (" + mPrayEvent.getLunarDate() + ")");
        }

    }

    private View.OnClickListener btnInviteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener btnOrderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PrayEventDetailActivity.this, BuyActivity.class);
            intent.putExtra(Argument.PRAY_EVENT, getIntent().getExtras().getString(Argument.PRAY_EVENT));
            startActivity(intent);
        }
    };

}
