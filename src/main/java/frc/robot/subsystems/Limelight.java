package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.lib.helpers.IDashboardProvider;
import frc.robot.lib.math.NotePosEstimator;

public class Limelight extends SubsystemBase implements IDashboardProvider {
    DoubleSubscriber primaryAprilTagId = NetworkTableInstance.getDefault().getTable("limelight")
            .getDoubleTopic("tid").subscribe(-1);
    DoubleSubscriber noteValid = NetworkTableInstance.getDefault().getTable("limelight-note")
            .getDoubleTopic("tv").subscribe(-1);
    DoubleSubscriber noteTy = NetworkTableInstance.getDefault().getTable("limelight-note")
            .getDoubleTopic("ty").subscribe(-1);
    DoubleSubscriber noteTx = NetworkTableInstance.getDefault().getTable("limelight-note")
            .getDoubleTopic("tx").subscribe(-1);

    public Limelight() {
        this.registerDashboard();
    }

    public Translation2d getNotePositionVector() {
        if (this.noNoteTarget()) return new Translation2d(0.0, 0.0);
        return NotePosEstimator.getPositionVector(this.noteTx.get(), this.noteTy.get());
    }

    public double getNoteGroundDistance() {
        return this.noNoteTarget() ? -1.0 : this.getNotePositionVector().getNorm();
    }

    public boolean noNoteTarget() {
        return this.noteValid.get() == 0.0;
    }

    @Override
    public void putDashboard() {
        // SmartDashboard.putNumber("Note Distance X", this.getNotePositionVector().getX());
        // SmartDashboard.putNumber("Note Distance Y", this.getNotePositionVector().getY());
        SmartDashboard.putNumber("Note Distance", this.getNoteGroundDistance());
        // SmartDashboard.putBoolean("is", this.noNoteTarget());
    }
}
