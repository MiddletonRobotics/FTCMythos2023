package org.firstinspires.ftc.teamcode.Utilities.Constants;

import com.qualcomm.hardware.motors.NeveRest20Gearmotor;

public class Constants {

    // ANDYMARK NeveRest Orbital 20 Gearmotors

    public static double ANO20_TICKS_PER_REVOLTUION = 537.6;
    public static int ANO20_RPM = 340;

    // ANDYMARK Orbital 3.7 Gearmotors

    public static double AO3G_TICKS_PER_REVOLTUION = 103.6;
    public static int AO3G_RPM = 1780;

    // All IDs of electronics connected to the Control and Expansion Hub
    public static final String ControlHubID = "Control Hub";
    public static final String ExpansionHubID = "Expansion Hub";

    public static final String FrontLeftID = "FrontLeft";
    public static final String FrontRightID = "FrontRight";
    public static final String BackLeftID = "BackLeft";
    public static final String BackRightID = "BackRight";
    public static final String AngleMotorID = "AngleMotor";
    public static final String LiftMotorID = "LiftMotor";
    public static final String DroneLauncherID = "DroneLauncher";

    // Driving Speeds and Encoder Positions throughout the robot

    public static double ForwardSpeedReduction = 0.45;
    public static double StrafeSpeedReduction = 0.45;
    public static double TwistSpeedReduction = 0.45;
    public static double DrivingAdjustment = 0.4;
    public static double LiftSpeed = 0.6;
    public static double AngleSpeed = 0.4;
    public static double AutoSpeed = 0.2;

    public static final int liftRetractedPosition = 0;
    public static final int liftIdlePosition = 0;
    public static final double liftReadyPosition = ANO20_TICKS_PER_REVOLTUION / 0.4;
    public static final double liftExtendedPosition = ANO20_TICKS_PER_REVOLTUION * 11;

    // Defining Colors for use to push patterns or colors to Control / Expansion Hub

    public static final String RED = "ff0000";
    public static final String ORANGE = "ff7116";
    public static final String YELLOW = "fff22e";
    public static final String GREEN = "00fe00";
    public static final String CYAN = "00b58d";
    public static final String BLUE = "0338ef";
    public static final String PINK = "f7aac3";
    public static final String LAVENDER = "d0a3f8";
    public static final String PURPLE = "a922f4";
    public static final String WHITE = "fafefe";
    public static final String BLACK = "fafefe";
    public static final String TRANSPARENT = "fafefe";

    public enum MecanumDriveType {
        SIMPLE,
        ADVANCED
    }
}
