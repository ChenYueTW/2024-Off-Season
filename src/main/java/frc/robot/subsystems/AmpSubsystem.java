package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DeviceId.Controller;
import frc.robot.lib.motors.ModuleTalon;

public class AmpSubsystem extends SubsystemBase {
    private final ModuleTalon ampMotor;

    public AmpSubsystem() {
        this.ampMotor = new ModuleTalon(Controller.amp.get(), false, true);
    }

    public void execute(double speed) {
        this.ampMotor.set(speed);
    }

    public void stopAmp() {
        this.ampMotor.stopMotor();
    }
}
