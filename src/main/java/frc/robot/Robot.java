// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.util.NoteVisualizer;
import frc.robot.util.RobotUtil3472.DRIVERSTATION;
import frc.robot.util.RobotUtil3472.REV_PDH;
import frc.robot.util.RobotUtil3472.ROBOTSTATE;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

public class Robot extends LoggedRobot {


  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {

     Logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
    Logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
    Logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
    Logger.recordMetadata("GitDate", BuildConstants.GIT_DATE);
    Logger.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);
    switch (BuildConstants.DIRTY) {
      case 0:
        Logger.recordMetadata("GitDirty", "All changes committed");
        break;
      case 1:
        Logger.recordMetadata("GitDirty", "Uncomitted changes");
        break;
      default:
        Logger.recordMetadata("GitDirty", "Unknown");
        break;
    }

    // Set up data receivers & replay source
    switch (Constants.currentMode) {
      case REAL:
        // Running on a real robot, log to a USB stick ("/U/logs")
        Logger.addDataReceiver(new WPILOGWriter());
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case SIM:
        // Running a physics simulator, log to NT
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case REPLAY:
        // Replaying a log, set up replay source
        setUseTiming(false); // Run as fast as possible
        String logPath = LogFileUtil.findReplayLog();
        Logger.setReplaySource(new WPILOGReader(logPath));
        Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
        break;
    }


    // Start AdvantageKit logger
    Logger.start();
    
    m_robotContainer = new RobotContainer();
  }

  public PowerDistribution PDH(){
    PowerDistribution PDH = new PowerDistribution(REV_PDH.PDH_CAN_ID, ModuleType.kRev);
    return PDH;
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    //OBTIENE LA PDH/////////
    REV_PDH.getPDH(this::PDH);
    /////////////////////////

    //DRIVER STATION LOGGING//
    Logger.recordOutput("RobotUtil3472/DriverStation/RemainingTime", DRIVERSTATION.getRemainingTime());
    Logger.recordOutput("RobotUtil3472/DriverStation/MatchType", DRIVERSTATION.getMatch());
    Logger.recordOutput("RobotUtil3472/DriverStation/Status/Auto/Auto", DRIVERSTATION.isAuto());
    Logger.recordOutput("RobotUtil3472/DriverStation/Status/Auto/AutoEnabled", DRIVERSTATION.isAutonomousEnabled());
    Logger.recordOutput("RobotUtil3472/DriverStation/Status/Teleop/Teleop", DRIVERSTATION.isTeleop());
    Logger.recordOutput("RobotUtil3472/DriverStation/Status/Teleop/TeleopEnabled", DRIVERSTATION.isTeleopEnabled());
    Logger.recordOutput("RobotUtil3472/DriverStation/Status/RobotEnabled", DRIVERSTATION.isRobotEnabled());
    Logger.recordOutput("RobotUtil3472/DriverStation/Alliance/Blue", DRIVERSTATION.isBlue());
    Logger.recordOutput("RobotUtil3472/DriverStation/Alliance/Red", DRIVERSTATION.isRed());
    //ROBORIO LOGGING//
    Logger.recordOutput("RobotUtil3472/RoboRio/Electrical/BatteryVoltage", ROBOTSTATE.getBatteryVoltage());
    Logger.recordOutput("RobotUtil3472/RoboRio/Electrical/Current", ROBOTSTATE.getCurrent());
    Logger.recordOutput("RobotUtil3472/RoboRio/Electrical/Voltage", ROBOTSTATE.getVoltage());
    Logger.recordOutput("RobotUtil3472/RoboRio/Electrical/IsBrownout", ROBOTSTATE.isSystemBrownout());
    Logger.recordOutput("RobotUtil3472/RoboRio/Temp", ROBOTSTATE.getTemperature());
    Logger.recordOutput("RobotUtil3472/RoboRio/CAN/Utilization/CAN", ROBOTSTATE.getCANUtilization());
    Logger.recordOutput("RobotUtil3472/RoboRio/CAN/Utilization/BUS", ROBOTSTATE.percentBusUtilization());
    Logger.recordOutput("RobotUtil3472/RoboRio/CAN/BussOffCount", ROBOTSTATE.busOffCount());
    Logger.recordOutput("RobotUtil3472/RoboRio/CAN/ErrorCount/recieve", ROBOTSTATE.receiveErrorCount());
    Logger.recordOutput("RobotUtil3472/RoboRio/CAN/ErrorCount/transmit", ROBOTSTATE.transmitErrorCount());
    Logger.recordOutput("RobotUtil3472/RoboRio/CAN/txFullCount", ROBOTSTATE.txFullCount());
    //PDH LOGGING//
    Logger.recordOutput("RobotUtil3472/PDH/Channels", REV_PDH.getPDHChannels());
    Logger.recordOutput("RobotUtil3472/PDH/Current", REV_PDH.getPDH_AMPS());
    Logger.recordOutput("RobotUtil3472/PDH/Energy", REV_PDH.getPDH_Energy());
    Logger.recordOutput("RobotUtil3472/PDH/Temp", REV_PDH.getPDH_Temp());
    Logger.recordOutput("RobotUtil3472/PDH/Watts", REV_PDH.getPDH_Watts());
    
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }

    NoteVisualizer.resetAutoNotes();

    
  }

  @Override
  public void autonomousPeriodic() {
    NoteVisualizer.showAutoNotes();
  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    NoteVisualizer.clearAutoNotes();
    NoteVisualizer.showAutoNotes();
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {
  }

}
