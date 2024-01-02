package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Blinker;

import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Utilities.Color;
import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@TeleOp(name="Mecanum Driving")

public class MecanumDrive extends OpMode {

    private Drivetrain AsterionMotors;
    private Lift ActuatorMotors;
    private ElapsedTime RunTime = new ElapsedTime();
    private Blinker LED;
    private LynxModule ControlHub;
    private LynxModule ExpansionHub;
    private VoltageSensor Battery;
    private Servo DroneLauncher;

    double ForwardSpeedReduction = 0.6;
    double StrafeSpeedReduction = 0.6;
    double TwistSpeedReduction = 0.6;

    boolean leftBumperButtonPreviousState = false;
    boolean rightBumperButtonPreviousState = false;
    boolean leftJoystickButtonPreviousState = false;
    boolean aButtonPreviousState = false;
    boolean bButtonPreviousState = false;

    ArrayList<Blinker.Step> steps;

    @Override
    public void init() {
        AsterionMotors = new Drivetrain(hardwareMap);
        AsterionMotors.setOpposite(1);
        AsterionMotors.setBrakingMode("BRAKING");

        ActuatorMotors = new Lift(hardwareMap);
        ActuatorMotors.setBrakingMode("BRAKING");
        // ActuatorMotors.setOpposite(1)

        ControlHub = Color.getLynxModule(hardwareMap, Constants.ControlHubID);
        ExpansionHub = Color.getLynxModule(hardwareMap, Constants.ExpansionHubID);
        Battery = hardwareMap.get(VoltageSensor.class, Constants.ControlHubID);
        LED = hardwareMap.get(Blinker.class, Constants.ControlHubID);

        DroneLauncher = hardwareMap.get(Servo.class, Constants.DroneLauncherID);

        if(Battery.getVoltage() < 11.7) {
            telemetry.addData("WARNING", "Battery Voltage is low!");
            ControlHub.setPattern(Color.batteryLowPattern());
            ExpansionHub.setPattern(Color.batteryLowPattern());
        } else {
            telemetry.addData("Status", "Initialized");
            telemetry.addData("Battery Voltage", Battery.getVoltage());

            ControlHub.setPattern(Color.readyPattern());
            ExpansionHub.setPattern(Color.readyPattern());
        }
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        RunTime.reset();

        ControlHub.setPattern(Color.teleopPattern());
        ExpansionHub.setPattern(Color.teleopPattern());
    }

    @Override
    public void loop() {
        if(gamepad1.left_bumper && !leftBumperButtonPreviousState) {
            ForwardSpeedReduction = ForwardSpeedReduction - 0.05;
            StrafeSpeedReduction = StrafeSpeedReduction - 0.05;
            TwistSpeedReduction = TwistSpeedReduction - 0.05;
        } else if(gamepad1.right_bumper && !rightBumperButtonPreviousState) {
            ForwardSpeedReduction = ForwardSpeedReduction + 0.05;
            StrafeSpeedReduction = StrafeSpeedReduction + 0.05;
            TwistSpeedReduction = TwistSpeedReduction + 0.05;
        }

        rightBumperButtonPreviousState = gamepad1.right_bumper;
        leftBumperButtonPreviousState = gamepad1.left_bumper;
        leftJoystickButtonPreviousState = gamepad1.left_stick_button;

        double drive = gamepad1.left_stick_y * ForwardSpeedReduction;
        double strafe = gamepad1.left_stick_x * StrafeSpeedReduction;
        double twist = gamepad1.right_stick_x * TwistSpeedReduction;
        double lift = gamepad2.right_stick_y;
        double angle = gamepad2.left_stick_y;

        if(gamepad1.dpad_up) {
            AsterionMotors.mecanumDrive(-Constants.DrivingAdjustment, 0,0, false);
        } else if(gamepad1.dpad_down) {
            AsterionMotors.mecanumDrive(Constants.DrivingAdjustment, 0, 0, false);
        } else if(gamepad1.dpad_right) {
            AsterionMotors.mecanumDrive(0, Constants.DrivingAdjustment, 0, false);
        } else if(gamepad1.dpad_left) {
            AsterionMotors.mecanumDrive(0, -Constants.DrivingAdjustment, 0, false);
        }

        if(gamepad2.a && !aButtonPreviousState) {
            DroneLauncher.setPosition(20);
        }

        if(gamepad2.b && !bButtonPreviousState) {
            DroneLauncher.setPosition(0);
        }

        aButtonPreviousState = gamepad2.a;
        bButtonPreviousState = gamepad2.b;

        ActuatorMotors.moveAngle(angle);
        ActuatorMotors.moveLift(lift);
        AsterionMotors.mecanumDrive(drive, strafe, twist, true);

    }

    @Override
    public void stop() {

    }
}
