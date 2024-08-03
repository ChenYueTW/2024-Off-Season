package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.SwerveDriveConstants.ControllerConstants;
import frc.robot.constants.DeviceId.Controller;
import frc.robot.constants.DeviceId.Encoder;
import frc.robot.lib.helpers.IDashboardProvider;
import frc.robot.lib.motors.ModuleTalon;

public class ShooterArmSubsystem extends SubsystemBase implements IDashboardProvider {
    private final ModuleTalon shooterArm;
    private final DutyCycleEncoder encoder;
    private final PIDController angleAdjustmentPid;

    public ShooterArmSubsystem() {
        this.registerDashboard();
        this.shooterArm = new ModuleTalon(Controller.shooterArm.get(), false, true);
        this.encoder = new DutyCycleEncoder(Encoder.shooterArm.get());
        this.angleAdjustmentPid = new PIDController(0.1, 0.05, 0.0);
    }

    public void toGoalDegrees(double degrees) {
        if (degrees == 0) return;
        double speed = this.angleAdjustmentPid.calculate(this.getPosition(), degrees);
        this.execute(-speed);
    }

    public void execute(double speed) {
        if (this.getPosition() < ControllerConstants.SHOOTER_ARM_DEG_DOWN_LIMIT && speed < 0) {
            this.shooterArm.set(speed);
        } else if (this.getPosition() > ControllerConstants.SHOOTER_ARM_DEG_UP_LIMIT && speed > 0) {
            this.shooterArm.set(speed);
        } else if (this.getPosition() >= ControllerConstants.SHOOTER_ARM_DEG_DOWN_LIMIT && this.getPosition() <= ControllerConstants.SHOOTER_ARM_DEG_UP_LIMIT) {
            this.shooterArm.set(speed);
        } else {
            this.shooterArm.stopMotor();
        }
        // this.shooterArm.set(speed);
    }

    public void stopShooterArm() {
        this.shooterArm.stopMotor();
    }

    public double getPosition() {
        return Units.rotationsToDegrees(this.encoder.getAbsolutePosition());
    }

    @Override
    public void putDashboard() {
        SmartDashboard.putNumber("Shooter Arm Deg", this.getPosition());
    }

    @Override
    public void putDashboardOnce() {}
}
