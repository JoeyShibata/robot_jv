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

public class NudgeNote extends Command {
  CANLauncher m_launcher;

  double m_NudgeFeederSpeed = kNudgeFeederSpeed;
  double m_NudgeLauncherSpeed = kNudgeLauncherSpeed;
  double m_NudgeSpinUpDelay = kNudgeSpinUpDelay;
  double m_NudgeShootTime = kNudgeShootTime;

  /** Creates a new LaunchNote. */
  public NudgeNote(CANLauncher launcher) {
    // save the launcher system internally
    m_launcher = launcher;

    SmartDashboard.putNumber("Nudge Feeder Speed", m_NudgeFeederSpeed);
    SmartDashboard.putNumber("Nudge Launcher Speed", m_NudgeLauncherSpeed);
    SmartDashboard.putNumber("Nudge Spin Up Delay", m_NudgeSpinUpDelay);
    SmartDashboard.putNumber("Nudge Shoot Time", m_NudgeShootTime);

    // indicate that this command requires the launcher system
    addRequirements(m_launcher);
  }

  // The initialize method is called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Set the wheels to launching speed
    m_NudgeFeederSpeed = SmartDashboard.getNumber("Nudge Feeder Speed", m_NudgeFeederSpeed);
    m_NudgeLauncherSpeed = SmartDashboard.getNumber("Nudge Launcher Speed", m_NudgeLauncherSpeed);
    m_NudgeSpinUpDelay = SmartDashboard.getNumber("Nudge Spin Up Delay", m_NudgeSpinUpDelay);
    m_NudgeShootTime = SmartDashboard.getNumber("Nudge Shoot Time", m_NudgeShootTime);

    // m_launcher.setLaunchWheel(m_NudgeLauncherSpeed);

    new SequentialCommandGroup(
            new InstantCommand(() -> m_launcher.setLaunchWheel(m_NudgeLauncherSpeed), m_launcher),
            new WaitCommand(m_NudgeSpinUpDelay),
            new InstantCommand(() -> m_launcher.setFeedWheel(m_NudgeFeederSpeed), m_launcher),
            new WaitCommand(m_NudgeShootTime),
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
