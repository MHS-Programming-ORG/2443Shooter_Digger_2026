// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.photonvision.PhotonCamera;
import frc.robot.Constants.ShooterConstants;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import frc.robot.commands.ShooterCommand;


public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  private static Encoder shooter1Enc, shooter2Enc;
  private static ShooterCalc shooterCalc;
  private static ArduCam camera;
  private static TalonFX shooterMotor1, shooterMotor2, shooterguide;
  
  private static final VelocityVoltage velocityRequest = new VelocityVoltage(0);

  public ShooterSubsystem() {
    shooterCalc = new ShooterCalc();

    shooterMotor1 = new TalonFX(ShooterConstants.SHOOTER_MOTOR1_PORT);
    shooterMotor2 = new TalonFX(ShooterConstants.SHOOTER_MOTOR2_PORT);
    shooterguide = new TalonFX(ShooterConstants.SHOOTER_GUIDE_PORT);

    // shooterMotor2.setControl(new Follower(shooterMotor1.getDeviceID(), MotorAlignmentValue.Opposed));

    var config = new TalonFXConfiguration();
    config.Slot0.kP = ShooterConstants.SHOOTER_KP;
    config.Slot0.kI = ShooterConstants.SHOOTER_KI;
    config.Slot0.kD = ShooterConstants.SHOOTER_KD;
    config.Feedback.SensorToMechanismRatio = ShooterConstants.SHOOTER_MOTOR_GEAR_RATIO;
    
    shooterMotor1.getConfigurator().apply(config);
    shooterMotor2.getConfigurator().apply(config);

    shooterMotor1.setNeutralMode(NeutralModeValue.Coast);
    shooterMotor2.setNeutralMode(NeutralModeValue.Coast);
    shooterguide.setNeutralMode(NeutralModeValue.Coast);

    
  }

  public void setShooterGuideSpeed(double speed){
    shooterguide.setControl(new VelocityVoltage(speed));
  }

  public void setLeftShooterMotorSpeed(double speed){
    shooterMotor1.set(speed);
  }

  public void setRightShooterMotorSpeed(double speed){
    shooterMotor2.set(-speed);
  }

  public void stopShooterMotors(){
    shooterMotor1.set(0);
    shooterMotor2.set(0);
  }

  public double errorVelocity(double rps)
  {
    double result = rps / 10;
    return result * 1.5;
  }

  public void setShooterVelocity(double targetRPS) {
    shooterMotor1.setControl(velocityRequest.withVelocity(targetRPS+errorVelocity(targetRPS)));
    shooterMotor2.setControl(velocityRequest.withVelocity(-(targetRPS+errorVelocity(targetRPS))));
  }

  public double getShooterVelocity() {
    return shooterMotor1.getVelocity().getValueAsDouble();
  }

  public double getShooterVelocity2() {
    return shooterMotor2.getVelocity().getValueAsDouble();
  }

  public void shooterShoot(){
     double motorRPS = ShooterCalc.calculateMotorRPS(camera.getX());
     shooterMotor1.setControl(new VelocityVoltage(motorRPS));
     shooterMotor2.setControl(new VelocityVoltage(-motorRPS));
  }

  @Override
  public void periodic() {
    // add speed limit here
    SmartDashboard.putNumber("[Shooter] Velocity RPS", getShooterVelocity());
    SmartDashboard.putNumber("[Shooter] Velocity RPS 2", getShooterVelocity2());
    SmartDashboard.putNumber("Rotor RPS",shooterMotor1.getRotorVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Mechanism RPS",shooterMotor1.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Velocity Error", shooterMotor1.getClosedLoopError().getValueAsDouble());
    // SmartDashboard.putNumber("X Distance", camera.getX());
    // SmartDashboard.putNumber("Calculation RPS", ShooterCalc.calculateMotorRPS(camera.getX()));
    //SmartDashboard.putNumber("[Shooter] Calculated DutyCycleOut", (convertDist_Vel() / (2*Math.PI*ShooterConstants.SHOOTER_MOTORWHEEL_RADIUS)) / 100);
  }
}
