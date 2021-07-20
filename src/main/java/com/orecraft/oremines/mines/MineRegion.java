package com.orecraft.oremines.mines;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Location;

import java.util.UUID;

public class MineRegion {

    @Getter(AccessLevel.PACKAGE) private final UUID regionID;
    @Getter(AccessLevel.PACKAGE) private final int startX, startY, startZ, endX, endY, endZ;

    MineRegion(UUID regionID, int startX, int startY, int startZ, int endX, int endY, int endZ) {
        this.regionID = regionID;
        if (startX < endX) {
            this.startX = startX;
            this.endX = endX;
        } else {
            this.startX = endX;
            this.endX = startX;
        }
        if (startY < endY) {
            this.startY = startY;
            this.endY = endY;
        } else {
            this.startY = endY;
            this.endY = startY;
        }
        if (startZ < endZ) {
            this.startZ = startZ;
            this.endZ = endZ;
        } else {
            this.startZ = endZ;
            this.endZ = startZ;
        }
    }

    boolean isInRegion(Location location) {
        if (startX < location.getBlockX() && location.getBlockX() > endX) {
            if (startY < location.getBlockY() && location.getBlockY() > endY) {
                return endZ < location.getBlockZ() && location.getBlockZ() > endZ;
            }
        }
        return false;
    }
}
