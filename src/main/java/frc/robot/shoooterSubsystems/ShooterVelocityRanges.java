// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shoooterSubsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterVelocityRanges extends SubsystemBase {
  /** Creates a new ShooterVelocityRanges. */
  public ShooterVelocityRanges() {}

  public double returnVelocity(double xDistance){ //untested values
    double velocity = 0;
    if(xDistance > 3){
      velocity = 100;
    } else if(xDistance > 2){
      velocity = 70;
    } else{
      velocity = 50;
    }
    return velocity; // in RPS
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
