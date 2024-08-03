package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.constants.DeviceId.Controller;
import frc.robot.constants.SwerveDriveConstants.ControllerConstants;
import frc.robot.lib.helpers.IDashboardProvider;
import frc.robot.lib.motors.ModuleSpark;
import frc.robot.lib.motors.ModuleTalon;
import com.revrobotics.CANSparkMax;

public class IntakeSubsystem extends SubsystemBase implements IDashboardProvider {
    private final ModuleTalon leftIntake;
    private final ModuleTalon rightIntake;
    private final CANSparkMax conveyorBelt;

    public IntakeSubsystem() {
        this.registerDashboard();
        this.leftIntake = new ModuleTalon(Controller.leftIntake.get(), false, false);
        this.rightIntake = new ModuleTalon(Controller.rightIntake.get(), false, false);
        this.conveyorBelt = new ModuleSpark(Controller.conveyorBelt.get(), true, false);
    }

    public void execute(double speed) {
        this.leftIntake.set(speed);
        this.rightIntake.set(speed);
        this.conveyorBelt.set(speed);
    }

    public Command releaseNote() {
        return Commands.runEnd(() -> {this.execute(ControllerConstants.INTAKE_SPEED);}, this::stopIntake, this);
    }

    public void stopIntake() {
        this.leftIntake.stopMotor();
        this.rightIntake.stopMotor();
        this.conveyorBelt.stopMotor();
    }

    @Override
    public void putDashboard() {
        SmartDashboard.putNumber("LeftIntake", this.leftIntake.getVelocity().getValue());
        SmartDashboard.putNumber("RightInakte", this.rightIntake.getVelocity().getValue());
        SmartDashboard.putNumber("Convey", this.conveyorBelt.getEncoder().getVelocity() * 60.0);
    }

    @Override
    public void putDashboardOnce() {}
}
