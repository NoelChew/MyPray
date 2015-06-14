package noelc.mypray.customclass;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import noelc.mypray.common.Util;

/**
 * Created by noelchew on 6/13/15.
 */
public class PrayEvent implements Comparable<PrayEvent> {
    private String title;
    private String description;
    private String date;
    private String lunarDate;
    private int pictureResourceId;

    private ArrayList<PrayItem> prayItems;

    public PrayEvent(String title, String description, String date, String lunarDate, int pictureResourceId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.lunarDate = lunarDate;
        this.pictureResourceId = pictureResourceId;
    }

    public PrayEvent(String title, String description, String date, String lunarDate, int pictureResourceId, ArrayList<PrayItem> prayItems) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.lunarDate = lunarDate;
        this.pictureResourceId = pictureResourceId;
        this.prayItems = prayItems;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLunarDate() {
        return lunarDate;
    }

    public int getPictureResourceId() {
        return pictureResourceId;
    }

    public ArrayList<PrayItem> getPrayItems() {
        return prayItems;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static PrayEvent deserialise(String json) {
        return new Gson().fromJson(json, PrayEvent.class);
    }

    public static PrayEvent deserialiseList(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<PrayEvent>>() {
        }.getType());
    }

    public String getTotalPrice(String currency) {
        Double totalCost = 0.0;
        for (PrayItem item : this.getPrayItems()) {
            totalCost += item.getPriceInDouble();
        }

        return currency + " " + Util.doubleToTwoDecimalString(totalCost);
    }

    @Override
    public int compareTo(PrayEvent another) {
        return (this.getDate().compareTo(another.getDate()));
    }
}
