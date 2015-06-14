package noelc.mypray.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ibm.mobile.services.data.IBMDataObject;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import noelc.mypray.R;
import noelc.mypray.activities.PrayEventDetailActivity;
import noelc.mypray.adapter.PrayEventRecyclerViewAdapter;
import noelc.mypray.application.BlueListApplication;
import noelc.mypray.common.Argument;
import noelc.mypray.customclass.Item;
import noelc.mypray.customclass.PrayEvent;
import noelc.mypray.customclass.PrayItem;
import noelc.mypray.events.ViewCardListener;

/**
 * Created by noelchew on 6/13/15.
 */
public class MainFragment extends Fragment {
    private BlueListApplication blApplication;
    private List<Item> itemList;
    private ListView listView;
    private RecyclerView recyclerView;
    //    private PrayEventAdapter adapter;
    private PrayEventRecyclerViewAdapter adapter;

    private static final boolean SAVE_INTO_DB = false;

    private ArrayList<PrayEvent> prayEvents;

    private static final String CLASS_NAME = MainFragment.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        blApplication = (BlueListApplication) getActivity().getApplication();
        itemList = blApplication.getItemList();
        if (SAVE_INTO_DB) {
            clearBluemixDb(itemList);
            saveToBluemixDb(generateData());
            itemList = blApplication.getItemList();
        }
        prayEvents = convertBluemixItemListToPrayEventList(itemList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        setView(v);

        return v;
    }

    private void setView(View v) {
//        listView = (ListView) v.findViewById(R.id.list_view_pray_event);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_pray_event);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

//        adapter = new PrayEventAdapter(getActivity(), 0, generateData());
//        listView.setAdapter(adapter);
        adapter = new PrayEventRecyclerViewAdapter(prayEvents, viewCardListener);
        recyclerView.setAdapter(adapter);
    }

    private ViewCardListener viewCardListener = new ViewCardListener() {
        @Override
        public void onClick(PrayEvent prayEvent) {
            Intent intent = new Intent(getActivity(), PrayEventDetailActivity.class);
            intent.putExtra(Argument.PRAY_EVENT, prayEvent.toString());
            startActivity(intent);
        }
    };


    private ArrayList<PrayEvent> generateData() {
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


        ArrayList<PrayItem> prayItems = new ArrayList<>();
        prayItems.add(tmpItem1);
        prayItems.add(tmpItem2);
        prayItems.add(tmpItem3);
        prayItems.add(tmpItem4);
        prayItems.add(tmpItem5);
        prayItems.add(tmpItem6);
        prayItems.add(tmpItem7);
        prayItems.add(tmpItem8);
        prayItems.add(tmpItem9);
        prayItems.add(tmpItem10);

        PrayEvent tmpEvent1 = new PrayEvent("Title 1", "Description blab blab aasakjfhsdkjf", "5 July 2015", "农历乙未年四月廿七", R.drawable.mypray_profile, prayItems);
        PrayEvent tmpEvent2 = new PrayEvent("Title 2", "Description blab blab aasakjfhsdkjf", "5 July 2015", "农历乙未年四月廿七", R.drawable.mypray_profile, prayItems);
        PrayEvent tmpEvent3 = new PrayEvent("Title 3", "Description blab blab aasakjfhsdkjf", "5 July 2015", "农历乙未年四月廿七", R.drawable.mypray_profile, prayItems);
        PrayEvent tmpEvent4 = new PrayEvent("Title 4", "Description blab blab aasakjfhsdkjf", "5 July 2015", "农历乙未年四月廿七", R.drawable.mypray_profile, prayItems);

        ArrayList<PrayEvent> events = new ArrayList<>();
        events.add(tmpEvent1);
        events.add(tmpEvent2);
        events.add(tmpEvent3);
        events.add(tmpEvent4);

        return events;
    }

    private ArrayList<PrayEvent> convertBluemixItemListToPrayEventList(List<Item> itemList) {
        ArrayList<PrayEvent> prayEvents = new ArrayList<>();
        for (Item item: itemList) {
            PrayEvent tmpPrayEvent = PrayEvent.deserialise(item.getName());
            prayEvents.add(tmpPrayEvent);
        }

        return prayEvents;
    }

    private void saveToBluemixDb(ArrayList<PrayEvent> prayEvents) {
        for (PrayEvent prayEvent : prayEvents) {
            Item item = new Item();
            item.setName(prayEvent.toString());
            // Use the IBMDataObject to create and persist the Item object.
            item.save().continueWith(new Continuation<IBMDataObject, Void>() {

                @Override
                public Void then(Task<IBMDataObject> task) throws Exception {
                    // Log if the save was cancelled.
                    if (task.isCancelled()) {
                        Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                    }
                    // Log error message, if the save task fails.
                    else if (task.isFaulted()) {
                        Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                    }

                    // If the result succeeds, load the list.
                    else {
                        //listItems();
                        Log.d(CLASS_NAME, "Save success.");
                        // success
                    }
                    return null;
                }

            });
        }
    }

    private void clearBluemixDb(List<Item> itemList) {
        for (Item item : itemList) {
            // This will attempt to delete the item on the server.
            item.delete().continueWith(new Continuation<IBMDataObject, Void>() {

                @Override
                public Void then(Task<IBMDataObject> task) throws Exception {
                    // Log if the delete was cancelled.
                    if (task.isCancelled()){
                        Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                    }

                    // Log error message, if the delete task fails.
                    else if (task.isFaulted()) {
                        Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                    }

                    // If the result succeeds, reload the list.
                    else {
//                        lvArrayAdapter.notifyDataSetChanged();
                        //success
                        Log.d(CLASS_NAME, "Delete success.");
                    }
                    return null;
                }
            },Task.UI_THREAD_EXECUTOR);
        }

    }
}
