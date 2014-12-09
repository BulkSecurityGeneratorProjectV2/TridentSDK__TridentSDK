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
package net.tridentsdk.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Used for advanced interaction with the Config API for custom serializing/deserializing of Java Objects
 *
 * @author The TridentSDK Team
 */
public final class GsonFactory {
    private static final GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    private static Gson gson = builder.create();

    private GsonFactory() {
    }

    /**
     * Registers the adapter with the given type inside the GSON builder
     *
     * @param type the type to use for registration
     * @param adapter the adapter registered with the type
     */
    public static void registerTypeAdapter(Type type, Object adapter) {
        builder.registerTypeAdapter(type, adapter);
        gson = builder.create();
    }

    /**
     * Acquires the {@code static} GSON builder
     *
     * @return the GSON builder registered with the factory
     */
    protected static Gson getGson() {
        return gson;
    }
}
