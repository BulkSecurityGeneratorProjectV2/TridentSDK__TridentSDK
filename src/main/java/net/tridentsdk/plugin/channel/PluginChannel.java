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

package net.tridentsdk.plugin.channel;

import com.google.common.collect.Lists;
import net.tridentsdk.Trident;
import net.tridentsdk.util.TridentLogger;

import java.util.List;

public abstract class PluginChannel {
    private final List<byte[]> history = Lists.newArrayList();

    public void process(byte... message) {
        if (!Trident.isTrident()) {
            TridentLogger.error(
                    new UnsupportedOperationException("Only TridentSDK is allowed to execute this method!"));
        }

        byte[] bytes = new byte[message.length - 1];

        for (int i = 0; i < message.length; i++) {
            bytes[i] = message[i];
        }

        this.history.add(bytes);
        this.onMessage(message);
    }

    public abstract void onMessage(byte... message);

    public List<byte[]> getHistory() {
        return this.history;
    }
}
