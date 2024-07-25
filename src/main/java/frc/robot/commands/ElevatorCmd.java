package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorCmd extends Command {
	private final ElevatorSubsystem elevatorSubsystem;
	private final Supplier<Double> adjustmentHeight; 
	public ElevatorCmd(ElevatorSubsystem elevatorSubsystem, Supplier<Double> adjustmentHeight) {
		this.elevatorSubsystem = elevatorSubsystem;
		this.adjustmentHeight = adjustmentHeight;
		addRequirements(this.elevatorSubsystem);
	}

	@Override
	public void initialize() {}

	@Override
	public void execute() {
		this.elevatorSubsystem.execute(this.adjustmentHeight.get());
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
