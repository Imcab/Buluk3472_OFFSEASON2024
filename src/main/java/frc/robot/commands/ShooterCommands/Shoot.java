package frc.robot.commands.ShooterCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels.Wheels;
import frc.robot.util.NoteVisualizer;

public class Shoot extends Command{
    private final Wheels wheels;
    Double setpointRPM;
    DoubleSupplier axisSupplier;


    ///Con PID + FEEDFORWARD
    public Shoot(Wheels wheels, Double setpointRPM){
        this.wheels = wheels;
        this.setpointRPM = setpointRPM;
        this.axisSupplier = null;

        addRequirements(wheels);
    }
    //Con Gatillo
    public Shoot(Wheels wheels, DoubleSupplier axisSupplier){
        this.wheels = wheels;
        this.setpointRPM = null;
        this.axisSupplier = axisSupplier;

        addRequirements(wheels);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute(){
        if(setpointRPM != null){
            wheels.setGoalRPM(setpointRPM);
        }
    }

    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished(){

        if(setpointRPM != null){
            if(wheels.getShooterRPM() >= setpointRPM -50){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
