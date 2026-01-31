// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class ShooterConstants {
    // PORTS
    //public static final int SHOOTER_PIVOTENC_PORT = 1;
    public static final int SHOOTER_MOTOR1_PORT = 2;
    public static final int SHOOTER_MOTOR2_PORT = 4;
    public static final int SHOOTER_GUIDE_PORT = 3;
    //PID VALUES
    public static final double SHOOTER_KP = .5;
    public static final double SHOOTER_KI = 0;
    public static final double SHOOTER_KD = 0;
    //SPEED
    public static final double SHOOTER_MAXSPEED = 0.95;
    public static final double SHOOTER_MINSPEED = -0.95;
    public static final double SHOOTER_GUIDESPEED = 50; //IN RPS
    public static final double SHOOTER_MOTORSPEED = 70; //IN RPS
    //MASUREMENTS
    public static final double SHOOTER_GUIDEWHEEL_RADIUS = 1; // IN METERS
    public static final double SHOOTER_MOTORWHEEL_RADIUS = 0.04; // IN METERS
    public static final double SHOOTER_GUIDE_GEAR_RATIO = 1/2; 
    public static final double SHOOTER_MOTOR_GEAR_RATIO = 1.0417;
  }
}
