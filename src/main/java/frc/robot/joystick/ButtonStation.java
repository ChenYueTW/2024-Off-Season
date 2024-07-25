package frc.robot.joystick;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class ButtonStation extends XboxController {
    public ButtonStation(int port) {
        super(port);
    }
    public static final int BUTTON_STATION_PORT = 2;

    public Trigger autoShoot() {
        return new Trigger(this::getStartButton);
    }
    public boolean conveyNote() {
        return this.getRawButtonPressed(2);
    }
}
