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

package net.tridentsdk.event;

import net.tridentsdk.docs.InheritThreadSafe;

/**
 * Represents an event that could be listened by the plugin listeners <p/> <p>All events are thread safe, their
 * visibility is required as the plugin threads that </p>
 *
 * @author The TridentSDK Team
 */
@InheritThreadSafe public abstract class Event {
    private final boolean isAsync;

    public Event() {
        this(false);
    }

    public Event(boolean async) {
        this.isAsync = async;
    }

    /**
     * @return return true if Event is asynchronous
     */
    public boolean isAsync() {
        return this.isAsync;
    }
}
