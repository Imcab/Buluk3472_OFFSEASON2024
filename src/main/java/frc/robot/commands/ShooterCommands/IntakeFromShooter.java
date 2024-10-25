package frc.robot.commands.ShooterCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer.Indexer;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.Wheels;

public class IntakeFromShooter extends Command{
    Wheels wheels;
    Indexer indexer;
    Debouncer debouncer = new Debouncer(0.29);
    DoubleSupplier supplier;

    public IntakeFromShooter(Wheels wheels,Indexer indexer, DoubleSupplier supplier){
        this.wheels = wheels;
        this.indexer = indexer;
        this.supplier = supplier;

        addRequirements(wheels);
    }
   
    @Override
    public void initialize(){}

    @Override
    public void execute(){

        if (supplier.getAsDouble() > 0.3 ) {
            wheels.setSpeed(-0.15);
            indexer.setSpeed(-0.1);
        }else{
            wheels.stop();
            indexer.stop();
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        wheels.stop();
        indexer.stop();
    }

    @Override
    public boolean isFinished(){
        return debouncer.calculate(!indexer.Trigger());        
    }
}
