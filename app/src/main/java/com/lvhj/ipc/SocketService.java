package com.lvhj.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SocketService extends Service {


    private static final String TAG = "SocketService";

    private boolean isDestroyed = false;
    private String[] messages = new String[]{
            "测试1",
            "测试2",
            "测试3",
            "测试4",
            "测试5"
    };

    public SocketService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TCPServer()).start();
    }


    @Override
    public void onDestroy() {
        isDestroyed = true;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TCPServer implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!isDestroyed) {
                try {
                    final Socket client = serverSocket.accept();
                    Log.d(TAG, "accept");
                    new Thread() {
                        @Override
                        public void run() {
                            responseClient(client);
                        }
                    }.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void responseClient(Socket client) {
            try {
                // 接收客户端消息
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                // 响应客户端消息
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                Log.d(TAG, "欢迎来到聊天室！");
                out.println("欢迎来到聊天室！");
                while (!isDestroyed) {
                    String line = in.readLine();
                    Log.d(TAG, "message from Client: " + line);
                    if (line == null) break;
                    int i = new Random().nextInt(messages.length);
                    String message = messages[i];
                    out.println(message);
                    Log.d(TAG, "response to Client: " + message);
                }
                out.close();
                in.close();

                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
