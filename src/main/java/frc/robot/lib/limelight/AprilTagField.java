package frc.robot.lib.limelight;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public enum AprilTagField {
    I(593.68, 9.68, 53.38, 120.0),
    II(637.21, 34.79, 53.38, 120.0),
    III(652.73, 196.17, 57.13, 180.0),
    IV(652.73, 196.17, 57.13, 180.0),
    V(578.77, 323.0, 53.38, 270.0),
    VI(72.5, 323.0, 53.38, 270.0),
    VII(-1.5, 218.42, 57.13, 0.0),
    VIII(-1.5, 196.17, 57.13, 0.0),
    IX(14.02, 34.79, 53.38, 60.0),
    X(57.54, 9.68, 53.38, 60.0),
    XI(468.69, 146.19, 52.0, 300.0),
    XII(468.69, 177.1, 52.0, 60.0),
    XIII(441.74, 161.62, 52.0, 180.0),
    XIV(209.48, 161.62, 52.0, 0.0),
    XV(182.73, 177.1, 52.0, 120.0),
    XVI(182.73, 146.19, 52.0, 240.0);

    private final double x;
    private final double y;
    private final double z;
    private final double rotation;

    AprilTagField(double x, double y, double z, double rotation) {
        this.x = Units.inchesToMeters(x);
        this.y = Units.inchesToMeters(y);
        this.z = Units.inchesToMeters(z);
        this.rotation = rotation;
    }

    private Translation2d get2d() {
        return new Translation2d(this.x, this.y);
    }

    private Translation3d get3d() {
        return new Translation3d(this.x, this.y, this.z);
    }

    private Rotation2d getRot() {
        return new Rotation2d(this.rotation);
    }

    public static Translation2d get2dById(double id) {
        return values()[(int) (id - 1)].get2d();
    }

    public static Translation3d get3dById(double id) {
        return values()[(int) (id - 1)].get3d();
    }

    public static Rotation2d getRotationById(double id) {
        return values()[(int) (id - 1)].getRot();
    }
}
