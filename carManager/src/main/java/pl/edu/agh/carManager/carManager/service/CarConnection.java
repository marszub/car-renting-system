package pl.edu.agh.carManager.carManager.service;

import com.zeroc.Ice.ConnectionLostException;
import pl.edu.agh.carManager.carManager.error.CarDisconnected;
import pl.edu.agh.carManager.carManager.persistence.Car;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CarConnection{
    private SocketChannel connection;
    private Selector selector;
    static final int bytesBufferSize = 2048;
    private SelectionKey key;
    private String carToken;

    public CarConnection(SocketChannel connection) {
        this.connection = connection;
        try {
            this.connection.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        carToken = null;
    }

    public void register(Selector selector) {
        this.selector = selector;
        try {
            this.key = this.connection.register(selector, SelectionKey.OP_READ, this);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        key.cancel();
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        ByteBuffer buff = ByteBuffer.allocate(message.length());
        buff.clear();
        buff.put(message.getBytes());
        buff.flip();
        while (buff.hasRemaining()) {
            try {
                connection.write(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCarToken() {
        return carToken;
    }

    public Position read() throws CarDisconnected {
        ByteBuffer buf = ByteBuffer.allocate(bytesBufferSize);
        int bytesRead = 1;
        try {
            bytesRead = connection.read(buf);
        } catch (IOException e) {
            System.out.println("Connection lost");
            throw new CarDisconnected();
        }
        if(bytesRead == -1) {
            System.out.println("Connection lost");
            throw new CarDisconnected();
        }
        if(bytesRead > 0) {
            String message = new String(buf.array());
            String[] splitString = message.split(";");
            if(carToken == null) {
                carToken = splitString[0];
            }
            if(!Objects.equals(carToken, splitString[0])) {
                System.out.println("ERROR");
            }
            return new Position(splitString[1], splitString[2]);
        }
        return null;
    }
}
