package frc.robot.lib.math;

import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.lib.limelight.AprilTagField;

public class AprilTagPoseEstimator {
    private static final double TOLERANCE = 0.012;
    private static final Vector3D CAMERA_POSE = new Vector3D(0.0, 0.262909, 0.6415148);
    private static final Vector3D CENTRAL_SIGHT = new Vector3D(0.0, 0.880132, 0.507567);
    private static final Vector3D CAM_X_AXIS = new Vector3D(-0.88, 0.0, 0.0);
    private static final Vector3D CAM_Y_AXIS = new Vector3D(0.0, -0.45, 0.77);

    @SuppressWarnings("deprecation")
    private static Plane getAprilTagPlane(int id) {
        Translation3d aprilTag = AprilTagField.get3dById(id);
        
        return new Plane(new Vector3D(0.0, 0.0, aprilTag.getZ()), new Vector3D(0.0, 0.0, 1.0));        
    }

    public static Translation3d getAprilTagPose(double tx, double ty, int id) {
        if (id == -1) return new Translation3d(0.0, 0.0, 0.0);
        Plane aprilTagPlane = AprilTagPoseEstimator.getAprilTagPlane(id);

        Rotation xRot = new Rotation(CAM_Y_AXIS, Units.degreesToRadians(tx), RotationConvention.VECTOR_OPERATOR);
        Rotation yRot = new Rotation(CAM_X_AXIS, -Units.degreesToRadians(ty), RotationConvention.VECTOR_OPERATOR);
        Vector3D xVector = xRot.applyTo(CENTRAL_SIGHT);
        Vector3D yVector = yRot.applyTo(CENTRAL_SIGHT);
        Plane xPlane = new Plane(CAMERA_POSE, CAMERA_POSE.add(xVector), CAMERA_POSE.add(CAM_Y_AXIS), TOLERANCE);
        Plane yPlane = new Plane(CAMERA_POSE, CAMERA_POSE.add(yVector), CAMERA_POSE.add(CAM_X_AXIS), TOLERANCE);
        Vector3D intersect = Plane.intersection(xPlane, yPlane, aprilTagPlane);

        return new Translation3d(intersect.getX(), intersect.getY(), intersect.getZ());
    }
}
