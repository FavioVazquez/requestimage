package pack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Home
 */

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		BDConex a = new BDConex();
		a.connected("jdbc:postgresql://localhost:5432/images", "postgres", "7413246");
		try {
			a.senten=a.conect.createStatement();
			a.senten.execute("SELECT data FROM photos WHERE name="+"'"+request.getHeader("name")+"'");
			ResultSet rs = a.senten.getResultSet();
			while(rs.next()){
				byte[] buffer =new byte[1000];
				InputStream imageinput = rs.getBinaryStream("data");
				OutputStream out = response.getOutputStream();
				for (int part = imageinput.read(buffer); part!=-1; part = imageinput.read(buffer))
				{
				    out.write(buffer,0,part);
				} 
				out.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BDConex a = new BDConex();
		a.connected("jdbc:postgresql://localhost:5432/images", "postgres", "7413246");
		InputStream image = request.getInputStream();
		ByteArrayOutputStream up = new ByteArrayOutputStream();
		byte[] data = new byte[16384];
		int n;
		while((n= image.read(data,0,data.length)) != -1){
			up.write(data,0,n);
		}
		up.flush();
		ByteArrayInputStream down = new ByteArrayInputStream(up.toByteArray());
		String SQL = "INSERT INTO photos VALUES(?,?)";
		try {
			PreparedStatement prepared = a.conect.prepareStatement(SQL);
			prepared.setString(1,request.getHeader("name"));
			prepared.setBinaryStream(2,down,up.size());
			prepared.execute();
			prepared.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
