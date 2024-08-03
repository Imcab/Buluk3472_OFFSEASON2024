package frc.robot.Subsystems.Elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Subsystems.Elevator.ConstantsElevator.ElevatorConstants;

public class Elevator extends SubsystemBase{
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();
    private final PIDController FeedBackController;
    private final SimpleMotorFeedforward ff;
    private Double setpoint = null;

    public Elevator(ElevatorIO io){

        this.io = io;
        setpoint = null;

        switch (Constants.currentMode) {
      case REAL:
      case REPLAY:
        FeedBackController = new PIDController(ElevatorConstants.kP, 0, ElevatorConstants.KD);
        ff = new SimpleMotorFeedforward(0, ElevatorConstants.kV);
        break;
      case SIM:
        FeedBackController = new PIDController(ElevatorConstants.kPSIM, 0, ElevatorConstants.KDSIM);
        ff = new SimpleMotorFeedforward(0, ElevatorConstants.kVSim);
        break;
      default:
        FeedBackController = new PIDController(0, 0, 0);
        ff = new SimpleMotorFeedforward(0, 0);
        break;
        }

        setBrakeMode(true);
    }
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Elevator" , inputs);
        Logger.recordOutput("Elevator/pose3d" , getPose3d());

        SmartDashboard.putNumber("ElevatorMeter", inputs.ElevatorMeters);

        if(setpoint != null){
            io.setElevator(ff.calculate(setpoint / 60)  +  FeedBackController.calculate(inputs.ElevatorMeters, setpoint));            
        }
    }
    public Double getMeters(){
      return inputs.ElevatorMeters;
    }
    public Double ReachGoal(double goal){
        setpoint = goal;
        return setpoint;
    }

    public Pose3d getPose3d(){
        return new Pose3d(0, 0, inputs.ElevatorMeters, new Rotation3d());
    }
    public void stop() {
        io.setElevator(0.0);
        setpoint = null;
      }
    public void setBrakeMode(boolean enabled) {
        io.setElevatorBrakeMode(enabled);
      }
    
}
