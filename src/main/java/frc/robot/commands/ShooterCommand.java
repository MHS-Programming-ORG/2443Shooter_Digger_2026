package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommand extends Command {
  private ShooterSubsystem shooterSub;
  public ShooterCommand(ShooterSubsystem shooterSub) {
    this.shooterSub = shooterSub;
    addRequirements(shooterSub);
  }

  @Override
  public void initialize() {
    shooterSub.setShooterVelocity(90); // half of the value here is outputted
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
