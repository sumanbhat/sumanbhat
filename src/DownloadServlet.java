

import java.io.File;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("Downloading......");
	    URL dl = null;
        File fl = null;
        String filename=null;
        
        try {
        	filename="UserLoad.csv";
            fl = new File(System.getProperty("user.home").replace("\\", "/") + "/Desktop/userLoad.csv");
            dl = new URL("http://localhost:9080/FileConversionApp/CSV/"+filename);
            FileUtils.copyURLToFile(dl, fl);
            
            System.out.println("Download Completed");
            request.setAttribute("message",
            "Download has been done successfully!");
        
        } catch (Exception ex) {
        	request.setAttribute("message",
                    "There was an error: " + ex.getStackTrace());
            ex.printStackTrace();
        }
        
        request.getRequestDispatcher("/result.jsp").forward(request, response);
	}

}
