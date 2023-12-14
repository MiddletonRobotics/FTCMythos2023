package org.firstinspires.ftc.teamcode.Utilities.Constants;

import com.qualcomm.hardware.motors.NeveRest20Gearmotor;

public interface Constants {

    // ANDYMARK NeveRest Orbital 20 Gearmotors

    double ANO20_TICKS_PER_REVOLTUION = 537.6;
    int ANO20_RPM = 340;

    // ANDYMARK Orbital 3.7 Gearmotors

    double AO3G_TICKS_PER_REVOLTUION = 103.6;
    int AO3G_RPM = 1780;

    // All IDs of electronics connected to the Control and Expansion Hub
    String ControlHubID = "Control Hub";
    String ExpansionHubID = "Expansion Hub";

    String FrontLeftID = "FrontLeft";
    String FrontRightID = "FrontRight";
    String BackLeftID = "BackLeft";
    String BackRightID = "BackRight";
    String AngleMotorID = "AngleMotor";
    String LiftMotorID = "LiftMotor";

    // Driving Speeds and Encoder Positions throughout the robot

    double ForwardSpeedReduction = 0.45;
    double StrafeSpeedReduction = 0.45;
    double TwistSpeedReduction = 0.45;
    double DrivingAdjustment = 0.4;
    double LiftSpeed = 0.9;
    double AngleSpeed = 0.4;
    double AutoSpeed = 0.2;

    int groundPosition = 0;
    int liftRetractedPosition = 0;
    double liftReadyPosition = ANO20_TICKS_PER_REVOLTUION / 0.4;
    double liftExtendedPosition = ANO20_TICKS_PER_REVOLTUION * 11;

    // Defining Colors for use to push patterns or colors to Control / Expansion Hub

    String RED = "ff0000";
    String ORANGE = "ff7116";
    String YELLOW = "fff22e";
    String GREEN = "00fe00";
    String CYAN = "00b58d";
    String BLUE = "0338ef";
    String PINK = "f7aac3";
    String LAVENDER = "d0a3f8";
    String PURPLE = "a922f4";
    String WHITE = "fafefe";
    String BLACK = "fafefe";
    String TRANSPARENT = "fafefe";
}
