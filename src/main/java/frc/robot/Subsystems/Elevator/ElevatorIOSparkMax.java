package frc.robot.Subsystems.Elevator;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import frc.robot.Subsystems.Elevator.ConstantsElevator.ElevatorConstants;

public class ElevatorIOSparkMax implements ElevatorIO{
    private final CANSparkMax Elevator; 
    private final boolean Reverse;
    private final RelativeEncoder encoder;

    public ElevatorIOSparkMax(){
        Elevator = new CANSparkMax(ElevatorConstants.ElevatorPort, MotorType.kBrushless);
        Reverse = ElevatorConstants.ElevatorMotorReversed;

        Elevator.restoreFactoryDefaults();

        Elevator.setInverted(Reverse);

        Elevator.setCANTimeout(250);

        Elevator.setInverted(Reverse);

        encoder = Elevator.getEncoder();

        encoder.setPosition(0.0);
        encoder.setMeasurementPeriod(10);
        encoder.setAverageDepth(2);

        Elevator.enableVoltageCompensation(12.0);

        Elevator.setCANTimeout(0);
        Elevator.burnFlash();
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs){
        inputs.ElevatorAppliedVolts = Elevator.getAppliedOutput() * Elevator.getBusVoltage();
        inputs.ElevatorVelocityRadPerSec = (encoder.getVelocity() / ElevatorConstants.ElevatorReduction);
        inputs.ElevatorCurrentAmps = new double[]{Elevator.getOutputCurrent()};
        inputs.ElevatorMeters = Units.rotationsToRadians(encoder.getPosition()) / ElevatorConstants.ElevatorReduction;
        
    }

    @Override
    public void setElevator(double voltage){
        Elevator.setVoltage(voltage);
    }

    @Override
    public void setElevatorBrakeMode(boolean enable){
        Elevator.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    }
}
