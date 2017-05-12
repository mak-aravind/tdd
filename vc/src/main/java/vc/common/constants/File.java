package vc.common.constants;

public class File {
	/*private File(){
		throw new AssertionError();//Big Bang when instantiated from native class(locally)
	}*/
	
	public File(){}
	public static final String FILE_NAME="full.data";
	public static final String TEST_FILE_NAME="invalid.data";
	public static final String EMPTY_FILE_NAME="empty.data";
	public static final String INPUT_VALIDATION_REGEX = "^[AaBb][0-9]+$";
}
