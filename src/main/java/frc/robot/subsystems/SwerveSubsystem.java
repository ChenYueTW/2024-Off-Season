package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.constants.Reverse;
import frc.robot.constants.Constants.AutoConstants;
import frc.robot.constants.Constants.EncoderOffset;
import frc.robot.constants.Constants.SwerveConstants;
import frc.robot.constants.DeviceId.Encoder;
import frc.robot.constants.DeviceId.Swerve;

public class SwerveSubsystem extends SubsystemBase {
    private final SwerveModule frontLeft;
    private final SwerveModule frontRight;
    private final SwerveModule backLeft;
    private final SwerveModule backRight;
    private final AHRS gyro;
    private final SwerveDriveOdometry odometry;

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
        this.odometry = new SwerveDriveOdometry(
            Constants.swerveDriveKinematics, this.gyro.getRotation2d(), this.getModulePosition()
        );
        this.wait(1000);
        this.gyro.reset();

        AutoBuilder.configureHolonomic(
            this::getPose, 
            this::resetPose,
            this::getSpeeds,
            this::autoDrive, 
            new HolonomicPathFollowerConfig(
                AutoConstants.PHYSICAL_MAX_SPEED_METERS_PER_SECOND,
                0.3,
                new ReplanningConfig(true, true)
            ),
            () -> {
                if (DriverStation.getAlliance().isPresent()) {
                    return DriverStation.getAlliance().get() == DriverStation.Alliance.Red;
                }
                return false;
            },
            this
        );
    }

    @Override
    public void periodic() {
        this.odometry.update(this.gyro.getRotation2d(), getModulePosition());
    }

    public void driveSwerve(double xSpeed, double ySpeed, double rotation, boolean field) {
        SwerveModuleState[] state = Constants.swerveDriveKinematics.toSwerveModuleStates(field ? 
            ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotation, this.gyro.getRotation2d()) :
            new ChassisSpeeds(xSpeed, ySpeed, rotation)
        );
        this.setModuleState(state);
    }

    public void autoDrive(ChassisSpeeds relativeSpeed) {
        ChassisSpeeds targetSpeed = ChassisSpeeds.discretize(relativeSpeed, 0.02);
        SwerveModuleState state[] = Constants.swerveDriveKinematics.toSwerveModuleStates(targetSpeed);
        this.setModuleState(state);
    }

    public void setModuleState(SwerveModuleState[] states) {
        SwerveDriveKinematics.desaturateWheelSpeeds(states, SwerveConstants.PHYSICAL_MAX_SPEED_METERS_PER_SECOND);
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

    public Pose2d getPose() {
        return this.odometry.getPoseMeters();
    }

    public void resetPose(Pose2d pose) {
        this.odometry.resetPosition(this.gyro.getRotation2d(), this.getModulePosition(), pose);
    }

    public ChassisSpeeds getSpeeds() {
        return Constants.swerveDriveKinematics.toChassisSpeeds(this.getModuleStates());
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