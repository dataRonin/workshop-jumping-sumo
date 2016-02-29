package de.bischinger.parrot.network;

import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandException;
import de.bischinger.parrot.commands.common.CurrentDate;
import de.bischinger.parrot.commands.common.CurrentTime;
import de.bischinger.parrot.commands.common.Pong;
import de.bischinger.parrot.listener.EventListener;
import de.bischinger.parrot.network.handshake.HandshakeRequest;
import de.bischinger.parrot.network.handshake.HandshakeResponse;
import de.bischinger.parrot.network.handshake.TcpHandshake;

import java.io.IOException;

import java.lang.invoke.MethodHandles;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.time.Clock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

import static java.net.InetAddress.getByName;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


/**
 * Represents the connection to the drone.
 *
 * @author  Tobias Schneider
 */
public class WirelessLanDroneConnection implements DroneConnection {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    private final List<EventListener> eventListeners = new ArrayList<>();
    private final DatagramSocket datagramSocket = new DatagramSocket();

    private final String deviceIp;
    private final int tcpPort;
    private final String wlanName;

    private int devicePort;
    private byte noAckCounter = 0;
    private byte ackCounter = 0;

    public WirelessLanDroneConnection(String deviceIp, int tcpPort, String wlanName) throws IOException {

        LOGGER.info(format("Creating DroneConnector for %s:%s...", deviceIp, tcpPort));
        this.deviceIp = deviceIp;
        this.tcpPort = tcpPort;
        this.wlanName = wlanName;
    }

    @Override
    public void connect() throws IOException {

        LOGGER.info("Connecting to drone...");

        HandshakeRequest handshakeRequest = new HandshakeRequest(wlanName, "_arsdk-0902._udp");
        HandshakeResponse handshakeResponse = new TcpHandshake(deviceIp, tcpPort).shake(handshakeRequest);
        devicePort = handshakeResponse.getC2d_port();
        LOGGER.info(format("Connected: Handshake completed with %s", handshakeResponse));

        Clock clock = Clock.systemDefaultZone();
        sendCommand(CurrentDate.currentDate(clock));
        sendCommand(CurrentTime.currentTime(clock));

        addAnswerSocket();
    }


    @Override
    public void sendCommand(Command command) throws IOException {

        byte[] packet = command.getBytes(changeAndGetCounter(command));

        LOGGER.fine(format("Sending command: %s", Arrays.toString(packet)));
        datagramSocket.send(new DatagramPacket(packet, packet.length, getByName(deviceIp), devicePort));

        // TODO FIX TRUE
        if (true) {
            try {
                MILLISECONDS.sleep(580);
            } catch (InterruptedException e) {
                throw new CommandException("I got interrupted while sleeping. That is not nice from you.", e);
            }
        }
    }


    private int changeAndGetCounter(Command command) {

        int counter = 0;

        switch (command.getAcknowledge()) {
            case None:
                break;

            case AckBefore:
                counter = ++ackCounter;
                break;

            case AckAfter:
                counter = ackCounter++;
                break;

            case NoAckBefore:
                counter = ++noAckCounter;
                break;
        }

        return counter;
    }


    @Override
    public void addEventListener(EventListener eventListener) {

        this.eventListeners.add(eventListener);
    }


    private void addAnswerSocket() {

        new Thread(() -> {
            try(DatagramSocket sumoSocket = new DatagramSocket(devicePort)) {
                LOGGER.info(format("Listing for answers on port %s", devicePort));

                while (true) {
                    byte[] buf = new byte[65000];

                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    sumoSocket.receive(packet);
                    LOGGER.config(format("Listing for answers on port %s", devicePort));

                    byte[] data = packet.getData();

                    eventListeners.stream().forEach(eventListener -> eventListener.eventFired(data));

                    // Answer with a Pong
                    if (data[1] == 126) {
                        sendCommand(Pong.pong(data[3]));
                    }

                    // FIXME
//                    CommandReader commandReader = CommandReader.commandReader(data);
//
//                    if (commandReader.isPing() || commandReader.isLinkQualityChanged()
//                            || commandReader.isWifiSignalChanged()) {
//                        continue;
//                    }
                }
            } catch (IOException e) {
                // TODO own exception with handling?
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public void close() {

        datagramSocket.close();
    }
}
