package frc.robot.commands.ShooterCommands.Turret;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.util.SmartTurret3472;

public class SmartAlignTurret extends Command{

    private final Turret turret;
    private final Angle shooterangle;
    boolean isFinished;

    //con enncoder
    public SmartAlignTurret(Turret turret,  Angle shooterangle){

        this.turret = turret;
        this.shooterangle = shooterangle;
        addRequirements(turret);
    }    
    @Override
    public void initialize(){}
    @Override
    public void execute(){

        shooterangle.UpdateTurretZ(turret.getTurretPosition());

        turret.runSmart();
    }
    
    @Override
    public void end(boolean interrupted) {
        turret.stop();
    }

     @Override
    public boolean isFinished(){
 
            if(SmartTurret3472.getOmega() >= -0.010  && SmartTurret3472.getOmega() <=  0.010 ){
                isFinished = true;
                System.out.println("Smart");
                return isFinished;
            }else{
                isFinished = false;
                System.out.println("No smart");
                return isFinished;
            }
    } 
}

    


