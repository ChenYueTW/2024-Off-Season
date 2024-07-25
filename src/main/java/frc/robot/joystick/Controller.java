package frc.robot.joystick;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.constants.SwerveDriveConstants;

public class Controller extends XboxController {
    public Controller(int port) {
        super(port);
    }
    public static final int CONTROLLER_PORT = 1;

    public boolean isShoot() {
        return this.getLeftBumper();
    }

    public boolean autoAim() {
        return this.getRightBumper();
    }
    
    public boolean isIntake() {
        return this.getBButton();
    }
    
    public boolean isRelease() {
        return this.getYButton();
    }

    public boolean isAmpInput() {
        return this.getXButton();
    }

    public boolean isAmpOutput() {
        return this.getAButton();
    }

    public double getShooterDirection() {
        return MathUtil.applyDeadband(this.getLeftY(), SwerveDriveConstants.DEAD_BAND);
    }

    public double getElevatorDirection() {
        return MathUtil.applyDeadband(this.getRightY(), SwerveDriveConstants.DEAD_BAND);
    }
}
