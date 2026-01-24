// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.photonvision.PhotonCamera;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  private static double shooterOuput, prevError, output;
  private static PhotonCamera camera;
  private static boolean pidOn;
  private static PIDController shooterPIDCtrler;
  private static TalonFX shooterMotor1, shooterMotor2, shooterguide;

  public ShooterSubsystem() {
    prevError = 0;
    output = 0;
    shooterPIDCtrler = new PIDController(ShooterConstants.SHOOTER_KP,ShooterConstants.SHOOTER_KI,ShooterConstants.SHOOTER_KD);
    shooterMotor1 = new TalonFX(ShooterConstants.SHOOTER_MOTOR1_PORT);
    shooterMotor2 = new TalonFX(ShooterConstants.SHOOTER_MOTOR2_PORT);
    shooterguide = new TalonFX(ShooterConstants.SHOOTER_GUIDE_PORT);
    shooterOuput = shooterMotor1.getDutyCycle().getValueAsDouble();
    camera = new PhotonCamera("Ardu cam");
    pidOn = false;
  }

  public void setShooterSetpoint(double setpoint){
    shooterPIDCtrler.setSetpoint(setpoint);
  }

  public void setShooterGuideSpeed(double speed){
    shooterguide.set(speed);
  }

  public void setShooterMotorSpeed(double speed){
    shooterMotor1.set(speed);
    shooterMotor2.set(-speed);
  }

  public double getShooterOutput(){
    return shooterOuput;
  }


  public double getShooterSetpoint(){
    return shooterPIDCtrler.getSetpoint();
  }

  public boolean atSetpoint(){
    return shooterPIDCtrler.atSetpoint();
  }

  @Override
  public void periodic() {
    double currError = getShooterSetpoint() - getShooterOutput();
    if(pidOn){
      output = shooterPIDCtrler.calculate(getShooterOutput(), getShooterSetpoint());
      if(output > ShooterConstants.SHOOTER_MAXSPEED){
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
    setShooterMotorSpeed(output);
    SmartDashboard.putNumber("[Shooter] Motor Vel:", getShooterOutput());
    SmartDashboard.putNumber("[Shooter] Setpoint:", getShooterSetpoint());
  }
}
