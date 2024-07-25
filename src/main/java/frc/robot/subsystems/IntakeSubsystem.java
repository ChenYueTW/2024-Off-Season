package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.constants.DeviceId.Controller;
import frc.robot.constants.SwerveDriveConstants.ControllerConstants;
import frc.robot.lib.motors.ModuleTalon;

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

    public Command releaseNote() {
        return new ParallelRaceGroup(
            Commands.runEnd(() -> {this.execute(ControllerConstants.INTAKE_SPEED);}, this::stopIntake, this),
            new WaitCommand(0.5)
        );
    }

    public void stopIntake() {
        this.leftIntake.stopMotor();
        this.rightIntake.stopMotor();
    }
}
