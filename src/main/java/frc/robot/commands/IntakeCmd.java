package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants.ControllerConstants;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCmd extends Command {
	private final IntakeSubsystem intakeSubsystem;
	private final Supplier<Boolean> isIntake;
	private final Supplier<Boolean> isOuttake;

	public IntakeCmd(IntakeSubsystem intakeSubsystem, Supplier<Boolean> isIntake, Supplier<Boolean> isOuttake) {
		this.intakeSubsystem = intakeSubsystem;
		this.isIntake = isIntake;
		this.isOuttake = isOuttake;
		this.addRequirements(this.intakeSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		if (this.isIntake.get()) this.intakeSubsystem.execute(ControllerConstants.INTAKE_SPEED);
		else if (this.isOuttake.get()) this.intakeSubsystem.execute(-ControllerConstants.INTAKE_SPEED);
		else this.intakeSubsystem.stopIntake();
	}

	@Override
	public void end(boolean interrupted) {
		this.intakeSubsystem.stopIntake();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
