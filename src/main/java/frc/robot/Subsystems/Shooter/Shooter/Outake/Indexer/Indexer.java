package frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Subsystems.Shooter.ShooterConstants.OutakeConstants;

public class Indexer extends SubsystemBase{
    private final IndexerIO io;
    private final IndexerIOInputsAutoLogged inputs = new IndexerIOInputsAutoLogged();
    private final PIDController PIDController;
    private final SimpleMotorFeedforward FeedForwardController;

    public Indexer(IndexerIO io){
        this.io = io;

        switch (Constants.currentMode) {
        case REAL:
        case REPLAY:
            PIDController = new PIDController(OutakeConstants.IkP, 0, OutakeConstants.IKD);
            FeedForwardController = new SimpleMotorFeedforward(0, OutakeConstants.IkV);
            break;
        case SIM:
            PIDController = new PIDController(0, 0, 0);
            FeedForwardController = new SimpleMotorFeedforward(0, 0);
            break;
        default:
            PIDController = new PIDController(0, 0, 0);
            FeedForwardController = new SimpleMotorFeedforward(0, 0);
            break;
        
        }

        setIndexerBrakeMode(true);    

    }

    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Shooter/Outake/Indexer", inputs);
        Logger.recordOutput("Shooter/Indexer/SetpointRPM", 0.0);
        Logger.recordOutput("Shooter/Indexer/SetpointRadPerSec", 0.0);
    }

    public void setGoalRPM(double RPM){
        double velocitysetpoint = Units.rotationsPerMinuteToRadiansPerSecond(RPM);
        Logger.recordOutput("Shooter/Indexer/SetpointRPM", Units.radiansPerSecondToRotationsPerMinute(velocitysetpoint));
        Logger.recordOutput("Shooter/Indexer/SetpointRadPerSec", velocitysetpoint);
        io.setIndexer(FeedForwardController.calculate(velocitysetpoint) + PIDController.calculate(inputs.IndexerVelocityRadPerSec, velocitysetpoint));
    }

    public void stop(){
        io.setIndexer(0.0);
    }
    
    public void setIndexerBrakeMode(boolean enabled){
       io.setIndexerBrakeMode(enabled); 
    }


}
