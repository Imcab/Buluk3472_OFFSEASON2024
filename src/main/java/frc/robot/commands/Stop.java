package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Shooter.Turret.Turret;

public class Stop extends Command{
    Angle angle;
    Turret turret;
    public Stop(Angle angle, Turret turret){

        this.angle = angle;
        this.turret = turret;
    }
    @Override
    public void initialize(){}

    @Override
    public void execute(){

        angle.stop();
        turret.stop();
        
    }

    @Override
    public boolean isFinished(){

        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
