package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeviceId.Controller;
import frc.robot.lib.Motor.ModuleTalon;

public class IntakeSubsystem extends SubsystemBase {
    private final ModuleTalon leftIntake;
    private final ModuleTalon rightIntake;

    public IntakeSubsystem() {
        this.leftIntake = new ModuleTalon(Controller.leftIntake.get(), false, false);
        this.rightIntake = new ModuleTalon(Controller.rightIntake.get(), false, false);
    }

    public void execute(double speed) {
        this.leftIntake.set(speed);
        this.rightIntake.set(speed);
    }

    public void stopIntake() {
        this.leftIntake.stopMotor();
        this.rightIntake.stopMotor();
    }
}
