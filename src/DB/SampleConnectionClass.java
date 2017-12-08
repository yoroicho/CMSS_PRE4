/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

/**
 *
 * @author tokyo
 * http://dev.classmethod.jp/server-side/java/java_mysql_transaction/
 */
import common.SystemPropertiesItem;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SampleConnectionClass {

    // URLなどの情報は初期化ファイルから取得するのでこれらをどのように設定するかをまず考える。
    static final String URL = SystemPropertiesItem.DB_URL;
    static final String USERNAME = SystemPropertiesItem.DB_USER;
    static final String PASSWORD = SystemPropertiesItem.DB_PASS;

    public static void preInsertShip() {

        String sql = "INSERT INTO ship VALUES (?, ?, ?);";

        String[][] list = {
            {"FFFF", "Sam", "Stay With Me"},
            {"GGGG", "Kan", "Gold Digger"},
            {"HHHH", "Ron", "It's A Jazz Thing"},
            {"IIII", "Pri", "Kiss"},
            {"JJJJ", "Led", "When The Levee Breaks"}
        };

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            for (int i = 0; i < list.length; i++) {
                statement.setString(1, list[i][0]);
                statement.setString(2, list[i][1]);
                statement.setString(3, list[i][2]);
                statement.addBatch();
                System.out.println(statement.toString());
            }
            int[] result = statement.executeBatch();
            System.out.println("登録：" + result.length + "件");
            try {
                connection.commit();
                System.out.println("登録成功");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("登録失敗：ロールバック実行");
                e.printStackTrace();
            }
        } catch (BatchUpdateException e) {
            System.out.println("登録失敗:すでに登録されています。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("outer");
            e.printStackTrace();
        }
    }
}
