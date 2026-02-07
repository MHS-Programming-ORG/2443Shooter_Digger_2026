package frc.robot.shoooterSubsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterCalcV2 {
    private final double[][] shooterData = {
        {50, 0},
        {50, 1.0},
        {50, 3.1},
        {50, 4.3},
        {50, 5.6},
        {60, 6.8},
        {70, 8.0},
        {80, 9.1},
        {90, 10.0},
        {100, 10.8}
    };

    public double getRPSForDistance(double targetDistanceMeters) {

        for (int i = 0; i < shooterData.length - 1; i++) {

            double rps1 = shooterData[i][0];
            double dist1 = shooterData[i][1];
            double rps2 = shooterData[i + 1][0];
            double dist2 = shooterData[i + 1][1];

            if (targetDistanceMeters >= dist1 && targetDistanceMeters <= dist2) {
                double ratio =
                        (targetDistanceMeters - dist1) / (dist2 - dist1);

                return rps1 + ratio * (rps2 - rps1);
            }
        }
        return shooterData[shooterData.length - 1][0];
    }
}