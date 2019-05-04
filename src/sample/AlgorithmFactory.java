package sample;

public class AlgorithmFactory {

    Encodable createAlgorithm(Algorithm algorithm) {
        Encodable encoder = null;
        switch (algorithm) {
            case CRC8:
                encoder = new CRC8();
                break;
            case CRC16:
                encoder = new CRC16();
                break;
            case CRC32:
                encoder = new CRC32();
                break;
            case PARITY:
                encoder = new Parity();
                break;
            default:
        }
        return encoder;
    }
}
