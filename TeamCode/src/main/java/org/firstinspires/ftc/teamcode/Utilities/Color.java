package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;

import java.math.BigInteger;

public class Color {

    private static  Blinker blinker;
    private static LynxModule lynxModule;
    private HardwareMap hardwareMap;

    public LynxModule getLynxModule(HardwareMap aHardwareMap) {
        hardwareMap = aHardwareMap;
        lynxModule = hardwareMap.get(LynxModule.class, Constants.ControlHubID);

        return lynxModule;
    }

    public Blinker getBlinker(HardwareMap aHardwareMap) {
        hardwareMap = aHardwareMap;
        blinker = hardwareMap.get(Blinker.class, Constants.ControlHubID);

        return blinker;
    }

    public static BigInteger hexToDecimal(String hexadecimal) {
        BigInteger ColorInt = new BigInteger(hexadecimal,16);
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
