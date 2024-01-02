package org.firstinspires.ftc.teamcode.Autonomous;

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

import org.firstinspires.ftc.teamcode.Utilities.Color;
import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@Autonomous(name="BP2-Pixel-Placement")

public class Blue2PixelPlace extends OpMode {

    private Drivetrain AsterionMotors;
    private ElapsedTime RunTime = new ElapsedTime();
    private Blinker LED;
    private LynxModule ControlHub;
    private LynxModule ExpansionHub;
    private VoltageSensor Battery;
    private Collection<Blinker.Step> steps;

    @Override
    public void init() {
        AsterionMotors = new Drivetrain(hardwareMap);
        AsterionMotors.setOpposite(1);
        AsterionMotors.setBrakingMode("BRAKING");

        ControlHub = Color.getLynxModule(hardwareMap, Constants.ControlHubID);
        ExpansionHub = Color.getLynxModule(hardwareMap, Constants.ExpansionHubID);
        Battery = hardwareMap.get(VoltageSensor.class, Constants.ControlHubID);
        LED = hardwareMap.get(Blinker.class, Constants.ControlHubID);
        telemetry.addData("Status", "Initialized");

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
        if (RunTime.seconds() < 2.9) {
            AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
        } else if (RunTime.seconds() > 2.9 && RunTime.seconds() < 3.1) {
            AsterionMotors.mecanumDrive(0, 0, 0);
        }  else if (RunTime.seconds() > 3.1 && RunTime.seconds() < 4.8) {
            AsterionMotors.mecanumDrive(Constants.AutoSpeed, 0, 0);
        } else if (RunTime.seconds() > 4.8) {
            AsterionMotors.mecanumDrive(0, 0, 0);
        }
    }

    @Override
    public void stop() {

    }
}
