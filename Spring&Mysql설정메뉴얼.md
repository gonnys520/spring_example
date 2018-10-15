[Spring & MySQL menual]

<1. Spring을 다운받는 3가지 방법>

1. 직접 완성형 파일 받기
- 1. https://spring.io/tools3/sts/all ver.3 url로 접속
- 2. Windows Based on Eclipse 4.9.0 64bit download
- 3. lombok과 tomcat file 필요
- 4. mysql이 유료이기 때문에 대신에 오픈 소스인 mariaDB 사용
- 5. mysql open sourse download (but 완전한 무료는 X)
     - 1. https://dev.mysql.com/downloads/windows/installer/8.0.html
     - 2. Windows (x86, 32-bit), MSI Installer 8.0.121 5.9M 다운로드
     - 3. No thanks, just start my download.

2. 이클립스에 연결시키는 방법
- Help -> Eclipse MarkertPlace -> popular tab - > Spring Tools 3 Add-on install

3. update site 추가 방법
- Help -> Install new software -> https://spring.io/tools3/sts/all URL의 Update Sites 에서 URL 복붙하기



<2. Spring 환경 설정>
1. lombok.jar 실행 -> sping install -> sts-3.9.6.RELEASE 에 lombok.jar 파일 있는지 확인

2. STS.ini 파일 비쥬얼스튜디오로 열기 -> 코드 맨위에
 -vm
C:\Program Files\Java\jdk1.8.0_181\bin\javaw.exe javw.exe
-> 파일 경로추가 (lombok 제대로 추가하는 작업)

3. sts.exe 실행

4. 고급 시스템 설정 - 환경 변수 - JAVA HOME(C:\Program Files\Java\jdk1.8.0_181) 과 PATH (%JAVA_HOME%\bin) 확인



<3. Spring Project 작업 순서>
(pom.xml에 라이브러리 추가 --> root-context.xml에 <bean>추가 / 의존성 셋팅 -->  소스코드 작성 --> 테스트)

1. File -> new -> Spring regarcy project -> Project name 작성 -> Templates:  Spring MVC Project ->
도메인 네임 (org.gonnys.controller) 작성 -> finish

2. 하단의 progress 탭을 통해 Spring MVC Project에 필요한 파일들이 다운로드 되는 걸 볼 수 있음.

3. HomeController.java 파일에서 private(...) 삭제 후 public 아래의 Logger.info 주석 처리

4. pom.xml 파일에서 spingframework 버전 수정 (12라인)
<org.springframework-version>5.1.0.RELEASE</org.springframework-version>

5. maven 1.8 버전으로 수정 (141, 142라인)
<source>1.8</source>
<target>1.8</target>

6. 프로젝트 우클릭 - > Maven - > Update Project 설정 / JRE System Libaray가 JavaSE-1.8 로 변경되었는지 확인
(설정 에러날때 c:\사용자\.m2\repository 밀고 다시 실행해보자)
- 프로젝트 실행은 Run as - Run on server - Manually define a new server - Tomcat v9.0 Server로 설정 잡고 실행
- 설정파일은 src->main->webapp->WEB-INF->spring->appServlet->servlet-context.xml 에 있다!

7. pom.xml에서 dependencis에 lombok, log4j, mysql, hikariCP mavan 라이브러리 추가 (120라인)

 <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.2</version>
    <scope>provided</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>

<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.12</version>
</dependency>

<!-- https://mvnrepository.com/artifact/com.zaxxer/HikariCP -->
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>3.2.0</version>
</dependency>

8. pom.xml 파일의 junit version 4.12로 변경
<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>4.12</version>
	<scope>test</scope>
</dependency>

9. 프로젝트 -> src -> main -> webapp -> WEB-INF -> spring -> root-context.xml
에서 Hikari 의존성 셋팅을 위해 해당 코드 <bean> </bean> 사이에 작성

