package frc.robot.commands.ShooterCommands.Angle;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;

public class AlignJoystick extends Command{

    private final Angle shooterAngle;
    private final DoubleSupplier joystickSupplier;

    public AlignJoystick(Angle shooterAngle, DoubleSupplier joystickSupplier){
        this.shooterAngle = shooterAngle;
        this.joystickSupplier = joystickSupplier;
        addRequirements(shooterAngle);
    }

    @Override
    public void initialize(){}
    @Override
    public void execute(){

        double joystickValue = joystickSupplier.getAsDouble();
            
            if (Math.abs(joystickValue) < 0.1){
                joystickValue = 0;
            }

            shooterAngle.runSpeed(joystickValue);
        
    }
    
    @Override
    public void end(boolean interrupted) {
        shooterAngle.stop();  
    }
    
    @Override
    public boolean isFinished(){
        return false;
    }

    
}
