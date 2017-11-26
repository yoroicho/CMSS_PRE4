/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmss_pre4;

import DB.ConnectionClass;
import FileDirController.CreateUnderDir;
import common.SystemPropertiesAcc;
import common.SystemPropertiesItem;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author tokyo
 */
public class FXMLDocumentController implements Initializable {

    /**
     * ー設定変更タブについてー 各BASE等すべての設定をメモリへ読み出すのは起動時の一回のみ。 ウインドウのロード前にファイルからメモリにロード。
     * 画面を開いた時にはメモリの内容をウィンドウに表示させる。 変更は確定ボタンでメモリを介さず直接ファイルに書き込み。
     * タブ方式につきキャンセルボタンは実装しないがメモリの内容をウィンドウに復元 するボタンを実装している。
     * すでにファイルに書き込み済みの内容が何かの具合で目に触れることなく有効にならないように配慮。
     */
    @FXML
    private TextField textFieldDatabaseUrl;

    @FXML
    private TextField textFieldDatabaseUser;

    @FXML
    private PasswordField passwordFieldDatabasePass;

    @FXML
    private Label label;

    @FXML
    private TextField textFieldShipBaseDir;

    @FXML
    private Label labelEnterCreateShipDir;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText(passwordFieldDatabasePass.getText());
        ConnectionClass.preInsertShip();

    }

    @FXML
    private void handleShipBaseDirButtonAction(ActionEvent event) {
        final DirectoryChooser fc = new DirectoryChooser();
        fc.setTitle("ディレクトリ選択");
        textFieldShipBaseDir.setText(fc.showDialog(null).getPath());
    }

    @FXML
    private void handleButtonEnterSystemPropertiesButtonAction(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "この設定が有効になるのはシステム再起動後です。");
        Alert alert = new Alert(AlertType.CONFIRMATION, "厳重確認：この操作は重大な影響を永続的に及ばします。");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> SystemPropertiesAcc.storeSystemProperties(
                textFieldDatabaseUrl.getText().trim(),
                textFieldDatabaseUser.getText().trim(),
                passwordFieldDatabasePass.getText().trim(),
                textFieldShipBaseDir.getText().trim(),
                "",
                "",
                ""));

    }

    @FXML
    private void handleButtonEnterCreateShipDirButtonAction(ActionEvent event) {
        String createdDir = CreateUnderDir.makeUnderDirUUID("SHIP-", SystemPropertiesItem.SHIP_BASE);
        Date d = new Date();
        List<String> list = new ArrayList<>();
        list.add(createdDir);
        list.add("SHIP_CREATED");
        list.add("----------------------------------------");
        list.add(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(d));
        list.add(new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒").format(d));
        list.add("----------------------------------------");
        CreateUnderDir.makeFileUnderDirContAppend("log.txt", list, createdDir);
        labelEnterCreateShipDir.setText(createdDir);
        String createdSubDir = CreateUnderDir.makeUnderDirNamed("Documents", createdDir);
    }

    @FXML // すべての内容を現在メモリに登録している状態にもどす。
    private void handleButtonReloadPropertiesOnMemory(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to reload Properties from this system's memory?");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> initializeSystemPropertiesWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeSystemPropertiesWindow(); // システム設定に初期状態の表示をさせる。
    }

    private void initializeSystemPropertiesWindow() {
        // SystemPropertiesの表示部に情報を流し込み。
        textFieldDatabaseUrl.setText(SystemPropertiesItem.DB_URL);
        textFieldDatabaseUser.setText(SystemPropertiesItem.DB_USER);
        passwordFieldDatabasePass.setText(SystemPropertiesItem.DB_PASS);
        textFieldShipBaseDir.setText(SystemPropertiesItem.SHIP_BASE);
    }

}
