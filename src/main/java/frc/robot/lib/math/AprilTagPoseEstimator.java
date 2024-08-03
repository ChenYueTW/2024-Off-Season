package frc.robot.lib.math;

import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.SwerveDriveConstants.ControllerConstants;
import frc.robot.lib.limelight.AprilTagField;

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

        return new Translation3d(intersect.getX(), intersect.getY(), intersect.getZ());
    }

    public static Rotation2d getAprilTagRotation(Translation3d aprilTagPose,  double aprilTagId) {
        if (aprilTagId == -1.0) return new Rotation2d(0.0);
        double x;
        if (aprilTagPose.getX() < 0.0 && aprilTagId == 3) x = -(Math.abs(aprilTagPose.getX()) + 0.55);
        else if (aprilTagPose.getX() > 0.0 && aprilTagId == 3) x = aprilTagPose.getX() - 0.55;
        else x = aprilTagPose.getX();
        
        Vector2D apriltagVec = new Vector2D(x, aprilTagPose.getY());
        
        double goalRadians = Math.acos(aprilTagPose.getY() / apriltagVec.getNorm());
        return new Rotation2d(Units.radiansToDegrees(goalRadians));
    }

    private static double function(double x) {
        double k = 0.000543599;
        double h = 173.632;

        return Math.atan((k * x * x + 2 * h) / (x * (1 + Math.sqrt(1 - 2 * k * h - k * k * x * x))));
    }

    public static double getAprilTagDegrees(Translation3d aprilTagPose) {
        double x0 = 17.8675;
        return (720.0 / Math.PI) * function((Math.abs(aprilTagPose.getY()) - 0.33) * 100.0 + x0) + 15.0;
    }
}
