package frc.robot.lib;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import edu.wpi.first.math.geometry.Translation2d;

public class MathHelper {
    public static Translation2d rotation2dMatrix(Translation2d vector, double theta) {
        RealVector realVector = new ArrayRealVector(new double[]{vector.getX(), vector.getY()});
        double[][] matrixData = {
            {Math.cos(theta), -Math.sin(theta)},
            {Math.sin(theta), Math.cos(theta)}
        };
        RealMatrix rotationMatrix = MatrixUtils.createRealMatrix(matrixData);
        RealVector rotatedVector = rotationMatrix.operate(realVector);
        return new Translation2d(rotatedVector.getEntry(0), rotatedVector.getEntry(1));
    }
    public static Translation2d translationSubtract(Translation2d a, Translation2d b) {
        double x = a.getX() - b.getX();
        double y = a.getY() - b.getY();
        return new Translation2d(x, y);
    }
}
