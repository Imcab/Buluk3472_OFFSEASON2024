// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.AngleIOKraken;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.AngleIOSim;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.Wheels;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.WheelsIO;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.WheelsIOSim;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.Subsystems.Shooter.Turret.TurretIO;
import frc.robot.Subsystems.Shooter.Turret.TurretIOSim;
import frc.robot.Subsystems.Shooter.Turret.TurretIOSparkMax;
import frc.robot.Subsystems.Swerve.Drive;
import frc.robot.Subsystems.Swerve.GyroIO;
import frc.robot.Subsystems.Swerve.GyroNavXIO;
import frc.robot.Subsystems.Swerve.ModuleIO;
import frc.robot.Subsystems.Swerve.ModuleIOSIM;
import frc.robot.Subsystems.Swerve.ModuleIOSparkMax;
import frc.robot.Subsystems.Vision.Vision;
import frc.robot.Subsystems.Vision.VisionIO;
import frc.robot.Subsystems.Vision.VisionLimelightIO;
import frc.robot.commands.DriveCommands.DriveCommands;
import frc.robot.commands.ElevatorCommands.ElevatorCommand;
import frc.robot.commands.ShooterCommands.AlignShooter;
import frc.robot.commands.ShooterCommands.AlignTurret;
import frc.robot.commands.ShooterCommands.Shoot;
import frc.robot.util.NoteVisualizer;
import frc.robot.Subsystems.Elevator.Elevator;
import frc.robot.Subsystems.Elevator.ElevatorIO;
import frc.robot.Subsystems.Elevator.ElevatorIOSIM;
import frc.robot.Subsystems.Elevator.ElevatorIOSparkMax;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.AngleIO;

public class RobotContainer {

  //controles
  //ps5 para pruebas en mi casa jajaj
  private final CommandPS5Controller controller = new CommandPS5Controller(0);
  private final CommandPS5Controller controller2 = new CommandPS5Controller(1);


  //xbox para el robot
  //private final CommandXboxController controller = new CommandXboxController(0);
  //private final CommandXboxController controller2 = new CommandXboxController(1);

  //subsistemas
  private final Drive drive;
  private final Turret turret;
  private final Vision vision;
  private final Angle shooterAngle;
  private final Elevator elevator;
  private final Wheels wheels;
  public Command amp;
  
  public RobotContainer() {
     

    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        drive =
            new Drive(
                new GyroNavXIO(),
                new ModuleIOSparkMax(0),
                new ModuleIOSparkMax(1),
                new ModuleIOSparkMax(2),
                new ModuleIOSparkMax(3));

        turret = new Turret(new TurretIOSparkMax());

        vision = new Vision(new VisionLimelightIO());

        shooterAngle = new Angle(new AngleIOKraken());

        elevator = new Elevator(new ElevatorIOSparkMax());

        wheels = new Wheels(new WheelsIO() {});

        


        break;

      case SIM:
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSIM(),
                new ModuleIOSIM(),
                new ModuleIOSIM(),
                new ModuleIOSIM());

        turret = new Turret(new TurretIOSim());

        vision = new Vision(new VisionIO(){});

        shooterAngle = new Angle(new AngleIOSim());

        elevator = new Elevator(new ElevatorIOSIM());

        wheels = new Wheels(new WheelsIOSim());

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

        turret = new Turret(new TurretIO() {});

        vision = new Vision(new VisionIO() {});

        shooterAngle = new Angle(new AngleIO() {});

        elevator = new Elevator(new ElevatorIO() {});

        wheels = new Wheels(new WheelsIO() {});

        break;
      
      }

    turret.setDefaultCommand(new AlignTurret(turret,
      ()-> -controller2.getLeftX(), shooterAngle
      
    ));

    shooterAngle.setDefaultCommand(new AlignShooter(shooterAngle, 
    ()-> -controller2.getLeftY()));


    ///For pathplanner//////

    NamedCommands.registerCommand("ShootFromSpeaker",
                                  new SequentialCommandGroup(
                                                          new ParallelCommandGroup(
                                                                                   new AlignTurret(turret, 0.0 , shooterAngle), 
                                                                                   new AlignShooter(shooterAngle, 50.0)
                                                          ), 
                                                          Commands.waitSeconds(0.2)
                                                          
                                                          
                                                          
                                                          
                                                          
                                                          
                                  ));


    //////////////////////// 
    
    ////Secuencias////////////
    amp = new SequentialCommandGroup(new ElevatorCommand(elevator, 0.370).alongWith(new ParallelCommandGroup(new AlignTurret(turret, 0.0, shooterAngle), new AlignShooter(shooterAngle, 40.0))));
    /////////////////////////
    configureBindings();
  }

  private void configureBindings() {

    

    //Drive
    drive.setDefaultCommand(DriveCommands.joystickDrive(drive, ()-> -controller.getLeftY(),  ()-> -controller.getLeftX(),  ()-> -controller.getRightX()));
    //////


    //controller2.a().whileTrue(new AlignTurret(turret, vision, shooterAngle));
    controller2.cross().whileTrue(new AlignTurret(turret, vision, shooterAngle));

    //controller2.b().whileTrue(new AlignTurret(turret, 90.0, shooterAngle));
    controller2.circle().whileTrue(new AlignTurret(turret, 90.0, shooterAngle));

    //controller2.y().whileTrue(new ParallelCommandGroup(new AlignTurret(turret, 0.0, shooterAngle), new AlignShooter(shooterAngle, 0.0)));

    controller2.triangle().whileTrue(new ParallelCommandGroup(new AlignTurret(turret, 0.0, shooterAngle)));

    //controller2.x().whileTrue(new AlignShooter(shooterAngle, 50.0));
    controller2.square().whileTrue(new AlignShooter(shooterAngle, 50.0));


    controller2.R1().whileTrue(new ParallelCommandGroup(new Shoot(wheels, 3000.0), NoteVisualizer.shoot()));
    controller2.L1().whileTrue(amp);
    ///
    //controller2.cross().whileTrue(NoteVisualizer.shoot());
  }

  public Command getAutonomousCommand() {
    //return Commands.print("No autonomous command configured");

    return new PathPlannerAuto("5pz");
  }
}
