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
public class FileSupport {

    /**
     * Recursively deletes all the files and folders in the given folder, but
     * not the folder itself.
     *
     * @param folder the folder whose content will be deleted
     * @throws IOException if an exception occurs
     */
    public static void deleteFolderContent(Path folder) throws IOException {
        Files.walkFileTree(folder, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                                                     BasicFileAttributes attrs) throws
                    IOException {
                /*
                 * no previsit actions
                 */
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs) throws
                    IOException {
                /*
                 * delete file
                 */
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws
                    IOException {
                throw exc;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws
                    IOException {
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
     * Recursively deletes all the files and folders in the given folder, but
     * not the folder itself.
     *
     * @param folder the folder whose content will be deleted
     * @throws IOException if an exception occurs
     */
    /**
     * Recursively deletes all the files and folders in the given folder, and
     * deletes the folder itself at the end.
     *
     * @param folder the folder to delete
     * @throws IOException if an exception occurs
     */
    public static void deleteFolder(Path folder) throws IOException {

        Files.walkFileTree(folder, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                                                     BasicFileAttributes attrs) throws
                    IOException {
                /*
                 * no previsit actions
                 */
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs) throws
                    IOException {
                /*
                 * delete file
                 */
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws
                    IOException {
                throw exc;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws
                    IOException {
                /*
                 * delete folder
                 */
                Files.delete(dir);

                return FileVisitResult.CONTINUE;
            }
        });

    }

}
