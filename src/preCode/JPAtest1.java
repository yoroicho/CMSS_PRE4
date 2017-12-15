/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preCode;



/**
 *
 * @author tokyo
 */
public class JPAtest1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
TestTable p1 = new TestTable( 14,"777");
System.out.println(TestTable.class);
        DatabaseManager<TestTable> dbm = new DatabaseManager<>(TestTable.class,"CMSS_PRE4PU");
        dbm.persist(p1);    // 保存する
        dbm.close(); 
    }
    
}
