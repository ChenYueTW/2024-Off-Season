package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.SwerveDriveConstants;
import frc.robot.subsystems.SwerveSubsystem;

public class SwerveDriveCmd extends Command {
	private final SwerveSubsystem swerveSubsystem;
	Supplier<Double> xSpeed, ySpeed, rotationSpeed;

	public SwerveDriveCmd(SwerveSubsystem swerveSubsystem, Supplier<Double> xSpeed, Supplier<Double> ySpeed, Supplier<Double> rotationSpeed) {
		this.swerveSubsystem = swerveSubsystem;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.rotationSpeed = rotationSpeed;
		addRequirements(this.swerveSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		// if (this.controller.getAButton()) {
		// 	this.swerveSubsystem.resetPose(new Pose2d(1.37, 5.55, new Rotation2d(0.0)));
		// } else if (this.controller.getYButton()) {
		// 	this.swerveSubsystem.resetGyro();
		// }

		this.swerveSubsystem.driveSwerve(this.xSpeed.get(), this.ySpeed.get(), this.rotationSpeed.get(), SwerveDriveConstants.gyroField);
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
