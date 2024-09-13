/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.localhost;

import de.linusdev.lutils.routing.RequestHandler;
import de.linusdev.lutils.routing.Routing;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class LLRequestHandler extends Thread {
    private final @NotNull Routing routing;
    private final @NotNull ServerSocket server;

    volatile boolean shutdown = false;

    public LLRequestHandler(int port, @NotNull InetAddress address) throws IOException {
        this.server = new ServerSocket(port, 3, address);

        this.routing = Routing.builder().setPrefix("/llog/")
                .setExceptionHandler(t -> { t.printStackTrace(); return null; })
                .route("")
                    .GET(RequestHandler.ofHtmlResource(LocalhostLoggerImpl.class, "index.html")).buildRoute()
                .route("index.css")
                    .GET(RequestHandler.ofCssResource(LocalhostLoggerImpl.class, "index.css")).buildRoute()
                .build();

        setDaemon(true);
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    public void shutdown() {
        this.shutdown = true;
    }

    @Override
    public void run() {

        Socket socket = null;
        while (!shutdown) {
            try {
                socket = server.accept();
                routing.route(socket.getInputStream()).buildResponse(socket.getOutputStream());
                socket.close();

            } catch (Throwable e) {
                e.printStackTrace();
                if(socket != null) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                //TODO: handle exception
                //throw new RuntimeException(e);
            }
        }
    }
}
