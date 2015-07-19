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

package net.tridentsdk.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Execution abstraction
 *
 * @author The TridentSDK Team
 */
public interface SelectableThread {
    /**
     * Adds the task to the queue
     *
     * @param task the task to add
     */
    void execute(Runnable task);

    /**
     * Runs a callback which returns a value as a future
     *
     * @param task the callback to run
     * @param <V> the return type of the callback
     * @return the returned value
     */
    <V> Future<V> submitTask(Callable<V> task);

    /**
     * Closes the thread and stops execution of new / remaining tasks
     */
    void interrupt();

    /**
     * Thread form
     *
     * @return the thread that is running
     */
    Thread asThread();
}