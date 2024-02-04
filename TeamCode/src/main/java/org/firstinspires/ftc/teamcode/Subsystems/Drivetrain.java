package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.Constants.MecanumDriveType.ADVANCED;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.Constants.MecanumDriveType.SIMPLE;

import java.util.Objects;

public class Drivetrain {
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor RearLeft;
    private DcMotor RearRight;

    public DcMotor[] AsterionMotors;

    public Constants.MecanumDriveType advancedMode;

    private HardwareMap hardwareMap;

    public Drivetrain(HardwareMap aHardwareMap) {
        hardwareMap = aHardwareMap;

        FrontLeft = hardwareMap.get(DcMotor.class, Constants.FrontLeftID);
        FrontRight = hardwareMap.get(DcMotor.class, Constants.FrontRightID);
        RearLeft = hardwareMap.get(DcMotor.class, Constants.BackLeftID);
        RearRight = hardwareMap.get(DcMotor.class, Constants.BackRightID);

        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RearLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RearRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        AsterionMotors = new DcMotor[]{FrontLeft, FrontRight, RearLeft, RearRight};
    }

    public void setOpposite(int number) {
        if (number == 0) {
            AsterionMotors[0].setDirection(DcMotor.Direction.FORWARD);
            AsterionMotors[1].setDirection(DcMotor.Direction.REVERSE);
            AsterionMotors[2].setDirection(DcMotor.Direction.FORWARD);
            AsterionMotors[3].setDirection(DcMotor.Direction.REVERSE);
        } else if (number == 1) {
            AsterionMotors[0].setDirection(DcMotorSimple.Direction.REVERSE);
            AsterionMotors[1].setDirection(DcMotorSimple.Direction.FORWARD);
            AsterionMotors[2].setDirection(DcMotorSimple.Direction.REVERSE);
            AsterionMotors[3].setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            throw new RuntimeException("Invalid parameter provided, setDirection() is only selective binary");
        }
    }

    public void setBrakingMode(String type) {
        if (Objects.equals(type, "BRAKING")) {
            AsterionMotors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            AsterionMotors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            AsterionMotors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            AsterionMotors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else if (Objects.equals(type, "COAST")) {
            AsterionMotors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            AsterionMotors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            AsterionMotors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            AsterionMotors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        } else {
            throw new RuntimeException("Invalid parameter provided, setBrakingMode() only has parameters of BRAKING and COAST!");
        }
    }

    public void mecanumDrive(double drive, double strafe, double twist) {

        double[] thetaSpeeds = {
                (drive + twist + strafe),
                (drive - twist - strafe),
                (drive + twist - strafe),
                (drive - twist + strafe)
        };

        AsterionMotors[0].setPower(thetaSpeeds[0]);
        AsterionMotors[1].setPower(thetaSpeeds[1]);
        AsterionMotors[2].setPower(thetaSpeeds[2]);
        AsterionMotors[3].setPower(thetaSpeeds[3]);
    }

    public void tankDrive(double leftDriveAxis, double rightDriveAxis, HardwareMap aHardwareMap) {
        AsterionMotors[0].setPower(leftDriveAxis);
        AsterionMotors[1].setPower(rightDriveAxis);
        AsterionMotors[2].setPower(leftDriveAxis);
        AsterionMotors[3].setPower(rightDriveAxis);
    }

    public void povDrive(double drive, double twist, HardwareMap aHardwareMap) {
        double[] speeds = {
                (drive + twist),
                (drive - twist)
        };

        AsterionMotors[0].setPower(speeds[1]);
        AsterionMotors[1].setPower(speeds[0]);
        AsterionMotors[2].setPower(speeds[1]);
        AsterionMotors[3].setPower(speeds[0]);
    }

    public void power(double power) {
        AsterionMotors[0].setPower(power);
        AsterionMotors[1].setPower(power);
        AsterionMotors[2].setPower(power);
        AsterionMotors[3].setPower(power);
    }
}
