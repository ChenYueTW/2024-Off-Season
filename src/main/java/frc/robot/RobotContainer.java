package frc.robot;

import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.auto.AutoTrackNote;
import frc.robot.commands.AmpCmd;
import frc.robot.commands.IntakeCmd;
import frc.robot.commands.ShooterArmCmd;
import frc.robot.commands.ShooterCmd;
import frc.robot.commands.SwerveDriveCmd;
import frc.robot.joystick.ButtonStation;
import frc.robot.joystick.Controller;
import frc.robot.joystick.Driver;
import frc.robot.lib.helpers.IDashboardProvider;
import frc.robot.subsystems.AmpSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShooterArmSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class RobotContainer implements IDashboardProvider {
	private final Driver driverJoystick = new Driver(Driver.DRIVER_PORT);
	private final Controller controllerJoystick = new Controller(Controller.CONTROLLER_PORT);
	// private final ButtonStation buttonStation = new ButtonStation(ButtonStation.BUTTON_STATION_PORT);

	private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
	private final Limelight limelight = new Limelight();
	// private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
	// private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	private final ShooterArmSubsystem shooterArmSubsystem = new ShooterArmSubsystem();
	// private final AmpSubsystem ampSubsystem = new AmpSubsystem();

	private final SwerveDriveCmd swerveDriveCmd = new SwerveDriveCmd(swerveSubsystem, driverJoystick);
	// private final IntakeCmd intakeCmd = new IntakeCmd(intakeSubsystem, controllerJoystick::isIntake, controllerJoystick::isRelease);
	// private final ShooterCmd shooterCmd = new ShooterCmd(shooterSubsystem, controllerJoystick::isShoot);
	private final ShooterArmCmd shooterArmCmd = new ShooterArmCmd(shooterArmSubsystem, controllerJoystick::getShooterDirection, controllerJoystick::autoAim);
	// private final AmpCmd ampCmd = new AmpCmd(ampSubsystem, controllerJoystick::isAmpInput, controllerJoystick::isAmpOutput);

	private final SendableChooser<Command> autoCommandChooser;

	public RobotContainer() {
		this.setDefaultCommand();
		this.registerCommand();
		this.registerDashboard();

		this.autoCommandChooser = AutoBuilder.buildAutoChooser();
	}

	private void setDefaultCommand() {
		this.swerveSubsystem.setDefaultCommand(this.swerveDriveCmd);
		// this.intakeSubsystem.setDefaultCommand(this.intakeCmd);
		// this.shooterSubsystem.setDefaultCommand(this.shooterCmd);0
		this.shooterArmSubsystem.setDefaultCommand(this.shooterArmCmd);
		// this.ampSubsystem.setDefaultCommand(this.ampCmd);
	}

	private void registerCommand() {
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
