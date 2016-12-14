# CS322 16F Project
* Author: 20150717 조영걸 [전산학부\] (rangewing@kaist.ac.kr)
* Used Language: Java (1.8)
* Written and Tested Environment
    * Ubuntu 14.04.5 LTS, GNU/Linux 4.4.0-36-generic x86_64, JDK 1.8.0_65, JRE 1.8.0_101
    * Windows 10 Pro, x64, JDK 1.8.0_65, JRE 1.8.0_77
* Charset: UTF-8
* Last Update: Nov 21th, 2016

## Project 1
### 예비 프로젝트 1-1
이 프로그램은 DFA를 읽어 class DFA의 인스턴스로 저장하고,
input의 각 string을 DFA에 입력했을 때 accept되는지 검사하여 '예' 또는 '아니요'를 output에 출력한다.
screenshots 폴더의 스크린샷을 참조하면 된다.

### 예비 프로젝트 1-2
이 프로그램은 Mealy Machine을 읽어 class Mealy의 인스턴스로 저장하고,
input의 각 string을 Mealy Machine에 입력했을 때 출력되는 문자열을 output에 출력한다.
존재하지 않는 path이면 'No path exists!'를 출력한다.
screenshots 폴더의 스크린샷(Execution_P1_2)을 참조하면 된다.

### 본 프로젝트 1
이 프로그램은 키보드로 입력하는 문자를 읽어 한글로 조합해 출력한다.
초성우선 모드와 받침우선 모드를 지원한다.
Java에서 Console 실시간 input을 지원하지 않는 관계로 input 후 엔터를 누르면 output이 출력되는 형태로 제작하였고,
백스페이스 키는 '<'로 입력하면 된다. EXIT을 입력하여 종료할 수 있다.
argument에 GUI를 입력하여 GUI mode로 들어갈 수 있으며, GUI mode에서는 백스페이스도 지원한다.
허용되지 않은 case (e.g. 모음만 입력)에서는 "Not valid input"을 출력한다.
screenshots 폴더의 스크린샷(Execution_Main1_*)을 참조하라.

## Project 2
### 예비 프로젝트 2-1
이 프로그램은 ε-NFA를 읽어 (partial) DFA로 변환한 후, 변환된 DFA를 minimize하여 m-DFA로 변환한다.
screenshots 폴더의 스크린샷(Execution_P2_1)을 참조하라.

###본 프로젝트 2
이 프로그램은 Regular Expression을 읽어 ANTLR4를 이용하여 Parse Tree를 만든 후, 이를 AST로 변환하고, 
AST를 traverse하며 ε-NFA로 변환한다. 변환된 ε-NFA는 예비 프로젝트 2-1를 이용하여 m-DFA로 변환한다.
Dead State는 문자 '~'를 사용하여 나타낸다. screenshots 폴더의 스크린샷(Execution_Main2_*)을 참조하라.
사용한 grammar file인 RE.g4와 ANTLR4로 generate된 java file은 모두 src의 cs322.main2.antr 패키지에 존재한다. 
ANTLR4 폴더에도 복사하여 두었다. Symbol은 영어 대소문자 및 숫자를 사용할 수 있다.

## Project 3
### 본 프로젝트 3
이 프로그램은 KT 나랏글 자판을 이용하여 한글을 조합해 출력한다.
본 프로젝트 2의 RE to m-DFA 변환 프로그램과 예비 프로젝트 1-2의 Mealy Machine을 사용하였다.
초성우선 모드와 받침우선 모드를 지원하며, TUI 및 GUI (실시간 입력) 모드를 지원한다.
키보드 왼쪽의 12키, 즉 123 qwe asd zxc 키를 사용하며 백스페이스 키는 '<'을 사용한다.
GUI 모드에서는 Backspace 키 또한 사용 가능하다.
허용되지 않는 case에 대해서는 "Invalid Input"을 출력한다.
screenshots 폴더의 스크린샷 (Execution_Main3_*)을 참조하라.

## How to compile
At the src folder,
* Pre-project 1-1: `$ javac cs322/p1_1/P1_1.java`
* Pre-project 1-2: `$ javac cs322/p1_2/P1_2.java`
* Main-project 1: `$ javac cs322/main1/PMain1.java`
* Pre-project 2-1: `$ javac cs322/p2_1/P2_1.java`
* Main-project 2: `$ javac -cp .;../lib/antlr-4.5.3-complete.jar cs322/main2/PMain2.java`
* Main-project 3: `$ javac -cp .;../lib/antlr-4.5.3-complete.jar cs322/main3/PMain3.java`
* You should append `-encoding UTF-8` option when compiling on Windows.

## How to execute
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

* Main-project 1:  
  `$ java cs322.main1.PMain1 <mode> <GUI/TUI(default)> <datapath>`
  * mode: 0 = 받침우선(Default), 1 = 초성우선
  * Do not use datapath option, unless it could not find the folder 'data'
  * example (TUI + 받침우선): `$ java cs322.main1.PMain1 0 `
  * example (GUI + 초성우선): `$ java cs322.main1.PMain1 1 GUI `

* Pre-project 2-1:  
    `$ java cs322.p2_1.P2_1 <E-NFA_FILE_PATH> <OUTPUT(M-DFA)_FILE_PATH>`
    * example: `$java cs322.p2_1.P2_1 ../testcase/e-nfa.txt ../m-dfa.txt`

* Main-project 2 [Only for Windows]:  
    `$java -cp .;../lib/antlr-4.5.3-complete.jar cs322.main2.PMain2 <RE_FILE_PATH> <OUTPUT(M-DFA)_FILE_PATH>`
    * example: `$java -cp .;../lib/antlr-4.5.3-complete.jar cs322.main2.PMain2 ../testcase/re.txt ../m-dfa.txt`
    * How to execute the parser generator, ANTLR4
        * 실제 개발 시에는 IntelliJ IDEA의 ANTLR plugin을 사용하여 parser generation을 수행하였지만, 
        command line에서 할 수 있는 방법을 소개한다. (Windows)
            * Unix/Linux는 https://github.com/antlr/antlr4/blob/master/doc/getting-started.md를 참고하여라.
        * 루트 폴더에서 다음 cmd를 열고 다음 command를 실행한다.
        ```
        antlr4 ANTLR4/RE.g4 -o ANTLR4 -visitor -encoding UTF-8  
        javac -cp .;ANTLR4;lib\antlr-4.5.3-complete.jar ANTLR4/RE*.java
        ```
        * ANTLR4 폴더에서 generate된 java file과 class file을 볼 수 있다. 다음과 같이 테스트할 수 있다.
        -gui 옵션 대신 -tree 옵션을 사용할 수 있으며, 끝에 반드시 EOF를 붙여야 한다.
        ```
        cd ANTLR4
        "../grun" RE e -gui
        <Parsing할 RE>
        ^Z
        ```

* Main-project 3:  
  Execute the following command at the root folder:  
  `$ java -cp ./bin/;./lib/antlr-4.5.3-complete.jar cs322.main3.PMain3 <mode> <GUI/TUI(default)> <datapath>`
  * mode: 0 = 받침우선(Default), 1 = 초성우선
  * Do not use datapath option, unless it could not find the folder 'data'
    * example (TUI + 받침우선): `$ java -cp ./bin/;./lib/antlr-4.5.3-complete.jar cs322.main3.PMain3 0 `
    * example (GUI + 초성우선): `$ java -cp ./bin/;./lib/antlr-4.5.3-complete.jar cs322.main3.PMain3 1 GUI `
  * Or use run_p3.bat (on Windows, pre-set with GUI + 받침우선) 