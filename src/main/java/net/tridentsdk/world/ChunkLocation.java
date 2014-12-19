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
package net.tridentsdk.world;

import java.io.Serializable;

/**
 * Stores the location of a Chunk
 *
 * @author The TridentSDK Team
 */
public class ChunkLocation implements Serializable, Cloneable {
    private static final long serialVersionUID = 9083698035337137603L;
    private final int x;
    private final int z;

    private ChunkLocation(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public static ChunkLocation create(int x, int z) {
        return new ChunkLocation(x, z);
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }
}
