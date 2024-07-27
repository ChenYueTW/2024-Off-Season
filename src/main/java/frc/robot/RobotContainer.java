package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.autoCommand.AutoCheckNoteCmd;
import frc.robot.commands.AmpCmd;
import frc.robot.commands.ElevatorCmd;
import frc.robot.commands.IntakeCmd;
import frc.robot.commands.ShooterArmCmd;
import frc.robot.commands.ShooterCmd;
import frc.robot.commands.SwerveDriveCmd;
<<<<<<< HEAD
=======
import frc.robot.autoCommand.AutoCheckNoteCmd;
>>>>>>> 761d60d80fe537127064f23c93a7b75ff0d84077
import frc.robot.joystick.ButtonStation;
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
	// private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	// private final ShooterArmSubsystem shooterArmSubsystem = new ShooterArmSubsystem();
	// private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
	// private final AmpSubsystem ampSubsystem = new AmpSubsystem();

	private final SendableChooser<Command> autoCommandChooser;

	public RobotContainer() {
		this.setDefaultCommands();
		this.registerCommands();
		this.configBindings();
		this.registerDashboard();

		this.autoCommandChooser = AutoBuilder.buildAutoChooser();
	}

	private void setDefaultCommands() {
		this.swerveSubsystem.setDefaultCommand(new SwerveDriveCmd(swerveSubsystem, driverJoystick));
		this.intakeSubsystem.setDefaultCommand(new IntakeCmd(intakeSubsystem, controllerJoystick::isIntake, controllerJoystick::isRelease));
		// this.shooterSubsystem.setDefaultCommand(new ShooterCmd(shooterSubsystem, controllerJoystick::isShoot));
		// this.shooterArmSubsystem.setDefaultCommand(new ShooterArmCmd(shooterArmSubsystem, controllerJoystick::getShooterDirection, controllerJoystick::autoAim));
		// this.elevatorSubsystem.setDefaultCommand(new ElevatorCmd(elevatorSubsystem, controllerJoystick::getElevatorDirection));
		// this.ampSubsystem.setDefaultCommand(new AmpCmd(ampSubsystem, controllerJoystick::isAmpInput, controllerJoystick::isAmpOutput));
	}

	private void registerCommands() {
		NamedCommands.registerCommand("AutoShoot", this.autoShoot());
		NamedCommands.registerCommand("AutoCheckNotes", new AutoCheckNoteCmd(swerveSubsystem, limelight));
	}

	private void configBindings() {
		// this.buttonStation.autoShoot().onTrue(
		// 	Commands.runOnce(this::autoShoot, this.shooterSubsystem, this.intakeSubsystem)
		// );
	}

	private Command autoShoot() {
		return new ParallelCommandGroup(
			// Commands.runEnd(this.shooterSubsystem::autoExecute, this.shooterSubsystem::stopShooter, this.shooterSubsystem),
			Commands.runEnd(this.intakeSubsystem::releaseNote, this.intakeSubsystem::stopIntake, this.intakeSubsystem)
		);
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
