package frc.robot.subsystems;

public class ShooterCalc {

    private static final double G = 9.81;
    private static final double INCH_TO_METER = 0.0254;

    // change these the height is right im pretty sure
    private static final double angleDegree = 50.0;
    private static final double shooterHeightOffset = 20.0 * INCH_TO_METER;
    private static final double hubHeight = 72.0 * INCH_TO_METER;

    public double calculateLaunchVelocity(double distanceMeters) {
        double theta = Math.toRadians(angleDegree);

        double heightDifference = hubHeight - shooterHeightOffset;

        double cosTheta = Math.cos(theta);
        double tanTheta = Math.tan(theta);

        double numerator = G * Math.pow(distanceMeters, 2);
        double denominator =
                2 * Math.pow(cosTheta, 2) *
                (distanceMeters * tanTheta - heightDifference);

        if (denominator <= 0) {
            throw new IllegalArgumentException(
                "Target unreachable at this distance."
            );
        }

        return Math.sqrt(numerator / denominator);
    }
}

