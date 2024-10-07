/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.localhost;

import de.linusdev.llog.LLog;
import de.linusdev.lutils.net.routing.RequestHandler;
import de.linusdev.lutils.net.routing.Routing;
import de.linusdev.lutils.net.ws.WebSocketServer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;

import static de.linusdev.llog.impl.localhost.LocalhostLoggerImpl.WEBSITE_PREFIX;

public class LLRequestHandler extends Thread {
    private final @NotNull Routing routing;
    private final @NotNull ServerSocket server;

    volatile boolean shutdown = false;

    public LLRequestHandler(int port, @NotNull InetAddress address, LLWebsocket llWebsocket) throws IOException, NoSuchAlgorithmException {
        super("llog-localhost-logger-request-handler");
        System.out.println("LocalhostLogger: Serving site at http://" + address.getHostName() + ":" + port + "/" + WEBSITE_PREFIX);
        this.server = new ServerSocket(port, 3, address);

        this.routing = Routing.builder().setPrefix("/" + WEBSITE_PREFIX + "/")
                .setExceptionHandler(t -> { t.printStackTrace(); return null; })
                .route("")
                    .GET(RequestHandler.ofHtmlResource(LocalhostLoggerImpl.class, "index.html")).buildRoute()
                .route("index.css")
                    .GET(RequestHandler.ofCssResource(LocalhostLoggerImpl.class, "index.css")).buildRoute()
                .route("index.js")
                    .GET(RequestHandler.ofJsResource(LocalhostLoggerImpl.class, "index.js")).buildRoute()
                .route("websocket")
                    .GET(new WebSocketServer(llWebsocket::addConnection)).buildRoute()
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
        while (!shutdown) {
            try {
                routing.route(server.accept());
            } catch (Throwable e) {
                LLog.getLogInstance().throwable(e);
            }
        }

        try {
            server.close();
        } catch (IOException e) {
            LLog.getLogInstance().throwable(e);
        }
    }
}
