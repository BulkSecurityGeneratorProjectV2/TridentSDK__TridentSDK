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

// TODO: JavaDoc
public class ClickEvent {
    private ClickAction action;
    private String value;

    public ClickEvent action(ClickAction action) {
        this.setAction(action);

        return this;
    }

    public ClickEvent value(String value) {
        this.setValue(value);

        return this;
    }

    public ClickAction getAction() {
        return action;
    }

    public void setAction(ClickAction action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public enum ClickAction {
        OPEN_URL,
        OPEN_FILE,
        RUN_COMMAND,
        SUGGEST_COMMAND;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
