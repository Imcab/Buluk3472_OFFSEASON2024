package frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase{
    private final IndexerIO io;
    private final IndexerIOInputsAutoLogged inputs = new IndexerIOInputsAutoLogged();

    public Indexer(IndexerIO io){
        this.io = io;
        setIndexerBrakeMode(true);    

    }

    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Shooter/Outake/Indexer", inputs);
        SmartDashboard.putBoolean("Sensor", Trigger());
    }

    public void setSpeed(double speed){
        io.setIndexer(speed);
    }

    public void stop(){
        io.setIndexer(0.0);
    }
    
    public void setIndexerBrakeMode(boolean enabled){
       io.setIndexerBrakeMode(enabled); 
    }

    public boolean Trigger(){
        return inputs.BeamSensor;
    }

}
