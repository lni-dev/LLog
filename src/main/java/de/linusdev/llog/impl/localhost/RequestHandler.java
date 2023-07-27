/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.localhost;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler extends Thread {

    private final @NotNull ServerSocket server;


    public RequestHandler(int port, @NotNull InetAddress address) throws IOException {
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

        while (true) {
            try {
                Socket socket = server.accept();



                InputStreamReader isr = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
            } catch (IOException e) {
                //TODO: handle exception
                throw new RuntimeException(e);
            }
        }

    }
}
