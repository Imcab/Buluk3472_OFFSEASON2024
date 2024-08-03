package frc.robot.commands.ComplexCommands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.commands.ShooterCommands.AlignShooter;
import frc.robot.commands.ShooterCommands.AlignTurret;

public class ComplexIntaking extends SequentialCommandGroup{

    final Turret turret;
    final Double turretsetpoint;

    final Angle shooterAngle;
    final Double shooterAnglesetpoint;

    ///IMPORTAR EL INTAKE//////////

    public ComplexIntaking(Turret turret, Double turretsetpoint, Angle shooterAngle, Double shooterAnglesetpoint){

        this.turret = turret;
        this.turretsetpoint = turretsetpoint;
        this.shooterAngle = shooterAngle;
        this.shooterAnglesetpoint = shooterAnglesetpoint;

        addRequirements(turret, shooterAngle);

        //añadir acción del intake
        addCommands(new ParallelCommandGroup(new AlignTurret(turret, Units.degreesToRadians(turretsetpoint), shooterAngle)), new AlignShooter(shooterAngle, Units.degreesToRadians(shooterAnglesetpoint)));
    }
    
}
