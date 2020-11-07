package socket.student.Common.Instructions;


public interface Instruction {

    // encode instruction to byte array
    byte[] encode() throws Exception;

    // get instruction type
    Integer getInstructionType();

    // execute command, not implement yet
    byte[] execute();
}
