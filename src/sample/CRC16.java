package sample;

public class CRC16 implements Encodable {
    /**
     * @param s text
     * @return String CRC binary representation
     */
    @Override
    public String encode(String s) {
        byte[] buffer = s.getBytes();

        int crc = 0xFFFF;
        int polynomial = 0x8005;

        for (byte b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        return String.format("%16s", Integer.toBinaryString(crc)).replace(' ', '0');
    }


    /**
     * @param data,crc
     * @return true - if text chceck ok
     * false - if text or crc modified
     */
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
        return message.substring(0, message.length() - 16);
    }

    @Override
    public String extractCode(String message) {
        return message.substring(message.length() - 16);
    }
}




