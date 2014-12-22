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
package net.tridentsdk.api;

import com.google.common.base.Preconditions;
import net.tridentsdk.api.util.Vector;
import net.tridentsdk.api.world.World;

/**
 * Represents a point on the coordinate grid of the world
 *
 * @author The TridentSDK Team
 */
public class Location implements Cloneable {
    private double x;
    private double y;
    private double z;

    private World world;

    private float yaw;
    private float pitch;

    /**
     * References the point on the world as a location that wraps the coordinates
     *
     * @param world the world which the point resides in
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param z     the z coordinate
     * @param yaw   goes side to side, in degrees
     * @param pitch goes up and down, in degrees
     */
    public Location(World world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;

        this.x = x;
        this.y = y;
        this.z = z;

        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Wraps the point without specific yaw and pitch (set to 0)
     *
     * @param world the world which the point resides in
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param z     the z coordinate
     */
    public Location(World world, double x, double y, double z) {
        this(world, x, y, z, 0.0F, 0.0F);
    }

    private static double square(double d) {
        return d * d;
    }

    /**
     * The x position of the location
     *
     * @return the x value of this location
     */
    public double getX() {
        return this.x;
    }

    /**
     * Sets the x value of the location
     *
     * @param x the x coordinate to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * The y position of the location
     *
     * @return the y value of this location
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets the y value of the location
     *
     * @param y the y coordinate to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * The z position of the location
     *
     * @return the z value of this location
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Sets the z value of the location
     *
     * @param z the z coordinate to set
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * The world the location is in
     *
     * @return the world where the location is
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Sets the location's world
     *
     * @param world the world to set the location to
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * The yaw of the location
     *
     * @return the yaw of this location
     */
    public float getYaw() {
        return this.yaw;
    }

    /**
     * Sets the yaw of the location
     *
     * @param yaw the yaw of the location to set
     */
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    /**
     * The pitch of the location
     *
     * @return the pitch value of this location
     */
    public float getPitch() {
        return this.pitch;
    }

    /**
     * Sets the pitch of the location
     *
     * @param pitch the pitch of the location to set
     */
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    /**
     * Adds a vector to this location, returning this locacion
     * @param vector the vector to add
     * @return this
     */
    public Location add(Vector vector) {
        this.setX(this.getX() + vector.getX());
        this.setY(this.getY() + vector.getY());
        this.setZ(this.getZ() + vector.getZ());

        return this;
    }

    public Location getRelative(Vector vector) {
        return new Location(this.getWorld(), vector.getX() + this.getX(), vector.getY() + this.getY(),
                vector.getZ() + this.getZ(), this.getYaw(), this.getPitch());
    }

    /**
     * Creates new Vector with Location's coordinates
     *
     * @return New Vector containing this Location's coordinates
     */
    public Vector toVector() {
        return new Vector(this.getX(), this.getY(), this.getZ());
    }

    /**
     * The distance this from location to another. Math.sqrt is costly, ergo calling this method a lot is not advised.
     *
     * @param location the location to measure distance with
     * @return distance from this location to another
     */
    public double distance(Location location) {
        return Math.sqrt(this.distanceSquared(location));
    }

    /**
     * The distance squared from this location to another
     *
     * @param location the location to measure distance with
     * @return distance squared from this location to another
     */
    public double distanceSquared(Location location) {
        Preconditions.checkNotNull(location, "Location cannot be null.");
        if (!this.getWorld().equals(location.getWorld())) return 0.0;
        return square(this.getX() - location.getX()) + square(this.getY() - location.getY()) +
                square(this.getZ() - location.getZ());
    }

    @Override
    public Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException ignored) {
            return null;
        }
    }
}
