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

package noelc.mypray.customclass;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

@IBMDataObjectSpecialization("Item")
public class Item extends IBMDataObject {
    public static final String CLASS_NAME = "Item";
    private static final String NAME = "name";

    public String name;

    public String getName() {
        return (String) getObject(NAME);
    }

    public void setName(String itemName) {
        setObject(NAME, (itemName != null) ? itemName : "");
    }

    /**
     * When calling toString() for an item, we'd really only want the name.
     *
     * @return String theItemName
     */
    public String toString() {
        String theItemName = "";
        theItemName = getName();
        return theItemName;
    }
}
