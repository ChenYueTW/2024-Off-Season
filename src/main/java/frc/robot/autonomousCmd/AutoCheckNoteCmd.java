package frc.robot.autonomousCmd;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.VisionSubsystem;


public class AutoCheckNoteCmd extends Command {
    private final SwerveSubsystem swerveSubsystem;
    private final VisionSubsystem limelight;

    public AutoCheckNoteCmd(SwerveSubsystem swerveSubsystem, VisionSubsystem limelight) {
        this.swerveSubsystem = swerveSubsystem;
        this.limelight = limelight;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.swerveSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (DriverStation.isAutonomous()) {
            if (this.limelight.isNoteTarget()) {
                while (true) {
                    this.swerveSubsystem.driveSwerve(0.5, 0.0, 0.3, false);
                    if (!this.limelight.isNoteTarget()) {
                        break;
                    }
                }
            } else {
                return;
            }
        }
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.swerveSubsystem.stopModules();
    }
}
