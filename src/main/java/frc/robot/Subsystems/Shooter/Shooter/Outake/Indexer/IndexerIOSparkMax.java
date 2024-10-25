package frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Subsystems.Shooter.ShooterConstants.OutakeConstants;

public class IndexerIOSparkMax implements IndexerIO{
    
    private final CANSparkMax Indexer;
    private final DigitalInput BeamSensor;

    public IndexerIOSparkMax(){
        Indexer = new CANSparkMax(OutakeConstants.IndexerPort, MotorType.kBrushless);

        Indexer.setInverted(OutakeConstants.IndexerMotorReversed);

        BeamSensor = new DigitalInput(OutakeConstants.BEAMSENSOR_RECIEVER_DIOPIN);

    }

    @Override
    public void updateInputs(IndexerIOInputs inputs){
        inputs.BeamSensor = BeamSensor.get();        
    }

    @Override
    public void setIndexer(double speed){
        Indexer.set(speed);
    }

    @Override
    public void setIndexerBrakeMode(boolean enable){
        Indexer.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    }

}
