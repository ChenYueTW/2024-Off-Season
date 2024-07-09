package frc.robot.lib.motors;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.util.Units;

public class ModuleTalon extends TalonFX {
    public ModuleTalon(int port, boolean reverse, boolean neutralMode) {
        super(port);
        this.setInverted(reverse);
        this.setNeutralMode(neutralMode ? NeutralModeValue.Brake : NeutralModeValue.Coast);
    }

    public double getDegPosition() {
        return Units.rotationsToDegrees(this.getPosition().getValue());
    }
}
