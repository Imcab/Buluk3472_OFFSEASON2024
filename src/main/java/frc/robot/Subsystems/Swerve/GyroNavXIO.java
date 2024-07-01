package frc.robot.Subsystems.Swerve;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;

public class GyroNavXIO implements GyroIO{

    private final AHRS navX =  new AHRS(SPI.Port.kMXP);

    public GyroNavXIO(){
        navX.reset();
    }
    public double getAngle(){
        return Math.IEEEremainder(navX.getAngle(), 360);
    }
    @Override
    public void updateInputs(GyroIOInputs inputs){
        inputs.navXangle = getAngle();
        inputs.rotation2d = Rotation2d.fromDegrees(-getAngle());
    }
    @Override
    public void resetGyro(){
        navX.reset();
    }
    
}
