package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorCmd extends Command {
	private final ElevatorSubsystem elevatorSubsystem;
	private final Supplier<Double> adjustmentHeight;
	private final Supplier<Boolean> a; 
	public ElevatorCmd(ElevatorSubsystem elevatorSubsystem, Supplier<Double> adjustmentHeight, Supplier<Boolean> a) {
		this.elevatorSubsystem = elevatorSubsystem;
		this.adjustmentHeight = adjustmentHeight;
		this.a = a;
		addRequirements(this.elevatorSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		if (this.a.get()) {
			this.elevatorSubsystem.decline();
		} else this.elevatorSubsystem.execute(this.adjustmentHeight.get());
	}

	@Override
	public void end(boolean interrupted) {
		this.elevatorSubsystem.stopElevator();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
