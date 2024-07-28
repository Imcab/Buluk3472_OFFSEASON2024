// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import java.util.Set;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;

public class NoteVisualizer {
  private static final Translation3d blueSpeaker = new Translation3d(0.225, 5.55, 2.1);
  private static final Translation3d redSpeaker = new Translation3d(16.317, 5.55, 2.1);
  private static final double shotSpeed = 10.26832; // Meters per sec
  private static Supplier<Pose2d> robotPoseSupplier = () -> new Pose2d();
  private static Supplier<Rotation2d> turretyaw = ()-> new Rotation2d();
  private static Supplier<Rotation2d> shooterpitch = ()-> new Rotation2d();

  public static void setRobotPoseSupplier(Supplier<Pose2d> supplier) {
    robotPoseSupplier = supplier;
  }

  public static void setturretyawPoseSupplier(Supplier<Rotation2d> supplier) {
    turretyaw = supplier;
  }
  public static void setshooterpitchPoseSupplier(Supplier<Rotation2d> supplier) {
    shooterpitch = supplier;
  }

  public static Pose3d getShooterPose3d(){
      Transform3d ShooterTransform = new Transform3d(0.0, 0.07, 0.650, 
      new Rotation3d(0.0, -shooterpitch.get().getRadians(), turretyaw.get().getRadians()));

      return new Pose3d(robotPoseSupplier.get()).transformBy(ShooterTransform);
  }


  public static Command shoot() {
    return new ScheduleCommand( // Branch off and exit immediately
        Commands.defer(
                () -> {
                  final Pose3d startPose = getShooterPose3d();
                  final boolean isRed =
                      DriverStation.getAlliance().isPresent()
                          && DriverStation.getAlliance().get().equals(Alliance.Red);
                  final Pose3d endPose =
                      new Pose3d(isRed ? redSpeaker : blueSpeaker, startPose.getRotation());

                  final double duration =
                      startPose.getTranslation().getDistance(endPose.getTranslation()) / shotSpeed;
                  final Timer timer = new Timer();
                  timer.start();
                  return Commands.run(
                          () -> {
                            Logger.recordOutput(
                                "NoteVisualizer",
                                new Pose3d[] {
                                  startPose.interpolate(endPose, timer.get() / duration)
                                });
                          })
                      .until(() -> timer.hasElapsed(duration))
                      .finallyDo(
                          () -> {
                            Logger.recordOutput("NoteVisualizer", new Pose3d[] {});
                          });
                },
                Set.of())
            .ignoringDisable(true));
  }
}