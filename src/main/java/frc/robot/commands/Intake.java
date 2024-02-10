// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.LauncherConstants.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CANLauncher;

public class Intake extends Command {
  CANLauncher m_launcher;

  double m_IntakeLauncherSpeed = kIntakeLauncherSpeed;
  double m_IntakeFeederSpeed = kIntakeFeederSpeed;
  double m_IntakeDelay = kIntakeDelay;

  /** Creates a new LaunchNote. */
  public Intake(CANLauncher launcher) {
    // save the launcher system internally
    m_launcher = launcher;

    SmartDashboard.putNumber("Intake launcher speed", m_IntakeLauncherSpeed);
    SmartDashboard.putNumber("Intake feeder speed", m_IntakeFeederSpeed);
    SmartDashboard.putNumber("Intake delay", m_IntakeDelay);

    // indicate that this command requires the launcher system
    addRequirements(m_launcher);
  }

  // The initialize method is called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Set the wheels to launching speed
    m_IntakeLauncherSpeed =
        SmartDashboard.getNumber("Intake launcher speed", m_IntakeLauncherSpeed);
    m_IntakeFeederSpeed = SmartDashboard.getNumber("Intake feeder speed", m_IntakeFeederSpeed);
    m_IntakeDelay = SmartDashboard.getNumber("Intake delay", m_IntakeDelay);

    // m_launcher.setLaunchWheel(m_FlingLauncherSpeed);

    new SequentialCommandGroup(
            new InstantCommand(() -> m_launcher.setLaunchWheel(m_IntakeLauncherSpeed), m_launcher),
            new InstantCommand(() -> m_launcher.setFeedWheel(m_IntakeFeederSpeed), m_launcher),
            new WaitCommand(m_IntakeDelay),
            new InstantCommand(() -> m_launcher.stop(), m_launcher))
        .schedule();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Always return false so the command never ends on it's own. In this project we use the
    // scheduler to end the command when the button is released.
    return false;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop the wheels when the command ends.
    m_launcher.stop();
  }
}
