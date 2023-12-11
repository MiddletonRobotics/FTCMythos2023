package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;

public class Lift {
    private DcMotor LiftMotor;
    private DcMotor AngleMotor;
    private HardwareMap hardwareMap;

    private DcMotor[] ActuatorMotors;

    double newTarget;

    public Lift(HardwareMap aHardwareMap) {
        hardwareMap = aHardwareMap;
        AngleMotor = hardwareMap.get(DcMotor.class, Constants.AngleMotorID);
        LiftMotor = hardwareMap.get(DcMotor.class, Constants.LiftMotorID);

        ActuatorMotors = new DcMotor[]{AngleMotor, LiftMotor};
    }

    public void setOpposite(int number) {
        if (number == 0) {
            LiftMotor.setDirection(DcMotor.Direction.FORWARD);
        } else if (number == 1) {
            LiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
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

    public void liftEncoder(int turnage) {
        newTarget = Constants.ticks / turnage;
        LiftMotor.setTargetPosition((int) newTarget)
        LiftMotor.setPower(Constants.DrivingAdjustment);
        LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION)
    }

    public void angleEncoder(int turnage) {
        newTarget = Constants.ticks / turnage;
        AngleMotor.setTargetPosition((int) newTarget)
        AngleMotor.setPower(Constants.DrivingAdjustment);
        AngleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION)
    }
}
