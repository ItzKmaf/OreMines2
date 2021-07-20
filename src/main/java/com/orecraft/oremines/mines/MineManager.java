package com.orecraft.oremines.mines;

import com.orecraft.oremines.Config;
import com.orecraft.oremines.OreMines;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MineManager {

    private static MineManager instance;
    @Getter private final Config mineConfig;
    ArrayList<Mine> mines = new ArrayList<>();

    public MineManager() {
        mineConfig = OreMines.getInstance().getMineConfig();
        ConfigurationSection mineList = mineConfig.getConfFile().getConfigurationSection("mines");
        assert mineList != null;
        for (String mineID: mineList.getKeys(false)) {
            ConfigurationSection mineSection = mineList.getConfigurationSection(mineID);
            UUID uuid = UUID.fromString(mineID);
            addMine(uuid,mineSection.getString("name"), mineSection.getString("permission"));
        }
    }

    /**
     * Adds a new Mine
     * @param mineID A UUID that will be linked to the mine. If not supplied or already in use it will be regenerated.
     * @param mineName The name of the mine e.g 'A'
     * @param minePermission The permission node required to be able to mine within the mine.
     */
    public void addMine(UUID mineID, String mineName, String minePermission) {
        if (mineID == null) {
            mineID = UUID.randomUUID();
        }
        for (Mine mine: mines) {
            // 2^128 of happening naturally but here encase someone edits a config file.
            if (mine.getMineID().equals(mineID)) {
                mineID = UUID.randomUUID();
            }
        }
        Mine mine = new Mine(mineID, mineName, minePermission);
        mines.add(mine);
    }

    public List<Mine> getMines() {
        return Collections.unmodifiableList(mines);
    }

    /**
     * Removes a pre-existing Mine
     * @param mineID The UUID of the mine that should be deleted.
     */
    public void removeMine(UUID mineID) {
        mines.removeIf(x -> x.getMineID().equals(mineID));
    }


    /**
     * Retrieves the pre-existing instance of MineManager, if their isn't one it instantiates a new one.
     * @return Instance of MineManager
     */
    public static MineManager getInstance() {
        if (instance == null) {
            instance = new MineManager();
        }
        return instance;
    }
}
