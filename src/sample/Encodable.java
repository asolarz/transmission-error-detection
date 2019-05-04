package sample;

public interface Encodable {
    String encode(String s);

    boolean decode(String data, String CRC);

    String extractData(String message);

    String extractCode(String message);

}
