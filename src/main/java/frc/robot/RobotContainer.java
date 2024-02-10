// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.FlingNote;
import frc.robot.commands.Intake;
import frc.robot.commands.NudgeNote;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.CANLauncher;

public class RobotContainer {
  // The robot's subsystems are defined here.
  private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  private final CANLauncher m_launcher = new CANLauncher();

  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {

    // Set the default command for the drivetrain to drive using the joysticks
    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () ->
                m_drivetrain.arcadeDrive(
                    -m_driverController.getLeftY(), -m_driverController.getRightX()),
            m_drivetrain));

    m_driverController.rightTrigger(0.5).whileTrue(new FlingNote(m_launcher));

    m_driverController.rightBumper().whileTrue(new NudgeNote(m_launcher));

    m_driverController.leftBumper().whileTrue(m_launcher.getIntakeCommand());

    m_driverController.leftTrigger(0.5).whileTrue(new Intake(m_launcher));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_drivetrain);
  }
}
