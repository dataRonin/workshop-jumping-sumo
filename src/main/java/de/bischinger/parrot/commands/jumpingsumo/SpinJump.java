package de.bischinger.parrot.commands.jumpingsumo;

import de.bischinger.parrot.commands.ChannelType;
import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandKey;
import de.bischinger.parrot.commands.FrameType;


/**
 * @author  Alexander Bischof
 */
public class SpinJump implements Command {

    private final CommandKey commandKey = CommandKey.commandKey(3, 2, 4);

    protected SpinJump() {

        // use fabric method
    }

    public static SpinJump spinJump() {

        return new SpinJump();
    }


    @Override
    public byte[] getBytes(int counter) {

        return new byte[] {
                (byte) FrameType.ARNETWORKAL_FRAME_TYPE_DATA_WITH_ACK.ordinal(),
                ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_ACK_ID.getId(), (byte) counter, 15, 0, 0, 0,
                commandKey.getProjectId(), commandKey.getClazzId(), commandKey.getCommandId(), 0, 6, 0, 0, 0
            };
    }
}