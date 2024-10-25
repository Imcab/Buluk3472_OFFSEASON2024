package frc.robot.Subsystems.Swerve;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;

public class GyroNavXIO implements GyroIO{

    private final AHRS navX =  new AHRS(SPI.Port.kMXP);

    public GyroNavXIO(){
        
        new Thread(() -> {
            try{
                Thread.sleep(1000);
                navX.reset();
            } catch (Exception e){
    
            }
          }).start();
    }

    @Override
    public void updateInputs(GyroIOInputs inputs){
        inputs.connected = navX.isConnected();
        inputs.Gyroangle = Math.IEEEremainder(navX.getAngle(), 360);
        inputs.Gyrorotation2d = Rotation2d.fromDegrees(-inputs.Gyroangle);
    }

    @Override
    public void reset(){
        navX.reset();
    }
    
}
