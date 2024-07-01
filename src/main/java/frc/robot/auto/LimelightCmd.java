package frc.robot.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.lib.MathHelper;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveSubsystem;

public class LimelightCmd extends Command {
	private final SwerveSubsystem swerveSubsystem;
	private final Limelight limelight;
	private final PIDController traslationPid;
	private final PIDController rotationPid;

	public LimelightCmd(SwerveSubsystem swerveSubsystem, Limelight limelight) {
		this.swerveSubsystem = swerveSubsystem;
		this.limelight = limelight;
		this.traslationPid = new PIDController(0.45, 0, 0);
		this.rotationPid = new PIDController(0.49, 0, 0);

		addRequirements(this.swerveSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		double tagId = this.limelight.getAprilTagId();
		Rotation2d robotRotation = this.swerveSubsystem.getRotation();
		Translation2d tagRelativeField = this.limelight.getTagRelativeField(tagId);
		Translation2d tagRelativeRobot = this.limelight.getTagRelativeRobot();

		Translation2d rotatedRobotField = MathHelper.rotation2dMatrix(tagRelativeRobot, robotRotation.getDegrees());
		Translation2d fieldRelativeRobot = MathHelper.translationSubtract(tagRelativeField, rotatedRobotField);

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
