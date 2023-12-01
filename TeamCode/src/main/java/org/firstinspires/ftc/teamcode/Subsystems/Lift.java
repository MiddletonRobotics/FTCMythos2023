package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Utilities.Constants.Constants;

public class Lift {
    private DcMotor LiftMotor;
    private HardwareMap hardwareMap;

    public Lift(HardwareMap aHardwareMap) {
        hardwareMap = aHardwareMap;
        LiftMotor = hardwareMap.get(DcMotor.class, Constants.LiftMotorID);
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
}
