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
import java.util.Collections;
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
    private static final boolean USE_BLUEMIX = false;

    private ArrayList<PrayEvent> prayEvents;

    private static final String CLASS_NAME = MainFragment.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        if (USE_BLUEMIX) {

            blApplication = (BlueListApplication) getActivity().getApplication();
            itemList = blApplication.getItemList();
            if (SAVE_INTO_DB) {
                clearBluemixDb(itemList);
                saveToBluemixDb(generateData());
                itemList = blApplication.getItemList();
            }
            prayEvents = convertBluemixItemListToPrayEventList(itemList);
            Collections.sort(prayEvents);
        } else {
            prayEvents = generateData();
            Collections.sort(prayEvents);

        }
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

        //item list 1
        ArrayList<PrayItem> prayItems1 = new ArrayList<>();
        prayItems1.add(tmpItem1);
        prayItems1.add(tmpItem10);
        prayItems1.add(tmpItem7);
        prayItems1.add(tmpItem3);
        prayItems1.add(tmpItem4);
        prayItems1.add(tmpItem2);

        //item list 2
        ArrayList<PrayItem> prayItems2 = new ArrayList<>();
        prayItems2.add(tmpItem1);
        prayItems2.add(tmpItem10);
        prayItems2.add(tmpItem7);
        prayItems2.add(tmpItem6);
        prayItems2.add(tmpItem8);
        prayItems2.add(tmpItem5);

        //item list 3
        ArrayList<PrayItem> prayItems3 = new ArrayList<>();
        prayItems3.add(tmpItem1);
        prayItems3.add(tmpItem10);
        prayItems3.add(tmpItem7);
        prayItems3.add(tmpItem2);
        prayItems3.add(tmpItem8);
        prayItems3.add(tmpItem5);


