// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.LauncherConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANLauncher extends SubsystemBase {
  VictorSPX m_launchWheel;
  VictorSPX m_feedWheel;

  double m_feedWheelSpeed;
  double m_launchWheelSpeed;

  /** Creates a new Launcher. */
  public CANLauncher() {
    m_launchWheel = new VictorSPX(kLauncherID);
    m_feedWheel = new VictorSPX(kFeederID);

    m_launchWheel.setInverted(true);
    m_feedWheel.setInverted(true);

    m_launchWheel.setNeutralMode(NeutralMode.Brake);
    m_feedWheel.setNeutralMode(NeutralMode.Brake);
  }

  public Command getIntakeCommand() {
    // The startEnd helper method takes a method to call when the command is initialized and one to
    // call when it ends
    return this.startEnd(
        // When the command is initialized, set the wheels to the intake speed values
        () -> {
          setFeedWheel(kIntakeFeederSpeed);
          setLaunchWheel(kIntakeLauncherSpeed);
        },
        // When the command stops, stop the wheels
        () -> {
          stop();
        });
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Launcher wheel Speed", m_launchWheelSpeed);
    SmartDashboard.putNumber("Feed wheel Speed", m_feedWheelSpeed);
  }

  // An accessor method to set the speed (technically the output percentage) of the launch wheel
  public void setLaunchWheel(double speed) {
    m_launchWheel.set(ControlMode.PercentOutput, speed);
    m_launchWheelSpeed = speed;
  }

  // An accessor method to set the speed (technically the output percentage) of the feed wheel
  public void setFeedWheel(double speed) {
    m_feedWheel.set(ControlMode.PercentOutput, speed);
    m_feedWheelSpeed = speed;
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    m_launchWheel.set(ControlMode.PercentOutput, 0.0);
    m_feedWheel.set(ControlMode.PercentOutput, 0.0);
    m_feedWheelSpeed = 0.0;
    m_launchWheelSpeed = 0.0;
  }
}
