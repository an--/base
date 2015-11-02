package file.read;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransCoding {

	public static final String UTF8_BOM = "\uFEFF";
	
	/**
	 * @throws IOException
	 */
	public static void copyFile( ) throws IOException{
		String filePath = "/Volumes/warehouse/Music/album/风之谷/CDImage.cue";
		String filePath_ = filePath + "_";
		File file = new File(filePath);
		File file_ = new File(filePath_);
		InputStream inStream = null;
		
		try {
			OutputStream out = new FileOutputStream(file_);
			
			FileReader reader = null;
			reader = new FileReader(file);
			BufferedReader buf = new BufferedReader(reader);
			String line = null;
			while( null != ( line = buf.readLine() ) ){
				out.write( (line + System.getProperty("line.separator") ).getBytes("cp1252") );
			}
//			inStream = new FileInputStream(file);
//			BufferedInputStream buf = new BufferedInputStream(inStream);
			
//			byte[] bufBytes = new byte[1024];
//			for( int i = 0; -1 != buf.read(bufBytes) ; i++){
//				String str = new String(bufBytes , "utf-8" );
//				if( i == 0){
//					if( str.startsWith(UTF8_BOM) ){
//						str = str.substring(1);
//					}
//				}
//				out.write( str.getBytes("cp1252") );
//			}
			buf.close();
			reader.close();
//			inStream.close();
			out.close();
			System.out.println(file_);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
	}
	
	public static void main( String[] args ){
		try {
			copyFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

