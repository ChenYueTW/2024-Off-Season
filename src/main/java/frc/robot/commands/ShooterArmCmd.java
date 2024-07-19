package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterArmSubsystem;

public class ShooterArmCmd extends Command {
	private final ShooterArmSubsystem shooterArmSubsystem;
	private final Supplier<Double> speed;

	public ShooterArmCmd(ShooterArmSubsystem shooterArmSubsystem, Supplier<Double> speed) {
		this.shooterArmSubsystem = shooterArmSubsystem;
		this.speed = speed;
		this.addRequirements(this.shooterArmSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
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
