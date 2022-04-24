package io.github.bioplethora.api;

import io.github.bioplethora.event.ServerWorldEvents;
import io.github.bioplethora.network.functions.LeftSwingPacket;
import io.github.bioplethora.network.functions.RightSwingPacket;

/**
 * This reach is handled by packets, check: {@link LeftSwingPacket}, {@link RightSwingPacket}, {@link ServerWorldEvents}
 */
public interface IReachWeapon {

    /**
     * @return Total Reach Distance (Default Distance + Additional Reach Distance)::
     * Default Distance is 5 (supposedly 3, but added 2 more since this reach is somehow kinda wonky)
     */
    double getReachDistance();
}