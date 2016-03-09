package de.bischinger.parrot.lib.listener;

import java.util.function.Consumer;


/**
 * @author  Tobias Schneider
 */
public final class BatteryListener implements EventListener {

    private final Consumer<Byte> consumer;

    protected BatteryListener(Consumer<Byte> consumer) {

        this.consumer = consumer;
    }

    public static BatteryListener batteryListener(Consumer<Byte> consumer) {

        return new BatteryListener(consumer);
    }


    @Override
    public void eventFired(byte[] data) {

        if (filterProject(data, 0, 5, 1)) {
            consumer.accept(data[11]);
        }
    }
}