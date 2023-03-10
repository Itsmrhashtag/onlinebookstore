package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import config.DBConnection;
import constants.BookStoreConstants;
import constants.db.BooksDBConstants;

public class AddBookServlet extends GenericServlet{
	public void service(ServletRequest req,ServletResponse res) throws IOException,ServletException
	{
		PrintWriter pw = res.getWriter();
		
		res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);
		
		String bCode = req.getParameter(BooksDBConstants.COLUMN_BARCODE);
		String bName = req.getParameter(BooksDBConstants.COLUMN_NAME);
		String bAuthor = req.getParameter(BooksDBConstants.COLUMN_AUTHOR);
		int bPrice =Integer.parseInt(req.getParameter(BooksDBConstants.COLUMN_PRICE));
		int bQty = Integer.parseInt(req.getParameter(BooksDBConstants.COLUMN_QUANTITY));
		String imageUrl= req.getParameter(BooksDBConstants.COLUMN_IMAGE);
		
		try {
			Connection con = DBConnection.getCon();
			PreparedStatement ps = con.prepareStatement("insert into " + BooksDBConstants.TABLE_BOOK + "  values(?,?,?,?,?,?)");
			ps.setString(1, bCode);
			ps.setString(2, bName);
			ps.setString(3, bAuthor);
			ps.setInt(4, bPrice);
			ps.setInt(5, bQty);
			ps.setString(6, imageUrl);
			int k = ps.executeUpdate();
			if(k==1)
			{
				RequestDispatcher rd = req.getRequestDispatcher("AddBook.html");
				rd.include(req, res);
				pw.println("<table class=\"tab\"><tr><td>Book Detail Updated Successfully!<br/>Add More Books</td></tr></table>");
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("AddBook.html");
				pw.println("<table class=\"tab\"><tr><td>Failed to Add Books! Fill up CareFully</td></tr></table>");
				rd.include(req, res);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
