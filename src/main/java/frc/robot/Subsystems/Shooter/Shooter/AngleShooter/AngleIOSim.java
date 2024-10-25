package frc.robot.Subsystems.Shooter.Shooter.AngleShooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.robot.Subsystems.Shooter.ShooterConstants.AngleShooterConstants;

public class AngleIOSim implements AngleIO{

    //configuraciones para el simulador
    double ShooterLenghtM = 0.463;
    double minAngleRads = Units.degreesToRadians(2.0);
    double maxAngleRads = Units.degreesToRadians(50.0);
    //
    private static final double LOOP_PERIOD_SECS = 0.02;

    private final SingleJointedArmSim shootersim = new SingleJointedArmSim(DCMotor.getKrakenX60(1), 
                                                                           AngleShooterConstants.AngleGearing,
                                                                           0.015,
                                                                           ShooterLenghtM,
                                                                           minAngleRads,
                                                                           maxAngleRads,
                                                                           false,
                                                                           0);

    private double shooterAngleAppliedVolts = 0.0;

    @Override
    public void updateInputs(AngleIOInputs inputs){

        shootersim.update(LOOP_PERIOD_SECS);
        
        inputs.AngleAppliedVolts = shooterAngleAppliedVolts;
        inputs.ShooterPosition = shootersim.getAngleRads();
        inputs.AngleVelocityRadPerSec = shootersim.getVelocityRadPerSec();
        //inputs.AngleCurrentAmps = new double[]{Math.abs(shootersim.getCurrentDrawAmps())};
    }

    @Override
    public void setAngleShooter(double voltage){
        shooterAngleAppliedVolts = MathUtil.clamp(voltage, -12.0, 12.0);
        shootersim.setInputVoltage(voltage);
    }
}
