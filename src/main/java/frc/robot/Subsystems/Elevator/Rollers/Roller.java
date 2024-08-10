package frc.robot.Subsystems.Elevator.Rollers;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Roller extends SubsystemBase {
    private final RollerIO io ;
    private final RollerIOInputsAutoLogged inputs = new RollerIOInputsAutoLogged();

    public Roller(RollerIO io){
        this.io = io;
        setBrakeMode(true);
    }

    public void setBrakeMode (boolean enabled){
        io.setRollerBrakeMode(enabled);
    }

    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Elevator/Roller", inputs);
    }

    public void stop(){
        io.setRoller(0);
    }





    
}



