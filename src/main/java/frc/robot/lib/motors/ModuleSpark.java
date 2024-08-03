package frc.robot.lib.motors;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

public class ModuleSpark extends CANSparkMax {
    public ModuleSpark(int deviceId, boolean reverse, boolean brake) {
        super(deviceId, CANSparkLowLevel.MotorType.kBrushless);
        this.setInverted(reverse);
        this.setIdleMode(brake ? IdleMode.kBrake : IdleMode.kCoast);
    }
}
