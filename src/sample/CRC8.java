package sample;

public class CRC8 implements Encodable {
    @Override
    public boolean decode(String data, String CRC) {

        boolean correct = false;
        String checkCRC = encode(data);

        if (checkCRC.equals(CRC))
            correct = true;

        System.out.println("OLD CRC: " + CRC + " NEW CRC " + checkCRC);
        return correct;

    }

    @Override
    public String extractData(String message) {
        return message.substring(0, message.length() - 8);
    }

    @Override
    public String extractCode(String message) {
        return message.substring(message.length() - 8);
    }

    @Override
    public String encode(String s) {

        byte[] buffer = s.getBytes();
        int length = buffer.length - 1;
        int crc = 0;
        int data_bit = 0x80;
        while (length > 0) {
            if (((crc & 0x01) != 0) != ((buffer[length] & data_bit) != 0)) {
                crc >>= 1;
                crc ^= 0xCD;
            } else {
                crc >>= 1;
            }
            data_bit >>= 1;
            if (data_bit == 0) {
                data_bit = 0x80;
                length--;
            }
        }
        return String.format("%8s", Integer.toBinaryString(crc)).replace(' ', '0');
    }
}
