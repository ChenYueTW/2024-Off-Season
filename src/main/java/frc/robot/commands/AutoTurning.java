package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoTurning extends Command {
	private final SwerveSubsystem swerveSubsystem;
	private final VisionSubsystem visionSubsystem;

	public AutoTurning(SwerveSubsystem swerveSubsystem, VisionSubsystem visionSubsystem) {
		this.swerveSubsystem = swerveSubsystem;
		this.visionSubsystem = visionSubsystem;

		addRequirements(this.swerveSubsystem, this.visionSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		double goalRotationDeg = this.visionSubsystem.getGoalRotationDeg();
		if (goalRotationDeg == 0.0) return;
		this.swerveSubsystem.autoTurnRobot(goalRotationDeg);
	}

	@Override
	public void end(boolean interrupted) {
		this.swerveSubsystem.stopModules();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
