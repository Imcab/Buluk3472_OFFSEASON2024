package frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Subsystems.Shooter.ShooterConstants.OutakeConstants;

public class IndexerIOSparkMax implements IndexerIO{
    
    private final CANSparkMax Indexer;
    private final RelativeEncoder Encoder;
    private final DigitalInput BeamSensor;

    public IndexerIOSparkMax(){
        Indexer = new CANSparkMax(OutakeConstants.IndexerPort, MotorType.kBrushless);

        Indexer.restoreFactoryDefaults();

        Indexer.setCANTimeout(250);

        Encoder = Indexer.getEncoder();

        Indexer.setInverted(OutakeConstants.IndexerMotorReversed);

        Indexer.enableVoltageCompensation(12.0);

        Encoder.setPosition(0.0);
        Encoder.setMeasurementPeriod(10);
        Encoder.setAverageDepth(2);

        Indexer.setCANTimeout(0);
        
        Indexer.burnFlash();

        BeamSensor = new DigitalInput(OutakeConstants.BEAMSENSOR_RECIEVER_DIOPIN);

    }

    @Override
    public void updateInputs(IndexerIOInputs inputs){
        inputs.IndexerAppliedVolts = Indexer.getAppliedOutput() * Indexer.getBusVoltage();
        inputs.IndexerVelocityRPM = Encoder.getVelocity();
        inputs.IndexerVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(inputs.IndexerVelocityRPM);
        inputs.IndexerCurrentAmps = new double[]{Indexer.getOutputCurrent()};

        if (!BeamSensor.get()) {
            inputs.BeamSensor = true; 
        }
        
        inputs.BeamSensor = false;

        
    }

    @Override
    public void setIndexer(double voltage){
        Indexer.setVoltage(voltage);
    }

    @Override
    public void setIndexerBrakeMode(boolean enable){
        Indexer.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    }

}
