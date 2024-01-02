package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;

public class Lift {
    private static DcMotor LiftMotor;
    private static DcMotor AngleMotor;
    private HardwareMap hardwareMap;

    private DcMotor[] ActuatorMotors;

    double newTarget;

    public Lift(HardwareMap aHardwareMap) {
        hardwareMap = aHardwareMap;
        AngleMotor = hardwareMap.get(DcMotor.class, Constants.AngleMotorID);
        LiftMotor = hardwareMap.get(DcMotor.class, Constants.LiftMotorID);

        AngleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ActuatorMotors = new DcMotor[]{AngleMotor, LiftMotor};
    }

    public void setOpposite(int number) {
        if (number == 0) {
            LiftMotor.setDirection(DcMotor.Direction.FORWARD);
            AngleMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        } else if (number == 1) {
            LiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            AngleMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            throw new RuntimeException("Invalid parameter provided, setDirection() is only selective binary");
        }
    }

    public void setBrakingMode(String type) {
        if (type == "BRAKING") {
            LiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else if (type == "COAST") {
            LiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        } else {
            throw new RuntimeException("Invalid parameter provided, setBrakingMode() only has parameters of BRAKING and COAST!");
        }
    }

    public void moveLift(double axis) {
        if(axis > 0.1) {
            LiftMotor.setPower(Constants.LiftSpeed);
        } else if(axis < -0.1) {
            LiftMotor.setPower(-Constants.LiftSpeed);
        } else {
            LiftMotor.setPower(0);
        }
    }

    public void moveAngle(double axis) {
        if(axis > 0.1) {
            AngleMotor.setPower(Constants.AngleSpeed);
        } else if(axis < -0.1) {
            AngleMotor.setPower(-Constants.AngleSpeed);
        } else {
            AngleMotor.setPower(0);
        }
    }

    public void setHangingPosition() {
        double angleTargetPosition = Constants.liftReadyPosition;
        AngleMotor.setTargetPosition((int) angleTargetPosition);
        AngleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        AngleMotor.setPower(Constants.LiftSpeed);

        double liftTargetPosition = Constants.liftExtendedPosition;
        LiftMotor.setTargetPosition((int) liftTargetPosition);
        LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LiftMotor.setPower(Constants.LiftSpeed);
    }

    public void startHanging() {
        LiftMotor.setTargetPosition(Constants.liftRetractedPosition);
        LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LiftMotor.setPower(Constants.LiftSpeed);
    }

    public void setIdlePositiion() {
        AngleMotor.setTargetPosition(Constants.liftIdlePosition);
        AngleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        AngleMotor.setPower(Constants.LiftSpeed);

        LiftMotor.setTargetPosition(Constants.liftRetractedPosition);
        LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LiftMotor.setPower(Constants.LiftSpeed);
    }

    /*

    public static int getLiftPosition() {
        int telem = LiftMotor.getCurrentPosition();
        return telem;
    }

    public int getAnglePosition() {
        int telem = AngleMotor.getCurrentPosition();
        return telem;
    }

    */
}
