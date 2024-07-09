package frc.robot.lib.aprilTagFeild;

import edu.wpi.first.math.geometry.Translation2d;

public enum BlueAlliance {
    I(593.68, 9.68),
    II(637.21, 34.79),
    III(652.73, 196.17),
    IV(652.73, 196.17),
    V(578.77, 323.0),
    VI(72.5, 323.0),
    VII(-1.5, 218.42),
    VIII(-1.5, 196.17),
    IX(14.02, 34.79),
    X(57.54, 9.68),
    XI(468.69, 146.19),
    XII(468.69, 177.1),
    XIII(441.74, 161.62),
    XIV(209.48, 161.62),
    XV(182.73, 177.1),
    XVI(182.73, 146.19);

    private final double x;
    private final double y;

    BlueAlliance(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private Translation2d get() {
        return new Translation2d(this.x, this.y);
    }

    public static Translation2d getById(double id) {
        return values()[(int) (id - 1)].get();
    }
}
