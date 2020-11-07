package socket.student.Common.Instructions;

import socket.student.Common.Constant;
import socket.student.Common.Response;
import socket.student.Common.WBit;

public class SelectInstruction implements Instruction {

    private Integer pageNo;
    private Integer pageSize;

    public SelectInstruction(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public SelectInstruction(byte[] data) throws Exception {
        WBit wBit = new WBit(data, Constant.AllPageCount + Constant.AllPageSize);
        byte[] pageNoBytes = wBit.get(Constant.AllPageCount);
        this.pageNo = pageNoBytes.length == 0 ? 0 : (int) pageNoBytes[0];

        byte[] pageSizeBytes = wBit.get(Constant.AllPageSize);
        this.pageSize = pageSizeBytes.length == 0 ? 0 : (int) pageSizeBytes[0];
    }

    @Override
    public byte[] encode() throws Exception {
        WBit wbit = new WBit(Constant.AllPageCount + Constant.AllPageSize);
        byte[] pageCountBytes = new byte[]{ pageNo.byteValue() };
        wbit.put(pageCountBytes, Constant.AllPageCount);

        byte[] pageSizeBytes = new byte[]{ pageSize.byteValue() };
        wbit.put(pageSizeBytes, Constant.AllPageSize);

        return wbit.getBytes();
    }

    @Override
    public Integer getInstructionType() {
        return 4;
    }

    @Override
    public byte[] execute() {
        System.out.printf("Execute select student operations of given pageNo: %d and pageSize: %d", pageNo, pageSize);
        return Response.getResponseBytes(Constant.Success);
    }
}
