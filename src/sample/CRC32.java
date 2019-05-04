package sample;

public class CRC32 implements Encodable {
    @Override
    public boolean decode(String data, String CRC) {

        boolean correct = false;
        String checkCRC = encode(data);

        if (checkCRC.equals(CRC))
            correct = true;

        return correct;

    }

    @Override
    public String extractData(String message) {
        return message.substring(0, message.length() - 32);
    }

    @Override
    public String extractCode(String message) {
        return message.substring(message.length() - 32);
    }

    @Override
    public String encode(String s) {

        byte[] buffer = s.getBytes();
        java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
        crc32.update(buffer);
        long crc = crc32.getValue();

        return String.format("%32s", Long.toBinaryString(crc)).replace(' ', '0');

    }
}
