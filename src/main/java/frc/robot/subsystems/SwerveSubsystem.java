package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.SwerveDriveConstants;
import frc.robot.constants.MotorReverse.Reverse;
import frc.robot.Robot;
import frc.robot.constants.DeviceId.Encoder;
import frc.robot.constants.DeviceId.Swerve;
import frc.robot.constants.SwerveDriveConstants.AutoConstants;
import frc.robot.constants.SwerveDriveConstants.EncoderOffset;
import frc.robot.constants.SwerveDriveConstants.SwerveConstants;
import frc.robot.lib.math.MathHelper;

public class SwerveSubsystem extends SubsystemBase {
    private final SwerveModule frontLeft;
    private final SwerveModule frontRight;
    private final SwerveModule backLeft;
    private final SwerveModule backRight;
    private final AHRS gyro;
    private static SwerveDriveOdometry odometry;
    private final PIDController drivePIDController = new PIDController(
            3.0, 0.0, 0.0,0.01); // TODO re-tune PID
    private final PIDController steerPIDController = new PIDController(
            2.5, 0.08, 0.0,0.01); // TODO re-tune PID
    private final PIDController noteSteerPIDController = new PIDController(
            2.0, 0.08, 0.0,0.01);
    private final Field2d field = new Field2d();

    public SwerveSubsystem() {
        this.frontLeft = new SwerveModule(
            Swerve.frontLeftDrive.get(),
            Swerve.frontLeftTurn.get(),
            Encoder.frontLeft.get(),
            Reverse.frontLeftDrive.get(),
            Reverse.frontLeftTurn.get(),
            EncoderOffset.FRONT_LEFT,
            "frontLeft"
        );
        this.frontRight = new SwerveModule(
            Swerve.frontRightDrive.get(),
            Swerve.frontRightTurn.get(),
            Encoder.frontRight.get(),
            Reverse.frontRightDrive.get(),
            Reverse.frontRightTurn.get(),
            EncoderOffset.FRONT_RIGHT,
            "frontRight"
        );
        this.backLeft = new SwerveModule(
            Swerve.backLeftDrive.get(),
            Swerve.backLeftTurn.get(),
            Encoder.backLeft.get(),
            Reverse.backLeftDrive.get(),
            Reverse.backLeftTurn.get(),
            EncoderOffset.BACK_LEFT,
            "backLeft"
        );
        this.backRight = new SwerveModule(
            Swerve.backRightDrive.get(),
            Swerve.backRightTurn.get(),
            Encoder.backRight.get(),
            Reverse.backRightDrive.get(),
            Reverse.backRightTurn.get(),
            EncoderOffset.BACK_RIGHT,
            "backRight"
        );
        this.gyro = new AHRS(SPI.Port.kMXP);
        odometry = new SwerveDriveOdometry(
            SwerveDriveConstants.swerveDriveKinematics, this.gyro.getRotation2d(), this.getModulePosition()
        );
        this.wait(1000);
        this.gyro.reset();

        AutoBuilder.configureHolonomic(
            SwerveSubsystem::getPose, 
            this::resetPose,
            this::getSpeeds,
            this::autoDrive, 
            new HolonomicPathFollowerConfig(
                new PIDConstants(5.0, 0.05, 0.0),
                new PIDConstants(5.0, 0.0, 0.0),
                AutoConstants.PHYSICAL_MAX_SPEED_METERS_PER_SECOND,
                0.637808,
                new ReplanningConfig()
            ),
            () -> {
                if (DriverStation.getAlliance().isPresent()) {
                    return DriverStation.getAlliance().get() == DriverStation.Alliance.Red;
                }
                return false;
            },
            this
        );
        SmartDashboard.putData("Field", field);
    }

    @Override
    public void periodic() {
        odometry.update(this.gyro.getRotation2d(), getModulePosition());
        field.setRobotPose(getPose());
    }

    public void driveSwerve(double xSpeed, double ySpeed, double rotation, boolean field) {
        SwerveModuleState[] state = SwerveDriveConstants.swerveDriveKinematics.toSwerveModuleStates(field ? 
            ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotation, this.gyro.getRotation2d()) :
            new ChassisSpeeds(xSpeed, ySpeed, rotation)
        );
        this.setModuleState(state);
    }

    public void situateRobot(Translation2d vector, double angle, boolean fieldOriented, boolean isNoteTracking) {
        double speed = this.drivePIDController.calculate(0.0, vector.getNorm());
        double rotation = isNoteTracking
            ? this.noteSteerPIDController.calculate(0.0, angle)
            : this.steerPIDController.calculate(0.0, angle);

        speed = MathHelper.applyMax(speed, AutoConstants.PHYSICAL_MAX_SPEED_METERS_PER_SECOND);
        rotation = MathHelper.applyMax(rotation, AutoConstants.PHYSICAL_MAX_ACCELERATION_METERS_PER_SECONE);

        final double xSpeed = (vector.getNorm() == 0) ? 0 : (speed * vector.getX() / vector.getNorm()
                * ((Robot.isBlueAlliance() || !fieldOriented) ? 1.0 : -1.0));
        final double ySpeed = (vector.getNorm() == 0) ? 0 : (speed * vector.getY() / vector.getNorm()
                * ((Robot.isBlueAlliance() || !fieldOriented) ? 1.0 : -1.0));

        this.driveSwerve(xSpeed, ySpeed, rotation, fieldOriented);
    }

    public void autoDrive(ChassisSpeeds relativeSpeed) {
        ChassisSpeeds targetSpeed = ChassisSpeeds.discretize(relativeSpeed, 0.02);
        SwerveModuleState state[] = SwerveDriveConstants.swerveDriveKinematics.toSwerveModuleStates(targetSpeed);
        this.setModuleState(state);
    }

    public void setModuleState(SwerveModuleState[] states) {
        SwerveDriveKinematics.desaturateWheelSpeeds(states, SwerveConstants.MAX_SPEED);
        this.frontLeft.setDesiredState(states[0]);
        this.frontRight.setDesiredState(states[1]);
        this.backLeft.setDesiredState(states[2]);
        this.backRight.setDesiredState(states[3]);
    }

    public SwerveModulePosition[] getModulePosition() {
        return new SwerveModulePosition[] {
            this.frontLeft.getPosition(),
            this.frontRight.getPosition(),
            this.backLeft.getPosition(),
            this.backRight.getPosition()
        };
    }

    public SwerveModuleState[] getModuleStates() {
        return new SwerveModuleState[] {
            this.frontLeft.getState(),
            this.frontRight.getState(),
            this.backLeft.getState(),
            this.backRight.getState()
        };
    }

    public static Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void resetPose(Pose2d pose) {
        odometry.resetPosition(this.gyro.getRotation2d(), this.getModulePosition(), pose);
    }

    public ChassisSpeeds getSpeeds() {
        return SwerveDriveConstants.swerveDriveKinematics.toChassisSpeeds(this.getModuleStates());
    }

    public Rotation2d getRotation() {
        return this.gyro.getRotation2d();
    }

    public void resetGyro() {
        this.gyro.reset();
    }

    public void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopModules() {
        this.frontLeft.stop();
        this.frontRight.stop();
        this.backLeft.stop();
        this.backRight.stop();
    }
}