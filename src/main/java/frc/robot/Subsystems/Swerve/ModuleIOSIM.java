package frc.robot.Subsystems.Swerve;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ModuleIOSIM implements ModuleIO{

    private static final double LOOP_PERIOD_SECS = 0.02;
    
    private final DCMotorSim driveSim =
        new DCMotorSim(DCMotor.getNEO(1), DriveConstants.DriveReduction, 0.025); //valores aproximados
    private final DCMotorSim TurnSim =
        new DCMotorSim(DCMotor.getNEO(1), DriveConstants.TurnReduction, 0.004); //valores aproximados

    private final Rotation2d turnAbsoluteInitPosition = new Rotation2d(Math.random() * 2.0 * Math.PI);
    private double driveAppliedVolts = 0.0;
    private double turnAppliedVolts = 0.0;
    
    @Override
    public void updateInputs(ModuleIOInputs inputs){
        driveSim.update(LOOP_PERIOD_SECS);
        TurnSim.update(LOOP_PERIOD_SECS);

        inputs.driveAppliedVolts = driveAppliedVolts;
        inputs.drivePositionRad = driveSim.getAngularPositionRad();
        inputs.driveVelocityRadPerSec = driveSim.getAngularVelocityRadPerSec();
        inputs.driveCurrentAmps = new double[]{Math.abs(driveSim.getCurrentDrawAmps())};

        inputs.turnAppliedVolts = turnAppliedVolts;
        inputs.TurningPosition = new Rotation2d(TurnSim.getAngularPositionRad()).plus(turnAbsoluteInitPosition);
        inputs.AngleEncoderPosition = new Rotation2d(TurnSim.getAngularPositionRad());
        inputs.turnVelocityRadPerSec = TurnSim.getAngularVelocityRadPerSec();
        inputs.TurnCurrentAmps = new double[]{Math.abs(TurnSim.getCurrentDrawAmps())};
    }

    @Override
    public void setDrive(double volts) {
      driveAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
      driveSim.setInputVoltage(driveAppliedVolts);
    }
  
    @Override
    public void setTurn(double volts) {
      turnAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
      TurnSim.setInputVoltage(turnAppliedVolts);
    }
}
