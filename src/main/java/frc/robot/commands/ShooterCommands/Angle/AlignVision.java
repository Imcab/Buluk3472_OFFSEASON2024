package frc.robot.commands.ShooterCommands.Angle;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Vision.Vision;

public class AlignVision extends Command{
    private final Vision limelightVision;
    private final Angle shooterangle;

    public AlignVision(Vision limelightVision, Angle shooterangle){
        this.limelightVision = limelightVision;
        this.shooterangle = shooterangle;

        addRequirements(shooterangle,limelightVision);
    }

    @Override
    public void initialize(){}
    @Override
    public void execute(){

        boolean targetFound = limelightVision.LimelighttargetFound();

        if(targetFound == true){
            shooterangle.RunVisionStatus(limelightVision.getLimelightY());

        }else{
            shooterangle.stop();
        }
        
    }
    
    @Override
    public void end(boolean interrupted) {
            shooterangle.stop();  
    }
    
    @Override
    public boolean isFinished(){
        return false;
    }

}
