package frc.robot.joystick;

import edu.wpi.first.wpilibj.Joystick;

public class ButtonStation extends Joystick {
    public ButtonStation(int port) {
        super(port);
    }
    public static final int BUTTON_STATION_PORT = 2;

    public boolean getAButton() {
        return this.getRawButtonPressed(1);
    }
}
