package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DeviceId.Controller;
import frc.robot.lib.helpers.IDashboardProvider;
import frc.robot.lib.motors.ModuleTalon;

public class ShooterSubsystem extends SubsystemBase implements IDashboardProvider {
    private final ModuleTalon leftShooter;
    private final ModuleTalon rightShooter;
    private final PIDController pid;

    public ShooterSubsystem() {
        this.registerDashboard();
        this.leftShooter = new ModuleTalon(Controller.leftShooter.get(), true, false);
        this.rightShooter = new ModuleTalon(Controller.rightShooter.get(), true, false);
        this.pid = new PIDController(0.2, 3.5, 0.0);
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

    @Override
    public void putDashboard() {
        SmartDashboard.putNumber("Left Velocity", this.leftShooter.getVelocity().getValue());
        SmartDashboard.putNumber("Right Velocity", this.rightShooter.getVelocity().getValue());
    }
}
