package vc.io.reader


import java.util.List
import static vc.common.util.FileUtil.getInputStreamReader
import static vc.common.constants.File.EMPTY_FILE_NAME
import static vc.common.constants.File.TEST_FILE_NAME
import static java.util.Collections.EMPTY_LIST;
import spock.lang.Specification

class InputReaderSpec extends Specification{
	def "Usage of non-existing file should throw FileNotFoundException"(){
		
		when: 
			def fileInput = getInputStreamReader("doesNotExist.txt")
		then: 
			thrown(FileNotFoundException)
	}
	
	def "Usage of empty file should return empty list"(){
		given:
			def fileInput = getInputStreamReader(EMPTY_FILE_NAME)
			def inputReader = new InputReader(fileInput)
		when:
			def result = inputReader.getParsedLines() 
		then:
			result == EMPTY_LIST
	}
	
	def "Force IOException to expect a empty list"(){
		given:
			def fileInput = getInputStreamReader(TEST_FILE_NAME)
		when:
			fileInput.close()//trigger IOException
			def inputReader = new InputReader(fileInput)
			def result = inputReader.getParsedLines() 
		then:
			result == EMPTY_LIST
	}

	
	def "Any line NOT starting with <a or A or b or B> and NOT appended with numbers"(String line1,String line2, List result){
		
		given:
			def input = [line1,line2]
			def stringReader = new StringReader(input.join("\n"))
			def inputReader = new InputReader(stringReader);
		expect:
			inputReader.getParsedLines() == result
		where:
			line1 		|line2		|result
			"Aaaa2345"	|"B547896"	|EMPTY_LIST
			"allchars"	|"89438493"	|EMPTY_LIST
			1			|8			|EMPTY_LIST
			"A"			|"B"		|EMPTY_LIST	
	}

	def "Each line starts with <a or A or b or B> and appended with numbers"(String line1,String line2, List result){
		given:
			def input = [line1,line2]
			def stringReader = new StringReader(input.join("\n"))
			def inputReader = new InputReader(stringReader)
		expect:
			inputReader.getParsedLines() == result
		where:
			line1 		|line2		|result
			"a2345"		|"b547896"	|[line1.toUpperCase(),line2.toUpperCase()]
			"a243345"	|"a84597"	|[line1.toUpperCase(),line2.toUpperCase()]
			"A84679"	|"b845796"	|[line1,line2.toUpperCase()]
			"a47136"	|"A5437961"	|[line1.toUpperCase(),line2]
			"A2345"		|"B547896"	|[line1,line2]
	}
	
	def "Check Regular Expression"(String line1,String line2, List result){
		
		given:
			def input = [line1,line2]
			def stringReader = new StringReader(input.join("\n"))
			def inputReader = new InputReader(stringReader);
		expect:
			inputReader.getParsedLines() == result
		where:
			line1 		|line2		|result
			"A2345AhkE"	|"B547896"	|EMPTY_LIST
			1			|"A45"		|EMPTY_LIST
			"A3"		|"B45"		|[line1,line2]
			"#jkjkdf"	|"!jkjkjd"	|EMPTY_LIST
	}
}
