package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants.ControllerConstants;
import frc.robot.subsystems.AmpSubsystem;

public class AmpCmd extends Command {
	private final AmpSubsystem ampSubsystem;
	private final Supplier<Boolean> isAmpInput, isAmpOutput;

	public AmpCmd(AmpSubsystem ampSubsystem, Supplier<Boolean> isAmpInput, Supplier<Boolean> isAmpOutput) {
		this.ampSubsystem = ampSubsystem;
		this.isAmpInput = isAmpInput;
		this.isAmpOutput = isAmpOutput;
		this.addRequirements(this.ampSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		if (this.isAmpInput.get()) this.ampSubsystem.execute(ControllerConstants.AMP_SPEED);
		else if (this.isAmpOutput.get()) this.ampSubsystem.execute(-ControllerConstants.AMP_SPEED);
		else this.ampSubsystem.stopAmp();
	}

	@Override
	public void end(boolean interrupted) {
		this.ampSubsystem.stopAmp();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
