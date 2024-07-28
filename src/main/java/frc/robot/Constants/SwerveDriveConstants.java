package frc.robot.constants;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public class SwerveDriveConstants {
	public static final class SwerveConstants {
		public static final double TRACK_WIDTH = 0.61;
		public static final double WHEEL_BASE = 0.61;
		public static final double WHEEL_RADIUS = 0.0508;
		public static final double DRIVE_GEAR_RATIO = 7.0 / 57.0;
		public static final double TURN_GEAR_RATIO = 7.0 / 150.0;

		public static final double MAX_SPEED = 3.0;
		public static final double MAX_ANGULAR_SPEED = 2.0;
		public static final double MAX_ACCELERATION_SPEED = 2.0;
		public static final double MAX_ACCELERATION_ANGULAR_SPEED = Math.PI;
	}

	public static final class ControllerConstants {
		public static final double INTAKE_SPEED = 0.35;
		public static final double AMP_SPEED = 0.3;
		public static final double ELEVATOR_ROT_UP_LIMIT = 10.0;
		public static final double ELEVATOR_ROT_DOWN_LIMIT = 0.0;
		public static final double SHOOTER_ARM_DEG_UP_LIMIT = 238.76669396916733;
		public static final double SHOOTER_ARM_DEG_DOWN_LIMIT = 71.62473779061845;
	}

	public static final class EncoderOffset {
		public static final double FRONT_LEFT = 0.002685546875;
		public static final double FRONT_RIGHT = -0.000732421875;
		public static final double BACK_LEFT = 0.004150390625;
		public static final double BACK_RIGHT = 0.00341796875;
	}

	public static final SwerveDriveKinematics swerveDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(SwerveConstants.WHEEL_BASE / 2, SwerveConstants.TRACK_WIDTH / 2),
        new Translation2d(SwerveConstants.WHEEL_BASE / 2, -SwerveConstants.TRACK_WIDTH / 2),
        new Translation2d(-SwerveConstants.WHEEL_BASE / 2, SwerveConstants.TRACK_WIDTH / 2),
        new Translation2d(-SwerveConstants.WHEEL_BASE / 2, -SwerveConstants.TRACK_WIDTH / 2)
    );

	public static final class AutoConstants {
		public static final double PHYSICAL_MAX_SPEED_METERS_PER_SECOND = 3.9;
		public static final double PHYSICAL_MAX_ACCELERATION_METERS_PER_SECONE = 3.0;
	}

    public static final double DEAD_BAND = 0.05;
	public static final boolean gyroField = true;
}
