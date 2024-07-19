package frc.robot.lib.math;

import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.SwerveDriveConstants.LLConstants;

public class NotePosEstimator {
    @SuppressWarnings("deprecation")
    public static Translation2d getPositionVector(double tx, double ty) {
        Rotation xRot = new Rotation(LLConstants.CAM_Y_AXIS, -tx, RotationConvention.VECTOR_OPERATOR);
        Rotation yRot = new Rotation(LLConstants.CAM_X_AXIS, ty, RotationConvention.VECTOR_OPERATOR);
        Vector3D xVector = xRot.applyTo(LLConstants.CENTRAL_SIGHT);
        Vector3D yVector = yRot.applyTo(LLConstants.CENTRAL_SIGHT);
        Plane xPlane = new Plane(LLConstants.CAMERA_POSE, LLConstants.CAMERA_POSE.add(xVector), LLConstants.CAMERA_POSE.add(LLConstants.CAM_Y_AXIS), LLConstants.TOLERANCE);
        Plane yPlane = new Plane(LLConstants.CAMERA_POSE, LLConstants.CAMERA_POSE.add(yVector), LLConstants.CAMERA_POSE.add(LLConstants.CAM_X_AXIS), LLConstants.TOLERANCE);
        Vector3D intersect = Plane.intersection(xPlane, yPlane, LLConstants.GROUND);
        Vector2D targetXY = new Vector2D(intersect.getX(), intersect.getY());

        if (targetXY.getNorm() == 0) return new Translation2d(0.0, 0.0);
        SmartDashboard.putNumberArray("xVec", xVector.toArray());
        SmartDashboard.putNumberArray("yVec", yVector.toArray());
        SmartDashboard.putNumberArray("intersect", intersect.toArray());
        // targetXY.scalarMultiply((targetXY.getNorm() - 0.415) / targetXY.getNorm());
        return new Translation2d(targetXY.getX(), targetXY.getY());
    }
}
