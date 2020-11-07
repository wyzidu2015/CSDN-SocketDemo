package socket.student.Server;

import socket.student.Common.Constant;
import socket.student.Common.Instructions.Instruction;
import socket.student.Common.Instructions.InstructionFactory;
import socket.student.Common.WBit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPServer {
    private final Integer BUFSIZE = 16;

    private ServerSocket serverSocket;

    TCPServer() throws IOException {
        this.serverSocket = new ServerSocket(Constant.ListenPort);
    }

    public void run() throws Exception {
        System.out.println("Listening on port: " + Constant.ListenPort);
        int recvMsgSize;
        while (true) {
            Socket clntSock = this.serverSocket.accept();
            SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
            System.out.println("Handling client from " + clientAddress);

            InputStream in = clntSock.getInputStream();
            byte[] buffer = new byte[BUFSIZE];
            byte[] receiveBytes = new byte[1024];
            int work = 0;
            while (-1 != (recvMsgSize = in.read(buffer))) {
                System.arraycopy(buffer, 0, receiveBytes, work, recvMsgSize);
                if (-1 != recvMsgSize) {
                    work += recvMsgSize;
                }
            }

            receiveBytes = WBit.cutArrayByLength(receiveBytes, work);
            clntSock.shutdownInput();

            OutputStream out = clntSock.getOutputStream();
            InstructionFactory factory = new InstructionFactory(receiveBytes);
            Instruction instruction = factory.getInstruction();

            byte[] result = instruction.execute();

            out.write(result);
            out.flush();

            clntSock.shutdownOutput();
        }
    }

    public static void main(String[] args) throws Exception {
        TCPServer tcpServer = new TCPServer();
        tcpServer.run();
    }
}
