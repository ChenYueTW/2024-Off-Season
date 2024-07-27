package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.SwerveDriveConstants.SwerveConstants;
import frc.robot.lib.helpers.IDashboardProvider;
import frc.robot.lib.math.MathHelper;
import frc.robot.lib.motors.SwerveTalon;

public class SwerveModule implements IDashboardProvider {
    private final SwerveTalon driveMotor;
    private final SwerveTalon turnMotor;

    private final CANcoder turnEncoder;

    private final PIDController drivePidController;
    private final PIDController turnPidController;

    private final String motorName;
    private double driveOutput;
    private double turnOutput;

    private final double turningEncoderOffset;

    public SwerveModule(
        int driveMotorPort, int turnMotorPort, int turnEncoderPort,
        boolean driveMotorReverse, boolean turnMotorReverse,
        double turnEncoderOffset, String motorName
    ){
        this.registerDashboard();

        this.driveMotor = new SwerveTalon(driveMotorPort, driveMotorReverse, SwerveConstants.DRIVE_GEAR_RATIO);
        this.turnMotor = new SwerveTalon(turnMotorPort, turnMotorReverse, SwerveConstants.TURN_GEAR_RATIO);

        this.turnEncoder = new CANcoder(turnEncoderPort);

        this.drivePidController = new PIDController(0.15, 0.45, 0.0); // TODO
        this.turnPidController = new PIDController(0.009, 0.0, 0.0);
        this.turnPidController.enableContinuousInput(-180, 180);

        this.motorName = motorName;
        this.turningEncoderOffset = turnEncoderOffset;
        this.resetEncoders();
    }

    public void resetEncoders() {
        this.driveMotor.setPosition(0);
        this.turnMotor.setPosition(this.getTurnPosition());
    }

    public double getTurnPosition() {
        double value = Units.rotationsToDegrees(this.turnEncoder.getAbsolutePosition().getValue()) - this.turningEncoderOffset;
        value %= 360.0;
        return value > 180 ? value - 360 : value;
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
            this.driveMotor.getMotorVelocity(),
            Rotation2d.fromDegrees(this.getTurnPosition())
        );
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            this.driveMotor.getMotorPosition(),
            Rotation2d.fromDegrees(this.getTurnPosition())
        );
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        if (Math.abs(desiredState.speedMetersPerSecond) < 0.001) {
            this.stop();
            return;
        }
        SwerveModuleState state = SwerveModuleState.optimize(desiredState, this.getState().angle);
        double speedMetersPerSecond = MathHelper.applyMax(state.speedMetersPerSecond, SwerveConstants.PHYSICAL_MAX_SPEED_METERS_PER_SECOND);

        this.driveOutput = this.drivePidController.calculate(
            this.getState().speedMetersPerSecond, speedMetersPerSecond
        );
        this.turnOutput = this.turnPidController.calculate(
            this.getTurnPosition(), state.angle.getDegrees()
        );

        this.driveMotor.set(this.driveOutput);
        this.turnMotor.set(this.turnOutput);
    }

    @Override
    public void putDashboard() {
        SmartDashboard.putNumber(this.motorName + " DriveSpeed", this.getState().speedMetersPerSecond);
        SmartDashboard.putNumber(this.motorName + " TurnPos", this.getTurnPosition());
    }

    @Override
    public void putDashboardOnce() {}

    public void stop() {
        this.driveMotor.set(0.0);
        this.turnMotor.set(0.0);
    }
}
