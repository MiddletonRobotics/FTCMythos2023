package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Autonomous.VisionColorDetector.*;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamServer;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Utilities.Color;
import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;
import org.firstinspires.ftc.vision.VisionPortal;

import org.opencv.core.Rect;

import java.util.Collection;

@Autonomous(name="RP1-Middle")
@Disabled

public class RPMiddle extends OpMode {
    VisionColorDetector detector;
    VisionPortal visionPortal;

    private Drivetrain AsterionMotors;
    private ElapsedTime RunTime = new ElapsedTime();
    private Blinker LED;
    private LynxModule ControlHub;
    private LynxModule ExpansionHub;
    private VoltageSensor Battery;
    private Collection<Blinker.Step> steps;

    private DcMotor AngleMotor;
    private DcMotor LiftMotor;

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

        AngleMotor = hardwareMap.get(DcMotor.class, Constants.AngleMotorID);
        LiftMotor = hardwareMap.get(DcMotor.class, Constants.LiftMotorID);

        AngleMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        RunTime.reset();

        ControlHub.setPattern(Color.teleopPattern());
        ExpansionHub.setPattern(Color.teleopPattern());
    }

    @Override
    public void loop() {
        if (RunTime.seconds() < 3.15) {
            AngleMotor.setPower(-0.25);
            AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
        } else if (RunTime.seconds() > 3.15 && RunTime.seconds() < 4) {
            AngleMotor.setPower(-0.3);
            AsterionMotors.mecanumDrive(0, 0, 0);
        } else if (RunTime.seconds() > 4 && RunTime.seconds() < 5) {
            AngleMotor.setPower(-0.3);
            AsterionMotors.mecanumDrive(Constants.AutoSpeed, 0, 0);
        } else if (RunTime.seconds() > 5 && RunTime.seconds() < 5.2) {
            AngleMotor.setPower(-0.3);
            AsterionMotors.mecanumDrive(0, 0, 0);
        } else if (RunTime.seconds() > 5.2 && RunTime.seconds() < 6.15) {
            AngleMotor.setPower(-0.3);
            AsterionMotors.mecanumDrive(0, 0, -0.3);
        } else if (RunTime.seconds() > 6.15 && RunTime.seconds() < 9.7) {
            AngleMotor.setPower(-0.3);
            AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
        } else if (RunTime.seconds() > 9.7 && RunTime.seconds() < 9.9) {
            AngleMotor.setPower(-0.3);
            AsterionMotors.mecanumDrive(0, 0, 0);
        } else if (RunTime.seconds() > 9.9 && RunTime.seconds() < 12) {
            AngleMotor.setPower(-0.3);
            LiftMotor.setPower(-0.6);
        } else if (RunTime.seconds() > 12 && RunTime.seconds() < 12.5) {
            LiftMotor.setPower(0);
            AngleMotor.setPower(0.2);
        }
    }
}