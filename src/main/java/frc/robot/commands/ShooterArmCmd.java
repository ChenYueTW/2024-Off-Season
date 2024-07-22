package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.SwerveDriveConstants.ControllerConstants;
import frc.robot.lib.math.ShooterPosEstimator;
import frc.robot.subsystems.ShooterArmSubsystem;

public class ShooterArmCmd extends Command {
	private final ShooterArmSubsystem shooterArmSubsystem;
	private final Supplier<Double> speed;
	private final Supplier<Boolean> isAutoAim;

	public ShooterArmCmd(ShooterArmSubsystem shooterArmSubsystem, Supplier<Double> speed, Supplier<Boolean> isAutoAim) {
		this.shooterArmSubsystem = shooterArmSubsystem;
		this.speed = speed;
		this.isAutoAim = isAutoAim;
		this.addRequirements(this.shooterArmSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		if (this.isAutoAim.get()) {
			double goalPosition = ShooterPosEstimator.getPosition() + ControllerConstants.SHOOTER_ARM_DEG_DOWN_LIMIT;
			this.shooterArmSubsystem.toGoalDegrees(goalPosition);
		}
		this.shooterArmSubsystem.execute(this.speed.get() * 1.0);
	}

	@Override
	public void end(boolean interrupted) {
		this.shooterArmSubsystem.stopShooterArm();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
