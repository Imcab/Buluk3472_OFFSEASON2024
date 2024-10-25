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

        addRequirements(limelightVision, shooterangle);
    }

    @Override
    public void initialize(){
        limelightVision.blink();
    }
    @Override
    public void execute(){

        boolean targetFound = limelightVision.LimelighttargetFound();

        if(targetFound){
            
            shooterangle.RunVisionStatus(limelightVision.getLimelightY());

        }     
    }
    
    @Override
    public void end(boolean interrupted) {
        if (shooterangle.getDegrees() <= 7 && shooterangle.getDegrees() > 2){
            shooterangle.runSpeed(-0.1);
        }else{
            shooterangle.stop();
        }
              
    }
    
    @Override
    public boolean isFinished(){
        return false;
    }

}
