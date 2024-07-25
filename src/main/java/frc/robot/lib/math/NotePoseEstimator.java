package frc.robot.lib.math;

import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NotePoseEstimator {
    private static final double TOLERANCE = 0.125;
    private static final Vector3D CAMERA_POSE = new Vector3D(-0.25, -0.225, 0.459);
    private static final Vector3D CENTRAL_SIGHT = new Vector3D(0.88, 0.03, -0.46);
    private static final Vector3D CAM_X_AXIS = new Vector3D(0.03, -0.88, 0);
    private static final Vector3D CAM_Y_AXIS = new Vector3D(0.4, 0.01, 0.77);
    private static final Plane GROUND = new Plane(new Vector3D(0.0, 0.0, 0.1), TOLERANCE);

    public static Translation2d getPositionVector(double tx, double ty) {
        Rotation xRot = new Rotation(CAM_Y_AXIS, -tx, RotationConvention.VECTOR_OPERATOR);
        Rotation yRot = new Rotation(CAM_X_AXIS, ty, RotationConvention.VECTOR_OPERATOR);
        Vector3D xVector = xRot.applyTo(CENTRAL_SIGHT);
        Vector3D yVector = yRot.applyTo(CENTRAL_SIGHT);
        Plane xPlane = new Plane(CAMERA_POSE, CAMERA_POSE.add(xVector), CAMERA_POSE.add(CAM_Y_AXIS), TOLERANCE);
        Plane yPlane = new Plane(CAMERA_POSE, CAMERA_POSE.add(yVector), CAMERA_POSE.add(CAM_X_AXIS), TOLERANCE);
        Vector3D intersect = Plane.intersection(xPlane, yPlane, GROUND);
        Vector2D targetXY = new Vector2D(intersect.getX(), intersect.getY());

        if (targetXY.getNorm() == 0) return new Translation2d(0.0, 0.0);
        SmartDashboard.putNumberArray("xVec", xVector.toArray());
        SmartDashboard.putNumberArray("yVec", yVector.toArray());
        SmartDashboard.putNumberArray("intersect", intersect.toArray());
        // targetXY.scalarMultiply((targetXY.getNorm() - 0.415) / targetXY.getNorm());
        return new Translation2d(targetXY.getX(), targetXY.getY());
    }
}
