package frc.robot.joystick;

import edu.wpi.first.wpilibj.XboxController;

public class Driver extends XboxController {
    public Driver(int port) {
        super(port);
    }
    public static final int DRIVER_PORT = 0;
}
