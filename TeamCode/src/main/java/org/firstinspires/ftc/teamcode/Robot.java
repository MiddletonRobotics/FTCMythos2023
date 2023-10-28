package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Robot extends LinearOpMode {

    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor RearLeft;
    private DcMotor RearRight;
    private double kpStickDriftingCorrection;
    private double triggerForward, triggerReverse, rightAxisX, leftAxisX;
    @Override
    public void runOpMode() throws InterruptedException {
        FrontLeft = hardwareMap.get(DcMotor.class, "FL");
        FrontRight = hardwareMap.get(DcMotor.class, "FR");
        RearLeft = hardwareMap.get(DcMotor.class, "BL");
        RearRight = hardwareMap.get(DcMotor.class, "BR");

        kpStickDriftingCorrection = 0.05;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Active");
            telemetry.update();

            triggerForward = gamepad1.left_trigger;
            triggerReverse = -(gamepad1.left_trigger);
            rightAxisX = gamepad1.right_stick_x;
            leftAxisX = gamepad1.left_stick_x;

            if(rightAxisX > kpStickDriftingCorrection || rightAxisX < -(kpStickDriftingCorrection)) {
                FrontLeft.setPower((triggerForward + triggerReverse) - rightAxisX);
                FrontRight.setPower((triggerForward + triggerReverse));
                RearLeft.setPower((triggerForward + triggerReverse));
                RearRight.setPower((triggerForward + triggerReverse) - rightAxisX);
            } else if(leftAxisX > kpStickDriftingCorrection || leftAxisX < -(kpStickDriftingCorrection)) {
                FrontLeft.setPower((triggerForward + triggerReverse));
                FrontRight.setPower(-(triggerForward + triggerReverse));
                RearLeft.setPower(0);
                RearRight.setPower(0);
            } else {
                FrontLeft.setPower((triggerForward + triggerReverse));
                FrontRight.setPower((triggerForward + triggerReverse));
                RearLeft.setPower((triggerForward + triggerReverse));
                RearRight.setPower((triggerForward + triggerReverse));
            }
        }

    }
}
