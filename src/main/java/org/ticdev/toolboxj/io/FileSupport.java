package org.ticdev.toolboxj.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * File operations support methods.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface FileSupport {

  /**
   * Recursively deletes all the files and folders in the given folder,
   * but not the folder itself.
   *
   * @param folder the folder whose content will be deleted
   * @throws IOException if an exception occurs
   */
  static void deleteFolderContent(final Path folder)
      throws IOException {
    Files.walkFileTree(folder, new FileVisitor<Path>() {
      @Override
      public FileVisitResult preVisitDirectory(
          final Path dir,
          final BasicFileAttributes attrs) throws IOException {
        /*
         * no previsit actions
         */
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFile(
          final Path file,
          final BasicFileAttributes attrs) throws IOException {
        /*
         * delete file
         */
        Files.delete(file);
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFileFailed(
          final Path file,
          final IOException exc) throws IOException {
        throw exc;
      }

      @Override
      public FileVisitResult postVisitDirectory(
          final Path dir,
          final IOException exc) throws IOException {
        if (!dir.equals(folder)) {
          /*
           * delete folder
           */
          Files.delete(dir);
        }
        return FileVisitResult.CONTINUE;
      }
    });
  }

  /**
   * Recursively deletes all the files and folders in the given folder, and
   * deletes the folder itself at the end.
   *
   * @param folder the folder to delete
   * @throws IOException if an exception occurs
   */
  static void deleteFolder(final Path folder) throws IOException {

    Files.walkFileTree(folder, new FileVisitor<Path>() {
      @Override
      public FileVisitResult preVisitDirectory(
          final Path dir,
          final BasicFileAttributes attrs) throws IOException {
        /*
         * no previsit actions
         */
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFile(
          final Path file,
          final BasicFileAttributes attrs) throws IOException {
        /*
         * delete file
         */
        Files.delete(file);
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFileFailed(
          final Path file, final IOException exc) throws IOException {
        throw exc;
      }

      @Override
      public FileVisitResult postVisitDirectory(
          final Path dir,
          final IOException exc) throws IOException {
        /*
         * delete folder
         */
        Files.delete(dir);

        return FileVisitResult.CONTINUE;
      }
    });

  }

}
