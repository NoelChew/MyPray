package noelc.mypray.listitem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import noelc.mypray.R;
import noelc.mypray.customclass.PrayEvent;

/**
 * Created by noelchew on 6/13/15.
 */
public class PrayEventCard extends LinearLayout {
    private TextView tvTitle, tvDescription, tvDate, tvHeading;
    private ImageView ivEvent;
    private Button btnView;

    public PrayEventCard(Context context) {
        super(context);
        init();
    }

    public PrayEventCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PrayEventCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        LayoutInflater.from(getContext()).inflate(R.layout.card_event, this, false);
        View.inflate(getContext(), R.layout.card_event, this);

        tvTitle = (TextView) findViewById(R.id.text_view_title);
        tvDescription = (TextView) findViewById(R.id.text_view_description);
        tvDate = (TextView) findViewById(R.id.text_view_date);
        ivEvent = (ImageView) findViewById(R.id.image_view_event);
        btnView = (Button) findViewById(R.id.button_view);

        tvHeading = (TextView) findViewById(R.id.text_view_heading);
    }

    public void update(PrayEvent prayEvent, int position) {

        tvTitle.setText(prayEvent.getTitle());
        tvDescription.setText(prayEvent.getDescription());
        ivEvent.setImageResource(prayEvent.getPictureResourceId());
        tvDate.setText(prayEvent.getDate() + " (" + prayEvent.getLunarDate() + ")");

        if (position == 0) {
            tvHeading.setText("Upcoming");
            tvDescription.setVisibility(VISIBLE);
        } else {
            tvDescription.setVisibility(GONE);
            if (position == 1) {
                tvHeading.setText("Future");
            } else {
                tvHeading.setVisibility(GONE);
            }
        }
    }
}
