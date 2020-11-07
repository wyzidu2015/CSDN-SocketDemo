package socket.student.Common.Instructions;


import socket.student.Common.Constant;
import socket.student.Common.Response;
import socket.student.Common.Student;

public class AddInstruction implements Instruction {
    private Student student;

    public AddInstruction(Student stu) {
        this.student = stu;
    }

    public AddInstruction(byte[] data) throws Exception {
        this.student = new Student(data, false);
    }

    @Override
    public byte[] encode() throws Exception {
        return this.student.toBytes(false);
    }

    @Override
    public Integer getInstructionType() {
        return 1;
    }

    @Override
    public byte[] execute() {
        System.out.println("Execute add student operations for stu: " + student.toString());
        return Response.getResponseBytes(Constant.Success);
    }

    public Student getStudent() {
        return student;
    }

}
