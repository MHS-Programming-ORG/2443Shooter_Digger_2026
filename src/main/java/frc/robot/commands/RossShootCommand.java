package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RossShootCommand extends Command {
  private ShooterSubsystem shooterSub;
  private ConveyorSubsystem conveyorSub;
  private double kickerDelay, kickerVel, distance, conveyorVel;
  private Timer timer;

  public RossShootCommand(ShooterSubsystem shooterSub, ConveyorSubsystem conveyorSub, double kickerDelay, double distance, double kickerVel, double conveyorVel) {
    this.shooterSub = shooterSub;
    addRequirements(shooterSub);

    this.conveyorSub = conveyorSub;
    addRequirements(conveyorSub);

    this.timer = new Timer();
    this.kickerDelay = kickerDelay;
    this.distance = distance;
    this.kickerVel = kickerVel;
    this.conveyorVel = conveyorVel;
  }

  @Override
  public void initialize() {
    timer.restart(); // Reset the timer

    // Run the shooter to let it spin up
    shooterSub.setShooterVelocity(distance);
    // shooterSub.shooterShoot(distance);
    shooterSub.setKickerVelocity(kickerVel);
  }

  @Override
  public void execute() {
    // This function runs in a loop so it's called over and over again while the
    // command is running.
    // Check to see that some amount of time has passed since the command
    // has started.  At that point, start the conveyor and kicker
    //MathUtil.isNear(shooterSub.getShooterShoot(), shooterSub.getShooterVelocity(), 3)
    SmartDashboard.putNumber("RPS", shooterSub.getShooterShoot(distance));
    if (timer.get() >= kickerDelay) {
      conveyorSub.setConveyorSpeed(conveyorVel);
    }
  }

  @Override
  public void end(boolean interrupted) {
    // Stop all motors since the command has ended.
    // This should still be called if the command in cancelled
    shooterSub.stopKickerMotor();
    shooterSub.stopShooterMotors();
    conveyorSub.setConveyorSpeed(0.0);
    timer.stop();
    if(MathUtil.isNear(shooterSub.getShooterShoot(distance), 20, 3)){
      shooterSub.setShooterNKickerIdle(conveyorVel, kickerDelay);
    }
  }

  @Override
  public boolean isFinished() {
    return false;  // This means run the command forever until we cancel the command
  }
}