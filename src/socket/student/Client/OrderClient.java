package socket.student.Client;

import socket.student.Common.Instructions.*;
import socket.student.Common.Response;
import socket.student.Common.Sex;
import socket.student.Common.Student;

public class OrderClient {

    private int sendDataToGetStatusResponse(Instruction instruction) throws Exception {
        InstructionFactory factory = new InstructionFactory(instruction);
        byte[] commandBytes = factory.getBytes();

        TCPClient client = new TCPClient();
        byte[] bytes = client.sendDataToServer(commandBytes);

        return Response.getStatusByResponse(bytes);
    }

    public void add(String name, Sex sex, Integer age) throws Exception {
        AddInstruction add = new AddInstruction(new Student(name, sex, age));
        System.out.println(sendDataToGetStatusResponse(add));
    }

    public void delete(int id) throws Exception {
        DeleteInstruction delete = new DeleteInstruction(id);
        System.out.println(sendDataToGetStatusResponse(delete));
    }

    public void updateAge(int id, int age) throws Exception {
        UpdateInstruction update = new UpdateInstruction(id, age);
        System.out.println(sendDataToGetStatusResponse(update));
    }

    public void updateSex(int id, Sex sex) throws Exception {
        UpdateInstruction update = new UpdateInstruction(id, sex);
        System.out.println(sendDataToGetStatusResponse(update));
    }

    public void select(int pageNo, int pageSize) throws Exception {
        SelectInstruction select = new SelectInstruction(pageNo, pageSize);
        System.out.println(sendDataToGetStatusResponse(select));
    }

    public static void main(String[] args) throws Exception {
        OrderClient client = new OrderClient();
        client.add("小明", Sex.Male, 19);

        client.delete(9);
        client.updateAge(8, 14);
        client.updateSex(8, Sex.Female);

        client.select(1, 10);
    }
}
