// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

public class ShooterCalc {

    private static final double G = 9.81;
    private final double INCH_TO_METER = 0.0254;

    // change these the height is right im pretty sure
    private final double angleDegree = 30.0;
    private final double shooterHeightOffset = 20 * INCH_TO_METER;
    private final double hubHeight = 72 * INCH_TO_METER; //The 2.75 is like a spacer so hopeuflly above lip idk

    private final double wheelRadiusMeters = 2.25 * INCH_TO_METER;
    private final double gearRatio = 1.0; // motor : wheel

    public double calculateLaunchVelocity(double distanceMeters) {
        double theta = Math.toRadians(angleDegree);
        double heightDifference = hubHeight - shooterHeightOffset;

        double numerator = G * Math.pow(distanceMeters, 2);
        double denominator = 2 * Math.pow(Math.cos(theta), 2) * (distanceMeters * Math.tan(theta) - heightDifference);
        return Math.sqrt(numerator / denominator);
    }

    public double calculateMotorRPS(double distanceMeters) {
        //  + (13.314522 * INCH_TO_METER)
        double launchVelocity = calculateLaunchVelocity(distanceMeters);
        return (launchVelocity / (2 * Math.PI * wheelRadiusMeters)) * gearRatio;
  }
}
