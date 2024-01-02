package org.firstinspires.ftc.teamcode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.hardware.lynx.LynxModule;

import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Utilities.Color;

@TeleOp(name="Tank Driving")

public class TankDrive extends OpMode {

    private Drivetrain AsterionMotors;
    private ElapsedTime RunTime = new ElapsedTime();
    private Blinker LED;
    private LynxModule ControlHub;
    private VoltageSensor Battery;

    double ForwardSpeedReduction = 0.6;
    double TwistSpeedReduction = 0.6;

    @Override
    public void init() {
        AsterionMotors = new Drivetrain(hardwareMap);
        AsterionMotors.setOpposite(1);
        AsterionMotors.setBrakingMode("BRAKING");

        ControlHub = hardwareMap.get(LynxModule.class, Constants.ControlHubID);
        Battery = hardwareMap.get(VoltageSensor.class, Constants.ControlHubID);
        LED = hardwareMap.get(Blinker.class, Constants.ControlHubID);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
    }


    @Override
    public void start() {
        RunTime.reset();
    }

    @Override
    public void loop() {
        if(gamepad1.right_bumper && ForwardSpeedReduction < 1) {
            ForwardSpeedReduction = ForwardSpeedReduction + 0.05;
            TwistSpeedReduction = TwistSpeedReduction + 0.05;
        } else if (gamepad1.left_bumper && ForwardSpeedReduction > 0) {
            ForwardSpeedReduction = ForwardSpeedReduction - 0.05;
            TwistSpeedReduction = TwistSpeedReduction - 0.05;
        }

        double leftDrive = gamepad1.left_stick_y * ForwardSpeedReduction;
        double rightDrive = gamepad1.right_stick_y * TwistSpeedReduction;

        if(gamepad1.dpad_up) {
            AsterionMotors.tankDrive(Constants.DrivingAdjustment, Constants.DrivingAdjustment, hardwareMap);
        } else if(gamepad1.dpad_down) {
            AsterionMotors.tankDrive(-Constants.DrivingAdjustment, Constants.DrivingAdjustment, hardwareMap);
        } else if(gamepad1.dpad_right) {
            AsterionMotors.tankDrive(Constants.DrivingAdjustment, -Constants.DrivingAdjustment, hardwareMap);
        } else if(gamepad1.dpad_left) {
            AsterionMotors.tankDrive(-Constants.DrivingAdjustment, -Constants.DrivingAdjustment, hardwareMap);
        }

        AsterionMotors.povDrive(leftDrive, rightDrive, hardwareMap);

        telemetry.addData("Status", "Run Time: " + RunTime.toString());
        telemetry.addData("Driving Speed", ForwardSpeedReduction);
    }

    @Override
    public void stop() {

    }
}
