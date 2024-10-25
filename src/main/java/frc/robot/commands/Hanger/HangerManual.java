package frc.robot.commands.Hanger;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Hanger.Hanger;

public class HangerManual extends Command{

    Hanger hanger;
    double speed;

    public HangerManual(Hanger hanger, double speed){
        this.hanger = hanger;
        this.speed = speed;

        addRequirements(hanger);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute(){

        hanger.manualControl(speed);

    }
    
    @Override
    public void end(boolean interrupted) {
        hanger.manualControl(0);
    }
    
    @Override
    public boolean isFinished(){
        return false;
    }
}
