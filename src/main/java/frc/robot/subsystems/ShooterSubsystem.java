// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.mechanisms.swerve.LegacySwerveRequest.Idle;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;


public class ShooterSubsystem extends SubsystemBase {
  // ADD SUPPLY CURRENT LIMIT
  /** Creates a new IntakeSubsystem. */
  private static final double[] shooterConfigVals = {0.85, 0.1904296875, 0.07};
  private static final double[] kickerConfigVals = {0, 0.4501953125, 0.06499999761581421};
  private double sVelocity = 50;
  private ShooterCalc shooterCalcV3;
  private ArduCam camera = new ArduCam();
  private TalonFX shooterMotor1, shooterMotor2, kickerMotor;
  private final VelocityVoltage velocityRequest = new VelocityVoltage(0);

  public ShooterSubsystem(ArduCam camera, int shooterPort1, int shooterPort2, int kickerPort) {
    shooterCalcV3 = new ShooterCalc();
    this.camera = camera;

    shooterMotor1 = new TalonFX(shooterPort1);
    shooterMotor2 = new TalonFX(shooterPort2);
    kickerMotor = new TalonFX(kickerPort);

    var shooterConfig = new TalonFXConfiguration();
    shooterConfig.Slot0.kP = shooterConfigVals[0];
    shooterConfig.Slot0.kS = shooterConfigVals[1];
    shooterConfig.Slot0.kV = shooterConfigVals[2];
    shooterConfig.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;

    var kickerConfig = new TalonFXConfiguration();
    kickerConfig.Slot0.kP = kickerConfigVals[0];
    kickerConfig.Slot0.kS = kickerConfigVals[1];
    kickerConfig.Slot0.kV = kickerConfigVals[2];
    kickerConfig.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
    
    shooterMotor1.getConfigurator().apply(shooterConfig);
    shooterMotor2.getConfigurator().apply(shooterConfig);
    kickerMotor.getConfigurator().apply(kickerConfig);

    shooterMotor1.setNeutralMode(NeutralModeValue.Coast);
    shooterMotor2.setNeutralMode(NeutralModeValue.Coast);
    kickerMotor.setNeutralMode(NeutralModeValue.Coast);
  }

  public void setShooterNKickerIdle(double shooter, double kicker){
    shooterMotor1.setControl(velocityRequest.withVelocity(shooter).withSlot(1));
    shooterMotor2.setControl(velocityRequest.withVelocity(-shooter).withSlot(1));
    kickerMotor.setControl(velocityRequest.withVelocity(kicker).withSlot(1));
  }

  public void setKickerVelocity(double speedRPS){
    kickerMotor.setControl(velocityRequest.withVelocity(speedRPS).withSlot(0));
  }

  public void stopShooterMotors(){
    shooterMotor1.stopMotor();
    shooterMotor2.stopMotor();
  }

  public void stopKickerMotor(){
    kickerMotor.stopMotor();
  }

  public void setShooterVelocity(double targetRPS) {
    shooterMotor1.setControl(velocityRequest.withVelocity(targetRPS).withSlot(0));
    shooterMotor2.setControl(velocityRequest.withVelocity(-targetRPS).withSlot(0));
  }

  public double getShooterVelocity() {
    return shooterMotor1.getVelocity().getValueAsDouble();
  }

  public double getShooterVelocity2() {
    return shooterMotor2.getVelocity().getValueAsDouble();
  }

  // Goal: Shoot a min dist of 6feet (1.8288m) to max dist of 12 feet (3.6576m)
  // Kicker Vel > Shooter Vel == Higher Y
  // Kicker Vel < Shooter Vel == Lower Y
  // Kicker Vel = Shooter Vel == Equal Y
  public void shooterShoot(){
    if(camera.cameraVisable()){
      System.out.println("df");
      SmartDashboard.putNumber("RPS", shooterCalcV3.calculateMotorRPS(camera.getX()));
      // setShooterVelocity(shooterCalcV3.calculateMotorRPS(camera.getX()));
    }
  }

  @Override
  public void periodic() {
    // add speed limit here
    SmartDashboard.putBoolean("Camera Visable", camera.cameraVisable());
    SmartDashboard.putNumber("[Shooter] Velocity RPS", getShooterVelocity());
    SmartDashboard.putNumber("[Shooter] Velocity RPS 2", getShooterVelocity2());
    SmartDashboard.putNumber("[S] Current ", shooterMotor1.getStatorCurrent().getValueAsDouble());
    SmartDashboard.putNumber("[Shooter] Kicker", kickerMotor.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("ArduCam", camera.getX());
    SmartDashboard.putNumber("Rotor RPS",shooterMotor1.getRotorVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Mechanism RPS",shooterMotor1.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Velocity Error", shooterMotor1.getClosedLoopError().getValueAsDouble());
    //  SmartDashboard.putNumber("Calculation RPS", shooterCalcvV2.getRPSForDistance(camera.getX()));
    //SmartDashboard.putNumber("[Shooter] Calculated DutyCycleOut", (convertDist_Vel() / (2*Math.PI*ShooterConstants.SHOOTER_MOTORWHEEL_RADIUS)) / 100);
  }
}
