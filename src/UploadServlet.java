

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private boolean isMultiPart;
	private String filePath;
	private int maxFileSize = 50*1024;
	private int maxMemSize = 4*1024;
	private File file;
	
	public void init(){
		filePath = getServletContext().getInitParameter("file-upload");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		isMultiPart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(!isMultiPart){
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet Upload</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>No file uploaded</p>");
			out.println("</body>");
			return;
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(maxMemSize);
		factory.setRepository(new File("c:\\temp"));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);
		
		try {
			
			List fileItems = upload.parseRequest(request);
			Iterator i = fileItems.iterator();
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			while(i.hasNext()){
				FileItem fi = (FileItem) i.next();
				if(!fi.isFormField()){
					String fieldName = fi.getFieldName();
					String fileName = fi.getName();
					String contentType = fi.getContentType();
					boolean isInMemory = fi.isInMemory();
					long sizeInBytes = fi.getSize();
					
					if(fileName.lastIndexOf("\\") >= 0){
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
					}
					else{
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")+1));
					}
					
					fi.write(file);
					out.println("Uploaded file name: " + fileName + "<br>");
				}
			}
			out.println("</body>");
			out.println("</html>");
			
			
		}catch(Exception e){
			System.out.println(e);
		}
	}

}
