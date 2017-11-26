/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDirController;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author tokyo
 */
public class CreateUnderDir {

    public static String makeFileUnderDirContAppend(String title, List<String> text, String parentPath) {
        if (parentPath.isEmpty()) {
            JOptionPane.showMessageDialog(null, "検査エラー" + title + "を作成する親ディレクトリ<" + parentPath + ">が見つかりません。");
            return null;
        } else {
            String createdFile = parentPath + System.getProperty("file.separator") + title;
            System.out.println(createdFile);
            Path targetPaths = Paths.get(createdFile);
            try {
                Files.createFile(targetPaths);
                Files.write(targetPaths, text, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
            } catch (NoSuchFileException ex) {
                JOptionPane.showMessageDialog(null, "例外発生" + title + "を作成する親ディレクトリ<" + parentPath + ">が見つかりません。" + ex.toString());
                Logger.getLogger(CreateUnderDir.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (IOException ex) {
                Logger.getLogger(CreateUnderDir.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "例外発生 " + ex.toString());
                return null;
            }
            return createdFile;
        }
    }

    public static String makeUnderDirNamed(String name, String parentPath) {
        if (parentPath.isEmpty()) {
            JOptionPane.showMessageDialog(null, "検査エラー" + name + "を作成する親ディレクトリ<" + parentPath + ">が見つかりません。");
            return null;
        } else {
            String createdDir = parentPath + System.getProperty("file.separator") + name;
            System.out.println(createdDir);
            Path targetPaths = Paths.get(createdDir);
            try {
                Files.createDirectory(targetPaths);
            } catch (NoSuchFileException ex) {
                JOptionPane.showMessageDialog(null, "例外発生" + name + "を作成する親ディレクトリ<" + parentPath + ">が見つかりません。" + ex.toString());
                Logger.getLogger(CreateUnderDir.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (IOException ex) {
                Logger.getLogger(CreateUnderDir.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "例外発生 " + ex.toString());
                return null;
            }
            return createdDir;
        }
    }

    public static String makeUnderDirUUID(String header, String parentPath) {
        if (parentPath.isEmpty()) {
            JOptionPane.showMessageDialog(null, header + " : 親ディレクトリが見当たりません。");
            return null;
        } else {
            String file = UUID.randomUUID().toString();
            String createdDir = parentPath + System.getProperty("file.separator") + header + file;
            System.out.println(createdDir);
            Path targetPaths = Paths.get(createdDir);
            try {
                Files.createDirectory(targetPaths);
            } catch (NoSuchFileException ex) {
                JOptionPane.showMessageDialog(null, header + " : 指定された作成基盤<" + parentPath + ">が見つかりません。");
                Logger.getLogger(CreateUnderDir.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (IOException ex) {
                Logger.getLogger(CreateUnderDir.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            return createdDir;
        }
    }
}
