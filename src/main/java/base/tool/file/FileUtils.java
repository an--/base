package base.tool.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {

	/**
     * file copy , use FileChannel;
     * @param originalFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyByChannel( File originalFile , File targetFile ) throws IOException {
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inStream = new FileInputStream(originalFile);
            outStream = new FileOutputStream(targetFile);
            inChannel = inStream.getChannel();
            outChannel = outStream.getChannel();
            inChannel.transferTo(inChannel.position(),inChannel.size(),outChannel);
        }catch (IOException e ){
            throw e;
        }finally {
            if(null != inChannel){
                inChannel.close();
            }
            if( null != inStream){
                inStream.close();
            }
            if( null != outChannel){
                outChannel.close();
            }
            if( null != outStream){
                outStream.close();
            }
        }
    }

    /**
     * file copy , use FileChannel;
     * if originalFile not exists throw FileNotFoundException;
     * if targetFile not exists  createNewFile;
     *
     * @param originalFile
     * @param targetFilePath
     * @throws IOException
     */
    public static void copyByChannel( File originalFile , String targetFilePath ) throws IOException {
        if( !originalFile.exists() ){
            throw new FileNotFoundException( originalFile.getPath() + " not exists" );
        }
        File targetFile = new File(targetFilePath);
        boolean targetCreate = false;
        if( !targetFile.exists()) {
            File parentFile = targetFile.getParentFile();
            if( !parentFile.exists() ){
                parentFile.mkdirs();
            }
            targetFile.createNewFile();
            targetCreate = true;
        }
        try {
            copyByChannel(originalFile, targetFile);
        }catch ( IOException e){
            if( targetCreate ){
                targetFile.delete();
            }
            throw e;
        }
    }

    /**
     * file copy , use FileChannel;
     * if originalFile not exists throw FileNotFoundException;
     * if targetFile not exists  createNewFile;
     *
     * @param originalFilePath
     * @param targetFilePath
     * @throws IOException
     */
    public static void copyByChannel( String originalFilePath , String targetFilePath ) throws IOException {
        File originalFile = new File(originalFilePath);
        copyByChannel(originalFile,targetFilePath);
    }
    
    
    
    public static void copyByOs(File sfile, File dfile) throws Exception{
		File dpdir = dfile.getParentFile();
		if (!dpdir.exists()) {
			dpdir.mkdirs();
		}
		String src = sfile.getAbsolutePath();
		String dest = dfile.getAbsolutePath();
		
		Runtime rt = Runtime.getRuntime();
		String cmd[] = null;
//		BufferedReader br = null;
		try {
			if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
				cmd = new String[] { "cmd.exe", "/c", "copy " + src + " " + dest };
			} else {
				cmd = new String[] { "/bin/sh", "-c", "cp -f " + src + " " + dest };
			}
			Process p = rt.exec(cmd);
//			if (p.waitFor() == 0) {
//				log.debug("拷贝文件{}到{}", src, dest);
//			} else {
//			if (log.isDebugEnabled()) {
//				log.debug(cmd[2]);
//				br = new BufferedReader(new InputStreamReader(p.getInputStream(), System.getProperty("sun.jnu.encoding")));
//				String line = null;
//				while((line = br.readLine()) != null) {
//					log.debug(line);
//				}
//			}
//			}
		} catch (Exception e) {
//			log.error("拷贝文件{}到{}失败：{}", cmd[2], e.getMessage());
			throw e;
		} finally {
//			close(br);
		}
	}
	
}
