package frc.robot.lib.math;

import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.lib.limelight.AprilTagField;
import frc.robot.subsystems.SwerveSubsystem;

public class AprilTagPoseEstimator {
    private static final double TOLERANCE = 0.012;
    private static final Vector3D CAMERA_POSE = new Vector3D(0.0, 0.26536, 0.640126);
    private static final Vector3D CENTRAL_SIGHT = new Vector3D(0.0, -0.603625, 0.197135);
    private static final Vector3D CAM_X_AXIS = new Vector3D(0.6, 0.0, 0.0);
    private static final Vector3D CAM_Y_AXIS = new Vector3D(0.0, 0.12, 0.36);

    @SuppressWarnings("deprecation")
    public static Translation3d getAprilTagPose(double tx, double ty, int id) {
        if (id == -1) return new Translation3d(0.0, 0.0, 0.0);
        Plane aprilTagPlane = new Plane(new Vector3D(0.0, 0.0, AprilTagField.get3dById(id).getZ()), new Vector3D(0.0, 0.0, 1.0));

        Rotation xRot = new Rotation(CAM_Y_AXIS, Units.degreesToRadians(tx), RotationConvention.VECTOR_OPERATOR);
        Rotation yRot = new Rotation(CAM_X_AXIS, -Units.degreesToRadians(ty), RotationConvention.VECTOR_OPERATOR);
        Vector3D xVector = xRot.applyTo(CENTRAL_SIGHT);
        Vector3D yVector = yRot.applyTo(CENTRAL_SIGHT);
        Plane xPlane = new Plane(CAMERA_POSE, CAMERA_POSE.add(xVector), CAMERA_POSE.add(CAM_Y_AXIS), TOLERANCE);
        Plane yPlane = new Plane(CAMERA_POSE, CAMERA_POSE.add(yVector), CAMERA_POSE.add(CAM_X_AXIS), TOLERANCE);
        Vector3D intersect = Plane.intersection(xPlane, yPlane, aprilTagPlane);

        // rotate 2d matrix
        double theta = SwerveSubsystem.getRotation().getDegrees();
        Vector3D rotatedVec = new Rotation(new Vector3D(0.0, 0.0, 1.0), -theta, RotationConvention.VECTOR_OPERATOR).applyTo(intersect);

        return new Translation3d(rotatedVec.getX(), rotatedVec.getY(), intersect.getZ());
    }

    public static Translation2d getFiled(Translation3d apriltagPose, double id) {
        if (id == -1) return new Translation2d(0.0, 0.0);
        Translation2d apriltagFeild = AprilTagField.get2dById(id);
        return new Translation2d(apriltagPose.getY() - apriltagFeild.getY(), apriltagPose.getX() - apriltagFeild.getX());
    }

    public static double getAprilTagRotation() {
        Pose2d pose = SwerveSubsystem.getPose();
        if (DriverStation.getAlliance().get() == DriverStation.Alliance.Red) {
            double y = 5.54 - pose.getY() > 0.0 ? 5.54 - pose.getY() : 5.54 - pose.getY();
            double theta = Math.atan(y / Math.abs(16.56 - pose.getX()));
            return Units.radiansToDegrees(theta);
        } else {
            double y =  5.54 - pose.getY() > 0.0 ? pose.getY() - 5.54 : pose.getY() - 5.54;
            double theta = Math.atan(y / Math.abs(pose.getX()));
            return Units.radiansToDegrees(theta);
        }   
    }

    private static double function(double x) {
        double k = 0.000543599;
        double h = 173.632;

        return Math.atan((k * x * x + 2 * h) / (x * (1 + Math.sqrt(1 - 2 * k * h - k * k * x * x))));
    }

    public static double getAprilTagDegrees(Translation3d aprilTagPose) {
        double x0 = 17.8675;
        if (SwerveSubsystem.getPose().getX() == 0.0) return 0.0;
        return (720.0 / Math.PI) * function((Math.abs(16.56 - SwerveSubsystem.getPose().getX()) - 0.33) * 100.0 + x0) + 15.0;
    }
}
