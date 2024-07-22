package frc.robot.lib.motors;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.util.Units;
import frc.robot.constants.SwerveDriveConstants.SwerveConstants;

public class SwerveTalon extends TalonFX {
    double gearRatio;

    public SwerveTalon(int motorPort, boolean reverse, double gearRatio) {
        super(motorPort);
        this.setInverted(reverse);
        this.setNeutralMode(NeutralModeValue.Brake);
        this.gearRatio = gearRatio;
    }

    public void resetPosition(double getAbsolutePosition) {
        double rotaion = Units.degreesToRotations(getAbsolutePosition);
        this.setPosition(rotaion * (2048.0 / this.gearRatio));
    }

    public double getMotorVelocity() {
        return this.getVelocity().getValue() * this.gearRatio * 2.0 * SwerveConstants.WHEEL_RADIUS * Math.PI;
    }
    
    public double getMotorPosition() {
        return this.getPosition().getValue() * this.gearRatio * 2.0 * SwerveConstants.WHEEL_RADIUS * Math.PI;
    }

    public double getTurnPosition() {
        double degrees = Units.rotationsToDegrees(this.getPosition().getValue());
        return degrees / (2048.0 / this.gearRatio);
    }

    public double getAbsolutePosition(double absolutePosition) {
        double degrees = Units.rotationsToDegrees(absolutePosition);
        return degrees / (2048.0 / this.gearRatio);
    }
}
