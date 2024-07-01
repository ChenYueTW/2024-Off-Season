package frc.robot.Constants;

public final class DeviceId {
    public enum Swerve {
        frontLeftDrive(2), frontLeftTurn(1),
        backLeftDrive(5), backLeftTurn(6),
        frontRightDrive(4), frontRightTurn(3),
        backRightDrive(7), backRightTurn(8);

        private final int port;

        Swerve(int port) {
            this.port = port;
        }

        public int get() {
            return this.port;
        }
    }

    public enum Controller {
        leftIntake(17), rightIntake(14),
        leftShooter(0), rightShooter(0),
        shooterArm(0),
        elevator(0),
        amp(0);

        private final int port;

        Controller(int port) {
            this.port = port;
        }

        public int get() {
            return this.port;
        }
    }

    public enum Encoder {
        frontLeft(9), frontRight(10),
        backLeft(11), backRight(12),
        shooterArm(0);

        private final int port;

        Encoder(int port) {
            this.port = port;
        }

        public int get() {
            return this.port;
        }
    }
}
