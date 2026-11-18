package tienich;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MaHoa {
    public static String bamSha256(String chuoi) {
        try {
            byte[] bam = MessageDigest.getInstance("SHA-256").digest(chuoi.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, bam));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
