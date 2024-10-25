// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.AngleIO;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.AngleIOKraken;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.AngleIOSim;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer.Indexer;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer.IndexerIO;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer.IndexerIOSparkMax;
import frc.robot.Subsystems.Hanger.Hanger;
import frc.robot.Subsystems.Hanger.HangerIO;
import frc.robot.Subsystems.Hanger.HangerIOSparkMax;
import frc.robot.Subsystems.Intake.Intake;
import frc.robot.Subsystems.Intake.IntakeIO;
import frc.robot.Subsystems.Intake.IntakeIOSparkMax;
import frc.robot.Subsystems.Swerve.Drive;
import frc.robot.Subsystems.Swerve.GyroIO;
import frc.robot.Subsystems.Swerve.GyroNavXIO;
import frc.robot.Subsystems.Swerve.ModuleIO;
import frc.robot.Subsystems.Swerve.ModuleIOSIM;
import frc.robot.Subsystems.Swerve.ModuleIOSparkMax;
import frc.robot.Subsystems.Vision.Vision;
import frc.robot.Subsystems.Vision.VisionSystemIO;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.Subsystems.Shooter.Turret.TurretIO;
import frc.robot.Subsystems.Shooter.Turret.TurretIOSim;
import frc.robot.Subsystems.Shooter.Turret.TurretIOSparkMax;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.Wheels;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.WheelsIO;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.WheelsIOKraken;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.WheelsIOSim;
import frc.robot.commands.ShooterCommands.IntakeCommand;
import frc.robot.commands.ShooterCommands.IntakeFromShooter;
import frc.robot.commands.ShooterCommands.Shoot;
import frc.robot.commands.ShooterCommands.Angle.AlignJoystick;
import frc.robot.commands.ShooterCommands.Angle.AlignShooter;
import frc.robot.commands.ShooterCommands.Angle.AlignVision;
import frc.robot.commands.ShooterCommands.Turret.AlignTurret;
import frc.robot.commands.ShooterCommands.Turret.JoystickTurret;
import frc.robot.commands.ShooterCommands.Turret.SmartAlignTurret;
import frc.robot.commands.ShooterCommands.Turret.VisionTurret;
import frc.robot.commands.Stop;
//import frc.robot.Subsystems.Vision.PhotonvisionIOSIM;
import frc.robot.commands.DriveCommands.DriveCommands;
import frc.robot.commands.DriveCommands.ResetHeading;
import frc.robot.commands.DriveCommands.SwerveAutoAlign;
import frc.robot.commands.Hanger.HangerManual;
import frc.robot.util.NoteVisualizer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;

public class RobotContainer {

  private final CommandXboxController controller = new CommandXboxController(0);
  private final CommandXboxController controller2 = new CommandXboxController(1);

  //subsistemas
  private final Drive drive;
  private final Intake intake;
  private final Turret turret;
  private final Wheels wheels;
  private final Indexer index;
  private final Angle angle;
  private final Hanger hanger;
  private final Vision vision;

  SendableChooser<Command> m_chooser = new SendableChooser<>(); //for autonomous

  public RobotContainer() {

    

    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        drive =
            new Drive(
                new GyroNavXIO() {},
                new ModuleIOSparkMax(0),
                new ModuleIOSparkMax(1),
                new ModuleIOSparkMax(2),
                new ModuleIOSparkMax(3));

        intake = new Intake(new IntakeIOSparkMax());

        turret = new Turret(new TurretIOSparkMax());

        wheels = new Wheels(new WheelsIOKraken());

        index = new Indexer(new IndexerIOSparkMax());

        angle = new Angle(new AngleIOKraken());

        hanger = new Hanger(new HangerIOSparkMax());

        vision = new Vision(new VisionSystemIO());

        break;

      case SIM:
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSIM(),
                new ModuleIOSIM(),
                new ModuleIOSIM(),
                new ModuleIOSIM());
        intake = new Intake(new IntakeIO() {});
        
        turret = new Turret(new TurretIOSim());

        wheels = new Wheels(new WheelsIOSim());

        index = new Indexer(new IndexerIO() {});

        angle = new Angle(new AngleIOSim());

        hanger = new Hanger(new HangerIO() {});

        vision = new Vision(new VisionSystemIO());

        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});

        intake = new Intake(new IntakeIO() {});

        turret = new Turret(new TurretIO() {});

        wheels = new Wheels(new WheelsIO() {});

        index = new Indexer(new IndexerIO() {});

        angle = new Angle(new AngleIO() {});

        hanger = new Hanger(new HangerIO() {});

        vision = new Vision(new VisionSystemIO());

        break;
      
    }

    ///REGISTRAR COMANDOS POR NOMBRE (PARA AUTONOMO Y NORMAL)//////
    
    //////////////////////// 
    //------AUTO_REGISTER---//
    //--------------------//
    ///////////////////////

    configureBindings();

  }

  private void configureBindings() {

    ////-----------------Main Driver Triggers-------------------------

    //Drive the chassis
    drive.setDefaultCommand(DriveCommands.joystickDrive(drive, ()-> -controller.getLeftY(),  ()-> -controller.getLeftX(),  ()-> -controller.getRightX()));
    //////

    //Resetea la pose del robot despues de presionar 1.5 segundos el boton de options
    controller.start().debounce(0.15).whileTrue(new ResetHeading(drive));

    //Intake
    controller.a().whileTrue(new IntakeCommand(intake, index,turret,442, 0.2,false));
    controller.b().whileTrue(new IntakeCommand(intake,index,turret, -442, -0.2,true));

    //Hanger
    controller.rightBumper().whileTrue(new HangerManual(hanger, 0.9));
    controller.leftBumper().whileTrue(new HangerManual(hanger, -0.9));

    wheels.setDefaultCommand(new IntakeFromShooter(wheels, index, ()-> controller.getLeftTriggerAxis()));
    //-----------------Mechanism Driver Triggers-------------------------

    //Shoot
    controller2.rightBumper().whileTrue(new Shoot(wheels, index, 90.0));

    controller2.a().toggleOnTrue(new AlignShooter(angle, 25.0));

    //controller2.b().whileTrue(new AlignTurret(turret, 80.0));

    turret.setDefaultCommand(new JoystickTurret(turret,()-> -controller2.getRightX() * 0.05));

    angle.setDefaultCommand(new AlignJoystick(angle, ()-> -controller2.getLeftY() * 0.6));
  
    controller2.povLeft().whileTrue(new Stop(angle, turret));

    controller2.y().whileTrue(new AlignVision(vision, angle));

    controller2.x().whileTrue(new SmartAlignTurret(turret));

    controller2.b().whileTrue(new VisionTurret(turret, vision));

  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
