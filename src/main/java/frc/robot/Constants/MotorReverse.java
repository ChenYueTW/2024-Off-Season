package frc.robot.constants;

public class MotorReverse {
    public enum Reverse {
        frontLeftDrive(false), frontLeftTurn(true),
        frontRightDrive(true), frontRightTurn(true),
        backLeftDrive(false), backLeftTurn(true),
        backRightDrive(true), backRightTurn(true);
    
        private final boolean reverse;
    
        Reverse(boolean reverse) {
            this.reverse = reverse;
        }
    
        public boolean get() {
            return this.reverse;
        }
    }
}
