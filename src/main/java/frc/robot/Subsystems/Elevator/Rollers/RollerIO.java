package frc.robot.Subsystems.Elevator.Rollers;
import org.littletonrobotics.junction.AutoLog;

public interface RollerIO {
    @AutoLog  
    public static class RollerIOInputs {
    
      public double RollerAppliedVolts = 0.0;
      public double RollerVelocityRPM = 0;
      public double [] ElevatorCurrentAmps = new double[] {};
        
    }
    
    /** Updates the set of loggable inputs. */
  public default void updateInputs(RollerIOInputs inputs) {}

  /** Run the motor at the specified voltage. */
  public default void setRoller(double voltage) {}
    
} 
    

