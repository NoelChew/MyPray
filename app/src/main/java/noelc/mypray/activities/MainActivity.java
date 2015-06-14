package noelc.mypray.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import noelc.mypray.R;
import noelc.mypray.common.Argument;
import noelc.mypray.customclass.PrayEvent;
import noelc.mypray.customclass.PrayItem;
import noelc.mypray.fragments.MainFragment;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        Fragment fm = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, fm).commit();

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.text_view_title);
        ImageView ivLeft = (ImageView) toolbar.findViewById(R.id.image_view_left);
        ImageView ivRight = (ImageView) toolbar.findViewById(R.id.image_view_right);

        tvTitle.setText(getString(R.string.app_name));
        ivLeft.setImageResource(R.drawable.mypray_profile);
        ivRight.setImageResource(android.R.drawable.ic_menu_add);

        ivLeft.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivRight.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreatePrayEventActivity.class);
                startActivity(intent);
            }
        });

        tvTitle.setOnClickListener(tvTitleOnClickListener);
        setSupportActionBar(toolbar);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private View.OnClickListener tvTitleOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent intent = new Intent(MainActivity.this, PrayEventDetailActivity.class);
            PrayItem tmpItem1 = new PrayItem("Fruit Offerings Set", "15.00", 1, R.drawable.fruits);
            PrayItem tmpItem2 = new PrayItem("Joss Paper Gold", "10.00", 1, R.drawable.josspapers1);
            PrayItem tmpItem3 = new PrayItem("Joss Paper God of Wealth", "8.00", 1, R.drawable.josspapers2);
            PrayItem tmpItem4 = new PrayItem("Joss Paper Fortune", "12.00", 1, R.drawable.josspapers3);
            PrayItem tmpItem5 = new PrayItem("Joss Paper Sutra", "7.00", 1, R.drawable.josspapers4);
            PrayItem tmpItem6 = new PrayItem("Joss Paper Car", "15.00", 1, R.drawable.josspaperscar);
            PrayItem tmpItem7 = new PrayItem("Joss Paper Gold Bar", "8.50", 1, R.drawable.josspapersgold);
            PrayItem tmpItem8 = new PrayItem("Joss Paper Money", "10.00", 1, R.drawable.josspapersmoney);
            PrayItem tmpItem9 = new PrayItem("Joss Paper Tshirt", "8.50", 1, R.drawable.josspaperst);
            PrayItem tmpItem10 = new PrayItem("Joss Stick", "4.00", 1, R.drawable.jossstick);

            //item list 3
            ArrayList<PrayItem> prayItems3 = new ArrayList<>();
            prayItems3.add(tmpItem1);
            prayItems3.add(tmpItem10);
            prayItems3.add(tmpItem7);
            prayItems3.add(tmpItem2);
            prayItems3.add(tmpItem8);
            prayItems3.add(tmpItem5);

            PrayEvent tmpEvent1 = new PrayEvent("Dragon Boat Festival – Dumpling Festival", "There are many legends about the evolution of the festival, the most popular of which is in commemoration of Qu Yuan (340-278 BC). Qu Yuan was minister of the State of Chu and one of China's earliest poets. He advocated enriching the country and strengthening its military forces so as to fight against the Qin. However, he was opposed by aristocrats and later deposed and exiled by King Huai. In 278 BC, he heard the news that Qin troops had finally conquered Chu's capital, he plunged himself into the Miluo River, clasping his arms to a large stone. After his death, the people of Chu crowded to the bank of the river to pay their respects to him. The fishermen sailed their boats up and down the river to look for his body. People threw into the water zongzi to divert possible fish or shrimp from attacking his body. An old doctor poured a jug of reaglar wine (Chinese liquor seasoned with realgar) into the water, hoping to turn all aquatic beasts drunk. That's why people later followed the customs such as dragon boat racing, eating zongzi and drinking realgar wine on that day.", "20 Jun 2015", "乙未年五月初五", R.drawable.dwf, prayItems3);

            intent.putExtra(Argument.PRAY_EVENT, tmpEvent1.toString());
            intent.putExtra(Argument.EXTRA_MESSAGE, "Hey Feng, Its Dumpling Festival next week. According to the tradition and culture, these are the required to buy for the offerings & prayers. Buy today to get 30% saving!");
            PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            final Notification n = new NotificationCompat.Builder(MainActivity.this).setContentTitle("MyPray - " + tmpEvent1.getTitle())
                    .setSmallIcon(tmpEvent1.getPictureResourceId())
                    .setContentIntent(pIntent)
                    .setContentText(tmpEvent1.getDescription())
                    .setSound(alarmSound)
                    .setTicker("MyPray Event Notification")
                    .build();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    notificationManager.notify(0, n);
                }
            }, 20000);


        }
    };
}
