package frc.robot.Subsystems.Shooter.Turret;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Subsystems.Shooter.ShooterConstants.TurretConstants;

public class TurretIOSim implements TurretIO{
    private static final double LOOP_PERIOD_SECS = 0.02;

    private final DCMotorSim Turretsim = new DCMotorSim(DCMotor.getNEO(1), TurretConstants.TurretReduction, 0.001);
    
    private double TurretAppliedVolts = 0.0;

    @Override
    public void updateInputs(TurretIOInputs inputs){

        Turretsim.update(LOOP_PERIOD_SECS);

        inputs.TurretAppliedVolts = TurretAppliedVolts;
        //inputs.TurretPosition = new Rotation2d(Turretsim.getAngularPositionRad());
        inputs.TurretVelocityRadPerSec = Turretsim.getAngularVelocityRadPerSec();
        inputs.TurretCurrentAmps = new double[]{Math.abs(Turretsim.getCurrentDrawAmps())};

    }
    @Override
    public void setTurret(double voltage){
        TurretAppliedVolts = MathUtil.clamp(voltage, -12.0, 12.0);
        Turretsim.setInputVoltage(voltage);
    }
    
}
