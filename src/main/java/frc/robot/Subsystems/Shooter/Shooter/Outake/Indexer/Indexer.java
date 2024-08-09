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
    private Double setpoint = null;

    public Indexer(IndexerIO io){
        this.io = io;
        setpoint = null;

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

        if (setpoint != null){
            double velocitysetpoint = Units.rotationsPerMinuteToRadiansPerSecond(setpoint);
            io.setIndexer(FeedForwardController.calculate(velocitysetpoint) + PIDController.calculate(inputs.IndexerVelocityRadPerSec, velocitysetpoint));
        }
    }

    public double setGoalRPM(double RPM){
        setpoint = RPM;
        return setpoint;
    }

    public void stop(){
        io.setIndexer(0.0);
        setpoint = null;
    }
    
    public void setIndexerBrakeMode(boolean enabled){
       io.setIndexerBrakeMode(enabled); 
    }


}
