package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Intake.Intake;

public class IntakeCommand extends Command{
    Intake intake;
    double RPM;
    public IntakeCommand(Intake intake, double RPM){
        this.intake = intake;
        this.RPM = RPM;
        addRequirements(intake);
    }
     @Override
    public void initialize(){
    }
    @Override
    public void execute(){
        intake.setIntake(RPM);      
    }
    @Override
    public boolean isFinished(){
        return false;
    }
    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}
