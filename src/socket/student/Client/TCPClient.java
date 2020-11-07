package socket.student.Client;

import socket.student.Common.Constant;
import socket.student.Common.WBit;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    private InputStream input;
    private OutputStream output;
    private Socket socket;

    TCPClient() throws IOException {
        socket = new Socket(Constant.ServerUrl, Constant.ListenPort);
        input = socket.getInputStream();
        output = socket.getOutputStream();
    }


    public byte[] sendDataToServer(byte[] data) throws IOException {
        byte[] result;

        try {
            output.write(data);
            output.flush();
            socket.shutdownOutput();

            byte[] receiveData = new byte[Constant.MaxTranslateSize];
            int receiveCount = input.read(receiveData);
            result = WBit.cutArrayByLength(receiveData, receiveCount);

            socket.shutdownInput();
            input.close();
            output.close();
            socket.close();

        } catch (IOException e) {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e1) {
                    System.out.println(e1);
                }
            }

            if (null != output) {
                try {
                    output.close();
                } catch (IOException e1) {
                    System.out.println(e1);
                }
            }

            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    System.out.println(e1);
                }
            }

            throw e;
        }

        return result;
    }

}
