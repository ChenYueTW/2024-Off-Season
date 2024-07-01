package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeviceId.Controller;
import frc.robot.lib.Motor.ModuleTalon;

public class ShooterSubsystem extends SubsystemBase {
    private final ModuleTalon leftShooter;
    private final ModuleTalon rightShooter;
    private final PIDController pid;

    public ShooterSubsystem() {
        this.leftShooter = new ModuleTalon(Controller.leftShooter.get(), false, false);
        this.rightShooter = new ModuleTalon(Controller.rightShooter.get(), false, false);
        this.pid = new PIDController(0.0, 0.0, 0.0);
    }

    public void execute(double goalSpeed) { // Rotation Per Second
        double leftSpeed = this.pid.calculate(
            this.leftShooter.getVelocity().getValue(), goalSpeed);
        double rightSpeed = this.pid.calculate(
            this.rightShooter.getVelocity().getValue(), goalSpeed);

        this.leftShooter.set(leftSpeed);
        this.rightShooter.set(rightSpeed);
    }

    public void stopShooter() {
        this.leftShooter.stopMotor();
        this.rightShooter.stopMotor();
    }
}
