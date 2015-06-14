package noelc.mypray.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import noelc.mypray.R;
import noelc.mypray.adapter.PrayerItemListAdapter;
import noelc.mypray.common.Argument;
import noelc.mypray.customclass.PrayEvent;
import noelc.mypray.customclass.PrayItem;

/**
 * Created by noelchew on 6/13/15.
 */
public class BuyActivity extends ActionBarActivity {

    private ListView listView;
    private PrayerItemListAdapter adapter;
    private Button btnCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
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

        tvTitle.setText("Order");

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
        listView = (ListView) findViewById(R.id.list_view_buy);
        btnCheckOut = (Button) findViewById(R.id.button_check_out);

        btnCheckOut.setOnClickListener(btnCheckOutOnClickListener);
    }

    private void init() {
        String prayEventJson = getIntent().getExtras().getString(Argument.PRAY_EVENT);
        if (prayEventJson != null && !TextUtils.isEmpty(prayEventJson)) {
            PrayEvent prayEvent = PrayEvent.deserialise(prayEventJson);

            adapter = new PrayerItemListAdapter(this, 0, new ArrayList<PrayItem>(prayEvent.getPrayItems()));
            listView.setAdapter(adapter);
        }
    }

    private View.OnClickListener btnCheckOutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BuyActivity.this);
            builder.setTitle("Checkout Order?")
                    .setMessage("Price including shipment (after 30% discount): " + adapter.getTotalPriceAfterDiscount(30))
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("Confirm",new  DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(BuyActivity.this, OrderSuccessActivity.class);
                            intent.putExtra(Argument.PRAY_EVENT, getIntent().getExtras().getString(Argument.PRAY_EVENT));
                            startActivity(intent);
                            finish();
                        }
                    }).show();

        }
    };
}