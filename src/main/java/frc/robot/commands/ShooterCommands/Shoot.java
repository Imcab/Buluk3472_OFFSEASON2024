package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer.Indexer;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.Wheels;

public class Shoot extends Command{
    private final Wheels wheels;
    private final Indexer indexer;
    Double setpointRPS;

    public Shoot(Wheels wheels, Indexer indexer, Double setpointRPS){
        this.wheels = wheels;
        this.indexer = indexer;
        this.setpointRPS = setpointRPS;
 
        addRequirements(wheels, indexer);
    }
   
    @Override
    public void initialize(){}

    @Override
    public void execute(){

        wheels.setGoalRPS(setpointRPS);

        if (wheels.getShooterRPS() >= (setpointRPS * 0.75)) {
            indexer.setSpeed(1);
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        if(DriverStation.isAutonomousEnabled() == false){
            wheels.stop();
            indexer.stop();
        }
        
    }

    @Override
    public boolean isFinished(){

        return false;
    }

}
