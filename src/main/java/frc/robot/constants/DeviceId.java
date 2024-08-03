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
        shooterArm(13),
        leftShooter(14), rightShooter(15),
        leftIntake(16), rightIntake(17),
        conveyorBelt(20),
        elevator(18),
        amp(19);

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
