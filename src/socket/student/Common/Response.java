package socket.student.Common;


public class Response {
    private static byte getHeader(Integer typeNumber) {
        byte result = typeNumber.byteValue();
        result |= Constant.ProtoctolNumber << 4;

        return result;
    }

    public static byte[] getResponseBytes(int status) {
        byte header = getHeader(Constant.StatusResponseNumber);
        byte result = new Integer(status).byteValue();

        return new byte[]{ header, result };
    }


    public static int getStatusByResponse(byte[] bytes) throws Exception {
        if (bytes.length == 0) {
            throw new Exception("Response is empty");
        }

        byte header = bytes[0];
        Integer typeNumber = header & 0x0F;
        Integer protoctol = header >> 4;

        if (!protoctol.equals(Constant.ProtoctolNumber)) {
            throw new Exception("Protoctol number don't match");
        }

        if (!typeNumber.equals(Constant.StatusResponseNumber)) {
            throw new Exception("Error response type");
        }

        return bytes[1];
    }
}
