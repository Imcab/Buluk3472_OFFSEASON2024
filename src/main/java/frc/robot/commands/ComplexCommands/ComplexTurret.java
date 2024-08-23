package frc.robot.commands.ComplexCommands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
//import frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer.Indexer;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.Wheels;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.Subsystems.Vision.Vision;
import frc.robot.commands.ShooterCommands.AlignShooter;
import frc.robot.commands.ShooterCommands.AlignTurret;
import frc.robot.commands.ShooterCommands.Shoot;
import frc.robot.util.NoteVisualizer;

public class ComplexTurret extends SequentialCommandGroup{

    final Turret turret;
    final Double turretsetpoint;

    final Angle shooterAngle;
    final Double shooterAnglesetpoint;

    final Vision limelight;

    final Wheels wheels;
    final Double wheelssetpointRPM;

    //final Indexer indexer;
    //final Double indexerpower;


    //Alinear torreta, angular y disparar (Con Encoder)
    public ComplexTurret(Turret turret, Double turretSetpoint, Angle shooterAngle, Double shooterAnglesetpoint, Wheels wheels, Double wheelssetpointRPM){

            this.turret = turret;
            this.turretsetpoint = turretSetpoint;
            this.shooterAngle = shooterAngle;
            this.shooterAnglesetpoint = shooterAnglesetpoint;
            this.limelight = null;
            this.wheels = wheels;
            this.wheelssetpointRPM = wheelssetpointRPM;

            addRequirements(turret, shooterAngle, wheels);

            addCommands(new ParallelCommandGroup(new AlignTurret(turret, Units.degreesToRadians(turretSetpoint), shooterAngle), new AlignShooter(shooterAngle, Units.degreesToRadians(shooterAnglesetpoint))).andThen( new Shoot(wheels, wheelssetpointRPM).andThen(NoteVisualizer.shoot())));
    
    }
    

}
