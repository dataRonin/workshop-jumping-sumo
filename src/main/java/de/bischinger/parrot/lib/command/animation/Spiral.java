package de.bischinger.parrot.lib.command.animation;

import de.bischinger.parrot.lib.command.Acknowledge;
import de.bischinger.parrot.lib.command.ChannelType;
import de.bischinger.parrot.lib.command.Command;
import de.bischinger.parrot.lib.command.CommandKey;
import de.bischinger.parrot.lib.command.FrameType;


/**
 * TODO: Do not use this will stop the drone
 *
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public final class Spiral implements Command {

    private final CommandKey commandKey = CommandKey.commandKey(3, 2, 4);

    protected Spiral() {

        // use fabric method
    }

    public static Spiral spiral() {

        return new Spiral();
    }


    @Override
    public byte[] getBytes(int counter) {

        return new byte[] {
                (byte) FrameType.ARNETWORKAL_FRAME_TYPE_DATA_WITH_ACK.ordinal(),
                ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_ACK_ID.getId(), (byte) counter, 15, 0, 0, 0,
                commandKey.getProjectId(), commandKey.getClazzId(), commandKey.getCommandId(), 0, 8, 0, 0, 0
            };
    }


    @Override
    public Acknowledge getAcknowledge() {

        return Acknowledge.AckBefore;
    }


    @Override
    public String toString() {

        return "Spiral";
    }


    @Override
    public int waitingTime() {

        return 7000;
    }
}