//
//        ArrayList<PrayItem> prayItems = new ArrayList<>();
//        prayItems.add(tmpItem1);
//        prayItems.add(tmpItem2);
//        prayItems.add(tmpItem3);
//        prayItems.add(tmpItem4);
//        prayItems.add(tmpItem5);
//        prayItems.add(tmpItem6);
//        prayItems.add(tmpItem7);
//        prayItems.add(tmpItem8);
//        prayItems.add(tmpItem9);
//        prayItems.add(tmpItem10);


        PrayEvent tmpEvent1 = new PrayEvent("Dragon Boat Festival – Dumpling Festival", "There are many legends about the evolution of the festival, the most popular of which is in commemoration of Qu Yuan (340-278 BC). Qu Yuan was minister of the State of Chu and one of China's earliest poets. He advocated enriching the country and strengthening its military forces so as to fight against the Qin. However, he was opposed by aristocrats and later deposed and exiled by King Huai. In 278 BC, he heard the news that Qin troops had finally conquered Chu's capital, he plunged himself into the Miluo River, clasping his arms to a large stone. After his death, the people of Chu crowded to the bank of the river to pay their respects to him. The fishermen sailed their boats up and down the river to look for his body. People threw into the water zongzi to divert possible fish or shrimp from attacking his body. An old doctor poured a jug of reaglar wine (Chinese liquor seasoned with realgar) into the water, hoping to turn all aquatic beasts drunk. That's why people later followed the customs such as dragon boat racing, eating zongzi and drinking realgar wine on that day.",
                "2015-06-20", "乙未年五月初五", R.drawable.dwf, prayItems3);
        PrayEvent tmpEvent2 = new PrayEvent("Birthday of Guan Gong, God of Warriors", "Guan Ti or Guan Yun Chang was born in Shan Xi province during the Three Kingdom (220 – 260 AD). He was known for his righteous, and justice which got Guan Yu into trouble when he interfered with a licentious and corrupt magistrate who forced a poor lady to become his concubine. The magistrate was slayed by Guan Yu. He had to flee for his life and escape to the mountain to seek refuge. As he was on his journey to the neighbouring province he stops by a stream to have a wash; when to his surprise he noticed a great changed in his appearance! His facial complexion had changed from pale white to reddish tint which saved him to disguise himself and was able to walk through the sentries who was guarding the mountain pass.",
                "2015-08-08", "乙未年六月廿四", R.drawable.guandi, prayItems1);
        PrayEvent tmpEvent3 = new PrayEvent("Hungry Ghost Festival", "Chinese Ghost Month is more formal and more earnest: during the whole of the 7th. month in the Chinese lunar calendar the gates of the Underworld are opened and the ghosts of the dead are free to wander the earth. Ghosts who have no descendents to send them offerings of food and money return hungry and bored, so people give them banquets and put on entertainment to keep them happy and stop them causing injury.",
                "2015-08-18", "乙未年七月初一至三十", R.drawable.hgf, prayItems3);
        PrayEvent tmpEvent4 = new PrayEvent("Mid-Autumn Festival – moon cake and lantern festival", "The festival has a long history. In ancient China, emperors followed the rite of offering sacrifices to the sun in spring and to the moon in autumn. Historical books of the Zhou Dynasty had had the word \"Mid-Autumn\". Later aristocrats and literary figures helped expand the ceremony to common people. They enjoyed the full, bright moon on that day, worshipped it and expressed their thoughts and feelings under it. By the Tang Dynasty (618-907), the Mid-Autumn Festival had been fixed, which became even grander in the Song Dynasty (960-1279). In the Ming (1368-1644) and Qing (1644-1911) dynasties, it grew to be a major festival of China.",
                "2015-09-27", "乙未年八月十五", R.drawable.maf, prayItems3);
        PrayEvent tmpEvent5 = new PrayEvent("Birthday of Sun Wugong, the Monkey King", "The Monkey King Festival is celebrated a day after the Mid Autumn Festival. The origin of the festival is traced to an epic novel titled Journey to the West (西游记) written by the Chinese novelist Wu Cheng'en (1500–1582) in the 16th century during the Ming Dynasty (1368–1644). The novel brings out the concept of immortality from Taoism and rebirth from Buddhism. It revolves around a Buddhist monk during the Tang Dynasty (618-907). Harassed by paranormal activity, he visits India, accompanied by his disciples Sun Wukong the Monkey King, Pigsy (猪八戒) and Sandy (沙悟浄). They return to China with Buddhist scriptures.",
                "2015-09-28", "乙未年八月十六", R.drawable.monkeyking, prayItems1);
        PrayEvent tmpEvent6 = new PrayEvent("Birthdays of the Cai Shen, God of Wealth", "Cai Shen, the most popular of the various gos of Fortune and Prosperity. The name Cai Shen itself (財神) means \"God of Wealth\" itself. Cai Shen was initially a Chinese folk hero during the Qin Dynasty, who was deified and venerated by local Daoist followers. Pure Land Buddhism also came to venerate him as a god. He is most often depicted riding a black tiger and holding a golden rod, although he may also be depicted with an iron tool capable of turning stone and iron into gold. Cai Shen is widely honoured during the Chinese New Year celebrations, when many gaudy and brightly coloured posters of him are circulated \"for good luck and prosperity.”",
                "2015-10-29", "乙未年九月十七", R.drawable.caishen, prayItems1);
        PrayEvent tmpEvent7 = new PrayEvent("Birthday of Ma Zhu, Goddess of the Sea", "The Queen of Heaven is also known Ma Zu. Originally named Lin Muo Niang; was born in 960 AD, on the 23rd day of the 3rd month in the Song Dynasty. She was born in a village along PuTian, Fujian’s Province. Based on the book “Gods of Ancient China”, the day she was born, the land was covered by a purple streak, perfumed scent filled every household, and a golden halo appeared above the Lin house, within which emitted a red glow. One month after her birth she had not cried. So her parents called her Lin Muo Niang (Muo is the Chinese character meaning silence).",
                "2015-11-05", "乙未年正月十五", R.drawable.mazhu, prayItems1);
        PrayEvent tmpEvent8 = new PrayEvent("Chinese New Year", "The Spring Festival is the most important festival for the Chinese people and is when all family members get together, just like Christmas in the West. All people living away from home go back, becoming the busiest time for transportation systems of about half a month from the Spring Festival.",
                "2016-02-19", "乙未年正月初一", R.drawable.cny, prayItems3);
        PrayEvent tmpEvent9 = new PrayEvent("Birthday of Jade Emperor, God of Heaven", "The Jade Emperor is the supreme ruler of Heavens, the hades and the protector of mankind according to Chinese folklore religion and the highest ranking deity of the Taoist pantheon. From the ninth century onwards, he was the patron deity of the Chinese imperial family. The Jade Emperor presides over Heaven and Earth just as the earthly emperors once ruled over China.",
                "2016-02-27", "乙未年正月初九", R.drawable.jadeemperor, prayItems1);
        PrayEvent tmpEvent10 = new PrayEvent("Wesak Day", "Vesak Day is the most significant day of the year in the Buddhist calendar and is celebrated by Buddhists the world over. The day commemorates the birth, enlightenment and death of the Buddha and is a day of immense joy, peace and reflection.On this day, devout Buddhists and followers alike congregate at their various temples before dawn for the ceremonial, where the Buddhist flag will be hoisted, and hymns sung in praise of the holy triple gem: The Buddha, The Dharma (his teachings), and The Sangha (his disciples). Devotees often bring simple offerings of flowers, candles and joss-sticks to lay at the feet of their spiritual teacher. These symbolic offerings remind followers that life too, is subject to decay and destruction when the offering burns out or wilts away.",
                "2016-03-01", "乙未年四月十五", R.drawable.wsd, prayItems3);
        PrayEvent tmpEvent11 = new PrayEvent("Chap Goh Meh", "Throughout the Han Dynasty (206 BC-AD 220), Buddhism flourished in China. One emperor heard that Buddhist monks would watch sarira, or remains from the cremation of Buddha's body, and light lanterns to worship Buddha on the 15th day of the 1st lunar month, so he ordered to light lanterns in the imperial palace and temples to show respect to Buddha on this day. Later, the Buddhist rite developed into a grand festival among common people and its influence expanded from the Central Plains to the whole of China.",
                "2016-05-03", "农历乙未年四月廿七", R.drawable.yxf, prayItems3);
        PrayEvent tmpEvent12 = new PrayEvent("Birthday of Tu Di Gong, Lord of the Earth", "He is also called “The Upright Spirit of Fortune and Wealth” (福德正神) when worshipped in temples and homes; while in a cemetery, he is called “Hou Tu” (后土). All the lands through out the world receives his protection; all the grasses, woods, stones, sands, paddy fields, hemp, bamboo, reeds, grains, rice, gems, and oil come forth from the ground because of his power. He can even prevent plagues ghosts from spreading epidemics; furthermore he’s the greatest wealth deity on earth! He’s always depicted as an elderly man with a white beard usually smiling and maintaining a benevolent expression. He holds an auspicious wish fulfilling object called “Ru Yi” 「如意」.",
                "2016-03-21", "乙未年二月初二", R.drawable.tudigong, prayItems1);
        PrayEvent tmpEvent13 = new PrayEvent("Birthday of Guanyin, Goddess of Mercy", "Guanyin is an East Asian deity of mercy, and a bodhisattva associated with compassion as venerated by Mahayana Buddhists. The name Guanyin is short for Guanshiyin, which means \"Perceiving the Sounds (or Cries) of the World\". She is also sometimes referred to as Guanyin Bodhisattva (Chinese: 觀音菩薩). Some Buddhists believe that when one of their adherents departs from this world, they are placed by Guanyin in the heart of a lotus, and then sent to the western pure land of Sukhāvatī.",
                "2016-07-04", "乙未年二月十九", R.drawable.guanyin, prayItems1);
        PrayEvent tmpEvent14 = new PrayEvent("Qing Ming Festival", " A.k.a. Tomb Sweeping Day is a traditional Chinese festival celebrated on the 104th day after the winter solstice. The living descendants pay respect and homage to all ancestors, including same age or younger generations of ancestors. It is a day to remember and honour one's ancestors at grave sites by sweeping the tombs and offering food, tea, wine, chopsticks to eat the food with and by praying. Joss paper accessories will be offered to the ancestors as well. While bland food is placed by the tombs on Tomb Sweeping Day, the Chinese regularly provide scrumptious offerings to their ancestors at altar tables in their homes.",
                "2016-05-22", "乙未年四月初五", R.drawable.qmf, prayItems3);

        ArrayList<PrayEvent> events = new ArrayList<>();
        events.add(tmpEvent1);
        events.add(tmpEvent2);
        events.add(tmpEvent3);
        events.add(tmpEvent4);
        events.add(tmpEvent5);
        events.add(tmpEvent6);
        events.add(tmpEvent7);
        events.add(tmpEvent8);
        events.add(tmpEvent9);
        events.add(tmpEvent10);
        events.add(tmpEvent11);
        events.add(tmpEvent12);
        events.add(tmpEvent13);
        events.add(tmpEvent14);

        return events;
    }

    private ArrayList<PrayEvent> convertBluemixItemListToPrayEventList(List<Item> itemList) {
        ArrayList<PrayEvent> prayEvents = new ArrayList<>();
        for (Item item : itemList) {
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
                    if (task.isCancelled()) {
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
            }, Task.UI_THREAD_EXECUTOR);
        }

    }
}
