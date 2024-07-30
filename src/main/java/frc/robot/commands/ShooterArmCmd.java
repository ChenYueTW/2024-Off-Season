package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterArmSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class ShooterArmCmd extends Command {
	private final ShooterArmSubsystem shooterArmSubsystem;
	private final VisionSubsystem visionSubsystem;
	private final Supplier<Double> speed;
	private final Supplier<Boolean> isAutoAim;

	public ShooterArmCmd(ShooterArmSubsystem shooterArmSubsystem, VisionSubsystem visionSubsystem, Supplier<Double> speed, Supplier<Boolean> isAutoAim) {
		this.shooterArmSubsystem = shooterArmSubsystem;
		this.visionSubsystem = visionSubsystem;
		this.speed = speed;
		this.isAutoAim = isAutoAim;
		this.addRequirements(this.shooterArmSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		if (this.isAutoAim.get()) {
			double goalPosition = this.visionSubsystem.getGoalArmDeg();
			if (goalPosition == 0.0) return;
			this.shooterArmSubsystem.toGoalDegrees(goalPosition);
		} else this.shooterArmSubsystem.execute(this.speed.get() * 1.0);
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
