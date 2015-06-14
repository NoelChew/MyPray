package noelc.mypray.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import noelc.mypray.listitem.PrayEventCard;
import noelc.mypray.customclass.PrayEvent;

/**
 * Created by noelchew on 6/13/15.
 */
public class PrayEventAdapter extends ArrayAdapter<PrayEvent> {

    public PrayEventAdapter(Context context, int resource, List<PrayEvent> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new PrayEventCard(getContext());
        }

        if (getItem(position) != null) {
            ((PrayEventCard) convertView).update(getItem(position), position);
        }

        return convertView;
    }
}
