package frc.robot.Subsystems.Shooter.Shooter.Outake.Indexer;

import org.littletonrobotics.junction.AutoLog;


public interface IndexerIO {
    @AutoLog
    public class IndexerIOInputs{

        public double IndexerAppliedVolts = 0.0;
        public double IndexerVelocityRadPerSec = 0.0;
        public double IndexerVelocityRPM = 0.0;
        public double [] IndexerCurrentAmps = new double[] {};

    }
    
    /** Updates the set of loggable inputs. */
    public default void updateInputs(IndexerIOInputs inputs) {}

    /** Run the motor at the specified voltage. */
    public default void setIndexer(double voltage) {}

     public default void setIndexerBrakeMode(boolean enable) {}

}
