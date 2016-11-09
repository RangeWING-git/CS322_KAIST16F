# CS322 16F Project
* Author: 20150717 조영걸 [전산학부\] (rangewing@kaist.ac.kr)
* Used Language: Java (1.8)
* Written and Tested Environment
    * Ubuntu 14.04.5 LTS, GNU/Linux 4.4.0-36-generic x86_64
    * JDK 1.8.0_65, JRE 1.8.0_101
    * Also tested on Windows 10 Pro, x64, JDK 1.8.0_65, JRE 1.8.0_77
* Charset: UTF-8
* Last Update: Nov 9th, 2016

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

## Files
* root
    * src (source codes)
        * cs322.common
            * DFA.java
            * FileHandler.java
            * Mealy.java
            * Pair.java
            * State.java
            * E_NFA.java
            * StateN.java
        * cs322.p1_1 (Pre-project 1-1)
            * FileHandler_P1_1.java
            * P1_1.java (Main)
        * cs322.p1_2 (Pre-project 1-2)
            * FileHandler_P1_2.java
            * P1_2.java (Main)
        * cs322.main1 (Main-project 1)
            * PMain1.java (Main)
            * FileHandler_Main1.java
            * Hangeul.java
            * HMealy.java
            * HState.java
        * cs322.p2_1 (Pre-project 2-1)
            * FileHandler_P2_1.java
            * P2_1.java (Main)
    * bin
    * data
        * OutFunc.txt
        * TFunc.txt
        * SymbolTable0.txt
        * SymbolTable1.txt
        * SymbolTable2.txt
        * SymbolTable3.txt
    * screenshots
    * testcase
        * dfa.txt
        * input.txt
        * output.txt
        * mealy.txt
        * input_mealy.txt
        * output_mealy.txt
        * e-nfa.txt
        * m-dfa.txt
    * README.md

### How to compile
At the src folder,
* Pre-project 1-1: `$ javac cs322/p1_1/P1_1.java`
* Pre-project 1-2: `$ javac cs322/p1_2/P1_2.java`
* Main-project 1: `$ javac cs322/main1/PMain1.java`
* Pre-project 2-1: `$ javac cs322/p2_1/P2_1.java`
* You should append `-encoding UTF-8` option when compiling on Windows.

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

* Main-project 1:
  `$ java cs322.main1.PMain1 <mode> <GUI/TUI(default)> <datapath>`
  * mode: 0 = 받침우선(Default), 1 = 초성우선
  * Do not use datapath option, unless it could not find the folder 'data'
  * example (TUI + 받침우선): `$ java cs322.main1.PMain1 0 `
  * example (GUI + 초성우선): `$ java cs322.main1.PMain1 1 GUI `

* Pre-projcet 2-1:
    `$ java cs322.p2_1.P2_1 <E-NFA_FILE_PATH> <OUTPUT(M-DFA)_FILE_PATH>`
    * example: `$java cs322.p2_1.P2_1 testcase/e-nfa.txt ./m-dfa.txt`
