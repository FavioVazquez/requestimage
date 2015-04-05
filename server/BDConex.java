package pack;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BDConex {
	Connection conect;
	Statement senten;
	public void connected(String direccion, String username, String password)
	{
		try{
		conect = null;	
	    Class.forName ("org.postgresql.Driver");
	    conect = DriverManager.getConnection (direccion, username, password);
		}
		catch (Exception e) {
			e.printStackTrace ();
		}
	}
	public ResultSet open(String sql)
	{
		try {
		senten= conect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		senten.executeQuery(sql);
		return senten.getResultSet();
		}
		catch(Exception e){
			e.printStackTrace ();	
			return null;
		}
	}
	public void exeSql (String sql)
	{
		try{
			senten=conect.createStatement();
			senten.executeQuery(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void close() 
	{
		try{
			senten.close();
			conect.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
