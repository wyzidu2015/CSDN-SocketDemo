package socket.student.Common.Instructions;


import socket.student.Common.Constant;
import socket.student.Common.Response;
import socket.student.Common.WBit;

import java.nio.ByteBuffer;

public class DeleteInstruction implements Instruction {
    private Integer id;

    public DeleteInstruction(Integer id) {
        this.id = id;
    }
    public DeleteInstruction(byte[] data) throws Exception {
        WBit wBit = new WBit(data, Constant.IdLength);

        byte[] bytes = wBit.get(Constant.IdLength);
        if (bytes.length == 1) {
            this.id = (int) bytes[0];
        } else {
            this.id = ByteBuffer.wrap(bytes).getInt();
        }

    }

    @Override
    public byte[] encode() throws Exception {
        WBit wbit = new WBit(Constant.IdLength);
        byte[] idBytes = new byte[]{ id.byteValue() };
        wbit.put(idBytes, Constant.IdLength);

        return wbit.getBytes();
    }

    @Override
    public Integer getInstructionType() {
        return 2;
    }

    @Override
    public byte[] execute() {
        System.out.println("Execute delete student operations of id: " + id);
        return Response.getResponseBytes(Constant.Success);
    }

    public Integer getId() {
        return id;
    }
}
