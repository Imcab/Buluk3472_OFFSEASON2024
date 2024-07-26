package frc.robot.Subsystems.Elevator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import frc.robot.Subsystems.Elevator.ConstantsElevator.ElevatorConstants;

public class ElevatorIOSIM implements ElevatorIO{
    private static final double LOOP_PERIOD_SECS = 0.02;
    ////Configuration///
    double minHeightMeters = 0.125640;
    double maxHeightMeters = 0.376;
    double drumradius = 0.016;
    double carriageMass = 3.262; //kg
    /////

    private final ElevatorSim Elevator = new ElevatorSim(DCMotor.getNEO(1),
                                                         ElevatorConstants.ElevatorReduction,
                                                         carriageMass,
                                                         drumradius,
                                                         minHeightMeters,
                                                         maxHeightMeters,
                                                         true,
                                                         0.0);

    private double AppliedVolts = 0.0;

    @Override
    public void updateInputs(ElevatorIOInputs inputs){
        Elevator.update(LOOP_PERIOD_SECS);

        inputs.ElevatorAppliedVolts = AppliedVolts;
        inputs.ElevatorMeters = Elevator.getPositionMeters();
        inputs.ElevatorVelocityRadPerSec = Elevator.getVelocityMetersPerSecond();
        inputs.ElevatorCurrentAmps = new double[]{Math.abs(Elevator.getCurrentDrawAmps())};

    }
    @Override
    public void setElevator(double voltage){
        AppliedVolts = MathUtil.clamp(voltage, -12.0, 12.0);
        Elevator.setInputVoltage(voltage);
    }
}

