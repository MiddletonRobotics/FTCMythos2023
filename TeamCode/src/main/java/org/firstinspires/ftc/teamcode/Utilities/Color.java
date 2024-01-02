package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Color {

    private static Blinker blinker;
    private static LynxModule lynxModule;
    private static HardwareMap hardwareMap;
    private static ArrayList<Blinker.Step> steps;

    public static LynxModule getLynxModule(HardwareMap aHardwareMap, String id) {
        hardwareMap = aHardwareMap;
        lynxModule = hardwareMap.get(LynxModule.class, id);

        return lynxModule;
    }

    public static Blinker getBlinker(HardwareMap aHardwareMap, String id) {
        hardwareMap = aHardwareMap;
        blinker = hardwareMap.get(Blinker.class, id);

        return blinker;
    }

    public static int hexToDecimal(String hexadecimal) {
        int ColorInt = Integer.parseInt(hexadecimal,16);
        return ColorInt;
    }

    public static String hexToBinary(String hexadecimal) {
        return new BigInteger(hexadecimal, 16).toString(2);
    }

    public static ArrayList<Blinker.Step> batteryLowPattern() {
        String YellowHexadecimal = Constants.YELLOW;
        String BlackHexadecimal = Constants.BLACK;

        int yellow = hexToDecimal(YellowHexadecimal);
        int black = hexToDecimal(BlackHexadecimal);

        steps = new ArrayList<Blinker.Step>();
        steps.add(new Blinker.Step(yellow, 150, TimeUnit.MILLISECONDS));
        steps.add(new Blinker.Step(black, 150, TimeUnit.MILLISECONDS));

        return steps;
    }

    public static ArrayList<Blinker.Step> readyPattern() {
        String GreenHexadecimal = Constants.GREEN;
        String BlackHexadecimal = Constants.BLACK;

        int green = hexToDecimal(GreenHexadecimal);
        int black = hexToDecimal(BlackHexadecimal);

        steps = new ArrayList<Blinker.Step>();
        steps.add(new Blinker.Step(green, 150, TimeUnit.MILLISECONDS));
        steps.add(new Blinker.Step(black, 150, TimeUnit.MILLISECONDS));

        return steps;
    }

    public static ArrayList<Blinker.Step> teleopPattern() {
        String OrangeHexadecimal = Constants.ORANGE;
        String BlackHexadecimal = Constants.BLACK;

        int orange = hexToDecimal(OrangeHexadecimal);
        int black = hexToDecimal(BlackHexadecimal);

        steps = new ArrayList<Blinker.Step>();
        steps.add(new Blinker.Step(orange, 150, TimeUnit.MILLISECONDS));
        steps.add(new Blinker.Step(black, 150, TimeUnit.MILLISECONDS));

        return steps;
    }
}
