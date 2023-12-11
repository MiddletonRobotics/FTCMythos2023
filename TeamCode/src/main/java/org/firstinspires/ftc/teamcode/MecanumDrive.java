package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Blinker;

// import org.firstinspires.ftc.teamcode.Utilities.Color;
import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@TeleOp(name="Mecanum Driving")

public class MecanumDrive extends OpMode {

    private Drivetrain AsterionMotors;
    private ElapsedTime RunTime = new ElapsedTime();
    private Blinker LED;
    private LynxModule ControlHub;
    private VoltageSensor Battery;

    private DcMotor AngleMotor;
    private DcMotor LiftMotor;

    double ForwardSpeedReduction = 0.6;
    double StrafeSpeedReduction = 0.6;
    double TwistSpeedReduction = 0.6;
    double newTarget;

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

        ControlHub = hardwareMap.get(LynxModule.class, Constants.ControlHubID);
        Battery = hardwareMap.get(VoltageSensor.class, Constants.ControlHubID);
        LED = hardwareMap.get(Blinker.class, Constants.ControlHubID);
        AngleMotor = hardwareMap.get(DcMotor.class, Constants.AngleMotorID)
        LiftMotor = hardwareMap.get(DcMotor.class, Constants.LiftMotorID)

        AngleMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");

        steps = new ArrayList<Blinker.Step>();
        steps.add(new Blinker.Step(16740630, 170, TimeUnit.MILLISECONDS)); //red
        steps.add(new Blinker.Step(android.graphics.Color.BLACK, 170, TimeUnit.MILLISECONDS)); //blue
        ControlHub.setPattern(steps);
    }

    @Override
    public void init_loop() {
        double batteryVoltage = Battery.getVoltage();

        if (batteryVoltage <= 11) {
            LED.setConstant(android.graphics.Color.YELLOW);
            telemetry.addData("WARNING", "Battery Voltage is low");
        }
    }


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

        if(gamepad1.dpad_up) {
            AsterionMotors.mecanumDrive(-Constants.DrivingAdjustment, 0,0);
        } else if(gamepad1.dpad_down) {
            AsterionMotors.mecanumDrive(Constants.DrivingAdjustment, 0, 0);
        } else if(gamepad1.dpad_right) {
            AsterionMotors.mecanumDrive(0, Constants.DrivingAdjustment, 0);
        } else if(gamepad1.dpad_left) {
            AsterionMotors.mecanumDrive(0, -Constants.DrivingAdjustment, 0);
        }

        if(gamepad2.a && !aButtonPreviousState) {
            
        } else if(gamepad2.b && !bButtonPreviousState) {
            
        }

        AsterionMotors.mecanumDrive(drive, strafe, twist);

        telemetry.addData("Status", "Run Time: " + RunTime.toString());
        telemetry.addData("Speed Percentage", ForwardSpeedReduction * 100 + "%");
        telemetry.addData("Driving Value", gamepad1.left_stick_y);
        telemetry.addData("Strafing Value", gamepad1.left_stick_x);
        telemetry.addData("Twist Value", gamepad1.right_stick_x);
        telemetry.addData("Battery Voltage", Battery.getVoltage());
    }

    @Override
    public void stop() {

    }

    public void liftEncoder(int turnage) {
        newTarget = Constants.ticks / turnage;
        LiftMotor.setTargetPosition((int) newTarget)
        LiftMotor.setPower(Constants.DrivingAdjustment);
        LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION)
    }

    public void angleEncoder(int turnage) {
        newTarget = Constants.ticks / turnage;
        AngleMotor.setTargetPosition((int) newTarget)
        AngleMotor.setPower(Constants.DrivingAdjustment);
        AngleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION)
    }
}
