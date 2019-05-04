package sample;

public class Parity implements Encodable {
    @Override
    public boolean decode(String data, String parity) {


        boolean correct = false;
        String checkParity = encode(data);

        if (checkParity.equals(parity))
            correct = true;
        return correct;

    }

    @Override
    public String extractData(String message) {
        return message.substring(0, message.length() - 1);
    }

    @Override
    public String extractCode(String message) {
        return message.substring(message.length() - 1);
    }

    @Override
    public String encode(String s) {
        String parityBit = "0";
        String binary = this.converToBinary(s);
        long counter = binary.chars().filter(ch -> ch == '1').count();
        if (counter % 2 != 0) {
            parityBit = "1";
        }

        return parityBit;
    }


    private String converToBinary(String text) {
        byte[] stringTab = text.getBytes();
        StringBuilder binaryText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            binaryText.append(String.format("%8s", Integer.toBinaryString(stringTab[i])).replace(' ', '0'));
        }
        return binaryText.toString();
    }


}
