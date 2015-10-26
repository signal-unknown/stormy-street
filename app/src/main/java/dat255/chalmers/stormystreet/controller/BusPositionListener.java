package dat255.chalmers.stormystreet.controller;

import java.util.Map;

import dat255.chalmers.stormystreet.model.GpsCoord;

/**
 * Interface for classes listening to the positions of different buses
 *
 * @author Alexander Karlsson
 */
public interface BusPositionListener {
    void updatePositions(Map<GpsCoord, String> positions);
}
