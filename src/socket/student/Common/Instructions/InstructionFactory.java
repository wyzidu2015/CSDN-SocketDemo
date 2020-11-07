package socket.student.Common.Instructions;

import socket.student.Common.Constant;

public class InstructionFactory {
    private Instruction instruction;
    private byte[] bytes;

    public InstructionFactory(Instruction instruction) {
        this.instruction = instruction;
    }

    public InstructionFactory(byte[] data) throws Exception {
        if (data.length == 0) {
            throw new Exception("Data is empty, can't decode");
        }

        bytes = data;
    }

    public byte[] getBytes() throws Exception {
        if (bytes == null) {
            byte[] instrBytes = instruction.encode();

            byte header = instruction.getInstructionType().byteValue();
            header |= Constant.ProtoctolNumber.byteValue() << 4;

            bytes = new byte[instrBytes.length + 1];
            bytes[0] = header;

            System.arraycopy(instrBytes, 0, bytes, 1, instrBytes.length);
        }

        return bytes;
    }

    public Instruction getInstruction() throws Exception {
        if (instruction == null) {

            byte header = bytes[0];
            byte[] instrBytes = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, instrBytes, 0, bytes.length - 1);

            int protoctolNumber = header >> 4;
            if (protoctolNumber != Constant.ProtoctolNumber) {
                System.out.printf("%d %d", protoctolNumber, Constant.ProtoctolNumber);
                throw new Exception("Protoctol don't match");
            }

            int type = header & 0x07;
            switch (type) {
                case 1:
                    instruction = new AddInstruction(instrBytes);
                    break;
                case 2:
                    instruction = new DeleteInstruction(instrBytes);
                    break;
                case 3:
                    instruction = new UpdateInstruction(instrBytes);
                    break;
                case 4:
                    instruction = new SelectInstruction(instrBytes);
                    break;
            }
        }

        return instruction;
    }

}
