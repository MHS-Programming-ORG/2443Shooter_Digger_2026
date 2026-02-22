package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.RossShootCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class RossShootCommand extends Command {
  private ShooterSubsystem shooterSub;
  private ConveyorSubsystem conveyorSub;
  private Timer timer;

  public RossShootCommand(ShooterSubsystem shooterSub, ConveyorSubsystem conveyorSub ) {
    this.shooterSub = shooterSub;
    addRequirements(shooterSub);

    this.conveyorSub = conveyorSub;
    addRequirements(conveyorSub);

    this.timer = new Timer();
  }

  @Override
  public void initialize() {
    timer.restart(); // Reset the timer

    // Run the shooter to let it spin up
    shooterSub.setShooterVelocity(45); // half of the value here is outputted
  }

  @Override
  public void execute() {
    // This function runs in a loop so it's called over and over again while the
    // command is running.
    // Check to see that some amount of time has passed since the command
    // has started.  At that point, start the conveyor and kicker
    if (timer.get() > 2.0) {
      shooterSub.setShooterGuideSpeed(45);
      conveyorSub.setConveyorSpeed(0.4);
    }
  }

  @Override
  public void end(boolean interrupted) {
    // Stop all motors since the command has ended.
    // This should still be called if the command in cancelled
    shooterSub.stopShooterMotors();
    shooterSub.setShooterGuideSpeed(0.0);
    conveyorSub.setConveyorSpeed(0.0);
  }

  @Override
  public boolean isFinished() {
    return false;  // This means run the command forever until we cancel the command
  }
}
