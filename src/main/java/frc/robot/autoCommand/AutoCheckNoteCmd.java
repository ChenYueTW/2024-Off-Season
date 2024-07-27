package frc.robot.autoCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutoTrackNote;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.commands.AutoTrackNote;


public class AutoCheckNoteCmd extends Command {
    private final SwerveSubsystem swerveSubsystem;
    private final VisionSubsystem limelight;

    public AutoCheckNoteCmd(SwerveSubsystem swerveSubsystem, VisionSubsystem limelight) {
        this.swerveSubsystem = swerveSubsystem;
        this.limelight = limelight;
        addRequirements(this.swerveSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (DriverStation.isAutonomous()) {
            if (!this.limelight.isNoteTarget()) {
                while (true) {
                    this.swerveSubsystem.driveSwerve(0.0, 0.3, -0.3, false);
                    if (this.limelight.isNoteTarget()) {
                        new AutoTrackNote(swerveSubsystem,limelight);
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
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.swerveSubsystem.stopModules();
    }
}
