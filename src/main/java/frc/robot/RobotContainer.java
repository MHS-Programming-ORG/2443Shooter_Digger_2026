package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.KickerCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.StopShooter;
import frc.robot.subsystems.ArduCam;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
public class RobotContainer {
  private final ArduCam camera = new ArduCam();
  // ShooterSubsytem Parameters: shooter side ArduCam, LeftShooterPort, RightShooterPort, KickerPort, ShooterGearRatio
  private final ShooterSubsystem shooterSub = new ShooterSubsystem(camera, 2, 4, 3, 48/50);
  private final ShooterCommand shooterCmd = new ShooterCommand(shooterSub);
  private final CommandJoystick stick = new CommandJoystick(1);
  private final CommandXboxController xbox = new CommandXboxController(0);
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    xbox.x().onTrue(shooterCmd);
    xbox.y().onTrue(new StopShooter(shooterSub));
    xbox.rightBumper().whileTrue(new KickerCommand(shooterSub));
    xbox.leftBumper().whileTrue(new InstantCommand(() -> shooterSub.shooterShoot()));
    xbox.a().onTrue(new InstantCommand(() -> new ConveyorSubsystem(0).setConveyorSpeed(0.7)));
    //xbox.leftBumper().whileFalse(new InstantCommand(() -> shooterSub.setShooterGuideSpeed(0)));

  }
  public Command getAutonomousCommand() {
    return null;
  }
}
