package frc.robot.Subsystems.Hanger;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

import frc.robot.Constants;
import frc.robot.Subsystems.Shooter.ShooterConstants.TurretConstants;
import frc.robot.util.HPPMathLib;

public class Hanger extends SubsystemBase {

    private final HangerIO io;
    private final HangerIOInputsAutoLogged inputs = new HangerIOInputsAutoLogged();
    private final PIDController PIDController;
    private final SimpleMotorFeedforward FeedForwardController;

    public Hanger(HangerIO io){

        this.io = io;

        switch (Constants.currentMode) {
            case REAL:
            case REPLAY:
                PIDController = new PIDController(ConstantsHanger.kP, 0, ConstantsHanger.KD);
                FeedForwardController = new SimpleMotorFeedforward(ConstantsHanger.kS , ConstantsHanger.kV);
                break;
            case SIM:
                PIDController = new PIDController(TurretConstants.kPSIM, 0, TurretConstants.KDSIM);
                FeedForwardController = new SimpleMotorFeedforward(ConstantsHanger.kS_SIM , ConstantsHanger.kV_SIM);
                break;
            default:
                PIDController = new PIDController(0, 0, 0);
                FeedForwardController = new SimpleMotorFeedforward(0 , 0);
                break;
        }
        PIDController.enableContinuousInput(-Math.PI, Math.PI);

        setBrakeMode(true);
    }

    public void setBrakeMode(boolean enabled) {
        io.setHangerBrakeMode(enabled);
    }
    
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Hanger", inputs);

        Logger.recordOutput("Hanger/PIDVALUE", new Rotation2d().getRadians());
    }

    public void moveHanger(double realAngle, double desireAngle){
        io.setHanger(PIDController.calculate(HPPMathLib.MinAngleDeg(realAngle, desireAngle)));
    }
    public void stop(){
        io.setHanger(0.0);
    }




    

    
    
}
