package frc.robot.Subsystems.Intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase{

    private final IntakeIO io; 
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    private final SimpleMotorFeedforward FF;
    private final PIDController FeedBackController;
    private Double setpoint;


    public Intake(IntakeIO io){

         this.io = io;
         this.setpoint = null;

        switch (Constants.currentMode) {

         case REAL:
         case REPLAY:
             FeedBackController = new PIDController(ConstantsIntake.IntakeConstants.kp, 0, 0.0);
             FF = new SimpleMotorFeedforward(0, ConstantsIntake.IntakeConstants.kv);
             break;
         case SIM:
             FeedBackController = new PIDController(0, 0, 0);
             FF = new SimpleMotorFeedforward(0, 0);
             break;
      default:
          FeedBackController = new PIDController(0, 0, 0);
           FF = new SimpleMotorFeedforward(0, 0);
           break;

        }
         setBrakeMode(true);
    }

    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);

        if(setpoint != null){
            io.setIntake(FF.calculate(Units.rotationsPerMinuteToRadiansPerSecond(setpoint)) + FeedBackController.calculate(inputs.IntakeVelocityRadPerSec, Units.rotationsPerMinuteToRadiansPerSecond(setpoint)));
        }
        
    }
    
    public void stop() {
        io.setIntake(0.0);
        setpoint = null;
      }

     public void setBrakeMode(boolean enabled) {
        io.setIntakeBrakeMode(enabled);
      }

} 
