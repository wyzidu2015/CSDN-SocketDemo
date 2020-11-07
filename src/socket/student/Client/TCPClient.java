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
        output.write(data);
        output.flush();
        socket.shutdownOutput();

        byte[] receiveData = new byte[128];
        int receiveCount = input.read(receiveData);
        byte[] result = WBit.cutArrayByLength(receiveData, receiveCount);

        socket.shutdownInput();

        return result;
    }

}
