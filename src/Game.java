import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.util.*;

import static java.lang.System.*;

public class Game {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        if (!isValid(args)) return;

        int index = new SecureRandom().nextInt(args.length);
        String key = getKey();
        String HMAC = getHMAC(key, args[index]);

        printMenu(args, HMAC);

        int choice = Integer.parseInt(new Scanner(in).nextLine());
        if (choice == 0) return;

        String result = getResult(args, index, choice);

        printResult(args, index, choice, result, key);
    }

    private static void printResult(String[] args, int index, int choice, String result, String key) {
        out.printf("""
                        Enter your move: %s
                        Your move: %s
                        Computer move: %s
                        %s
                        HMAC key: %s
                        """
                , choice, args[choice - 1], args[index], result, key);
    }

    private static void printMenu(String[] args, String HMAC) {
        out.printf("""
                HMAC:
                %s
                Available moves:
                """, HMAC);
        for (int i = 0; i < args.length; i++) out.println(i + 1 + " - " + args[i]);
        out.println("0 - exit");
    }

    private static String getResult(String[] args, int index, int choice) {
        String result;
        int count = 0, temp = choice - 1;
        for (int i = 0; i < args.length; i++) {
            if (temp == index) break;
            if (temp++ == args.length - 1) temp = 0;
            count++;
        }
        if (count == 0) result = "It's a draw!";
        else if (count <= args.length / 2) result = "You've lost!";
        else result = "You win!";
        return result;
    }

    private static String getKey() throws NoSuchAlgorithmException {
        var keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encodedKey = secretKey.getEncoded();
        return new BigInteger(1, encodedKey).toString(16);
    }

    private static String getHMAC(String key, String message) throws NoSuchAlgorithmException, InvalidKeyException {
        var mac = Mac.getInstance("HmacSHA256");
        var secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] finalMac = mac.doFinal(message.getBytes());
        return new BigInteger(1, finalMac).toString(16);
    }

    private static boolean isValid(String[] args) {
        var builder = new StringBuilder();
        int countMistakes = 0;
        if (!Arrays.stream(args).sequential().allMatch(new HashSet<>()::add)) {
            builder.append("Duplicate arguments. (1)\n");
            countMistakes++;
        }
        if (args.length % 2 == 0) {
            builder.append("Even number of arguments passed. (2)\n");
            countMistakes++;
        }
        if (args.length < 3) {
            builder.append("The number of arguments is less than three. (3)\n");
            countMistakes++;
        }
        if (countMistakes > 0) {
            out.printf("""
                    Mistakes:
                    %s
                    Requirements:
                    1 Unique arguments.
                    2 Odd number of arguments.
                    3 The number of arguments is greater than or equal to three.""", builder);
            return false;
        }
        return true;
    }
}

