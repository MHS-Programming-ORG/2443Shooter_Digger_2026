package frc.robot;

import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.ArduCam;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.commands.RossIdleCommand;
import frc.robot.commands.RossShootCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
public class RobotContainer {
  private final ArduCam camera = new ArduCam();
  // ShooterSubsytem Parameters: shooter side ArduCam, LeftShooterPort, RightShooterPort, KickerPort, ShooterGearRatio
  private final ShooterSubsystem shooterSub = new ShooterSubsystem(camera, 15, 16, 17);
  private final ShooterCommand shooterCmd = new ShooterCommand(shooterSub);
  private final ConveyorSubsystem conveyorSub = new ConveyorSubsystem(18);
  private final CommandJoystick stick = new CommandJoystick(1);
  private final CommandXboxController xbox = new CommandXboxController(0);

  // Create the command object
  //RossShootCommand Parameter: Shooter Sub, conveyor sub, kicker delay(seconds), Shooter Velocity(RPS), Kicker velocity(RPS), Conveyor Velocity(%)
  private final RossShootCommand rossShootCmd = new RossShootCommand(shooterSub, conveyorSub, 
  2, 40, 55, 0.5);

  public RobotContainer() {

    configureBindings();
  }

  private void configureBindings() {
    xbox.x().whileTrue( rossShootCmd );
    shooterSub.setDefaultCommand(new RossIdleCommand(shooterSub, 20));
    // xbox.x().whileTrue(new InstantCommand(() -> shooterSub.shooterShoot()));
  }

  public ShooterSubsystem getShooterSubsystem(){
    return new ShooterSubsystem(camera, 15, 16, 17);
  }

  public Command getAutonomousCommand() {
    return null;
  }
}