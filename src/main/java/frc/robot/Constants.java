package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public final class Constants {
  public static final Mode currentMode = Mode.SIM;
  
  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }
  
  public class FieldConstants {

    public static final double fieldLength = Units.inchesToMeters(651.223);
    public static final double fieldWidth = Units.inchesToMeters(323.277);
    public static final double wingX = Units.inchesToMeters(229.201);
    public static final double podiumX = Units.inchesToMeters(126.75);
    public static final double startingLineX = Units.inchesToMeters(74.111);

    public static final Translation2d ampCenter =
      new Translation2d(Units.inchesToMeters(72.455), fieldWidth);

    /** Staging locations for each note */
    public static final class StagingLocations {
        public static final double centerlineX = fieldLength / 2.0;

      // need to update
      public static final double centerlineFirstY = Units.inchesToMeters(29.638);
      public static final double centerlineSeparationY = Units.inchesToMeters(66);
      public static final double spikeX = Units.inchesToMeters(114);
      // need
      public static final double spikeFirstY = Units.inchesToMeters(161.638);
      public static final double spikeSeparationY = Units.inchesToMeters(57);

      public static final Translation2d[] centerlineTranslations = new Translation2d[5];
      public static final Translation2d[] spikeTranslations = new Translation2d[3];

      static {
        for (int i = 0; i < centerlineTranslations.length; i++) {
          centerlineTranslations[i] =
              new Translation2d(centerlineX, centerlineFirstY + (i * centerlineSeparationY));
        }
      }

      static {
        for (int i = 0; i < spikeTranslations.length; i++) {
          spikeTranslations[i] = new Translation2d(spikeX, spikeFirstY + (i * spikeSeparationY));
        }
      }
    }
  }
}
