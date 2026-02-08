// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shoooterSubsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterCalcV3 extends SubsystemBase {
  /** Creates a new ShooterCalcV3. */
  private double G = 9.80665;
  public ShooterCalcV3() {}

  public double shooterRPS0(double xDistance){
    return Math.sqrt(2*(G*xDistance))/(2*Math.PI*2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
