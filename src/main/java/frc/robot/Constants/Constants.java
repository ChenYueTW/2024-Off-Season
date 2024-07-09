package frc.robot.constants;

import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public final class Constants {
	public static final class SwerveConstants {
		public static final double TRACK_WIDTH = 0.66;
		public static final double WHEEL_BASE = 0.66;
		public static final double WHEEL_RADIUS = 0.0508;
		
		public static final double PHYSICAL_MAX_SPEED_METERS_PER_SECOND = 3.0;
		public static final double PHYSICAL_MAX_ACCELERATION_METERS_PER_SECONE = 3.0;
		public static final double DRIVE_GEAR_RATIO = 1.0 / 8.14;
		public static final double TURN_GEAR_RATIO = 7.0 / 150.0;

		public static final double DRIVE_VELOCITY_CONVERSION_FACTOR = (1.0 / DRIVE_GEAR_RATIO / 2048) * WHEEL_RADIUS * Math.PI * 10;
		public static final double DRIVE_POSITION_CONVERSION_FACTOR = (1.0 / DRIVE_GEAR_RATIO / 2048) * WHEEL_RADIUS * Math.PI;
	}

	public static final class ControllerConstants { // TODO
		public static final double INTAKE_SPEED = 0.3;
		public static final double AMP_SPEED = 0.3;
		public static final double SHOOTER_SPEED = 80.0;
		public static final double ELEVATOR_GOAL_DEG = 0.0;
		public static final double ELEVATOR_DEG_UP_LIMIT = 0.0;
		public static final double ELEVATOR_DEG_DOWN_LIMIT = 0.0;
		public static final double SHOOTER_ARM_DEG_UP_LIMIT = 0.0;
		public static final double SHOOTER_ARM_DEG_DOWN_LIMIT = 0.0;
	}

	public static final class EncoderOffset {
		public static final double FRONT_LEFT = 0.002685546875;
		public static final double FRONT_RIGHT = -0.000732421875;
		public static final double BACK_LEFT = 0.004150390625;
		public static final double BACK_RIGHT = 0.00341796875;
	}

	public static final SwerveDriveKinematics swerveDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(Constants.SwerveConstants.WHEEL_BASE / 2, Constants.SwerveConstants.TRACK_WIDTH / 2),
        new Translation2d(Constants.SwerveConstants.WHEEL_BASE / 2, -Constants.SwerveConstants.TRACK_WIDTH / 2),
        new Translation2d(-Constants.SwerveConstants.WHEEL_BASE / 2, Constants.SwerveConstants.TRACK_WIDTH / 2),
        new Translation2d(-Constants.SwerveConstants.WHEEL_BASE / 2, -Constants.SwerveConstants.TRACK_WIDTH / 2)
    );

	public static final class AutoConstants {
		public static final double PHYSICAL_MAX_SPEED_METERS_PER_SECOND = 4.2;
		public static final double PHYSICAL_MAX_ACCELERATION_METERS_PER_SECONE = 0.5;
	}

	public static final class LLConstants {
		public static final Vector3D CAMERA_POSE = new Vector3D(0, 0, 0);
		public static final Vector3D CENTRAL_SIGHT = new Vector3D(0, 0, 0);
		public static final Vector3D CAM_X_AXIS = new Vector3D(0, 0, 0);
		public static final Vector3D CAM_Y_AXIS = new Vector3D(0, 0, 0);
		@SuppressWarnings("deprecation")
		public static final Plane GROUND = new Plane(new Vector3D(0.0, 0.0, 1.0));
	}

    public static final double DEAD_BAND = 0.05;
	public static final double MAX_SPEED = 0.8;
	public static final double MAX_ANGULAR_SPEED = 1.3;
	public static final boolean gyroField = true;
}
