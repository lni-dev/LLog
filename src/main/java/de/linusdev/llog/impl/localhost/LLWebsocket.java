/*
 * Copyright (c) 2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.localhost;

import de.linusdev.llog.LLog;
import de.linusdev.lutils.llist.LLinkedList;
import de.linusdev.lutils.net.ws.WebSocket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LLWebsocket extends Thread {
    private final @NotNull LLinkedList<WebSocket> activeConnections = new LLinkedList<>();

    volatile boolean shutdown = false;


    public LLWebsocket() throws IOException {
        super("llog-localhost-logger-websocket");

        setDaemon(true);
    }

    public void addConnection(@NotNull WebSocket webSocket) {
        System.out.println("added connection");
        activeConnections.add(webSocket);
    }

    public void shutdown() {
        this.shutdown = true;
    }

    public void sendToAll(@NotNull String message) {
        for (WebSocket socket : activeConnections) {
            try {
                socket.sendText(message);
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
       //TODO: receive messages.
    }
}
