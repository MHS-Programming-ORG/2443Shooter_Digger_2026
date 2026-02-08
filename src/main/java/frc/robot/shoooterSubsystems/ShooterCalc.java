package frc.robot.shoooterSubsystems;

public class ShooterCalc {

    private static final double G = 9.81;
    private final double INCH_TO_METER = 0.0254;

    // change these the height is right im pretty sure
    private final double angleDegree = 50.0;
    private final double shooterHeightOffset = 0 * INCH_TO_METER;
    private final double hubHeight = 0 * INCH_TO_METER;

    private final double wheelRadiusMeters = 2.25 * INCH_TO_METER;
    private final double gearRatio = 1.0; // motor : wheel

    public double calculateLaunchVelocity(double distanceMeters) {
        double theta = Math.toRadians(angleDegree);
        double heightDifference = hubHeight - shooterHeightOffset;

        double numerator = G * Math.pow(distanceMeters, 2);
        double denominator = 2 * Math.pow(Math.cos(theta), 2) * (distanceMeters * Math.tan(theta) - heightDifference);

        if (denominator <= 0) {
            throw new IllegalArgumentException("Target out of range for the given shooter angle and height difference.");
        }

        return Math.sqrt(numerator / denominator);
    }

    public double calculateMotorRPS(double distanceMeters) {
        double launchVelocity = calculateLaunchVelocity(distanceMeters);
        double wheelRPS = launchVelocity / (2 * Math.PI * wheelRadiusMeters);

        return wheelRPS * gearRatio;
    }
}

