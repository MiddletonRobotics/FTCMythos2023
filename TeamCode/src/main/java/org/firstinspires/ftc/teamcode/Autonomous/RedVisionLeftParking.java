package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Autonomous.VisionColorDetector.*;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Blinker;
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

@Autonomous(name="RP1-VISION-ParkRight")

public class RedVisionLeftParking extends OpMode {
    VisionColorDetector detector;
    VisionPortal visionPortal;

    private Drivetrain AsterionMotors;
    private ElapsedTime RunTime = new ElapsedTime();
    private Blinker LED;
    private LynxModule ControlHub;
    private LynxModule ExpansionHub;
    private VoltageSensor Battery;
    private Collection<Blinker.Step> steps;

    private String h;

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

        // Initialize and configure detector and camera
        Rect leftZone = centerRect(120, 350, 150, 230);
        Rect rightZone = centerRect(350, 350, 230, 150);
        detector = new VisionColorDetector(telemetry, TargetColor.RED, ViewMode.RAW, leftZone, rightZone);
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), detector);

        detector.targetColor = TargetColor.RED;
        CameraStreamServer.getInstance().setSource(detector);
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        RunTime.reset();

        ControlHub.setPattern(Color.teleopPattern());
        ExpansionHub.setPattern(Color.teleopPattern());

        if (detector.isConfident()) {
            if (detector.getDetection() == Detection.RIGHT) {
                h = "RIGHT";
            } else if (detector.getDetection() == Detection.LEFT) {
                h = "LEFT";
            } else if (detector.getDetection() == Detection.NONE) {
                h = "NONE";
            }
        }
    }

    @Override
    public void loop() {
        handle_input();
    }

    public void handle_input() {
        if (detector.isConfident()) {
            if (h == "RIGHT") {
                if (RunTime.seconds() < 2.95) {
                    AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 2.95 && RunTime.seconds() < 3.2) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 3.2 && RunTime.seconds() < 4.2) {
                    AsterionMotors.mecanumDrive(Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 4.2 && RunTime.seconds() < 4.4) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                }
            } else if (h == "LEFT") {
                if (RunTime.seconds() < 2.65) {
                    AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 2.65 && RunTime.seconds() < 4) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 4 && RunTime.seconds() < 4.9) {
                    AsterionMotors.mecanumDrive(0, 0, 0.3);
                } else if (RunTime.seconds() > 4.9 && RunTime.seconds() < 5.4) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 5.4 && RunTime.seconds() < 5.82) {
                    AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 5.82) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                }
            } else {
                if (RunTime.seconds() < 2.65) {
                    AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 2.65 && RunTime.seconds() < 4) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 4 && RunTime.seconds() < 4.9) {
                    AsterionMotors.mecanumDrive(0, 0, -0.3);
                } else if (RunTime.seconds() > 4.9 && RunTime.seconds() < 5.4) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 5.4 && RunTime.seconds() < 6.05) {
                    AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 6.05) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                }
            }
        }
    }
}