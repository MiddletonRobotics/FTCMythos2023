package org.firstinspires.ftc.teamcode.Utilities;

import java.math.BigInteger;

public class Color {
    public static int hexToDecimal(String hexadecimal) {
        int ColorInt = Integer.parseInt(hexadecimal,16);
        return ColorInt;
    }

    public static String hexToBinary(String hexadecimal) {
        return new BigInteger(hexadecimal, 16).toString(2);
    }

    public static int[] hexToRGB(String hexadecimal) {
        int hex = Integer.parseInt(hexadecimal);

        int r = (hex & 0xFF0000) >> 16;
        int g = (hex & 0xFF00) >> 8;
        int b = (hex & 0xFF);

        int[] rgb = {r, g, b};

        return rgb;
    }
}
