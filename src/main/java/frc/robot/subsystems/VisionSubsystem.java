package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.lib.helpers.IDashboardProvider;
import frc.robot.lib.limelight.VisionHelper;
import frc.robot.lib.math.NotePoseEstimator;

public class VisionSubsystem extends SubsystemBase implements IDashboardProvider {
    private final VisionHelper aprilTagCam = new VisionHelper("limelight-tag");
    private final VisionHelper noteCam = new VisionHelper("limelight-note");

    public VisionSubsystem() {
        this.registerDashboard();
    }

    public Translation2d getNotePositionVector() {
        if (this.noNoteTarget()) return new Translation2d(0.0, 0.0);
        return NotePoseEstimator.getPositionVector(this.noteCam.getTx(), this.noteCam.getTy());
    }

    public double getNoteGroundDistance() {
        return this.noNoteTarget() ? -1.0 : this.getNotePositionVector().getNorm();
    }

    public boolean noNoteTarget() {
        return this.noteCam.getAprilTagId() == 0.0;
    }

    @Override
    public void putDashboard() {
        SmartDashboard.putNumber("Note Distance", this.getNoteGroundDistance());
    }

    @Override
    public void putDashboardOnce() {}
}
