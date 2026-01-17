// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  private static double shooterEnc, prevError, output;
  private static boolean pidOn;
  private static PIDController shooterPIDCtrler;
  private static TalonFX shooterMotor;

  public ShooterSubsystem() {
    shooterPIDCtrler = new PIDController(ShooterConstants.SHOOTER_KP,ShooterConstants.SHOOTER_KI,ShooterConstants.SHOOTER_KD);
    shooterMotor = new TalonFX(ShooterConstants.SHOOTER_MOTOR_PORT);
    shooterEnc = shooterMotor.getRotorPosition().getValueAsDouble();
    pidOn = true;
  }

  public void setShooterSetpoint(double setpoint){
    shooterPIDCtrler.setSetpoint(setpoint);
  }

  /*
  *  public void stopShooter(){
  *    shooterMotor.stopMotor();
  *  }
  */

  public double getShooterEnc(){
    return shooterEnc;
  }

  public double getShooterSetpoint(){
    return shooterPIDCtrler.getSetpoint();
  }

  public boolean atSetpoint(){
    return shooterPIDCtrler.atSetpoint();
  }

  @Override
  public void periodic() {
    double currError = getShooterSetpoint() - getShooterEnc();
    if(pidOn){
      output = shooterPIDCtrler.calculate(getShooterEnc(), getShooterSetpoint());
      if(output> ShooterConstants.SHOOTER_MAXSPEED){
        output = ShooterConstants.SHOOTER_MAXSPEED;
      } else if (output < ShooterConstants.SHOOTER_MINSPEED){
        output = ShooterConstants.SHOOTER_MINSPEED;
      }
      if(currError< 0 && prevError >0){
        shooterPIDCtrler.reset();
      }else if(currError > 0 && prevError < 0){
        shooterPIDCtrler.reset();
      }
      prevError = currError;
    }
    shooterMotor.set(output);
  }
}
