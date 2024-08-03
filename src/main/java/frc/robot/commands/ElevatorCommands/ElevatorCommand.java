package frc.robot.commands.ElevatorCommands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Elevator.Elevator;

public class ElevatorCommand extends Command{
    private final Elevator elevator;
    Double setpoint;

    public ElevatorCommand(Elevator elevator, Double setpoint){
        this.elevator = elevator;
        this.setpoint = setpoint;

        addRequirements(elevator);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        if(setpoint != null){
            elevator.ReachGoal(setpoint);
        }
        
    }

    @Override
    public boolean isFinished(){

        /*if(setpoint != null){
            if(elevator.getMeters() >=  setpoint - 0.05 &&  elevator.getMeters() <= setpoint + 0.05){

                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
        */
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
    }
}
