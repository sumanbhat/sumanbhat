

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Servlet implementation class ConvertServlet
 */
public class ConvertServlet extends HttpServlet {
	
	 private static final long serialVersionUID = 1L;
	 private static Writer csvOut=null;
	 private static final String CSV_PATH = "/CSV";
	 private static String CSVPath=null;
     
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConvertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		  CSVPath = getServletContext().getRealPath("")
          + File.separator + CSV_PATH;
		  System.out.println("csv path::"+CSVPath);
		
		//Calling Conversion method from CreateCSV class
		String filename=request.getParameter("filename");
		System.out.println(filename);
		
        Zen_User(filename); 
        
        request.setAttribute("message",
        "File Conversion has been done successfully!");
        
        // redirects client to message page
        request.getRequestDispatcher("/download.jsp").forward(request, response);
             
	}
	
	 public static void Zen_User(String filename) {
			
			
		
		
		 System.out.println("FILE NAME::"+filename);
		
		 
		FileInputStream file=null;
		XSSFWorkbook workbook=null;
		XSSFSheet sheet=null;
		int dataRowNum=0;
		
		try 
		{
			
			file = new FileInputStream(new File(filename));
		
			System.out.println("load file...");
			workbook =new XSSFWorkbook(file);
			System.out.println("Parse file...");
			sheet=workbook.getSheet("Agents");
			System.out.println("sheet"+sheet);
			Iterator<Row> iterator = sheet.iterator();
	         
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                
	              
	                
	                if(cell.getStringCellValue().equals("Name"))
	                {
	               	  
	                  int rowNum=nextRow.getRowNum();
	                  dataRowNum=rowNum+1;
	                  System.out.println("rowNum--->>>"+rowNum);
	                  System.out.println("************Start of the Actual data*********");
	                  
	                  csvOut=writeCSV();
	                  
	                  for(int i=dataRowNum;i<sheet.getLastRowNum();i++)
	                  {
	                	
	               	  
	               	  Row dataRow=sheet.getRow(i);
	               	  if(dataRow.getCell(0)==null)	  
	                  {
	               		System.out.println("***********End of File***************");//if blank get out of the list
	                    break;
	                	  
	                  }
	               	XSSFCellStyle style=(XSSFCellStyle) dataRow.getCell(0).getCellStyle();
                    long color=style.getCoreXf().getFillId();
	                System.out.println("Color Code::"+color);
	                if(color==6)
                    	System.out.println("*******Delete Record****");
	               	  
	               	 else
	               	 {
	               		 
	               	   
	                	System.out.println("------------------------------------------------");	
	                    
	               	    System.out.println("Row Number--->>"+dataRow.getCell(0).getRowIndex());
	                    
	               	 
	               	 
	               	  String name=dataRow.getCell(0).getStringCellValue();  
	               	  String email=dataRow.getCell(1).getStringCellValue();
	               	  String restriction=dataRow.getCell(4).getStringCellValue();
	               	  String organization=dataRow.getCell(5).getStringCellValue();
	               	  organization=organization.replace(",", "|");
            		  System.out.println("Name:"+name); 
            		  System.out.println("Email:"+email); 
            		  System.out.println("role:"+restriction); 
            		  System.out.println("organization:"+organization);
            		  
            		  
            		  csvOut.append(name+",");
            		  csvOut.append(email+",,,,,");
            		  csvOut.append("agent,");
            		  csvOut.append(restriction+",");
            		  csvOut.append(organization+",,");
            		  csvOut.append('\n');
            		  csvOut.flush();
	               	 }
	                  }
	                  
	                  }
	                }
	                
	        }
	        
	     }
		
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
			} 
		catch (IOException e) {
			e.printStackTrace();
		    } 
		finally{
			try {
				if(workbook!=null)
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
}
	 
	 public static Writer writeCSV()
		{
			File fileDir=null;
		    File filePath=null;
		    String path="UserLoad.csv";
		   
		  fileDir=new File(CSVPath);
		  fileDir.setWritable(true);
		  if(!fileDir.exists())
		  {
			  fileDir.mkdirs();
			  System.out.println("Directory created");
		 
		}
		else
		{
			System.out.println("Directory already exists!!");
			
		}
			filePath=new File(fileDir,path);
			System.out.println("csv file path::"+filePath);
			filePath.setWritable(true);
			if(!filePath.exists())
			{
				try {
					filePath.createNewFile();
					System.out.println("file Created");
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("File already exists!!");
				
			}
			try {
				  csvOut=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
				try {
					
				    String CSVCaption="name,email,external_id,details,notes,phone,role,restriction,organization,tags";

					csvOut.write(CSVCaption);
					csvOut.append('\n');
					
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			return csvOut;
			
			
		}

}
