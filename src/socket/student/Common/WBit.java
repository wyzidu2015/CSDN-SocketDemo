package socket.student.Common;

import static socket.student.Common.Constant.ByteSize;

public class WBit {
    private byte[] bytes;
    private int pos;

    public WBit(int length) {
        int arrayLength = (length % ByteSize == 0) ? length / ByteSize : (length / ByteSize + 1);
        bytes = new byte[arrayLength];
        this.pos = 0;
    }

    public WBit(byte[] bytes, int pos) {
        this.pos = pos;
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int getSize() {
        return this.pos;
    }

    public void put(byte[] array, int length) throws Exception {
        if (length % ByteSize != 0) {
            throw new Exception("Not support non-integer length.");
        }

        int byteIndex = this.pos / ByteSize;

        if (array.length < length / ByteSize) {
            int supplyZeroCount = length / ByteSize - array.length;
            for (int i = 0; i < supplyZeroCount; i++) {
                bytes[i + byteIndex] = (byte)0;
            }

            byteIndex += supplyZeroCount;
        }

        System.arraycopy(array, 0, bytes, byteIndex, array.length);
        pos += length;
    }

    public void putByte(byte data) throws Exception {
        put(new byte[]{ data }, ByteSize);
    }

    public byte[] get(int length) throws Exception {
        if (length % ByteSize != 0) {
            throw new Exception("Not support non-integer length.");
        }

        this.pos -= length;
        int byteIndex = this.pos / ByteSize;

        byte[] stringBytes = new byte[length / ByteSize];
        int i = Math.max(0, byteIndex - 1), work = 0;
        for (; i < length / ByteSize; i++) {
            if (bytes[i] != 0) {
                stringBytes[work++] = bytes[i];
                bytes[i] = 0;
            }
        }

        System.arraycopy(bytes, i, bytes, 0, this.pos / ByteSize);

        byte[] result = new byte[work];
        System.arraycopy(stringBytes, 0, result, 0, work);

        return result;
    }

    public byte getByte() throws Exception {
        byte[] result = get(ByteSize);
        if (result.length == 0) {
            return (byte) 0;
        }

        return result[0];
    }

    public static byte[] cutArrayByLength(byte[] bytes, int length) {
        byte[] result = new byte[length];
        System.arraycopy(bytes, 0, result, 0, length);

        return result;
    }
}