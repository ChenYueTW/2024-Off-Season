package frc.robot.lib.motors;

import com.revrobotics.CANSparkMax;
public class ModuleNEO extends CANSparkMax{
    public ModuleNEO(int deviceId, MotorType type) {
        super(deviceId, type);
    }
}
