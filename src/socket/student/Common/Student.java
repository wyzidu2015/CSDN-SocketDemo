package socket.student.Common;

import java.nio.ByteBuffer;

import static socket.student.Common.Constant.ByteSize;
import static socket.student.Common.Constant.DefaultCharset;


public class Student {

    private Integer id;
    private String name;
    private Sex sex;
    private Integer age;

    public Student(String name, Sex sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] toBytes(boolean containsId) throws Exception {
        WBit wbit = new WBit(containsId ? Constant.TotalLength + Constant.IdLength : Constant.TotalLength);

        if (containsId) {
            id = 12;
            byte[] idBytes = { id.byteValue()};
            wbit.put(idBytes, Constant.IdLength);
        }

        wbit.put(name.getBytes(DefaultCharset), Constant.NameLength);

        byte sexByte = (byte)(sex == Sex.Male ? 0 : 1);
        byte ageByte = age.byteValue();
        byte sexAndAgeByte = (byte)(sexByte << 7 | ageByte);

        byte[] sexAndAgeRes = { sexAndAgeByte };
        wbit.put(sexAndAgeRes, 8);

        return wbit.getBytes();
    }

    @Override
    public String toString() {
        return "Student("
                + this.id + ", "
                + this.name + ", "
                + this.sex + ", "
                + this.age + ')';
    }

    public Student(byte[] data, boolean containsId) throws Exception {
        WBit wbit = new WBit(data, containsId ? Constant.TotalLength + Constant.IdLength : Constant.TotalLength);

        if (containsId) {
            byte[] idRes = wbit.get(Constant.IdLength);
            this.id = ByteBuffer.wrap(idRes).getInt();
        }

        this.name = new String(wbit.get(Constant.NameLength), DefaultCharset);

        byte sexAndAgeByte = wbit.get(ByteSize)[0];
        this.sex = sexAndAgeByte >> 7 == 0 ? Sex.Male : Sex.Female;
        this.age = sexAndAgeByte & 0x7F;
    }

}
