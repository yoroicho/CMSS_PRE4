/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author tokyo
 */
public class SystemPropertiesAcc {

    private static final String systemPropertiesXmlFilePath = "SYSTEM_PROPERTIES.xml";

    public static void storeSystemProperties( // 登録画面から直接ファイルに書込
            String DB_URL,
            String DB_USER,
            String DB_PASS,
            String SHIP_BASE,
            String VOYAGE_BASE,
            String BOOKING_BASE,
            String CARGO_BASE
    ) {
        // 書き込み
        Properties properties = new Properties();
        properties.setProperty("DB_URL", DB_URL);
        properties.setProperty("DB_USER", DB_USER);
        properties.setProperty("DB_PASS", DB_PASS);
        properties.setProperty("SHIP_BASE", SHIP_BASE);
        properties.setProperty("VOYAGE_BASE", VOYAGE_BASE);
        properties.setProperty("BOOKING_BASE", BOOKING_BASE);
        properties.setProperty("CARGO_BASE", CARGO_BASE);
        try {
            OutputStream ostream = new FileOutputStream(systemPropertiesXmlFilePath);
            properties.storeToXML(ostream, "SystemProperties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SystemPropertiesAcc.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "設定ファイルが見当たりません。");
        } catch (IOException ex) {
            Logger.getLogger(SystemPropertiesAcc.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "設定ファイルの書き込み時に問題が発生しました。");
        }
    }

    public static boolean loadSystemProperties() {

        Properties properties = new Properties();

        // 読み込み
        try {
            InputStream istream = new FileInputStream(systemPropertiesXmlFilePath);
            properties.loadFromXML(istream);
            SystemPropertiesItem.DB_URL = properties.getProperty("DB_URL");
            SystemPropertiesItem.DB_USER = properties.getProperty("DB_USER");
            SystemPropertiesItem.DB_PASS = properties.getProperty("DB_PASS");
            SystemPropertiesItem.SHIP_BASE = properties.getProperty("SHIP_BASE");
            SystemPropertiesItem.VOYAGE_BASE = properties.getProperty("VOYAGE_BASE");
            SystemPropertiesItem.BOOKING_BASE = properties.getProperty("BOOKING_BASE");
            SystemPropertiesItem.CARGO_BASE = properties.getProperty("CARGO_BASE");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SystemPropertiesAcc.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "設定ファイルが見当たりません。");
            return false;
        } catch (IOException ex) {
            Logger.getLogger(SystemPropertiesAcc.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "設定ファイルの読み込み時に問題が発生しました。");
            // System.exit(0); 
            return false;
        }
        return true;
        // Mapに格納
    }
}

//設定ファイルが存在すればロードしてフラグを立てる。
// 設定ファイルの内容をcommon.SystemPropertisに代入する。
//設定ファイルが存在しない場合はフラグを倒してから警告を出す。
//
//設定ファイルに書き出す。
//設定ファイルに書き出しても読み込みはinitでの一回だけ。
//アプリケーションを再起動しなければ読み込まれない。
//
//

