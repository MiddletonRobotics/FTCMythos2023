package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;

public class Drivetrain {

    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor RearLeft;
    private DcMotor RearRight;

    DcMotor[] AsterionMotors;

    private HardwareMap hardwareMap;

    public Drivetrain(HardwareMap aHardwareMap) {
        hardwareMap = aHardwareMap;

        FrontLeft = hardwareMap.get(DcMotor.class, Constants.FrontLeftID);
        FrontRight = hardwareMap.get(DcMotor.class, Constants.FrontRightID);
        RearLeft = hardwareMap.get(DcMotor.class, Constants.BackLeftID);
        RearRight = hardwareMap.get(DcMotor.class, Constants.BackRightID);

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
        if (type == "BRAKING") {
            AsterionMotors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            AsterionMotors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            AsterionMotors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            AsterionMotors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else if (type == "COAST") {
            AsterionMotors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            AsterionMotors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            AsterionMotors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            AsterionMotors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        } else {
            throw new RuntimeException("Invalid parameter provided, setBrakingMode() only has parameters of BRAKING and COAST!");
        }
    }

    public void mecanumDrive(double drive, double strafe, double twist) {
        double[] speeds = {
                (drive - strafe - twist),
                (drive + strafe + twist),
                (drive + strafe - twist),
                (drive - strafe + twist)
        };

        double max = Math.abs(speeds[0]);
        for(int i = 0; i < speeds.length; i++) {
            if ( max < Math.abs(speeds[i]) ) max = Math.abs(speeds[i]);
        }

        if (max > 1) {
            for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
        }

        AsterionMotors[0].setPower(speeds[0]);
        AsterionMotors[1].setPower(speeds[1]);
        AsterionMotors[2].setPower(speeds[2]);
        AsterionMotors[3].setPower(speeds[3]);
    }

    public void tankDrive(double leftDriveAxis, double rightDriveAxis) {
        AsterionMotors[0].setPower(leftDriveAxis);
        AsterionMotors[1].setPower(rightDriveAxis);
        AsterionMotors[2].setPower(leftDriveAxis);
        AsterionMotors[3].setPower(rightDriveAxis);
    }

    public void povDrive(double drive, double twist) {
        double[] speeds = {
                (drive + twist),
                (drive - twist)
        };

        AsterionMotors[0].setPower(speeds[1]);
        AsterionMotors[1].setPower(speeds[0]);
        AsterionMotors[2].setPower(speeds[1]);
        AsterionMotors[3].setPower(speeds[0]);
    }
}
