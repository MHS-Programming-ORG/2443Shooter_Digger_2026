// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.photonvision.PhotonCamera;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.ShooterCommand;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  private static double leftShooterOuput, rightShooterOutput, leftPrevError, rightPrevError, leftOutput, rightOutput, dist;
  private static ShooterCalc shooterCalc;
  //private static ArduCam camera;
  private static boolean pidOn;
  private static PIDController shooterPIDCtrler;
  private static TalonFX shooterMotor1, shooterMotor2, shooterguide;

  public ShooterSubsystem() {
    leftPrevError = 0;
    leftOutput = 0;
    shooterCalc = new ShooterCalc();
    //camera = new ArduCam();
    shooterPIDCtrler = new PIDController(ShooterConstants.SHOOTER_KP,ShooterConstants.SHOOTER_KI,ShooterConstants.SHOOTER_KD);
    shooterMotor1 = new TalonFX(ShooterConstants.SHOOTER_MOTOR1_PORT);
    shooterMotor2 = new TalonFX(ShooterConstants.SHOOTER_MOTOR2_PORT);
    shooterguide = new TalonFX(ShooterConstants.SHOOTER_GUIDE_PORT);
    leftShooterOuput = shooterMotor1.getDutyCycle().getValueAsDouble();
    rightShooterOutput = shooterMotor2.getDutyCycle().getValueAsDouble();
    //dist = camera.getX();
    pidOn = false;
    shooterMotor1.setNeutralMode(NeutralModeValue.Brake);
    shooterMotor2.setNeutralMode(NeutralModeValue.Brake);
    shooterguide.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setPidState(boolean stat){
    pidOn = stat;
  }

  public void setShooterSetpoint(double setpoint){
    shooterPIDCtrler.setSetpoint(setpoint);
  }

  public void setShooterGuideSpeed(double speed){
    shooterguide.set(speed);
  }

  public void setLeftShooterMotorSpeed(double speed){
    shooterMotor1.set(speed);
  }

  public void setRightShooterMotorSpeed(double speed){
    shooterMotor2.set(-speed);
  }

  //public double convertDist_Vel(){
  //  return shooterCalc.calculateLaunchVelocity(dist);
  //}

  public double getLeftShooterOutput(){
    return leftShooterOuput;
    //return (convertDist_Vel() / (2*Math.PI*ShooterConstants.SHOOTER_MOTORWHEEL_RADIUS)) / 100;
  }

  public double getRightShooterOutput(){
    return rightShooterOutput;
  }


  public double getShooterSetpoint(){
    return shooterPIDCtrler.getSetpoint();
  }

  public boolean atSetpoint(){
    return shooterPIDCtrler.atSetpoint();
  }

  @Override
  public void periodic() {
    double leftCurrError = getShooterSetpoint() - getLeftShooterOutput();
    double rightCurrError = getShooterSetpoint()- getRightShooterOutput();
    if(pidOn){
      leftOutput = shooterPIDCtrler.calculate(getLeftShooterOutput(), getShooterSetpoint());
      if(leftOutput > ShooterConstants.SHOOTER_MAXSPEED){
        leftOutput = ShooterConstants.SHOOTER_MAXSPEED;
      } else if (leftOutput < ShooterConstants.SHOOTER_MINSPEED){
        leftOutput = ShooterConstants.SHOOTER_MINSPEED;
      }
      if(leftCurrError< 0 && leftPrevError > 0){
        shooterPIDCtrler.reset();
      }else if(leftCurrError > 0 && leftPrevError < 0){
        shooterPIDCtrler.reset();
      }
      leftPrevError = leftCurrError;

      rightOutput = shooterPIDCtrler.calculate(getRightShooterOutput(), getShooterSetpoint());
      if(rightOutput > ShooterConstants.SHOOTER_MAXSPEED){
        rightOutput = ShooterConstants.SHOOTER_MAXSPEED;
      } else if (rightOutput < ShooterConstants.SHOOTER_MINSPEED){
        rightOutput = ShooterConstants.SHOOTER_MINSPEED;
      }
      if(rightCurrError< 0 && rightPrevError > 0){
        shooterPIDCtrler.reset();
      }else if(rightCurrError > 0 && rightPrevError < 0){
        shooterPIDCtrler.reset();
      }
      rightPrevError = rightCurrError;
    }
    
    setLeftShooterMotorSpeed(leftOutput);
    setRightShooterMotorSpeed(rightOutput);
    
    SmartDashboard.putNumber("[Shooter] Motor Left DCO:", leftOutput);
    SmartDashboard.putNumber("[Shooter] Motor Right DCO:", rightOutput);
    SmartDashboard.putNumber("[Shooter] Setpoint:", getShooterSetpoint());
    SmartDashboard.putNumber("[Shooter] LO:", getLeftShooterOutput());
    SmartDashboard.putNumber("[Shooter] Ro:", getRightShooterOutput());
    SmartDashboard.putBoolean("[Shooter] At Setpoint", atSetpoint());
    //SmartDashboard.putNumber("[Shooter] Calculated DutyCycleOut", (convertDist_Vel() / (2*Math.PI*ShooterConstants.SHOOTER_MOTORWHEEL_RADIUS)) / 100);
  }
}
