package noelc.mypray.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import noelc.mypray.common.Util;
import noelc.mypray.customclass.PrayItem;
import noelc.mypray.events.TextEditedListener;
import noelc.mypray.listitem.PrayItemListItem;

/**
 * Created by noelchew on 6/13/15.
 */
public class PrayerItemListAdapter extends ArrayAdapter<PrayItem> {
    private ArrayList<PrayItem> prayItems;

    public PrayerItemListAdapter(Context context, int resource, List<PrayItem> objects) {
        super(context, resource, objects);
        prayItems = new ArrayList<>(objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       if (convertView == null) {
           convertView = new PrayItemListItem(getContext(), new TextEditedListener() {
               @Override
               public void onTextEdited(String text) {
                   if (text != null && !TextUtils.isEmpty(text)) {
                       getItem(position).setQuantity(Integer.parseInt(text));
                   } else {
                       getItem(position).setQuantity(0);
                   }
                   notifyDataSetChanged();
               }
           });
       }

        if (getItem(position) != null) {
            ((PrayItemListItem) convertView).update(getItem(position));
        }

        return convertView;
    };

    public String getTotalPrice() {
        Double totalCost = 0.0;
        for (PrayItem item: (prayItems)) {
            totalCost += item.getPriceInDouble();
        }

        return "RM " + Util.doubleToTwoDecimalString(totalCost);
    }

}
