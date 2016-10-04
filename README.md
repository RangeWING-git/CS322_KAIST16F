#CS322 16F Project
* Author: 20150717 조영걸 [전산학부\] (rangewing@kaist.ac.kr)
* Used Language: Java (1.8)
* Written and Tested Environment
    * Ubuntu 14.04.5 LTS, GNU/Linux 4.4.0-36-generic x86_64
    * JDK 1.8.0_65, JRE 1.8.0_101
* Charset: UTF-8
* Last Update: Oct 4th, 2016

##예비 프로젝트 1-1
이 프로그램은 DFA를 읽어 class DFA의 인스턴스로 저장하고,
input의 각 string을 DFA에 입력했을 때 accept되는지 검사하여 '예' 또는 '아니요'를 output에 출력한다.
screenshots 폴더의 스크린샷을 참조하면 된다.

##예비 프로젝트 1-2
이 프로그램은 Mealy Machine를 읽어 class Mealy의 인스턴스로 저장하고,
input의 각 string을 Mealy Machine에 입력했을 때 출력되는 문자열을 output에 출력한다.
존재하지 않는 path이면 'No path exists!'를 출력한다.
screenshots 폴더의 스크린샷(Execution_P1_2)을 참조하면 된다.

###Files
* root
    * src (source codes)
        * cs322.common
            * DFA.java
            * FileHandler.java
            * Mealy.java
            * Pair.java
            * State.java
        * cs322.p1_1 (Pre-project 1-1)
            * FileHandler_P1_1.java
            * P1_1.java (Main)
        * cs322.p1_2 (Pre-project 1-2)
            * FileHandler_P1_2.java
            * P1_2.java (Main)
        * cs322.main1 (Main-project 1)
            * PMain1.java (Main)
    * bin (classes)
    * screenshots
    * testcase
        * dfa.txt
        * input.txt
        * output.txt
        * mealy.txt
        * input_mealy.txt
        * output_mealy.txt
    * README.md

### How to compile
At the src folder,
* Pre-project 1-1: `$ javac cs322/p1_1/P1_1.java`
* Pre-project 1-2: `$ javac cs322/p1_2/P1_2.java`

### How to execute
Where the class files exists (e.g. bin folder or src folder after compile; look at the screenshot),
* Pre-project 1-1:
  `$ java cs322.p1_1.P1_1 <DFA_FILE_PATH> <INPUT_FILE_PATH> <OUTPUT_FILE_PATH>`
  * example: `$ java cs322.p1_1.P1_1 ../testcase/dfa.txt ../testcase/input.txt ../testcase/output.txt`
* Pre-project 1-2:
  `$ java cs322.p1_2.P1_2 <MEALY_FILE_PATH> <INPUT_FILE_PATH> <OUTPUT_FILE_PATH>`
  * example: `$ java cs322.p1_2.P1_2 ../testcase/mealy.txt ../testcase/input.txt ../testcase/output.txt`

   or omit the parameters to use the following default path settings:
  * DFA_FILE_PATH = ./dfa.txt
  * MEALY_FILE_PATH = ./mealy.txt
  * INPUT_FILE_PATH = ./input.txt
  * OUTPUT_FILE_PATH = ./output.txt

