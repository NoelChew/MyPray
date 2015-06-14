package noelc.mypray.customclass;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by noelchew on 6/13/15.
 */
public class PrayItem {

    String name;
    String price;
    int quantity;
    int imageResource;

    public PrayItem(String name, String price, int quantity, int imageResource) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static PrayItem deserialise(String json) {
        return new Gson().fromJson(json, PrayItem.class);
    }

    public static PrayItem deserialiseList(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<PrayItem>>() {
        }.getType());
    }

    public double getPriceInDouble() {
        return Double.parseDouble(this.price) * this.quantity;
    }
}
