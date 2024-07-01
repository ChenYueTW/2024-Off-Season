package frc.robot.joystick;

import edu.wpi.first.wpilibj.XboxController;

public class Controller extends XboxController {
    public Controller(int port) {
        super(port);
    }
    public static final int CONTROLLER_PORT = 1;

    public boolean isShoot() {
        return this.getAButton();
    }
    
    public boolean isIntake() {
        return this.getBButton();
    }
    
    public boolean isOuttake() {
        return this.getYButton();
    }
}
