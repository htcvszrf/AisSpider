package whut.navigation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLOperations {
	@SuppressWarnings("resource")
	public static void work() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String urlMmsi = "jdbc:mysql://localhost:3306/MMSIDatabase";
		String urlResult = "jdbc:mysql://localhost:3306/ResultDatabase";
		String userName = "root";
		String passWord = "root";
		Connection conMmsi = null;
		Connection conResult = null;
		Statement sqlMmsi = null;
		Statement sqlResult = null;
		try {
			conMmsi = DriverManager.getConnection(urlMmsi, userName, passWord);
			conResult = DriverManager.getConnection(urlResult, userName, passWord);
			sqlMmsi = conMmsi.createStatement();
			sqlResult = conResult.createStatement();
			//drop table if exists student
			sqlResult.execute("create database if not exists ResultDatabase");
			sqlMmsi.execute("create database if not exists MmsiDatabase");
			//sqlResult.execute("drop table if exists Result_Table");
			sqlResult.execute("create table  if not exists Result_Table("
					+ "ship_name varchar(30) not null default 'null',"//船名
					+ "chinese_ship_name varchar(30),"//中文船名
					+ "call_sign varchar(30),"//呼号
					+ "IMO varchar(20),"//IMO
					+ "MMSI varchar(20),"//MMSI
					+ "ship_type varchar(20),"//船舶类型
					+ "ship_length varchar(20),"//船长
					+ "ship_width varchar(20),"//船宽
					+ "gross_tonnage varchar(20),"//总吨
					+ "net_tonnage varchar(20),"//净吨
					+ "nationality varchar(10),"//国籍
					+ "made_date varchar(20),"//造船日期
					+ "main_engin_type varchar(20),"//主机种类
					+ "main_engin_special varchar(20),"//主机型号
					+ "main_engin_performance varchar(20),"//主机性能
					+ "main_engin_speed varchar(20),"//主机速率
					+ "main_engin_fuel varchar(20),"//主机燃料
					+ "main_engin_capacity varchar(20),"//主机总功率
					+ "primary key(MMSI), unique key(MMSI) ) ENGINE=InnoDB DEFAULT CHARSET=utf8");
			sqlResult.execute("alter table Result_Table add UNIQUE (MMSI)");
			sqlMmsi.execute("create table if not exists MMSI_Table(Mmsi varchar(10) not null,"
					+ "primary key(Mmsi) );");
//			sqlMmsi.execute("insert MMSI_Table values('357098000');");
//			sqlMmsi.execute("insert MMSI_Table values('413656000');");
//			sql.execute("insert student values(15, 'ehe',80 );");
//			sql.execute("insert student values(16, 'lhehe',30 );");
			String queryStringMmsi = "select * from MMSI_table";
			ResultSet  result = sqlMmsi.executeQuery(queryStringMmsi);
			String mmsi;
			String sqlResultString;
			while(result.next()) {
				mmsi = result.getString("Mmsi");
				String infoString = HtmlToAisSouchuan.htmlToAis(mmsi, 1);
//System.out.println("The Initial = "+infoString);

				if(infoString.equals("") || infoString.length() == 0) {
					System.out.println(" is null");
					infoString = " , , , ,"
							+ mmsi
							+ ", , , , , , , , , , , , , ";
				}
				
				String[] info = infoString.split(",");
				sqlResultString = "insert ignore into Result_Table("
						+ "ship_name, chinese_ship_name, call_sign, IMO, MMSI, ship_type, ship_length, ship_width, gross_tonnage, net_tonnage, nationality, made_date, main_engin_type, main_engin_special, main_engin_performance, main_engin_speed, main_engin_fuel, main_engin_capacity) "
						+ "values('"
						+ info[0]
						+ "','"
						+ info[1]
						+ "','"
						+ info[2]
						+ "','"
						+ info[3]
						+ "','"
						+ info[4]
						+ "','"
						+ info[5]
						+ "','"
						+ info[6]
						+ "','"
						+ info[7]
						+ "','"
						+ info[8]
						+ "','"
						+ info[9]
						+ "','"
						+ info[10]
						+ "','"
						+ info[11]
						+ "','"
						+ info[12]
						+ "','"
						+ info[13]
						+ "','"
						+ info[14]
						+ "','"
						+ info[15]
						+ "','"
						+ info[16]
						+ "','"
						+ info[17]
						+ "');";
				sqlResult.execute(sqlResultString);
				sqlMmsi.execute("delete from MMSI_Table where Mmsi="
						+mmsi
						+";");
				result = sqlMmsi.executeQuery(queryStringMmsi);
			}
			sqlMmsi.close();
			sqlResult.close();
			conMmsi.close();
			conResult.close();
		} catch (SQLException e) {
			sqlMmsi.close();
			sqlResult.close();
			conMmsi.close();
			conResult.close();
			e.printStackTrace();
		}
	}
	public static void main(String[] argStrings) throws Exception {
		work();
	}
}
