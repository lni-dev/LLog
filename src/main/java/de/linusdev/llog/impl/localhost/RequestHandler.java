/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.localhost;

import de.linusdev.lutils.http.HTTPRequest;
import de.linusdev.lutils.http.HTTPResponse;
import de.linusdev.lutils.http.body.Bodies;
import de.linusdev.lutils.http.body.BodyParsers;
import de.linusdev.lutils.http.body.UnparsedBody;
import de.linusdev.lutils.http.status.StatusCodes;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class RequestHandler extends Thread {

    private final @NotNull ServerSocket server;


    public RequestHandler(int port, @NotNull InetAddress address) throws IOException {
        System.out.println("Start Request Handler");
        this.server = new ServerSocket(port, 3, address);
        setDaemon(true);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {

        Socket socket = null;
        while (true) {
            try {
                System.out.println("Wait for accept");
                socket = server.accept();
                System.out.println("accepted!");

                HTTPRequest<UnparsedBody> request = HTTPRequest.parse(socket.getInputStream(), BodyParsers.newUnparsedBodyParser());

                System.out.println("request.getPath(): " + request.getPath());

                if(Objects.equals(request.getPath(), "/logger")) {
                    HTTPResponse.builder()
                            .setBody(Bodies.html().ofResource(LocalhostLoggerImpl.class, "index.html"))
                            .buildResponse(socket.getOutputStream());
                } else if(Objects.equals(request.getPath(), "/logger/index.css")) {
                    HTTPResponse.builder()
                            .setBody(Bodies.css().ofResource(RequestHandler.class, "index.css"))
                            .buildResponse(socket.getOutputStream());
                } else {
                    HTTPResponse.builder().setStatusCode(StatusCodes.NOT_FOUND).buildResponse(socket.getOutputStream());
                }

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
