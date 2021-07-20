package com.orecraft.oremines.mines;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;


public class Mine {
    @Getter private final UUID mineID;
    @Getter private final String mineName;
    private final String minePermission;
    private final ArrayList<MineRegion> regions = new ArrayList<>();

    public Mine(UUID mineID, String mineName, String minePermission) {
        this.mineID = mineID;
        this.mineName = mineName;
        this.minePermission = minePermission;
    }

    /**
     * Adds a new region
     * @param regionUUID The UUID that will be linked to the region. If not supplied or already in use it will be regenerated.
     * @param maxX the max x chord
     * @param maxY the max y chord
     * @param maxZ the max z chord
     * @param minX the min x chord
     * @param minY the min y chord
     * @param minZ the min z chord
     */
    void addRegion(UUID regionUUID, int maxX, int maxY, int maxZ, int minX, int minY, int minZ) {
        if (regionUUID == null) {
            regionUUID = UUID.randomUUID();
        }
        for (MineRegion region: regions) {
            // 2^128 of happening naturally but here encase someone edits a config file.
            if (region.getRegionID().equals(regionUUID)) {
                regionUUID = UUID.randomUUID();
            }
        }
        regions.add(new MineRegion(regionUUID, maxX, maxY, maxZ, minX, minY, minZ));
    }

    /**
     * Removes a pre-existing region
     * @param regionUUID The UUID of the region that should be deleted.
     */
    void removeRegion(UUID regionUUID) {
        regions.removeIf(x -> x.getRegionID().equals(mineID));
    }

    /**
     * Checks if a player can mine within a mine
     * @param player The player in question
     * @return True if the player can mine, False if the player cannot mine
     */
    public boolean canMine(Player player) {
        return player.hasPermission(minePermission);
    }

    /**
     * Checks if a location is within a mine
     * @param location The location in question
     * @return True if the location is within the mine, False if the location is outside of the mine
     */
    public boolean isInMine(Location location) {
        for (MineRegion region: regions) {
            if (region.isInRegion(location)) {
                return true;
            }
        }
        return false;
    }

}
