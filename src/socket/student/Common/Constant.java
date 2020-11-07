package socket.student.Common;


public class Constant {
    public static final int IdLength = 16;
    public static final int NameLength = 64;
    public static final int SexLength = 1;
    public static final int AgeLength = 7;
    public static final int TotalLength = NameLength + SexLength + AgeLength;

    public static final Integer ProtoctolNumber = 1;

    public static final String DefaultCharset = "GBK";
    public static final int ByteSize = 8;

    public static final Integer StatusResponseNumber = 10;

    public static final Integer Success = 1;

    public static final int ListenPort = 10240;
    public static final String ServerUrl = "localhost";
    public static final int AllPageCount = 16;
    public static final int AllPageSize = 8;
}
