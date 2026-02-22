// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;


public class ShooterSubsystem extends SubsystemBase {
  // ADD SUPPLY CURRENT LIMIT
  /** Creates a new IntakeSubsystem. */
  private static final double[] shooterPIDVals = {0.85, 0.1904296875, 0.07};
  private double sVelocity = 50;
  private ShooterVelocityRanges shootVelR;
  private ShooterCalc shooterCalcV3;
  private ArduCam camera = new ArduCam();
  private TalonFX shooterMotor1, shooterMotor2, shooterguide;
  private final VelocityVoltage velocityRequest = new VelocityVoltage(0);

  public ShooterSubsystem(ArduCam camera, int shooterPort1, int shooterPort2, int kickerPort) {
    shooterCalcV3 = new ShooterCalc();
    this.camera = camera;

    shooterMotor1 = new TalonFX(shooterPort1);
    shooterMotor2 = new TalonFX(shooterPort2);
    shooterguide = new TalonFX(kickerPort);

    var config = new TalonFXConfiguration();
    config.Slot0.kP = shooterPIDVals[0];
    config.Slot0.kS = shooterPIDVals[1];
    config.Slot0.kV = shooterPIDVals[2];
    config.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
    
    shooterMotor1.getConfigurator().apply(config);
    shooterMotor2.getConfigurator().apply(config);

    shooterMotor1.setNeutralMode(NeutralModeValue.Coast);
    shooterMotor2.setNeutralMode(NeutralModeValue.Coast);
    shooterguide.setNeutralMode(NeutralModeValue.Coast);
  }

  public void setShooterGuideSpeed(double speed){
    shooterguide.setControl(velocityRequest.withVelocity(speed));
  }

  public void stopShooterMotors(){
    shooterMotor1.set(0);
    shooterMotor2.set(0);
  }

  public void setShooterVelocity(double targetRPS) {
    shooterMotor1.setControl(velocityRequest.withVelocity(targetRPS));
    shooterMotor2.setControl(velocityRequest.withVelocity(-targetRPS));
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
      setShooterVelocity(shooterCalcV3.calculateMotorRPS(camera.getX()));
    }
  // if(camera.cameraVisable()){
  //   sVelocity = shootVelR.returnVelocity(camera.getX());
  // }
  // setShooterVelocity(sVelocity);
  }

  @Override
  public void periodic() {
    // add speed limit here
    SmartDashboard.putNumber("[Shooter] Velocity RPS", getShooterVelocity());
    SmartDashboard.putNumber("[Shooter] Velocity RPS 2", getShooterVelocity2());
    SmartDashboard.putNumber("ArduCam", camera.getX());
    SmartDashboard.putNumber("Rotor RPS",shooterMotor1.getRotorVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Mechanism RPS",shooterMotor1.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Velocity Error", shooterMotor1.getClosedLoopError().getValueAsDouble());
    SmartDashboard.putNumber("Initial RPS", shooterCalcV3.calculateLaunchVelocity(camera.getX()));
    //  SmartDashboard.putNumber("Calculation RPS", shooterCalcvV2.getRPSForDistance(camera.getX()));
    //SmartDashboard.putNumber("[Shooter] Calculated DutyCycleOut", (convertDist_Vel() / (2*Math.PI*ShooterConstants.SHOOTER_MOTORWHEEL_RADIUS)) / 100);
  }
}
