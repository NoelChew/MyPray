package noelc.mypray.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import noelc.mypray.R;
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
}
