/*
 * Trident - A Multithreaded Server Alternative
 * Copyright 2014 The TridentSDK Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tridentsdk.meta;

/**
 * Enum of all bar types.
 *
 * @author The TridentSDK Team
 */
public enum BarType {
    /**
     * The action bar.
     */
    ACTION_BAR(0),

    /**
     * The boss bar.
     */
    BOSS_BAR(1);

    private int id;

    BarType(int id) {
        this.id = id;
    }

    /**
     * Get the id value of the enum
     *
     * @return the id value
     */
    public int id() {
        return id;
    }
}
