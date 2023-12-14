package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Blinker;

// import org.firstinspires.ftc.teamcode.Utilities.Color;
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

    private DcMotor AngleMotor;
    private DcMotor LiftMotor;

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

        ControlHub = hardwareMap.get(LynxModule.class, Constants.ControlHubID);
        ExpansionHub = hardwareMap.get(LynxModule.class, Constants.ExpansionHubID);
        Battery = hardwareMap.get(VoltageSensor.class, Constants.ControlHubID);
        LED = hardwareMap.get(Blinker.class, Constants.ControlHubID);

        ControlHub.setPattern(Color.readyPattern());
        ExpansionHub.setPattern(Color.readyPattern());

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {}


    @Override
    public void start() {
        RunTime.reset();
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
            AsterionMotors.mecanumDrive(-Constants.DrivingAdjustment, 0,0, hardwareMap);
        } else if(gamepad1.dpad_down) {
            AsterionMotors.mecanumDrive(Constants.DrivingAdjustment, 0, 0, hardwareMap);
        } else if(gamepad1.dpad_right) {
            AsterionMotors.mecanumDrive(0, Constants.DrivingAdjustment, 0, hardwareMap);
        } else if(gamepad1.dpad_left) {
            AsterionMotors.mecanumDrive(0, -Constants.DrivingAdjustment, 0, hardwareMap);
        }

        aButtonPreviousState = gamepad2.a;
        bButtonPreviousState = gamepad2.b;

        ActuatorMotors.moveAngle(angle);
        ActuatorMotors.moveLift(lift);
        telemetry.addData("Battery Voltage", Battery.getVoltage());
    }

    @Override
    public void stop() {

    }
}
