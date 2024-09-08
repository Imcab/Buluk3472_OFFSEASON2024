// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.choreo.lib.Choreo;
import com.choreo.lib.ChoreoTrajectory;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
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
import frc.robot.Subsystems.Vision.VisionSystemIO;
import frc.robot.commands.ComplexCommands.ComplexIntaking;
import frc.robot.commands.ComplexCommands.ComplexTurret;
import frc.robot.commands.DriveCommands.DriveCommands;
import frc.robot.commands.ElevatorCommands.ElevatorCommand;
import frc.robot.commands.ShooterCommands.AlignShooter;
import frc.robot.commands.ShooterCommands.Shoot;
import frc.robot.commands.ShooterCommands.Turret.AlignTurret;
import frc.robot.commands.ShooterCommands.Turret.SmartAlignTurret;
import frc.robot.util.NoteVisualizer;
import frc.robot.Subsystems.Elevator.Elevator;
import frc.robot.Subsystems.Elevator.ElevatorIO;
import frc.robot.Subsystems.Elevator.ElevatorIOSIM;
import frc.robot.Subsystems.Elevator.ElevatorIOSparkMax;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.AngleIO;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;

public class RobotContainer {

  //controles
  //ps5 para pruebas en mi casa jajaj
  private final CommandPS5Controller controller = new CommandPS5Controller(0);
  private final CommandPS5Controller controller2 = new CommandPS5Controller(1);


  //xbox para el robot
  //private final CommandXboxController controller = new CommandXboxController(0);
  //private final CommandXboxController controller2 = new CommandXboxController(1);

  //Cancha
  Field2d field = new Field2d();

  ChoreoTrajectory CenterSpike;

  //subsistemas
  private final Drive drive;
  private final Turret turret;
  private final Vision vision;
  private final Angle shooterAngle;
  private final Elevator elevator;
  private final Wheels wheels;

  SendableChooser<Command> m_chooser = new SendableChooser<>(); //for autonomous

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

        vision = new Vision(new VisionSystemIO());

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

    shooterAngle.setDefaultCommand(new AlignShooter(shooterAngle ,()-> -controller2.getLeftY()));

    PathPlannerPath path1 = PathPlannerPath.fromChoreoTrajectory("3spike.1");
    PathPlannerPath path2 = PathPlannerPath.fromChoreoTrajectory("3spike.2");
    PathPlannerPath path3 = PathPlannerPath.fromChoreoTrajectory("3spike.3");

 
    ///REGISTRAR COMANDOS POR NOMBRE (PARA AUTONOMO Y NORMAL)//////
    NamedCommands.registerCommand("ShootFromSpeaker", 
    new ComplexTurret(turret, 0.0, shooterAngle, 50.0, wheels, 5000.0));

    NamedCommands.registerCommand("Intaking", 
    new ComplexIntaking(turret, 0.0, shooterAngle, 35.0));

    NamedCommands.registerCommand("Shoot",
    new ComplexTurret(turret, 110.0, shooterAngle, 32.0, wheels, 5000.0));
    
    NamedCommands.registerCommand("ShootFromLine", 
    new ComplexTurret(turret, 91.0,  shooterAngle,  32.0, wheels, 5000.0));

    NamedCommands.registerCommand("ShootFromFar",
    new ComplexTurret(turret, 110.0, shooterAngle, 18.0, wheels, 5000.0));

     NamedCommands.registerCommand("ShootFromFar90",
    new ComplexTurret(turret, 114.0, shooterAngle, 18.0, wheels, 5000.0));

    NamedCommands.registerCommand("Oox2pzShoot",
    new ComplexTurret(turret, 120.0, shooterAngle, 32.0, wheels, 5000.0));

    NamedCommands.registerCommand("Amp", null);

    NamedCommands.registerCommand("AutoAlignTurret", new SmartAlignTurret(turret, shooterAngle));

    NamedCommands.registerCommand("SmartShoot", new SequentialCommandGroup(new SmartAlignTurret(turret, shooterAngle), new AlignShooter(shooterAngle, Units.degreesToRadians(32.0))).andThen( new Shoot(wheels, 5000.0).andThen(NoteVisualizer.shoot())));
    //////////////////////// 


    m_chooser.addOption("6 Notes Auto (Wak)", new PathPlannerAuto("Wak"));
    m_chooser.addOption("3 Center Notes Auto (Oox)", new PathPlannerAuto("Oox"));
    m_chooser.addOption("SECRETT", new PathPlannerAuto("SECRETTT"));
    m_chooser.addOption("3Spike", new PathPlannerAuto("CenterSpike"));
    SmartDashboard.putData(m_chooser);

    configureBindings();


  }

  private void configureBindings() {

    //Drive
    drive.setDefaultCommand(DriveCommands.joystickDrive(drive, ()-> -controller.getLeftY(),  ()-> -controller.getLeftX(),  ()-> -controller.getRightX()));
    //////

    //controller2.L1().whileTrue(NamedCommands.getCommand("Amp"));

    controller2.cross().whileTrue(NamedCommands.getCommand("AutoAlignTurret"));
    controller2.triangle().whileTrue(NamedCommands.getCommand("ShootFromSpeaker"));
    controller2.circle().whileTrue(new AlignTurret(turret, -15.0, shooterAngle));

  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
