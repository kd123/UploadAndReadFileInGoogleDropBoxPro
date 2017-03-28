package kd.musk.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.Locale;

import com.dropbox.core.DbxAccountInfo;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE 	= 1024 * 1024 * 3; 	// 3MB
	private static final int MAX_FILE_SIZE 		= 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE 	= 1024 * 1024 * 50; // 50MB
	private static final String DROP_BOX_APP_KEY = "enter drop box app key";
	private static final String DROP_BOX_APP_SECRET = "enter drop box app secret";
	DbxClient dbxClient;

	/**
	 * handles file upload via HTTP POST method
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter writer = response.getWriter();
			writer.println("Request does not contain upload data");
			writer.flush();
			return;
		}
		
		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		// constructs the directory path to store upload file
		String uploadPath = getServletContext().getRealPath("")
			+ File.separator + UPLOAD_DIRECTORY;
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		try {
			// parses the request's content to extract file data
			SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
			List formItems = upload.parseRequest(request);
			Iterator iter = formItems.iterator();
			String pan="";
			String name="";
			Date date=new Date();
			// iterates over form's fields
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// processes only fields that are not form fields
				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					String filePath = uploadPath + File.separator + fileName;
					System.out.println(fileName);
					File storeFile = new File(filePath);
					System.out.println(storeFile);
					Scanner sc = new Scanner(storeFile);
					pan=sc.nextLine();
					name=sc.nextLine();
					date= formatter.parse(sc.nextLine());
					String panStr[]=pan.split(":");
					pan=panStr[1];
					String nameStr[]=name.split(":");
					name =nameStr[1];
					System.out.println(pan);
					System.out.println(name);
					System.out.println(date);
					// saves the file on disk
					UploadServlet javaDropbox = new UploadServlet();
					javaDropbox.authDropbox(DROP_BOX_APP_KEY, DROP_BOX_APP_SECRET);
					System.out.println("Dropbox Size: " + javaDropbox.getDropboxSize()
							+ " GB");
					javaDropbox.uploadToDropbox(fileName,filePath);
					item.write(storeFile);
				}
			}
			request.setAttribute("PAN", pan);
			request.setAttribute("NAME", name);
			request.setAttribute("DATE", date);
			request.setAttribute("message", "Upload has been done successfully!");
		} catch (Exception ex) {
			request.setAttribute("message", "There was an error: " + ex.getMessage());
		}
		getServletContext().getRequestDispatcher("/upload.jsp").forward(request, response);
	}
	public DbxClient authDropbox(String dropBoxAppKey, String dropBoxAppSecret)
			throws IOException, DbxException {
		DbxAppInfo dbxAppInfo = new DbxAppInfo(dropBoxAppKey, dropBoxAppSecret);
		DbxRequestConfig dbxRequestConfig = new DbxRequestConfig(
				"UploadServletApp/1.0", Locale.getDefault().toString());
		DbxWebAuthNoRedirect dbxWebAuthNoRedirect = new DbxWebAuthNoRedirect(
				dbxRequestConfig, dbxAppInfo);
		String authorizeUrl = dbxWebAuthNoRedirect.start();
		System.out.println("1. Authorize: Go to URL and click Allow : "
				+ authorizeUrl);
		System.out
				.println("2. Auth Code: Copy authorization code and input here ");
		/*String dropboxAuthCode ="enter drobbox auth code"; 
		DbxAuthFinish authFinish = dbxWebAuthNoRedirect.finish(dropboxAuthCode);
		System.out.println(authFinish);*/
		String authAccessToken = "enter auth token";
		dbxClient = new DbxClient(dbxRequestConfig, authAccessToken);
		System.out.println("Dropbox Account Name: "
				+ dbxClient.getAccountInfo().displayName);

		return dbxClient;
	}

	/* returns Dropbox size in GB */
	public long getDropboxSize() throws DbxException {
		long dropboxSize = 0;
		DbxAccountInfo dbxAccountInfo = dbxClient.getAccountInfo();
		// in GB :)
		dropboxSize = dbxAccountInfo.quota.total / 1024 / 1024 / 1024;
		return dropboxSize;
	}

	public void uploadToDropbox(String fileName,String filePath) throws DbxException,
			IOException {
		System.out.println(fileName);
		File inputFile = new File(filePath);
		System.out.println(inputFile);
		FileInputStream fis = new FileInputStream(inputFile);
		System.out.println(fis);
		try {
			DbxEntry.File uploadedFile = dbxClient.uploadFile("/" + fileName,
					DbxWriteMode.add(), inputFile.length(), fis);
			String sharedUrl = dbxClient.createShareableUrl("/" + fileName);
			System.out.println("Uploaded: " + uploadedFile.toString() + " URL "
					+ sharedUrl);
		} finally {
			fis.close();
		}
	}
}
