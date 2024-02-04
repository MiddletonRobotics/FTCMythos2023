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

@Autonomous(name="BP1-VISION-LeftParking")
@Disabled

public class BlueVisionRightParking extends OpMode {
    VisionColorDetector detector;
    VisionPortal visionPortal;

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

        // Initialize and configure detector and camera
        Rect leftZone = centerRect(120, 250, 150, 230);
        Rect rightZone = centerRect(480, 410, 250, 150);
        detector = new VisionColorDetector(telemetry, TargetColor.BLUE, ViewMode.RAW, leftZone, rightZone);
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), detector);

        detector.targetColor = TargetColor.BLUE;
        CameraStreamServer.getInstance().setSource(detector);
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
        handle_input();
    }

    // Switch the viewModes and targetColors
    void handle_input() {
        if(detector.isConfident()) {
            if(detector.getDetection() == Detection.RIGHT) {
                if (RunTime.seconds() < 2.4) {
                    AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 2.4 && RunTime.seconds() < 2.6) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 2.6 && RunTime.seconds() < 3.2) {
                    AsterionMotors.mecanumDrive(0, 0, -0.3);
                } else if (RunTime.seconds() > 3.2 && RunTime.seconds() < 3.4) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 3.4 && RunTime.seconds() < 4.4) {
                    AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 4.4 && RunTime.seconds() < 4.6) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 4.6 && RunTime.seconds() < 5.6) {
                    AsterionMotors.mecanumDrive(Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 5.1 && RunTime.seconds() < 5.3) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 5.3 && RunTime.seconds() < 5.9) {
                    AsterionMotors.mecanumDrive(0, 0, 0.3);
                } else if (RunTime.seconds() > 5.9 && RunTime.seconds() < 6.1) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 6.1 && RunTime.seconds() < 7.1) {
                    AsterionMotors.mecanumDrive(Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 7.1 && RunTime.seconds() < 7.3) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 7.3 && RunTime.seconds() < 10) {
                    AsterionMotors.mecanumDrive(0, -0.3, 0);
                } else if (RunTime.seconds() > 10) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                }
            } else if(detector.getDetection() == Detection.LEFT) {
                if (RunTime.seconds() < 3.85) {
                    AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 3.85 && RunTime.seconds() < 4) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 4 && RunTime.seconds() < 6.2) {
                    AsterionMotors.mecanumDrive(Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 6.2 && RunTime.seconds() < 6.4) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 6.4 && RunTime.seconds() < 8.2) {
                    AsterionMotors.mecanumDrive(0, -0.3, 0);
                } else if (RunTime.seconds() > 8.2) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                }
            } else {
                if (RunTime.seconds() < 2.4) {
                    AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 2.4 && RunTime.seconds() < 2.6) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 2.6 && RunTime.seconds() < 3.2) {
                    AsterionMotors.mecanumDrive(0, 0, 0.3);
                } else if (RunTime.seconds() > 3.2 && RunTime.seconds() < 3.4) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 3.4 && RunTime.seconds() < 4.4) {
                    AsterionMotors.mecanumDrive(-Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 4.4 && RunTime.seconds() < 4.6) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 4.6 && RunTime.seconds() < 5.6) {
                    AsterionMotors.mecanumDrive(Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 5.1 && RunTime.seconds() < 5.3) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 5.3 && RunTime.seconds() < 5.9) {
                    AsterionMotors.mecanumDrive(0, 0, -0.3);
                } else if (RunTime.seconds() > 5.9 && RunTime.seconds() < 6.1) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 6.1 && RunTime.seconds() < 7.1) {
                    AsterionMotors.mecanumDrive(Constants.AutoSpeed, 0, 0);
                } else if (RunTime.seconds() > 7.1 && RunTime.seconds() < 7.3) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                } else if (RunTime.seconds() > 7.3 && RunTime.seconds() < 9) {
                    AsterionMotors.mecanumDrive(0, -0.3, 0);
                } else if (RunTime.seconds() > 9) {
                    AsterionMotors.mecanumDrive(0, 0, 0);
                }
            }
        }
    }
}