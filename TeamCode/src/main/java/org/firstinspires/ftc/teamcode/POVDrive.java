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

@TeleOp(name="POV Driving")

public class POVDrive extends OpMode {

    private Drivetrain AsterionMotors;
    private ElapsedTime RunTime = new ElapsedTime();
    private Blinker LED;
    private LynxModule ControlHub;
    private VoltageSensor Battery;

    double ForwardSpeedReduction = 0.6;
    double TwistSpeedReduction = 0.6;

    ArrayList<Blinker.Step> steps;

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
        double batteryVoltage = Battery.getVoltage();

        if (batteryVoltage <= 11) {
            BigInteger yellowBigInteger = Color.hexToDecimal(Constants.YELLOW);
            int yellow = yellowBigInteger.intValue();
            LED.setConstant(yellow);
            telemetry.addData("WARNING", "Battery Voltage is low");
        } else {
            BigInteger orangeBigInteger = Color.hexToDecimal(Constants.ORANGE);
            BigInteger transparentBigInteger = Color.hexToDecimal(Constants.TRANSPARENT);

            int orange = orangeBigInteger.intValue();
            int transparent = transparentBigInteger.intValue();

            steps = new ArrayList<Blinker.Step>();
            steps.add(new Blinker.Step(orange, 3000, TimeUnit.MILLISECONDS));
            steps.add(new Blinker.Step(transparent, 3000, TimeUnit.MILLISECONDS));

            ControlHub.pushPattern(steps);
        }
    }


    @Override
    public void start() {
        RunTime.reset();

        BigInteger orangeBigInteger = Color.hexToDecimal(Constants.ORANGE);
        int orange = orangeBigInteger.intValue();
        LED.setConstant(orange);
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

        double drive = gamepad1.left_stick_y * ForwardSpeedReduction;
        double twist = gamepad1.right_stick_x * TwistSpeedReduction;

        if(gamepad1.dpad_up) {
            AsterionMotors.povDrive(Constants.DrivingAdjustment, 0);
        } else if(gamepad1.dpad_down) {
            AsterionMotors.povDrive(-Constants.DrivingAdjustment, 0);
        } else if(gamepad1.dpad_right) {
            AsterionMotors.povDrive(0, Constants.DrivingAdjustment);
        } else if(gamepad1.dpad_left) {
            AsterionMotors.povDrive(0, -Constants.DrivingAdjustment);
        }

        AsterionMotors.povDrive(drive, twist);

        telemetry.addData("Status", "Run Time: " + RunTime.toString());
        telemetry.addData("Driving Speed", ForwardSpeedReduction);
    }

    @Override
    public void stop() {

    }
}
