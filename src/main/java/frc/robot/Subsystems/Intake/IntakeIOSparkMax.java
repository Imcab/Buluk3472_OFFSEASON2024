package frc.robot.Subsystems.Intake; 

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Subsystems.Intake.ConstantsIntake.IntakeConstants;

public class IntakeIOSparkMax implements IntakeIO{

    private final CANSparkMax Intake; 

    private final RelativeEncoder enc_intake;

    public IntakeIOSparkMax(){

        Intake = new CANSparkMax(ConstantsIntake.IntakeConstants.Intakeport, MotorType.kBrushless);

        Intake.restoreFactoryDefaults();

        Intake.setCANTimeout(250);

        Intake.setInverted(ConstantsIntake.IntakeConstants.IntakeMotorReversed);
        
        Intake.enableVoltageCompensation(12.0);

        enc_intake = Intake.getEncoder();

        Intake.setCANTimeout(0);

        Intake.burnFlash();

    }

     @Override
    public void updateInputs(IntakeIOInputs inputs){
        inputs.IntakeAppliedVolts = Intake.getAppliedOutput() * Intake.getBusVoltage();
        inputs.IntakeVelocityRadPerSec = enc_intake.getVelocity()/12;
        inputs.IntakeCurrentAmps = new double[]{Intake.getOutputCurrent()};
        inputs.IntakeRpm = enc_intake.getVelocity()/IntakeConstants.ratio;
    }

    @Override
     public void setIntake(double voltage){
        Intake.setVoltage(voltage);

     }

 @Override 
     public void setIntakeBrakeMode(boolean enable){
        Intake.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
     }
}
