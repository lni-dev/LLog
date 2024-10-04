/*
 * Copyright (c) 2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.localhost;

import de.linusdev.llog.LLog;
import de.linusdev.lutils.llist.LLinkedList;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class LLWebsocket extends Thread {
    private final @NotNull ServerSocket server;
    private final @NotNull LLinkedList<Socket> activeConnections = new LLinkedList<>();

    volatile boolean shutdown = false;


    public LLWebsocket(int port, @NotNull InetAddress address) throws IOException {
        super("llog-localhost-logger-websocket");
        this.server = new ServerSocket(port, 3, address);

        setDaemon(true);
    }

    public void shutdown() {
        this.shutdown = true;
    }

    public void sendToAll(@NotNull String message) {
        for (Socket socket : activeConnections) {
            try {
                var out = socket.getOutputStream();
                out.write(message.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                LLog.getLogInstance().throwable(e);
                activeConnections.remove(socket);
                try {
                    socket.close();
                } catch (IOException ex) {
                    LLog.getLogInstance().throwable(ex);
                }
            }
        }
    }

    @Override
    public void run() {

        Socket socket = null;
        while (!shutdown) {
            try {
                socket = server.accept();
                activeConnections.add(socket);
            } catch (Throwable e) {
                LLog.getLogInstance().throwable(e);
                if(socket != null) {
                    try {
                        socket.close();
                        activeConnections.remove(socket);
                    } catch (IOException ex) {
                        LLog.getLogInstance().throwable(ex);
                    }
                }
            }
            try {
                socket.setKeepAlive(true);
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            server.close();
        } catch (IOException e) {
            LLog.getLogInstance().throwable(e);
        }
    }
}
