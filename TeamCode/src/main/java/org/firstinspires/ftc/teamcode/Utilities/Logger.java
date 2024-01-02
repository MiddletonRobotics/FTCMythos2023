package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

public class Logger {
    public Telemetry myTelem;
    public Drivetrain drivetrain;
    public HardwareMap aHardwareMap;

    public void startupTelemtry(Telemetry telemetry, VoltageSensor battery) {
        myTelem = telemetry;

        myTelem.addData("Robot Status", "Initialized");
        myTelem.addData("Battery Voltage", battery.getVoltage());
    }

    public void MotorVelocity(Telemetry telemetry, VoltageSensor battery, HardwareMap hardwareMap) {
        myTelem = telemetry;
        aHardwareMap = hardwareMap;

        drivetrain = new Drivetrain(aHardwareMap);

        myTelem.clearAll();
        myTelem.addData("Robot Status", "Active");
        myTelem.addData("Battery Voltage", battery.getVoltage());
        myTelem.addData("Front-Left Position", "");
        myTelem.addData("Front-Right Position", "");
        myTelem.addData("Rear-Left Position", "");
        myTelem.addData("Rear-Right Position", "");
    }
}
