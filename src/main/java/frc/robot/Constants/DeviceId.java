package frc.robot.constants;

public class DeviceId {
    public enum Swerve {
        frontLeftDrive(2), frontLeftTurn(1),
        frontRightDrive(4), frontRightTurn(3),
        backLeftDrive(6), backLeftTurn(5),
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
        leftIntake(0), rightIntake(0),
        leftShooter(14), rightShooter(15),
        shooterArm(13),
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
