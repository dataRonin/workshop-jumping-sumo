package de.devoxx4kids.drone.programmatic;

import de.devoxx4kids.drone.DroneController;

import de.devoxx4kids.dronecontroller.command.flip.DownsideDown;
import de.devoxx4kids.dronecontroller.network.DroneConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;


/**
 * ProgrammaticDriver.
 *
 * <p>Predefine the moves of the drone</p>
 *
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public class ProgrammaticDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DroneController drone;

    public ProgrammaticDriver(DroneConnection droneConnection) {

        drone = new DroneController(droneConnection);

        // enable battery listener
        drone.addBatteryListener(aByte -> LOGGER.info("Batterylevel: " + aByte.toString() + "%"));

        // disable video and mute the drone
        drone.video().disableVideo().drone().audio().mute();
    }

    public void drive() {

        drone.send(DownsideDown.downsideDown()).right().left();
    }
}
