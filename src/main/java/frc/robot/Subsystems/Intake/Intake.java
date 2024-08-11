package frc.robot.Subsystems.Intake;

import org.littletonrobotics.junction.Logger;

//import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Subsystems.Intake.ConstantsIntake.IntakeCosntants;

public class Intake extends SubsystemBase{

    private final IntakeIO io; 
    private final IntakeIOInputAutologged inputs = new IntakeIOInputAutologged(); 
    private final Double setpoint = null; 

    public Intake(IntakeIO io){

        this.io = io;
        setpoint = null;

        switch (Constants.currentMode) {

         case REAL:
         case REPLAY:
             FeedBackController = new PIDController(ElevatorConstants.kP, 0, ElevatorConstants.KD);
             II = new SimpleMotorFeedforward(0, ElevatorConstants.kV);
             break;
         case SIM:
             FeedBackController = new PIDController(ElevatorConstants.kPSIM, 0, ElevatorConstants.KDSIM);
             II = new SimpleMotorFeedforward(0, ElevatorConstants.kVSim);
             break;
      default:
          FeedBackController = new PIDController(0, 0, 0);
           II = new SimpleMotorFeedforward(0, 0);
           break;

        }
         setBrakeMode(true);
    }

    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);
        Logger.recordOutput("Intake/pose3d", getPose3d());
    }
    

    public Pose3d getPose3d(){
        return new Pose3d(0, 0, inputs.ElevatorMeters, new Rotation3d());
    }

    public void stop() {
        io.setIntake(0.0);
        setpoint = null;
      }

     public void setBrakeMode(boolean enabled) {
        io.setIntakeBrakeMode(enabled);
      }

}
