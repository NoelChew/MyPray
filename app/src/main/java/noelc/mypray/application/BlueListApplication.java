/*
 * Copyright 2014 IBM Corp. All Rights Reserved
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package noelc.mypray.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import bolts.Continuation;
import bolts.Task;
import noelc.mypray.customclass.Item;


public final class BlueListApplication extends Application {
    private static final String APP_ID = "58205b17-22f3-4bdd-a700-96935a394d36";
    private static final String APP_SECRET = "357ffb5e36c14f6279f3b3f1cca63591dc9473e3";
    private static final String APP_ROUTE = "http://dead.eu-gb.mybluemix.net";
    private static final String PROPS_FILE = "bluelist.properties";

    private static final String CLASS_NAME = BlueListApplication.class.getSimpleName();
    List<Item> itemList;

    public BlueListApplication() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(CLASS_NAME, "Activity created: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(CLASS_NAME, "Activity started: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(CLASS_NAME, "Activity resumed: " + activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.d(CLASS_NAME, "Activity saved instance state: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(CLASS_NAME, "Activity paused: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(CLASS_NAME, "Activity stopped: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(CLASS_NAME, "Activity destroyed: " + activity.getLocalClassName());
            }
        });
    }

    public void onCreate() {
        super.onCreate();
        itemList = new ArrayList<Item>();
        // Read from properties file
        Properties props = new java.util.Properties();
        Context context = getApplicationContext();
//        try {
//            AssetManager assetManager = context.getAssets();
//            props.load(assetManager.open(PROPS_FILE));
//            Log.i(CLASS_NAME, "Found configuration file: " + PROPS_FILE);
//        } catch (FileNotFoundException e) {
//            Log.e(CLASS_NAME, "The bluelist.properties file was not found.", e);
//        } catch (IOException e) {
//            Log.e(CLASS_NAME, "The bluelist.properties file could not be read properly.", e);
//        }

        // initialize the IBM core backend-as-a-service
//        IBMBluemix.initialize(this, props.getProperty(APP_ID),
//                props.getProperty(APP_SECRET), props.getProperty(APP_ROUTE));
        IBMBluemix.initialize(this, APP_ID, APP_SECRET, APP_ROUTE);

// initialize the IBM Data Service
        IBMData.initializeService();
// register the Item Specialization
        Item.registerSpecialization(Item.class);
        populateListItem();
    }

    /**
     * Returns the itemList, an array of Item objects.
     *
     * @return itemList
     */
    public List<Item> getItemList() {
        return itemList;

    }

    public void setItemList(List<Item> list) {
        itemList = list;
    }

    public void populateListItem() {
        try {
            IBMQuery<Item> query = IBMQuery.queryForClass(Item.class);
            // Query all the Item objects from the server
            query.find().continueWith(new Continuation<List<Item>, Void>() {

                @Override
                public Void then(Task<List<Item>> task) throws Exception {
                    final List<Item> objects = task.getResult();
                    // Log if the find was cancelled.
                    if (task.isCancelled()){
                        Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                    }
                    // Log error message, if the find task fails.
                    else if (task.isFaulted()) {
                        Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                    }


                    // If the result succeeds, load the list.
                    else {
                        // Clear local itemList.
                        // We'll be reordering and repopulating from DataService.
                        itemList.clear();
                        for(IBMDataObject item:objects) {
                            itemList.add((Item) item);
                        }
//                        sortItems(itemList);
//                        lvArrayAdapter.notifyDataSetChanged();
                        Log.d(CLASS_NAME, "Data retrieved.");
                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);


        }  catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }


    }
}