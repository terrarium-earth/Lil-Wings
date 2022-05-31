package com.toadstoolstudios.lilwings.api;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PatreonData {
    private Map<UUID, ButterflyType> leveledPatrons;
    private Set<UUID> patrons;

    public Map<UUID, ButterflyType> getLeveledPatrons() {
        return leveledPatrons;
    }

    public Set<UUID> getPatrons() {
        return patrons;
    }

    public enum ButterflyType {
        WHITE_FOX,
        SWAMP_HOPPER,
        SWALLOW_TAIL,
        SHROOM_SKIPPER,
        PAINTED_PANTHER,
        CRYSTAL_PUFF,
        CLOUDY_PUFF,
        BUTTER_GOLD,
        APONI,
        RED_APPLEFLY,
        GOLD_APPLEFLY,
        GRAYLING,
        BLOOMING_GRAYLING,
        FLOWERING_GRAYLING;
    }
}


