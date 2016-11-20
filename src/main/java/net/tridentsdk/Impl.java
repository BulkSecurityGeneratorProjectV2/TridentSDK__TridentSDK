/*
 * Trident - A Multithreaded Server Alternative
 * Copyright 2016 The TridentSDK Team
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
package net.tridentsdk;

import net.tridentsdk.command.logger.LogHandler;
import net.tridentsdk.command.logger.Logger;
import net.tridentsdk.config.Config;
import net.tridentsdk.doc.Internal;
import net.tridentsdk.ui.bossbar.BossBar;
import net.tridentsdk.ui.tablist.TabListManager;
import net.tridentsdk.world.WorldLoader;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;

/**
 * This provides the accessors to implementations provided
 * by the server in order for plugins to use API methods
 * implemented by the server.
 *
 * @author TridentSDK
 * @since 0.4-alpha
 */
@Internal
@ThreadSafe
public final class Impl {
    /**
     * The latch that is used to guard the thread-safety of
     * the implementation provider
     */
    private static final CountDownLatch IMPL_LATCH = new CountDownLatch(1);
    /**
     * The instance of the implementation provider
     */
    private static ImplementationProvider impl;

    private static final Object lock = new Object();

    // Utility class; do not allow instantiation
    private Impl() {
    }

    // READ: AGAIN ANOTHER PLACE TO PAY CLOSE ATTENTION
    // We expect that implementation designers start
    // threads that call to this class after this class has
    // been properly loaded, such that there is a stronger
    // guarantee that the set method is called before get.
    // However, in the case that that does not happen, it
    // is safely guarded by a CountDownLatch.
    //
    // NOTE: DO NOT SET 'impl' TO VOLATILE
    // The only thing that is needed for thread-safety in
    // this model is the fact that no two threads may both
    // contend to set the implementation. It uses a
    // compound check-then-act idiom which is not
    // thread-safe. The CountDownLatch serves also not only
    // as a safety mechanism, but also as a synchronizer
    // to properly ensure the visibility of the 'impl' field.
    //
    // volatile cannot guarantee the former while the latter
    // is needed because a synchronization edge is
    // unfortunately not enough to cause a hardware flush
    // to ensure the visibility to ALL threads - only
    // a synchronization edge generated by the CountDownLatch
    // is strong enough to guarantee this.

    public static void setImpl(ImplementationProvider i) {
        // Ignore static synchronization warning
        // Static method synchronization must use a static
        // synchronizer, period. There is no way around this
        // thus you must take the performance hit
        synchronized (lock) {
            if (Impl.impl == null) {
                Impl.impl = i;
                IMPL_LATCH.countDown();
            }
        }
    }

    @Nonnull
    public static ImplementationProvider get() {
        try {
            IMPL_LATCH.await();
            return impl;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Functions implementation
    public interface ImplementationProvider {
        // Obtains the implementation of the server object
        // that represents the API side version
        Server svr();

        // Create a new config; since the implementation
        // should use a path anyways, there is only one way
        Config newCfg(Path p);

        // Create a new logger
        Logger newLogger(String s);

        // attaches the handler to the logger
        void attachHandler(Logger logger, LogHandler handler);

        // removes handlers from the logger
        boolean removeHandler(Logger logger, LogHandler handler);

        // get the world handler
        WorldLoader wrlds();

        // Get the TabList Manager
        TabListManager tabListManager();

        // Create a new bossbar
        BossBar newBossBar();
    }
}
