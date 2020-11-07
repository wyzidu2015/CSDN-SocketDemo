package socket.student.Common.Instructions;

import socket.student.Common.Constant;
import socket.student.Common.Response;
import socket.student.Common.Sex;
import socket.student.Common.WBit;

public class UpdateInstruction implements Instruction {
    private Integer id;
    private Integer age;
    private Sex sex;
    private boolean updateAge;

    public UpdateInstruction(Integer id, Integer age) {
        this.id = id;
        this.age = age;
        updateAge = true;
    }

    public UpdateInstruction(Integer id, Sex sex) {
        this.id = id;
        this.sex = sex;
        updateAge = false;
    }

    public UpdateInstruction(byte[] data) throws Exception {
        WBit wBit = new WBit(data, Constant.IdLength + 2 * 8);
        byte[] idBytes = wBit.get(Constant.IdLength);

        if (idBytes.length == 1) {
            id = (int) idBytes[0];
        }

        int flag = wBit.getByte();

        if (flag == 1) {
            age = (int) wBit.getByte();
        } else {
            sex = wBit.getByte() == 0 ? Sex.Male : Sex.Female;
        }
    }

    @Override
    public byte[] encode() throws Exception {
        WBit wbit = new WBit(Constant.IdLength + 2 * 8);
        wbit.put(new byte[]{ id.byteValue() }, Constant.IdLength);
        wbit.putByte((byte)(updateAge ? 1 : 0));

        if (updateAge) {
            wbit.putByte(age.byteValue());
        } else {
            wbit.putByte((byte)(sex == Sex.Male ? 0 : 1));
        }

        return wbit.getBytes();
    }

    @Override
    public Integer getInstructionType() {
        return 3;
    }

    @Override
    public byte[] execute() {
        if (updateAge) {
            System.out.printf("Execute update student operations of given id(%s) and age(%d)", id, age);
        } else {
            System.out.printf("Execute update student operations of given id(%s) and sex(%s)", id,
                    sex == Sex.Male ? "man" : "woman");
        }

        return Response.getResponseBytes(Constant.Success);
    }
}
