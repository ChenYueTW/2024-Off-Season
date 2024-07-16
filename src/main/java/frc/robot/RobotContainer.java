package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auto.AutoTrackNote;
import frc.robot.commands.AmpCmd;
import frc.robot.commands.IntakeCmd;
import frc.robot.commands.ShooterCmd;
import frc.robot.commands.SwerveDriveCmd;
import frc.robot.joystick.ButtonStation;
import frc.robot.joystick.Controller;
import frc.robot.joystick.Driver;
import frc.robot.subsystems.AmpSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class RobotContainer {
	private final Driver driverJoystick = new Driver(Driver.DRIVER_PORT);
	// private final Controller controllerJoystick = new Controller(Controller.CONTROLLER_PORT);
	// private final ButtonStation buttonStation = new ButtonStation(ButtonStation.BUTTON_STATION_PORT);

	private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
	private final Limelight limelight = new Limelight();
	// private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
	// private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	// private final AmpSubsystem ampSubsystem = new AmpSubsystem();

	private final SwerveDriveCmd swerveDriveCmd = new SwerveDriveCmd(swerveSubsystem, driverJoystick);
	// private final IntakeCmd intakeCmd = new IntakeCmd(intakeSubsystem, controllerJoystick::isIntake, controllerJoystick::isRelease);
	// private final ShooterCmd shooterCmd = new ShooterCmd(shooterSubsystem, controllerJoystick::isShoot);
	// private final AmpCmd ampCmd = new AmpCmd(ampSubsystem, controllerJoystick::isAmpInput, controllerJoystick::isAmpOutput);

	public RobotContainer() {
		this.swerveSubsystem.setDefaultCommand(this.swerveDriveCmd);
		// this.intakeSubsystem.setDefaultCommand(this.intakeCmd);
		// this.shooterSubsystem.setDefaultCommand(this.shooterCmd);
		// this.ampSubsystem.setDefaultCommand(this.ampCmd);
	}

	public Command getAutonomousCommand() {
		return new AutoTrackNote(swerveSubsystem, limelight);
	}
}
