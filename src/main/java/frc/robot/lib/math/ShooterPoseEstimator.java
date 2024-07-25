package frc.robot.lib.math;

import frc.robot.subsystems.SwerveSubsystem;

public class ShooterPoseEstimator {
    public static final double aprilTagHeight = 145.1102;
    public static double getPosition() {
        double robotXPose = SwerveSubsystem.getPose().getX();
        return Math.atan(robotXPose / aprilTagHeight);
    }
}
