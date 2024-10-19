package frc.robot.commands.ShooterCommands.Turret;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.util.LimitYaw3472;

public class JoystickTurret extends Command{

    private final Turret turret;
    private final DoubleSupplier supplier;

    public JoystickTurret(Turret turret, DoubleSupplier supplier){
        this.turret = turret;
        this.supplier = supplier;

        addRequirements(turret);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute(){

            double joystickValue = supplier.getAsDouble();
            
            if (Math.abs(joystickValue) < 0.05){
                joystickValue = 0;
            }
            
            if (LimitYaw3472.shouldFix()) {
                turret.runWithJoystick(0);
            }
            else{
                turret.runWithJoystick(joystickValue);
            }
            
    }
    @Override
    public void end(boolean interrupted) {
        turret.stop();
    }

    @Override
    public boolean isFinished(){
        return false;
    }       
}
