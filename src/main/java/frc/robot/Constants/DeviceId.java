package frc.robot.constants;

public class DeviceId {
    public enum Swerve {
        frontLeftDrive(2), frontLeftTurn(1),
        backLeftDrive(6), backLeftTurn(5),
        frontRightDrive(4), frontRightTurn(3),
        backRightDrive(8), backRightTurn(7);

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
