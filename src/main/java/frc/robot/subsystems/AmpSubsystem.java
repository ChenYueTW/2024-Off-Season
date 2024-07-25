package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DeviceId.Controller;

public class AmpSubsystem extends SubsystemBase {
    private final CANSparkMax ampMotor;

    @SuppressWarnings("removal")
    public AmpSubsystem() {
        this.ampMotor = new CANSparkMax(Controller.amp.get(), MotorType.kBrushless);
    }

    public void execute(double speed) {
        this.ampMotor.set(speed);
    }

    public void stopAmp() {
        this.ampMotor.stopMotor();
    }
}
