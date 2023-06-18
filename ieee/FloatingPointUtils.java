package ieee;
import java.math.BigInteger;

import org.kerbaya.ieee754lib.BitSink;
import org.kerbaya.ieee754lib.BitSource;
import org.kerbaya.ieee754lib.BitUtils;
import org.kerbaya.ieee754lib.IEEE754;
import org.kerbaya.ieee754lib.IEEE754.IEEE754Number;
import org.kerbaya.ieee754lib.IEEE754Format;

public class FloatingPointUtils {
    
    private static IEEE754Format ieee = IEEE754Format.SINGLE;
    private static IEEE754Format ieee7bitExp = new IEEE754Format(7, 23, BigInteger.valueOf(0));

    
    public static byte[] readBinaryString(String b){
        b = b.replaceAll(" ", "");
        int len = b.length();
        if (len == 8) {
            return new BigInteger(b, 16).toByteArray();
        } else if (len == 32) {
            return new BigInteger(b, 2).toByteArray();
        } else {
            throw new IllegalArgumentException("Incorrect input data!");
        }
    }

    public static IEEE754 parseIEEE754(String b) {
        BitSource source = BitUtils.wrapSource(readBinaryString(b));
        return IEEE754.decode(ieee, source);
    }
    
    public static IEEE754 parseInternal(String b) {
        //tu źle coś
        String binary = String.format("%32s",new BigInteger(readBinaryString(b)).toString(2)).replace(' ', '0');

        binary = binary.replaceAll(" ", "");
        if(!binary.contains("1")){
            return (IEEE754Number) IEEE754.POSITIVE_ZERO;
        }
        if(binary.startsWith("0")){
            binary = binary.replaceFirst("\\d", "1");
        } else {
            binary = binary.replaceFirst("\\d", "0");
        }
        binary = String.join("", binary.substring(0, 8), binary.substring(9), "0");

        BitSource source = BitUtils.wrapSource(readBinaryString(binary));
        return IEEE754.decode(ieee7bitExp, source);
    }

    public static String toIEEE(IEEE754 n) {
        byte[] out = new byte[4];
        BitSink sink = BitUtils.wrapSink(out);
        n.toBits(ieee, sink);
        StringBuilder sb = new StringBuilder();
        for (byte b : out) {
            String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            sb.append(s1);
        }
        return sb.toString();
    }

    public static String toInternal(IEEE754 n) {
        byte[] out2 = new byte[4];
        BitSink sink2 = BitUtils.wrapSink(out2);
        n.toBits(ieee7bitExp, sink2);
        StringBuilder sb2 = new StringBuilder();
        for (byte b : out2) {
            String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            sb2.append(s1);
        }
        String interResult = sb2.toString();
        if (!n.equals(IEEE754.POSITIVE_ZERO)) {
            if (interResult.startsWith("0")) {
                interResult = interResult.replaceFirst("\\d", "1");
            } else {
                interResult = interResult.replaceFirst("\\d", "0");
            }
        }
        interResult = String.join("", interResult.substring(0, 8), "0", interResult.substring(8)).substring(0, 32);
        return interResult;
    }

    public static String divideInFours(String out){
        String[] chunks = out.split("(?<=\\G.{4})");
        return String.join(" ", chunks).trim();
    }
}
