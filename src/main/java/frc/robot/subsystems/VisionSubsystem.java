package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.lib.helpers.IDashboardProvider;
import frc.robot.lib.limelight.VisionHelper;
import frc.robot.lib.math.AprilTagPoseEstimator;
import frc.robot.lib.math.NotePoseEstimator;

public class VisionSubsystem extends SubsystemBase implements IDashboardProvider {
    private final VisionHelper aprilTagCam = new VisionHelper("limelight-tag");
    private final VisionHelper noteCam = new VisionHelper("limelight-note");

    public VisionSubsystem() {
        this.registerDashboard();
    }

    public Translation2d getNotePositionVector() {
        if (!this.noteCam.isNoteTarget()) return new Translation2d(0.0, 0.0);
        return NotePoseEstimator.getPositionVector(this.noteCam.getTx(), this.noteCam.getTy());
    }

    public double getGoalRotationDeg() {
        if (DriverStation.getAlliance().get() == DriverStation.Alliance.Red && 
        this.aprilTagCam.getAprilTagId() == 3 && 
        this.aprilTagCam.getAprilTagId() == 4
        ) {
            return AprilTagPoseEstimator.getAprilTagRotation(AprilTagPoseEstimator.getAprilTagPose(this.aprilTagCam.getTx(), this.aprilTagCam.getTy(), this.aprilTagCam.getAprilTagId())).getDegrees();
        } else if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue && 
        this.aprilTagCam.getAprilTagId() == 7 && 
        this.aprilTagCam.getAprilTagId() == 8
        ) {
            return AprilTagPoseEstimator.getAprilTagRotation(AprilTagPoseEstimator.getAprilTagPose(this.aprilTagCam.getTx(), this.aprilTagCam.getTy(), this.aprilTagCam.getAprilTagId())).getDegrees();
        } else return 0.0;
    }

    public double getGoalArmDeg() {
        if (DriverStation.getAlliance().get() == DriverStation.Alliance.Red && 
        this.aprilTagCam.getAprilTagId() == 3 ||
        this.aprilTagCam.getAprilTagId() == 4
        ) {
            return AprilTagPoseEstimator.getAprilTagDegrees(AprilTagPoseEstimator.getAprilTagPose(this.aprilTagCam.getTx(), this.aprilTagCam.getTy(), this.aprilTagCam.getAprilTagId()));
        } else if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue && 
        this.aprilTagCam.getAprilTagId() == 7 ||
        this.aprilTagCam.getAprilTagId() == 8
        ) {
            return AprilTagPoseEstimator.getAprilTagDegrees(AprilTagPoseEstimator.getAprilTagPose(this.aprilTagCam.getTx(), this.aprilTagCam.getTy(), this.aprilTagCam.getAprilTagId()));
        } else return 0.0;
    }

    public double getNoteGroundDistance() {
        return !this.noteCam.isNoteTarget() ? -1.0 : this.getNotePositionVector().getNorm();
    }

    public Translation3d getAprilTagVector() {
        if (!this.aprilTagCam.isNoteTarget()) return new Translation3d(0.0, 0.0, 0.0);
        return AprilTagPoseEstimator.getAprilTagPose(this.aprilTagCam.getTx(), this.aprilTagCam.getTy(), this.aprilTagCam.getAprilTagId());
    }

    public boolean isNoteTarget() {
        return this.noteCam.isNoteTarget();
    }

    @Override
    public void putDashboard() {
        SmartDashboard.putNumber("Note Distance", this.getNoteGroundDistance());
        SmartDashboard.putNumber("AprilTag", this.aprilTagCam.getAprilTagId());
        SmartDashboard.putNumber("AprilTagArmDeg", this.getGoalArmDeg());
        SmartDashboard.putNumber("AprilTagRotDeg", this.getGoalRotationDeg());
    }

    @Override
    public void putDashboardOnce() {}
}
