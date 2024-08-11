package frc.robot.Subsystems.Intake; 

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import frc.robot.Subsystems.Intake.ConstantsIntake; 

public class IntakeIOSparkMax implements IntakeIO{

    private final CANSparkMax Intake; 
    private final 

    public IntakeIOSparkMax(){

        Intake = new CANSparkMax(ConstantsIntake., MotorType.kBrushless);

        Intake.setInverted()




    
    @Override
     public void setIntake(double voltage){
        Intake.setVoltage(voltage);

     }

     @Override 
     public void setIntakeBrakeMode(boolean enable){
        Intake.setIdleMode(enable ? IdleMode.Brake : IdleMode.KCoast);
     }

    }
}