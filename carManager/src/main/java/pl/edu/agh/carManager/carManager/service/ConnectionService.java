package pl.edu.agh.carManager.carManager.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.edu.agh.carManager.carManager.error.CarDisconnected;
import pl.edu.agh.carManager.carManager.error.CarDoesNotExistError;
import pl.edu.agh.carManager.carManager.persistence.CarRepository;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@Component
public class ConnectionService extends Thread {
    private Selector selector;
    private String serverAddress;
    private Integer serverPort;
    private ServerSocketChannel serverSocketChannel;
    private final Map<String, CarConnection> connectionMap = new HashMap<>();
    private final CarsMap carsMap;

    public ConnectionService(Environment environment, CarsMap carsMap) {
        System.out.println("STARTING");
        createSelector();
        serverAddress = environment.getProperty("tcp.server.address");
        serverPort = Integer.valueOf(Objects.requireNonNull(environment.getProperty("tcp.server.port")));
        createServerSocket();
        try {
            System.out.println(serverSocketChannel.getLocalAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.carsMap = carsMap;
    }

    @Override
    public void run() {
        System.out.println("AWAITING CALLS");
        while(true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while(keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                processKey(key);
            }
        }
    }

    public void sendMessage(String message, Integer carId) throws CarDoesNotExistError {
        String carsToken = carsMap.getCarsToken(carId);
        if(carsToken == null || !connectionMap.containsKey(carsToken)) {
            throw new CarDoesNotExistError();
        }
        CarConnection connection = connectionMap.get(carsToken);
        connection.send(message);
    }

    private void createSelector() {
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createServerSocket() {
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(this.serverAddress, this.serverPort));
            this.serverSocketChannel.configureBlocking(false);
            this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, serverSocketChannel);
            System.out.println("ENDING SERVER SOCKER CREATION");
            selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processKey(SelectionKey key) {
        if(key.isAcceptable()) {
            try {
                System.out.println("ADDING CONNECTION");
                SocketChannel channel = serverSocketChannel.accept();
                if(channel != null) {
                    CarConnection connection = new CarConnection(channel);
                    connection.register(selector);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(key.isReadable()) {
            System.out.println("RECEIVING MESSAGE");
            CarConnection connection = (CarConnection) key.attachment();
            Position position = null;
            try {
                position = connection.read();
                String token = connection.getCarToken();
                if(!carsMap.isCarRegistered(token)) {
                    System.out.println("UNREGISTERED CONNECTION SPOTTED!");
                    disconnectConnection(connection);
                    return;
                }
                if(!connectionMap.containsKey(token)) {
                    connectionMap.put(token, connection);
                }
                System.out.println(position.latitude() + ";" + position.longitude());
            } catch (CarDisconnected e) {
                disconnectConnection(connection);
            }
        }
    }

    private void disconnectConnection(CarConnection carConnection) {
        connectionMap.remove(carConnection.getCarToken());
        carConnection.close();
    }
}
