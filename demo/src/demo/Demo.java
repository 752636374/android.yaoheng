package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Demo {
	// private static ArrayList<String> filelist = new ArrayList<String>();
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/fetal_heart_monitor";
	static String username = "root";
	static String password = "";
	static Connection conn = null;

	public static void main(String[] args) throws IOException, SQLException, InterruptedException {
		String filePath = "F:/my_data/DATA2";
		getFiles(filePath);
	}

	static void getFiles(String filePath) throws IOException, SQLException, InterruptedException {
		Connection connection = getConn();
		File root = new File(filePath);
		File[] files = root.listFiles();
		byte[] byte1 = new byte[1024];
		byte[] data = new byte[4];
		byte[] data2 = new byte[4];
		StringBuffer sbs = new StringBuffer();
		String s, s2,s3,s4, sb0 = null,sql;
		int h = 0, j = 0, n = 0;
		InputStream is = null;
		for (File file : files) {
			if (file.isDirectory()) {
				// System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
			} else {

//				System.out.println("显示" + filePath + "下所有子目录" + file.getAbsolutePath() + "/" + (n++) + "-----"
//						+ Runtime.getRuntime().maxMemory() + "/" + Runtime.getRuntime().freeMemory());

				is = new FileInputStream(file.getAbsolutePath());

				while ((is.read(byte1)) != -1) {
					for (int i = 0; i < byte1.length; i += 4, h++) {
						if (h >= 4) {
							
							s="(" + "'" + Math.abs(data[0]) + "'" + "," + "'" + Math.abs(data2[0])+ "'" + ")";
							s2=	"(" + "'" + Math.abs(data[0]) + "'" + "," + "'" + Math.abs(data2[0])+ "'" + ")";
							s3=	"(" + "'" + Math.abs(data[0]) + "'" + "," + "'" + Math.abs(data2[0])+ "'" + ")";
							s4=	"(" + "'" + Math.abs(data[0]) + "'" + "," + "'" + Math.abs(data2[0])+ "'" + ")";
							sbs.append(s+","+s2+","+s3+","+s4+",");
							h = 0;
						}
						data[h] = byte1[i];
						data2[h] = byte1[i + 1];
					}
				}
				sb0=sbs.toString();
				sbs=new StringBuffer();
				byte1 = new byte[1024];
				is.close();
				sql = "insert into tag_fetalpacket(fhr1,uc) values" + sb0+"("+"'"+"109"+"'"+","+"'"+"76"+"'"+")";
				sb0=null;
				PreparedStatement preparedStatement;
				preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
				preparedStatement.execute();
				System.out.println("现在是第---"+(++j)+"----条数据");
			}
		}

	}

	public static Connection getConn() {
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
