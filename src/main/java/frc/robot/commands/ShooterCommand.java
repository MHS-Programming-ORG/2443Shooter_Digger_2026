package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.Constants.ShooterConstants;

public class ShooterCommand extends Command {
  private ShooterSubsystem shooterSub;
  public ShooterCommand(ShooterSubsystem shooterSub) {
    this.shooterSub = shooterSub;
    addRequirements(shooterSub);
  }

  @Override
  public void initialize() {
    shooterSub.setPidState(true);
    shooterSub.setShooterSetpoint(ShooterConstants.SHOOTER_MOTORSPEED);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    shooterSub.setShooterSetpoint(0);
  }

  @Override
  public boolean isFinished() {
    return shooterSub.atSetpoint();
  }
}