<bean id="hikariConfjg" class="com.zaxxer.hikari.HikariConfig">
	<property name="driverClassName"
	value="com.mysql.cj.jdbc.Driver"></property>
	<!-- '&'는 특수문자라 인식이 안되므로 &다음에 amp;를 써줘야한다 -->
	<property name="jdbcUrl"
	value="jdbc:mysql://localhost:3306/jr?useSSL=false&amp;serverTimezone=UTC"></property>
	<property name="username" value="jr2018"></property>
	<property name="password" value="jr2018"></property>
</bean>

<bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
	<constructor-arg ref="hikariConfjg"></constructor-arg>
</bean>


10. Spring Test를 위해 pom.xml 파일에서(33라인) spring-webmvc 태그 복사해서 아래 복붙 후
<artifactId>spring-webmvc</artifactId> 를 test로 변경

<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-test</artifactId>
	<version>${org.springframework-version}</version>
</dependency>


11. 프로젝트 -> src/main/resources -> log4j.xml -> <level value="debug" /> 로 3개정도 바꿔주자

12. HomeController.java (Log4j TEST)
- public class 위에 @Log4j 추가
- public String home(Locale locale, Model model) {}에 log.warn("LOG4J TEST");
코드 작성 후 로그 정상적인지 확인


13. DataSourceTests.java (controller pakage 아래 new class생성 / junit TEST)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DatasourceTests {
	
	@Autowired
	private DataSource ds;
	
	@Test
	public void test1() {
		log.info("test1.........................");
	}
	
	@Test
	public void testConnection() throws SQLException{
		log.info(ds.getConnection());
	}

}
- 코드 작성후 junit test 실행해서 정상적으로 작동하는 지 확인.

14. JDBCTests.java (controller pakage 아래 new class생성 / JDBC TEST)
@Log4j
public class JDBCTests {
	
	@Test
	public void testConnection() throws Exception {
		log.info("test Connection Start.........................");

		// 사용하려는 데이터베이스명을 포함한 URL 기술
		String url = "jdbc:mysql://10.10.10.102:3306/jr?useSSL=false&serverTimezone=UTC";
		// 사용자 계정
		String id = "gonnys";
		// 사용자 계정의 패스워드
		String pw = "12345678";

		// 데이터베이스와 연동하기 위해 DriverManager에 등록한다.
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// DriverManager 객체로부터 Connection 객체를 얻어온다.
		Connection con = DriverManager.getConnection(url, id, pw);

		log.info(con);
		
		PreparedStatement pstmt = con.prepareStatement("select now()");
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			log.info(rs.getString(1));
		
		}
		rs.close();
		pstmt.close();
		con.close();
	}
	
}
- 코드 작성후 junit test 실행해서 정상적으로 작동하는 지 확인.


<4. MySQL 설치 및 설정>
(방화벽 생성 --> 스키마 생성 --> 사용자 계정 생성 --> 연결 테스트)
1. 긍정적으로... MySQL Installer 설치
account and roles
- password 12345678
- user name: jr2018 password: jr2018

2. 고급 보안이 포함된 windows 방화벽 --> 인바운드, 아웃바운드 규칙 --> 프로토콜 및 포트 --> 3306 추가


3. 제어판 --> 시스템 및 보안 --> 관리도구 --> 서비스 --> MySQL80 --> 수동으로 설정
- 평소에는 꺼놓고 있음 ( 자기 PC에 항상 돌리지 X -> 너무 무겁기 때문에)

4. MySQL접속 --> Home의 MySQL Conncetion --> root로 접속(기본 생성자) --> Navigatordml MANAGEMENT에서 User and Privileges
--> 하단의 Add Account --> Login tab에서 Login Name 및 Password 설정 --> 
Administartive Roles tab에서 전체 선택 후 Apply -->
Schema Privileges tab에서 Add Entry --> All Schema(%) --> OK --> Select*ALL* --> Apply

5. Spring을 통해 연결 테스트



--! [create table] --
AI는 auto import
PK-AI 는 항상 쌍이다
