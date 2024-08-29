package frc.robot.commands.ShooterCommands.Turret;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.Subsystems.Vision.Vision;

public class VisionTurret extends Command{

    private final Turret turret;
    private final Angle shooterangle;
    private final Vision limelightVision;
 
    public VisionTurret(Turret turret, Vision limelightVision,  Angle shooterangle){

        this.turret = turret;
        this.limelightVision = limelightVision;
        this.shooterangle = shooterangle;

        addRequirements(turret);
    }    
    @Override
    public void initialize(){}
    @Override
    public void execute(){

        shooterangle.UpdateTurretZ(turret.getTurretPosition());

        if (limelightVision.LimelighttargetFound() == true) {
            turret.runVision(limelightVision.getLimelightX());
        }
        
    }
    
    @Override
    public void end(boolean interrupted) {
        turret.stop();
    }

     @Override
    public boolean isFinished(){
        return false;
    } 
        
}
