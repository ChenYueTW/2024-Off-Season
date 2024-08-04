package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.autoCommand.AutoCheckNoteCmd;
import frc.robot.commands.AmpCmd;
import frc.robot.commands.AutoTurning;
import frc.robot.commands.ElevatorCmd;
import frc.robot.commands.IntakeCmd;
import frc.robot.commands.ShooterArmCmd;
import frc.robot.commands.ShooterCmd;
import frc.robot.commands.SwerveDriveCmd;
import frc.robot.joystick.Controller;
import frc.robot.joystick.Driver;
import frc.robot.lib.helpers.IDashboardProvider;
import frc.robot.subsystems.AmpSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.ShooterArmSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class RobotContainer implements IDashboardProvider {
	private final Driver driverJoystick = new Driver(Driver.DRIVER_PORT);
	private final Controller controllerJoystick = new Controller(Controller.CONTROLLER_PORT);
	// private final ButtonStation buttonStation = new ButtonStation(ButtonStation.BUTTON_STATION_PORT);

	private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
	private final VisionSubsystem limelight = new VisionSubsystem();
	private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
	private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	private final ShooterArmSubsystem shooterArmSubsystem = new ShooterArmSubsystem();
	private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
	private final AmpSubsystem ampSubsystem = new AmpSubsystem();

	private final SendableChooser<Command> autoCommandChooser;

	public RobotContainer() {
		this.setDefaultCommands();
		this.registerCommands();
		this.autoCommandChooser = AutoBuilder.buildAutoChooser();
		this.configBindings();
		this.registerDashboard();	
	}

	private void setDefaultCommands() {
		this.swerveSubsystem.setDefaultCommand(new SwerveDriveCmd(swerveSubsystem, driverJoystick::getDesiredXSpeed, driverJoystick::getDesiredYSpeed, driverJoystick::getDesiredRotationSpeed));
		this.intakeSubsystem.setDefaultCommand(new IntakeCmd(intakeSubsystem, controllerJoystick::isIntake, controllerJoystick::isRelease));
		this.shooterSubsystem.setDefaultCommand(new ShooterCmd(shooterSubsystem, controllerJoystick::isShoot));
		this.shooterArmSubsystem.setDefaultCommand(new ShooterArmCmd(shooterArmSubsystem, limelight, controllerJoystick::getShooterDirection, controllerJoystick::autoAim));
		this.elevatorSubsystem.setDefaultCommand(new ElevatorCmd(elevatorSubsystem, controllerJoystick::getElevatorDirection));
		this.ampSubsystem.setDefaultCommand(new AmpCmd(ampSubsystem, controllerJoystick::isAmpInput, controllerJoystick::isAmpOutput));
	}

	private void registerCommands() {
		NamedCommands.registerCommand("Controller", this.runController());
		NamedCommands.registerCommand("AutoShoot", this.autoShoot());
		NamedCommands.registerCommand("AutoAim", this.autoAim());
		NamedCommands.registerCommand("AutoTurn", new AutoTurning(swerveSubsystem, limelight));
		NamedCommands.registerCommand("ReleaseIntake", this.intakeSubsystem.releaseNote());
		NamedCommands.registerCommand("AutoCheckNotes", new SequentialCommandGroup(new ParallelDeadlineGroup(new WaitCommand(3.0), new AutoCheckNoteCmd(swerveSubsystem, limelight))));
	}

	private void configBindings() {
		this.driverJoystick.autoAmp().onTrue(
			Commands.runOnce(() -> {this.autoAMP().schedule();}, this.elevatorSubsystem, this.ampSubsystem)
		);
		this.driverJoystick.autoShoot().onTrue(
			Commands.runOnce(this::allAutoShoot, this.shooterSubsystem, this.intakeSubsystem, this.shooterArmSubsystem)
		);
		this.driverJoystick.stopSwerve().whileTrue(
			Commands.run(this.swerveSubsystem::stopModules, this.swerveSubsystem)
		);
		this.driverJoystick.shooterToElevator().onTrue(
			Commands.runEnd(this::shooterToElevator, this::stopShooterToElevator, this.ampSubsystem, this.shooterSubsystem, this.shooterArmSubsystem)
		);
	}

	private Command runController() {
		return new ParallelCommandGroup(
			this.autoAim(),
			this.intakeSubsystem.releaseNote(),
			this.shooterSubsystem.autoExecute()
		);
	}

	private Command autoShoot() {
		return new ParallelCommandGroup(
			new ParallelRaceGroup(
				Commands.runEnd(this.shooterSubsystem::autoExecute, this.shooterSubsystem::stopShooter, this.shooterSubsystem),
				new WaitCommand(1.5)
			),
			new SequentialCommandGroup(
				new WaitCommand(1.0),
				new ParallelRaceGroup(
					Commands.runEnd(this.intakeSubsystem::releaseNote, this.intakeSubsystem::stopIntake, this.intakeSubsystem),
					new WaitCommand(1.0)
				)
			)
		);
	}

	private Command autoAim() {
		double goalPosition = this.limelight.getGoalArmDeg();
		return Commands.runEnd(() -> {this.shooterArmSubsystem.toGoalDegrees(goalPosition);}, this.shooterArmSubsystem::stopShooterArm, this.shooterArmSubsystem);
	}

	public Command autoAMP() {
		return new SequentialCommandGroup(
			new ParallelRaceGroup(
				Commands.runEnd(this.elevatorSubsystem::decline, this.elevatorSubsystem::stopElevator, this.elevatorSubsystem),
				new WaitCommand(0.8)
			),
			new WaitCommand(0.3),
			new ParallelRaceGroup(
				Commands.runEnd(() -> {this.ampSubsystem.execute(0.4);}, this.ampSubsystem::stopAmp, this.ampSubsystem),
				new WaitCommand(1.0)
			),
			new WaitCommand(0.3),
			new ParallelRaceGroup(
				Commands.runEnd(this.elevatorSubsystem::rise, this.elevatorSubsystem::stopElevator, this.elevatorSubsystem),
				new WaitCommand(0.8)
			)
		);
	}

	public Command shooterToElevator() {
		return new SequentialCommandGroup(
			new ParallelRaceGroup(
				Commands.runEnd(this.shooterArmSubsystem::toElevator, this.shooterArmSubsystem::stopShooterArm, this.shooterArmSubsystem),
				new WaitCommand(1.0)
			),
			new ParallelCommandGroup(
				new ParallelRaceGroup(
					Commands.runEnd(this.shooterSubsystem::toElevator, this.shooterSubsystem::stopShooter, this.shooterSubsystem),
					new WaitCommand(0.7)
				),
				new ParallelRaceGroup(
					Commands.runEnd(this.ampSubsystem::execute, this.ampSubsystem::stopAmp, this.ampSubsystem),
					new WaitCommand(0.7)
				)
			)
		);
	}

	private Command allAutoShoot() {
		return new SequentialCommandGroup(
			new ParallelRaceGroup(
				new AutoTurning(this.swerveSubsystem, this.limelight),
				new WaitCommand(0.5)
			),
			new ParallelRaceGroup(
				Commands.runEnd(this::autoShoot, this::stopShoot, this.shooterSubsystem, this.shooterArmSubsystem, this.intakeSubsystem),
				new WaitCommand(1.5)
			)
		);
	}

	private void stopShoot() {
		this.shooterSubsystem.stopShooter();
		this.shooterArmSubsystem.stopShooterArm();
		this.intakeSubsystem.stopIntake();
	}

	private void stopAmp() {
		this.ampSubsystem.stopAmp();
		this.elevatorSubsystem.stopElevator();
	}

	private void stopShooterToElevator() {
		this.ampSubsystem.stopAmp();
		this.shooterSubsystem.stopShooter();
		this.shooterArmSubsystem.stopShooterArm();
	}

	public Command getAutonomousCommand() {
		return this.autoCommandChooser.getSelected();
	}

	@Override
	public void putDashboard() {}

	@Override
	public void putDashboardOnce() {
		SmartDashboard.putData("PathChooser", this.autoCommandChooser);
	}
}
