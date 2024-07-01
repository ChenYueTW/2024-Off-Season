package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.lib.BetterLimelight;
import frc.robot.lib.IDashboardProvider;
import frc.robot.lib.AprilTagFeild.BlueAlliance;

public class Limelight implements IDashboardProvider {
    private final BetterLimelight limelight;
    private double distanceToGoalVerticalMeters;
    private double distanceToGoalHorizontalMeters;

    public Limelight() {
        this.registerDashboard();
        this.limelight = new BetterLimelight("limelight");
    }

    public Translation2d getTagRelativeField(double id) {
        return BlueAlliance.getById(id);
    }

    public Translation2d getTagRelativeRobot() {
        return this.limelight.getPositionVector();
    }

    public double getAprilTagId() {
        return this.limelight.getAprilTagId();
    }

    @Override
    public void putDashboard() {
        SmartDashboard.putNumber("Vertical", this.distanceToGoalVerticalMeters);
        SmartDashboard.putNumber("Horizontal", this.distanceToGoalHorizontalMeters);
        SmartDashboard.putNumber("AprilTag", this.getAprilTagId());
    }
}
