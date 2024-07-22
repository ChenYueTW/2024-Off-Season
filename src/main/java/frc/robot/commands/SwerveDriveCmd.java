package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.SwerveDriveConstants;
import frc.robot.subsystems.SwerveSubsystem;

public class SwerveDriveCmd extends Command {
	private final SwerveSubsystem swerveSubsystem;
	private final XboxController controller;

	public SwerveDriveCmd(SwerveSubsystem swerveSubsystem, XboxController controller) {
		this.swerveSubsystem = swerveSubsystem;
		this.controller = controller;
		addRequirements(this.swerveSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		double xSpeed = -MathUtil.applyDeadband(this.controller.getLeftY(), SwerveDriveConstants.DEAD_BAND) * SwerveDriveConstants.MAX_SPEED;
		double ySpeed = -MathUtil.applyDeadband(this.controller.getLeftX(), SwerveDriveConstants.DEAD_BAND) * SwerveDriveConstants.MAX_SPEED;
		double rotation = MathUtil.applyDeadband(this.controller.getRightX(), SwerveDriveConstants.DEAD_BAND) * SwerveDriveConstants.MAX_ANGULAR_SPEED;

		if (this.controller.getAButton()) {
			this.swerveSubsystem.resetPose(new Pose2d(1.37, 5.55, new Rotation2d(0.0)));
		} else if (this.controller.getYButton()) {
			this.swerveSubsystem.resetGyro();
		}

		this.swerveSubsystem.driveSwerve(xSpeed, ySpeed, rotation, SwerveDriveConstants.gyroField);
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
