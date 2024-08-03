package frc.robot.joystick;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.constants.SwerveDriveConstants;
import frc.robot.constants.SwerveDriveConstants.SwerveConstants;

public class Driver extends XboxController {
    private final SlewRateLimiter xSpeed = new SlewRateLimiter(SwerveConstants.MAX_ACCELERATION_SPEED);
    private final SlewRateLimiter ySpeed = new SlewRateLimiter(SwerveConstants.MAX_ACCELERATION_SPEED);
    private final SlewRateLimiter rotationSpeed = new SlewRateLimiter(SwerveConstants.MAX_ACCELERATION_ANGULAR_SPEED);

    public Driver(int port) {
        super(port);
    }
    public static final int DRIVER_PORT = 0;

    public double getDesiredXSpeed() {
        double speed = -MathUtil.applyDeadband(this.getLeftY(), SwerveDriveConstants.DEAD_BAND) * this.getBrake();
        return this.xSpeed.calculate(speed * SwerveConstants.MAX_SPEED);
    }

    public double getDesiredYSpeed() {
        double speed = -MathUtil.applyDeadband(this.getLeftX(), SwerveDriveConstants.DEAD_BAND) * this.getBrake();
        return this.ySpeed.calculate(speed * SwerveConstants.MAX_SPEED);
    }

    public double getDesiredRotationSpeed() {
        double speed = -MathUtil.applyDeadband(this.getRightX(), SwerveDriveConstants.DEAD_BAND) * this.getBrake();
        return this.rotationSpeed.calculate(speed * SwerveConstants.MAX_ANGULAR_SPEED);
    }

    public double getBrake() {
        return 1.0 - (this.getRightTriggerAxis() * 0.8);
    }

    public Trigger autoTurn() {
        return new Trigger(this::getAButton);
    }

    public Trigger autoAmp() {
        return new Trigger(this::getXButton);
    }
}
