package frc.robot.commands.ShooterCommands;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Subsystems.Intake.Intake;
import frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer.Indexer;
import frc.robot.Subsystems.Shooter.Turret.Turret;

public class IntakeCommand extends Command{
    Intake intake;
    Indexer index;
    Turret turret;
    double IndexerSpeed;
    double RPM;
    boolean reverse;
    Debouncer debouncer = new Debouncer(0.1);

    public IntakeCommand(Intake intake, Indexer index, Turret turret,double RPM, double IndexerSpeed, boolean reverse){
        this.intake = intake;
        this.index = index;
        this.turret = turret;
        this.RPM = RPM;
        this.IndexerSpeed = IndexerSpeed;
        this.reverse = reverse;
        addRequirements(intake, index);
    }
     @Override
    public void initialize(){
    }
    @Override
    public void execute(){
        intake.setIntake(RPM);  
        index.setSpeed(IndexerSpeed);
        turret.runTurret(1.0);  

    }
    @Override
    public boolean isFinished(){
        if (reverse) {
            return false;
        }else{
            return debouncer.calculate(!index.Trigger());
        }
    }
    @Override
    public void end(boolean interrupted) {
        intake.stop();
        index.stop();
        turret.stop();
    }
}
