package org.firstinspires.ftc.teamcode.Autonomous;

import android.graphics.Color;

import java.math.BigInteger;
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

// import org.firstinspires.ftc.teamcode.Utilities.Color;
import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@Autonomous(name="RP1-Parking")

public class RedParking extends OpMode {

    private Drivetrain AsterionMotors;
    private ElapsedTime RunTime = new ElapsedTime();
    private Blinker LED;
    private LynxModule ControlHub;
    private VoltageSensor Battery;
    private Collection<Blinker.Step> steps;

    @Override
    public void init() {
        AsterionMotors = new Drivetrain(hardwareMap);
        AsterionMotors.setOpposite(1);
        AsterionMotors.setBrakingMode("BRAKING");

        ControlHub = hardwareMap.get(LynxModule.class, Constants.ControlHubID);
        Battery = hardwareMap.get(VoltageSensor.class, Constants.ControlHubID);
        LED = hardwareMap.get(Blinker.class, Constants.ControlHubID);
        telemetry.addData("Status", "Initialized");

        steps = new ArrayList<Blinker.Step>();
        steps.add(new Blinker.Step(16740630, 400, TimeUnit.MILLISECONDS)); //red
        steps.add(new Blinker.Step(android.graphics.Color.BLACK, 400, TimeUnit.MILLISECONDS)); //blue
        ControlHub.setPattern(steps);
    }

    @Override
    public void init_loop() {
        double batteryVoltage = Battery.getVoltage();

        if (batteryVoltage <= 11) {
            LED.setConstant(Color.YELLOW);
            telemetry.addData("WARNING", "Battery Voltage is low");
        }
    }


    @Override
    public void start() {
        RunTime.reset();
    }

    @Override
    public void loop() {
        double drive = gamepad1.left_stick_y * Constants.ForwardSpeedReduction;
        double strafe = gamepad1.left_stick_x * Constants.StrafeSpeedReduction;
        double twist = gamepad1.right_stick_x * Constants.TwistSpeedReduction;

        if (RunTime.seconds() < 1) {
            AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
        } else if (RunTime.seconds() > 1.5 && RunTime.seconds() < 5) {
            AsterionMotors.mecanumDrive(0, Constants.AutoSpeed, 0);
        } else if (RunTime.seconds() > 5) {
            AsterionMotors.mecanumDrive(0, 0, 0);
            ControlHub.setPattern(steps);
        }
    }

    @Override
    public void stop() {

    }
}
