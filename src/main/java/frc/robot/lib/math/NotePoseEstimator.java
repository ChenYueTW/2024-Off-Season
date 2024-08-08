package frc.robot.lib.math;

import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NotePoseEstimator {
    private static final double TOLERANCE = 0.125;
    private static final Vector3D CAMERA_POSE = new Vector3D(0.0, 0.326949, 0.608956);
    private static final Vector3D CENTRAL_SIGHT = new Vector3D(0.0, 0.520162, -0.364221);
    private static final Vector3D CAM_X_AXIS = new Vector3D(-0.52, 0.0, 0.0);
    private static final Vector3D CAM_Y_AXIS = new Vector3D(0.0, 0.19, 0.27);
    private static final Plane GROUND = new Plane(new Vector3D(0.0, 0.0, 1.0), TOLERANCE);

    public static Translation2d getPositionVector(double tx, double ty) {
        Rotation xRot = new Rotation(CAM_Y_AXIS, Units.degreesToRadians(tx), RotationConvention.VECTOR_OPERATOR);
        Rotation yRot = new Rotation(CAM_X_AXIS, Units.degreesToRadians(ty), RotationConvention.VECTOR_OPERATOR);
        Vector3D xVector = xRot.applyTo(CENTRAL_SIGHT);
        Vector3D yVector = yRot.applyTo(CENTRAL_SIGHT);
        Plane xPlane = new Plane(CAMERA_POSE, CAMERA_POSE.add(xVector), CAMERA_POSE.add(CAM_Y_AXIS), TOLERANCE);
        Plane yPlane = new Plane(CAMERA_POSE, CAMERA_POSE.add(yVector), CAMERA_POSE.add(CAM_X_AXIS), TOLERANCE);
        Vector3D intersect = Plane.intersection(xPlane, yPlane, GROUND);
        Vector2D targetXY = new Vector2D(intersect.getX(), intersect.getY());

        SmartDashboard.putNumber("TX", targetXY.getX());
        SmartDashboard.putNumber("TY", targetXY.getY());

        if (targetXY.getNorm() == 0.0) return new Translation2d(0.0, 0.0);
        targetXY.scalarMultiply((targetXY.getNorm() - 0.33) / targetXY.getNorm());
        
        return new Translation2d(targetXY.getX(), targetXY.getY());
    }
}
