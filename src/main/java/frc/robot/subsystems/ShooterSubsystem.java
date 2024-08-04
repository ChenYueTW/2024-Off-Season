package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DeviceId.Controller;
import frc.robot.lib.helpers.IDashboardProvider;
import frc.robot.lib.motors.ModuleTalon;

public class ShooterSubsystem extends SubsystemBase implements IDashboardProvider {
    private final ModuleTalon leftShooter;
    private final ModuleTalon rightShooter;
    private final PIDController leftPid;
    private final PIDController rightPid;

    public ShooterSubsystem() {
        this.registerDashboard();
        this.leftShooter = new ModuleTalon(Controller.leftShooter.get(), false, false);
        this.rightShooter = new ModuleTalon(Controller.rightShooter.get(), true, false);
        this.leftPid = new PIDController(0.5, 0.25, 0.0003);
        this.rightPid = new PIDController(0.5, 0.28, 0.0003);
    }
    
    public void execute() { // Rotation Per Second
        double leftSpeed = this.leftPid.calculate(
            this.leftShooter.getVelocity().getValue(), 85.0);
        double rightSpeed = this.rightPid.calculate(
            this.rightShooter.getVelocity().getValue(), 65.0);

        this.leftShooter.set(leftSpeed);
        this.rightShooter.set(rightSpeed);
    }

    public boolean canShoot() {
        return this.leftShooter.getVelocity().getValue() >= 80.0 && this.rightShooter.getVelocity().getValue() >= 60.0;
    }

    public Command autoExecute() {
        return Commands.runEnd(this::execute, this::stopShooter, this);
    }

    public void toElevator() {
        this.leftShooter.set(0.08);
        this.rightShooter.set(0.08);
    }

    public void stopShooter() {
        this.leftShooter.stopMotor();
        this.rightShooter.stopMotor();
    }

    @Override
    public void putDashboard() {
        SmartDashboard.putNumber("Left", this.leftShooter.getVelocity().getValue());
        SmartDashboard.putNumber("Right", this.rightShooter.getVelocity().getValue());
        SmartDashboard.putBoolean("CanShoot", this.canShoot());
    }

    @Override
    public void putDashboardOnce() {}
}
