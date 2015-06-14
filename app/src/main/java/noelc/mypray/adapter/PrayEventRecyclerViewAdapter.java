package noelc.mypray.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import noelc.mypray.R;
import noelc.mypray.customclass.PrayEvent;
import noelc.mypray.events.ViewCardListener;

/**
 * Created by noelchew on 6/13/15.
 */
public class PrayEventRecyclerViewAdapter extends RecyclerView.Adapter<PrayEventRecyclerViewAdapter.ViewHolder> {
    private ArrayList<PrayEvent> prayEvents;
    private ViewCardListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView tvTitle, tvDescription, tvDate, tvHeading;
        private ImageView ivEvent;
        private Button btnView;
        private ViewCardListener listener;
        public ViewHolder(View v, ViewCardListener listener) {
            super(v);

            tvTitle = (TextView) v.findViewById(R.id.text_view_title);
            tvDescription = (TextView) v.findViewById(R.id.text_view_description);
            tvDate = (TextView) v.findViewById(R.id.text_view_date);
            ivEvent = (ImageView) v.findViewById(R.id.image_view_event);
            btnView = (Button) v.findViewById(R.id.button_view);

            tvHeading = (TextView) v.findViewById(R.id.text_view_heading);
            this.listener = listener;
        }

        public void update(final PrayEvent prayEvent, int position) {
            tvTitle.setText(prayEvent.getTitle());
            tvDescription.setText(prayEvent.getDescription());
            ivEvent.setImageResource(prayEvent.getPictureResourceId());
            tvDate.setText(prayEvent.getDate() + " (" + prayEvent.getLunarDate() + ")");

            if (position == 0) {
                tvHeading.setVisibility(View.VISIBLE);
                tvHeading.setText("Upcoming");
                tvDescription.setVisibility(View.VISIBLE);
            } else {
                tvDescription.setVisibility(View.GONE);
                if (position == 1) {
                    tvHeading.setVisibility(View.VISIBLE);
                    tvHeading.setText("Future");
                } else {
                    tvHeading.setVisibility(View.GONE);
                }
            }

            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(prayEvent);
                }
            });
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvDescription() {
            return tvDescription;
        }

        public TextView getTvDate() {
            return tvDate;
        }

        public ImageView getIvEvent() {
            return ivEvent;
        }

        public Button getBtnView() {
            return btnView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PrayEventRecyclerViewAdapter(ArrayList<PrayEvent> prayEvents, ViewCardListener listener) {
        this.prayEvents = prayEvents;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PrayEventRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_event, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v, listener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.update(prayEvents.get(position), position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (prayEvents != null && prayEvents.size() > 0) {
            return prayEvents.size();
        } else {
            return 0;
        }
    }
}