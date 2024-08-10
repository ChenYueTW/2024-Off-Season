package frc.robot.joystick;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
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
        return this.getAButton();
    }

    public boolean isAmpOutput() {
        return this.getXButton();
    }

    public Trigger shootHigh() {
        return new Trigger(this::getStartButton);
    }

    public Trigger autoShoot() {
        return new Trigger(this::getBackButton);
    }

    public double getShooterDirection() {
        return MathUtil.applyDeadband(this.getLeftY(), SwerveDriveConstants.DEAD_BAND);
    }

    public double getElevatorDirection() {
        return MathUtil.applyDeadband(this.getRightY(), SwerveDriveConstants.DEAD_BAND);
    }
}
