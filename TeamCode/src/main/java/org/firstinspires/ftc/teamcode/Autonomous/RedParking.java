package org.firstinspires.ftc.teamcode.Autonomous;

// Temporary Color Library to see if mine works

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Blinker;

import org.firstinspires.ftc.teamcode.Utilities.Color;
import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@Autonomous(name="RP1-Parking")

public class RedParking extends OpMode {

    private Drivetrain AsterionMotors;
    private ElapsedTime RunTime = new ElapsedTime();
    private Blinker LED;
    private LynxModule ControlHub;
    private VoltageSensor Battery;

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
            int yellow = org.firstinspires.ftc.teamcode.Utilities.Color.hexToDecimal(Constants.YELLOW);
            LED.setConstant(yellow);
            telemetry.addData("WARNING", "Battery Voltage is low");
        } else {
            int orange = org.firstinspires.ftc.teamcode.Utilities.Color.hexToDecimal(Constants.ORANGE);
            int transparent = org.firstinspires.ftc.teamcode.Utilities.Color.hexToDecimal(Constants.TRANSPARENT);

            steps = new ArrayList<Blinker.Step>();
            steps.add(new Blinker.Step(orange, 3000, TimeUnit.MILLISECONDS));
            steps.add(new Blinker.Step(transparent, 3000, TimeUnit.MILLISECONDS));

            ControlHub.setPattern(steps);
        }
    }


    @Override
    public void start() {
        RunTime.reset();

        int orange = Color.hexToDecimal(Constants.ORANGE);
        LED.setConstant(orange);
    }

    @Override
    public void loop() {
        double drive = gamepad1.left_stick_y * Constants.ForwardSpeedReduction;
        double strafe = gamepad1.left_stick_x * Constants.StrafeSpeedReduction;
        double twist = gamepad1.right_stick_x * Constants.TwistSpeedReduction;

        if (RunTime.seconds() < 1) {
            AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
        } else if (RunTime.seconds() > 1.5 && RunTime.seconds() < 4) {
            AsterionMotors.mecanumDrive(0, Constants.AutoSpeed, 0);
        }
    }

    @Override
    public void stop() {

    }
}
