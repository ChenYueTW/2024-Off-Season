package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class AutoTrackNote extends Command {
	private final SwerveSubsystem swerveSubsystem;
	private final VisionSubsystem limelight;

	public AutoTrackNote(SwerveSubsystem swerveSubsystem, VisionSubsystem limelight) {
		this.swerveSubsystem = swerveSubsystem;
		this.limelight = limelight;
		addRequirements(this.swerveSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		if (this.limelight.isNoteTarget() || this.limelight.getNotePositionVector() == null || this.limelight.getNoteGroundDistance() < 0.1) {
			this.swerveSubsystem.driveSwerve(0.0, 0.0, 0.0, false);
			return;
		}
		Translation2d vector = this.limelight.getNotePositionVector();
		this.swerveSubsystem.situateRobot(vector, vector.getAngle().getDegrees(), false, true);
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
