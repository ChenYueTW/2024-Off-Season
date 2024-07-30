package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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

    public Command execute() {
        return new ParallelRaceGroup(
            Commands.runEnd(() -> {this.execute(0.3);}, this::stopAmp, this),
            new WaitCommand(1.0)
        );
    }

    public void stopAmp() {
        this.ampMotor.stopMotor();
    }
}
