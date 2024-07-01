package frc.robot.Constants;

public enum Reverse {
    frontLeftDrive(true), frontLeftTurn(true),
    frontRightDrive(false), frontRightTurn(true),
    backLeftDrive(true), backLeftTurn(true),
    backRightDrive(false), backRightTurn(true);

    private final boolean reverse;

    Reverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean get() {
        return this.reverse;
    }
}
