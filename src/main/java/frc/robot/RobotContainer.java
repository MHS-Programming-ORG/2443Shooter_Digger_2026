package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
public class RobotContainer {
  private final ShooterSubsystem shooterSub = new ShooterSubsystem();
  private final ShooterCommand shooterCmd = new ShooterCommand(shooterSub);
  private final CommandJoystick stick = new CommandJoystick(1);
  private final CommandXboxController xbox = new CommandXboxController(0);
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    xbox.x().onTrue(shooterCmd);
    xbox.a().onTrue(new InstantCommand(()->shooterSub.setShooterSetpoint(0)));
  }
  public Command getAutonomousCommand() {
    return null;
  }
}
