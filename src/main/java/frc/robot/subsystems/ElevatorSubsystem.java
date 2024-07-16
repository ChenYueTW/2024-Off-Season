package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.SwerveDriveConstants.ControllerConstants;
import frc.robot.constants.DeviceId.Controller;
import frc.robot.lib.motors.ModuleTalon;

public class ElevatorSubsystem extends SubsystemBase {
    private final ModuleTalon elevator;
    private final PIDController heightAdjustmentPid;

    public ElevatorSubsystem() {
        this.elevator = new ModuleTalon(Controller.elevator.get(), false, true);
        this.heightAdjustmentPid = new PIDController(0.0, 0.0, 0.0);
    }

    public double getPosition() {
        return Units.rotationsToDegrees(this.elevator.getPosition().getValue());
    }

    public double caculateSpeed() {
        return this.heightAdjustmentPid.calculate(this.getPosition(), ControllerConstants.ELEVATOR_GOAL_DEG);
    }

    public void execute(double speed) {
        if (this.getPosition() < ControllerConstants.ELEVATOR_DEG_DOWN_LIMIT && speed > 0) {
            this.elevator.set(speed);
        } else if (this.getPosition() > ControllerConstants.ELEVATOR_DEG_UP_LIMIT && speed < 0) {
            this.elevator.set(speed);
        } else if (this.getPosition() >= ControllerConstants.ELEVATOR_DEG_DOWN_LIMIT && this.getPosition() <= ControllerConstants.ELEVATOR_DEG_UP_LIMIT) {
            this.elevator.set(speed);
        } else {
            this.elevator.stopMotor();
        }
    }

    public void stopElevator() {
        this.elevator.stopMotor();
    }
}
