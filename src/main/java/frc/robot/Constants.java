package frc.robot;

public final class Constants {
  public static final Mode currentMode = Mode.SIM;
  public static final Controller controllertype = Controller.PS5;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }
  public static enum Controller{
    XBOX, PS5
  }
}
