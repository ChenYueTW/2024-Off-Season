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

    public void decline() {
        double speed = this.heightAdjustmentPid.calculate(this.elevator.getPosition().getValue(), ControllerConstants.ELEVATOR_ROT_UP_LIMIT);
        this.execute(speed);
    }

    public void rise() {
        double speed = this.heightAdjustmentPid.calculate(this.elevator.getPosition().getValue(), ControllerConstants.ELEVATOR_ROT_DOWN_LIMIT);
        this.execute(speed);
    }

    public void execute(double speed) {
        if (this.elevator.getPosition().getValue() < ControllerConstants.ELEVATOR_ROT_DOWN_LIMIT && speed > 0) {
            this.elevator.set(speed);
        } else if (this.elevator.getPosition().getValue() > ControllerConstants.ELEVATOR_ROT_UP_LIMIT && speed < 0) {
            this.elevator.set(speed);
        } else if (this.elevator.getPosition().getValue() >= ControllerConstants.ELEVATOR_ROT_DOWN_LIMIT && this.getPosition() <= ControllerConstants.ELEVATOR_ROT_UP_LIMIT) {
            this.elevator.set(speed);
        } else {
            this.elevator.stopMotor();
        }
    }

    public void stopElevator() {
        this.elevator.stopMotor();
    }
}
