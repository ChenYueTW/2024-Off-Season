package frc.robot.lib;

import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants.Constants.LLConstants;

public class BetterLimelight {
    private final NetworkTable table;
    private final NetworkTableEntry tx;
    private final NetworkTableEntry ty;
    private final NetworkTableEntry tid;

    public BetterLimelight(String key) {
        this.table = NetworkTableInstance.getDefault().getTable(key);
        this.tx  = this.table.getEntry("tx");
        this.ty = this.table.getEntry("ty");
        this.tid = this.table.getEntry("tid");
    }

    @SuppressWarnings("deprecation")
    public Translation2d getPositionVector() {
        Rotation xRot = new Rotation(LLConstants.CAM_Y_AXIS, this.tx.getDouble(0.0), RotationConvention.VECTOR_OPERATOR);
        Rotation yRot = new Rotation(LLConstants.CAM_X_AXIS, this.ty.getDouble(0.0), RotationConvention.VECTOR_OPERATOR);
        Vector3D xVector = xRot.applyTo(LLConstants.CENTRAL_SIGHT);
        Vector3D yVector = yRot.applyTo(LLConstants.CENTRAL_SIGHT);
        Plane xPlane = new Plane(LLConstants.CAMERA_POSE, LLConstants.CAMERA_POSE.add(xVector), LLConstants.CAMERA_POSE.add(LLConstants.CAM_Y_AXIS));
        Plane yPlane = new Plane(LLConstants.CAMERA_POSE, LLConstants.CAMERA_POSE.add(yVector), LLConstants.CAMERA_POSE.add(LLConstants.CAM_X_AXIS));
        Vector3D intersect = Plane.intersection(xPlane, yPlane, LLConstants.GROUND);
        Vector2D targetXY = new Vector2D(intersect.getX(), intersect.getY());

        return targetXY.getNorm() == 0 ? new Translation2d(0.0, 0.0) : new Translation2d(targetXY.getX(), targetXY.getY());
    }

    public double getAprilTagId() {
        return this.tid.getDouble(0.0);
    }
}
