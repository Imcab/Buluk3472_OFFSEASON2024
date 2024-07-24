package frc.robot.Subsystems.Shooter.Shooter.AngleShooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

import frc.robot.Subsystems.Shooter.ShooterConstants.AngleShooterConstants;

public class AngleIOSim implements AngleIO{
    private static final double LOOP_PERIOD_SECS = 0.02;

    private final DCMotorSim shooterAngleSim = new DCMotorSim(DCMotor.getKrakenX60(1), AngleShooterConstants.AngleReduction, 0.015);

    private double shooterAngleAppliedVolts = 0.0;

    @Override
    public void updateInputs(AngleIOInputs inputs){

        shooterAngleSim.update(LOOP_PERIOD_SECS);
        
        inputs.AngleAppliedVolts = shooterAngleAppliedVolts;
        inputs.ShooterPosition = new Rotation2d(shooterAngleSim.getAngularPositionRad());
        inputs.AngleVelocityRadPerSec = shooterAngleSim.getAngularVelocityRadPerSec();
        inputs.AngleCurrentAmps = new double[]{Math.abs(shooterAngleSim.getCurrentDrawAmps())};
    }

    @Override
    public void setAngleShooter(double voltage){
        shooterAngleAppliedVolts = MathUtil.clamp(voltage, -12.0, 12.0);
        shooterAngleSim.setInputVoltage(voltage);
    }
}
