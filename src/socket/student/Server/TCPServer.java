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

    private ServerSocket serverSocket;

    TCPServer() throws IOException {
        this.serverSocket = new ServerSocket(Constant.ListenPort);
    }

    public void run() {
        System.out.println("Listening on port: " + Constant.ListenPort);
        int recvMsgSize;
        Socket clntSock = null;
        InputStream in = null;
        OutputStream out = null;

        while (true) {
            try {
                clntSock = this.serverSocket.accept();
                SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
                System.out.println("Handling client from " + clientAddress);

                in = clntSock.getInputStream();
                byte[] buffer = new byte[Constant.BufferSize];
                byte[] receiveBytes = new byte[Constant.MaxTranslateSize];
                int work = 0;
                while (-1 != (recvMsgSize = in.read(buffer))) {
                    System.arraycopy(buffer, 0, receiveBytes, work, recvMsgSize);
                    work += recvMsgSize;
                }

                receiveBytes = WBit.cutArrayByLength(receiveBytes, work);
                clntSock.shutdownInput();

                out = clntSock.getOutputStream();
                InstructionFactory factory = new InstructionFactory(receiveBytes);
                Instruction instruction = factory.getInstruction();

                byte[] result = instruction.execute();

                out.write(result);
                out.flush();

                clntSock.shutdownOutput();
            } catch (Exception e) {
                System.out.println(e);
                if (null != out) {
                    try {
                        out.close();
                    } catch (IOException e1) {
                        System.out.println(e1);
                    }
                }

                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e1) {
                        System.out.println(e1);
                    }
                }

                if (null != clntSock) {
                    try {
                        clntSock.close();
                    } catch (IOException e1) {
                        System.out.println(e1);
                    }
                }

            }
        }
    }

    public static void main(String[] args) throws Exception {
        TCPServer tcpServer = new TCPServer();
        tcpServer.run();
    }
}
