// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.spark.SparkMax;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  public static TalonFX intakeMotor;
  public static TalonFX intakePivot;
  public IntakeSubsystem() {
    intakeMotor = new TalonFX(1);
    intakePivot = new TalonFX(2);
  }

  public void setISpeed(double speed){
    intakeMotor.set(speed);
  }

  public void setPSpeed(double speed){
    intakePivot.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
