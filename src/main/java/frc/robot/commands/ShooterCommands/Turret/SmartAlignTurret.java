package frc.robot.commands.ShooterCommands.Turret;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.util.SmartTurret3472;

public class SmartAlignTurret extends Command{

    private final Turret turret;
    boolean isFinished;

    public SmartAlignTurret(Turret turret){
        this.turret = turret;
        addRequirements(turret);
    }    
    @Override
    public void initialize(){}
    @Override
    public void execute(){
        turret.runSmart();
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

    


